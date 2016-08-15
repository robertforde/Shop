
package com.daniel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.daniel.model.QuoteHeader;
import com.daniel.model.RetailOrder;
import com.daniel.model.RetailOrderLine;
import com.daniel.model.TradeOrder;
import com.daniel.model.TradeOrderLine;
import com.daniel.utilities.Utilities;

/**
 * This class contains all of the Quote methods for interacting with the Database
 * @author Robert Forde
 *
 */
public class QuoteDaoImpl extends NamedParameterJdbcDaoSupport implements QuoteDao{

	/**
	 * Method that finds from the database a quote based on it's number and return it as a retail order
	 * @param selectedQuoteNo The quotationNo of the quote to find
	 * @return Returns a quote as a Retail Order
	 */
	public RetailOrder loadRetailQuote(int selectedQuoteNo) {
		
		String sql = "SELECT * FROM quoteheader WHERE quotationNo = ?";

		return this.getJdbcTemplate().queryForObject(sql, new Object[]{selectedQuoteNo}, new RetailOrderMapper());
	}
	
	/**
	 * Method to find a quote's quotedetail records and load them into retail order lines and return them as an ArrayList of Retail Order Lines
	 * @param selectQuoteNo The quotationNo of a quote whose detail lines are to be returned
	 * @return An ArrayList of RetailOrderLines that belong to the quote whose number is passed in as a parameter  
	 */
	public ArrayList<RetailOrderLine> loadRetailQuoteDetail(int selectedQuoteNo) {

		String sql = "SELECT * FROM quotedetail WHERE quotationNo = ?";

		return (ArrayList<RetailOrderLine>)this.getJdbcTemplate().query(sql, new Object[]{selectedQuoteNo}, new RetailOrderLineMapper());
		
	}

	/**
	 * Method that finds from the database a quote based on it's number and return it as a trade order
	 * @param selectedQuoteNo The quotationNo of the quote to find
	 * @return Returns a quote as a Trade Order
	 */
	public TradeOrder loadTradeQuote(int selectedQuoteNo) {
		
		String sql = "SELECT * FROM quoteheader WHERE quotationNo = ?";

		return this.getJdbcTemplate().queryForObject(sql, new Object[]{selectedQuoteNo}, new TradeOrderMapper());
	}

	/**
	 * Method to find a quote's quotedetail records and load them into retail order lines and return them as an ArrayList of Trade Order Lines
	 * @param selectedQuoteNo The quotationNo of a quote whose detail lines are to be returned
	 * @return An ArrayList of TradeOrderLines that belong to the quote whose number is passed in as a parameter
	 */
	public ArrayList<TradeOrderLine> loadTradeQuoteDetail(int selectedQuoteNo) {

		String sql = "SELECT * FROM quotedetail WHERE quotationNo = ?";

		return (ArrayList<TradeOrderLine>)this.getJdbcTemplate().query(sql, new Object[]{selectedQuoteNo}, new TradeOrderLineMapper());
		
	}

	/**
	 * Method to return from the database detail lines of a quote as a List of RetailOrderLines
	 * @param selectedQuoteNo The quotationNo of a quote whose detail lines are to be returned
	 * @return The quote detail lines as a List of Retail Order Lines that belong to the quote 
	 */
	public List<RetailOrderLine> loadQuoteDetail(int selectedQuoteNo) {
		
		String sql = "SELECT * FROM quotedetail WHERE quotationNo = ?";
		
		return this.getJdbcTemplate().query(sql, new Object[]{selectedQuoteNo}, new RetailOrderLineMapper());
	}

