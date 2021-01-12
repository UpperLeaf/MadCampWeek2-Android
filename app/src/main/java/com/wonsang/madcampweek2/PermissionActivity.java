package com.wonsang.madcampweek2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;

public class PermissionActivity extends AppCompatActivity implements ApiCallable<String> {

    private static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ApiProvider apiProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        setTheme(R.style.Theme_AppCompat_DayNight);
        initializeGrant();

//        AccountDatabase ab = AccountDatabase.getAppDatabase(this);
//        if (ab.AccountDataDao().getCount() >= 1) {
//            hasToken = true;
//            apiProvider = new ApiProvider(this);
//            String token = LoginManagement.getInstance().getToken(this);
//            apiProvider.isValidAccessToken(token, this);
//        }else {
//            hasToken = false;
//            intentSignInActivity();
//        }
    }

    @Override
    public void onBackPressed() {
    }

    private void initializeGrant() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, 100);
    }

    private void intentMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AccountDatabase ab = AccountDatabase.getAppDatabase(this);
        if (ab.AccountDataDao().getCount() >= 1) {
            apiProvider = new ApiProvider(this);
            String token = LoginManagement.getInstance().getToken(this);
            apiProvider.isValidAccessToken(token, this);
        }else {
            intentSignInActivity();
        }
    }

    private void intentSignInActivity() {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, String response) {
        intentMainActivity();
    }

    @Override
    public void getError(VolleyError error) {
        AccountDatabase ab = AccountDatabase.getAppDatabase(this);
        ab.AccountDataDao().deleteAll();
        intentSignInActivity();
    }
}