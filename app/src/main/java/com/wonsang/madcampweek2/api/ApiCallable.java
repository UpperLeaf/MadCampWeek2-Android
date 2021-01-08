package com.wonsang.madcampweek2.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public interface ApiCallable {
    void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response);
    void getError(VolleyError error);
}
