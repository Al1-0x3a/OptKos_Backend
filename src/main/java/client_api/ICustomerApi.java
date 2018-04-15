package client_api;

import data_models.Customer;

import java.util.List;
import java.util.UUID;

public interface ICustomerApi {

	public Customer getCustomer(UUID customerId);

	public List<Customer> getCustomers();

	public Boolean setCustomer();

}