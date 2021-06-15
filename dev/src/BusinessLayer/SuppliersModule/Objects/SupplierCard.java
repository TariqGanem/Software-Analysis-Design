package BusinessLayer.SuppliersModule.Objects;

import DTOPackage.ContactDTO;
import DTOPackage.SupplierDTO;
import Resources.ContactMethod;

import java.util.HashMap;
import java.util.Map;

public class SupplierCard {
    private String name;
    private String manifactur;
    private final int company_id;
    private String phone;
    private int bankAccount;
    private String paymentConditions;
    private String orderType;
    private boolean selfPickup;
    private final Map<String, ContactPerson> contacts;

    public SupplierCard(String name, String manifactur, int company_id, String phone, int BankAccount,
                        String paymentConditions, String orderType, boolean selfPickup) {
        this.name = name;
        this.manifactur = manifactur;
        this.company_id = company_id;
        this.phone = phone;
        this.bankAccount = BankAccount;
        this.paymentConditions = paymentConditions;
        this.orderType = orderType;
        this.selfPickup = selfPickup;
        this.contacts = new HashMap<>();
    }

    public SupplierCard(SupplierDTO supplier) {
        name = supplier.getName();
        manifactur = supplier.getManifactur();
        company_id = supplier.getCompany_id();
        phone = supplier.getPhone();
        bankAccount = supplier.getBankAccount();
        paymentConditions = supplier.getPaymentConditions();
        orderType = supplier.getOrderType();
        contacts = new HashMap<>();
        for (Map.Entry<String, ContactDTO> pair : supplier.getContacts().entrySet()) {
            contacts.put(pair.getKey(), new ContactPerson(pair.getValue()));
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

    public Map<String, ContactPerson> getContacts() {
        return contacts;
    }


    /***
     * Changing the payment conditions of a current supplier.
     * @param paymentConditions the new payment conditions that is going to be added in place of the old one.
     */
    public void ChangePaymentConditions(String paymentConditions) {
        this.paymentConditions = paymentConditions;
    }

    /***
     * Changing the payment conditions of a current supplier.
     * @param bankAccount the new bank account that is going to be added in place of the old one.
     */
    public void ChangeBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }

    /***
     * Adding a new contact person to the supplier's card.
     * @param name is the name of the new contact.
     * @param method are the methods that the supplier is going to contact this person with.
     * @param method_data is the data of the method like the phone number.
     * @throws Exception if the contact is already in the supplier's contact table so the is an error.
     */
    public void AddContactPerson(String name, ContactMethod method, String method_data) throws Exception {
        if (contacts.containsKey(name))
            throw new Exception("There's already a contact person with this name.");
        contacts.put(name, new ContactPerson(name, method, method_data));
    }

    /***
     * Removing a current contact person from the contacts table of a supplier.
     * @param name is the name of the contact person.
     * @throws Exception if there is no contact person in the supplier's contact table so the is an error.
     */
    public void RemoveContact(String name) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.remove(name);
    }

    /***
     * Adding a new method for a current contact person.
     * @param name is the name of the contact person.
     * @param method is the new method that we wanna add.
     * @param method_data is the data of the new method like the phone number.
     * @throws Exception if there is no contact person in the supplier's contact table so the is an error.
     * also if the method is already founded so there is an error.
     */
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

    /***
     *  Editing a current method of a current contact person that is appeared on a current supplier's card.
     * @param name is the name of the contact person.
     * @param method is the new method that we wanna add.
     * @param method_data is the data of the new method like the phone number.
     * @throws Exception if there is no contact person in the supplier's contact table so the is an error.
     * also if the method is not founded so there is an error.
     */
    public void EditMethod(String name, ContactMethod method, String method_data) throws Exception {
        if (!contacts.containsKey(name))
            throw new Exception("There's no contact person with this name.");
        contacts.get(name).EditMethod(method, method_data);
    }


}
