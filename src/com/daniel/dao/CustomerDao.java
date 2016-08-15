package com.daniel.dao;

import java.util.List;

import com.daniel.model.Customer;


public interface CustomerDao {

	/**
	 * Method to check if a customer already exists in the database
	 * @param customer The Customer Object that is to be inserted
	 * @return Returns the Customer if it exists otherwise a new Customer is returned  
	 */
	public Customer customerExists(Customer customer);
	
	/**
	 * Method to insert a customer into the customer table in the database
	 * @param customer The Customer object to be inserted
	 */
	public void customerInsert(Customer customer);
	
	/**
	 * Method to update a customer in the customer table in the database
	 * @param customer The Customer object to be updated
	 * @return Returns 1 if the update has been successful or 0 if it failed 
	 */
	public int customerUpdate(Customer customer);

	/**
	 * Method to delete a customer from the customer table in the database
	 * @param id A String id of the Customer to be deleted
	 * @return Returns 1 if the update has been successful or 0 if it failed
	 */
	public int customerDelete(String id);
	
	/**
	 * Method to retrieve a customer from the database based on its ID 
	 * @param id The id of the customer to retrieve
	 * @return Returns a Customer object with the details of the Customer retrieved from the database and if not found then a new Customer
	 */ 
	public Customer customerRetrieve(int id);
	
	/**
	 * Method to return a list of all customers from the database that match certain selection criteria
	 * @param selection The table field to apply the filtered on
	 * @param filter The filter to be applied
	 * @return A List of Customer Objects retrieved from the database that match the criteria passed
	 */
	public List<Customer> customerLoadFiltered(String selection, String filter);
	
	/**
	 *  Method to find return a list of customers based on certain user selected filters
	 *  @param name The name to filter on, or blank if no filter
	 *  @param address1 The address1 to filter on, or blank if no filter 
	 *  @param address2 The address2 to filter on, or blank if no filter
	 *  @param phone The phone to filter on, or blank if no filter
	 *  @return Returns a List of Customer objects from the database that match the criteria passed
	 */ 
	public List<Customer> findFilteredCustomer(String name, String address1, String address2, String phone);

	/**
	 * Method to find the customer details in the customer table based on id and return it
	 * @param id The id of the Customer to retrieve
	 * @return Returns the Customer object from the database
	 */
	public Customer selectFilteredCustomer(int id);
	
}