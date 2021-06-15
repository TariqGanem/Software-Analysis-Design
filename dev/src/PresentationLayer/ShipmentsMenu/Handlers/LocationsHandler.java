package PresentationLayer.ShipmentsMenu.Handlers;


import BusinessLayer.Response;
import BusinessLayer.ResponseT;
import BusinessLayer.ShipmentsModule.Facade;
import DTOPackage.LocationDTO;

import java.util.LinkedList;
import java.util.List;

public class LocationsHandler extends Handler {

    private Facade facade;
    private List<LocationDTO> locations;

    public LocationsHandler(Facade facade) {
        this.facade = facade;
        locations = new LinkedList<>();
    }


    public void addLocation() {
        System.out.printf("\nEnter address: ");
        String address = scanner.nextLine();
        System.out.printf("\nEnter phone number:");
        String phoneNumber = scanner.nextLine();
        System.out.printf("\nEnter contact name: ");
        String contactName = scanner.nextLine();
        System.out.println();
        Response res = facade.addLocation(address, phoneNumber, contactName);
        if (res.getErrorOccurred())
            printer.error(res.getErrorMessage());
        else {
            printer.success("Location has been added!");
        }
    }

    public void viewAllLocations() {
        ResponseT<List<LocationDTO>> res = facade.getAllLocations();
        locations = res.getValue();
        if (res.getErrorOccurred())
            printer.error(res.getErrorMessage());
        else {
            printer.viewAllLocations(locations);
        }
    }

    public LocationDTO chooseLocation() {
        while (true) {
            try {
                return locations.get(getInt() - 1);
            } catch (Exception e) {
                printer.error("Invalid input!");
            }
        }
    }
}
