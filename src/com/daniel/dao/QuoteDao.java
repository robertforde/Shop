package com.daniel.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.daniel.model.QuoteHeader;
import com.daniel.model.RetailOrder;
import com.daniel.model.RetailOrderLine;
import com.daniel.model.TradeOrder;
import com.daniel.model.TradeOrderLine;

public interface QuoteDao {

	/**
	 * Method that finds from the database a quote based on it's number and return it as a retail order
	 * @param selectedQuoteNo The quotationNo of the quote to find
	 * @return Returns a quote as a Retail Order
	 */
	public RetailOrder loadRetailQuote(int selectedQuoteNo);
	
	/**
	 * Method to find a quote's quotedetail records and load them into retail order lines and return them as an ArrayList
	 * @param selectQuoteNo The quotationNo of a quote whose detail lines are to be returned
	 * @return An ArrayList of RetailOrderLines that belong to the quote whose number is passed in as a parameter  
	 */  
	public ArrayList<RetailOrderLine> loadRetailQuoteDetail(int selectedQuoteNo);
	
	/**
	 * Method that finds from the database a quote based on it's number and return it as a trade order
	 * @param selectedQuoteNo The quotationNo of the quote to find
	 * @return Returns a quote as a Trade Order
	 */
	public TradeOrder loadTradeQuote(int selectedQuoteNo);
	
	/**
	 * Method to find a quote's quotedetail records and load them into retail order lines and return them as an ArrayList of Trade Order Lines
	 * @param selectedQuoteNo The quotationNo of a quote whose detail lines are to be returned
	 * @return An ArrayList of TradeOrderLines that belong to the quote whose number is passed in as a parameter
	 */  
	public ArrayList<TradeOrderLine> loadTradeQuoteDetail(int selectedQuoteNo);
	
	/**
	 * Method to return from the database detail lines of a quote as a List of RetailOrderLines
	 * @param selectedQuoteNo The quotationNo of a quote whose detail lines are to be returned
	 * @return The quote detail lines as a List of Retail Order Lines that belong to the quote 
	 */ 
	public List<RetailOrderLine> loadQuoteDetail(int selectedQuoteNo);
	
	/**
	 * Method to find the matching quotations in the database based on a number of selection criteria 
	 * @param rdbtnConvertRetailQuotations If true then Retail Quotes are searched, if false then TradeQuotes are searched for
	 * @param txtConvertQuotationNo A Quotation Number to search for, if empty then ignore this field
	 * @param txtConvertName The quote's person's name to search for, if empty then ignore this field
	 * @param txtConvertAddress The quote's person's address to search for, if empty then ignore this field
	 * @param txtConvertFromDate The quote's from date range, if null then ignore
	 * @param txtConvertToDate The quote's to date range, if null then ignore
	 * @param txtConvertPhone The quote's person's phone number to search for, if empty then ignore this field
	 * @param txtConvertValue The quote's value to search for, if empty then ignore this field
	 * @return Returns a List of QuoteHeader objects representing the quotes from the Database that match the passed in selection criteria
	 */
	public List<QuoteHeader> filterQuotes(boolean rdbtnConvertRetailQuotations, String txtConvertQuotationNo, String txtConvertName, String txtConvertAddress, Date txtConvertFromDate, 
			Date txtConvertToDate, String txtConvertPhone, String txtConvertValue);
	
	/**
	 * Method to find the matching quotations in the database based on a number of selection criteria including orders' item codes and descriptions 
	 * @param rdbtnConvertRetailQuotations If true then Retail Quotes are searched, if false then TradeQuotes are searched for
	 * @param txtConvertSrchItemCode An Item Code to be search for on quotes' detail lines, if empty then ignore this field 
	 * @param txtConvertSrchItemDesc An Item Description to be search for on quotes' detail lines, if empty then ignore this field
	 * @param txtConvertQuotationNo A Quotation Number to search for, if empty then ignore this field
	 * @param txtConvertName The quote's person's name to search for, if empty then ignore this field
	 * @param txtConvertAddress The quote's person's address to search for, if empty then ignore this field
	 * @param txtConvertFromDate The quote's from date range, if null then ignore
	 * @param txtConvertToDate The quote's to date range, if null then ignore
	 * @param txtConvertPhone The quote's person's phone number to search for, if empty then ignore this field
	 * @param txtConvertValue The quote's value to search for, if empty then ignore this field
	 * @return Returns a List of QuoteHeader objects representing the quotes from the Database that match the passed in selection criteria
	 */
	public List<QuoteHeader> filterQuotesByItem(boolean rdbtnConvertRetailQuotations, String txtConvertSrchItemCode, String txtConvertSrchItemDesc, String txtConvertQuotationNo, 
			String txtConvertName, String txtConvertAddress, Date txtConvertFromDate, Date txtConvertToDate, String txtConvertPhone, String txtConvertValue);
	
}
