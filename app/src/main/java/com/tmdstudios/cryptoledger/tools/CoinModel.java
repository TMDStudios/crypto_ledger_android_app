package com.tmdstudios.cryptoledger.tools;

public class CoinModel {

    private final String coin_name;
    private final String coin_price;
    private final String price1h;
    private final String price24h;
    private final String priceBTC;
    private final String priceETH;

    public CoinModel(String coin_name, String coin_price, String price1h, String price24h, String priceBTC, String priceETH) {
        this.coin_name = coin_name;
        this.coin_price = coin_price;
        this.price1h = price1h;
        this.price24h = price24h;
        this.priceBTC = priceBTC;
        this.priceETH = priceETH;
    }

    public String getCoin_name() {return coin_name;}
    public String getCoin_price() {return coin_price;}
    public String getPrice1h() {return price1h;}
    public String getPrice24h() {return price24h;}
    public String getPriceBTC(){return priceBTC;}
    public String getPriceETH(){return priceETH;}
}
