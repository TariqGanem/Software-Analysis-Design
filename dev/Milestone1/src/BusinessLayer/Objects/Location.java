package BusinessLayer.Objects;

import java.util.HashMap;
import java.util.Map;

public class Location {
    private String address;
    private String phoneNumber;
    private String contactName;
    private Map<Integer,String> products;

    public Location(String address, String phoneNumber, String contactName) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.products = new HashMap<>();
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

    public Map<Integer, String> getProducts() {
        return products;
    }
}