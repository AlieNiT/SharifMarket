package SharifMarket;

public class Admin {

    private String name;
    private String pass;
    String ID;


    public Admin(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Admin() {
    }

    public String getName() {
        return name;
    }
}
