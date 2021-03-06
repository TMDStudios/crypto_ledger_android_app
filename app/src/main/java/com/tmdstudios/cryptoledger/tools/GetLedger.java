package com.tmdstudios.cryptoledger.tools;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class GetLedger {
    public JSONArray ledgerData;
    public Boolean ledgerReady = false;

    public GetLedger(String API_KEY, Context context) {
        String url = "https://crypto-ledger.herokuapp.com/api/get-user-ledger/"+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length()>0){
                            ledgerData=response;
                            ledgerReady=true;
//                            Toast.makeText(context, "GOT: "+ledgerData.toString(), Toast.LENGTH_SHORT).show();
                        }
//                        Toast.makeText(context, "ledger has been checked", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue mQueue = Volley.newRequestQueue(context);
        mQueue.add(request);
    }
}
