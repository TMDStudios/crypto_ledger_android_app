package com.tmdstudios.cryptoledger.tools;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmdstudios.cryptoledger.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LedgerCoinAdapter extends RecyclerView.Adapter<LedgerCoinAdapter.Viewholder> {

    private Context context;
    private ArrayList<LedgerCoinModel> LedgerCoinModelArrayList;

    public LedgerCoinAdapter(Context context, ArrayList<LedgerCoinModel> LedgerCoinModelArrayList) {
        this.context = context;
        this.LedgerCoinModelArrayList = LedgerCoinModelArrayList;
    }

    @NonNull
    @Override
    public LedgerCoinAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ledger_card_view, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LedgerCoinAdapter.Viewholder holder, int position) {
        LedgerCoinModel model = LedgerCoinModelArrayList.get(position);
        holder.coinName.setText(model.getCoin_name());
        holder.coinPrice.setText(model.getCoin_price());
        String coinTrend = "Trend:  " + model.getCoin_trend() + "%";
        holder.coinTrend.setText(coinTrend);
        if(model.getCoin_trend().startsWith("-")){holder.coinTrend.setTextColor(Color.RED);}
        else{holder.coinTrend.setTextColor(Color.argb(255,34,139,34));}
    }

    @Override
    public int getItemCount() {return LedgerCoinModelArrayList.size();}

    public class Viewholder extends RecyclerView.ViewHolder {
        private final TextView coinName;
        private final TextView coinPrice;
        private final TextView coinTrend;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            coinName = itemView.findViewById(R.id.ledgerCoinName);
            coinPrice = itemView.findViewById(R.id.ledgerCoinPrice);
            coinTrend = itemView.findViewById(R.id.ledgerCoinTrend);
        }
    }
}
