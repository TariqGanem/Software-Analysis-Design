package DTO;

import Business_Layer.Objects.ContactPerson;

import java.util.HashMap;
import java.util.Map;

public class SupplierDTO {
    private String name;
    private String manifactur;
    private int company_id;
    private int bankAccount;
    private String paymentConditions;
    private String orderType;
    private boolean selfPickup;
    private Map<String, ContactDTO> contacts;

    public SupplierDTO(String name, String manifactur, int company_id, int BankAccount,
                        String paymentConditions, String orderType, boolean selfPickup, Map<String, ContactDTO> contacts) {
        this.name = name;
        this.manifactur = manifactur;
        this.company_id = company_id;
        this.bankAccount = BankAccount;
        this.paymentConditions = paymentConditions;
        this.orderType = orderType;
        this.selfPickup = selfPickup;
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        String output = "============================================" + '\n' +
                "name: " + name + '\n' +
                "manifactur: " + manifactur + '\n' +
                "company_id: " + company_id + '\n' +
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

    public String AllContacts(){
        String output = "";
        for (String contact : contacts.keySet()) {
            output += contacts.get(contact).toString();
        }
        return output;
    }
}
