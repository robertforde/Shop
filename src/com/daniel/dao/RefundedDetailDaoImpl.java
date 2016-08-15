package com.daniel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.daniel.model.RefundedDetail;
import com.daniel.model.RetailOrder;
import com.daniel.model.RetailOrderLine;
import com.daniel.utilities.Utilities;

/**
 * This class contains all of the Refund methods for interacting with the Database
 * @author Robert Forde
 *
 */
public class RefundedDetailDaoImpl extends NamedParameterJdbcDaoSupport implements RefundedDetailDao{

	/**
	 * Method to find an order's detail lines from the database and return them as a List of Retail Order Line
	 * @param selectedInvoiceNo The order number whose detail lines are to be returned
	 * @return Returns a List of RetailOrderLine objects that are the detail lines of the passed in receiptNo number
	 */
	public List<RetailOrderLine> refundLoadInvoiceDetail(int selectedInvoiceNo) {
		
		String sql = "SELECT * FROM orderdetail WHERE receiptNo = ?";
		
		return this.getJdbcTemplate().query(sql,new Object[] {selectedInvoiceNo}, new OrderLineMapper());		
	}

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
			Date txtRefundFromDate, Date txtRefundToDate, String txtRefundAddress, String txtRefundPhone, String txtRefundValue) {
		
		// Find the users filters entered and build the sql query string
		String sql = "";
		String saleTypeFilter;
				
		if(rdbtnRetailRefund)
			saleTypeFilter = "Retail";
		else if(rdbtnTradeRefund)
			saleTypeFilter = "Trade";
		else
		saleTypeFilter = "Account";
			
		sql = "SELECT * FROM orderheader WHERE saleType='" + saleTypeFilter + "'";
		
		if(!txtRefundInvoiceNo.equals(""))
			sql += " AND receiptNo = " + txtRefundInvoiceNo;
		
		if(!txtRefundName.equals(""))
			sql += " AND name LIKE '%" + txtRefundName + "%'";

		if(!txtRefundAddress.equals(""))
			sql += " AND addressLine1 LIKE '%" + txtRefundAddress + "%'";
		
		Date fromDate = txtRefundFromDate;
		if(fromDate != null) {
			java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
			sql += " AND orderDate >= '" + sqlFromDate + "'";
		}
		
		Date toDate = txtRefundToDate;
		if(toDate != null) {
			java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());
			sql += " AND orderDate <= '" + sqlToDate + "'";
		}
						
		if(!txtRefundPhone.equals(""))
			sql += " AND phone LIKE '%" + txtRefundPhone + "%'";
			
		if(!txtRefundValue.equals(""))
			sql += " AND FORMAT(totalPostRounding,2) = " + Float.parseFloat(txtRefundValue);
	
		sql += " ORDER BY receiptNo";

		return this.getJdbcTemplate().query(sql, new OrderHeaderMapper());
		
	}

	
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
			String txtRefundSrchItemCode, String txtRefundSrchItemDesc) {
				
		// Find the users filters entered and build the sql query string
		String sql = "";
		String saleTypeFilter;
		
		if(rdbtnRetailRefund)
			saleTypeFilter = "Retail";
		else if(rdbtnTradeRefund)
			saleTypeFilter = "Trade";
		else
			saleTypeFilter = "Account";

		sql = "SELECT DISTINCT * FROM orderheader header JOIN orderdetail detail ON header.receiptNo=detail.receiptNo WHERE saleType='" + 
				saleTypeFilter + "'";
		
/*		sql = "SELECT DISTINCT header.receiptNo AS INVOICE_NO, header.name AS NAME, header.addressLine1 AS ADDRESS, DATE_FORMAT(header.orderDate,'%d-%m-%Y') AS DATE, "
			+ "header.phone AS PHONE, + FORMAT(header.totalPostRounding,2) AS VALUE  FROM orderdetail detail JOIN orderheader header ON "
			+ "header.receiptNo=detail.receiptNo WHERE saleType='" + saleTypeFilter + "'";
*/		
		if (!txtRefundSrchItemCode.equals(""))
			sql += " AND detail.itemCode LIKE '%" + txtRefundSrchItemCode + "%'";
		
		if (!txtRefundSrchItemDesc.equals(""))
			sql += " AND detail.itemDescription LIKE '%" + txtRefundSrchItemDesc + "%'";
		
		if(!txtRefundInvoiceNo.equals(""))
			sql += " AND header.receiptNo = " + txtRefundInvoiceNo;
		
		if(!txtRefundName.equals(""))
			sql += " AND header.name LIKE '%" + txtRefundName + "%'";
			
		if(!txtRefundAddress.equals(""))
			sql += " AND header.addressLine1 LIKE '%" + txtRefundAddress + "%'";
		
		Date fromDate = txtRefundFromDate;
		if(fromDate != null) {
			java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
			sql += " AND header.orderDate >= '" + sqlFromDate + "'";
		}
		
		Date toDate = txtRefundToDate;
		if(toDate != null) {
			java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());
			sql += " AND header.orderDate <= '" + sqlToDate + "'";
		}
					
		if(!txtRefundPhone.equals(""))
			sql += " AND header.phone LIKE '%" + txtRefundPhone + "%'";
		
		if(!txtRefundValue.equals(""))
			sql += " AND FORMAT(totalPostRounding,2) = " + Float.parseFloat(txtRefundValue);
		
		sql += " ORDER BY header.receiptNo";
	
		return this.getJdbcTemplate().query(sql, new OrderHeaderMapper());
		
	}

	
	/**
	 * Method to update the orderdetail and refundeddetail tables based on the items that are being refunded
	 * @param refundedDetail A RefundedDetail object that is being refunded
	 */
	public void refundUpdateDetail(RefundedDetail refundedDetail) {
		
		// Find the cost price of this refund
		String sql = "SELECT * FROM orderdetail WHERE receiptNo = :receiptNo AND lineNo = :lineNo";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("receiptNo", refundedDetail.getReceiptNo())
			.addValue("lineNo", refundedDetail.getLineNo());
		
		RetailOrderLine line = getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, new OrderLineMapper());
		refundedDetail.setCostPrice(line.getLineCostValue() / Integer.parseInt(line.getOrderQty()));
		
		// Update the orderdetail		
		sql = "UPDATE orderdetail SET qty=qty-1, valueExDiscount=valueExDiscount-?, discValue=discValue-?, valueExVat=valueExVat-?, costPrice=costPrice-?, " + 
				"refundedValue=refundedValue+? WHERE receiptNo=? AND lineNo=?";
		
		this.getJdbcTemplate().update(sql, new Object[] {refundedDetail.getPrice(), refundedDetail.getDiscValue(), refundedDetail.getValueExVat(), 
				refundedDetail.getCostPrice(), refundedDetail.getValueIncVat(), refundedDetail.getReceiptNo(), refundedDetail.getLineNo()});
				
		
		// Save the refunded line to the refundeddetail table
		sql = "INSERT INTO refundeddetail (receiptNo, lineNo, refundDate, saleType, itemCode, itemDescription, qty, price, discValue, valueExVat, vat, valueIncVat, " + 
				"costPrice) values(:receiptNo,:lineNo,:refundDate,:saleType,:itemCode,:itemDescription,:qty,:price,:discValue,:valueExVat,:vat,:valueIncVat,:costPrice)";
		
		namedParameters = new MapSqlParameterSource("receiptNo", refundedDetail.getReceiptNo())
													.addValue("lineNo", refundedDetail.getLineNo())
													.addValue("refundDate", refundedDetail.getRefundDate())
													.addValue("saleType", refundedDetail.getSaleType())
													.addValue("itemCode", refundedDetail.getItemCode())
													.addValue("itemDescription", refundedDetail.getItemDescription())
													.addValue("qty", refundedDetail.getQty())
													.addValue("price", refundedDetail.getPrice())
													.addValue("discValue", refundedDetail.getDiscValue())
													.addValue("valueExVat", refundedDetail.getValueExVat())
													.addValue("vat", refundedDetail.getVat())
													.addValue("valueIncVat", refundedDetail.getValueIncVat())
													.addValue("costPrice", refundedDetail.getCostPrice());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);

	}

	/**
	 * Method to update the orderheader table based on the items that are being refunded
	 * @param selectedInvoiceNo The order that is whose detail is being refunded 
	 * @param totExVat The order's new totExVat
	 * @param totVat The order's new totVat
	 * @param totalRefunded The order's new refundValue
	 * @param totCost The order's new Cost value
	 */
	public void refundUpdateHeader(int selectedInvoiceNo, float totExVat, float totVat, float totalRefunded, float totCost) {
		
		// Update the orderheader table based on this refund
		
		String sql = "UPDATE orderheader SET totalExVat=totalExVat-?, totalVat=totalVat-?, totalPreRounding=totalPreRounding-?, totalPostRounding=totalPostRounding-?, " + 
				"totalCostPrice=totalCostPrice-?, refundValue=refundValue+? WHERE receiptNo=?";
		
		this.getJdbcTemplate().update(sql, new Object[] {totExVat, totVat, totalRefunded, totalRefunded, totCost, totalRefunded, selectedInvoiceNo});
		
	}
	
	/**
	 * Static Inner RowMapper Class that maps OrderDetail members to the columns of the orderdetail table
	 * @author Robert Forde
	 *
	 */
	private static final class OrderLineMapper implements RowMapper<RetailOrderLine>{

		// Has to be a retail order line because it has all of the fields
		@Override
		public RetailOrderLine mapRow(ResultSet resultSet, int rownum) throws SQLException {

			RetailOrderLine orderLine = new RetailOrderLine();
			orderLine.setReceiptNo(resultSet.getInt("receiptNo"));
			orderLine.setLineNo(resultSet.getInt("lineNo"));
			orderLine.setItemCode(resultSet.getString("itemCode"));
			orderLine.setItemDescription(resultSet.getString("itemDescription"));
			orderLine.setOrderQty(String.valueOf(resultSet.getInt("qty")));
			orderLine.setItemPrice(Utilities.floatToString2Dec(resultSet.getFloat("price")));
			orderLine.setItemTradePrice(resultSet.getFloat("tradePrice"));
			orderLine.setValueExDiscount(Utilities.floatToString2Dec(resultSet.getFloat("valueExDiscount")));
			orderLine.setDiscountPercent(Utilities.floatToString2Dec(resultSet.getFloat("discPercent")));
			orderLine.setDiscountValue(Utilities.floatToString2Dec(resultSet.getFloat("discValue")));
			orderLine.setValueExVat(Utilities.floatToString2Dec(resultSet.getFloat("valueExVat")));
			orderLine.setLineCostValue(resultSet.getFloat("costPrice"));
			orderLine.setRefundedValue(resultSet.getFloat("refundedValue"));
			
			return orderLine;
		}
		
	}
	
	/**
	 * Static Inner RowMapper Class that maps OrderHeader members to the columns of the orderdetail table
	 * @author Robert Forde
	 *
	 */
	private static final class OrderHeaderMapper implements RowMapper<RetailOrder>{

		// Has to use retail order because it has all of the fields
		@Override
		public RetailOrder mapRow(ResultSet resultSet, int rownum) throws SQLException {

			RetailOrder order = new RetailOrder();
			order.setReceiptNo(resultSet.getInt("receiptNo"));
			order.setRepNo(resultSet.getString("repNo"));
			order.setTotalExVat(Utilities.floatToString2Dec(resultSet.getFloat("totalExVat")));
			order.setTotalVat(String.valueOf(resultSet.getFloat("totalVat")));
			order.setTotalPreRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPreRounding")));
			order.setRounding(String.valueOf(resultSet.getFloat("rounding")));
			order.setTotalPostRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPostRounding")));
			order.setOrderDate(resultSet.getDate("orderDate"));
			order.setPayType(resultSet.getString("payType"));
			order.setSaleType(resultSet.getString("saleType"));
			order.setTotalCost(resultSet.getFloat("totalCostPrice"));
			order.setCustId(resultSet.getInt("custId"));
			order.setName(resultSet.getString("name"));
			order.setAddress1(resultSet.getString("addressLine1"));
			order.setAddress2(resultSet.getString("addressLine2"));
			order.setPhone(resultSet.getString("phone"));
			
			return order;
		}
	}

}
