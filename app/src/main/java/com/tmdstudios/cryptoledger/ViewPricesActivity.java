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
        coinModelArrayList.add(new CoinModel("Bitcoin", 44500));
        coinModelArrayList.add(new CoinModel("Ethereum", 2300));
        coinModelArrayList.add(new CoinModel("Monero", 520));
        coinModelArrayList.add(new CoinModel("Ripple", 1));
        coinModelArrayList.add(new CoinModel("Doge", .30f));
        coinModelArrayList.add(new CoinModel("Cardano", 2.30f));

        CoinAdapter coinAdapter = new CoinAdapter(this, coinModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        coinRV.setLayoutManager(linearLayoutManager);
        coinRV.setAdapter(coinAdapter);
    }
}