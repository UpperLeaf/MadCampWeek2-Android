package com.wonsang.madcampweek2.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager2.widget.ViewPager2;

import com.wonsang.madcampweek2.AccountData;
import com.wonsang.madcampweek2.AccountDatabase;
//import com.wonsang.madcampweek2.ContactDialog;
import com.wonsang.madcampweek2.LoginManagement;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.SignInActivity;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Post;

public class PostView {

//    LinearLayout bg;
//    TextView title;
//    TextView content;
//    Button Editbutton;
//    Button Deletebutton;
//    ApiProvider apiProvider;


//    public PostView(Context context, Post post, int flag) {
//        super(context);
//        initView(post, flag);
//    }

//    private void changeView(Context context, Post post, int flag) {
//        String infService = Context.LAYOUT_INFLATER_SERVICE;
//        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
//        View v =li.inflate(R.layout.postview_layout, this, false);
//        this.removeAllViews();
//        addView(v);
//        bg = (LinearLayout) findViewById(R.id.bg);
//        title = (TextView) findViewById(R.id.post_title);
//        content = (TextView) findViewById(R.id.post_content);
//
//        bg.setBackgroundResource(R.drawable.border);
//        title.setText(post.getTitle());
//        content.setText(post.getContent());
//
//    }
//
//    private void initView(Post post, int flag) {
//
//        String infService = Context.LAYOUT_INFLATER_SERVICE;
//        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
//        View v =li.inflate(R.layout.postview_layout, this, false);
//        addView(v);
//
//        bg = (LinearLayout) findViewById(R.id.bg);
//        title = (TextView) findViewById(R.id.post_title);
//        content = (TextView) findViewById(R.id.post_content);
//
//        bg.setBackgroundResource(R.drawable.border);
//        title.setText(post.getTitle());
//        content.setText(post.getContent());
//
//        if (flag==1) {
//            Editbutton = v.findViewById(R.id.editbutton);
//            Deletebutton = v.findViewById(R.id.deletebutton);
//        }
//
//    }

//    private void setEditbutton(View v) {
//        Editbutton.setOnClickListener(new OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }

//    private void setDeletebutton(View v) {
//        Deletebutton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String token = LoginManagement.getInstance().getToken(getContext());
//                androidx.appcompat.app.AlertDialog.Builder alt_bld = new androidx.appcompat.app.AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Dialog_Alert);
//                alt_bld.setMessage("삭제 하시겠습니까?")
//                        .setCancelable(false)
//                        .setPositiveButton("네",
//                                (dialog, id) -> {
//                                    apiProvider = new ApiProvider(getContext());
//                                    apiProvider.deletePost(token)
//
//                                }).setNegativeButton("아니오",
//                        (dialog, id) -> {
//                            dialog.cancel();
//                        });
//                AlertDialog alert = alt_bld.create();
//                alert.setTitle("로그아웃");
//                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 62, 79, 92)));
//                alert.show();
//            }
//        });
//
//    }
}
