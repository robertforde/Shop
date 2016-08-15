package com.daniel.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.daniel.model.Order;
import com.daniel.model.RetailOrderLine;
import com.daniel.model.TradeOrderLine;


/**
 * This class contains all of the Order methods for interacting with the Database
 * @author Robert Forde
 *
 */
public class OrderDaoImpl extends NamedParameterJdbcDaoSupport implements OrderDao{

	/**
	 * Method to update the rounding and totalPostRounding fields of an orderheader or quoteheader record in the database
	 * @param header A String containing orderHeader to update an order or anything else to update a quotation
	 * @param rounding The new rounding value
	 * @param postRounding The new postRounding value
	 * @param receiptNo The receipt number of the order or quotation
	 */
	public void updateRoundingHeader(String header, float rounding, String postRounding, int receiptNo) {
		
		String number = "";
				
		if(header.equals("orderheader"))
			number = "receiptNo";
		else
			number = "quotationNo";
		
		String sql = "UPDATE " + header + " SET rounding = :rounding, totalPostRounding = :totalPostRounding WHERE " + number + " = :receiptNo";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("rounding", rounding)
		.addValue("totalPostRounding", postRounding)
		.addValue("receiptNo", receiptNo);
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
	}

	
	/**
	 * Method to get the next receipt number from either the orderheader table or the quoteheader table in the database
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param headerTable The name of the table to get the next number from: orderheader or quoteheader
	 * @return Returns the next available number
	 */
	public int findNextReceiptNo(String typeNo, String headerTable) {
		
		int nextReceiptNo = 1;
		try {
			
			String sql = "SELECT MAX(" + typeNo + ") AS NUMBER FROM " + headerTable;
		
			nextReceiptNo = this.getJdbcTemplate().queryForObject(sql, Integer.class);
			nextReceiptNo++;
			
		}catch(EmptyResultDataAccessException e){
			return 1;
		}		
		return nextReceiptNo;
		
	}
	
	
	/**
	 * Method to save header details into the appropriate header table
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param headerTable The name of the table to insert into: orderheader or quoteheader
	 * @param order The order object to be save to the database
	 */
	public void insertHeaderOrder(String typeNo, String headerTable, Order order) {
		
		String sql = "INSERT INTO " + headerTable + " (" + typeNo + ", repNo, totalExVat, totalVat, totalPreRounding, rounding, totalPostRounding, orderDate, " + 
				"payType, saleType, totalCostPrice, CustId, Name, AddressLine1, AddressLine2, Phone) VALUES(:number, :repNo, :totalExVat, :totalVat, :totalPreRounding, "
				+ ":rounding, :totalPostRounding, :orderDate, :payType, :saleType, :totalCostPrice, :custId, :name, :address1, :address2, :phone)";

		SqlParameterSource namedParameters = new MapSqlParameterSource("number", order.getReceiptNo())
			.addValue("repNo", order.getRepNo())
			.addValue("totalExVat", order.getTotalExVat())
			.addValue("totalVat", order.getTotalVat())
			.addValue("totalPreRounding", order.getTotalPreRounding())
			.addValue("rounding", order.getRounding())
			.addValue("totalPostRounding", order.getTotalPostRounding())
			.addValue("orderDate", order.getOrderDate())
			.addValue("payType", order.getPayType())
			.addValue("saleType", order.getSaleType())
			.addValue("totalCostPrice", order.getTotalCost())
			.addValue("custId", order.getCustId())
			.addValue("name", order.getName())
			.addValue("address1", order.getAddress1())
			.addValue("address2", order.getAddress2())
			.addValue("phone", order.getPhone());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
	}

