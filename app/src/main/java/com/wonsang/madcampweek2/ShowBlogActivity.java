package com.wonsang.madcampweek2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wonsang.madcampweek2.adapter.BlogContactAdapter;
import com.wonsang.madcampweek2.adapter.BlogPostAdapter;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Blog;
import com.wonsang.madcampweek2.model.Contact;
import com.wonsang.madcampweek2.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class ShowBlogActivity extends AppCompatActivity implements ApiCallable<JSONObject>, Updatable{

    private RecyclerView contactRecyclerView;
    private RecyclerView postRecyclerView;

    private BlogContactAdapter contactAdapter;
    private BlogPostAdapter postAdapter;

    private TextView blogTitle;
    private TextView description;
    private ImageView bannerImageView;
    private ImageView profileImageView;

    private FloatingActionButton fab;
    private TextView postTitle;
    private TextView postContent;

    private ApiProvider apiProvider;
    private Base64.Decoder decoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_3);
        blogTitle = findViewById(R.id.title);
        description = findViewById(R.id.description);

        postTitle = findViewById(R.id.post_title);
        postContent = findViewById(R.id.post_content);

        bannerImageView = findViewById(R.id.bannerImage);
        profileImageView = findViewById(R.id.profileImage);

        postRecyclerView = findViewById(R.id.recyclerview2);
        contactRecyclerView = findViewById(R.id.recyclerview3);


        contactAdapter = new BlogContactAdapter(this);
        postAdapter = new BlogPostAdapter(this, this);

        postRecyclerView.setAdapter(postAdapter);
        contactRecyclerView.setAdapter(contactAdapter);

        postRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


        fab = findViewById(R.id.fab_main3);
        fab.setVisibility(View.INVISIBLE);

        decoder = Base64.getDecoder();
        getOtherBlog();
    }

    private void getOtherBlog() {
        apiProvider = new ApiProvider(this);
        Intent intent = getIntent();
        String email = intent.getExtras().getString("email");
        String token = LoginManagement.getInstance().getToken(this);
        apiProvider.getOtherBlog(token,  email, this);
    }


    @Override
    public void getResponse(ApiProvider.RequestType type, JSONObject response) {
        Blog blog = new Blog();
        try {
            String blogTitle = response.getString("title");
            String description = response.getString("description");
            String bannerImage = response.getString("bannerImage");
            String profileImageUrl = response.getString("userImageUrl");
            String profileImage = null;
            boolean hasProfileImage = response.getBoolean("hasProfileImage");
            if(hasProfileImage)
                profileImage = response.getString("profileImage");

            List<Post> postList = new ArrayList<>();
            JSONArray jsonArray = response.getJSONArray("post");
            for (int i = 0; i < jsonArray.length(); i++) {
                int id = jsonArray.getJSONObject(i).getInt("id");
                String postTitle = jsonArray.getJSONObject(i).getString("title");
                String postContent = jsonArray.getJSONObject(i).getString("content");
                Post post = new Post(postTitle, postContent, id);
                postList.add(post);
            }

            List<Contact> contacts = new ArrayList<>();
            JSONArray contactArray = response.getJSONArray("contacts");
            for (int i = 0; i < contactArray.length(); i++) {
                String name = contactArray.getJSONObject(i).getString("name");
                String email = contactArray.getJSONObject(i).getString("email");
                Contact contact = new Contact(name, email);
                contacts.add(contact);
            }


            blog.setTitle(blogTitle);
            blog.setDescription(description);
            blog.setBannerImage(bannerImage);
            blog.setProfileImageUrl(profileImageUrl);
            blog.setPosts(postList);
            blog.hasProfileImage = hasProfileImage;
            if(hasProfileImage)
                blog.profileImage = profileImage;

            int postSize = postList.size();
            if(postSize >= 1){
                Post post = postList.get(postSize - 1);
                postTitle.setText(post.getTitle());
                postContent.setText(post.getContent());
            }
            setBlogData(blog);

            contactAdapter.setList(contacts);
            contactAdapter.notifyDataSetChanged();
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Post post) {
        this.postTitle.setText(post.getTitle());
        this.postContent.setText(post.getContent());
    }

    private void setBlogData(Blog blog) {
        blogTitle.setText(blog.getTitle());
        description.setText(blog.getDescription());

        if(!blog.getBannerImage().equals("null")){
            byte[] bannerImage = decoder.decode(blog.getBannerImage());
            Glide.with(this).load(bannerImage).into(bannerImageView);
        }
        else
            Glide.with(this).load(R.drawable.banner_image3).into(bannerImageView);

        if(blog.hasProfileImage){
            byte[] bytes = decoder.decode(blog.profileImage);
            Glide.with(this).load(bytes).into(profileImageView);
        }else
            Glide.with(this).load(blog.getProfileImageUrl()).into(profileImageView);

        postAdapter.setList(blog.posts);
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void getError(VolleyError error) {

    }
}