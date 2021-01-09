package com.wonsang.madcampweek2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.api.JsonHeaderRequest;

import java.util.Map;


public class SigninActivity extends AppCompatActivity implements ApiCallable {

    SignInButton signInButton;
    private ApiProvider apiProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        signInButton = findViewById(R.id.sign_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        AccountDatabase ab = AccountDatabase.getAppDatabase(this);
        LoginManagement loginManagement = LoginManagement.getInstance();
        apiProvider = new ApiProvider(this);
        if (ab.AccountDataDao().getCount() >= 1) {
            AccountData data = ab.AccountDataDao().findAccountDataLimitOne();
            apiProvider.isValidAccessToken(data.getToken(), this);
        }
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
            String m2 =account.getGivenName();
            String m3 = account.getDisplayName();
            String m4 = account.getIdToken();
            Log.d("TOKEN", account.getIdToken());
            Log.d("Name : ", m);
            Log.d("Name 2: ", m2);
            Log.d("Name 3: ", m3);
            Log.d("Email :", email);
            new InsertAsyncTask(ab.AccountDataDao()).execute(new AccountData(m4));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
        if(type == ApiProvider.RequestType.TOKEN_VALIDATION){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void getError(VolleyError error) {
//        LoginManagement loginManagement = LoginManagement.getInstance();
        AccountDatabase ab = AccountDatabase.getAppDatabase(this);
        ab.AccountDataDao().deleteAll();
//        revokeAccess();

//        Intent intent = loginManagement.login(this, getString(R.string.client_id));
//        startActivityForResult(intent, 100);

    }

    public static class InsertAsyncTask extends AsyncTask<AccountData, Void, Void> {
        private AccountDataDao mAccountDataDao;


        public InsertAsyncTask(AccountDataDao accountDatadao){
            this.mAccountDataDao = accountDatadao;
        }
        @Override
        protected Void doInBackground(AccountData... AccountDatas) {
            mAccountDataDao.insert(AccountDatas[0]);
            return null;
        }
    }

//    public static class AgentAsyncTask extends AsyncTask<Void, Void, Integer> {
////        private AccountDataDao mAccountDataDao;
//        private WeakReference<Activity> weakActivity;
//        private String Token;
//
//        public AgentAsyncTask(Activity activity, String Token) {
//            weakActivity = new WeakReference<>(activity);
//            this.Token =Token;
//
//        }
//        @Override
//        protected Integer doInBackground(Void... params) {
//            AccountDataDao accountDataDao = AccountDatabase.getAppDatabase.AccountDataDao();
//        }
//
//        @Override
//        protected Integer doInBackground(Void... params) {
//            Activity activity = weakActivity.get();
//            if (activity == null) {
//                return;
//            }
//
//            if (agentsCount )
//        }
//        public CheckAsyncTask(AccountDataDao accountDatadao){
//            this.mAccountDataDao = accountDatadao;
//        }
//        CheckAsyncTask.execute(new Runnable(){
//            @Override
//            public void run() {
//                AccountDatabase.getInstance(context)
//            }
//        })
//    }
}