	/**
	 * Method to find the matching quotations in the database based on a number of selection criteria excluding orders' item codes and descriptions 
	 * @param rdbtnConvertRetailQuotations If true then Retail Quotes are searched, if false then Trade Quotes are searched for
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
			Date txtConvertToDate, String txtConvertPhone, String txtConvertValue) {
		
		String sql = "";
		String saleTypeFilter;
	
		if(rdbtnConvertRetailQuotations)
			saleTypeFilter = "RetailQuote";
		else
			saleTypeFilter = "TradeQuote";
			
		sql = "SELECT * FROM quoteheader WHERE saleType='" + saleTypeFilter + "'";
		
		if(!txtConvertQuotationNo.equals(""))
			sql += " AND quotationNo = " + txtConvertQuotationNo;
		
		if(!txtConvertName.equals(""))
			sql += " AND name LIKE '%" + txtConvertName + "%'";

		if(!txtConvertAddress.equals(""))
			sql += " AND addressLine1 LIKE '%" + txtConvertAddress + "%'";
		
		Date fromDate = txtConvertFromDate;
		if(fromDate != null) {
			java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
			sql += " AND orderDate >= '" + sqlFromDate + "'";
		}
		
		Date toDate = txtConvertToDate;
		if(toDate != null) {
			java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());
			sql += " AND orderDate <= '" + sqlToDate + "'";
		}
					
		if(!txtConvertPhone.equals(""))
			sql += " AND phone LIKE '%" + txtConvertPhone + "%'";
		
		if(!txtConvertValue.equals(""))
			sql += " AND FORMAT(totalPostRounding,2) = " + Float.parseFloat(txtConvertValue);
		
		sql += " ORDER BY quotationNo";
			
		return this.getJdbcTemplate().query(sql, new QuoteHeaderMapper());

	}
	
	/**
	 * Method to find the matching quotations in the database based on a number of selection criteria including order lines' item codes and descriptions 
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
			String txtConvertName, String txtConvertAddress, Date txtConvertFromDate, Date txtConvertToDate, String txtConvertPhone, String txtConvertValue){
		
		String sql = "";
		String saleTypeFilter;
	
		if(rdbtnConvertRetailQuotations)
			saleTypeFilter = "RetailQuote";
		else
			saleTypeFilter = "TradeQuote";

		sql = "SELECT DISTINCT * FROM quoteheader header JOIN quotedetail detail ON header.quotationNo=detail.quotationNo WHERE saleType='" + saleTypeFilter + "'";
		
		if (!txtConvertSrchItemCode.equals(""))
			sql += " AND detail.itemCode LIKE '%" + txtConvertSrchItemCode + "%'";
		
		if (!txtConvertSrchItemDesc.equals(""))
			sql += " AND detail.itemDescription LIKE '%" + txtConvertSrchItemDesc + "%'";
		
		if(!txtConvertQuotationNo.equals(""))
			sql += " AND header.quotationNo = " + txtConvertQuotationNo;
		
		if(!txtConvertName.equals(""))
			sql += " AND header.name LIKE '%" + txtConvertName + "%'";

		if(!txtConvertAddress.equals(""))
			sql += " AND header.addressLine1 LIKE '%" + txtConvertAddress + "%'";
		
		Date fromDate = txtConvertFromDate;
		if(fromDate != null) {
			java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
			sql += " AND header.orderDate >= '" + sqlFromDate + "'";
		}
		
		Date toDate = txtConvertToDate;
		if(toDate != null) {
			java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());
			sql += " AND header.orderDate <= '" + sqlToDate + "'";
		}
					
		if(!txtConvertPhone.equals(""))
			sql += " AND header.phone LIKE '%" + txtConvertPhone + "%'";
		
		if(!txtConvertValue.equals(""))
			sql += " AND FORMAT(totalPostRounding,2) = " + Float.parseFloat(txtConvertValue);
		
		sql += " ORDER BY header.quotationNo";
		
		return this.getJdbcTemplate().query(sql, new QuoteHeaderMapper());
		
	}

	
	/**
	 * Static Inner RowMapper Class that maps RetailOrder members to the columns of the quoteheader table
	 * @author Robert Forde
	 *
	 */
	private static final class RetailOrderMapper implements RowMapper<RetailOrder>{

		@Override
		public RetailOrder mapRow(ResultSet resultSet, int rownum) throws SQLException {

			RetailOrder retailOrder = new RetailOrder();
			retailOrder.setRepNo(resultSet.getString("repNo"));
			retailOrder.setTotalExVat(Utilities.floatToString2Dec(resultSet.getFloat("totalExVat")));
			retailOrder.setTotalVat(Utilities.floatToString2Dec(resultSet.getFloat("totalVat")));
			retailOrder.setTotalPreRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPreRounding")));
			retailOrder.setRounding(Utilities.floatToString2Dec(resultSet.getFloat("rounding")));
			retailOrder.setTotalPostRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPostRounding")));
			retailOrder.setOrderDate(resultSet.getDate("orderDate"));
			retailOrder.setPayType(resultSet.getString("payType"));
			retailOrder.setSaleType("Retail");
			retailOrder.setTotalCost(resultSet.getFloat("totalCostPrice"));
			retailOrder.setCustId(resultSet.getInt("custId"));
			retailOrder.setName(resultSet.getString("name"));
			retailOrder.setAddress1(resultSet.getString("addressLine1"));
			retailOrder.setAddress2(resultSet.getString("addressLine2"));
			retailOrder.setPhone(resultSet.getString("phone"));
			
			return retailOrder;
		}
		
	}

	
	/**
	 * Static Inner RowMapper Class that maps RetailOrderLine members to the columns of the orderdetail table
	 * @author Robert Forde
	 *
	 */
	private static final class RetailOrderLineMapper implements RowMapper<RetailOrderLine>{

		@Override
		public RetailOrderLine mapRow(ResultSet resultSet, int rownum) throws SQLException {

			RetailOrderLine retailOrderLine = new RetailOrderLine();
			retailOrderLine.setItemCode(resultSet.getString("itemCode"));
			retailOrderLine.setItemDescription(resultSet.getString("itemDescription"));
			retailOrderLine.setOrderQty(String.valueOf(resultSet.getInt("qty")));
			retailOrderLine.setItemPrice(Utilities.floatToString2Dec(resultSet.getFloat("price")));
			retailOrderLine.setItemTradePrice(resultSet.getFloat("tradePrice"));
			retailOrderLine.setValueExDiscount(Utilities.floatToString2Dec(resultSet.getFloat("valueExDiscount")));
			retailOrderLine.setDiscountPercent(Utilities.floatToString2Dec(resultSet.getFloat("discPercent")));
			retailOrderLine.setDiscountValue(Utilities.floatToString2Dec(resultSet.getFloat("discValue")));
			retailOrderLine.setValueExVat(Utilities.floatToString2Dec(resultSet.getFloat("valueExVat")));
			retailOrderLine.setLineCostValue(resultSet.getFloat("costPrice"));
			
			return retailOrderLine;
		}
		
	}
	
	
	/**
	 * Static Inner RowMapper Class that maps TradeOrder members to the columns of the quoteheader table
	 * @author Robert Forde
	 *
	 */
	private static final class TradeOrderMapper implements RowMapper<TradeOrder>{

