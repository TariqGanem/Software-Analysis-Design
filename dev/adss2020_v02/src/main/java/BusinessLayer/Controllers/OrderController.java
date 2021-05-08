package BusinessLayer.Controllers;

import BusinessLayer.Objects.FixedOrder;
import BusinessLayer.Objects.Item;
import BusinessLayer.Objects.Order;
import BusinessLayer.Objects.SingleOrder;
import DTO.ItemDTO;
import DTO.OrderDTO;
import DataAccessLayer.Mappers.OrdersMapper;
import Enums.Status;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderController {
    private static OrderController instance = null;
    private OrdersMapper mapper;

    Map<Integer, Order> inPrepareOrders;
    Map<Integer, Order> activeOrders;
    Map<Integer, Order> archivedOrders;
    Map<Integer, Integer> order_Vs_supplier;
   // Map<Integer, Integer> supplier_Vs_FixedOrder;
    private OrderController(){
        activeOrders = new HashMap<>();
        archivedOrders = new HashMap<>();
        inPrepareOrders = new HashMap<>();
        order_Vs_supplier = new HashMap<>();
    //    supplier_Vs_FixedOrder = new HashMap<>();
        mapper = new OrdersMapper();
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
    public void completeAnOrder(int orderID) throws Exception {
        if (activeOrders.containsKey(orderID)){
            Order order = activeOrders.remove(orderID);
            order.setNewStatus(Status.Completed);
            archive(order);
        }
        else throw new Exception("Cannot complete non active order!");
    }

    /**
     * @param orderID id of order to be canceled
     */
    public void cancelAnOrder(int orderID) throws Exception {
        if (activeOrders.containsKey(orderID)){
            Order order = activeOrders.remove(orderID);
            order.setNewStatus(Status.Canceled);
            archive(order);
        }
        else throw new Exception("Cannot cancel non active order!");
    }

    /**
     * @param orderID id of order to be rescheduled
     * @param newDate new date to be update for the order
     */
    public void rescheduleAnOrder(int orderID, LocalDate newDate) throws Exception {

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
        } else throw new NullPointerException();
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
     * @param supplierID id of supplier to ask fixed order from
     * @param dueDate due-date of the order
     * @return fixed order object
     */
    public FixedOrder openFixedOrder(int supplierID, LocalDate dueDate) {
        FixedOrder fixedOrder = new FixedOrder(mapper.getNewOrderID(), Status.inPrepared, LocalDate.now(), dueDate);
        inPrepareOrders.put(fixedOrder.getId(), fixedOrder);
        order_Vs_supplier.put(fixedOrder.getId(), supplierID);
        return fixedOrder;
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

    /**
     * @param orderID id of order to check
     * @return true if the order with the given id exists, otherwise it returns false.
     */
    public boolean isOrder(int orderID) {
        return order_Vs_supplier.containsKey(orderID);
    }

    /**
     * @param supplierID id of supplier to ask order from
     * @param dueDate due-date of the order
     * @return single order object
     */
    public SingleOrder openSingleOrder(int supplierID, LocalDate dueDate) {
        SingleOrder singleOrder = new SingleOrder(mapper.getNewOrderID(), Status.inPrepared, LocalDate.now(), dueDate);
        inPrepareOrders.put(singleOrder.getId(), singleOrder);
        order_Vs_supplier.put(singleOrder.getId(), supplierID);
        return singleOrder;
    }
}
