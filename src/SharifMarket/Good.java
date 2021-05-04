package SharifMarket;

import java.util.Random;

public class Good {
    String name;
    int ID;
    int buyPrice;
    int sellPrice;
    int count;
    int P;
    public Good(String name, int count, int cellPrice, int buyPrice) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = cellPrice;
        this.count = count;
        Random random = new Random();
        this.ID = random.nextInt(100000000);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setSellPrice(int cellPrice) {
        this.sellPrice = cellPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
