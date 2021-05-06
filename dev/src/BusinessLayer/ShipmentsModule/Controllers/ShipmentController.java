package BusinessLayer.ShipmentsModule.Controllers;

import BusinessLayer.ShipmentsModule.Builder;
import BusinessLayer.ShipmentsModule.Objects.Item;
import BusinessLayer.ShipmentsModule.Objects.Location;
import BusinessLayer.ShipmentsModule.Objects.Shipment;
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
        trackingNumber = 0;
        shipmentId = 0;
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
     * @param source           - The location which the delivery will start on
     * @throws Exception in case of wrong parameters values
     */
    public int addShipment(Date date, String departureHour, String truckPlateNumber, String driverId, Location source) throws Exception {
        if (departureHour == null || departureHour.trim().isEmpty())
            throw new Exception("Couldn't add new shipment - Invalid parameters");
        int shipmentID = this.shipmentId;
        mapper.addShipment(shipmentID, date, departureHour, truckPlateNumber, driverId, source.getId());
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
    public void addDocument(int shipmentId, Location dest, List<Item> products) throws Exception {
        docMapper.addDocument(trackingNumber, dest.getId(), shipmentId, Builder.buildItemsListDTO(products));
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
}