package DTOs;

import BusinessLayer.Objects.Document;
import BusinessLayer.Objects.Location;

import java.util.List;
import java.util.Map;

public class DocumentDTO {

    private int trackingNumber;
    private Map<String, List<Double>> products;
    private Location destination;

    public DocumentDTO(Document d) {
        trackingNumber = d.getTrackingNumber();
        products = d.getProducts();
        destination = d.getDestination();
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public Map<String, List<Double>> getProducts() {
        return products;
    }

    public Location getDestination() {
        return destination;
    }

}