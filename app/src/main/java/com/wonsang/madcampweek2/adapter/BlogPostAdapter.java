package com.wonsang.madcampweek2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.Updatable;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.fragment.Fragment3;
import com.wonsang.madcampweek2.fragment.GalleryFragment;
import com.wonsang.madcampweek2.model.Post;

import java.util.ArrayList;
import java.util.List;

public class BlogPostAdapter extends RecyclerView.Adapter<BlogPostAdapter.Holder>{

    private Context context;
    private List<Post> list;
    private Updatable updatable;

    public BlogPostAdapter(Context context, Updatable updatable) {
        this.context = context;
        this.list = new ArrayList<>();
        this.updatable = updatable;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag1_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Post post = list.get(position);
        holder.wordText.setText(String.valueOf(position+1));
        holder.meaningText.setText(post.getTitle());
        holder.view.setOnClickListener((v) -> {
            updatable.update(post);
        });
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
        private View view;
        public Holder(View view){
            super(view);
            wordText =  view.findViewById(R.id.wordText);
            meaningText = view.findViewById(R.id.meaningText);
            this.view = view;
        }
    }
}
