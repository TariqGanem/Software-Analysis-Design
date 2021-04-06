package Business_Layer.Objects;

import DTO.ContactDTO;
import DTO.SupplierDTO;

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

    public SupplierDTO DTO() {
        Map<String, ContactDTO> dto_contacts = new HashMap<>();
        for (String name : contacts.keySet()) {
            dto_contacts.put(name, contacts.get(name).DTO());
        }
        return new SupplierDTO(name, manifactur, company_id, bankAccount, paymentConditions
                , orderType, selfPickup, dto_contacts);
    }

    public void ChangePaymentConditions(String paymentConditions) {
        this.paymentConditions = paymentConditions;
    }

    public void ChangeBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void AddContactPreson(String name, Map<String, String> contactMethods) throws Exception {
        if (contacts.containsKey(name))
            throw new Exception("There's already a contact person with this name.");
        contacts.put(name, new ContactPerson(name, contactMethods));
    }

    public void RemoveContact(String name) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.remove(name);
    }

    public void AddMethod(String name, String method, String method_data) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.get(name).AddMethod(method, method_data);
    }

    public void RemoveMethod(String name, String method) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.get(name).RemoveMethod(method);
    }

    public void EditMethod(String name, String method, String method_data) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.get(name).EditMethod(method, method_data);
    }
}
