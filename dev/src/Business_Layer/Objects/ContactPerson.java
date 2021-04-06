package Business_Layer.Objects;

import DTO.ContactDTO;

import java.util.*;

public class ContactPerson {
    private String name;
    private Map<String, String> contactMethods;

    public ContactPerson(String name, Map<String, String> contactMethods) {
        this.name = name;
        this.contactMethods = contactMethods;
    }

    public ContactDTO DTO(){
        return new ContactDTO(name, contactMethods);
    }

    public void AddMethod(String method, String method_data) throws Exception {
        if (contactMethods.containsKey(method))
            throw new Exception("There's already a method such as this!");
        contactMethods.put(method, method_data);
    }

    public void RemoveMethod(String method) throws Exception {
        if (!contactMethods.containsKey(method))
            throw new Exception("There's no method such as this!");
        contactMethods.remove(method);
    }

    public void EditMethod(String method, String method_data) throws Exception {
        if (!contactMethods.containsKey(method))
            throw new Exception("There's no method such as this!");
        contactMethods.put(method, method_data);
    }
}
