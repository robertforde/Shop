package com.daniel.model;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.Date;

import com.daniel.AccountDetailScreen;
import com.daniel.AccountListScreen;

/**
 * This abstract class represents the common members, getters, setters and methods that all orders need  
 * @author Robert Forde
 *
 */
public abstract class Order {

	private int receiptNo;
	private String repNo = "";
	private String totalExVat = "0.00";
	private String totalVat = "0.00";
	private String totalPreRounding = "0.00";
	private String rounding = "0.00";
	private String totalPostRounding = "0.00";
	private Date orderDate;
	private String payType = "";
	private String saleType = "";
	private float totalCost = 0.00f;
	private int orderLinesToPrint;
	private int totalPage;
	private int custId = 0;
	private String name = "";
	private String address1 = "";
	private String address2 = "";
	private String phone = "";


	public int getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(int receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getRepNo() {
		return repNo;
	}

	public void setRepNo(String repNo) {
		this.repNo = repNo;
	}

	public String getTotalExVat() {
		return totalExVat;
	}

	public void setTotalExVat(String totalExVat) {
		this.totalExVat = totalExVat;
	}

	public String getTotalVat() {
		return totalVat;
	}

	public void setTotalVat(String totalVat) {
		this.totalVat = totalVat;
	}

	public String getTotalPreRounding() {
		return totalPreRounding;
	}

	public void setTotalPreRounding(String totalPreRounding) {
		this.totalPreRounding = totalPreRounding;
	}

	public String getRounding() {
		return rounding;
	}

	public void setRounding(String rounding) {
		this.rounding = rounding;
	}

	public String getTotalPostRounding() {
		return totalPostRounding;
	}

	public void setTotalPostRounding(String totalPostRounding) {
		this.totalPostRounding = totalPostRounding;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public int getOrderLinesToPrint() {
		return orderLinesToPrint;
	}

	public void setOrderLinesToPrint(int orderLinesToPrint) {
		this.orderLinesToPrint = orderLinesToPrint;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	/**
	 * Abstract pre-printing method that ensures the arraylists are updated and then saves the order details to the orderheader and orderdetail tables for each order type
	 * @param invoiceSettings The invoiceSettings object that determines settings in relation to receipt printing
	 * @param accountDetailScreen A reference to the accountDetailScreen so it can be updated after printing account orders 
	 * @param accountListScreen A reference to the accountListScreen so it can be updated after printing account orders
	 * @param acc A reference to the account object that is being updated if this is an account order that is being printed
	 * @throws Exception The exception that will be thrown if an exception occurs
	 */
	public abstract void printOrder(InvoiceSettings invoiceSettings, AccountDetailScreen accountDetailScreen, AccountListScreen accountListScreen, Account acc) throws Exception;
	
	
	/**
	 * Abstract print method to do the actual printing
	 * @param graphics The graphics object
	 * @param pageFormat The pageFormat
	 * @param pageIndex The pageIndex
	 * @return Returns an int to indicate if the printing was successful
	 * @throws This method can throw a Printer Exception
	 */
	public abstract int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException;
}