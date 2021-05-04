package DataAccessLayer.ShipmentsModule;

import DTOPackage.TruckDTO;
import DataAccessLayer.ShipmentsModule.Mappers.TruckMapper;

public class DalController {
    private static DalController instance = null;
    private TruckMapper truckMapper;

    private DalController() {
        //.initialize();
        truckMapper = TruckMapper.getInstance();
    }

    public static DalController getInstance() {
        if (instance == null)
            instance = new DalController();
        return instance;
    }

    public TruckDTO addNewTruck(String plateNumber, String model, Double natoWeight, Double maxWeight, boolean available) throws Exception {
        return truckMapper.addTruck(plateNumber, model, natoWeight, maxWeight, available);
    }

    public TruckDTO getTruck(String plateNumber) throws Exception {
        return truckMapper.getTruck(plateNumber);
    }

    public TruckDTO updateTruck(String plateNumber, boolean available) throws Exception {
        return truckMapper.updateTruck(plateNumber, available);
    }

    public TruckDTO getAvailableTruck(double weight) throws Exception {
        return truckMapper.getAvailableTruck(weight);
    }

    public TruckMapper getTruckMapper() {
        return truckMapper;
    }
}
