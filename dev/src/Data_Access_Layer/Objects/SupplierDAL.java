package Data_Access_Layer.Objects;

import DTO.ContactDTO;
import DTO.SupplierDTO;
import enums.ContactMethod;

import java.util.HashMap;
import java.util.Map;

public class SupplierDAL {
    private String name;
    private String manifactur;
    private int company_id;
    private int bankAccount;
    private String paymentConditions;
    private String orderType;
    private boolean selfPickup;
    private Map<String, ContactDAL> contacts;

    public SupplierDAL(String name, String manifactur, int company_id, int BankAccount,
                       String paymentConditions, String orderType, boolean selfPickup, Map<String, ContactDAL> contacts) {
        this.name = name;
        this.manifactur = manifactur;
        this.company_id = company_id;
        this.bankAccount = BankAccount;
        this.paymentConditions = paymentConditions;
        this.orderType = orderType;
        this.selfPickup = selfPickup;
        this.contacts = contacts;
    }

    public SupplierDAL(SupplierDTO dto) {
        this.name = dto.getName();
        this.manifactur = dto.getManifactur();
        this.company_id = dto.getCompany_id();
        this.bankAccount = dto.getBankAccount();
        this.paymentConditions = dto.getPaymentConditions();
        this.orderType = dto.getOrderType();
        this.selfPickup = dto.isSelfPickup();
        this.contacts = new HashMap<>();
        for (String name:dto.getContacts().keySet()) {
            contacts.putIfAbsent(name,new ContactDAL(dto.getContacts().get(name)));
        }
    }

    public SupplierDTO DTO(){
        Map<String, ContactDTO> contactsDTO = new HashMap<>();
        for (String contact: contacts.keySet()) {
            contactsDTO.put(contact,contacts.get(contact).DTO());
        }
        return new SupplierDTO(name, manifactur, company_id, bankAccount,
                                paymentConditions, orderType, selfPickup, contactsDTO);
    }

    public ContactDAL getContact(String name){
        if(contacts.containsKey(name)){
            return contacts.get(name);
        }else{
            return null;
        }
    }

    public void update(String data) {
        paymentConditions = data;
    }

    public void update(int data) {
        bankAccount = data;
    }

    public void addContact(String name, ContactMethod method, String data){
        if(!contacts.containsKey(name)){
            Map<ContactMethod,String> methods = new HashMap<>();
            methods.putIfAbsent(method,data);
            contacts.putIfAbsent(name,new ContactDAL(name,methods));
        }
    }

    public void addMethod(String name, ContactMethod method, String data) {
        if(contacts.containsKey(name)){
           contacts.get(name).addMethod(method,data);
        }
    }

    public void removeContact(String name) {
        if(contacts.containsKey(name)){
            contacts.remove(name);
        }
    }

    public void removeMethod(String name, ContactMethod method) {
        if(contacts.containsKey(name)){
            contacts.get(name).removeMethod(method);
        }
    }
}
