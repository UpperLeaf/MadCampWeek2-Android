package com.wonsang.madcampweek2;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.wonsang.madcampweek2.model.Contact;

import java.util.ArrayList;
import java.util.List;
import android.view.View;

import android.view.animation.Animation;

import android.view.animation.AnimationUtils;

import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    public GoogleSignInClient mGoogleSignInClient;
    private LoginManagement loginManagement = LoginManagement.getInstance();
    private ViewpagerAdapter viewpagerAdapter;
    private TextView mResultTextView;
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

        for(int i=0;i<3;i++) tab.getTabAt(i).setIcon(images.get(i));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && data != null) {
            String name = data.getStringExtra("name");
            String phoneNumber = data.getStringExtra("phoneNumber");
            Contact contact = new Contact(name, phoneNumber);
            Fragment1 fragment1 = (Fragment1) viewpagerAdapter.getItems().get(ViewpagerAdapter.CONTACT_POSITION);
            fragment1.notifyAddContact(contact);
        }

        else if (requestCode == 101 && data!= null) {
            int id = data.getExtras().getInt("id");
            int pos = data.getExtras().getInt("position");
            System.out.println(pos);
            String newname = data.getStringExtra("name");
            String newphNumbers = data.getStringExtra("phoneNumber");
            Contact contact = new Contact( newname, newphNumbers, id);
            Fragment1 fragment1 = (Fragment1) viewpagerAdapter.getItems().get(ViewpagerAdapter.CONTACT_POSITION);
            fragment1.notifyEditContact(contact, pos);

        }
    }

    @Override
    public void onBackPressed() {
////        AlertDialog.Builder alt_bld = new AlertDialog.Builder((getApplicationContext()));
////        alt_bld.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("네", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                signOut();
////
////        }).setNegativeButton("아니오", new DialogInterface.OnClickListener(){
////            public void onClick(DialogInterface dialog, int id){
////                dialog.cancel();
////            }
////        })
////        })
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        alt_bld.setMessage("로그아웃 하시겠습니까?").setCancelable(false)

                .setPositiveButton("네",

                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                // 네 클릭

                                // 로그아웃 함수 call

                                loginManagement.logout(getApplicationContext(), getString(R.string.client_id));
                                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                startActivity(intent);
//                                revokeAccess();

                            }

                        }).setNegativeButton("아니오",

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        // 아니오 클릭. dialog 닫기.

                        dialog.cancel();

                    }

                });

        AlertDialog alert = alt_bld.create();

        alert.setTitle("로그아웃");

//        alert.setIcon(R.drawable.check_dialog_64)
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,62,79,92)));
        alert.show();
    }

//    public void signOut() {
//        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                AccountDatabase ab = AccountDatabase.getAppDatabase(getApplicationContext());
//                ab.AccountDataDao().deleteAll();
//            }
//        });
//    }
}

