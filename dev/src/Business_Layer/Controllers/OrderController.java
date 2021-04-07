package Business_Layer.Controllers;

import Business_Layer.Objects.Order;
import DTO.OrderDTO;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class OrderController {
    private static OrderController instance = null;
    Queue<Order> activeOrders;
    Queue<Order> archivedOrders;

    private OrderController(){
        activeOrders = new PriorityQueue<>();
        archivedOrders = new PriorityQueue<>();
    }

    public static OrderController getInstance(){
        if (instance == null){
            instance = new OrderController();
        }
        return instance;
    }


    // return type of functions may differ in coming development's levels
    public void placeAnOrder(OrderDTO order){
        this.activeOrders.add(new Order(order));
    }

    public void completeAnOrder(OrderDTO order){}
    public void cancelAnOrder(OrderDTO order){


    }

    public void editAnOrder(OrderDTO order){}

    public void rescheduleAnOrder(OrderDTO order){}

    public void scheduleAnOrder(OrderDTO order){}

}
