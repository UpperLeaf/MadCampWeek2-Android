package com.wonsang.madcampweek2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wonsang.madcampweek2.AccountData;
import com.wonsang.madcampweek2.AccountDatabase;
import com.wonsang.madcampweek2.AddContactActivity;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.adapter.RecyclerAdapter;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.api.JsonHeaderRequest;
import com.wonsang.madcampweek2.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment implements ApiCallable {
    private ApiProvider apiProvider;
    private RecyclerAdapter adapter;
    private ArrayList<Contact> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static int CONTACT_ADD_REQUEST = 300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
        AccountDatabase ab = AccountDatabase.getAppDatabase(getActivity());
        AccountData data = ab.AccountDataDao().findAccountDataLimitOne();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);


        apiProvider = new ApiProvider(getContext());
        apiProvider.getAllContacts(data.getToken(), this);

        adapter = new RecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        FloatingActionButton fab = rootView.findViewById(R.id.fab_main);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddContactActivity.class);
            getActivity().startActivityForResult(intent, CONTACT_ADD_REQUEST);
        });

        return rootView;
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
        JSONArray jsonArray = response.getResponse();
        List<Contact> contacts = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt("id");
                String name = object.getString("name");
                String phoneNumber = object.getString("phone_number");
                contacts.add(new Contact(name, phoneNumber, id));
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        adapter.setList(contacts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getError(VolleyError error) {
        System.out.println(error);
    }

    public void notifyAddContact(Contact contact) {
        int pos = this.adapter.getList().size();
        this.adapter.getList().add(contact);
        this.adapter.notifyItemInserted(pos);
    }

    public void notifyEditContact(Contact contact, int pos) {
        this.adapter.getList().set(pos, contact);
        this.adapter.notifyItemChanged(pos);
    }

    public void notifyDeleteContact(Contact contact, int pos) {
        this.adapter.getList().remove(pos);
        this.adapter.notifyItemRemoved(pos);
    }

}