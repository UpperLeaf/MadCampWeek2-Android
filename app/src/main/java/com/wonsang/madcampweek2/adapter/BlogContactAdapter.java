package com.wonsang.madcampweek2.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.ShowBlogActivity;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Contact;

import java.util.List;

public class BlogContactAdapter extends RecyclerView.Adapter<BlogContactAdapter.Holder> {

    private Context context;
    private List<Contact> list;
    private ApiProvider apiProvider;
    private int deletepos;
    public BlogContactAdapter(Context context, List<Contact> list) {
        this.context = context;
        this.list = list;
    }

    public List<Contact> getList() {
        return list;
    }
    public void setList(List<Contact> list) {
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
        holder.wordText.setText(list.get(itemposition).getName());
        holder.meaningText.setText(list.get(itemposition).getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
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
                        Intent intent = new Intent(v.getContext(), ShowBlogActivity.class);
                        //intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        Contact item = list.get(pos);
                        intent.putExtra("id", item.getId());
                        intent.putExtra("position", pos);
                        intent.putExtra("name", item.getName());
                        intent.putExtra("email", item.getEmail());
                        Context context = v.getContext();
                        ((Activity)(context)).startActivityForResult(intent, 101);
                    }
                }
            });
        }
    }
}
