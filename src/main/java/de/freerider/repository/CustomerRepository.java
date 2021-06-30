package de.freerider.repository;

import de.freerider.datamodel.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;


@Component
@Qualifier("CustomerRepository_Impl")
class CustomerRepository implements CrudRepository<Customer, String> {
	private final HashSet<Customer> customers = new HashSet<>();
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );

	@Override
	public <S extends Customer> S save(S entity) {
		if(entity == null) {
			throw new IllegalArgumentException();
		}
		else if(entity.getId() == null || entity.getId().equals("")) {
			String id = idGen.nextId();
			entity.setId(id);
		}
		if (!existsById(entity.getId())) {
			customers.add(entity);
		}
		//ID already present
		else {
			Optional<Customer> co = findById(entity.getId());
			if(co.isPresent()) {
				Customer customer = co.get();
				customers.remove(customer);
				customers.add(entity);
				entity = (S) customer;
			}
		}
		return entity;
	}

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
		if(entities == null) {
			throw new IllegalArgumentException();
		}
		for(Customer customer : entities) {
			if(customer == null) {
				throw new IllegalArgumentException();
			}
			else if(customer.getId() == null || customer.getId().equals("")) {
				String id = idGen.nextId();
				customer.setId(id);
			}
			if(!existsById(customer.getId())) {
				customers.add(customer);
			}
			else {
				Optional<Customer> co = findById(customer.getId());
				if(co.isPresent()) {
					Customer cust = co.get();
					customers.remove(cust);
					customers.add(customer);
					//entities = (S) cust;
				}
				/*findById(customer.getId()).ifPresent(c -> {
					customers.remove(c);
					customers.add(customer);
					entities = (S) customers;
				});*/
			}
		}
		return entities;
	}

	@Override
	public Optional<Customer> findById(String s) {
		if(s == null) {
			throw new IllegalArgumentException();
		}
		for(Customer customer : customers) {
			if(customer.getId().equals(s)) {
				return Optional.of(customer);
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean existsById(String s) {
		if(s == null) {
			throw new IllegalArgumentException();
		}
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
		return new HashSet<Customer>();
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<String> strings) {
		if(strings == null) {
			throw new IllegalArgumentException();
		}
		HashSet<Customer> queriedCustomers = new HashSet<>();
		for(Customer customer : customers) {
			for(String id : strings) {
				if(id == null) {
					throw new IllegalArgumentException();
				}
				else if(customer.getId().equals(id)) {
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
		if(s == null) {
			throw new IllegalArgumentException();
		}
		customers.removeIf(customer -> customer.getId().equals(s));
	}

	@Override
	public void delete(Customer entity) {
		if(entity == null || entity.getId() == null) {
			throw new IllegalArgumentException();
		}
		customers.remove(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		if(ids == null) {
			throw new IllegalArgumentException();
		}
		for(String id : ids) {
			if(id == null) {
				throw new IllegalArgumentException();
			}
			findById(id).ifPresent(c2 -> customers.remove(c2));
		}
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		if(entities == null) {
			throw new IllegalArgumentException();
		}
		for(Customer customer : entities) {
			if(customer == null || customer.getId() == null) {
				throw new IllegalArgumentException();
			}
			customers.remove(customer);
		}
	}

	@Override
	public void deleteAll() {
		customers.clear();
	}
}
