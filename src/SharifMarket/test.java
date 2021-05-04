package SharifMarket;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {

    }

    static int numOfDigits(int i) {
        if(i == 0) return 1;
        int n = 0;
        while (i != 0) {
            n += 1;
            i /= 10;
        }
        return n;
    }





}
