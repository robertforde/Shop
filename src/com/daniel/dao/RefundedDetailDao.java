package com.daniel.dao;

import java.util.Date;
import java.util.List;

import com.daniel.model.RefundedDetail;
import com.daniel.model.RetailOrder;
import com.daniel.model.RetailOrderLine;

public interface RefundedDetailDao {

	/**
	 * Method to find an order's detail lines from the database and return them as a List of Retail Order Line
	 * @param selectedInvoiceNo The order number whose detail lines are to be returned
	 * @return Returns a List of RetailOrderLine objects that are the detail lines of the passed in receiptNo number
	 */
	public List<RetailOrderLine> refundLoadInvoiceDetail(int selectedInvoiceNo);
	
	/**
	 * Method to find the matching orders in the database based on a number of selection criteria excluding orders' item codes and descriptions
	 * @param rdbtnRetailRefund If true then Retail Order are searched
	 * @param rdbtnTradeRefund If true then Trade Orders are searched for
	 * @param txtRefundInvoiceNo An Order Number to search for, if empty then ignore this field
	 * @param txtRefundName The order's person's name to search for, if empty then ignore this field
	 * @param txtRefundFromDate The order's from date range, if null then ignore
	 * @param txtRefundToDate The order's to date range, if null then ignore
	 * @param txtRefundAddress The order's person's address to search for, if empty then ignore this field
	 * @param txtRefundPhone The order's person's phone to search for, if empty then ignore this field
	 * @param txtRefundValue The order's value to search for, if empty then ignore this field
	 * @return Returns a List of Retail Order objects that match the criteria passed 
	 */
	public List<RetailOrder> refundFilterInvoices(boolean rdbtnRetailRefund, boolean rdbtnTradeRefund, String txtRefundInvoiceNo, String txtRefundName, 
			Date txtRefundFromDate, Date txtRefundToDate, String txtRefundAddress, String txtRefundPhone, String txtRefundValue);
	
	/**
	 * Method to find the matching orders in the database based on a number of selection criteria including order lines' item codes and descriptions
	 * @param rdbtnRetailRefund If true then Retail Order are searched
	 * @param rdbtnTradeRefund If true then Trade Orders are searched for
	 * @param txtRefundInvoiceNo An Order Number to search for, if empty then ignore this field
	 * @param txtRefundName The order's person's name to search for, if empty then ignore this field
	 * @param txtRefundFromDate The order's from date range, if null then ignore
	 * @param txtRefundToDate The order's to date range, if null then ignore
	 * @param txtRefundAddress The order's person's address to search for, if empty then ignore this field
	 * @param txtRefundPhone The order's person's phone to search for, if empty then ignore this field
	 * @param txtRefundValue The order's value to search for, if empty then ignore this field
	 * @param txtRefundSrchItemCode An Item Code to be searched for on quotes' detail lines, if empty then ignore this field
	 * @param txtRefundSrchItemDesc An Item Description to be searched for on quotes' detail lines, if empty then ignore this field
	 * @return Returns a List of Retail Order objects that match the criteria passed
	 */
	public List<RetailOrder> refundFilterInvoicesByItem(boolean rdbtnRetailRefund, boolean rdbtnTradeRefund, String txtRefundInvoiceNo, String txtRefundName, 
			Date txtRefundFromDate, Date txtRefundToDate, String txtRefundAddress, String txtRefundPhone, String txtRefundValue, 
			String txtRefundSrchItemCode, String txtRefundSrchItemDesc);
	
	/**
	 * Method to update the orderdetail and refundeddetail tables based on the items that are being refunded
	 * @param refundedDetail A RefundedDetail object that is being refunded
	 */ 
	public void refundUpdateDetail(RefundedDetail refundedDetail);
	
	/**
	 * Method to update the orderheader table based on the items that are being refunded
	 * @param selectedInvoiceNo The order that is whose detail is being refunded 
	 * @param totExVat The order's new totExVat
	 * @param totVat The order's new totVat
	 * @param totalRefunded The order's new refundValue
	 * @param totCost The order's new Cost value
	 */ 
	public void refundUpdateHeader(int selectedInvoiceNo, float totExVat, float totVat, float totalRefunded, float totCost);
	
}
