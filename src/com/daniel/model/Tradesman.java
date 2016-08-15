package com.daniel.model;

/**
 * This class represents tradesman objects which are held in the tradesman table in the database
 * @author Robert Forde
 *
 */
public class Tradesman {

	private int tradesManId;
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String phone;
	private int deleted;
	
	
	public int getTradesManId() {
		return tradesManId;
	}
	
	public void setTradesManId(int tradesManId) {
		this.tradesManId = tradesManId;
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
	public Tradesman(){}
	
	
	/**
	 * Constructor that constructs the object and assigns all of the members of the object
	 * @param tradesManId The ID of the tradesman
	 * @param name The name of the tradesman
	 * @param addressLine1 The first line of the tradesman's address
	 * @param addressLine2 The second line of the tradesman's address
	 * @param phone The phone of the tradesman
	 * @param deleted A deleted flag (1 represents deleted and 0 represents not deleted)
	 */
	public Tradesman(int tradesManId, String name, String addressLine1, String addressLine2, String phone, int deleted) {
		super();
		this.tradesManId = tradesManId;
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.phone = phone;
		this.deleted = deleted;
	}

	
	/**
	 * Constructor that constructs the object and assigns all of the members of the object except the tradesManId that the database assigns
	 * @param name The name of the tradesman
	 * @param addressLine1 The first line of the tradesman's address
	 * @param addressLine2 The second line of the tradesman's address
	 * @param phone The phone of the tradesman
	 * @param deleted A deleted flag (1 represents deleted and 0 represents not deleted)
	 */
	public Tradesman(String name, String addressLine1, String addressLine2, String phone, int deleted) {
		super();
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.phone = phone;
		this.deleted = deleted;
	}

}