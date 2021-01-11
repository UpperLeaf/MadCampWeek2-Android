package com.wonsang.madcampweek2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.api.JsonHeaderRequest;
import com.wonsang.madcampweek2.api.VolleyManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddContactActivity extends AppCompatActivity implements ApiCallable, View.OnClickListener{
    private TextView tv;
    private EditText etName;
    private EditText etEmail;
    private Button btnSend;
    private ApiProvider apiProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        tv = findViewById(R.id.tvMain);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etPhNumber);
        btnSend = findViewById(R.id.btnSend);

        apiProvider = new ApiProvider(this);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String token = LoginManagement.getInstance().getToken(this);
        apiProvider.AddContact(token, name, email, this);
    }


    @Override
    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
        if (type == ApiProvider.RequestType.ADD_CONTACTS){
            try {
                String name = response.getResponse().getJSONObject(0).getString("name");
                String email = response.getResponse().getJSONObject(0).getString("email");

                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                setResult(100, intent);
                finish();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getError(VolleyError error) {

    }
}