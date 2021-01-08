package com.wonsang.madcampweek2.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyManager {

    private static VolleyManager volleyManager;
    private RequestQueue requestQueue;

    private VolleyManager(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    };

    public static VolleyManager getInstance(Context context){
        if(volleyManager == null)
            volleyManager = new VolleyManager(context);
        return volleyManager;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
