package com.tmdstudios.cryptoledger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.tmdstudios.cryptoledger.tools.CoinAdapter;
import com.tmdstudios.cryptoledger.tools.CoinModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewPricesActivity extends AppCompatActivity {
    private Button refreshBtn;
    private TextView tickerText;
    private CardView cardView;
    private TextView coinName;

    private RecyclerView coinRV;
    private ArrayList<CoinModel> coinModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prices);

        tickerText = findViewById(R.id.scrollingText);
        tickerText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tickerText.setSelected(true);

        refreshBtn = findViewById(R.id.refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewPricesActivity.this, "Getting Prices", Toast.LENGTH_SHORT).show();
            }
        });

//        cardView = findViewById(R.id.cardView);
//        coinName = findViewById(R.id.coinName);

        Intent getPrices = getIntent();
        tickerText.append(getPrices.getStringExtra("tickerData"));

        coinRV = findViewById(R.id.RVCoin);

        coinModelArrayList = new ArrayList<>();
//        coinModelArrayList.add(new CoinModel("Bitcoin", 44500));
//        coinModelArrayList.add(new CoinModel("Ethereum", 2300));
//        coinModelArrayList.add(new CoinModel("Monero", 520));
//        coinModelArrayList.add(new CoinModel("Ripple", 1));
//        coinModelArrayList.add(new CoinModel("Doge", .30f));
//        coinModelArrayList.add(new CoinModel("Cardano", 2.30f));

        getPrices();

//        CoinAdapter coinAdapter = new CoinAdapter(this, coinModelArrayList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        coinRV.setLayoutManager(linearLayoutManager);
//        coinRV.setAdapter(coinAdapter);
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
                                coinModelArrayList.add(new CoinModel(name, Float.parseFloat(price)));
                            }
                            CoinAdapter coinAdapter = new CoinAdapter(ViewPricesActivity.this, coinModelArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewPricesActivity.this, LinearLayoutManager.VERTICAL, false);
                            coinRV.setLayoutManager(linearLayoutManager);
                            coinRV.setAdapter(coinAdapter);
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