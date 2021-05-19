package SharifMarket;

import java.util.Scanner;

import static SharifMarket.SharedMethods.*;

public class UserMode {
    DataBase dataBase;
    String fileName;
    Scanner scanner = new Scanner(System.in);

    public UserMode(String fileName) {
        this.fileName = fileName;
        dataBase = new DataBase();
        dataBase = DataBase.read(fileName);
    }

    public static void main(String[] args) {
        UserMode inputProcessor = new UserMode("Data Base.json");
        inputProcessor.run();
    }

    private void run() {
        try {
            System.out.print("(user menu)Please enter your command:");
            String[] cmd = scanner.nextLine().split(" ");
            if (cmd.length == 1 && cmd[0].equals("exit"))
                if (exit())
                    return;
            if (cmd[0].equals("login") && dataBase.userExists(cmd[1])) {
                userMode(dataBase.getUser(cmd[1]));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("WRONG INPUTS");
            run();
        }
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
                else if (cmd[0].equals("order") && !cmd[1].equals("-d") && dataBase.goodExists(Integer.parseInt(cmd[1])) && cmd[2].equals("-c")) {
                    System.out.println(dataBase.makeOrder(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[3]), user.ID));
                } else if (cmd[0].equals("order") && cmd[1].equals("-d")) {
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
        run();
    }
}

