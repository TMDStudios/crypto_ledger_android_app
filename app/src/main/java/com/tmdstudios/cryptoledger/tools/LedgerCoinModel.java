package com.tmdstudios.cryptoledger.tools;

public class LedgerCoinModel {
    private final String coin_name;
    private final String coin_price;
    private final String coin_trend;

    public LedgerCoinModel(String coin_name, String coin_price, String coin_trend) {
        this.coin_name = coin_name;
        this.coin_price = coin_price;
        this.coin_trend = coin_trend;
    }

    public String getCoin_name() {return coin_name;}
    public String getCoin_price() {return coin_price;}
    public String getCoin_trend() {return coin_trend;}
}
