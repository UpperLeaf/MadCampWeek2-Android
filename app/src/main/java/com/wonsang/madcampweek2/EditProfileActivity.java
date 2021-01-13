package com.wonsang.madcampweek2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Contact;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity implements ApiCallable<JSONObject>, View.OnClickListener {

    private static final int CAMERA_PICK = 510;
    ImageButton editProfile;
    String editImage;
    EditText editBlogTitle;
    EditText editDescription;
    Uri selectedImage;
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
                if(selectedImage != null) {
                    String image = new String(getImageContent(selectedImage));
                    apiProvider.saveProfile(token, newBlogTitle, newDescription, image, this);
                }
                else
                    apiProvider.saveProfile(token, newBlogTitle, newDescription, this);
                break;
            case R.id.editProfile:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CAMERA_PICK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_PICK && data != null) {
            selectedImage = data.getData();
            Glide.with(this).load(selectedImage).into(editProfile);
        }
    }

    private byte[] getImageContent(Uri uri){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = getContentResolver().openInputStream(uri);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
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
