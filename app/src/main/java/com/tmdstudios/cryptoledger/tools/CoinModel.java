package com.tmdstudios.cryptoledger.tools;

public class CoinModel {

    private String coin_name;
    private float coin_price;

    public CoinModel(String coin_name, float coin_price) {
        this.coin_name = coin_name;
        this.coin_price = coin_price;
    }

    public String getCoin_name() {
        return coin_name;
    }

    public void setCoin_name(String coin_name) {
        this.coin_name = coin_name;
    }

    public float getCoin_price() {
        return coin_price;
    }

    public void setCoin_price(float coin_price) {
        this.coin_price = coin_price;
    }
}
