package DataAccessLayer.SuppliersModule.Objects;

import DTOPackage.ContactDTO;
import DTOPackage.SupplierDTO;
import Resources.ContactMethod;

import java.util.HashMap;
import java.util.Map;

public class Supplier {
    private String name;
    private String manifactur;
    private int company_id;
    private String phone;
    private int bankAccount;
    private String paymentConditions;
    private String orderType;
    private boolean selfPickup;
    private Map<String, Contact> contacts;

    public Supplier(String name, String manifactur, int company_id, String phone, int BankAccount,
                    String paymentConditions, String orderType, boolean selfPickup, Map<String, Contact> contacts) {
        this.name = name;
        this.manifactur = manifactur;
        this.company_id = company_id;
        this.phone = phone;
        this.bankAccount = BankAccount;
        this.paymentConditions = paymentConditions;
        this.orderType = orderType;
        this.selfPickup = selfPickup;
        this.contacts = contacts;
    }

    public Supplier(SupplierDTO dto) {
        this.name = dto.getName();
        this.manifactur = dto.getManifactur();
        this.company_id = dto.getCompany_id();
        this.phone = dto.getPhone();
        this.bankAccount = dto.getBankAccount();
        this.paymentConditions = dto.getPaymentConditions();
        this.orderType = dto.getOrderType();
        this.selfPickup = dto.isSelfPickup();
        this.contacts = new HashMap<>();

        for (Map.Entry<String, ContactDTO> contactDTO : dto.getContacts().entrySet()) {
            this.contacts.put(contactDTO.getKey(), new Contact(contactDTO.getValue()));
        }
    }


    public Contact getContact(String name) {
        if (contacts.containsKey(name)) {
            return contacts.get(name);
        } else {
            return null;
        }
    }

    public void update(String data) {
        paymentConditions = data;
    }

    public void update(int data) {
        bankAccount = data;
    }

    public void addContact(String name, ContactMethod method, String data) {
        if (!contacts.containsKey(name)) {
            Map<ContactMethod, String> methods = new HashMap<>();
            methods.putIfAbsent(method, data);
            contacts.putIfAbsent(name, new Contact(name, methods));
        }
    }

    public void addMethod(String name, ContactMethod method, String data) {
        if (contacts.containsKey(name)) {
            contacts.get(name).addMethod(method, data);
        }
    }

    public void removeContact(String name) {
        if (contacts.containsKey(name)) {
            contacts.remove(name);
        }
    }

    public void removeMethod(String name, ContactMethod method) {
        if (contacts.containsKey(name)) {
            contacts.get(name).removeMethod(method);
        }
    }

    public String getName() {
        return name;
    }

    public boolean isSelfPickup() {
        return selfPickup;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getPhone() {
        return phone;
    }

    public Map<String, Contact> getContacts() {
        return contacts;
    }

    public String getManifactur() {
        return manifactur;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getPaymentConditions() {
        return paymentConditions;
    }
}
