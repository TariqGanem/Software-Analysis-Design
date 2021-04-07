package DTO;

import Business_Layer.Objects.ContactPerson;

import java.util.Map;

public class ContactDTO {
    private final String name;
    private final Map<String, String> contactMethods;

    public ContactDTO(String name, Map<String, String> contactMethods) {
        this.name = name;
        this.contactMethods = contactMethods;
    }

    public ContactDTO(ContactPerson contactPerson){
        name = contactPerson.getName();
        contactMethods = contactPerson.getContactMethods();
    }

    @Override
    public String toString() {
        String output = "***************************" + "\n" +
                "name: " + name + '\n' +
                "contactMethods: " + '\n';
        for (String method : contactMethods.keySet()) {
            output += method + " - " + contactMethods.get(method) + '\n';
        }
        output += "***************************" + '\n';
        return output;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getContactMethods() {
        return contactMethods;
    }
}
