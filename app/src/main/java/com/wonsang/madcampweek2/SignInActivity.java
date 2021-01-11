package com.wonsang.madcampweek2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;



public class SignInActivity extends AppCompatActivity implements ApiCallable<String>{

    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        signInButton = findViewById(R.id.sign_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        LoginManagement loginManagement = LoginManagement.getInstance();

        signInButton.setOnClickListener(v -> {
            Intent intent = loginManagement.login(this, getString(R.string.client_id));
            startActivityForResult(intent, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            AccountDatabase ab = AccountDatabase.getAppDatabase(this);
            handleSignInResult(task, ab);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask, AccountDatabase ab) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String email = account.getEmail();
            String m = account.getFamilyName();
            String m2 = account.getGivenName();
            String m3 = account.getDisplayName();
            String m4 = account.getIdToken();
            Log.d("TOKEN", account.getIdToken());
            Log.d("Name : ", m);
            Log.d("Name 2: ", m2);
            Log.d("Name 3: ", m3);
            Log.d("Email :", email);

            ab.AccountDataDao().insert(new AccountData(m4, email));
            ApiProvider apiProvider = new ApiProvider(this);
            apiProvider.isValidAccessToken(account.getIdToken(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, String response) {
        if (type == ApiProvider.RequestType.TOKEN_VALIDATION) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void getError(VolleyError error) {
        System.out.println(error);
    }
}