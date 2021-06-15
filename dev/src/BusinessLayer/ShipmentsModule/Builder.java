package BusinessLayer.ShipmentsModule;

import BusinessLayer.ShipmentsModule.Objects.*;
import DTOPackage.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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

    public static LocationDTO buildDTO(Location loc) {
        return new LocationDTO(loc.getId(), loc.getAddress(), loc.getPhoneNumber(), loc.getContactName());

    }

    public static Shipment build(ShipmentDTO shipment) {
        Shipment s = new Shipment(shipment.getShipmentId(), shipment.getDate(), shipment.getDepartureHour(),
                shipment.getTruckPlateNumber(), shipment.getDriverId(), build(shipment.getSource()));
        for (DocumentDTO doc : shipment.getDocuments().values()) {
            s.addDocument(Builder.buildItemsList(doc.getProducts()), build(doc.getDestination()), doc.getTrackingNumber());
        }
        s.setApproved(shipment.isApproved());
        s.setDelivered(shipment.isDelivered());
        return s;
    }

    public static ShipmentDTO buildDTO(Shipment shipment) {
        ShipmentDTO s = new ShipmentDTO(shipment.getShipmentId(), shipment.getDate(), shipment.getDepartureHour(),
                shipment.getTruckPlateNumber(), shipment.getDriverId(), buildDTO(shipment.getSource()));
        for (Document doc : shipment.getDocuments().values()) {
            s.addDocument(doc.getTrackingNumber(), buildItemsListDTO(doc.getProducts()), buildDTO(doc.getDestination()));
        }
        s.setApproved(shipment.isApproved());
        s.setDelivered(shipment.isDelivered());
        return s;
    }

    public static Driver build(DriverDTO driver) {
        return new Driver(driver.getId(), driver.getName(), driver.getAllowedWeight());
    }

    public static DriverDTO buildDTO(Driver driver) {
        return new DriverDTO(driver.getId(), driver.getName(), driver.getAllowedWeight());
    }

    public static Item build(ShippedItemDTO item) {
        Item itemBO = new Item(item.getName(), item.getAmount(), item.getWeight());
        itemBO.setDocumentId(item.getDocumentId());
        return itemBO;
    }


    public static List<Truck> buildTrucksList(List<TruckDTO> trucksDTO) {
        List<Truck> trucks = new LinkedList<>();
        trucksDTO.forEach(t -> trucks.add(new Truck(t.getTruckPlateNumber(), t.getModel(), t.getNatoWeight(), t.getMaxWeight())));
        return trucks;
    }

    public static List<TruckDTO> buildTrucksListDTO(List<Truck> trucks) {
        List<TruckDTO> trucksDTO = new LinkedList<>();
        trucks.forEach(t -> trucksDTO.add(new TruckDTO(t.getTruckPlateNumber(), t.getModel(), t.getNatoWeight(), t.getMaxWeight())));
        return trucksDTO;
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

    public static List<Item> buildItemsList(List<ShippedItemDTO> productsDTO) {
        List<Item> items = new LinkedList<>();
        productsDTO.forEach(p -> items.add(new Item(p.getName(), p.getAmount(), p.getWeight())));
        return items;
    }

    public static List<ShippedItemDTO> buildItemsListDTO(List<Item> products) {
        List<ShippedItemDTO> items = new LinkedList<>();
        products.forEach(p -> items.add(new ShippedItemDTO(p.getName(), p.getAmount(), p.getWeight())));
        return items;
    }

    public static List<Shipment> buildShipmentsList(List<ShipmentDTO> shipmentsDTO) {
        List<Shipment> shipments = new LinkedList<>();
        for (ShipmentDTO s : shipmentsDTO) {
            Shipment shipment = new Shipment(s.getShipmentId(),
                    s.getDate(), s.getDepartureHour(), s.getTruckPlateNumber(), s.getDriverId(),
                    build(s.getSource()));
            for (DocumentDTO doc : s.getDocuments().values()) {
                shipment.addDocument(Builder.buildItemsList(doc.getProducts()), Builder.build(doc.getDestination()), doc.getTrackingNumber());
            }
            shipment.setApproved(s.isApproved());
            shipment.setDelivered(s.isDelivered());
            shipments.add(shipment);
        }
        return shipments;
    }

    public static List<DriverDTO> buildDriversListDTO(List<Driver> drivers) {
        List<DriverDTO> driversDTO = new LinkedList<>();
        drivers.forEach(d -> driversDTO.add(new DriverDTO(d.getId(), d.getName(), d.getAllowedWeight())));
        return driversDTO;
    }

    public static List<ShipmentDTO> buildShipmentsListDTO(List<Shipment> shipments) {
        List<ShipmentDTO> shipmentsDTO = new LinkedList<>();
        for (Shipment s : shipments) {
            ShipmentDTO shipment = new ShipmentDTO(s.getShipmentId(),
                    s.getDate(), s.getDepartureHour(), s.getTruckPlateNumber(), s.getDriverId(),
                    buildDTO(s.getSource()));
            for (Document doc : s.getDocuments().values()) {
                shipment.addDocument(doc.getTrackingNumber(), buildItemsListDTO(doc.getProducts()), buildDTO(doc.getDestination()));
            }
            shipment.setApproved(s.isApproved());
            shipment.setDelivered(s.isDelivered());
            shipmentsDTO.add(shipment);
        }
        return shipmentsDTO;
    }

    public static List<LocationDTO> buildLocationsListDTO(List<Location> locations) {
        List<LocationDTO> locationDTOList = new LinkedList<>();
        locations.forEach(l -> locationDTOList.add(buildDTO(l)));
        return locationDTOList;
    }

    public static Map<Integer, List<Item>> buildItemsPerDestinations(Map<Integer, List<ShippedItemDTO>> itemsDTO) {
        Map<Integer, List<Item>> items = new HashMap<>();
        for (Integer i : itemsDTO.keySet()) {
            items.put(i, buildItemsList(itemsDTO.get(i)));
        }
        return items;
    }
}
