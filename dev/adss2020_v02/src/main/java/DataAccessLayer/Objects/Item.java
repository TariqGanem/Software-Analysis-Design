package DataAccessLayer.Objects;

import DTO.ItemDTO;

public class Item {
    private int id;
    private String name;
    private double price;

    public Item(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Item (ItemDTO itemDTO){
        this.id = itemDTO.getId();
        this.name = itemDTO.getName();
        this.price = itemDTO.getPrice();
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
}
