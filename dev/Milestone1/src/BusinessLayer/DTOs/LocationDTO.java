package BusinessLayer.DTOs;

import BusinessLayer.Objects.Location;

import java.util.Map;

public class LocationDTO {
    private String address;
    private String phoneNumber;
    private String contactName;
    private Map<Integer, String> products;

    public LocationDTO(Location location){
        address = location.getAddress();
        phoneNumber = location.getPhoneNumber();
        contactName = location.getContactName();
        products = location.getProducts();
    }
}