package com.wonsang.madcampweek2.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.wonsang.madcampweek2.model.Contact;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ApiProvider {

    private String url = "http://192.249.18.221:8080/";
    private RequestQueue requestQueue;

    public ApiProvider(Context context) {
        this.requestQueue = VolleyManager.getInstance(context).getRequestQueue();
    }

    public void isValidAccessToken(String token, ApiCallable apiCallable){
        String requestUrl = url + "token/";

        JsonHeaderRequest request = new JsonHeaderRequest(Request.Method.GET
                , requestUrl
                , null
                , response -> apiCallable.getResponse(RequestType.TOKEN_VALIDATION, response)
                , apiCallable::getError);

        try {
            request.getHeaders().put("Authorization", token);
        }
        catch (AuthFailureError authFailureError) {
            Log.d("Auth Failure Error", authFailureError.getMessage());
            throw new RuntimeException();
        }

        requestQueue.add(request);
    }

    public void getAllContacts(String token, ApiCallable apiCallable){
        String requestUrl = url + "contact/";

        JsonHeaderRequest request = new JsonHeaderRequest(Request.Method.GET, requestUrl, null, response -> apiCallable.getResponse(RequestType.GET_ALL_CONTACTS, response), apiCallable::getError);
        try {
            request.getHeaders().put("Authorization", token);
        }
        catch (AuthFailureError authFailureError) {
            Log.d("Auth Failure Error", authFailureError.getMessage());
            throw new RuntimeException();
        }

        requestQueue.add(request);
    }

    public void AddContact(String token, String name, String phNumbers, ApiCallable apiCallable) {
        String requestUrl = url + "contact/" ;
        try {
            JSONObject object = new JSONObject();
            object.put("name", name);
            object.put("phone_number", phNumbers);
            JsonHeaderRequest request = new JsonHeaderRequest(Request.Method.POST, requestUrl, object, response -> apiCallable.getResponse(RequestType.ADD_CONTACTS, response), apiCallable::getError);
            request.getHeaders().put("Authorization", token);
            requestQueue.add(request);

        }
        catch (JSONException | AuthFailureError ex) {
            System.out.println(ex.toString());
            throw new RuntimeException();
        }
    }

    public void EditContact(String token, int id, int position, String newname, String newphNumbers, ApiCallable apiCallable) {
        String requestUrl = url +"contact/" + id ;
        JSONObject object = new JSONObject();
        try {
            object.put("name", newname);
            object.put("phone_number", newphNumbers);
            JsonHeaderRequest request = new JsonHeaderRequest(Request.Method.PUT, requestUrl, object, response -> apiCallable.getResponse(RequestType.EDIT_CONTACTS, response), apiCallable::getError);
            request.getHeaders().put("Authorization", token);
            requestQueue.add(request);

        } catch (JSONException | AuthFailureError ex) {
            System.out.println(ex.toString());
            throw new RuntimeException();
        }

    }

    public enum RequestType{
        TOKEN_VALIDATION,
        GET_ALL_CONTACTS,
        ADD_CONTACTS,
        EDIT_CONTACTS,
    }
}
