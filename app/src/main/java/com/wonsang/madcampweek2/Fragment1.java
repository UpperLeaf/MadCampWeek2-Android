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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), AddContactActivity.class);
//                startActivity(intent);
            }
        });
        return rootView;
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