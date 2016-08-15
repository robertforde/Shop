package com.daniel.model;

import java.util.Date;

/**
 * This class represents a quote's header details which are stored in the quoteheader table in the database
 * @author Robert Forde
 *
 */
public class QuoteHeader {

	private int quotationNo;
	private String repNo = "";
	private String totalExVat = "0.00f";
	private String totalVat = "0.00f";
	private String totalPreRounding = "0.00f";
	private String rounding = "0.00f";
	private String totalPostRounding = "0.00f";
	private Date orderDate;
	private String payType = "";
	private String saleType = "";
	private float totalCost = 0.00f;
	private int custId = 0;
	private String name = "";
	private String address1 = "";
	private String address2 = "";
	private String phone = "";
	
	
	public int getQuotationNo() {
		return quotationNo;
	}
	
	public void setQuotationNo(int quotationNo) {
		this.quotationNo = quotationNo;
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
		
}