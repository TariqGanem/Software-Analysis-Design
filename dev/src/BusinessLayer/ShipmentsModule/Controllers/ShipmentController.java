package BusinessLayer.ShipmentsModule.Controllers;

import BusinessLayer.ShipmentsModule.Builder;
import BusinessLayer.ShipmentsModule.Objects.Item;
import BusinessLayer.ShipmentsModule.Objects.Shipment;
import BusinessLayer.StoreModule.Objects.ItemSpecs;
import DTOPackage.ShipmentDTO;
import DataAccessLayer.ShipmentsModule.Mappers.DocumentMapper;
import DataAccessLayer.ShipmentsModule.Mappers.ShipmentMapper;

import java.util.Date;
import java.util.List;

public class ShipmentController {
    private ShipmentMapper mapper;
    private DocumentMapper docMapper;
    private int trackingNumber;
    private int shipmentId;

    public ShipmentController() {
        mapper = ShipmentMapper.getInstance();
        docMapper = DocumentMapper.getInstance();
        trackingNumber = docMapper.getMaxID() + 1;
        shipmentId = mapper.getMaxID() + 1;
    }

    /***
     * Searching for a specific shipment in the system
     * @return the wanted shipment
     * @throws Exception in case of invalid parameters
     */
    public Shipment getShipment(Date date, String departureHour, String driverId) throws Exception {
        return Builder.build(mapper.getShipment(date, departureHour, driverId));
    }

    /**
     * Adding a shipment to the system
     *
     * @param date             - Date of the shipment to be transported
     * @param departureHour    - The exact hour for the transportation of the shipment
     * @param truckPlateNumber - The truck's id which transports the delivery
     * @param driverId         - The driver's id which transports the delivery
     * @param sourceId         - The location which the delivery will start on
     * @throws Exception in case of wrong parameters values
     */
    public int addShipment(Date date, String departureHour, String truckPlateNumber, String driverId, int sourceId) throws Exception {
        if (departureHour == null || departureHour.trim().isEmpty())
            throw new Exception("Couldn't add new shipment - Invalid parameters");
        int shipmentID = this.shipmentId;
        mapper.addShipment(shipmentID, date, departureHour, truckPlateNumber, driverId, sourceId);
        this.shipmentId++;
        return shipmentID;
    }

    /**
     * Adding a document to the shipment (foreach location as requested)
     *
     * @param shipmentId - The driver's unique id
     * @param dest       - The destination of the shipment
     * @param products   - The items that will be transported
     */
    public void addDocument(int shipmentId, int dest, List<Item> products) throws Exception {
        docMapper.addDocument(trackingNumber, dest, shipmentId, Builder.buildItemsListDTO(products));
        trackingNumber++;
    }

    /**
     * Deleting a shipment from the system
     *
     * @param shipmentId - The driver's unique id
     */
    public void deleteShipment(int shipmentId) throws Exception {
        mapper.deleteShipment(shipmentId);
    }

    /**
     * @param trackingId - Document trackingNumber
     * @return The shipment per tracking id of its document
     */
    public Shipment trackShipment(int trackingId) throws Exception {
        return Builder.build(mapper.trackShipment(trackingId));
    }

    /**
     * @return all shipments in the system
     */
    public List<Shipment> getAllShipments() throws Exception {
        return Builder.buildShipmentsList(mapper.getAllShipments());
    }

    public List<Shipment> getNotApprovedShipments() throws Exception {
        return Builder.buildShipmentsList(mapper.getNotApprovedShipments());
    }

    public List<Shipment> getApprovedShipments() throws Exception {
        return Builder.buildShipmentsList(mapper.getApprovedShipments());
    }

    public List<Shipment> getNotDeliveredShipments() throws Exception {
        return Builder.buildShipmentsList(mapper.getNotDeliveredShipments());
    }

    public void approveShipment(Date date, String depHour, String driverId) throws Exception {
        mapper.approveShipment(date, depHour, driverId);
    }

    public void removeAllShipmentsDriver(Date date, boolean isMorning, String driverId) throws Exception {
        List<ShipmentDTO> shipmentsList = mapper.getAllShipments();
        for (ShipmentDTO shipmentDTO : shipmentsList) {
            if (shipmentDTO.getDate().equals(date)
                    && isMorning(shipmentDTO.getDepartureHour()) == isMorning
                    && shipmentDTO.getDriverId().equals(driverId)) {
                mapper.deleteDriverTruck(driverId, date, isMorning);
                mapper.deleteShipment(shipmentDTO.getShipmentId());

            }
        }
    }

    private boolean isMorning(String hour) {
        int left = Integer.parseInt(hour.substring(0, 2));
        return left >= 6 && left <= 14;
    }

    public void removeShipments(Date date, boolean isMorning) throws Exception {
        List<ShipmentDTO> shipmentsList = mapper.getAllShipments();
        for (ShipmentDTO shipmentDTO : shipmentsList) {
            if (shipmentDTO.getDate().equals(date)
                    && isMorning(shipmentDTO.getDepartureHour()) == isMorning) {
                mapper.deleteShipment(shipmentDTO.getShipmentId());
                mapper.deleteDriverTruck(shipmentDTO.getDriverId(), date, isMorning);
            }
        }
    }

    public List<ItemSpecs> getReceivedItems(int shipmentId) throws Exception {
        List<ItemSpecs> receivedItems = mapper.getReceivedItems(shipmentId);
        return receivedItems;
    }
}