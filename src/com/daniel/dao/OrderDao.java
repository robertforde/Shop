package com.daniel.dao;

import com.daniel.model.Order;
import com.daniel.model.RetailOrderLine;
import com.daniel.model.TradeOrderLine;

public interface OrderDao {


	/**
	 * Method to update the rounding and totalPostRounding fields of an orderheader or quoteheader record in the database
	 * @param header A String containing orderHeader to update an order or anything else to update a quotation
	 * @param rounding The new rounding value
	 * @param postRounding The new postRounding value
	 * @param receiptNo The receipt number of the order or quotation
	 */
	public void updateRoundingHeader(String header, float rounding, String postRounding, int receiptNo);
	
	/**
	 * Method to get the next receipt number from either the orderheader table or the quoteheader table in the database
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param headerTable The name of the table to get the next number from: orderheader or quoteheader
	 * @return Returns the next available number
	 */
	public int findNextReceiptNo(String typeNo, String headerTable);
	
	/**
	 * Method to save header details into the appropriate header table
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param headerTable The name of the table to get the next number from: orderheader or quoteheader
	 * @param order The order object to be save to the database
	 */
	public void insertHeaderOrder(String typeNo, String headerTable, Order order);
	
	/**
	 * Method to save the retail detail line, either an order or a quote into the appropriate table
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param detailTable The name of the table to get the next number from: orderheader or quoteheader
	 * @param retailOrderLine The retail order/quote line to be inserted
	 */
	public void insertRetailDetailOrder(String typeNo, String detailTable, RetailOrderLine retailOrderLine);
	
	/**
	 * Method to save the trade detail line (order/quote) passed in, to the passed in table
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param detailTable The name of the table to get the next number from: orderheader or quoteheader
	 * @param tradeOrderLine The retail order/quote line to be inserted
	 */
	public void insertTradeDetailOrder(String typeNo, String detailTable, TradeOrderLine tradeOrderLine);
	
	/**
	 * Method to Update Retail header details into the appropriate header table
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param headerTable The name of the table to get the next number from: orderheader or quoteheader
	 * @param order The order/quote to be updated 
	 */
	public void updateHeaderOrder(String typeNo, String headerTable, Order order);
	
	/**
	 * Method to detail all of the detail lines for a specific order header
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param detailTable The name of the table to delete from: orderdetail or quotedetail
	 * @param number The order's receiptNo or quote's quotationNo  
	 */
	public void deleteDetailOrder(String typeNo, String detailTable, int number);
	
}
