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
        return new Truck(truck.getTruckPlateNumber(), truck.getModel(), truck.getNatoWeight(), truck.getMaxWeight(), truck.isAvailable());
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
        return s;
    }

    public static ShipmentDTO buildDTO(Shipment shipment) {
        ShipmentDTO s = new ShipmentDTO(shipment.getShipmentId(), shipment.getDate(), shipment.getDepartureHour(),
                shipment.getTruckPlateNumber(), shipment.getDriverId(), buildDTO(shipment.getSource()));
        for (Document doc : shipment.getDocuments().values()) {
            ;
            s.addDocument(doc.getTrackingNumber(), buildItemsListDTO(doc.getProducts()), buildDTO(doc.getDestination()));
        }
        return s;
    }

    public static Driver build(DriverDTO driver) {
        return new Driver(driver.getId(), driver.getName(), driver.getAllowedWeight());
    }

    public static DriverDTO buildDTO(Driver driver) {
        return new DriverDTO(driver.getId(), driver.getName(), driver.getAllowedWeight());
    }

    public static Item build(ItemDTO item) {
        return new Item(item.getDocumentId(), item.getName(), item.getAmount(), item.getWeight());
    }


    public static List<Truck> buildTrucksList(List<TruckDTO> trucksDTO) {
        List<Truck> trucks = new LinkedList<>();
        trucksDTO.forEach(t -> trucks.add(new Truck(t.getTruckPlateNumber(), t.getModel(), t.getNatoWeight(), t.getMaxWeight(), t.isAvailable())));
        return trucks;
    }

    public static List<TruckDTO> buildTrucksListDTO(List<Truck> trucks) {
        List<TruckDTO> trucksDTO = new LinkedList<>();
        trucks.forEach(t -> trucksDTO.add(new TruckDTO(t.getTruckPlateNumber(), t.getModel(), t.getNatoWeight(), t.getMaxWeight(), t.isAvailable())));
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
        for (ShipmentDTO s : shipmentsDTO) {
            Shipment shipment = new Shipment(s.getShipmentId(),
                    s.getDate(), s.getDepartureHour(), s.getTruckPlateNumber(), s.getDriverId(),
                    build(s.getSource()));
            for (DocumentDTO doc : s.getDocuments().values()) {
                shipment.addDocument(Builder.buildItemsList(doc.getProducts()), Builder.build(doc.getDestination()), doc.getTrackingNumber());
            }
            shipments.add(shipment);
        }
//        shipmentsDTO.forEach(s -> shipments.add(new Shipment(s.getShipmentId(),
//                s.getDate(), s.getDepartureHour(), s.getTruckPlateNumber(), s.getDriverId(),
//                build(s.getSource()))));
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
            shipmentsDTO.add(shipment);
        }
        return shipmentsDTO;
    }

    public static List<LocationDTO> buildLocationsListDTO(List<Location> locations) {
        List<LocationDTO> locationDTOList = new LinkedList<>();
        locations.forEach(l -> locationDTOList.add(buildDTO(l)));
        return locationDTOList;
    }
}
