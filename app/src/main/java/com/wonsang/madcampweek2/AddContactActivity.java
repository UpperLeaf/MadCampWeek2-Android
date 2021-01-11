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

import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;

import org.json.JSONObject;


public class AddContactActivity extends AppCompatActivity implements ApiCallable<JSONObject>, View.OnClickListener{
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
        apiProvider.addContact(token, name, email, this);
    }


    @Override
    public void getResponse(ApiProvider.RequestType type, JSONObject response) {
        if (type == ApiProvider.RequestType.ADD_CONTACTS){
            try {
                String name = response.getString("name");
                String email = response.getString("email");
                int id = response.getInt("id");

                Intent intent = new Intent();
                intent.putExtra("id", id);
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
        System.out.println(error);
    }
}