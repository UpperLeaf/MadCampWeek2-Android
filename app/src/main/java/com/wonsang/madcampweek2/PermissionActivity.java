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
import com.wonsang.madcampweek2.api.JsonHeaderRequest;

public class PermissionActivity extends AppCompatActivity implements ApiCallable {

    private static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private ApiProvider apiProvider;
    private boolean isTokenValid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        initializeGrant();

        AccountDatabase ab = AccountDatabase.getAppDatabase(this);
        apiProvider = new ApiProvider(this);
        if (ab.AccountDataDao().getCount() >= 1) {
            String token = LoginManagement.getInstance().getToken(this);
            apiProvider.isValidAccessToken(token, this);
        }
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

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && isTokenValid){
            intentMainActivity();
        }
        else
            intentSignInActivity();
    }

    private void intentSignInActivity() {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
        if (type == ApiProvider.RequestType.TOKEN_VALIDATION) {
            isTokenValid = true;
        }
        else
            isTokenValid = false;
    }

    @Override
    public void getError(VolleyError error) {
        AccountDatabase ab = AccountDatabase.getAppDatabase(this);
        ab.AccountDataDao().deleteAll();
    }
}