		@Override
		public TradeOrder mapRow(ResultSet resultSet, int rownum) throws SQLException {
				
			TradeOrder tradeOrder = new TradeOrder();
			tradeOrder.setRepNo(resultSet.getString("repNo"));
			tradeOrder.setTotalExVat(Utilities.floatToString2Dec(resultSet.getFloat("totalExVat")));
			tradeOrder.setTotalVat(Utilities.floatToString2Dec(resultSet.getFloat("totalVat")));
			tradeOrder.setTotalPreRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPreRounding")));
			tradeOrder.setRounding(Utilities.floatToString2Dec(resultSet.getFloat("rounding")));
			tradeOrder.setTotalPostRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPostRounding")));
			tradeOrder.setOrderDate(resultSet.getDate("orderDate"));
			tradeOrder.setPayType(resultSet.getString("payType"));
			tradeOrder.setSaleType("Retail");
			tradeOrder.setTotalCost(resultSet.getFloat("totalCostPrice"));
			tradeOrder.setCustId(resultSet.getInt("custId"));
			tradeOrder.setName(resultSet.getString("name"));
			tradeOrder.setAddress1(resultSet.getString("addressLine1"));
			tradeOrder.setAddress2(resultSet.getString("addressLine2"));
			tradeOrder.setPhone(resultSet.getString("phone"));
			
			return tradeOrder;
		}
			
	}
	
	
	/**
	 * Static Inner RowMapper Class that maps TradeOrderLine members to the columns of the orderdetail table
	 * @author Robert Forde
	 *
	 */
	private static final class TradeOrderLineMapper implements RowMapper<TradeOrderLine>{

		@Override
		public TradeOrderLine mapRow(ResultSet resultSet, int rownum) throws SQLException {

			TradeOrderLine tradeOrderLine = new TradeOrderLine();
			tradeOrderLine.setItemCode(resultSet.getString("itemCode"));
			tradeOrderLine.setItemDescription(resultSet.getString("itemDescription"));
			tradeOrderLine.setOrderQty(String.valueOf(resultSet.getInt("qty")));
			tradeOrderLine.setItemPrice(Utilities.floatToString2Dec(resultSet.getFloat("price")));
			tradeOrderLine.setItemTradePrice(resultSet.getFloat("tradePrice"));
			tradeOrderLine.setValueExDiscount(Utilities.floatToString2Dec(resultSet.getFloat("valueExDiscount")));
			tradeOrderLine.setValueExVat(Utilities.floatToString2Dec(resultSet.getFloat("valueExVat")));
			tradeOrderLine.setLineCostValue(resultSet.getFloat("costPrice"));
			
			return tradeOrderLine;
		}
		
	}

	
	/**
	 * Static Inner RowMapper Class that maps RetailOrder members to the columns of the quoteheader table
	 * @author Robert Forde
	 *
	 */
	private static final class QuoteHeaderMapper implements RowMapper<QuoteHeader>{

		@Override
		public QuoteHeader mapRow(ResultSet resultSet, int rownum) throws SQLException {

			QuoteHeader quoteHeader = new QuoteHeader();
			quoteHeader.setQuotationNo(resultSet.getInt("quotationNo"));
			quoteHeader.setRepNo(resultSet.getString("repNo"));
			quoteHeader.setTotalExVat(Utilities.floatToString2Dec(resultSet.getFloat("totalExVat")));
			quoteHeader.setTotalVat(Utilities.floatToString2Dec(resultSet.getFloat("totalVat")));
			quoteHeader.setTotalPreRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPreRounding")));
			quoteHeader.setRounding(Utilities.floatToString2Dec(resultSet.getFloat("rounding")));
			quoteHeader.setTotalPostRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPostRounding")));
			quoteHeader.setOrderDate(resultSet.getDate("orderDate"));
			quoteHeader.setPayType(resultSet.getString("payType"));
			quoteHeader.setSaleType("Retail");
			quoteHeader.setTotalCost(resultSet.getFloat("totalCostPrice"));
			quoteHeader.setCustId(resultSet.getInt("custId"));
			quoteHeader.setName(resultSet.getString("name"));
			quoteHeader.setAddress1(resultSet.getString("addressLine1"));
			quoteHeader.setAddress2(resultSet.getString("addressLine2"));
			quoteHeader.setPhone(resultSet.getString("phone"));
			
			return quoteHeader;
		}
	}

}
