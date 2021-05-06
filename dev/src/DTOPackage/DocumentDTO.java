package DTOPackage;

import BusinessLayer.ShipmentsModule.Objects.Document;
import BusinessLayer.ShipmentsModule.Objects.Item;

import java.util.LinkedList;
import java.util.List;

public class DocumentDTO {
    private int trackingNumber;
    private List<ItemDTO> products;
    private LocationDTO destination;
    private double weight;

    public DocumentDTO(Document d) {
        trackingNumber = d.getTrackingNumber();
        products = new LinkedList<>();
        for (Item i : d.getProducts()) {
            ItemDTO item = new ItemDTO(i.getName(), i.getAmount(), i.getWeight());
            item.setDocumentId(i.getDocumentId());
            products.add(item);
        }
        destination = new LocationDTO(d.getDestination());
        weight = d.getWeight();
    }

    public DocumentDTO(int trackingNumber, List<ItemDTO> products, LocationDTO destination) {
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

    public LocationDTO getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }
}