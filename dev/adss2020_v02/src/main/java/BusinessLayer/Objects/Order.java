package BusinessLayer.Objects;

import DTO.ItemDTO;
import DTO.OrderDTO;
import Enums.Status;

import java.time.LocalDate;
import java.util.*;


public class Order {
    private final int id;
    private Status status;
    private LocalDate placementDate;
    private LocalDate orderDate;
    private Map<Integer, Item> items;

    public Order(int Id,Status Status, LocalDate placementDate,LocalDate OrderDate) {
        this.id = Id;
        this.status = Status;
        this.placementDate = placementDate;
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

    public void setNewDate(LocalDate newDate) throws Exception {
        if (orderDate.isBefore(newDate)){
            orderDate = newDate;
        }
        else{
            throw new Exception("new date cannot be equal or earlier than current due date of the order!");
        }
    }

    public void setNewStatus(Status newStatus) throws Exception {
        if ((status == Status.Active && ( newStatus == Status.Completed|| newStatus==Status.Canceled)) || (status == Status.Canceled && newStatus == Status.Active) || (status == Status.inPrepared && newStatus == Status.Active))
            status = newStatus;
        else throw new Exception("Status cannot be changed from "+status+" to "+newStatus+"!");
    }
    public void addItem(Item newItem){

        items.put(newItem.getId(), newItem);
    }

    public void removeItem(int itemID){
        if(items.containsKey(itemID))
            items.remove(itemID);
        else
            throw new NoSuchElementException("item doesn't exists!");

    }


}
