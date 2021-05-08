package SharifMarket;

import java.util.ArrayList;
import java.util.Scanner;

public class InputProcessor {
    DataBase dataBase;
    String fileName;
    Scanner scanner = new Scanner(System.in);
    public InputProcessor(String fileName) {
        this.fileName = fileName;
        dataBase = new DataBase();
        dataBase = DataBase.read(fileName);
    }
    public void run() {
        mainMenu();

    }

    private void mainMenu() {
        try {

            System.out.print("(main menu)Please enter your command:");
            String[] cmd = scanner.nextLine().split(" ");
            if (cmd.length == 1 && cmd[0].equals("exit"))
                if (exit())
                    return;
            if (cmd[0].equals("login") && dataBase.adminExists(cmd[1])) {
                adminMode(dataBase.getAdmin(cmd[1]));
            } else if (cmd[0].equals("login") && dataBase.userExists(cmd[1])) {
                userMode(dataBase.getUser(cmd[1]));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("WRONG INPUTS");
            mainMenu();
        }
    }
    private void adminMode(Admin admin) {
        System.out.println("Hello " + admin.name + ".");
        while (true) {
            try {
                System.out.print("(admin mode)Please enter your command:");
                String[] cmd = scanner.nextLine().split(" ");

                if (cmd[0].equals("ls") && cmd[1].equals("-r")) showInventory(dataBase.getGoods());
                else if (cmd[0].equals("ls") && cmd[1].equals("-i")) showAvailableInventory(dataBase.getGoods());
                else if (cmd[0].equals("ls") && cmd[1].equals("-n")) showNotAvailableInventory(dataBase.getGoods());
                else if (cmd[0].equals("ls") && cmd[1].equals("-o")) dataBase.showOrders(false);
                else if (cmd[0].equals("ls") && cmd[1].equals("-ho")) dataBase.showOrders(true);
                else if (cmd[0].equals("checkout") && dataBase.uncheckedOrderExists(Integer.parseInt(cmd[1]))) dataBase.checkOut(Integer.parseInt(cmd[1]));
                else if (cmd[0].equals("add") &&
                        cmd[1].equals("-n") &&
                        cmd[3].equals("-c") &&
                        cmd[5].equals("-sp") &&
                        cmd[7].equals("-bp"))
                    System.out.println(dataBase.addGood(cmd[2], Integer.parseInt(cmd[4]), Integer.parseInt(cmd[6]), Integer.parseInt(cmd[8])));
                else if (cmd[0].equals("remove") && cmd[1].equals("-c")) System.out.println(dataBase.removeGood(Integer.parseInt(cmd[2])));
                else if (cmd[0].equals("edit") && dataBase.goodExists(Integer.parseInt(cmd[0]))) { Good good = dataBase.getGood(Integer.parseInt(cmd[0]));
                    for (int i = 2; i < cmd.length - 1; i++)
                        switch (cmd[i]) {
                            case "-n" -> good.setName(cmd[i + 1]);
                            case "-c" -> good.setCount(Integer.parseInt(cmd[i + 1]));
                            case "-sp" -> good.setSellPrice(Integer.parseInt(cmd[i + 1]));
                            case "-bp" -> good.setBuyPrice(Integer.parseInt(cmd[i + 1]));
                        }
                } else if (cmd[0].equals("logout") && cmd.length == 1)
                    break;
                else if (cmd[0].equals("calc")) {
                    if (cmd[1].equals("-p") && cmd.length == 2)
                        System.out.println(dataBase.getP() + " IRR");
                    else if (cmd[1].equals("-p") && cmd[2].equals("-c") && dataBase.goodExists(Integer.parseInt(cmd[3])) && cmd.length == 4)
                        System.out.println(dataBase.getP(Integer.parseInt(cmd[3])) + " IRR");
                    else if (cmd[1].equals("-s") && cmd.length == 2)
                        System.out.println(dataBase.getSellProfit() + " IRR");
                    else if (cmd[1].equals("-s") && cmd[2].equals("-c") && dataBase.goodExists(Integer.parseInt(cmd[4])))
                        System.out.println(dataBase.getSellProfit(Integer.parseInt(cmd[3])) + " IRR");
                    else continue;
                }
                else throw new Exception();
            } catch (Exception e) {
                System.out.println("WRONG INPUTS");
                continue;
            }
            DataBase.write(fileName, dataBase);
        }
        mainMenu();
    }
    private void userMode(User user) {
        System.out.println("Hello " + user.name + ".");
        while (true) {
            try {
                System.out.print("(user mode)Please enter your command:");
                String[] cmd = scanner.nextLine().split(" ");
                if (cmd[0].equals("ls") && cmd[1].equals("-r")) showInventory(dataBase.getGoods());
                else if (cmd[0].equals("ls") && cmd[1].equals("-i")) showNotAvailableInventory(dataBase.getGoods());
                else if (cmd[0].equals("ls") && cmd[1].equals("-n")) showAvailableInventory(dataBase.getGoods());
                if (cmd[0].equals("order") && !cmd[1].equals("-d") && dataBase.goodExists(Integer.parseInt(cmd[1])) && cmd[2].equals("-c")) {
                    System.out.println(dataBase.makeOrder(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[3]), user.ID));
                } else if (cmd[0].equals("order") && cmd[1].equals("-d")) {
                    System.out.println("dfg");
                    System.out.println(dataBase.deleteOrder(Integer.parseInt(cmd[2]), user.ID));
                } else if (cmd[0].equals("logout") && cmd.length == 1)
                    break;
                else throw new Exception();
            } catch (Exception e) {
                System.out.println("WRONG INPUTS");
                continue;
            }
            DataBase.write(fileName, dataBase);
        }
        mainMenu();
    }

    private void showInventory(ArrayList<Good> goods) {
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
    private void showAvailableInventory(ArrayList<Good> goods) {
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
    private void showNotAvailableInventory(ArrayList<Good> goods) {
        System.out.println("+-----------------+------------+------------+");
        System.out.println("| Good name       | Price(IRR) | ID         |");
        System.out.println("+-----------------+------------+------------+");
        for (Good good : goods) {
            if (good.count == 0) {
                System.out.print("| " + good.name);
                spaceMaker(16 - good.name.length());
                System.out.print("| " + good.sellPrice);
                spaceMaker(11 - numOfDigits(good.sellPrice));
                System.out.println("| " + good.ID);
                System.out.print(11 - numOfDigits(good.ID)+"|");
            }
        }
        System.out.println("+-----------------+------------+------------+");
    }
    private boolean exit() {
        System.out.print("Do you really wish to exit(YES/NO)?");
        return new Scanner(System.in).next().equals("YES");
    }
    void spaceMaker(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
    }
    int numOfDigits(int i) {
        int n = 0;
        while (i != 0) {
            n += 1;
            i /= 10;
        }
        return n;
    }
}
