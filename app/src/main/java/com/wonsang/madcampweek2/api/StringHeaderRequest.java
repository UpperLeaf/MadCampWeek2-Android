package com.wonsang.madcampweek2.api;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StringHeaderRequest extends StringRequest {

    private Map<String, String> headers;

    public StringHeaderRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.headers = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders()  {
        return headers;
    }
}
