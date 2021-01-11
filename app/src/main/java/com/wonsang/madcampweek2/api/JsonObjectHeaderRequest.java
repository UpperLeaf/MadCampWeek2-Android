package com.wonsang.madcampweek2.api;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonObjectHeaderRequest extends JsonObjectRequest {

    private Map<String, String> headers;

    public JsonObjectHeaderRequest(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.headers = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }
}
