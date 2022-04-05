package hr.cizmic.seebanking.activities;

import android.app.Application;

public class SeeBankingApp extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application instance() {
        return instance;
    }
}
