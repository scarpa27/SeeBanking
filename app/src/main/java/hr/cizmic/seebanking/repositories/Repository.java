package hr.cizmic.seebanking.repositories;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.cizmic.seebanking.R;
import hr.cizmic.seebanking.activities.LaunchActivity;
import hr.cizmic.seebanking.activities.SeeBankingApp;
import hr.cizmic.seebanking.models.Account;
import hr.cizmic.seebanking.models.Login;
import hr.cizmic.seebanking.models.User;
import hr.cizmic.seebanking.util.JSONParse;
import hr.cizmic.seebanking.util.Util;

public class Repository {
    private static final String TAG = "BANKA";
    
    private MutableLiveData<Login> mutLogin;
    private MutableLiveData<User> mutLiveUser;
    private MutableLiveData<Account> mutLiveAcc;
    private MutableLiveData<Boolean> passMatch;
    private User holdUser;
    private Account holdAcc;
    private int currAcc = 0;

    //region Singleton
    private static Repository instance;
    public static Repository instance() {
        return instance = instance == null ? new Repository() : instance;
    }
    //endregion

    private Repository() { setup(); }

    public LiveData<Login> getLiveLoginInfo() {
        fetchLoginInfo();
        return mutLogin;
    }

    //simulacija provjere u backendu
    public boolean checkPassword(String pass) {
        return pass.equals(Objects.requireNonNull(mutLogin.getValue()).get_password());
    }

    public LiveData<User> getLiveUserInfo() {
        fetchData();
        return mutLiveUser;
    }

    public LiveData<Account> getLiveAccountInfo() {
        fetchData();
        return mutLiveAcc;
    }

    public void postNewUserInfo(String... s) {
        String info = s[0]+";"+s[1]+";"+s[2];
        Application app = SeeBankingApp.instance();
        SharedPreferences pref = app.getSharedPreferences(app.getString(R.string.login_info), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(app.getString(R.string.login_info),info);
        edit.apply();
    }

    private void setup() {
        if (mutLiveUser == null)
            mutLiveUser = new MutableLiveData<>();
        if (mutLiveUser.getValue() == null)
            mutLiveUser.postValue(new User());
        if (mutLiveAcc == null)
            mutLiveAcc = new MutableLiveData<>();
        if (mutLiveAcc.getValue() == null)
            mutLiveAcc.postValue(new Account());
        if (mutLogin == null)
            mutLogin = new MutableLiveData<>();
        if (mutLogin.getValue() == null)
            mutLogin.postValue(new Login("2","",""));
        if (passMatch == null)
            passMatch = new MutableLiveData<>();
        if (passMatch.getValue() == null)
            passMatch.postValue(false);
    }

    private void fetchLoginInfo() {
        Application app = SeeBankingApp.instance();
        SharedPreferences pref = app.getSharedPreferences(app.getString(R.string.login_info), Context.MODE_PRIVATE);
        String[] info = pref.getString(app.getString(R.string.login_info),"").split(";");

        mutLogin.setValue(new Login(info[0],info[1],info[2]));
    }

    public void removeUserInfo() {
        Application app = SeeBankingApp.instance();
        SharedPreferences pref = app.getSharedPreferences(app.getString(R.string.login_info), Context.MODE_PRIVATE);
        pref.edit().remove(app.getString(R.string.login_info)).apply();
    }

    private void fetchData() {
        Runnable fetchJsonRunnable = this::queryUrl;

        ExecutorService threadpool = Executors.newSingleThreadExecutor();
        threadpool.execute(fetchJsonRunnable);
        threadpool.shutdown();

        Log.d(TAG, "thread lifecycle done");
    }

    private void queryUrl() {
        Log.d(TAG, "runnable started");

        holdUser = JSONParse.parseJSONtoTransactionList();

        //additional fake transactions
        holdUser = Util.generateFakeData(holdUser);

        postHoldToMutable();
    }

    private void postHoldToMutable() {
        holdAcc = holdUser.getAccounts().get(getCurrAcc());
        mutLiveUser.postValue(holdUser);
        mutLiveAcc.postValue(holdAcc);
    }

    private int getCurrAcc() {
        int noAcc = holdUser.getAccounts().size();
        return currAcc =
                currAcc < 0 ? noAcc - 1
                        : currAcc >= noAcc ? 0 : currAcc;
    }

    public void SwipeRight() {
        currAcc++;
        Log.d("BANKA", "SWIPE RIGHT");
        postHoldToMutable();
    }

    public void SwipeLeft() {
        currAcc--;
        Log.d("BANKA", "SWIPE LEFT");
        postHoldToMutable();
    }



}
