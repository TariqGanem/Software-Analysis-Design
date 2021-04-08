package Business_Layer.Objects;

import DTO.ItemDTO;
import DTO.OrderDTO;
import DTO.OrderDTO.*;
import DTO.Status;

import java.time.LocalDate;
import java.util.*;


public class Order {
    private final int id;
    private Status status;
    private LocalDate placementDate;
    private LocalDate orderDate;
    private Map<Integer, Item> items;

    public Order(int Id,Status Status, LocalDate OrderDate) {
        this.id = Id;
        this.status = Status;
        this.placementDate = LocalDate.now();
        this.orderDate = OrderDate;
        this.items = new HashMap<>();
    }
    public Order(OrderDTO order){
        id = order.getId();
        status = order.getStatus();
        placementDate = order.getPlacementDate();
        orderDate = order.getOrderDate();
        this.items = new HashMap<>();
        for (Map.Entry<Integer, ItemDTO> items:
             order.getItems().entrySet()) {
            this.items.put(items.getKey(),new Item(items.getValue()));
        }
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getPlacementDate() {
        return placementDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public void setNewDate(LocalDate newDate){
        orderDate = newDate;
    }

    public void setNewStatus(Status newStatus){
        status = newStatus;
    }
    public void addItem(Item newItem){
        items.put(newItem.getId(), newItem);
    }

    public void removeItem(Item newItem){
        if(items.containsKey(newItem.getId()))
            items.remove(newItem.getId());
        else
            throw new NoSuchElementException("item doesn't exists!");

    }


}
