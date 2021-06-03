package com.tmdstudios.cryptoledger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
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
import com.tmdstudios.cryptoledger.tools.SwipeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Button getDataBtn;
    private Button clearDataBtn;
    private TextView textView;
    private TextView tempText;
    private String API_KEY;
    private CardView cardView;
    private TextView coinName;
    private TextView coinPrice;
    private TextView coinTrend;
    private int coinNum = 0;
    private int availableCoins = 0;
    private List<List<String>> coins = new ArrayList<>();
    private Button viewAllPricesBtn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardView = findViewById(R.id.cardView);
        cardView.setOnTouchListener(new SwipeListener(this){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                super.onTouch(cardView, motionEvent);
//                Toast.makeText(HomeActivity.this, "Card on touch", Toast.LENGTH_SHORT).show();
                if(coinNum<availableCoins-1){coinNum++;}else{coinNum=0;}
//                Toast.makeText(HomeActivity.this, coinNum + "::" + coins.get(coinNum), Toast.LENGTH_SHORT).show();
                if(availableCoins>0){
                    coinName.setText(coins.get(coinNum).get(0));
                    coinPrice.setText("Price: " + coins.get(coinNum).get(1));
                    coinTrend.setText("Trend: " + coins.get(coinNum).get(2));
                    if(coins.get(coinNum).get(2).startsWith("-")){coinTrend.setTextColor(Color.RED);}
                    else{coinTrend.setTextColor(Color.GREEN);}
                }
                return false;
            }
        });

        textView = findViewById(R.id.scrollingText);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSelected(true);

        coinName = findViewById(R.id.coinName);
        coinPrice = findViewById(R.id.coinPrice);
        coinTrend = findViewById(R.id.coinTrend);

        tempText = findViewById(R.id.tempText);
        getPrices();

        Intent getKey = getIntent();
        API_KEY = getKey.getStringExtra("api_key");

        getDataBtn = findViewById(R.id.get_data);
        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Getting Prices", Toast.LENGTH_SHORT).show();
                getPrices();
            }
        });

        clearDataBtn = findViewById(R.id.clear_data);
        clearDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                Toast.makeText(HomeActivity.this, "Getting Ledger", Toast.LENGTH_SHORT).show();
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
                        tempText.setText("");
                        try {
                            availableCoins=response.length();
                            for(int i = 0; i < response.length(); i++){
                                JSONObject coin = response.getJSONObject(i);
                                String name = coin.getString("name");
                                String price = coin.getString("total_value");
                                tempText.append(name + " Total Value: " + price.substring(0,price.length()-6) + "\n");
                                String currentPrice = coin.getString("current_price");
                                String priceDifference = coin.getString("price_difference");
                                if(currentPrice.length()>8){
                                    currentPrice = currentPrice.substring(0,currentPrice.length()-6);
                                }
                                priceDifference = priceDifference.substring(0,8);
                                coinName.setText(coin.getString("name"));
                                coinPrice.setText("Price: " + currentPrice);
                                coinTrend.setText("Trend: " + priceDifference);
                                if(priceDifference.startsWith("-")){coinTrend.setTextColor(getResources().getColor(R.color.design_default_color_error));}
                                else{coinTrend.setTextColor(getResources().getColor(R.color.black));}
                                coins.add(new ArrayList<>());
                                coins.get(i).add(coin.getString("name"));
                                if(currentPrice.length()>8){
                                    coins.get(i).add(currentPrice.substring(0,currentPrice.length()-6));
                                }else{coins.get(i).add(currentPrice);}
                                coins.get(i).add(priceDifference);
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

    public void viewAllPrices(){
        Intent intent = new Intent(this, ViewPricesActivity.class);
        String prices = "" + textView.getText();
        intent.putExtra("tickerData", prices);
        startActivity(intent);
    }

}