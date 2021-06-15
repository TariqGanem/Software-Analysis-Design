package DataAccessLayer.SuppliersModule.Objects;

import DTOPackage.ItemDTO;
import DTOPackage.OrderDTO;
import Resources.Status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int id;
    private Status status;
    private LocalDate placementDate;
    private LocalDate duedate;
    private Map<Integer, Item> items;
    private Map<Integer, Integer> amounts;


    public Order(OrderDTO orderDTO) {
        this.id = orderDTO.getId();
        this.status = orderDTO.getStatus();
        this.placementDate = orderDTO.getPlacementDate();
        this.duedate = orderDTO.getDueDate();
        this.items = new HashMap<>();
        for (Map.Entry<Integer, ItemDTO> item : orderDTO.getItems().entrySet()) {
            this.items.put(item.getKey(), new Item(item.getValue()));
        }
        this.amounts = new HashMap<>();
        for (Map.Entry<Integer, Integer> amounts :
                orderDTO.getAmounts().entrySet()) {
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

    public LocalDate getDuedate() {
        return duedate;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public Map<Integer, Integer> getAmounts() {
        return amounts;
    }
}
