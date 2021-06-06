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
        holder.coinPrice.setText(model.getCoin_price());
        String price1h = "1h:  " + model.getPrice1h() + "%";
        holder.price1h.setText(price1h);
        if(model.getPrice1h().startsWith("-")){holder.price1h.setTextColor(Color.RED);}
        else{holder.price1h.setTextColor(Color.argb(255,34,139,34));}
        String price24h = "24h:  " + model.getPrice24h() + "%";
        holder.price24h.setText(price24h);
        if(model.getPrice24h().startsWith("-")){holder.price24h.setTextColor(Color.RED);}
        else{holder.price24h.setTextColor(Color.argb(255,34,139,34));}
        holder.priceBTC.setText(model.getPriceBTC());
        holder.priceETH.setText(model.getPriceETH());
    }

    @Override
    public int getItemCount() {return CoinModelArrayList.size();}

    public class Viewholder extends RecyclerView.ViewHolder {
        private final TextView coinName;
        private final TextView coinPrice;
        private final TextView price1h;
        private final TextView price24h;
        private final TextView priceBTC;
        private final TextView priceETH;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            coinName = itemView.findViewById(R.id.coinName);
            coinPrice = itemView.findViewById(R.id.coinPrice);
            price1h = itemView.findViewById(R.id.price1h);
            price24h= itemView.findViewById(R.id.price24h);
            priceBTC = itemView.findViewById(R.id.priceBTC);
            priceETH = itemView.findViewById(R.id.priceETH);
        }
    }
}
