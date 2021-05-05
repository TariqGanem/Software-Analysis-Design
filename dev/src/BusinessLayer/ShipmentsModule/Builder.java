package BusinessLayer.ShipmentsModule;

import BusinessLayer.ShipmentsModule.Objects.*;
import DTOPackage.*;


public class Builder {

    public Document build(DocumentDTO doc) {
        return new Document(doc.getTrackingNumber(), null, doc.getDestination());
    }

    public Truck build(TruckDTO truck) {
        return new Truck(truck.getTruckPlateNumber(), truck.getModel(), truck.getNatoWeight(), truck.getMaxWeight());
    }

    public Location build(LocationDTO loc) {
        return new Location(loc.getAddress(), loc.getPhoneNumber(), loc.getContactName());

    }

    public Shipment build(ShipmentDTO shipment) {
        return new Shipment(shipment.getDate(), shipment.getDepartureHour(), shipment.getTruckPlateNumber(),
                shipment.getDriverId(), null, build(shipment.getSource()));
    }

    public Driver build(DriverDTO driver) {
        return new Driver(driver.getId(), driver.getName(), driver.getAllowedWeight());
    }

    public Item build(ItemDTO item) {
        return new Item(item.getDocumentId(), item.getName(), item.getAmount(), item.getWeight());
    }


}
