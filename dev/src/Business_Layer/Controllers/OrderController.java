package Business_Layer.Controllers;

import Business_Layer.Objects.Order;
import DTO.OrderDTO;
import enums.Status;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderController {
    private static OrderController instance = null;

    Map<Integer, Order> activeOrders;
    Map<Integer, Order> archivedOrders;

    private OrderController(){
        activeOrders = new HashMap<>();
        archivedOrders = new HashMap<>();
    }

    public static OrderController getInstance(){
        if (instance == null){
            instance = new OrderController();
        }
        return instance;
    }


    // return type of functions may differ in coming development's levels
    public void placeAnOrder(OrderDTO order){
        this.activeOrders.put(order.getId(),new Order(order));
    }

    public void completeAnOrder(int orderID){
        if (activeOrders.containsKey(orderID)){
            Order order = activeOrders.remove(orderID);
            order.setNewStatus(Status.Completed);
            archive(order);
        }
    }
    public void cancelAnOrder(int orderID){
        if (activeOrders.containsKey(orderID)){
            Order order = activeOrders.remove(orderID);
            order.setNewStatus(Status.Canceled);
            archive(order);
        }

    }

    public void rescheduleAnOrder(int orderID, LocalDate newDate){

        if (activeOrders.containsKey(orderID)){
            Order order = activeOrders.remove(orderID);
            if (newDate.isAfter(order.getOrderDate()))
                order.setNewDate(newDate);
            else
                throw new DateTimeException("date should be more later than: "+ order.getOrderDate());

        }
    }



    private void archive(Order order) {
        if (order != null) {
            archivedOrders.put(order.getId(), order);
        }
    }

}
