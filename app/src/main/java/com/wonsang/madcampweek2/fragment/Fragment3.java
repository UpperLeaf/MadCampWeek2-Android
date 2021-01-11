package com.wonsang.madcampweek2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.AccountDatabase;
import com.wonsang.madcampweek2.LoginManagement;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.adapter.BlogContactAdapter;
import com.wonsang.madcampweek2.adapter.BlogPostAdapter;
import com.wonsang.madcampweek2.adapter.PostView;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Contact;
import com.wonsang.madcampweek2.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ApiProvider apiProvider;
    private BlogContactAdapter contactadapter;
    private BlogPostAdapter postlistadapter;
    private PostView postView;
    private ArrayList<Contact> list = new ArrayList<>();
    private ArrayList<Post> postlist = new ArrayList<>();

    public Fragment3() {
        // Required empty public constructor
    }

    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}

//      @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_3, container, false);
//        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview3);
//        TextView introduction = rootView.findViewById(R.id.introduction);
//        recyclerView.setHasFixedSize(true);
//        String token = LoginManagement.getInstance().getToken(getContext());
//        AccountDatabase ab = AccountDatabase.getAppDatabase(getContext());
//
//        String user_email = ab.AccountDataDao().findAccountDataLimitOne().getEmail();
//        apiProvider = new ApiProvider(getContext());
//        //apiProvider.getAllContacts(token, this);
//        contactadapter = new BlogContactAdapter(getActivity(), list);
//        recyclerView.setAdapter(contactadapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        RecyclerView recyclerView2 = (RecyclerView) rootView.findViewById(R.id.recyclerview2);
//        recyclerView2.setHasFixedSize(true);
////        apiProvider = new ApiProvider(getContext());
////        apiProvider.getMyBlog(token, this);
//        postView= new PostView(getActivity(), postlist.get(postlist.size()), 1);
//        postlistadapter = new BlogPostAdapter(getActivity(), postlist);
//        recyclerView2.setAdapter(postlistadapter);
//        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//
//        return rootView;
//    }

//    @Override
//    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
//        JSONArray jsonArray = response.getResponse();
//        List<Post> posts = new ArrayList<>();
//        if (type == ApiProvider.RequestType.GET_MYBLOG) {
//            try {
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject object = jsonArray.getJSONObject(i);
////                int id = object.getInt("id");
//                    JSONArray postlistJSON = object.getJSONArray("posts");
//                    String email = object.getString("email");
//                    String description = object.getString("description");
//
//                    if (postlistJSON != null) {
//                        for (int j = 0; i < postlistJSON.length(); j++) {
//                            JSONObject postJSON = postlistJSON.getJSONObject(j);
//                            String title = postJSON.getString("title");
//                            String content = postJSON.getString("content");
//
//                            posts.add(new Post(title, content));
//                        }
//                    }
//
//                }
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }
//            postlistadapter.setList(posts);
//            postlistadapter.notifyDataSetChanged();
//        }
//        else if (type == ApiProvider.RequestType.GET_ALL_CONTACTS) {
//            try {
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject object = jsonArray.getJSONObject(i);
//                    int id = object.getInt("id");
//                    String name = object.getString("name");
//                    String email = object.getString("email");
//                    list.add(new Contact(name, email, id));
//                }
//            }catch (JSONException ex){
//                ex.printStackTrace();
//            }
//            contactadapter.setList(list);
//            contactadapter.notifyDataSetChanged();
//        }
//    }

//
//    @Override
//    public void getResponse(ApiProvider.RequestType type, JSONObject response) {
//
//    }
//
//    @Override
//    public void getError(VolleyError error) {
//
//    }
//}