package com.wonsang.madcampweek2.api;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class JsonHeaderRequest extends JsonRequest<JsonHeaderRequest.JsonHeaderObject> {

    private Map<String, String> headers;

    public JsonHeaderRequest(int method, String url, @Nullable JSONObject json, Response.Listener<JsonHeaderObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, (json == null) ? null : json.toString(), listener, errorListener);
        headers = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    @Override
    protected Response<JsonHeaderObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(
                            response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

            if(jsonString.startsWith("{"))
                jsonString = "[" + jsonString + "]";

            Map<String,String> headers = response.headers;
            JSONArray jsonObject = null;
            if(jsonString.equals("null") || jsonString.equals("[]"))
                jsonObject = new JSONArray();
            else
                jsonObject = new JSONArray(jsonString);

            int statusCode = response.statusCode;
            JsonHeaderObject object = new JsonHeaderObject(headers, jsonObject, statusCode);
            return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    public static class JsonHeaderObject {
        Map<String, String> headers;
        JSONArray response;
        int status;

        JsonHeaderObject(Map<String,String> headers, JSONArray jsonObject, int status){
            this.headers=headers;
            this.response = jsonObject;
            this.status = status;
        }

        public JSONArray getResponse() {
            return response;
        }
        public Map<String, String> getHeaders() {
            return headers;
        }
        public int getStatus() {
            return status;
        }
    }
}
