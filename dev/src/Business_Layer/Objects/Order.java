package Business_Layer.Objects;

import Business_Layer.Objects.Item;

import java.time.LocalDate;
import java.util.*;

public class Order {
    private int id;
    private String status;
    private LocalDate createdDate;
    private LocalDate orderDate;
    private Map<Integer, Item> items;

    public Order(int Id,String Status, LocalDate OrderDate) {
        this.id = Id;
        this.status = Status;
        this.createdDate = LocalDate.now();
        this.orderDate = OrderDate;
        this.items = new HashMap<>();
    }
}
