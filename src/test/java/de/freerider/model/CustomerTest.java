package de.freerider.model;

import de.freerider.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CustomerTest {
    private Customer mats;
    private Customer thomas;

    @BeforeEach
    public void initializeTestCustomers() {
        mats = new Customer(null, null, null);
        thomas = new Customer(null, null, null);
    }

    @Test
    public void testIdNull() {
        assertNull(mats.getId());
        assertNull(thomas.getId());
    }

    @Test
    public void testSetId() {
        mats.setId("C123456");
        thomas.setId("C654321");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
    }

    @Test
    public void testSetIdOnlyOnce() {
        mats.setId("C123456");
        thomas.setId("C654321");

        mats.setId("C77777");
        thomas.setId("C88888");

        assertEquals(mats.getId(), "C123456");
        assertEquals(thomas.getId(), "C654321");
    }

    @Test
    public void testResetId() {
        mats.setId("C123456");
        thomas.setId("C654321");

        mats.setId(null);
        thomas.setId(null);

        assertNull(mats.getId());
        assertNull(thomas.getId());
    }

    @Test
    public void testNamesInitial() {
        assertEquals(mats.getLastName(), "");
        assertEquals(mats.getFirstName(), "");
    }

    @Test
    public void testNamesSetNull() {
        mats.setLastName(null);
        assertEquals(mats.getLastName(), "");

        thomas.setLastName(null);
        assertEquals(thomas.getLastName(), "");

        mats.setFirstName(null);
        assertEquals(mats.getFirstName(), "");

        thomas.setFirstName(null);
        assertEquals(thomas.getFirstName(), "");
    }

    @Test
    public void testSetNames() {
        mats.setLastName("Heine");
        mats.setFirstName("Heinrich");

        assertEquals(mats.getLastName(),"Heine");
        assertEquals(mats.getFirstName(),"Heinrich");
    }

    @Test
    public void testContactsInitial() {
        assertEquals(thomas.getContact(), "");
    }

    @Test
    public void testContactsSetNull() {
        mats.setContact(null);
        assertEquals(mats.getContact(), "");

        thomas.setContact(null);
        assertEquals(thomas.getContact(), "");
    }

    @Test
    public void testSetContacts() {
        mats.setContact("madmats@web.de");
        assertEquals(mats.getContact(),"madmats@web.de");
    }

    @Test
    public void testStatusInitial() {
        assertEquals(mats.getStatus(), Customer.Status.New);
        assertEquals(thomas.getStatus(), Customer.Status.New);
    }

    @Test
    public void testSetStatus() {
        mats.setStatus(Customer.Status.InRegistration);
        assertEquals(mats.getStatus(), Customer.Status.InRegistration);

        thomas.setStatus(Customer.Status.Suspended);
        assertEquals(thomas.getStatus(), Customer.Status.Suspended);

        mats.setStatus(Customer.Status.Active);
        assertEquals(mats.getStatus(), Customer.Status.Active);

        thomas.setStatus(Customer.Status.Deleted);
        assertEquals(thomas.getStatus(), Customer.Status.Deleted);
    }
}
