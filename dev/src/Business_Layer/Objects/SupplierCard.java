package Business_Layer.Objects;

import DTO.ContactDTO;
import DTO.SupplierDTO;
import enums.ContactMethod;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SupplierCard {
    private String name;
    private String manifactur;
    private int company_id;
    private int bankAccount;
    private String paymentConditions;
    private String orderType;
    private boolean selfPickup;
    private Map<String, ContactPerson> contacts;

    public SupplierCard(String name, String manifactur, int company_id, int BankAccount,
                        String paymentConditions, String orderType, boolean selfPickup) {
        this.name = name;
        this.manifactur = manifactur;
        this.company_id = company_id;
        this.bankAccount = BankAccount;
        this.paymentConditions = paymentConditions;
        this.orderType = orderType;
        this.selfPickup = selfPickup;
        this.contacts = new HashMap<>();
    }

    public SupplierCard(SupplierDTO supplier){
        name = supplier.getName();
        manifactur = supplier.getManifactur();
        company_id = supplier.getCompany_id();
        bankAccount = supplier.getBankAccount();
        paymentConditions = supplier.getPaymentConditions();
        orderType = supplier.getOrderType();
        contacts = new HashMap<>();
        for (Map.Entry<String, ContactDTO> pair: supplier.getContacts().entrySet()) {
            contacts.put(pair.getKey(),new ContactPerson(pair.getValue()));
        }
        selfPickup = supplier.isSelfPickup();
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

    public Map<String, ContactPerson> getContacts() {
        return contacts;
    }






    public void ChangePaymentConditions(String paymentConditions) {
        this.paymentConditions = paymentConditions;
    }

    public void ChangeBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void AddContactPreson(String name, Map<ContactMethod, String> contactMethods) throws Exception {
        if (contacts.containsKey(name))
            throw new Exception("There's already a contact person with this name.");
        contacts.put(name, new ContactPerson(name, contactMethods));
    }

    public void RemoveContact(String name) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.remove(name);
    }

    public void AddMethod(String name, ContactMethod method, String method_data) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.get(name).AddMethod(method, method_data);
    }

    public void RemoveMethod(String name, ContactMethod method) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.get(name).RemoveMethod(method);
    }

    public void EditMethod(String name, ContactMethod method, String method_data) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.get(name).EditMethod(method, method_data);
    }


}