	/**
	 * Method to save the retail detail line, either an order or a quote into the appropriate table
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param detailTable The name of the table to insert into: orderdetail or quotedetail
	 * @param retailOrderLine The retail order/quote line to be inserted
	 */
	public void insertRetailDetailOrder(String typeNo, String detailTable, RetailOrderLine retailOrderLine) {
		
		String sql  = "INSERT INTO " + detailTable + " (" + typeNo + ", lineNo, itemCode, itemDescription, qty, price, tradePrice, valueExDiscount, discPercent, discValue, "
				+ "valueExVat, costPrice) VALUES(:number, :lineNo, :itemCode, :itemDescription, :qty, :price, :tradePrice, :valueExDiscount, :discPercent, :discValue, "
				+ ":valueExVat, :costPrice)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("number", retailOrderLine.getReceiptNo())
		.addValue("lineNo", retailOrderLine.getLineNo())
		.addValue("itemCode", retailOrderLine.getItemCode())
		.addValue("itemDescription", retailOrderLine.getItemDescription())
		.addValue("qty", retailOrderLine.getOrderQty())
		.addValue("price", retailOrderLine.getItemPrice())
		.addValue("tradePrice", retailOrderLine.getItemTradePrice())
		.addValue("valueExDiscount", retailOrderLine.getValueExDiscount())
		.addValue("discPercent", retailOrderLine.getDiscountPercent())
		.addValue("discValue", retailOrderLine.getDiscountValue())
		.addValue("valueExVat", retailOrderLine.getValueExVat())
		.addValue("costPrice", retailOrderLine.getLineCostValue());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
	}

	/**
	 * Method to save the trade detail line (order/quote) passed in, to the passed in table
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param detailTable The name of the table to insert into: orderdetail or quotedetail
	 * @param tradeOrderLine The retail order/quote line to be inserted
	 */
	public void insertTradeDetailOrder(String typeNo, String detailTable, TradeOrderLine tradeOrderLine) {
		
		String sql  = "INSERT INTO " + detailTable + " (" + typeNo + ", lineNo, itemCode, itemDescription, qty, price, tradePrice, valueExDiscount, valueExVat, costPrice) "
				+ "VALUES(:number, :lineNo, :itemCode, :itemDescription, :qty, :price, :tradePrice, :valueExDiscount, :valueExVat, :costPrice)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("number", tradeOrderLine.getReceiptNo())
		.addValue("lineNo", tradeOrderLine.getLineNo())
		.addValue("itemCode", tradeOrderLine.getItemCode())
		.addValue("itemDescription", tradeOrderLine.getItemDescription())
		.addValue("qty", tradeOrderLine.getOrderQty())
		.addValue("price", tradeOrderLine.getItemPrice())
		.addValue("tradePrice", tradeOrderLine.getItemTradePrice())
		.addValue("valueExDiscount", tradeOrderLine.getValueExDiscount())
		.addValue("valueExVat", tradeOrderLine.getValueExVat())
		.addValue("costPrice", tradeOrderLine.getLineCostValue());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
	}

	/**
	 * Method to Update Retail header details into the appropriate header table
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param headerTable The name of the table to insert into: orderheader or quoteheader
	 * @param order The order/quote to be updated 
	 */
	public void updateHeaderOrder(String typeNo, String headerTable, Order order){
		
		String sql = "UPDATE " + headerTable + " SET totalExVat = :totalExVat, totalVat = :totalVat, totalPreRounding = :totalPreRounding, rounding = :rounding, "
				+ "totalPostRounding = :totalPostRounding, orderDate = :orderDate, payType = :payType, totalCostPrice = :totalCostPrice, custId = :custId, name = :name, "
				+ "addressLine1 = :addressLine1, addressLine2 = :addressLine2, phone = :phone  WHERE " + typeNo + " = :number";

		SqlParameterSource namedParameters = new MapSqlParameterSource("totalExVat", order.getTotalExVat())
		.addValue("totalVat", order.getTotalVat())
		.addValue("totalPreRounding", order.getTotalPreRounding())
		.addValue("rounding", order.getRounding())
		.addValue("totalPostRounding", order.getTotalPostRounding())
		.addValue("orderDate", order.getOrderDate())
		.addValue("payType", order.getPayType())
		.addValue("totalCostPrice", order.getTotalCost())
		.addValue("custId", order.getCustId())
		.addValue("name", order.getName())
		.addValue("addressLine1", order.getAddress1())
		.addValue("addressLine2", order.getAddress2())
		.addValue("phone", order.getPhone())
		.addValue("number", order.getReceiptNo());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
	}

	/**
	 * Method to detail all of the detail lines for a specific order header
	 * @param typeNo The field name that holds the number: receiptNo or quotationNo
	 * @param detailTable The name of the table to delete from: orderdetail or quotedetail
	 * @param number The order's receiptNo or quote's quotationNo  
	 */
	public void deleteDetailOrder(String typeNo, String detailTable, int number) {
		
		String sql = "DELETE FROM "  + detailTable + " WHERE " + typeNo + "=?";

		this.getJdbcTemplate().update(sql, new Object[] {number});
		
	}
}
