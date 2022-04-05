package hr.cizmic.seebanking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import hr.cizmic.seebanking.R;
import hr.cizmic.seebanking.links.LoginLink;
import hr.cizmic.seebanking.models.Login;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("BANKA","login activity" );
        LoginLink link = new ViewModelProvider(this).get(LoginLink.class);
        link.get_login_info().observe(this, new Observer<Login>() {
            @Override
            public void onChanged(Login login) {

            }
        });
        findViewById(R.id.login_button).setOnClickListener(view -> {
            startActivity(new Intent(this, AccountsActivity.class));
        });

    }

}