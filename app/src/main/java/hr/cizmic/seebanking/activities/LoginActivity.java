package hr.cizmic.seebanking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import hr.cizmic.seebanking.databinding.ActivityLoginBinding;
import hr.cizmic.seebanking.links.LoginLink;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "BANKA";
    LoginLink link;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("BANKA", "LOGIN");
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        link = new ViewModelProvider(this).get(LoginLink.class);

        link.get_login_info().observe(this, login -> {
            binding.loginName.setText(login.get_name());
            binding.loginSurname.setText(login.get_surname());
        });

        binding.loginButton.setOnClickListener(view -> {
            if (LoginLink.isValidPass(binding.editTextNumberPassword.getText().toString())) {
                link.isCorrectPass(binding.editTextNumberPassword.getText().toString()).observe(this, correct -> {
                    if (correct)
                        startActivity(new Intent(this, AccountsActivity.class));
                    else
                        Toast.makeText(this, "incorrect password", Toast.LENGTH_SHORT).show();
                });
            }
            else
                Toast.makeText(this, "password must be 4 - 6 digits long", Toast.LENGTH_SHORT).show();
        });

        binding.removeButton.setOnClickListener(view -> {
            link.removeUserInfo();
            startActivity(new Intent(this, LaunchActivity.class));
        });
    }
}