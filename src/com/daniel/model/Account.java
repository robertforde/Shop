package com.daniel.model;

/**
 * This class represents an account in the account table of the database
 * @author Robert Forde
 *
 */
public class Account {

	private int id;
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String phone;
	private float balance;

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public float getBalance() {
		return balance;
	}
	
	public void setBalance(float balance) {
		this.balance = balance;
	}

	
	/**
	 * No arg constructor
	 */
	public Account(){}
	
	
	/**
	 * Constructor of an account object with all fields bar the database assigned id
	 * @param name Account's name
	 * @param addressLine1 Account's first line of address
	 * @param addressLine2 Account's second line of address
	 * @param phone Account's phone number
	 * @param balance Account's balance
	 */
	public Account(String name, String addressLine1, String addressLine2, String phone, float balance) {
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.phone = phone;
		this.balance = balance;
	}
	
	
	/**
	 * Constructor of an account object with assigning all fields
	 * @param id Account's id
	 * @param name Account's name
	 * @param addressLine1 Account's first line of address
	 * @param addressLine2 Account's second line of address
	 * @param phone Account's phone number
	 * @param balance Account's balance	 */
	public Account(int id, String name, String addressLine1, String addressLine2, String phone, float balance) {
		this.id = id;
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.phone = phone;
		this.balance = balance;
	}
}