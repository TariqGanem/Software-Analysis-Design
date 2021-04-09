package Business_Layer.Objects;

import DTO.ItemDTO;

public class Item {
    private final int id;
    private final String name;
    private double price;

    public Item(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Item (ItemDTO item){
        id = item.getId();
        name = item.getName();
        price = item.getPrice();
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


    public void ChangePrice(double price) {
        this.price = price;
    }

    public void Replace(Item item) {
        price = item.price;
    }
}
