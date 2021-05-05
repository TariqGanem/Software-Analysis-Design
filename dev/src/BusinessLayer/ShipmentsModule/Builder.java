package BusinessLayer.ShipmentsModule;

import BusinessLayer.ShipmentsModule.Objects.*;
import DTOPackage.*;


public class Builder {

    public static Document build(DocumentDTO doc) {
        return new Document(doc.getTrackingNumber(), null, doc.getDestination()); // TODO
    }

    public static Truck build(TruckDTO truck) {
        return new Truck(truck.getTruckPlateNumber(), truck.getModel(), truck.getNatoWeight(), truck.getMaxWeight());
    }

    public static Location build(LocationDTO loc) {
        return new Location(loc.getId(), loc.getAddress(), loc.getPhoneNumber(), loc.getContactName());

    }

    public static Shipment build(ShipmentDTO shipment) {
        return new Shipment(shipment.getDate(), shipment.getDepartureHour(), shipment.getTruckPlateNumber(),
                shipment.getDriverId(), null, build(shipment.getSource()));
    }

    public static Driver build(DriverDTO driver) {
        return new Driver(driver.getId(), driver.getName(), driver.getAllowedWeight());
    }

    public static Item build(ItemDTO item) {
        return new Item(item.getDocumentId(), item.getName(), item.getAmount(), item.getWeight());
    }


}
