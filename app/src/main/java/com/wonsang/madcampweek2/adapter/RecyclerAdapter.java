package com.wonsang.madcampweek2.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wonsang.madcampweek2.EditContactActivity;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.model.Contact;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private Context context;
    private List<Contact> list;

    public RecyclerAdapter(Context context, List<Contact> list) {
        this.context = context;
        this.list = list;
    }

    public List<Contact> getList() {
        return list;
    }
    public void setList(List<Contact> list) {
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag1_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    /*
     * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
     *
     * */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.wordText.setText(list.get(itemposition).getName());
        holder.meaningText.setText(list.get(itemposition).getPhoneNumber());
        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView wordText;
        public TextView meaningText;

        public Holder(View view){
            super(view);
            wordText = (TextView) view.findViewById(R.id.wordText);
            meaningText = (TextView) view.findViewById(R.id.meaningText);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(v.getContext(), EditContactActivity.class);
                        //intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        Contact item = list.get(pos);
                        intent.putExtra("id", item.getId());
                        intent.putExtra("position", pos);
                        intent.putExtra("name", item.getName());
                        intent.putExtra("phNumbers", item.getPhoneNumber());
                        Context context = v.getContext();
                        ((Activity)(context)).startActivityForResult(intent, 101);
                    }
                }

            });
        }

    }

}