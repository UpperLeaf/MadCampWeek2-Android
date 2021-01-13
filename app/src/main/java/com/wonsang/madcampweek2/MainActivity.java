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
import com.wonsang.madcampweek2.fragment.Fragment3;
import com.wonsang.madcampweek2.fragment.GalleryFragment;
import com.wonsang.madcampweek2.model.Contact;
import com.wonsang.madcampweek2.model.Post;

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
            int id = data.getExtras().getInt("id");
            String name = data.getStringExtra("name");
            String email = data.getStringExtra("email");
            Contact contact = new Contact(name, email, id);

            Fragment1 fragment1 = (Fragment1) viewpagerAdapter.getItems().get(ViewpagerAdapter.CONTACT_POSITION);
            fragment1.notifyAddContact(contact);

        } else if (requestCode == 101 && data != null) {
            int id = data.getExtras().getInt("id");
            String newName = data.getStringExtra("name");
            String newEmail = data.getStringExtra("email");

            Contact contact = new Contact(newName, newEmail, id);

            Fragment1 fragment1 = (Fragment1) viewpagerAdapter.getItems().get(ViewpagerAdapter.CONTACT_POSITION);
            fragment1.notifyEditContact(contact);
        } else if (requestCode == GalleryFragment.CAMERA_REQUEST_CODE && resultCode == -1) {
            ((GalleryFragment) viewpagerAdapter.getItem(ViewpagerAdapter.GALLERY_POSITION)).capture();
        } else if (requestCode == Fragment3.POST_ADD_REQUEST && data != null) {
            int id = data.getExtras().getInt("id");
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            Post post = new Post(title, content, id);

            Fragment3 fragment3 = (Fragment3) viewpagerAdapter.getItems().get(ViewpagerAdapter.BLOG_POSITION);
            fragment3.notifyAddPost(post);
        } else if (requestCode == Fragment3.PROFILE_EDIT_REQUEST && data!= null) {
            String title = data.getStringExtra("blogTitle");
            String description = data.getStringExtra("description");
            boolean changedProfileImage = data.getExtras().getBoolean("changedProfileImage");
            Uri profileImage = null;
            if(changedProfileImage)
                profileImage = data.getData();
            Fragment3 fragment3 = (Fragment3) viewpagerAdapter.getItems().get(ViewpagerAdapter.BLOG_POSITION);
            fragment3.notifyEditProfile(title, description, changedProfileImage, profileImage);

        } else if (requestCode == GalleryFragment.CAMERA_PICK && data != null){
            Uri selectedImage = data.getData();
            ((GalleryFragment) viewpagerAdapter.getItem(ViewpagerAdapter.GALLERY_POSITION)).selectImage(selectedImage);
        }
        else if (requestCode == Fragment3.EDIT_POST_REQUEST && data != null){
            String title = data.getStringExtra("editTitle");
            String content = data.getStringExtra("editContent");
            Fragment3 fragment3 = (Fragment3) viewpagerAdapter.getItems().get(ViewpagerAdapter.BLOG_POSITION);
            fragment3.notifyEditPost(title, content);
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
