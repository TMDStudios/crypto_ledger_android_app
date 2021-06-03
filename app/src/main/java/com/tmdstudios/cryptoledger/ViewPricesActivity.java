package com.tmdstudios.cryptoledger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPricesActivity extends AppCompatActivity {
    private Button refreshBtn;
    private TextView tickerText;
    private CardView cardView;
    private TextView coinName;

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

        cardView = findViewById(R.id.cardView);
        coinName = findViewById(R.id.coinName);

        Intent getPrices = getIntent();
        tickerText.append(getPrices.getStringExtra("tickerData"));
    }
}