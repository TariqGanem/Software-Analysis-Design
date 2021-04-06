package DTO;

import java.util.Map;

public class ContactDTO {
    private String name;
    private Map<String, String> contactMethods;

    public ContactDTO(String name, Map<String, String> contactMethods) {
        this.name = name;
        this.contactMethods = contactMethods;
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

}
