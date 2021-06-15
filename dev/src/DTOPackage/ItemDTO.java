package DTOPackage;

import BusinessLayer.SuppliersModule.Objects.Item;

public class ItemDTO {
    private final int id;
    private final String name;
    private final double price;
    private final double weight;


    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public ItemDTO(int id, String name, double price, double weight) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    public ItemDTO(Item item) {
        id = item.getId();
        name = item.getName();
        price = item.getPrice();
        weight = item.getWeight();
    }

    public ItemDTO(DataAccessLayer.SuppliersModule.Objects.Item item) {
        this.id = item.getId();
        this.price = item.getPrice();
        this.name = item.getName();
        this.weight = item.getWeight();
    }

    @Override
    public String toString() {
        return "Item's id: " + id + ", Item's name: " + name + ", Item's price: " + price + ", Item's weight: " + weight + "\n";
    }
}
