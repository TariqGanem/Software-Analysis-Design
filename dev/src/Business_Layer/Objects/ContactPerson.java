package Business_Layer.Objects;

import DTO.ContactDTO;
import enums.ContactMethod;

import java.util.*;

public class ContactPerson {
    private final String name;
    private final Map<ContactMethod, String> contactMethods;

    public ContactPerson(String name, Map<ContactMethod, String> contactMethods) {
        if (contactMethods == null)
            throw new NullPointerException();
        this.name = name;
        this.contactMethods = contactMethods;
    }


    public ContactPerson(ContactDTO contact){
        if (contact == null)
            throw new NullPointerException();
        name = contact.getName();
        contactMethods = contact.getContactMethods();
    }


    public void AddMethod(ContactMethod method, String method_data) throws Exception {
        if (contactMethods.containsKey(method))
            throw new Exception("There's already a method such as this!");
        contactMethods.put(method, method_data);
    }

    public void RemoveMethod(ContactMethod method) throws Exception {
        if (!contactMethods.containsKey(method))
            throw new Exception("There's no method such as this!");
        contactMethods.remove(method);
    }

    public void EditMethod(ContactMethod method, String method_data) throws Exception {
        if (!contactMethods.containsKey(method))
            throw new Exception("There's no method such as this!");
        contactMethods.put(method, method_data);
    }

    public String getName() {
        return name;
    }

    public Map<ContactMethod, String> getContactMethods() {
        return contactMethods;
    }


}
