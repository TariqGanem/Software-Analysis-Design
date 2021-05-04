
import DTOs.*;
import DataAccessLayer.DalController;
import DataAccessLayer.Mappers.DocumentMapper;
import DataAccessLayer.Mappers.LocationMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class dalTESTING {
    public static void main(String[] args) throws Exception {
        DalController dC = DalController.getInstance();
        LocationMapper.getInstance().addLocation(9,"loc","3243","yazan");
        LocationMapper.getInstance().getLocation(1);
        LocationMapper.getInstance().getLocation(3);
        LocationMapper.getInstance().getLocation(1);
//        DataAccessLayer.IdentityMap.getInstance().getLocations().forEach(l-> System.out.println(l.getId() + " \t "+ l));
////        DocumentDTO doc = DocumentMapper.getInstance().getDocument(2);
//        List<ItemDTO> items = new LinkedList<>();
//        items.add(new ItemDTO(2, "colaaa", 5.5, 6.7));
//        items.add(new ItemDTO(2, "blue", 51.3, 77.7));
//        DocumentDTO doc1 = DocumentMapper.getInstance().addDocument(99, 1, 1, items);
//        IdentityMap.getInstance().getDocuments().forEach(l -> p("\t Document Address: \t " + l.getTrackingNumber() + " \t " + l));
//        doc1.getProducts().forEach(p -> System.out.println(p.getName()));

//
//        LocationDTO loc2 = LocationMapper.getInstance().getLocation("yyyyy", "030443", "dsfds");
//       IdentityMap.getInstance().getLocations().forEach(l -> p("\t Location Address: \t " + l.getAddress() + " \t " + l));
//

        Scanner scanner = new Scanner(System.in);
        int input;
        while (true) {
            try {

//                LocationDTO loc1 = LocationMapper.getInstance().addLocation("beer sheva", "43343", "yazna");
//                IdentityMap.getInstance().getLocations().forEach(l -> p("\t Location Address: \t " + l.getAddress() + " \t " + l));


                System.out.println("\n1.Add truck\n2.Get truck\n3.Update truck\n4.View trucks in memory\n5.Exit\n6.Get available truck");
                input = Integer.parseInt(scanner.nextLine());
                switch (input) {
                    case 1:
                        System.out.println("Truck plateNumber [autoComplete]: ");
                        TruckDTO t1 = dC.addNewTruck(scanner.nextLine(), "F16", 6000.5, 4500.0, true);
                        p("truck added! \t plateNumber: " + t1.getTruckPlateNumber() + " \t " + t1);
                        break;
                    case 2:
                        System.out.println("Truck plateNumber [autoComplete]: ");
                        TruckDTO t3 = dC.getTruck(scanner.nextLine());
                        p("truck found! \t plateNumber: " + t3.getTruckPlateNumber() + " \t " + t3);
                        break;
                    case 3:
                        System.out.println("Truck plateNumber & isAvailable: ");
                        TruckDTO t4 = dC.updateTruck(scanner.nextLine(), scanner.nextBoolean());
                        p("truck updated! \t plateNumber: " + t4.getTruckPlateNumber() + " \t " + t4.isAvailable() + " \t " + t4);
                        break;
                    case 4:
                        System.out.println("Trucks in memory: " + dC.getTruckMapper().getMemory().size() + " \t ");
                        dC.getTruckMapper().getMemory().forEach(t -> p("\t Truck Address: \t " + t));
                        break;
                    case 5:
                        System.exit(0);
                    case 6:
                        TruckDTO t5 = dC.getAvailableTruck(3500.0);
                        p("Getting available truck \t plateNumber: " + t5.getTruckPlateNumber() + " \t " + t5);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error->\t" + e.getMessage());
            }
        }

//        try {
//
////            TruckDTO t1 = dC.addNewTruck("2", "F16", 6000.5, 4500.0, true);
////            p("truck added! \t plateNumber: " + t1.getTruckPlateNumber() + " \t " + t1);
//
//            TruckDTO t2 = dC.getTruck("2");
//            p("truck found! \t plateNumber: " + t2.getTruckPlateNumber() + " \t " + t2);
//
//            p("Trucks in memory: " + dC.getTruckMapper().getMemory().size());
//
//            TruckDTO t3 = dC.getTruck("1");
//            p("truck found! \t plateNumber: " + t3.getTruckPlateNumber() + " \t " + t3);
//
//            System.out.println("Trucks in memory: " + dC.getTruckMapper().getMemory().size() + " \t ");
//            dC.getTruckMapper().getMemory().forEach(t -> p("\t Truck Address: \t " + t));
//
//        } catch (Exception e) {
//            System.out.println("Error->\t" + e.getMessage());
//        }

    }


    public static void p(String msg) {
        System.out.println(msg);
    }
}
