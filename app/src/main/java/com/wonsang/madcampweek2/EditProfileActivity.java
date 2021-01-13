package com.wonsang.madcampweek2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Contact;

import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity implements ApiCallable<JSONObject>, View.OnClickListener {

    ImageButton editProfile;
    String editImage;
    EditText editBlogTitle;
    EditText editDescription;
    ApiProvider apiProvider;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editProfile = findViewById(R.id.editProfile);
        editBlogTitle = findViewById(R.id.editBlogTitle);
        editDescription = findViewById(R.id.editDescription);
        saveBtn = findViewById(R.id.save_button);
        apiProvider = new ApiProvider(this);
        String token = LoginManagement.getInstance().getToken(this);
        editProfile.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                String newBlogTitle = editBlogTitle.getText().toString();
                String newDescription = editDescription.getText().toString();

                apiProvider = new ApiProvider(this);
                String token = LoginManagement.getInstance().getToken(this);
                apiProvider.saveProfile(token, newBlogTitle, newDescription, this);
                break;
            case R.id.editProfile:
                //TODO: 형한테 부탁
                break;
        }
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JSONObject response) {
        if (type == ApiProvider.RequestType.EDIT_PROFILE){
            try {
                String blogTitle = editBlogTitle.getText().toString();
                String description = editDescription.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("blogTitle", blogTitle);
                intent.putExtra("description", description);
                setResult(101, intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getError(VolleyError error) {
        System.out.println(error);
    }

}
