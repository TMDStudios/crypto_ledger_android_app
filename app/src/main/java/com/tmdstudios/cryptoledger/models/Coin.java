package com.tmdstudios.cryptoledger.models;

public class Coin {

    public String symbol;
    public String name;
    public String price;
    public String price_1h;
    public String price_24h;
    public String price_btc;
    public String price_eth;

    public Coin(String symbol, String name, String price, String price_1h, String price_24h,
                String price_btc, String price_eth){
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.price_1h = price_1h;
        this.price_24h = price_24h;
        this.price_btc = price_btc;
        this.price_eth = price_eth;
    }
}
