package BusinessLayer.ShipmentsModule;

import BusinessLayer.ShipmentsModule.Objects.*;
import DTOPackage.*;

import java.util.LinkedList;
import java.util.List;


public class Builder {

    public static Document build(DocumentDTO doc) {
        return new Document(doc.getTrackingNumber(), buildItemsList(doc.getProducts()), build(doc.getDestination()));
    }


    public static Truck build(TruckDTO truck) {
        return new Truck(truck.getTruckPlateNumber(), truck.getModel(), truck.getNatoWeight(), truck.getMaxWeight());
    }

    public static Location build(LocationDTO loc) {
        return new Location(loc.getId(), loc.getAddress(), loc.getPhoneNumber(), loc.getContactName());

    }

    public static Shipment build(ShipmentDTO shipment) {
        return new Shipment(shipment.getShipmentId(), shipment.getDate(), shipment.getDepartureHour(), shipment.getTruckPlateNumber(),
                shipment.getDriverId(), build(shipment.getSource()));
    }

    public static Driver build(DriverDTO driver) {
        return new Driver(driver.getId(), driver.getName(), driver.getAllowedWeight());
    }

    public static Item build(ItemDTO item) {
        return new Item(item.getDocumentId(), item.getName(), item.getAmount(), item.getWeight());
    }


    public static List<Truck> buildTrucksList(List<TruckDTO> trucksDTO) {
        List<Truck> trucks = new LinkedList<>();
        trucksDTO.forEach(t -> trucks.add(new Truck(t.getTruckPlateNumber(), t.getModel(), t.getNatoWeight(), t.getMaxWeight())));
        return trucks;
    }

    public static List<Driver> buildDriversList(List<DriverDTO> driversDTO) {
        List<Driver> drivers = new LinkedList<>();
        driversDTO.forEach(d -> drivers.add(new Driver(d.getId(), d.getName(), d.getAllowedWeight())));
        return drivers;
    }

    public static List<Location> buildLocationsList(List<LocationDTO> locationsDTO) {
        List<Location> locations = new LinkedList<>();
        locationsDTO.forEach(l -> locations.add(new Location(l.getId(), l.getAddress(), l.getPhoneNumber(), l.getContactName())));
        return locations;
    }

    public static List<Item> buildItemsList(List<ItemDTO> productsDTO) {
        List<Item> items = new LinkedList<>();
        productsDTO.forEach(p -> items.add(new Item(p.getDocumentId(), p.getName(), p.getAmount(), p.getWeight())));
        return items;
    }

    public static List<ItemDTO> buildItemsListDTO(List<Item> products) {
        List<ItemDTO> items = new LinkedList<>();
        products.forEach(p -> items.add(new ItemDTO(p.getName(), p.getAmount(), p.getWeight())));
        return items;
    }

    public static List<Shipment> buildShipmentsList(List<ShipmentDTO> shipmentsDTO) {
        List<Shipment> shipments = new LinkedList<>();
        shipmentsDTO.forEach(s -> shipments.add(new Shipment(s.getShipmentId(),
                s.getDate(), s.getDepartureHour(), s.getTruckPlateNumber(), s.getDriverId(),
                build(s.getSource()))));
        return shipments;
    }
}
