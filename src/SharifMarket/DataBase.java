package SharifMarket;
import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
public class DataBase {
    private ArrayList<Good> goods = new ArrayList<>();
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Order> ordersHistory = new ArrayList<>();
    private ArrayList<Order> uncheckedOrders = new ArrayList<>();
    private int P = 0;
    private int S = 0;
    public ArrayList<Order> getUncheckedOrders(){ return uncheckedOrders; }
    public void checkOut(int ID){
        for (Order order : uncheckedOrders)
            if (order.ID == ID) {
                ordersHistory.add(order);
                P += order.count*(order.sellPrice-order.buyPrice);
                S += order.count*order.sellPrice;
                uncheckedOrders.remove(order);
                return;
            }
    }
    public boolean uncheckedOrderExists(int ID){
        for (Order order : uncheckedOrders)
            if (order.ID == ID)
                return true;
        return false;
    }
    public ArrayList<Order> getAllOrders() {
        return ordersHistory;
    }
    public boolean adminExists(String s) {
        for (Admin admin:admins) {
            if(admin.ID.equals(s))
                return true;
        }
        return false;
    }
    public Admin getAdmin(String s) {
        for (Admin admin:admins) {
            if(admin.ID.equals(s))
                return admin;
        }
        return new Admin();
    }
    public int getP(){
        return P;
    }
    public int getP(int ID){
        int p=0;
        for (Order order : ordersHistory)
            if(order.type_ID == ID)
                p += order.count*(order.sellPrice - order.buyPrice);
        return p;
    }
    public boolean userExists(String s) {
        for (User user:users) {

            if((user.ID).equals(s))
                return true;
            System.out.println(user.ID + " != " + s);
        }
        return false;
    }
    public User getUser(String s) {
        for (User user:users) {
            if(user.ID == s)
                return user;
        }
        return new User();
    }
    boolean goodExists(String s){
        for (Good good:goods) {
            if(good.name.equals(s))
                return true;
        }
        return false;
    }
    boolean goodExists(int ID){
        for (Good good:goods) {
            if(good.ID == ID)
                return true;
        }
        return false;
    }
    public String addGood(String s, int initialCount, int sellPrice, int buyPrice) {
        if(goodExists(s))
            return "The good already exists.";
        Good good = new Good(s,initialCount,sellPrice,buyPrice);
        goods.add(good);
        return "add was successful -> good_id = "+good.ID;
    }
    public ArrayList<Good> getGoods(){
        return goods;
    }
    public Good getGood(int ID){
        for (Good good:goods) {
            if(good.ID == ID)
                return good;
        }
        return null;
    }
    public String removeGood(int ID) {
        boolean b = goods.removeIf(good -> good.ID == ID);
        if(b)
            return "The good is removed.";
        return "The ID is wrong.";
    }
    public String getSellProfit() {
        //TODO
        return "WTF AM I SUPPOSED TO SHOW TO U?";
    }
    public String getSellProfit(int ID) {
        Good good = getGood(ID);
        int orders=0;
        int totalSells = 0;
        int sell = 0;
        int profit = 0;
        for (Order order: ordersHistory) {
            if(order.type_ID == ID){
                orders += 1;
                totalSells += order.count;
                sell += order.count*order.sellPrice;
                profit += order.count*(order.sellPrice-order.buyPrice);
            }
        }
        return orders+" orders, "+totalSells+" "+good.name+"s, "+sell+" IRR sell, "+profit+" IRR profit";
    }
    public String makeOrder(int ID, int count, String buyerID) {
        if(goodExists(ID)){
            Good good = getGood(ID);
            if(good.count>=count){
                Order order = new Order(buyerID,ID,count,good.sellPrice,good.buyPrice);
                uncheckedOrders.add(order);
                good.count -= count;
                return "Your order id is = "+order.ID;
            }
            return "Not enough in storage";
        }
        return "This ID is wrong or doesnt exist anymore";
    }
    public String deleteOrder(int ID) {
        //TODO
        for (Order order: ordersHistory) {
            if(order.ID == ID) {
                ordersHistory.remove(order);
                return "order "+ID+" was deleted successfully!";
            }
        }
        for (Order order: uncheckedOrders) {
            if(order.ID == ID){
                uncheckedOrders.remove(order);
                return "order "+ID+" was deleted successfully!";
            }

        }
        return "The ID is wrong or the order doesn't exist anymore.";
    }
    public static void write(String fileName, DataBase dataBase) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(fileName);){
            gson.toJson(dataBase, writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static DataBase read(String fileName) {
        Gson gson = new Gson();
        try (FileReader json = new FileReader(fileName)){
            return gson.fromJson(json,DataBase.class);
        }  catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("DIDNT READ");
        return new DataBase();
    }
}
