package com.wonsang.madcampweek2.api;

import android.app.VoiceInteractor;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.wonsang.madcampweek2.AccountDatabase;
import com.wonsang.madcampweek2.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ApiProvider {

    private String url = "http://192.249.18.221:8080/";
//    private String url = "http://172.31.0.1:8080/";
    private RequestQueue requestQueue;

    public ApiProvider(Context context) {
        this.requestQueue = VolleyManager.getInstance(context).getRequestQueue();
    }

    public void isValidAccessToken(String token, ApiCallable<String> apiCallable) {
        String requestUrl = url + "token/";
        StringHeaderRequest request = new StringHeaderRequest(Request.Method.GET, requestUrl,
                response -> apiCallable.getResponse(RequestType.TOKEN_VALIDATION, response), apiCallable::getError);
        request.getHeaders().put("Authorization", token);
        requestQueue.add(request);
    }

    public void getAllContacts(String token, ApiCallable<JSONArray> apiCallable) {
        String requestUrl = url + "contact/";
        JsonArrayHeaderRequest request = new JsonArrayHeaderRequest(Request.Method.GET, requestUrl
                , null
                , response -> apiCallable.getResponse(RequestType.GET_ALL_CONTACTS, response)
                , apiCallable::getError);
        request.getHeaders().put("Authorization", token);
        requestQueue.add(request);
    }

    public void addContact(String token, String name, String email, ApiCallable<JSONObject> apiCallable) {
        String requestUrl = url + "contact/";
        try {
            JSONObject object = new JSONObject();

            object.put("name", name);
            object.put("email", email);

            JsonObjectHeaderRequest request
                    = new JsonObjectHeaderRequest(Request.Method.POST
                    ,requestUrl
                    , object
                    , response -> apiCallable.getResponse(RequestType.ADD_CONTACTS, response)
                    , apiCallable::getError);
            request.getHeaders().put("Authorization", token);
            requestQueue.add(request);

        } catch (JSONException ex) {
            System.out.println(ex.toString());
            throw new RuntimeException();
        }
    }

    public void editContact(String token, Contact contact, ApiCallable<JSONObject> apiCallable) {
        String requestUrl = url + "contact/" + contact.getId();
        JSONObject object = new JSONObject();
        try {
            object.put("name",  contact.getName());
            object.put("email", contact.getEmail());
            JsonObjectHeaderRequest request = new JsonObjectHeaderRequest(Request.Method.PUT
                    , requestUrl
                    , object
                    , response -> apiCallable.getResponse(RequestType.EDIT_CONTACTS, response)
                    , apiCallable::getError);
            request.getHeaders().put("Authorization", token);
            requestQueue.add(request);

        } catch (JSONException ex) {
            System.out.println(ex.toString());
            throw new RuntimeException();
        }
    }

    public void deleteContact(String token, int id, ApiCallable<String> apiCallable) {
        String requestUrl = url + "contact/" + id;
        StringHeaderRequest request = new StringHeaderRequest(Request.Method.DELETE
                , requestUrl
                , response -> apiCallable.getResponse(RequestType.DELETE_CONTACTS, response)
                , apiCallable::getError);
        request.getHeaders().put("Authorization", token);
        requestQueue.add(request);
    }

    public void getAllImages(String token, ApiCallable<JSONArray> apiCallable) {
        String requestUrl = url + "picture/";

        JsonArrayHeaderRequest request = new JsonArrayHeaderRequest(Request.Method.GET, requestUrl, null,
                response -> apiCallable.getResponse(RequestType.GET_ALL_IMAGES, response), apiCallable::getError);

        request.getHeaders().put("Authorization", token);
        requestQueue.add(request);
    }

    public void addImage(String token, String title, String image, ApiCallable<JSONObject> apiCallable) {
        String requestUrl = url + "picture/";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("title", title);
            jsonObject.put("image", image);
            JsonObjectHeaderRequest request = new JsonObjectHeaderRequest(Request.Method.POST
                    ,requestUrl
                    ,jsonObject
                    ,response -> apiCallable.getResponse(RequestType.ADD_IMAGE, response)
                    , apiCallable::getError);

            request.getHeaders().put("Authorization", token);
            requestQueue.add(request);
        } catch (JSONException error) {
            error.printStackTrace();
        }
    }

    public void deleteImage(String token, int id, ApiCallable<String> apiCallable)  {
        String requestUrl = url + "picture/" + id;
        StringHeaderRequest request = new StringHeaderRequest(Request.Method.DELETE
                , requestUrl
                , response -> apiCallable.getResponse(RequestType.DELETE_IMAGE, response)
                , apiCallable::getError);
        request.getHeaders().put("Authorization", token);
        requestQueue.add(request);
    }
//
//    public void getMyBlog(String token, ApiCallable apiCallable) {
//        String requestUrl = url + "/blog";
//        JsonHeaderRequest request = new JsonHeaderRequest(Request.Method.GET
//                , requestUrl
//                , null
//                , response -> apiCallable.getResponse(RequestType.GET_MY_BLOG, response)
//                , apiCallable::getError);
//        request.getHeaders().put("Authorization", token);
//
//        requestQueue.add(request);
//    }

    public void getOtherBlog(String token, String email, ApiCallable apiCallable) {

    }

    public void addPost(String token, String email, String title, String content, ApiCallable apiCallable) {
        String requestUrl = url + "post/";
                StringHeaderRequest request = new StringHeaderRequest(Request.Method.POST
                , requestUrl, response -> apiCallable.getResponse(RequestType.ADD_POST, response), apiCallable::getError);
        request.getHeaders().put("Authorization", token);
        requestQueue.add(request);
    }
    public enum RequestType {
        TOKEN_VALIDATION,
        GET_ALL_CONTACTS,
        ADD_CONTACTS,
        EDIT_CONTACTS,
        GET_ALL_IMAGES,
        ADD_IMAGE,
        DELETE_CONTACTS,
        DELETE_IMAGE,
        GET_MY_BLOG,
        GET_OTHER_BLOG,
        ADD_POST,
        EDIT_POST,
        DELETE_POST,}
}
