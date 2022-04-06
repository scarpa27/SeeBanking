package hr.cizmic.seebanking.activities;

import android.app.Application;

import hr.cizmic.seebanking.repositories.Repository;

public class SeeBankingApp extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Repository.instance();
    }

    public static Application instance() {
        return instance;
    }
}
