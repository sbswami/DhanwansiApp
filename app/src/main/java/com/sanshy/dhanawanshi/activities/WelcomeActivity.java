package com.sanshy.dhanawanshi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sanshy.dhanawanshi.Home;
import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void GetStarted(View view){
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.PhoneBuilder().build());

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                ST.currentUser = FirebaseAuth.getInstance().getCurrentUser();
                ST.mUid = ST.currentUser.getUid();
                startActivity(new Intent(this, Home.class));
                // ...
            } else {

                ST.ShowDialog(this,getString(R.string.something_wrong));

                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
