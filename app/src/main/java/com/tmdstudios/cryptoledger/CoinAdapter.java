package com.tmdstudios.cryptoledger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.Viewholder> {

    private Context context;
    private ArrayList<CoinModel> CoinModelArrayList;

    public CoinAdapter(Context context, ArrayList<CoinModel> CoinModelArrayList) {
        this.context = context;
        this.CoinModelArrayList = CoinModelArrayList;
    }

    @NonNull
    @Override
    public CoinAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinAdapter.Viewholder holder, int position) {
        CoinModel model = CoinModelArrayList.get(position);
        holder.coinName.setText(model.getCoin_name());
        holder.coinPrice.setText("" + model.getCoin_price());
    }

    @Override
    public int getItemCount() {
        return CoinModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView coinName, coinPrice;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            coinName = itemView.findViewById(R.id.coinName);
            coinPrice = itemView.findViewById(R.id.coinPrice);
        }
    }
}
