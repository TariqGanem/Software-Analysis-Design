package BusinessLayer.Objects;

import DTO.ItemDTO;

public class Item {
    private final int id;
    private final String name;
    private double price;
    private int amount;


    public Item(int id, String name, double price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Item (ItemDTO item){
        id = item.getId();
        name = item.getName();
        price = item.getPrice();
        amount = item.getAmount();
    }
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

    public void ChangePrice(double price) {
        this.price = price;
    }

    public void Replace(Item item) {
        price = item.price;
    }
}
