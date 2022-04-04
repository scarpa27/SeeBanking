package hr.cizmic.seebanking;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.cizmic.seebanking.models.User;
import hr.cizmic.seebanking.util.JSONParse;

public class Repository {
    private MutableLiveData<User> mutLiveUser;
    private User holdData;

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

    public void provjera() {
        if (mutLiveUser == null)
            mutLiveUser = new MutableLiveData<>();
        if (mutLiveUser.getValue() == null)
            mutLiveUser.postValue(new User());
        fetchData();
    }

    public void fetchData() {
        Runnable fetchJsonRunnable = this::queryUrl;

        ExecutorService threadpool = Executors.newSingleThreadExecutor();
        threadpool.execute(fetchJsonRunnable);
        threadpool.shutdown();

        mutLiveUser.setValue(holdData);
        Log.d("BANKA", "thread lifecycle done");
    }

    private void queryUrl() {
        Log.d("BANKA", "runnable started");
        holdData = JSONParse.parseJSONtoTransactionList();
        holdData.printUser();
        mutLiveUser.postValue(holdData);
    }

}
