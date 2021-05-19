package SharifMarket;

import java.util.Scanner;

import static SharifMarket.SharedMethods.*;

public class AdminMode {
    DataBase dataBase;
    String fileName;
    Scanner scanner = new Scanner(System.in);

    public AdminMode(String fileName) {
        this.fileName = fileName;
        dataBase = new DataBase();
        dataBase = DataBase.read(fileName);
    }

    public static void main(String[] args) {
        AdminMode adminMode = new AdminMode("Data Base.json");
        adminMode.run();
    }

    private void run() {
        try {
            System.out.print("(admin menu)Please enter your command:");
            String[] cmd = scanner.nextLine().split(" ");
            if (cmd.length == 1 && cmd[0].equals("exit"))
                if (exit())
                    return;
            if (cmd[0].equals("login") && dataBase.adminExists(cmd[1]))
                adminMode(dataBase.getAdmin(cmd[1]));
            else throw new Exception();
        } catch (Exception e) {
            System.out.println("WRONG INPUTS");
            run();
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
                else if (cmd[0].equals("checkout") && dataBase.uncheckedOrderExists(Integer.parseInt(cmd[1])))
                    dataBase.checkOut(Integer.parseInt(cmd[1]));
                else if (cmd[0].equals("add") &&
                        cmd[1].equals("-n") &&
                        cmd[3].equals("-c") &&
                        cmd[5].equals("-sp") &&
                        cmd[7].equals("-bp"))
                    System.out.println(dataBase.addGood(cmd[2], Integer.parseInt(cmd[4]), Integer.parseInt(cmd[6]), Integer.parseInt(cmd[8])));
                else if (cmd[0].equals("remove") && cmd[1].equals("-c"))
                    System.out.println(dataBase.removeGood(Integer.parseInt(cmd[2])));
                else if (cmd[0].equals("edit") && dataBase.goodExists(Integer.parseInt(cmd[0]))) {
                    Good good = dataBase.getGood(Integer.parseInt(cmd[0]));
                    for (int i = 2; i < cmd.length - 1; i++)
                        switch (cmd[i]) {
                            case "-n" -> good.setName(cmd[i + 1]);
                            case "-c" -> good.setCount(Integer.parseInt(cmd[i + 1]));
                            case "-sp" -> good.setSellPrice(Integer.parseInt(cmd[i + 1]));
                            case "-bp" -> good.setBuyPrice(Integer.parseInt(cmd[i + 1]));
                        }
                } else if (cmd[0].equals("logout") && cmd.length == 1) {
                    System.out.println("You are logged out.");
                    break;
                } else if (cmd[0].equals("calc")) {
                    if (cmd[1].equals("-p") && cmd.length == 2)
                        System.out.println(dataBase.getP() + " IRR");
                    else if (cmd[1].equals("-p") && cmd[2].equals("-c") && dataBase.goodExists(Integer.parseInt(cmd[3])) && cmd.length == 4)
                        System.out.println(dataBase.getP(Integer.parseInt(cmd[3])) + " IRR");
                    else if (cmd[1].equals("-s") && cmd.length == 2)
                        System.out.println(dataBase.getSellProfit() + " IRR");
                    else if (cmd[1].equals("-s") && cmd[2].equals("-c") && dataBase.goodExists(Integer.parseInt(cmd[4])))
                        System.out.println(dataBase.getSellProfit(Integer.parseInt(cmd[3])) + " IRR");
                    else continue;
                } else throw new Exception();
            } catch (Exception e) {
                System.out.println("WRONG INPUTS");
                continue;
            }
            DataBase.write(fileName, dataBase);
        }
        run();
    }


}
