package com.wonsang.madcampweek2.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonsang.madcampweek2.AccountData;
import com.wonsang.madcampweek2.AccountDatabase;
import com.wonsang.madcampweek2.ContactDialog;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.ShowBlogActivity;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Contact;
import com.wonsang.madcampweek2.model.Post;

import java.util.List;

public class BlogPostAdapter extends RecyclerView.Adapter<BlogPostAdapter.Holder>{

    private Context context;
    private List<Post> list;
    private ApiProvider apiProvider;
    private int deletepos;
    public BlogPostAdapter(Context context, List<Post> list) {
        this.context = context;
        this.list = list;
    }




    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag1_item, parent, false);
//        Holder holder = new Holder(view, this);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int itemposition = position;
        holder.wordText.setText(itemposition);
        holder.meaningText.setText(list.get(itemposition).getContent());
//        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<Post> getList() {
        return list;
    }
    public void setList(List<Post> list) {
        this.list = list;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView wordText;
        public TextView meaningText;

        public Holder(View view, ApiCallable apiCallable){
            super(view);
            wordText = (TextView) view.findViewById(R.id.wordText);
            meaningText = (TextView) view.findViewById(R.id.meaningText);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Post item = list.get(pos);
                        Context context = v.getContext();
                        apiProvider = new ApiProvider(context);
                        // 여기서는 postview 바꾸는 것
                    }
                }
            });
        }
    }
}
