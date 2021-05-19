package SharifMarket;

import java.util.ArrayList;
import java.util.Scanner;

public class SharedMethods {
    static public void showInventory(ArrayList<Good> goods) {
        System.out.println("+-----------------+------------+------------+------------+");
        System.out.println("| Good name       | Inventory  | Price(IRR) | ID         |");
        System.out.println("+-----------------+------------+------------+------------+");
        for (Good good : goods) {
            System.out.print("| " + good.name);
            spaceMaker(16 - good.name.length());
            System.out.print("| " + good.count + " ");
            spaceMaker(10 - numOfDigits(good.count));
            System.out.print("| " + good.sellPrice);
            spaceMaker(11 - numOfDigits(good.sellPrice));
            System.out.print("| " + good.ID);
            spaceMaker(11 - numOfDigits(good.ID));
            System.out.println("|");
        }
        System.out.println("+-----------------+------------+------------+------------+");
    }

    static public void showAvailableInventory(ArrayList<Good> goods) {
        System.out.println("+-----------------+------------+------------+------------+");
        System.out.println("| Good name       | Inventory  | Price(IRR) | ID         |");
        System.out.println("+-----------------+------------+------------+------------+");
        for (Good good : goods) {
            if (good.count > 0) {
                System.out.print("| " + good.name);
                spaceMaker(16 - good.name.length());
                System.out.print("| " + good.count + " ");
                spaceMaker(10 - numOfDigits(good.count));
                System.out.print("| " + good.sellPrice);
                spaceMaker(11 - numOfDigits(good.sellPrice));
                System.out.print("| " + good.ID);
                spaceMaker(11 - numOfDigits(good.ID));
                System.out.println("|");
            }
        }
        System.out.println("+-----------------+------------+------------+------------+");
    }

    static public void showNotAvailableInventory(ArrayList<Good> goods) {
        System.out.println("+-----------------+------------+------------+");
        System.out.println("| Good name       | Price(IRR) | ID         |");
        System.out.println("+-----------------+------------+------------+");
        for (Good good : goods) {
            if (good.count == 0) {
                System.out.print("| " + good.name);
                spaceMaker(16 - good.name.length());
                System.out.print("| " + good.sellPrice);
                spaceMaker(11 - numOfDigits(good.sellPrice));
                System.out.print("| " + good.ID);
                spaceMaker(11 - numOfDigits(good.ID));
                System.out.println("|");
            }
        }
        System.out.println("+-----------------+------------+------------+");
    }

    static public boolean exit() {
        System.out.print("Do you really wish to exit(YES/NO)?");
        return new Scanner(System.in).next().equals("YES");
    }

    static public void spaceMaker(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
    }

    static public int numOfDigits(int i) {
        if (i == 0)
            return 1;
        int n = 0;
        while (i != 0) {
            n += 1;
            i /= 10;
        }
        return n;
    }
}
