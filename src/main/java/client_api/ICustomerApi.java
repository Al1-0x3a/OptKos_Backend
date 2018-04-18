package client_api;

import data_models.Customer;

import java.util.List;
import java.util.UUID;

public interface ICustomerApi {

	Customer getCustomer(UUID customerId);

	List<Customer> getCustomers();

	boolean createCustomer(Customer customer);

	boolean updateCustomer(Customer customer);

}