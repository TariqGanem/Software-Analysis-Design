package BusinessLayer.SuppliersModule.Objects;

import DTOPackage.ItemDTO;

public class Item {
    private final int id;
    private final String name;
    private double price;
    private double weight;


    public Item(int id, String name, double price, double weight) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    public Item(ItemDTO item) {
        id = item.getId();
        name = item.getName();
        price = item.getPrice();
        weight = item.getWeight();
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

    public double getWeight() {
        return weight;
    }
}
