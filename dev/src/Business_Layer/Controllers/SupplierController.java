package Business_Layer.Controllers;

import DTO.SupplierDTO;
import Business_Layer.Objects.SupplierCard;
import enums.ContactMethod;

import java.util.HashMap;
import java.util.Map;

public class SupplierController {
    private static SupplierController instance;
    private Map<Integer, SupplierCard> suppliers;


    private SupplierController() {
        suppliers = new HashMap<>();
    }

    public static SupplierController getInstance() {
        if(instance == null)
            instance = new SupplierController();
        return instance;
    }

    /***
     * The function adds a new supplier to the system.
     * @param name is the new supplier's name.
     * @param manifactur is the manifactur that he works with.
     * @param company_id the id of the company that he works with.
     * @param BankAccount is the account of the supplier in the bank;
     * @param paymentConditions are conditions of the contract between the new supplier and Super-Li
     * @param orderType is the type of the contract between the supplier and Super-Li.
     * @param selfPickup its value is true or false, if the supplier is picking up the orders by himself or not.
     * @throws Exception if the there is already a supplier that works with the same company so that's an error.
     */
    public void AddSupplier(String name, String manifactur, int company_id, int BankAccount,
                            String paymentConditions, String orderType, boolean selfPickup) throws Exception {
        if (suppliers.containsKey(company_id))
            throw new Exception("There's already supplier working with this company!!!");
        suppliers.put(company_id, new SupplierCard(name, manifactur, company_id,
                BankAccount, paymentConditions, orderType, selfPickup));
    }

    /***
     * Removing a current supplier in the system.
     * @param company_id the id of the company that he works with.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     */
    public void RemoveSupplier(int company_id) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.remove(company_id);
    }

    /***
     * Changing the payment conditions of a current supplier.
     * @param company_id is the id of the company that the supplier works with it.
     * @param paymentConditions the new payment conditions that is going to be added in place of the old one.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     */
    public void ChangePaymentConditions(int company_id, String paymentConditions) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).ChangePaymentConditions(paymentConditions);
    }

    /***
     * Changing the payment conditions of a current supplier.
     * @param company_id is the id of the company that the supplier works with it.
     * @param bankAccount the new bank account that is going to be added in place of the old one.
     * @return the response of the system. if there is no supplier that works with the company with this id so there is an error.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     */
    public void ChangeBankAccount(int company_id, int bankAccount) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).ChangeBankAccount(bankAccount);
    }

    /***
     * Printing the Card of a current supplier.
     * @param company_id is the id of the company that the supplier works with it.
     * @return a supplier dto that holds the same data of the supplier that we wanna print.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     */
    public SupplierDTO PrintSupplierCard(int company_id) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        return new SupplierDTO(suppliers.get(company_id));
    }

    /***
     * Adding a new contact person to the supplier's card.
     * @param company_id is the id of the company that the supplier works with it.
     * @param name is the name of the new contact.
     * @param contactMethods are the methods that the supplier is going to contact this person with.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     * also if the contact is already in the supplier's contact table so the is an error.
     */
    public void AddContactPerson(int company_id, String name, Map<ContactMethod, String> contactMethods) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).AddContactPerson(name, contactMethods);
    }

    /***
     * Removing a current contact person from the contacts table of a supplier.
     * @param company_id is the id of the company that the supplier works with it.
     * @param name is the name of the contact person.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     * also if there is no contact person in the supplier's contact table so the is an error.
     */
    public void RemoveContact(int company_id, String name) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).RemoveContact(name);
    }

    /***
     * Printing all the contacts of a current supplier.
     * @param company_id is the id of the company that the supplier works with it.
     * @return a supplier dto that holds the same data of the supplier that we wanna print.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     */
    public SupplierDTO PrintAllContacts(int company_id) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        return new SupplierDTO(suppliers.get(company_id));
    }

    /***
     * Adding a new method for a current contact person.
     * @param company_id is the id of the company that the supplier works with it.
     * @param name is the name of the contact person.
     * @param method is the new method that we wanna add.
     * @param method_data is the data of the new method like the phone number.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     * also if there is no contact person in the supplier's contact table so the is an error.
     * also if the method is already founded so there is an error.
     */
    public void AddMethod(int company_id, String name, ContactMethod method, String method_data) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).AddMethod(name, method, method_data);
    }

    /***
     *
     * @param company_id
     * @param name
     * @param method
     * @throws Exception
     */
    public void RemoveMethod(int company_id, String name, ContactMethod method) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).RemoveMethod(name, method);
    }

    /***
     *  Editing a current method of a current contact person that is appeared on a current supplier's card.
     * @param company_id is the id of the company that the supplier works with it.
     * @param name is the name of the contact person.
     * @param method is the new method that we wanna add.
     * @param method_data is the data of the new method like the phone number.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     * also if there is no contact person in the supplier's contact table so the is an error.
     * also if the method is not founded so there is an error.
     */
    public void EditMethod(int company_id, String name, ContactMethod method, String method_data) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).EditMethod(name, method, method_data);
    }
}
