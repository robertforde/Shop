package com.daniel.model;

/**
 * This class is used to represent all of the settings fields from the receiptsettings table in the database 
 * @author Robert Forde
 *
 */
public class InvoiceSettings {
	
	private int receiptTopBanner;
	private int receiptTopBannerX;
	private int receiptTopBannerY;
	private int receiptVerticalGridLines;
	private int receiptBodyImage;
	private int receiptBodyImageX;
	private int receiptBodyImageY;
	private float receiptVatRate;
	private String receiptFooterLine1;
	private String receiptFooterLine2;
	private String receiptFooterLine3;

	public int getReceiptTopBanner() {
		return receiptTopBanner;
	}
	
	public void setReceiptTopBanner(int receiptTopBanner) {
		this.receiptTopBanner = receiptTopBanner;
	}
	
	public int getReceiptTopBannerX() {
		return receiptTopBannerX;
	}
	
	public void setReceiptTopBannerX(int receiptTopBannerX) {
		this.receiptTopBannerX = receiptTopBannerX;
	}
	
	public int getReceiptTopBannerY() {
		return receiptTopBannerY;
	}
	
	public void setReceiptTopBannerY(int receiptTopBannerY) {
		this.receiptTopBannerY = receiptTopBannerY;
	}
	
	public int getReceiptVerticalGridLines() {
		return receiptVerticalGridLines;
	}
	
	public void setReceiptVerticalGridLines(int receiptVerticalGridLines) {
		this.receiptVerticalGridLines = receiptVerticalGridLines;
	}
	
	public int getReceiptBodyImage() {
		return receiptBodyImage;
	}
	
	public void setReceiptBodyImage(int receiptBodyImage) {
		this.receiptBodyImage = receiptBodyImage;
	}
	
	public int getReceiptBodyImageX() {
		return receiptBodyImageX;
	}
	
	public void setReceiptBodyImageX(int receiptBodyImageX) {
		this.receiptBodyImageX = receiptBodyImageX;
	}
	
	public int getReceiptBodyImageY() {
		return receiptBodyImageY;
	}
	
	public void setReceiptBodyImageY(int receiptBodyImageY) {
		this.receiptBodyImageY = receiptBodyImageY;
	}
	
	public float getReceiptVatRate() {
		return receiptVatRate;
	}
	
	public void setReceiptVatRate(float receiptVatRate) {
		this.receiptVatRate = receiptVatRate;
	}
	
	public String getReceiptFooterLine1() {
		return receiptFooterLine1;
	}
	
	public void setReceiptFooterLine1(String receiptFooterLine1) {
		this.receiptFooterLine1 = receiptFooterLine1;
	}
	
	public String getReceiptFooterLine2() {
		return receiptFooterLine2;
	}
	
	public void setReceiptFooterLine2(String receiptFooterLine2) {
		this.receiptFooterLine2 = receiptFooterLine2;
	}
	
	public String getReceiptFooterLine3() {
		return receiptFooterLine3;
	}
	
	public void setReceiptFooterLine3(String receiptFooterLine3) {
		this.receiptFooterLine3 = receiptFooterLine3;
	}

}