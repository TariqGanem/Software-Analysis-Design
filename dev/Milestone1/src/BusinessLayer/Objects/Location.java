package BusinessLayer.Objects;


import java.util.HashMap;
import java.util.Map;

public class Location {
    private String address;
    private String phoneNumber;
    private String contactName;
    private Map<String, Integer> products;

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

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> prods){
        products = prods;
    }
}