package DTOs;

import BusinessLayer.Objects.Location;

import java.util.Map;

public class LocationDTO {
    private String address;
    private String phoneNumber;
    private String contactName;

    public LocationDTO(Location location) {
        address = location.getAddress();
        phoneNumber = location.getPhoneNumber();
        contactName = location.getContactName();
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }
}