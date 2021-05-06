package DTOPackage;

import BusinessLayer.ShipmentsModule.Objects.Document;
import BusinessLayer.ShipmentsModule.Objects.Item;
import BusinessLayer.ShipmentsModule.Objects.Shipment;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShipmentDTO {
    private int shipmentId;
    private Date date;
    private String departureHour;
    private String truckPlateNumber;
    private String driverId;
    private Map<Integer, DocumentDTO> documents;
    private LocationDTO source;

    public ShipmentDTO(Shipment s) {
        this.shipmentId = s.getShipmentId();
        this.date = s.getDate();
        this.departureHour = s.getDepartureHour();
        this.truckPlateNumber = s.getTruckPlateNumber();
        this.driverId = s.getDriverId();
        this.source = new LocationDTO(s.getSource());
        for (Integer key : s.getDocuments().keySet()) {
            Document d = s.getDocuments().get(key);
            List<ItemDTO> items = new LinkedList<>();
            for (Item i : d.getProducts()) {
                ItemDTO item = new ItemDTO(i.getName(), i.getAmount(), i.getWeight());
                item.setDocumentId(d.getTrackingNumber());
                items.add(item);
            }
            this.documents.put(key, new DocumentDTO(d.getTrackingNumber(),
                    items, new LocationDTO(d.getDestination())));
        }
    }

    public ShipmentDTO(int shipmentId, Date date, String departureHour, String truckPlateNumber, String driverId, LocationDTO source) {
        this.shipmentId = shipmentId;
        this.date = date;
        this.departureHour = departureHour;
        this.truckPlateNumber = truckPlateNumber;
        this.driverId = driverId;
        this.source = source;
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

    public void addDocument(int trackingNumber, List<ItemDTO> products, LocationDTO dest) {
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
}