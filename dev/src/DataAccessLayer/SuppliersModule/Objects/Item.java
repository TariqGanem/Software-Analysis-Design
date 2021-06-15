package DataAccessLayer.SuppliersModule.Objects;

import DTOPackage.ItemDTO;

public class Item {
    private int id;
    private String name;
    private double price;
    private double weight;

    public Item(int id, String name, double price, double weight) {
        this.id = id;
        this.name = name;// should delete
        this.price = price;// should delete
        this.weight = weight;
    }

    public Item(ItemDTO itemDTO) {
        this.id = itemDTO.getId();
        this.name = itemDTO.getName(); // should delete
        this.price = itemDTO.getPrice(); // should delete
        this.weight = weight;
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

    public double getWeight() {
        return weight;
    }
}
