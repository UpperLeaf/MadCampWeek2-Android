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
import com.wonsang.madcampweek2.LoginManagement;
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

public class Fragment1 extends Fragment implements ApiCallable, View.OnClickListener {
    private ApiProvider apiProvider;
    private RecyclerAdapter adapter;
    private ArrayList<Contact> list = new ArrayList<>();
    private FloatingActionButton fab_main, fab_sub1, fab_sub2;
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static int CONTACT_ADD_REQUEST = 300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
        String token = LoginManagement.getInstance().getToken(getContext());
        Context mContext = getActivity().getApplicationContext();
        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);

        fab_main = (FloatingActionButton) rootView.findViewById(R.id.fab_main);
        fab_sub1 = (FloatingActionButton) rootView.findViewById(R.id.fab_sub1);
        fab_sub2 = (FloatingActionButton) rootView.findViewById(R.id.fab_sub2);
        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
        apiProvider = new ApiProvider(getContext());
        apiProvider.getAllContacts(token, this);

        adapter = new RecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        FloatingActionButton fab = rootView.findViewById(R.id.fab_sub2);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddContactActivity.class);
            getActivity().startActivityForResult(intent, CONTACT_ADD_REQUEST);
        });

//        FloatingActionButton fab2 = rootView.findViewById(R.id.fab_sub1);
//        fab2.setOnClickListener(view -> {
//            Intent intent = new Intent(getActivity().getApplicationContext(), EditContactActivity.class);
//            getActivity().startActivityForResult(intent, 101);
//        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fab_main:
                toggleFab();
                break;

            case R.id.fab_sub1:
                toggleFab();
                Toast.makeText(getActivity(), "Camera Open-!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sub2:
                toggleFab();
                Toast.makeText(getActivity(), "Map Open-!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    private void toggleFab() {
        Log.d("hi", "hi");

        if (isFabOpen) {
            fab_main.setImageResource(R.drawable.ic_add);
            fab_sub1.startAnimation(fab_close);
            fab_sub2.startAnimation(fab_close);
            fab_sub1.setClickable(false);
            fab_sub2.setClickable(false);
            isFabOpen = false;

        } else {
            fab_main.setImageResource(R.drawable.ic_close);
            fab_sub1.startAnimation(fab_open);
            fab_sub2.startAnimation(fab_open);
            fab_sub1.setClickable(true);
            fab_sub2.setClickable(true);
            isFabOpen = true;

        }
    }
}