package APIs;

import BusinessLayer.ResponseT;
import DTOPackage.OrderDTO;
import DTOPackage.ShippedItemDTO;
import PresentationLayer.ShipmentsMenu.Menu;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipmentsSuppliersAPI {

    public ShipmentsSuppliersAPI() {

    }

    public void scheduleShipment(String manufacture, String supp_phone, String contactName, OrderDTO order) throws Exception {
        int store_loc_id = addStoreLocation();

        int supp_loc_id = addSupplierLocation(manufacture, supp_phone, contactName);

        Map<Integer, List<ShippedItemDTO>> items = new HashMap<>();
        items.put(store_loc_id, order.getItemsForShipping());


        Menu.getInstance().getFacade().arrangeDelivery(Date.from(order.getDueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()), supp_loc_id, items);
    }

    private int addStoreLocation() throws Exception {
        ResponseT<Integer> res = Menu.getInstance().getFacade().addLocation("SuperLee Store", "9999999", "RAMI");
        if (res.getErrorOccurred())
            throw new Exception(res.getErrorMessage());
        return res.getValue();
    }

    private int addSupplierLocation(String manufacture, String phone, String contactName) throws Exception {
        ResponseT<Integer> res = Menu.getInstance().getFacade().addLocation(manufacture, phone, contactName);
        if (res.getErrorOccurred())
            throw new Exception(res.getErrorMessage());
        return res.getValue();
    }
}
