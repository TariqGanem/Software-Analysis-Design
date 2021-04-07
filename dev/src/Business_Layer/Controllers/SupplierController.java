package Business_Layer.Controllers;

import DTO.SupplierDTO;
import Business_Layer.Objects.SupplierCard;

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

    public void AddSupplier(String name, String manifactur, int company_id, int BankAccount,
                            String paymentConditions, String orderType, boolean selfPickup) throws Exception {
        if (suppliers.containsKey(company_id))
            throw new Exception("There's already supplier working with this company!!!");
        suppliers.put(company_id, new SupplierCard(name, manifactur, company_id,
                BankAccount, paymentConditions, orderType, selfPickup));
    }

    public void RemoveSupplier(int company_id) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.remove(company_id);
    }

    public void ChangePaymentConditions(int company_id, String paymentConditions) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).ChangePaymentConditions(paymentConditions);
    }

    public void ChangeBankAccount(int company_id, int bankAccount) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).ChangeBankAccount(bankAccount);
    }

    public SupplierDTO PrintSupplierCard(int company_id) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        return new SupplierDTO(suppliers.get(company_id));
    }

    public void AddContactPerson(int company_id, String name, Map<String, String> contactMethods) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).AddContactPreson(name, contactMethods);
    }

    public void RemoveContact(int company_id, String name) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).RemoveContact(name);
    }

    public SupplierDTO PrintAllContacts(int company_id) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        return new SupplierDTO(suppliers.get(company_id));
    }

    public void AddMethod(int company_id, String name, String method, String method_data) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).AddMethod(name, method, method_data);
    }

    public void RemoveMethod(int company_id, String name, String method) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).RemoveMethod(name, method);
    }

    public void EditMethod(int company_id, String name, String method, String method_data) throws Exception {
        if (!suppliers.containsKey(company_id))
            throw new Exception("There's no supplier working with this company!!!");
        suppliers.get(company_id).EditMethod(name, method, method_data);
    }
}
