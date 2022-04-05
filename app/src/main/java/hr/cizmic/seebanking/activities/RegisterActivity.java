package hr.cizmic.seebanking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import hr.cizmic.seebanking.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.register).setOnClickListener(view -> {
            SharedPreferences pref = getSharedPreferences(getString(R.string.login_info),Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
            edit.putString(getString(R.string.login_info), "toni;cizmic;123456");
            edit.apply();
            startActivity(new Intent(this, LaunchActivity.class));
        });
    }
}