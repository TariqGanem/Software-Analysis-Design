package DTOPackage;

import java.util.List;

public class DocumentDTO {
    private int trackingNumber;
    private List<ItemDTO> products;
    private LocationDTO destination;

    public DocumentDTO(int trackingNumber, List<ItemDTO> products, LocationDTO destination) {
        this.trackingNumber = trackingNumber;
        this.products = products;
        this.destination = destination;
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
        double weight = 0;
        for (ItemDTO item : products) {
            weight += item.getWeight() * item.getAmount();
        }
        return weight;
    }
}