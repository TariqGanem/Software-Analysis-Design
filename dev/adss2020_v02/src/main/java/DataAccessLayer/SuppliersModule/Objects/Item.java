package DataAccessLayer.SuppliersModule.Objects;

import DTO.ItemDTO;

public class Item {
    private int id;
    private String name;
    private double price;

    public Item(int id, String name, double price) {
        this.id = id;
        this.name = name;// should delete
        this.price = price;// should delete
    }

    public Item (ItemDTO itemDTO){
        this.id = itemDTO.getId();
        this.name = itemDTO.getName(); // should delete
        this.price = itemDTO.getPrice(); // should delete
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
