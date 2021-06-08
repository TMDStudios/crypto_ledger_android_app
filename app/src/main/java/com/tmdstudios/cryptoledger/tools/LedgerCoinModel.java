package com.tmdstudios.cryptoledger.tools;

public class LedgerCoinModel {
    private final String coin_name;
    private final String coin_price;

    public LedgerCoinModel(String coin_name, String coin_price) {
        this.coin_name = coin_name;
        this.coin_price = coin_price;
    }

    public String getCoin_name() {return coin_name;}
    public String getCoin_price() {return coin_price;}
}
