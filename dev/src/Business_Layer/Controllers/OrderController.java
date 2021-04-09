package Business_Layer.Controllers;

import Business_Layer.Objects.Item;
import Business_Layer.Objects.Order;
import DTO.ItemDTO;
import DTO.OrderDTO;
import enums.Status;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderController {
    private static OrderController instance = null;

    Map<Integer, Order> inPrepareOrders;
    Map<Integer, Order> activeOrders;
    Map<Integer, Order> archivedOrders;
    Map<Integer, Integer> order_Vs_supplier;

    private OrderController(){
        activeOrders = new HashMap<>();
        archivedOrders = new HashMap<>();
        inPrepareOrders = new HashMap<>();
        order_Vs_supplier = new HashMap<>();
    }

    public static OrderController getInstance(){
        if (instance == null){
            instance = new OrderController();
        }
        return instance;
    }


    /**
     * @param orderID id of order to be placed/submitted to progress
     * @throws Exception order cannot be submitted/placed before preparing
     */
    public void placeAnOrder(int orderID) throws Exception {
        if (inPrepareOrders.containsKey(orderID)){
            Order orderToPlace = inPrepareOrders.remove(orderID);
            orderToPlace.setNewStatus(Status.Active);
            activeOrders.put(orderToPlace.getId(), orderToPlace);
        }
        else {
            throw new Exception("Order is not prepared!");
        }
    }

    /**
     * @param orderID id of order to be completed
     */
    public void completeAnOrder(int orderID){
        if (activeOrders.containsKey(orderID)){
            Order order = activeOrders.remove(orderID);
            order.setNewStatus(Status.Completed);
            archive(order);
        }
    }

    /**
     * @param orderID id of order to be canceled
     */
    public void cancelAnOrder(int orderID){
        if (activeOrders.containsKey(orderID)){
            Order order = activeOrders.remove(orderID);
            order.setNewStatus(Status.Canceled);
            archive(order);
        }

    }

    /**
     * @param orderID id of order to be rescheduled
     * @param newDate new date to be update for the order
     */
    public void rescheduleAnOrder(int orderID, LocalDate newDate){

        if (activeOrders.containsKey(orderID)){
            Order order = activeOrders.remove(orderID);
            if (newDate.isAfter(order.getOrderDate()))
                order.setNewDate(newDate);
            else
                throw new DateTimeException("date should be more later than: "+ order.getOrderDate());

        }
    }


    /**
     * @param order order to archived
     */
    private void archive(Order order) {
        if (order != null) {
            archivedOrders.put(order.getId(), order);
        }
    }

    /**
     * @param orderID id of order to remove item from
     * @param itemID id of item to be removed from order
     * @throws Exception cannot remove item from this order
     */
    public void removeItemFromOrder(int orderID, int itemID) throws Exception {
        if (activeOrders.containsKey(orderID)){
            activeOrders.get(orderID).removeItem(itemID);
        }
        else if(archivedOrders.containsKey(orderID))
            throw new Exception("Order is not active!");
        else
            throw new NullPointerException("Order does not exist!");
    }

    /**
     * @param orderID id of order to add item to
     * @param item item to be added to the order
     * @throws Exception cannot add item to this order
     */
    public void addItemToOrder(int orderID, Item item) throws Exception {
        if (activeOrders.containsKey(orderID)){
            activeOrders.get(orderID).addItem(item);
        }
        else if(archivedOrders.containsKey(orderID))
            throw new Exception("Order is not active!");
        else
            throw new NullPointerException("Order does not exist!");
    }

    /**
     * @param orderID id of order to get
     * @return order by its id
     */
    public Order getOrder(int orderID) {
        if (activeOrders.containsKey(orderID)){
            return activeOrders.get(orderID);
        }
        else if(archivedOrders.containsKey(orderID))
            return archivedOrders.get(orderID);
        else
            throw new NullPointerException("Order does not exist!");
    }

    /**
     * @param orderID id of order to edit one of its items
     * @param item edited item to updated
     * @throws Exception cannot update item in this order
     */
    public void editItemInOrder(int orderID, Item item) throws Exception {
        if (activeOrders.containsKey(orderID)) {
            if (activeOrders.get(orderID).getItems().containsKey(item.getId())) {
                activeOrders.get(orderID).getItems().get(item.getId()).Replace(item);
            }

            else{
                throw new Exception("Item does not exist in the order!");
            }
        }
        else if(archivedOrders.containsKey(orderID)) {
            throw new Exception("Order is not active!");
        }
       else {
           throw new Exception("Order does not exist!");
        }

    }


    /**
     * @param supplierID id of supplier to ask for order
     * @param newOrder new order id
     * @param dueDate due date of the order
     */
    public void openOrder(int supplierID, int newOrder,LocalDate dueDate) {
        inPrepareOrders.put(newOrder, new Order(newOrder, Status.inPrepared, dueDate));
        order_Vs_supplier.put(newOrder, supplierID);
    }

    /**
     * @param orderID id of order to get its supplier
     * @return id of supplier of the order
     * @throws Exception order does not exist
     */
    public Integer getSupplier(int orderID) throws Exception {
        if (order_Vs_supplier.containsKey(orderID))
            return order_Vs_supplier.get(orderID);
        else
            throw new Exception("Order does not exist!");
    }

    public boolean isOrder(int orderID) {
        return order_Vs_supplier.containsKey(orderID);
    }
}
