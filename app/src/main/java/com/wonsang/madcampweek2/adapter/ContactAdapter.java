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

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.AccountData;
import com.wonsang.madcampweek2.AccountDatabase;
import com.wonsang.madcampweek2.ContactDialog;
import com.wonsang.madcampweek2.EditContactActivity;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.api.JsonHeaderRequest;
import com.wonsang.madcampweek2.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Holder> implements ApiCallable {

    private Context context;
    private List<Contact> list;
    private ApiProvider apiProvider;
    private int deletepos;
    public ContactAdapter(Context context, List<Contact> list) {
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
        Holder holder = new Holder(view, this);
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
        holder.meaningText.setText(list.get(itemposition).getEmail());
        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
        this.getList().remove(deletepos);
        notifyItemRemoved(deletepos);
    }

    @Override
    public void getError(VolleyError error) {
        System.out.println(error.toString());
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
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
                        Intent intent = new Intent(v.getContext(), EditContactActivity.class);
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

            view.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    Context context = v.getContext();
                    Contact item = list.get(pos);
                    int itemid = item.getId();
                    String name = item.getName();
                    String email = item.getEmail();
                    if(pos!= RecyclerView.NO_POSITION) {
                        apiProvider = new ApiProvider(context);
                        AccountDatabase ab = AccountDatabase.getAppDatabase(context);
                        AccountData data = ab.AccountDataDao().findAccountDataLimitOne();
                        ContactDialog cd = new ContactDialog(context, item, itemid, pos,apiCallable,R.style.Theme_AppCompat_Dialog_Alert);
                        cd.setMessage(name + " / " + email +"삭제 하시겠습니까?")
                                .setCancelable(false)
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        apiProvider.DeleteContact(data.getToken(), itemid, name, email, apiCallable);
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("아니오",
                                (dialog, id) -> {
                                    dialog.cancel();
                                });
                        AlertDialog alert = cd.create();
                        alert.setTitle("삭제");
                        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 62, 79, 92)));
                        alert.show();
                        alert.setOnDismissListener(dialog -> {
                            deletepos = cd.getpos();
                        });
                    }
                    return false;
                }
            });
        }
    }
}
