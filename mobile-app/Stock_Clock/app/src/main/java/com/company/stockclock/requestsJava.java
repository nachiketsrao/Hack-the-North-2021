package com.company.stockclock;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import org.json.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;


import java.io.StringReader;
import java.util.ArrayList;

public class requestsJava {
    public interface VolleyCallback{
        void onSuccess(JSONObject result) throws JSONException;
    }

    public JSONObject makeRequest(final VolleyCallback callback ,Context context, String path, ArrayList<String> keys, ArrayList<String> values) {
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        String url = "https://canvas-antler-326322.uc.r.appspot.com" + path;

        String final_path = "?";

        for (int i = 0; i < keys.size(); i++) {

            final_path = final_path + keys.get(i) + "=" + values.get(i) + "&";

        }
        url = url + final_path;

        //JsonObject reJson = new JsonObject();
        //JSONObject jsonArray;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(context.getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonRequest);
        return null;
    }


}
