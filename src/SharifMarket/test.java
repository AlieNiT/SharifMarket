package SharifMarket;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
