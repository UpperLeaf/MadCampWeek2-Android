package com.wonsang.madcampweek2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

public class LoginManagement implements OAuthLogin{

    private static LoginManagement instance = new LoginManagement();
    private GoogleSignInClient client;
    private String token;
    private String email;

    private LoginManagement() {

    }



    public static LoginManagement getInstance() {
        return instance;
    }

    public Intent login(Context context, String clientId) {
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(clientId)
                        .requestProfile()
                        .requestEmail()
                        .build();

        client = GoogleSignIn.getClient(context, gso);
        return client.getSignInIntent();
    }

    @Override
    public void logout(Context context, String clientId) {
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(clientId)
                        .requestProfile()
                        .requestEmail()
                        .build();

        client = GoogleSignIn.getClient(context, gso);
        client.signOut();
        client.revokeAccess();
        AccountDatabase ab = AccountDatabase.getAppDatabase(context);
        ab.AccountDataDao().deleteAll();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken(Context context) {
        if (token == null)
            token = AccountDatabase.getAppDatabase(context).AccountDataDao().findAccountDataLimitOne().getToken();
        return token;
    }
}

