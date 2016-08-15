package com.daniel.model;

import java.util.Date;

/**
 * This class id used to represent transaction objects which are account statements as of when the transaction took place, so they include the 
 * balance as of the time when the transaction took place 
 * @author Robert Forde
 *
 */
public class Transaction {

	private int number;
	private String date;
	private Date utilDate;
	private String type;
	private String amount;
	private String balance;
	
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public Date getUtilDate() {
		return utilDate;
	}
	
	public void setUtilDate(Date utilDate) {
		this.utilDate = utilDate;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getBalance() {
		return balance;
	}
	
	public void setBalance(String balance) {
		this.balance = balance;
	}

	
	/**
	 * Constructsa a transaction object while assigning all of the member fields
	 * @param number
	 * @param date
	 * @param utilDate
	 * @param type
	 * @param amount
	 * @param balance
	 */
	public Transaction(int number, String date, Date utilDate, String type, String amount, String balance) {

		this.number = number;
		this.date = date;
		this.utilDate = utilDate;
		this.type = type;
		this.amount = amount;
		this.balance = balance;
	}
		
}