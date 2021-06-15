package DTOPackage;

import BusinessLayer.SuppliersModule.Objects.ContactPerson;
import BusinessLayer.SuppliersModule.Objects.SupplierCard;
import DataAccessLayer.SuppliersModule.Objects.Contact;
import DataAccessLayer.SuppliersModule.Objects.Supplier;

import java.util.HashMap;
import java.util.Map;

public class SupplierDTO {
    private String name;
    private String manifactur;
    private int company_id;
    private String phone;
    private int bankAccount;
    private String paymentConditions;
    private String orderType;
    private boolean selfPickup;
    private Map<String, ContactDTO> contacts;

    public SupplierDTO(String name, String manifactur, int company_id, String phone, int BankAccount,
                       String paymentConditions, String orderType, boolean selfPickup, Map<String, ContactDTO> contacts) {
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

    public SupplierDTO(SupplierCard supplierCard) {
        name = supplierCard.getName();
        manifactur = supplierCard.getManifactur();
        company_id = supplierCard.getCompany_id();
        phone = supplierCard.getPhone();
        bankAccount = supplierCard.getBankAccount();
        orderType = supplierCard.getOrderType();
        selfPickup = supplierCard.isSelfPickup();
        paymentConditions = supplierCard.getPaymentConditions();
        this.contacts = new HashMap<>();
        for (Map.Entry<String, ContactPerson> contacts :
                supplierCard.getContacts().entrySet()) {
            this.contacts.put(contacts.getKey(), new ContactDTO(contacts.getValue()));
        }

    }

    public SupplierDTO(Supplier supplier) {
        this.name = supplier.getName();
        this.manifactur = supplier.getManifactur();
        this.company_id = supplier.getCompany_id();
        this.phone = supplier.getPhone();
        this.bankAccount = supplier.getBankAccount();
        this.orderType = supplier.getOrderType();
        this.selfPickup = supplier.isSelfPickup();
        this.paymentConditions = supplier.getPaymentConditions();
        this.contacts = new HashMap<>();
        for (Map.Entry<String, Contact> contacts :
                supplier.getContacts().entrySet()) {
            this.contacts.put(contacts.getKey(), new ContactDTO(contacts.getValue()));
        }
    }

    @Override
    public String toString() {
        String output = "============================================" + '\n' +
                "name: " + name + '\n' +
                "manifactur: " + manifactur + '\n' +
                "company_id: " + company_id + '\n' +
                "phone: " + phone + '\n' +
                "bankAccount: " + bankAccount + '\n' +
                "paymentConditions: " + paymentConditions + '\n' +
                "orderType: " + orderType + '\n' +
                "selfPickup: " + selfPickup + '\n' +
                "contacts:" + '\n' +
                "---------------Contacts---------------" + '\n';
        for (String contact : contacts.keySet()) {
            output += contacts.get(contact).toString();
        }
        output += "--------------------------------------" + '\n' +
                "============================================" + '\n';
        return output;
    }

    public String AllContacts() {
        String output = "";
        for (String contact : contacts.keySet()) {
            output += contacts.get(contact).toString();
        }
        return output;
    }

    public String getName() {
        return name;
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

    public String getManifactur() {
        return manifactur;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getPaymentConditions() {
        return paymentConditions;
    }

    public boolean isSelfPickup() {
        return selfPickup;
    }

    public Map<String, ContactDTO> getContacts() {
        return contacts;
    }
}
