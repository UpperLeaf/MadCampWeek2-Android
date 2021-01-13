package com.wonsang.madcampweek2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Post;

import org.json.JSONObject;

public class EditPostActivity extends AppCompatActivity implements ApiCallable<JSONObject> {

    private TextView title;
    private EditText editTitle;
    private EditText editContent;
    private ApiProvider apiProvider;

    private Button button;

    private int id;
    private String titleValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        this.title = findViewById(R.id.wordText);
        this.editTitle = findViewById(R.id.edit_title);
        this.editContent = findViewById(R.id.edit_content);
        this.button = findViewById(R.id.save_button);
        this.apiProvider = new ApiProvider(this);
        Intent intent = getIntent();

        id = intent.getExtras().getInt("id");
        titleValue = intent.getExtras().getString("title");

        this.title.setText(titleValue);

        button.setOnClickListener((v) -> {
            String editTitleValue = editTitle.getText().toString();
            String editContentValue = editContent.getText().toString();

            Post post = new Post(editTitleValue, editContentValue, id);
            apiProvider.editPost(LoginManagement.getInstance().getToken(this), post, this);
        });
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JSONObject response) {
        Intent intent = new Intent();
        String editTitleValue = editTitle.getText().toString();
        String editContentValue = editContent.getText().toString();

        intent.putExtra("editTitle", editTitleValue);
        intent.putExtra("editContent", editContentValue);
        setResult(100, intent);
        finish();
    }

    @Override
    public void getError(VolleyError error) {
        System.out.println(error);
    }
}
