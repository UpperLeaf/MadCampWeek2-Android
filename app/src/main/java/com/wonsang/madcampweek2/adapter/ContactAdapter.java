package com.wonsang.madcampweek2.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.EditContactActivity;
import com.wonsang.madcampweek2.LoginManagement;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Contact;


import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Holder> {

    private List<Contact> list;
    private ApiProvider apiProvider;

    public ContactAdapter(List<Contact> list) {
        this.list = list;
    }

    public List<Contact> getList() {
        return list;
    }
    public void setList(List<Contact> list) {
        this.list = list;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag1_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.contactId = list.get(position).getId();
        holder.pos = position;
        holder.wordText.setText(list.get(position).getName());
        holder.meaningText.setText(list.get(position).getEmail());

        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditContactActivity.class);
            Contact item = list.get(position);
            intent.putExtra("id", item.getId());
            intent.putExtra("name", item.getName());
            intent.putExtra("email", item.getEmail());
            Context context = v.getContext();
            ((Activity)(context)).startActivityForResult(intent, 101);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Holder extends RecyclerView.ViewHolder implements ApiCallable<String> {
        private int contactId;
        private int pos;
        private View view;
        public TextView wordText;
        public TextView meaningText;

        public Holder(View view){
            super(view);
            this.view = view;
            wordText =  view.findViewById(R.id.wordText);
            meaningText =  view.findViewById(R.id.meaningText);


            view.setOnLongClickListener(v -> {
                Context context = v.getContext();
                Contact item = list.get(pos);
                apiProvider = new ApiProvider(context);
                String token = LoginManagement.getInstance().getToken(context);

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert)
                                .setMessage(item.getName() + " / " + item.getEmail() +"삭제 하시겠습니까?")
                                .setPositiveButton("네", (dialog, which) -> {
                                    apiProvider.deleteContact(token, contactId, this);
                                    dialog.dismiss();
                                })
                                .setNegativeButton("아니오",
                                        (dialog, id) -> dialog.cancel());

                AlertDialog alert = builder.create();
                alert.setTitle("삭제");
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 62, 79, 92)));
                alert.show();

                return true;
            });
        }

        @Override
        public void getResponse(ApiProvider.RequestType type, String response) {
            if(type == ApiProvider.RequestType.DELETE_CONTACTS){
                getList().remove(pos);
                notifyItemRemoved(pos);
            }
        }

        @Override
        public void getError(VolleyError error) {
            System.out.println(error);
        }
    }
}
