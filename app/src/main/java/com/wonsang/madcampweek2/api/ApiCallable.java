package com.wonsang.madcampweek2.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public interface ApiCallable<T>{
    void getResponse(ApiProvider.RequestType type, T response);
    void getError(VolleyError error);
}
