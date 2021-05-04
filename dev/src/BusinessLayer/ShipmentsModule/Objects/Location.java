package ShipmentsModule.Objects;

public class Location {
    private String address;
    private String phoneNumber;
    private String contactName;

    public Location(String address, String phoneNumber, String contactName) {
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
}