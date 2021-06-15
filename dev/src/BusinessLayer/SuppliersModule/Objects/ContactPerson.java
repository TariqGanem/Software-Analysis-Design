package BusinessLayer.SuppliersModule.Objects;

import DTOPackage.ContactDTO;
import Resources.ContactMethod;

import java.util.HashMap;
import java.util.Map;

public class ContactPerson {
    private final String name;
    private final Map<ContactMethod, String> contactMethods;

    public ContactPerson(String name, Map<ContactMethod, String> contactMethods) {
        if (contactMethods == null)
            throw new NullPointerException();
        this.name = name;
        this.contactMethods = contactMethods;
    }


    public ContactPerson(ContactDTO contact) {
        if (contact == null)
            throw new NullPointerException();
        name = contact.getName();
        contactMethods = contact.getContactMethods();
    }

    public ContactPerson(String name, ContactMethod method, String method_data) {
        this.name = name;
        this.contactMethods = new HashMap<>();
        this.contactMethods.put(method, method_data);
    }

    /***
     * @return a dto object that holds the same data of the the contact object.
     */
    public ContactDTO DTO() {
        return new ContactDTO(name, contactMethods);
    }

    /***
     * Adding a new method for a current contact person.
     * @param method is the new method that we wanna add.
     * @param method_data is the data of the new method like the phone number.
     * @throws Exception if the method is already founded so there is an error.
     */
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

    /***
     *  Editing a current method of a current contact person that is appeared on a current supplier's card.
     * @param method is the new method that we wanna add.
     * @param method_data is the data of the new method like the phone number.
     * @throws Exception if the method is not founded so there is an error.
     */
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
