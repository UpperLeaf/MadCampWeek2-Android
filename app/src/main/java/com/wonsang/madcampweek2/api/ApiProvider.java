package com.wonsang.madcampweek2.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.wonsang.madcampweek2.model.Contact;

import java.util.List;

public class ApiProvider {

    private String url = "http://192.249.18.221:8080/";
    private RequestQueue requestQueue;

    public ApiProvider(Context context) {
        this.requestQueue = VolleyManager.getInstance(context).getRequestQueue();
    }

    public void isValidAccessToken(String token, ApiCallable apiCallable){
        String requestUrl = url + "token";

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

    public List<Contact> getAllContacts(String accessToken){
        return null;
    }

    public enum RequestType{
        TOKEN_VALIDATION,
        GET_ALL_CONTACTS,
    }
}
