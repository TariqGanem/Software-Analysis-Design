import BusinessLayer.ShipmentsModule.Facade;
import BusinessLayer.ShipmentsModule.Response;
import DTOPackage.ItemDTO;
import DTOPackage.ShipmentDTO;
import DataAccessLayer.ShipmentsModule.Mappers.DocumentMapper;
import DataAccessLayer.ShipmentsModule.Mappers.ShipmentMapper;
import DataAccessLayer.dbMaker;
import PresentationLayer.ShipmentsMenu.Printer;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class dalTESTING {


    public static void main(String[] args) throws Exception {
        try {

                new dbMaker().initialize();
                Facade facade = new Facade();

                Map<Integer, List<ItemDTO>> itemsPerLocation = new HashMap<>();
                List<ItemDTO> items = new LinkedList<>();
                items.add(new ItemDTO("creama", 3, 9));
                items.add(new ItemDTO("weed", 6, 7));
                itemsPerLocation.put(5,items);
                List<ItemDTO> items1 = new LinkedList<>();
                items1.add(new ItemDTO("apple", 1, 4));
                items1.add(new ItemDTO("bana", 6, 9));
                itemsPerLocation.put(3,items1);


                Response res =  facade.arrangeDelivery(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2042"),
                        "13:13",0,itemsPerLocation);
                if(res.errorOccured()) {
                    System.out.println("->" + res.getMsg());
                    System.out.println("hiiii");
                }

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
//            List<ItemDTO> items = new LinkedList<>();
//            items.add(new ItemDTO("creama", 3, 9));
//            items.add(new ItemDTO("weed", 6, 7));
//            DocumentMapper.getInstance().addDocument(3, 2, 7, items);
////            p(DocumentMapper.getInstance().getDocument(1).toString());
////            DocumentMapper.getInstance().getDocument(2).getProducts().forEach(p -> p(p.getName()));
////            DocumentMapper.getInstance().getShipmentDocuments(3).forEach(d->p(d.getTrackingNumber() + ""));
//
//            ShipmentDTO shipment = ShipmentMapper.getInstance().addShipment(7,new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2042"), "14:55","123","admin",0);
////            System.out.println(shipment.getShipmentId() + " \t " + new SimpleDateFormat("dd/MM/yyyy").format(shipment.getDate()));
//
//          ShipmentDTO shipment1 = ShipmentMapper.getInstance().getShipment(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2033"), "14:34", "admin");
//            ShipmentDTO shipment2 = ShipmentMapper.getInstance().getShipment(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2033"), "14:34", "admin");
//            System.out.println(shipment1.getShipmentId() + " \t " + new SimpleDateFormat("dd/MM/yyyy").format(shipment1.getDate()) + " \t " + shipment1);
//            System.out.println(shipment2.getShipmentId() + " \t " + new SimpleDateFormat("dd/MM/yyyy").format(shipment2.getDate()) + " \t " + shipment2);
//            ShipmentMapper.getInstance().trackShipment(2).getDocuments().get(2).getProducts().forEach(p -> System.out.println(p.getName()));
//            ShipmentMapper.getInstance().getAllShipments().forEach(s -> System.out.println(s.getDate()));
//            ShipmentMapper.getInstance().deleteShipment(3);

//            p(shipment1.getDocuments().get(1).getTrackingNumber()+"");
            //shipment1.getDocuments().values().forEach(d-> Printer.getInstance().viewShipment(shipment1, d));
        } catch (Exception e) {
            p(e.getMessage());
        }
    }


    public static void p(String msg) {
        System.out.println(msg);
    }
}
