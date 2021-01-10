package com.wonsang.madcampweek2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wonsang.madcampweek2.fragment.Fragment1;
import com.wonsang.madcampweek2.fragment.GalleryFragment;
import com.wonsang.madcampweek2.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LoginManagement loginManagement = LoginManagement.getInstance();
    private ViewpagerAdapter viewpagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = findViewById(R.id.viewpager);
        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        vp.setAdapter(viewpagerAdapter);

        TabLayout tab = findViewById(R.id.tabLayout);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.contact);
        images.add(R.drawable.photos);
        images.add(R.drawable.health);

        for (int i = 0; i < 3; i++) tab.getTabAt(i).setIcon(images.get(i));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Fragment1.CONTACT_ADD_REQUEST && data != null) {
            String name = data.getStringExtra("name");
            String phoneNumber = data.getStringExtra("phoneNumber");
            Contact contact = new Contact(name, phoneNumber);
            Fragment1 fragment1 = (Fragment1) viewpagerAdapter.getItems().get(ViewpagerAdapter.CONTACT_POSITION);
            fragment1.notifyAddContact(contact);
        }
        else if(requestCode == GalleryFragment.CAMERA_REQUEST_CODE){
            ((GalleryFragment)viewpagerAdapter.getItem(ViewpagerAdapter.GALLERY_POSITION)).capture();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        alt_bld.setMessage("로그아웃 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("네",
                        (dialog, id) -> {
                            loginManagement.logout(getApplicationContext(), getString(R.string.client_id));
                            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                            startActivity(intent);
                        }).setNegativeButton("아니오",
                (dialog, id) -> {
                    dialog.cancel();
                });
        AlertDialog alert = alt_bld.create();
        alert.setTitle("로그아웃");
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 62, 79, 92)));
        alert.show();
    }
}