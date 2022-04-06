package hr.cizmic.seebanking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hr.cizmic.seebanking.R;
import hr.cizmic.seebanking.databinding.ActivityRegisterBinding;
import hr.cizmic.seebanking.links.LoginLink;
import hr.cizmic.seebanking.links.RegisterLink;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "BANKA";
    RegisterLink link;
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "REGISTER");
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        link = new ViewModelProvider(this).get(RegisterLink.class);

        binding.btRegister.setOnClickListener(view -> {

            if (
                    RegisterLink.isInvalidName(binding.etName.getText().toString())
                    || RegisterLink.isInvalidName(binding.etSurname.getText().toString())
                    || !LoginLink.isValidPass(binding.etPass.getText().toString()))
            {
                Toast.makeText(this, "Name and surname shouldn't be empty and should be shorter than 30 characters. \nPassword should contain 4 to 6 digits.", Toast.LENGTH_LONG).show();
            }
            else {
                link.postRegistrationValues(
                        binding.etName.getText().toString(),
                        binding.etSurname.getText().toString(),
                        binding.etPass.getText().toString()
                );
                startActivity(new Intent(this, LaunchActivity.class));
            }
        });
    }
}