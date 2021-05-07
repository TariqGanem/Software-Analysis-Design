import DTOPackage.ItemDTO;
import DTOPackage.ShipmentDTO;
import DataAccessLayer.ShipmentsModule.Mappers.ShipmentMapper;
import DataAccessLayer.dbMaker;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class dalTESTING {


    public static void main(String[] args) throws Exception {
        try {
            new dbMaker().initialize();

            //TruckMapper.getInstance().addTruck("3e", "volvo", 324.4, 423.4, true);
//            p(TruckMapper.getInstance().getTruck("fyt").toString());
//            p(TruckMapper.getInstance().getTruck("123").toString());
//            p(TruckMapper.getInstance().getTruck("123").toString());
//            System.out.println(TruckMapper.getInstance().updateTruck("1",false).isAvailable());
//            TruckMapper.getInstance().getAllTrucks().forEach(t-> System.out.println((t.getTruckPlateNumber() + " \t " + t.isAvailable())));
//            p(TruckMapper.getInstance().getAvailableTruck(334).getTruckPlateNumber());
            //p(DriverMapper.getInstance().addDriver("admin",23.4,false).toString());
//            p(DriverMapper.getInstance().getDriver("111111111").toString());
//            p(DriverMapper.getInstance().getDriver("123456789").toString());
//            p(DriverMapper.getInstance().getDriver("111111111").toString());
//            System.out.println(DriverMapper.getInstance().updateDriver("111111111", true).isAvailable());
//            System.out.println(DriverMapper.getInstance().getDriver("111111111").isAvailable() + " \t " + DriverMapper.getInstance().getDriver("111111111").toString());
            //DriverMapper.getInstance().getAllDrivers().forEach(d->p(d.toString()));
            List<ItemDTO> items = new LinkedList<>();
            items.add(new ItemDTO("bmba", 2, 34));
            items.add(new ItemDTO("coca", 4, 22));
            //DocumentMapper.getInstance().addDocument(2, 2, 3, new LinkedList<>());
//            p(DocumentMapper.getInstance().getDocument(1).toString());
//            DocumentMapper.getInstance().getDocument(2).getProducts().forEach(p -> p(p.getName()));
//            DocumentMapper.getInstance().getShipmentDocuments(3).forEach(d->p(d.getTrackingNumber() + ""));

//            ShipmentDTO shipment = ShipmentMapper.getInstance().addShipment(3,new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2033"), "14:34","123","admin",2);
//            System.out.println(shipment.getShipmentId() + " \t " + new SimpleDateFormat("dd/MM/yyyy").format(shipment.getDate()));

            ShipmentDTO shipment1 = ShipmentMapper.getInstance().getShipment(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2033"), "14:34", "admin");
            ShipmentDTO shipment2 = ShipmentMapper.getInstance().getShipment(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2033"), "14:34", "admin");
            System.out.println(shipment1.getShipmentId() + " \t " + new SimpleDateFormat("dd/MM/yyyy").format(shipment1.getDate()) + " \t " + shipment1);
            System.out.println(shipment2.getShipmentId() + " \t " + new SimpleDateFormat("dd/MM/yyyy").format(shipment2.getDate()) + " \t " + shipment2);
            ShipmentMapper.getInstance().trackShipment(2).getDocuments().get(2).getProducts().forEach(p -> System.out.println(p.getName()));
            ShipmentMapper.getInstance().getAllShipments().forEach(s -> System.out.println(s.getDate()));
            ShipmentMapper.getInstance().deleteShipment(3);

//            p(shipment1.getDocuments().get(1).getTrackingNumber()+"");
//            shipment1.getDocuments().values().forEach(d->Printer.getInstance().viewShipment(shipment1, d));
        } catch (Exception e) {
            p(e.getMessage());
        }
    }


    public static void p(String msg) {
        System.out.println(msg);
    }
}
