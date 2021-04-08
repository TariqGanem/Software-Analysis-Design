package DTOs;

import BusinessLayer.Objects.Document;
import BusinessLayer.Objects.Location;
import javafx.util.Pair;

import java.util.Map;

public class DocumentDTO {
    private int trackingNumber;
    private Map<String, Pair<Double, Integer>> products;
    private Location destination;

    public DocumentDTO(Document d) {
        trackingNumber = d.getTrackingNumber();
        products = d.getProducts();
        destination = d.getDestination();
    }
}