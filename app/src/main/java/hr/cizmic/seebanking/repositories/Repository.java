package hr.cizmic.seebanking.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.cizmic.seebanking.R;
import hr.cizmic.seebanking.activities.SeeBankingApp;
import hr.cizmic.seebanking.models.Account;
import hr.cizmic.seebanking.models.Login;
import hr.cizmic.seebanking.models.User;
import hr.cizmic.seebanking.util.JSONParse;
import hr.cizmic.seebanking.util.Util;

public class Repository {
    private MutableLiveData<Login> mutLogin = new MutableLiveData<>();
    private MutableLiveData<User> mutLiveUser;
    private MutableLiveData<Account> mutLiveAcc;
    private User holdUser;
    private Account holdAcc;
    private int currAcc = 0;

    //region Singleton
    private static Repository instance;
    public static Repository instance() {
        return instance = instance == null ? new Repository() : instance;
    }
    //endregion

    public LiveData<Login> getLiveLoginInfo() {
        provjera();
        fetchLoginInfo();
        return mutLogin;
    }

    public LiveData<User> getLiveUserInfo() {
        provjera();
        fetchData();
        return mutLiveUser;
    }

    public LiveData<Account> getLiveAccountInfo() {
        provjera();
        fetchData();
        return mutLiveAcc;
    }

    private void provjera() {
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
            mutLogin.postValue(new Login("","",""));
    }

    private void fetchLoginInfo() {
        Application app = SeeBankingApp.instance();
        SharedPreferences pref = app.getSharedPreferences(app.getString(R.string.login_info), Context.MODE_PRIVATE);
        String[] info = pref.getString(app.getString(R.string.login_info),"").split(";");
        mutLogin.setValue(new Login(info[0],info[1],info[2]));
    }

    private void fetchData() {
        Runnable fetchJsonRunnable = this::queryUrl;

        ExecutorService threadpool = Executors.newSingleThreadExecutor();
        threadpool.execute(fetchJsonRunnable);
        threadpool.shutdown();


        Log.d("BANKA", "thread lifecycle done");
    }

    private void queryUrl() {
        Log.d("BANKA", "runnable started");

        holdUser = JSONParse.parseJSONtoTransactionList();

        //additional fake transactions
        holdUser = Util.generateFakeData(holdUser);

        postHoldToMutable();
    }

    private int getCurrAcc() {
        int noAcc = holdUser.getAccounts().size();
        return currAcc =
                currAcc < 0 ? noAcc - 1
                : currAcc >= noAcc ? 0 : currAcc;
    }

    private void postHoldToMutable() {
        holdAcc = holdUser.getAccounts().get(getCurrAcc());
        mutLiveUser.postValue(holdUser);
        mutLiveAcc.postValue(holdAcc);
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
