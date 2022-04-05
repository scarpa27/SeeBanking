package hr.cizmic.seebanking.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import hr.cizmic.seebanking.R;

public class LaunchActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,
                hasLocalUserInfo() ? LoginActivity.class : RegisterActivity.class));
    }

    private boolean hasLocalUserInfo() {
        return getSharedPreferences(getString(R.string.login_info), Context.MODE_PRIVATE).contains(getString(R.string.login_info));
    }
}
