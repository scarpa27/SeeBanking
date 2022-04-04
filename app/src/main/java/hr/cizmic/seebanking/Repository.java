package hr.cizmic.seebanking;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.cizmic.seebanking.models.Account;
import hr.cizmic.seebanking.models.User;
import hr.cizmic.seebanking.util.JSONParse;
import hr.cizmic.seebanking.util.Util;

public class Repository {
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


    public LiveData<User> getLiveUserInfo() {
        provjera();
        return mutLiveUser;
    }

    public LiveData<Account> getLiveAccountInfo() {
        provjera();
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
        fetchData();
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
        mutLiveAcc.postValue(holdAcc.sortTransactions());
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
