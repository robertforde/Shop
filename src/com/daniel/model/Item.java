package com.daniel.model;

/**
 * This class is used to represent an item (product) in the item table of the database
 * @author Robert Forde
 *
 */
public class Item {

	private int itemId;
	private String itemCode;
	private String itemDescription;
	private float retailPrice;
	private float tradePrice;
	private float costPrice;
	private int deleted;
	private int stock;
	private int stockAlert;
	
		
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
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
	
	public float getRetailPrice() {
		return retailPrice;
	}
	
	public void setRetailPrice(float retailPrice) {
		this.retailPrice = retailPrice;
	}

	public float getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(float tradePrice) {
		this.tradePrice = tradePrice;
	}

	public float getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(float costPrice) {
		this.costPrice = costPrice;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStockAlert() {
		return stockAlert;
	}

	public void setStockAlert(int stockAlert) {
		this.stockAlert = stockAlert;
	}

	
	/**
	 * Necessary no-arg constructor
	 */
	public Item(){}
	
	
	/**
	 * Constructor that creates an item object and assigns the initially relevant information
	 * @param itemCode The Item's Code code
	 * @param itemDescription The Item's description
	 * @param retailPrice The Item's retail price
	 * @param tradePrice The Item's trade price 
	 * @param costPrice The Item's cost price
	 */
	public Item(String itemCode, String itemDescription, float retailPrice, float tradePrice, float costPrice) {

		this.itemCode = itemCode;
		this.itemDescription = itemDescription;
		this.retailPrice = retailPrice;
		this.tradePrice = tradePrice;
		this.costPrice = costPrice;
	}
	
}