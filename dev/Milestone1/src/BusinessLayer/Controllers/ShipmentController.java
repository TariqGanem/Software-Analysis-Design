package BusinessLayer.Controllers;

import BusinessLayer.Objects.Shipment;

import java.util.LinkedList;
import java.util.List;

public class ShipmentController {
    private List<Shipment> data;

    public ShipmentController() {
        data = new LinkedList<>();
    }
}