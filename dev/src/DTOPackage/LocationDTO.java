package DTOPackage;

import BusinessLayer.ShipmentsModule.Objects.Location;

public class LocationDTO {
    private int id;
    private String address;
    private String phoneNumber;
    private String contactName;

    public LocationDTO(Location location) {
        id = location.getId();
        address = location.getAddress();
        phoneNumber = location.getPhoneNumber();
        contactName = location.getContactName();
    }

    public LocationDTO(int id, String address, String phoneNumber, String contactName) {
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
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

    public int getId() {
        return id;
    }
}