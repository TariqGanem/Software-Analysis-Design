package DTOPackage;

import BusinessLayer.ShipmentsModule.Objects.Document;
import BusinessLayer.ShipmentsModule.Objects.Item;
import BusinessLayer.ShipmentsModule.Objects.Location;

import java.util.LinkedList;
import java.util.List;

public class DocumentDTO {
    private int trackingNumber;
    private List<ItemDTO> products;
    private Location destination;
    private double weight;

    public DocumentDTO(Document d) {
        trackingNumber = d.getTrackingNumber();
        products = new LinkedList<>();
        for (Item i : d.getProducts()) {
            products.add(new ItemDTO(i.getDocumentId(), i.getName(), i.getAmount(), i.getWeight()));
        }
        destination = d.getDestination();
        weight = d.getWeight();
    }

    public DocumentDTO(int trackingNumber, List<ItemDTO> products, Location destination, double weight) {
        this.trackingNumber = trackingNumber;
        this.products = products;
        this.destination = destination;
        this.weight = weight;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public List<ItemDTO> getProducts() {
        return products;
    }

    public Location getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }
}