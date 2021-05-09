package DataAccessLayer.Objects;

import DTO.ItemDTO;

public class Item {
    private int id;
    private String name;
    private double price;
    private int amount;

    public Item(int id, String name, double price, int amount) {
        this.id = id;
        this.name = name;// should delete
        this.price = price;// should delete
        this.amount = amount;
    }

    public Item (ItemDTO itemDTO){
        this.id = itemDTO.getId();
        this.name = itemDTO.getName(); // should delete
        this.price = itemDTO.getPrice(); // should delete
        this.amount = itemDTO.getAmount();
    }

    public void updatePrice(double price) {
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
