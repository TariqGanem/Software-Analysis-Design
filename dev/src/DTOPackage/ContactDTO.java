package DTOPackage;

import BusinessLayer.SuppliersModule.Objects.ContactPerson;
import DataAccessLayer.SuppliersModule.Objects.Contact;
import Resources.ContactMethod;

import java.util.Map;

public class ContactDTO {
    private final String name;
    private final Map<ContactMethod, String> contactMethods;

    public ContactDTO(String name, Map<ContactMethod, String> contactMethods) {
        this.name = name;
        this.contactMethods = contactMethods;
    }

    public ContactDTO(ContactPerson contactPerson) {
        name = contactPerson.getName();
        contactMethods = contactPerson.getContactMethods();
    }

    public ContactDTO(Contact contract) {
        this.name = contract.getName();
        this.contactMethods = contract.getContactMethods();
    }

    @Override
    public String toString() {
        String output = "***************************" + "\n" +
                "name: " + name + '\n' +
                "contactMethods: " + '\n';
        for (ContactMethod method : contactMethods.keySet()) {
            output += method + " - " + contactMethods.get(method) + '\n';
        }
        output += "***************************" + '\n';
        return output;
    }

    public String getName() {
        return name;
    }

    public Map<ContactMethod, String> getContactMethods() {
        return contactMethods;
    }
}
