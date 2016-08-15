package com.daniel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.daniel.model.Customer;


/**
 * This class contains all of the Customer methods for interacting with the Database
 * @author Robert Forde
 *
 */
public class CustomerDaoImpl extends NamedParameterJdbcDaoSupport implements CustomerDao{
	
	/**
	 * Method to check if a customer already exists in the database
	 * @param customer The Customer Object that is to be inserted
	 * @return Returns the Customer if it exists otherwise a new Customer is returned  
	 */
	public Customer customerExists(Customer customer) {
		
		try {
			String sql = "SELECT * FROM customer WHERE name = :name AND addressLine1 = :addressLine1 AND addressLine2 = :addressLine2 AND phone = :phone AND deleted = 0";
			
			SqlParameterSource namedParameters = new MapSqlParameterSource("name", customer.getName())
			.addValue("addressLine1", customer.getAddressLine1())
			.addValue("addressLine2", customer.getAddressLine2())
			.addValue("phone", customer.getPhone());
			
			return this.getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, new CustomerMapper());
			
		}catch(EmptyResultDataAccessException e){
		
			return new Customer();
		}

	}
	

	/**
	 * Method to insert a customer into the customer table in the database
	 * @param customer The Customer object to be inserted
	 */
	public void customerInsert(Customer customer) {
		
		String sql = "INSERT INTO customer (name, addressLine1, addressLine2, phone) VALUES(:name, :addressLine1, :addressLine2, :phone)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("name", customer.getName())
													.addValue("addressLine1", customer.getAddressLine1())
													.addValue("addressLine2", customer.getAddressLine2())
													.addValue("phone", customer.getPhone());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);

	}

		
	/**
	 * Method to update a customer in the customer table in the database
	 * @param customer The Customer object to be updated
	 * @return Returns 1 if the update has been successful or 0 if it failed 
	 */
	public int customerUpdate(Customer customer) {
		
		String sql = "UPDATE customer SET name = :name, addressLine1 = :addressLine1, addressLine2 = :addressLine2, phone = :phone WHERE customerId = :id AND deleted = 0";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", customer.getCustomerId())
		.addValue("name", customer.getName())
		.addValue("addressLine1", customer.getAddressLine1())
		.addValue("addressLine2", customer.getAddressLine2())
		.addValue("phone", customer.getPhone());

		return this.getNamedParameterJdbcTemplate().update(sql, namedParameters);

	}

	
	/**
	 * Method to delete a customer from the customer table in the database
	 * @param id A String id of the Customer to be deleted
	 * @return Returns 1 if the update has been successful or 0 if it failed
	 */
	public int customerDelete(String id) {
		
		String sql = "UPDATE customer SET deleted=1 WHERE customerId = ? AND deleted = 0";

		return this.getJdbcTemplate().update(sql, new Object[] {id});
		
	}

	
	/**
	 * Method to retrieve a customer from the database based on its ID 
	 * @param id The id of the customer to retrieve
	 * @return Returns a Customer object with the details of the Customer retrieved from the database and if not found then a new Customer
	 */
	public Customer customerRetrieve(int id) {

		try {
			String sql = "SELECT * FROM customer WHERE customerId = ? AND deleted = 0";
			
			return this.getJdbcTemplate().queryForObject(sql, new Object[] {id}, new CustomerMapper());
			
		}catch(EmptyResultDataAccessException e){
		
			return new Customer();
		}

	}


	/**
	 * Method to return a list of all customers from the database that match certain selection criteria
	 * @param selection The table field to apply the filtered on
	 * @param filter The filter to be applied
	 * @return A List of Customer Objects retrieved from the database that match the criteria passed
	 */
	public List<Customer> customerLoadFiltered(String selection, String filter) {
			
		String sql = "SELECT * FROM customer WHERE " + selection + " LIKE '" + filter + "' AND deleted=0 ORDER BY " + selection;

		return this.getJdbcTemplate().query(sql, new CustomerMapper());
			
	}

	
	/**
	 *  Method to find return a list of customers based on certain user selected filters
	 *  @param name The name to filter on, or blank if no filter
	 *  @param address1 The address1 to filter on, or blank if no filter 
	 *  @param address2 The address2 to filter on, or blank if no filter
	 *  @param phone The phone to filter on, or blank if no filter
	 *  @return Returns a List of Customer objects from the database that match the criteria passed
	 */
	public List<Customer> findFilteredCustomer(String name, String address1, String address2, String phone) {

		// Find the users filters entered and build the sql query string

		boolean first = true;
			
		String sql = "SELECT * FROM customer";
				
		if(!name.equals("")) {
			if(first){
				sql += " WHERE name LIKE '%" + name + "%'";
				first = false;
			} else{
				sql += " AND name LIKE '%" + name + "%'";
			}
		}
			
		if(!address1.equals("")) {
			if(first){
				sql += " WHERE addressLine1 LIKE '%" + address1 + "%'";
				first = false;
			} else{
				sql += " AND addressLine1 LIKE '%" + address1 + "%'";
			}
		}
		
		if(!address2.equals("")) {
			if(first){
				sql += " WHERE addressLine2 LIKE '%" + address2 + "%'";
				first = false;
			} else{
				sql += " AND addressLine2 LIKE '%" + address2 + "%'";
			}
		}
		
		if(!phone.equals("")) {
			if(first){
				sql += " WHERE phone LIKE '%" + phone + "%'";
				first = false;
			} else{
				sql += " AND phone LIKE '%" + phone + "%'";
			}
		}
		
		// If no filters
		if(first) {
			sql += " WHERE deleted=0 ORDER BY name DESC";	
		}else {
			sql += " AND deleted=0 ORDER BY name DESC";						
		}
			
		return this.getJdbcTemplate().query(sql, new CustomerMapper());
			
	}

	
	/**
	 * Method to find the customer details in the customer table based on id and return it
	 * @param id The id of the Customer to retrieve
	 * @return Returns the Customer object from the database
	 */
	public Customer selectFilteredCustomer(int id) {

		String sql = "SELECT * FROM customer WHERE customerID  = :customerID";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("customerID", id);

		return this.getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, new CustomerMapper());
		
	}
	
	
	/**
	 * Static Inner RowMapper Class that maps Customer members to the columns of the customer table
	 * @author Robert Forde
	 *
	 */
	private static final class CustomerMapper implements RowMapper<Customer>{

		@Override
		public Customer mapRow(ResultSet resultSet, int rownum) throws SQLException {

			Customer customer = new Customer();
			customer.setCustomerId(resultSet.getInt("customerId"));
			customer.setName(resultSet.getString("name"));
			customer.setAddressLine1(resultSet.getString("addressLine1"));
			customer.setAddressLine2(resultSet.getString("addressLine2"));
			customer.setPhone(resultSet.getString("phone"));
			customer.setDeleted(resultSet.getInt("deleted"));
			
			return customer;
		}
	}	

}
