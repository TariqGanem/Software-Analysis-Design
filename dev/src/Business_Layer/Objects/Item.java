package Business_Layer.Objects;

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

    public ItemDTO DTO() {
        return new ItemDTO(id, name, price);
    }

    public void ChangePrice(double price) {
        this.price = price;
    }
}
