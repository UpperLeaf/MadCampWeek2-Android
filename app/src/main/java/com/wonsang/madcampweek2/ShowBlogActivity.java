package com.wonsang.madcampweek2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wonsang.madcampweek2.adapter.PostView;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Blog;
import com.wonsang.madcampweek2.model.Post;

import org.json.JSONObject;

import java.util.List;

public class ShowBlogActivity extends AppCompatActivity implements ApiCallable<JSONObject> {
    private TextView title;
    private TextView content;
    private ImageView banner_image;
    private ImageView profile_image;
    private TextView description;
    private ApiProvider apiProvider;
    private PostView postView;
    private Blog blog;
    private List<Post> postlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_3);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        apiProvider = new ApiProvider(this);
        Intent intent = getIntent();
        String email = intent.getExtras().getString("email");
        String token = LoginManagement.getInstance().getToken(this);
        apiProvider.getOtherBlog(token,  email, this);
    }

//    @Override
//    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
//        if (type == ApiProvider.RequestType.GET_OTHER_BLOG){
//            try {
//                String blogtitle = response.getResponse().getString("title");
//                String blogcontent = response.getResponse().getString("content");
//                String blogdescription = response.getResponse().getString("description");
//                postlist = blog.getPosts();
//                title.setText(blogtitle);
//                content.setText(blogcontent);
//                description.setText(blogdescription);
//                postView = new PostView(getApplicationContext(),postlist.get(postlist.size()), 0);
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JSONObject response) {

    }

    @Override
    public void getError(VolleyError error) {

    }
}