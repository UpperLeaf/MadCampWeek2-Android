package com.wonsang.madcampweek2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.api.JsonHeaderRequest;
import com.wonsang.madcampweek2.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment1 extends Fragment implements ApiCallable {
    private ApiProvider apiProvider;
    private RecyclerAdapter adapter;
    private ArrayList<Contact> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
        AccountDatabase ab = AccountDatabase.getAppDatabase(getActivity());
        AccountData data = ab.AccountDataDao().findAccountDataLimitOne();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        apiProvider = new ApiProvider(getActivity());
        apiProvider.getAllContacts(data.getToken(), this);
//        list =
        adapter = new RecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddContactActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
        JSONArray responsebody;
        int cnt = 0;
        Contact Contact = new Contact();
        if (type == ApiProvider.RequestType.GET_ALL_CONTACTS) {
            responsebody = response.getResponse();
            while (responsebody.length()>cnt) {
                try {
                    JSONObject jsonObject = responsebody.getJSONObject(cnt);
                    String username = jsonObject.getString("name");
                    String userphnumber = jsonObject.getString("phone_number");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void getError(VolleyError error) {

    }
}
//public class Fragment1 extends Fragment implements View.OnClickListener {
//    private Context mContext;
//    private RecyclerView recyclerView;
////    private FragmentAdapter adapter;
////    private ArrayList<Contacts> list = new ArrayList<>();
////    Button loadBtn;
//    private FloatingActionButton fab_main, fab_sub1, fab_sub2;
//    private Animation fab_open, fab_close;
//    private boolean isFabOpen = false;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Context mContext = getActivity().getApplicationContext();
//
//        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
//        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
//        fab_main = (FloatingActionButton) findViewById(R.id.fab_main);
//        fab_sub1 = (FloatingActionButton) findViewById(R.id.fab_sub1);
//        fab_sub2 = (FloatingActionButton) findViewById(R.id.fab_sub2);
//        fab_main.setOnClickListener(this);
//        fab_sub1.setOnClickListener(this);
//        fab_sub2.setOnClickListener(this);
//
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
//
////        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler1);
////        recyclerView.setHasFixedSize(true);
//////        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//////        list = Contacts.createContactsList(getActivity());
////        list = Contacts.createContactsList(getActivity());
////        adapter = new FragmentAdapter(getActivity(), list);
////        recyclerView.setAdapter(adapter);
////        Log.e("Frag", "MainFragment");
////        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//////        loadBtn = (Button) rootView.findViewById(R.id.button1);
//////        loadBtn.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v) {
//////                // TODO Auto-generated method stub
//////                list = Contacts.createContactsList(getActivity());
//////                adapter = new FragmentAdapter(getActivity(), list);
//////                recyclerView.setAdapter(adapter);
//////                loadBtn.setVisibility(rootView.GONE);
//////            }
//////        });
//        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionButton2);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Intent intent = new Intent(getActivity().getApplicationContext(), AddContactActivity.class);
//               startActivity(intent);
//            }
//        });
//
//        private void toggleFab() {
//
//            if (isFabOpen) {
//
//                fab_main.setImageResource(R.drawable.ic_add);
//
//                fab_sub1.startAnimation(fab_close);
//
//                fab_sub2.startAnimation(fab_close);
//
//                fab_sub1.setClickable(false);
//
//                fab_sub2.setClickable(false);
//
//                isFabOpen = false;
//
//            } else {
//
//                fab_main.setImageResource(R.drawable.ic_close);
//
//                fab_sub1.startAnimation(fab_open);
//
//                fab_sub2.startAnimation(fab_open);
//
//                fab_sub1.setClickable(true);
//
//                fab_sub2.setClickable(true);
//
//                isFabOpen = true;
//
//            }
//
//        }
//        return rootView;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//}