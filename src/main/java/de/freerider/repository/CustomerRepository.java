package de.freerider.repository;

import de.freerider.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;


@Component
class CustomerRepository implements CrudRepository<Customer, String> {
	private final HashSet<Customer> customers = new HashSet<>();
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );

	@Override
	public <S extends Customer> S save(S entity) {
		if(entity.getId() == null || entity.getId().equals("")) {
			String id = idGen.nextId();
			entity.setId(id);

			if (!existsById(entity.getId())) {
				customers.add(entity);
			}
		}
		return entity;
	}

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
		for(Customer customer : entities) {
			if(customer.getId() == null || customer.getId().equals("")) {
				String id = idGen.nextId();
				customer.setId(id);

				if(!existsById(customer.getId())) {
					customers.add(customer);
				}
			}
		}
		return entities;
	}

	@Override
	public Optional<Customer> findById(String s) {
		for(Customer customer : customers) {
			if(customer.getId().equals(s)) {
				return Optional.of(customer);
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean existsById(String s) {
		for(Customer customer : customers) {
			if(customer.getId().equals(s)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterable<Customer> findAll() {
		if(!customers.isEmpty()) {
			return customers;
		}
		return null;
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<String> strings) {
		HashSet<Customer> queriedCustomers = new HashSet<>();

		for(Customer customer : customers) {
			for(String id : strings) {
				if(customer.getId().equals(id)) {
					queriedCustomers.add(customer);
				}
			}
		}
		return queriedCustomers;
	}

	@Override
	public long count() {
		return customers.size();
	}

	@Override
	public void deleteById(String s) {
		customers.removeIf(customer -> customer.getId().equals(s));
	}

	@Override
	public void delete(Customer entity) {
		customers.remove(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
			for(String id : ids) {
				findById(id).ifPresent(c2 -> customers.remove(c2));
			}
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		for(Customer customer : entities) {
			customers.remove(customer);
		}
	}

	@Override
	public void deleteAll() {
		customers.clear();
	}
}
