package com.daniel.model;

/**
 * This class represents the values of order detail lines that are to be refunded
 * @author Robert Forde
 *
 */
public class RefundDetail {

	private int lineNo;
	private String itemCode;
	private String itemDescription;
	private float price;
	private float discPercent;
	private float incVat;
	
	
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
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public float getDiscPercent() {
		return discPercent;
	}
	
	public void setDiscPercent(float discPercent) {
		this.discPercent = discPercent;
	}

	public float getIncVat() {
		return incVat;
	}

	public void setIncVat(float incVat) {
		this.incVat = incVat;
	}

	
	/**
	 * Constructor used to create a RefundDetail object and assign all of it's member fields
	 * @param lineNo
	 * @param itemCode
	 * @param itemDescription
	 * @param price
	 * @param discPercent
	 * @param incVat
	 */
	public RefundDetail(int lineNo, String itemCode, String itemDescription, float price,float discPercent, float incVat) {
		this.lineNo = lineNo;
		this.itemCode = itemCode;
		this.itemDescription = itemDescription;
		this.price = price;
		this.discPercent = discPercent;
		this.incVat = incVat;
	}

}