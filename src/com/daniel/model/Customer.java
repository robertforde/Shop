package com.daniel.model;

/**
 * This class is used to represent all of the fields of a customer as per the customer table in the database
 * @author Robert Forde
 *
 */
public class Customer {

	private int customerId;
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String phone;
	private int deleted;
	
	
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddressLine1() {
		return addressLine1;
	}
	
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	
	public String getAddressLine2() {
		return addressLine2;
	}
	
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public int getDeleted() {
		return deleted;
	}
	
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	
	/**
	 * Necessary no-arg constructor
	 */
	public Customer(){}
	
	
	/**
	 * Constructor of a Customer object with all of the fields assigned
	 * @param tradesManId
	 * @param name
	 * @param addressLine1
	 * @param addressLine2
	 * @param phone
	 * @param deleted
	 */
	public Customer(int tradesManId, String name, String addressLine1, String addressLine2, String phone, int deleted) {
		super();
		this.customerId = tradesManId;
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.phone = phone;
		this.deleted = deleted;
	}

	
	/**
	 * Constructor of a Customer object with all of the fields assigned except the database assigned customerId
	 * @param name Customer's name
	 * @param addressLine1 Customer's first line of their address
	 * @param addressLine2 Customer's second line of their address
	 * @param phone Customer's phone number
	 * @param deleted Customer's deleted flag (1 for deleted, 0 for non-deleted)
	 */
	public Customer(String name, String addressLine1, String addressLine2, String phone, int deleted) {
		super();
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.phone = phone;
		this.deleted = deleted;
	}

}