package SharifMarket;

import java.util.Random;

public class Order {
    int ID;
    String name;
    String buyer_ID;
    int type_ID;
    int count;
    int sellPrice;
    int buyPrice;

    public Order(String buyer_ID, int type_ID, int count, int sellPrice, int buyPrice, String name) {
        this.name = name;
        this.buyer_ID = buyer_ID;
        this.type_ID = type_ID;
        this.count = count;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.ID = (new Random()).nextInt(1000000000);
    }
}
