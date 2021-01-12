package com.wonsang.madcampweek2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditProfileActivity extends AppCompatActivity {

    ImageButton editProfile;
    EditText editBlogTitle;
    EditText editDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editProfile = findViewById(R.id.editProfile);
        editBlogTitle = findViewById(R.id.editBlogTitle);
        editDescription = findViewById(R.id.editDescription);
    }
}