package DTO;

import BusinessLayer.Objects.Item;

public class ItemDTO {
    private final int id;
    private final String name;
    private final double price;


    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public ItemDTO(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ItemDTO(Item item){
        id = item.getId();
        name = item.getName();
        price = item.getPrice();
    }

    @Override
    public String toString() {
        return "Item's id: " + id + ", Item's name: " + name + ", Item's price: " + price + "\n";
    }
}
