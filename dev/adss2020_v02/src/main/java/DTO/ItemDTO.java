package DTO;

import DataAccessLayer.Objects.Item;

public class ItemDTO {
    private final int id;
    private final String name;
    private final double price;
    private int amount;


    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public ItemDTO(int id, String name, double price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public ItemDTO(BusinessLayer.Objects.Item item){
        id = item.getId();
        name = item.getName();
        price = item.getPrice();
        amount = item.getAmount();
    }

    public ItemDTO(DataAccessLayer.Objects.Item item){
        this.id = item.getId();
        this.price = item.getPrice();
        this.name = item.getName();
        this.amount = item.getAmount();
    }
    @Override
    public String toString() {
        return "Item's id: " + id + ", Item's name: " + name + ", Item's price: " + price + ", amount: " + amount + "\n";
    }
}
