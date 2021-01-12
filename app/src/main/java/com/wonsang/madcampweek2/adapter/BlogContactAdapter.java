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


import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.LoginManagement;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.ShowBlogActivity;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.fragment.Fragment3;
import com.wonsang.madcampweek2.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BlogContactAdapter extends RecyclerView.Adapter<BlogContactAdapter.Holder> implements ApiCallable<JSONArray> {

    private ApiProvider apiProvider;
    private Context context;
    private List<Contact> contacts;

    public BlogContactAdapter(Context context) {
        this.context = context;
        this.contacts = new ArrayList<>();
        this.apiProvider = new ApiProvider(context);
        apiProvider.getAllContacts(LoginManagement.getInstance().getToken(context), this);
    }

    public List<Contact> getList() {
        return contacts;
    }
    public void setList(List<Contact> list) {
        this.contacts = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag1_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.wordText.setText(contacts.get(position).getName());
        holder.meaningText.setText(contacts.get(position).getEmail());
        holder.view.setOnClickListener((v) -> {
            Intent intent = new Intent(context, ShowBlogActivity.class);
            intent.putExtra("email", contacts.get(position).getEmail());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JSONArray response) {
        List<Contact> contacts = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject object = response.getJSONObject(i);
                int id = object.getInt("id");
                String name = object.getString("name");
                String email = object.getString("email");
                contacts.add(new Contact(name, email, id));
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @Override
    public void getError(VolleyError error) {
        System.out.println("Get All Contacts : " + error);
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView wordText;
        public TextView meaningText;
        private View view;
        public Holder(View view){
            super(view);
            this.view = view;
            wordText =  view.findViewById(R.id.wordText);
            meaningText =  view.findViewById(R.id.meaningText);
        }
    }
}
