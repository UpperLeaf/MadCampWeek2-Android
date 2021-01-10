package com.wonsang.madcampweek2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wonsang.madcampweek2.api.ApiProvider;

public class DeleteContactActivity extends AppCompatActivity {
    private ApiProvider apiProvider;
    private int id;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);
    }
}