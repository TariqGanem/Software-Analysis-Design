package BusinessLayer.ShipmentsModule.Objects;

public class Location {
    private int id;
    private String address;
    private String phoneNumber;
    private String contactName;

    public Location(int id, String address, String phoneNumber, String contactName) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.id = id;
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