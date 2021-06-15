package DTOPackage;

import java.util.*;

public class ShipmentDTO {
    private int shipmentId;
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private Map<Integer, DocumentDTO> documents;
    private LocationDTO source;
    private boolean approved;
    private boolean delivered;

    public ShipmentDTO(int shipmentId, Date date, String departureHour, String truckPlateNumber, String driverId, LocationDTO source) {
        this.shipmentId = shipmentId;
        this.date = date;
        this.departureHour = departureHour;
        this.truckPlateNumber = truckPlateNumber;
        this.driverId = driverId;
        this.source = source;
        documents = new HashMap<>();
        approved = false;
        delivered = false;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public Date getDate() {
        return date;
    }

    public String getDepartureHour() {
        return departureHour;
    }

    public String getTruckPlateNumber() {
        return truckPlateNumber;
    }

    public String getDriverId() {
        return driverId;
    }

    public void addDocument(int trackingNumber, List<ShippedItemDTO> products, LocationDTO dest) {
        DocumentDTO d = new DocumentDTO(trackingNumber, products, dest);
        documents.put(trackingNumber, d);
    }

    public double getShipmentWeight() {
        int shipmentWeight = 0;
        for (DocumentDTO doc : documents.values()) {
            shipmentWeight += doc.getWeight();
        }
        return shipmentWeight;
    }

    public LocationDTO getSource() {
        return source;
    }

    public Map<Integer, DocumentDTO> getDocuments() {
        return documents;
    }

    public List<LocationDTO> getDestinations() {
        List<LocationDTO> locationDTOList = new LinkedList<>();
        documents.values().forEach(d -> locationDTOList.add(d.getDestination()));
        return locationDTOList;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isDelivered() {
        return delivered;
    }
}