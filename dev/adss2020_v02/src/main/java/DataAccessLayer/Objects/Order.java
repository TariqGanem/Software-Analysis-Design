package DataAccessLayer.Objects;

import DTO.ItemDTO;
import DTO.OrderDTO;
import Enums.Status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int id;
    private Status status;
    private LocalDate placementDate;
    private LocalDate duedate;
    private Map<Integer, Item> items;




    public Order (OrderDTO orderDTO){
        this.id = orderDTO.getId();
        this.status = orderDTO.getStatus();
        this.placementDate = orderDTO.getPlacementDate();
        this.duedate = orderDTO.getDueDate();
        this.items = new HashMap<>();
        for (Map.Entry<Integer, ItemDTO> item: orderDTO.getItems().entrySet()) {
            this.items.put(item.getKey(), new Item(item.getValue()));
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
}
