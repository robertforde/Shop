package com.daniel.model;

import java.util.Date;

/**
 * This class is used to hold transaction information for accounts
 * @author Robert Forde
 *
 */
public class AccountDetail {

	private Date theDate;
	private int number;
	private String type;
	float amount;
	
	
	public Date getTheDate() {
		return theDate;
	}
	
	public void setTheDate(Date theDate) {
		this.theDate = theDate;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}

}