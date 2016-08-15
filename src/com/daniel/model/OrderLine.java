package com.daniel.model;

/**
 * This class represents the common members, getters and setters that all order detail lines need 
 * @author Robert Forde
 *
 */
public abstract class OrderLine {

	private int receiptNo;
	private int lineNo;
	private String itemCode;
	private String itemDescription;
	private String orderQty;
	private String itemPrice;
	private float itemTradePrice;
	private String valueExDiscount;
	private String valueExVat;
	private float lineCostValue;
	private float refundedValue;
		
	
	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}
	
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	
	public String getOrderQty() {
		return orderQty;
	}
	
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	
	public String getItemPrice() {
		return itemPrice;
	}
	
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public float getItemTradePrice() {
		return itemTradePrice;
	}

	public void setItemTradePrice(float itemTradePrice) {
		this.itemTradePrice = itemTradePrice;
	}

	public int getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(int receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getValueExVat() {
		return valueExVat;
	}

	public void setValueExVat(String valueExVat) {
		this.valueExVat = valueExVat;
	}

	public String getValueExDiscount() {
		return valueExDiscount;
	} 
	
	public void setValueExDiscount(String valueExDiscount) {
		this.valueExDiscount = valueExDiscount;
	}	

	public float getLineCostValue() {
		return lineCostValue;
	}

	public void setLineCostValue(float lineCostValue) {
		this.lineCostValue = lineCostValue;
	}

	public float getRefundedValue() {
		return refundedValue;
	}

	public void setRefundedValue(float refundedValue) {
		this.refundedValue = refundedValue;
	}
	
}