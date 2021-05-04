package SharifMarket;

import java.util.ArrayList;
import java.util.Random;

public class Order {
    int ID;
    int buyer_ID;
    int type_ID;
    int count;
    String date;
    int sellPrice;
    int buyPrice;
    public Order(int buyer_ID, int type_ID, int count, int sellPrice, int buyPrice) {
        this.buyer_ID = buyer_ID;
        this.type_ID = type_ID;
        this.count = count;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.ID = (new Random()).nextInt(1000000000);
    }
}
