package BusinessLayer.SuppliersModule.Objects;

import DTOPackage.ItemDTO;
import DTOPackage.OrderDTO;
import Resources.Status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


public class Order {
    protected int id;
    protected Status status;
    protected LocalDate placementDate;
    protected LocalDate orderDate;
    protected Map<Integer, Item> items;
    protected Map<Integer, Integer> amounts;

    public Order(int id, Status Status, LocalDate OrderDate) {
        this.id = id;
        this.status = Status;
        this.placementDate = LocalDate.now();
        this.orderDate = OrderDate;
        this.items = new HashMap<>();
        this.amounts = new HashMap<>();
    }

    public Order(OrderDTO order) {
        id = order.getId();
        status = order.getStatus();
        placementDate = order.getPlacementDate();
        orderDate = order.getDueDate();
        this.items = new HashMap<>();
        for (Map.Entry<Integer, ItemDTO> items :
                order.getItems().entrySet()) {
            this.items.put(items.getKey(), new Item(items.getValue()));
        }
        this.amounts = new HashMap<>();
        for (Map.Entry<Integer, Integer> amounts :
                order.getAmounts().entrySet()) {
            this.amounts.put(amounts.getKey(), amounts.getValue());
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

    public Map<Integer, Integer> getAmounts() {
        return amounts;
    }

    public void setNewDate(LocalDate newDate) throws Exception {
        if (orderDate.isBefore(newDate)) {
            orderDate = newDate;
        } else {
            throw new Exception("new date cannot be equal or earlier than current due date of the order!");
        }
    }

    public void setNewStatus(Status newStatus) throws Exception {
        if ((status == Status.Active && (newStatus == Status.Completed || newStatus == Status.Canceled)) || (status == Status.Canceled && newStatus == Status.Active) || (status == Status.inPrepared && newStatus == Status.Active))
            status = newStatus;
        else throw new Exception("Status cannot be changed from " + status + " to " + newStatus + "!");
    }

    public void addItem(Item newItem) {
        items.put(newItem.getId(), newItem);
    }

    public void removeItem(int itemID) {
        if (items.containsKey(itemID))
            items.remove(itemID);
        else
            throw new NoSuchElementException("item doesn't exists!");

    }


}
