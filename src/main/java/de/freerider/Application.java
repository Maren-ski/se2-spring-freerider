package de.freerider;

import de.freerider.model.Customer;
import de.freerider.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

@SpringBootApplication
public class Application {
	@Autowired
	CrudRepository<Customer,String> customerManager;

	@EventListener(ApplicationReadyEvent.class)
	public void doWhenApplicationReady() {

		Customer bernd = new Customer("Müller", "Bernd", "bernd.mueller@gmail.com");
		Customer rita = new Customer("Schneider", "Rita", "rita37@web.de");
		Customer cem = new Customer("Erdogan", "Cem", "cem.e@web.de");
		Customer miriam = new Customer("Gehrke", "Miriam", "m.gehrke@t-online.de");
		Customer ava = new Customer("Mahmoud", "Ava", "ava_mahmoud@gmail.com");

		//Save single customer
		customerManager.save(bernd);
		customerManager.save(rita);
		customerManager.save(ava);

		//Save multiple customers
		ArrayList<Customer> newCustomers = new ArrayList<>();
		newCustomers.add(cem);
		newCustomers.add(miriam);
		customerManager.saveAll(newCustomers);

		//Count number of customers in repository
		long customerCount = customerManager.count();
		System.out.println();
		System.out.println("Anzahl Customers: " + customerCount);

		//Find all customers in repository and print their ids and first names
		Iterable<Customer> allCustomers = customerManager.findAll();
		System.out.println();
		for (Customer customer : allCustomers) {
			System.out.println(customer.getId() + " : " + customer.getFirstName());
		}

		//Find customer by id and print his/her first name
		Optional<Customer> findById = customerManager.findById(bernd.getId());
		System.out.println();
		System.out.println("Bernd wird gesucht und gefunden: ");
		System.out.println(findById.get().getFirstName());

		//Find multiple customers by id
		String berndId = bernd.getId();
		String avaId = ava.getId();
		ArrayList<String> ids = new ArrayList<>();
		ids.add(berndId);
		ids.add(avaId);
		Iterable<Customer> queriedCustomers = customerManager.findAllById(ids);
		System.out.println();
		System.out.println("Ava und Bernd: ");
		for (Customer customer : queriedCustomers) {
			System.out.println(customer.getFirstName());
		}

		//Check if customer exists by id
		System.out.println();
		System.out.println("Existiert: " + customerManager.existsById(bernd.getId()));

		//Delete specific customer
		customerManager.delete(bernd);
		System.out.println();
		System.out.println("Bernd ist gelöscht: ");
		for (Customer customer : allCustomers) {
			System.out.println(customer.getId() + " : " + customer.getFirstName());
		}

		//Delete specific customer by id
		customerManager.deleteById(rita.getId());
		System.out.println();
		System.out.println("Rita ist gelöscht: ");
		for (Customer customer : allCustomers) {
			System.out.println(customer.getId() + " : " + customer.getFirstName());
		}

		//Save Cem again to test if the same customer can only be saved once
		customerManager.save(cem);
		Iterable<Customer> allCustomersUpdated = customerManager.findAll();
		System.out.println();
		System.out.println("Cem ist nur einmal drin: ");
		for (Customer customer : allCustomersUpdated) {
			System.out.println(customer.getId() + " : " + customer.getFirstName());
		}

		//Delete multiple customers; auskommentiert, weil sonst doppelt gelöscht wird, funktioniert aber
		/*customerManager.deleteAll(newCustomers);
		for (Customer customer : allCustomers) {
			System.out.println();
			System.out.println("Cem und Miriam sind gelöscht: ");
			System.out.println(customer.getId() + " : " + customer.getFirstName());
		}*/

		//TODO: concurrent modification exception ???
		//Delete multiple customers by id
		String cemId = cem.getId();
		String miriamId = miriam.getId();
		ArrayList<String> ids1 = new ArrayList<>();
		ids1.add(cemId);
		ids1.add(miriamId);
		customerManager.deleteAllById(ids1);
		for (Customer customer : allCustomers) {
			System.out.println();
			System.out.println("Cem und Miriam sind gelöscht: ");
			System.out.println(customer.getId() + " : " + customer.getFirstName());
		}

		//Delete all customers from repository
		customerManager.deleteAll();
		System.out.println();
		System.out.println("Alle sind gelöscht: " + customerManager.findAll());
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
