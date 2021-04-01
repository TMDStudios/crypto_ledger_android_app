package com.tmdstudios.cryptoledger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    private Button getDataBtn;
    private Button clearDataBtn;
    private TextView textView;
    private TextView tempText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = findViewById(R.id.scrollingText);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSelected(true);

        tempText = findViewById(R.id.tempText);
        parseJson();

        getDataBtn = findViewById(R.id.get_data);
        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseJson();
            }
        });

        clearDataBtn = findViewById(R.id.clear_data);
        clearDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempText.setText("");
            }
        });
    }

    private void parseJson(){
        String url = "https://crypto-ledger.herokuapp.com/api/get-prices";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject coin = response.getJSONObject(i);
                                String name = coin.getString("name");
                                tempText.append(name);
                            }
                        } catch (JSONException e) {e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue mQueue = Volley.newRequestQueue(this);
        mQueue.add(request);
    }

}