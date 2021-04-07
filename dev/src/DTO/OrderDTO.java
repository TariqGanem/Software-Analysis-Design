package DTO;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import Business_Layer.Objects.Item;
import Business_Layer.Objects.Order;

public class OrderDTO {
    private final int id;
    private final Status status;
    private final LocalDate placementDate;
    private final LocalDate orderDate;
    private final Map<Integer, ItemDTO> items;


    public OrderDTO(Order order){
        id = order.getId();
        status = order.getStatus();
        placementDate = order.getPlacementDate();
        orderDate = order.getOrderDate();
        items = new HashMap<>();
        for (Map.Entry<Integer, Item> items:
             order.getItems().entrySet()) {
            this.items.put(items.getKey(), new ItemDTO(items.getValue()));
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

    public Map<Integer, ItemDTO> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder output =
                new StringBuilder("Order id: " + id + "\n"
                        + "Status: " + status + "\n"
                        + "Placement date: " + placementDate + "\n"
                        + "Order date: " + orderDate + "\n"
                        + "Items: " + "\n");

        for (Map.Entry<Integer, ItemDTO> item
                : items.entrySet()) {
            output.append(item.getValue().toString()).append("\n");
        }
        return output.toString();
    }

}
