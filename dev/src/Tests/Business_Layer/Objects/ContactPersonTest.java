package Business_Layer.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import enums.ContactMethod;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContactPersonTest {
    private static ContactPerson contact;

    @BeforeAll
    static void setUp() { // done
        try {
            Map<ContactMethod,String> methods = new HashMap<>();
            methods.put(ContactMethod.Email, "Alis@outlook.com");
            methods.put(ContactMethod.Mobile, "0537021127");
            contact = new ContactPerson("Alis", methods);
        }
        catch (Exception e){
            fail();
        }

    }

    @AfterEach
    void tearDown() { // done
        try {
            Map<ContactMethod,String> methods = new HashMap<>();
            methods.put(ContactMethod.Email, "Alis@outlook.com");
            methods.put(ContactMethod.Mobile, "0537021127");
            contact = new ContactPerson("Alis", methods);
        }
        catch (Exception e){
            fail();
        }

    }

    @Test
    void addMethod() { // done
        try {
            assertEquals(2, contact.getContactMethods().size());
            contact.AddMethod(ContactMethod.Fax, "088888888");
            assertEquals(3, contact.getContactMethods().size());
            assertTrue(contact.getContactMethods().containsKey(ContactMethod.Fax));
            assertEquals("088888888", contact.getContactMethods().get(ContactMethod.Fax));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void removeMethod() { // done
        try {
            assertEquals(2, contact.getContactMethods().size());
            contact.RemoveMethod(ContactMethod.Mobile);
            assertTrue(contact.getContactMethods().containsKey(ContactMethod.Mobile));
            assertEquals(1, contact.getContactMethods().size());

        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void editMethod() { // done
        try {
            contact.EditMethod(ContactMethod.Email, "Alis2021@outlook.com");
            assertTrue(contact.getContactMethods().containsKey(ContactMethod.Email));
            assertNotEquals("Alis@outlook.com",contact.getContactMethods().get(ContactMethod.Email));
            assertEquals("Alis2021@outlook.com",contact.getContactMethods().get(ContactMethod.Email));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void getName() { // done
        try {
            assertEquals("Alis", contact.getName());
        }
        catch (Exception e){
            fail();
        }

    }

    @Test
    void getContactMethods() { // done
        try {
            Map<ContactMethod, String> methods = contact.getContactMethods();
            assertTrue(methods.containsKey(ContactMethod.Email));
            assertTrue(methods.containsKey(ContactMethod.Mobile));
            assertEquals("0537021127", methods.get(ContactMethod.Mobile));
            assertEquals("Alis@outlook.com", methods.get(ContactMethod.Email));
            assertEquals(2, methods.size());
        }
        catch (Exception e){
            fail();
        }
    }
}