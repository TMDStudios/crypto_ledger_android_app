package com.tmdstudios.cryptoledger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tmdstudios.cryptoledger.tools.GetLedger;
import com.tmdstudios.cryptoledger.tools.LedgerCoinAdapter;
import com.tmdstudios.cryptoledger.tools.LedgerCoinModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Button getDataBtn;
    private Button clearDataBtn;
    private TextView textView;
    private String API_KEY;
    private Button viewAllPricesBtn;

    private RecyclerView ledgerCoinRV;
    private ArrayList<LedgerCoinModel> ledgerCoinModelArrayList;

    private GetLedger ledgerGetter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ledgerCoinRV = findViewById(R.id.LedgerCoin);

        ledgerCoinModelArrayList = new ArrayList<>();

        textView = findViewById(R.id.scrollingText);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSelected(true);

        Intent getKey = getIntent();
        API_KEY = getKey.getStringExtra("api_key");

        getPrices();
        getLedger();

        getDataBtn = findViewById(R.id.refresh_ticker);
        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Getting Prices", Toast.LENGTH_SHORT).show();
                getPrices();
            }
        });

        clearDataBtn = findViewById(R.id.get_ledger);
        clearDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
//                Toast.makeText(HomeActivity.this, "Getting Ledger", Toast.LENGTH_SHORT).show();
                getLedger();
            }
        });

        viewAllPricesBtn = findViewById(R.id.allPrices);
        viewAllPricesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAllPrices();
            }
        });
    }

    private void getPrices(){
        String url = "https://crypto-ledger.herokuapp.com/api/get-prices";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject coin = response.getJSONObject(i);
                                String name = coin.getString("symbol");
                                String price = coin.getString("price");
                                textView.append(" " + name + " - " + price.substring(0,price.length()-6) + " ");
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

    private void getLedger(){
        String url = "https://crypto-ledger.herokuapp.com/api/get-user-ledger/"+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject coin = response.getJSONObject(i);
                                String name = coin.getString("name");
                                String price = "Price: $" + coin.getString("current_price").substring(0,coin.getString("current_price").indexOf(".")+3);
                                String trend = coin.getString("price_difference");
                                trend = trend.substring(0, trend.indexOf(".")+2);
                                ledgerCoinModelArrayList.add(new LedgerCoinModel(name, price, trend));
                            }
                            LedgerCoinAdapter ledgerCoinAdapter = new LedgerCoinAdapter(HomeActivity.this, ledgerCoinModelArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
                            ledgerCoinRV.setLayoutManager(linearLayoutManager);
                            ledgerCoinRV.setAdapter(ledgerCoinAdapter);
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

    public void viewAllPrices(){
        Intent intent = new Intent(this, ViewPricesActivity.class);
        String prices = "" + textView.getText();
        intent.putExtra("tickerData", prices);
        startActivity(intent);
    }

}