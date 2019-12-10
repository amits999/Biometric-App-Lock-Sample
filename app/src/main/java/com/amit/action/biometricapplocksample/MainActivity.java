package com.amit.action.biometricapplocksample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Executor executor = Executors.newSingleThreadExecutor();

        final FragmentActivity activity = this;

        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                    Toast.makeText(activity, "Operation Cancelled By User!", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO: Called when an unrecoverable error has been encountered and the operation is complete.
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //TODO: Called when a biometric is recognized.
                startActivity(new Intent(MainActivity.this, LoggedInActivity.class));
                Toast.makeText(activity, "Login Successful!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //TODO: Called when a biometric is valid but not recognized.
                Toast.makeText(activity, "Fingerprint not recognized!", Toast.LENGTH_SHORT).show();
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Set the title to display.")
                .setSubtitle("Set the subtitle to display.")
                .setDescription("Set the description to display")
                .setNegativeButtonText("Negative Button")
                .build();

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}
