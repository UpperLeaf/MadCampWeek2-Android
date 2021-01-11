package com.wonsang.madcampweek2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.model.Post;

public class PostView extends LinearLayout {

    LinearLayout bg;
    TextView title;
    TextView content;
    Button Editbutton;
    Button Deletebutton;


    public PostView(Context context, Post post, int flag) {
        super(context);
        initView(post, flag);
    }

    private void changeView(Context context, Post post, int flag) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v =li.inflate(R.layout.postview_layout, this, false);
        this.removeAllViews();
        addView(v);
        bg = (LinearLayout) findViewById(R.id.bg);
        title = (TextView) findViewById(R.id.post_title);
        content = (TextView) findViewById(R.id.post_content);

        bg.setBackgroundResource(R.drawable.border);
        title.setText(post.getTitle());
        content.setText(post.getContent());

    }

    private void initView(Post post, int flag) {

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v =li.inflate(R.layout.postview_layout, this, false);
        addView(v);

        bg = (LinearLayout) findViewById(R.id.bg);
        title = (TextView) findViewById(R.id.post_title);
        content = (TextView) findViewById(R.id.post_content);

        bg.setBackgroundResource(R.drawable.border);
        title.setText(post.getTitle());
        content.setText(post.getContent());

        if (flag==1) {
            Editbutton = v.findViewById(R.id.editbutton);
            Deletebutton = v.findViewById(R.id.deletebutton);
        }

    }

//    private void setEditbutton(View v) {
//        Editbutton.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }

//    private void setDeletebutton() {
//
//    }
}
