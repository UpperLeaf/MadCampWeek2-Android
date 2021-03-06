package com.wonsang.madcampweek2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.fragment.Fragment3;

import org.json.JSONObject;

public class AddPostActivity extends AppCompatActivity implements ApiCallable<JSONObject>, View.OnClickListener {

    private EditText etTitle;
    private EditText etContent;
    private Button btnSend;
    private ApiProvider apiProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSend = findViewById(R.id.btnSend);

        apiProvider = new ApiProvider(this);
        btnSend.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String token = LoginManagement.getInstance().getToken(this);
        apiProvider.addPost(token, title, content, this);
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JSONObject response) {
        if (type == ApiProvider.RequestType.ADD_POST){
            try {
                int id = response.getInt("id");
                String title = response.getString("title");
                String content = response.getString("content");

                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                setResult(Fragment3.POST_ADD_REQUEST, intent);
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