package DTO;

public class ItemDTO {
    private int id;
    private String name;
    private double price;

    public ItemDTO(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item's id: " + id + ", Item's name: " + name + ", Item's price: " + price + "\n";
    }
}
