package de.freerider.repository;

import de.freerider.datamodel.Customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    CrudRepository<Customer,String> customerManager;
    private Customer mats;
    private Customer thomas;

    @BeforeEach
    public void resetRepository() {
        customerManager.deleteAll();
    }

    @Test
    public void testInitialRepositoryEmpty() {
        assertEquals(customerManager.count(), 0);
    }

    @Test
    public void testSavedSuccessfully() {
        mats = new Customer("heine", "mats", "23");
        customerManager.save(mats);
        String matsID = mats.getId();

        assertEquals(customerManager.count(), 1);
        assertTrue(customerManager.existsById(matsID));
    }

    @Test
    public void testGotID() {
        thomas = new Customer("heine", "mats", "23");
        customerManager.save(thomas);
        String thomasID = thomas.getId();

        assertTrue(customerManager.existsById(thomasID));
    }

    @Test
    public void testIdUnchangeable() {
        thomas = new Customer("hase", "tommy", "0151");
        customerManager.save(thomas);
        String thomasID = thomas.getId();
        thomas.setId("C477704");
        assertEquals(thomas.getId(), thomasID);
    }

    @Test
    public void testSaveSameObjectTwice() {
        thomas = new Customer("hase", "tommy", "0151");
        customerManager.save(thomas);
        String id1 = thomas.getId();
        long count1 = customerManager.count();
        customerManager.save(thomas);
        String id2 = thomas.getId();
        long count2 = customerManager.count();

        assertNotNull(id1);
        assertEquals(id1, id2);
        assertEquals(count1, 1);
        assertEquals(count2, 1);
    }

    @Test
    public void testSaveWithSameID() {
        thomas = new Customer("müller", "tommy", "23");
        mats = new Customer("heine", "mats", "23");
        thomas.setId("abcd");
        mats.setId("abcd");
        customerManager.save(thomas);
        Optional<Customer> c1 = customerManager.findById("abcd");
        customerManager.save(mats);
        Optional<Customer> c2 = customerManager.findById("abcd");
        long count = customerManager.count();
        assertEquals(c1.get().getLastName(), "müller");
        assertEquals(c2.get().getLastName(), "heine");
        assertEquals(count, 1);
        assertEquals(thomas.getId(), "abcd");
        assertEquals(mats.getId(), "abcd");
    }

    @Test
    public void testSaveNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.save(null);
        });
    }

    @Test
    public void testSavedAllSuccessfully() {
        mats = new Customer(null, null, null);
        thomas = new Customer(null, null, null);
        HashSet<Customer> customers = new HashSet<>();
        customers.add(mats);
        customers.add(thomas);
        customerManager.saveAll(customers);

        assertTrue(customerManager.existsById(mats.getId()));
        assertTrue(customerManager.existsById(thomas.getId()));
    }

    @Test
    public void testAllGotID() {
        mats = new Customer(null, null, null);
        thomas = new Customer(null, null, null);
        HashSet<Customer> customers = new HashSet<>();
        customers.add(mats);
        customers.add(thomas);
        customerManager.saveAll(customers);

        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
    }

    @Test
    public void testIdsUnchangeable() {
        thomas = new Customer("hase", "tommy", "0151");
        mats = new Customer(null, null, null);
        customerManager.save(thomas);
        customerManager.save(mats);
        String thomasID = thomas.getId();
        String matsID = mats.getId();
        thomas.setId("C477704");
        mats.setId("C477705");
        assertEquals(thomas.getId(), thomasID);
        assertEquals(mats.getId(), matsID);
    }

    @Test
    public void testSaveAllNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.saveAll(null);
        });

        thomas = new Customer("hase", "tommy", "0151");
        HashSet<Customer> customers = new HashSet<>();
        customers.add(thomas);
        customers.add(null);

        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.saveAll(customers);
        });
    }

    @Test
    public void testSaveSameObjectsTwice() {
        thomas = new Customer("hase", "tommy", "0151");
        HashSet<Customer> customers = new HashSet<>();
        customers.add(thomas);
        customerManager.saveAll(customers);
        String id1 = thomas.getId();
        long count1 = customerManager.count();
        customerManager.saveAll(customers);
        String id2 = thomas.getId();
        long count2 = customerManager.count();

        assertNotNull(id1);
        assertEquals(id1, id2);
        assertEquals(count1, 1);
        assertEquals(count2, 1);
    }

    @Test
    public void testSaveWithSameIDs() {
        thomas = new Customer("müller", "tommy", "23");
        mats = new Customer("heine", "mats", "23");
        thomas.setId("abcd");
        mats.setId("abcd");
        HashSet<Customer> customers = new HashSet<>();
        customers.add(thomas);
        customers.add(mats);
        customerManager.saveAll(customers);

        Optional<Customer> c1 = customerManager.findById("abcd");
        long count = customerManager.count();
        assertEquals(c1.get().getLastName(), "heine");
        assertEquals(count, 1);
        assertEquals(mats.getId(), "abcd");
    }

    @Test
    public void testFindObjectWithGivenID() {
        thomas = new Customer(null, null, null);
        customerManager.save(thomas);
        assertNotNull(customerManager.findById(thomas.getId()));
    }

    @Test
    public void testFindByIdNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.findById(null);
        });
    }

    @Test
    public void testReturnsTrueIfIdExists() {
        mats = new Customer("heine", "mats", "23");
        customerManager.save(mats);

        assertTrue(customerManager.existsById(mats.getId()));
    }

    @Test
    public void testReturnsFalseIfNoSuchID() {
        assertFalse(customerManager.existsById("C787741"));
    }

    @Test
    public void testExistsByIdNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.existsById(null);
        });
    }

    @Test
    public void testFindObjectsWithGivenIds() {
        mats = new Customer(null, null, null);
        thomas = new Customer(null, null, null);
        customerManager.save(mats);
        customerManager.save(thomas);
        HashSet<String> customerIds = new HashSet<>();
        customerIds.add(mats.getId());
        customerIds.add(thomas.getId());
        HashSet<Customer> customers = new HashSet<>();
        customers.add(mats);
        customers.add(thomas);

        assertEquals(customerManager.findAllById(customerIds), customers);
    }

    @Test
    public void testFindAllByIdNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.findAllById(null);
        });

        thomas = new Customer("hase", "tommy", "0151");
        customerManager.save(thomas);
        HashSet<String > ids = new HashSet<>();
        ids.add(thomas.getId());
        ids.add(null);

        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.findAllById(ids);
        });
    }

    @Test
    public void testFindAllObjects() {
        mats = new Customer(null, null, null);
        thomas = new Customer(null, null, null);
        customerManager.save(mats);
        customerManager.save(thomas);
        HashSet<Customer> customers = new HashSet<>();
        customers.add(mats);
        customers.add(thomas);

        assertEquals(customerManager.findAll(), customers);
    }

    @Test
    public void testReturnsEmptyIterableIfRepositoryEmpty() {
        assertEquals(customerManager.findAll(), new HashSet<Customer>());
    }

    @Test
    public void testReturnsRightCount() {
        mats = new Customer(null, null, null);
        thomas = new Customer(null, null, null);
        customerManager.save(mats);
        customerManager.save(thomas);

        assertEquals(customerManager.count(), 2);
    }

    @Test
    public void testReturnZeroIfEmpty() {
        assertEquals(customerManager.count(), 0);
    }

    @Test
    public void testDeleteGivenObject() {
        thomas = new Customer(null, null, null);
        customerManager.save(thomas);
        customerManager.delete(thomas);

        assertEquals(customerManager.count(), 0);
        assertSame(customerManager.findById(thomas.getId()), Optional.empty());
    }

    @Test
    public void testDeleteNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.delete(null);
        });
    }

    @Test
    public void testDeleteObjectWithGivenId() {
        thomas = new Customer(null, null, null);
        customerManager.save(thomas);
        customerManager.deleteById(thomas.getId());

        assertEquals(customerManager.count(), 0);
        assertSame(customerManager.findById(thomas.getId()), Optional.empty());
    }

    @Test
    public void testDeleteByIdNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.deleteById(null);
        });
    }

    @Test
    public void testDeleteObjectsWithGivenId() {
        thomas = new Customer(null, null, null);
        mats = new Customer(null, null, null);
        customerManager.save(mats);
        customerManager.save(thomas);
        HashSet<String> customerIds = new HashSet<>();
        customerIds.add(mats.getId());
        customerIds.add(thomas.getId());
        customerManager.deleteAllById(customerIds);

        assertEquals(customerManager.count(), 0);
        assertSame(customerManager.findById(thomas.getId()), Optional.empty());
        assertSame(customerManager.findById(mats.getId()), Optional.empty());
    }

    @Test
    public void testDeleteAllByIdNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.deleteAllById(null);
        });

        HashSet<String> customerIds = new HashSet<>();
        thomas = new Customer(null, null, null);
        mats = new Customer(null, null, null);
        customerIds.add(mats.getId());
        customerIds.add(thomas.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.deleteAllById(customerIds);
        });
    }

    @Test
    public void testDeleteGivenObjects() {
        thomas = new Customer(null, null, null);
        mats = new Customer(null, null, null);
        customerManager.save(mats);
        customerManager.save(thomas);
        HashSet<Customer> customers = new HashSet<>();
        customers.add(mats);
        customers.add(thomas);
        customerManager.deleteAll(customers);

        assertEquals(customerManager.count(), 0);
        assertSame(customerManager.findById(thomas.getId()), Optional.empty());
        assertSame(customerManager.findById(mats.getId()), Optional.empty());
    }

    @Test
    public void testDeleteGivenObjectsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.deleteAll(null);
        });

        HashSet<Customer> customers = new HashSet<>();
        thomas = new Customer(null, null, null);
        customers.add(null);
        customers.add(thomas);

        assertThrows(IllegalArgumentException.class, () -> {
            customerManager.deleteAll(customers);
        });
    }

    @Test
    public void testDeleteAllObjects() {
        thomas = new Customer(null, null, null);
        mats = new Customer(null, null, null);
        customerManager.save(mats);
        customerManager.save(thomas);
        customerManager.deleteAll();

        assertEquals(customerManager.count(), 0);
    }
}
