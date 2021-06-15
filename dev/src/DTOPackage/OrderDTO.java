package DTOPackage;

import BusinessLayer.SuppliersModule.Objects.Order;
import Resources.Status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderDTO {
    private int id;
    private final Status status;
    private final LocalDate placementDate;
    private final LocalDate dueDate;
    private final Map<Integer, ItemDTO> items;
    private final Map<Integer, Integer> amounts;


    public OrderDTO(Order order) {
        id = order.getId();
        status = order.getStatus();
        placementDate = order.getPlacementDate();
        dueDate = order.getOrderDate();
        items = new HashMap<>();
        for (Integer id :
                order.getItems().keySet()) {
            this.items.put(id, new ItemDTO(order.getItems().get(id)));
        }
        this.amounts = new HashMap<>();
        for (Integer id : order.getAmounts().keySet()) {
            this.amounts.put(id, order.getAmounts().get(id));
        }
    }

    public OrderDTO(DataAccessLayer.SuppliersModule.Objects.Order order) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.placementDate = order.getPlacementDate();
        this.dueDate = order.getDuedate();
        this.items = new HashMap<>();
        for (Map.Entry<Integer, DataAccessLayer.SuppliersModule.Objects.Item> items :
                order.getItems().entrySet()) {
            this.items.put(items.getKey(), new ItemDTO(items.getValue()));
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Map<Integer, ItemDTO> getItems() {
        return items;
    }

    public Map<Integer, Integer> getAmounts() {
        return amounts;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder output =
                new StringBuilder("Order id: " + id + "\n"
                        + "Status: " + status + "\n"
                        + "Placement date: " + placementDate + "\n"
                        + "Order date: " + dueDate + "\n"
                        + "Items: " + "\n");

        for (Map.Entry<Integer, ItemDTO> item
                : items.entrySet()) {
            output.append(item.getValue().toString()).append("\n");
        }
        return output.toString();
    }

    public List<ShippedItemDTO> getItemsForShipping() {
        List<ShippedItemDTO> returnedItems = new LinkedList<>();
        for (Integer itemID : items.keySet()) {
            returnedItems.add(new ShippedItemDTO(
                    items.get(itemID).getName(),
                    amounts.get(itemID),
                    items.get(itemID).getWeight()
            ));
        }
        return returnedItems;
    }

}
