package com.daniel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.daniel.model.SuspendedOrder;
import com.daniel.model.SuspendedOrderLine;
import com.daniel.model.SuspendedPayment;
import com.daniel.utilities.Utilities;

/**
 * This class contains all of the Suspended Orders' methods for interacting with the Database
 * @author Robert Forde
 *
 */
public class SuspendedDaoImpl extends NamedParameterJdbcDaoSupport implements SuspendedDao{

	/**
	 * Method to update the rounding, totalPostRounding and balance fields of a suspendheader in the database
	 * @param rounding The new rounding value for this order
	 * @param postRounding The new postRounding value for this order
	 * @param balance The new balance value for this order
	 * @param receiptNo The receiptNo of the Suspended order to update
	 */
	public void updateRoundingHeader(float rounding, String postRounding, float balance, int receiptNo){
		
		String sql = "UPDATE suspendheader SET rounding = :rounding, totalPostRounding = :totalPostRounding, balance = :balance WHERE receiptNo = :receiptNo";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("rounding", rounding)
		.addValue("totalPostRounding", postRounding)
		.addValue("receiptNo", receiptNo)
		.addValue("balance", balance);
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
	}
	
	
	/**
	 * Method to return a list of all of the Suspended Order Headers from the database that are still open
	 * @param type A String holding the saletype of the open suspended orders to rerieve
	 * @return Returns a List of SuspendedOrder objects of the requested sale type that are open
	 */
	public List<SuspendedOrder> suspendedHeadersListOpen(String type) {
	
		String sql = "SELECT * FROM suspendheader WHERE (dispatchedDate IS NULL OR balance > 0) AND deleted = 0 AND saleType = '" + type + "'";
		
		return this.getJdbcTemplate().query(sql, new SuspendedOrderMapper());
		
	}

	
	/**
	 * Method to get the next receipt number from the suspendheader table in the database
	 * @return Returns the newxt receipt number as an int 
	 */
	public int findNextReceiptNo(){
		
		int nextReceiptNo = 1;
		try {
			
			String sql = "SELECT MAX(receiptNo) FROM suspendheader";
		
			nextReceiptNo = this.getJdbcTemplate().queryForObject(sql, Integer.class);
			nextReceiptNo++;
			
		}catch(EmptyResultDataAccessException e){
			return 1;
		}		
		return nextReceiptNo;
		
	}
	
	
	/**
	 * Method to insert a suspended header  of an order into the suspendheader table
	 * @param order The SuspendedOrder object that is to be inserted
	 */
	public void insertHeaderOrder(SuspendedOrder order){
		
		String sql = "INSERT INTO suspendheader (receiptNo, repNo, totalExVat, totalVat, totalPreRounding, rounding, totalPostRounding, orderDate, " + 
				"saleType, totalCostPrice, custId, name, addressLine1, addressLine2, phone, balance) VALUES(:receiptNo, :repNo, :totalExVat, :totalVat, :totalPreRounding, "
				+ ":rounding, :totalPostRounding, :orderDate, :saleType, :totalCostPrice, :custId, :name, :address1, :address2, :phone, :balance)";

		SqlParameterSource namedParameters = new MapSqlParameterSource("receiptNo", order.getReceiptNo())
			.addValue("repNo", order.getRepNo())
			.addValue("totalExVat", order.getTotalExVat())
			.addValue("totalVat", order.getTotalVat())
			.addValue("totalPreRounding", order.getTotalPreRounding())
			.addValue("rounding", order.getRounding())
			.addValue("totalPostRounding", order.getTotalPostRounding())
			.addValue("orderDate", order.getOrderDate())
			.addValue("saleType", order.getSaleType())
			.addValue("totalCostPrice", order.getTotalCost())
			.addValue("custId", order.getCustId())
			.addValue("name", order.getName())
			.addValue("address1", order.getAddress1())
			.addValue("address2", order.getAddress2())
			.addValue("phone", order.getPhone())
			.addValue("balance", order.getBalance());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
	}

	
	/**
	 * Method to insert a suspended order line into the suspendeddetail table in the database
	 * @param orderLine The suspendedOrderLine object to be inserted into the database
	 */
	public void insertRetailDetailOrder(SuspendedOrderLine orderLine){
		
		String sql  = "INSERT INTO suspenddetail (receiptNo, lineNo, itemCode, itemDescription, qty, price, tradePrice, valueExDiscount, discPercent, discValue, "
				+ "valueExVat, costPrice, refundedValue, dispatched, dispatchedDate, deleted, deletedDate) VALUES(:receiptNo, :lineNo, :itemCode, :itemDescription, :qty, "
				+ ":price, :tradePrice, :valueExDiscount, :discPercent, :discValue, :valueExVat, :costPrice, :refundedValue, :dispatched, :dispatchedDate, :deleted, "
				+ ":deletedDate)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("receiptNo", orderLine.getReceiptNo())
		.addValue("lineNo", orderLine.getLineNo())
		.addValue("itemCode", orderLine.getItemCode())
		.addValue("itemDescription", orderLine.getItemDescription())
		.addValue("qty", orderLine.getOrderQty())
		.addValue("price", orderLine.getItemPrice())
		.addValue("tradePrice", orderLine.getItemTradePrice())
		.addValue("valueExDiscount", orderLine.getValueExDiscount())
		.addValue("discPercent", orderLine.getDiscountPercent())
		.addValue("discValue", orderLine.getDiscountValue())
		.addValue("valueExVat", orderLine.getValueExVat())
		.addValue("costPrice", orderLine.getLineCostValue())
		.addValue("refundedValue", orderLine.getRefundedValue())
		.addValue("dispatched", orderLine.getDispatched())
		.addValue("dispatchedDate", orderLine.getDispatchedDate())
		.addValue("deleted", orderLine.getDeleted())
		.addValue("deletedDate", orderLine.getDeleteDate());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
	}

	
	/**
	 * Method to update the suspendheader table in the database with the details of a SuspendedOrder that is passed in 
	 * @param order The SuspendedOrder object that is to be updated in the database
	 */
	public void updateHeaderOrder(SuspendedOrder order){
		
		String sql = "UPDATE suspendheader SET totalExVat = :totalExVat, totalVat = :totalVat, totalPreRounding = :totalPreRounding, rounding = :rounding, "
				+ "totalPostRounding = :totalPostRounding, orderDate = :orderDate, totalCostPrice = :totalCostPrice, custId = :custId, name = :name, "
				+ "addressLine1 = :addressLine1, addressLine2 = :addressLine2, phone = :phone, balance = :balance WHERE receiptNo = :receiptNo";

		SqlParameterSource namedParameters = new MapSqlParameterSource("totalExVat", order.getTotalExVat())
		.addValue("totalVat", order.getTotalVat())
		.addValue("totalPreRounding", order.getTotalPreRounding())
		.addValue("rounding", order.getRounding())
		.addValue("totalPostRounding", order.getTotalPostRounding())
		.addValue("orderDate", order.getOrderDate())
		.addValue("totalCostPrice", order.getTotalCost())
		.addValue("custId", order.getCustId())
		.addValue("name", order.getName())
		.addValue("addressLine1", order.getAddress1())
		.addValue("addressLine2", order.getAddress2())
		.addValue("phone", order.getPhone())
		.addValue("receiptNo", order.getReceiptNo())
		.addValue("balance", order.getBalance());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
	}

	
	/**
	 * Method to delete all of the detail lines, from the database, for a specific suspended order header
	 * @param receiptNo The receiptNo of the Suspended Order whose detail lines will be deleted
	 */
	public void deleteDetailOrder(int receiptNo){
		
		String sql = "DELETE FROM suspenddetail WHERE receiptNo = ?";

		this.getJdbcTemplate().update(sql, new Object[] {receiptNo});
		
	}

	
	/**
	 * Method to mark a suspendheader order as deleted in the database and then call a method to mark all of it's suspenddetail order lines 
	 * as deleted also
	 * @param receiptNo The receiptNo of the Suspended Order to be marked as deleted
	 * @return Returns a 1 if the records was successfully deleted, otherwise returns a 0
	 */
	public int makeOrderDeleted(int receiptNo){
		
		java.sql.Date today = new java.sql.Date(new Date().getTime());
		
		String sql = "UPDATE suspendheader SET deleted = 1, deletedDate = '" + today + "' WHERE receiptNo = ?";

		int result = this.getJdbcTemplate().update(sql, new Object[] {receiptNo});
		
		if(result == 1) {
			makeOrderLinesDeleted(receiptNo);
		}
		
		return result;
	}	

	
	/**
	 * Method to mark all suspenddetail order lines for a suspended order number as deleted in the database
	 * @param receiptNo The receiptNo of the Suspended Order whose lines are to be marked as deleted
	 */
	public void makeOrderLinesDeleted(int receiptNo){
		
		java.sql.Date today = new java.sql.Date(new Date().getTime());
		
		String sql = "UPDATE suspenddetail SET deleted = 1, deletedDate = '" + today + "' WHERE receiptNo = ?";

		this.getJdbcTemplate().update(sql, new Object[] {receiptNo});
		
	}
	
	
	/**
	 * Method to find a Suspended Order from the suspendheader table in the database and return it as a SuspendedOrder object
	 * @param receiptNo The receiptNo of the Suspended Order to find
	 * @return Returns a SuspendedOrder object that was found in the database
	 */
	public SuspendedOrder findSuspendOrderByNumber(int receiptNo){
		
		String sql = "SELECT * FROM suspendheader WHERE receiptNo = ?";
		
		return this.getJdbcTemplate().queryForObject(sql, new Object [] {receiptNo}, new SuspendedOrderMapper());

	} 
	
	
	/**
	 * Method to return a list of SuspendedOrderLines from the database based on a Suspended Order Receipt No that is passed in
	 * @param receiptNo The receiptNo of the Suspended Order whose lines we are finding
	 * @return Returns a List of SuspendedOrderLine objects that have been found for the receiptNo that was searched
	 */
	public List<SuspendedOrderLine> findSuspendedOrderLinesByReceiptNo(int receiptNo){
		
		String sql = "SELECT * FROM suspenddetail WHERE receiptNo = ? AND qty!= 0 AND deleted = 0 ORDER BY dispatchedDate ASC, lineNo ASC";
		
		return this.getJdbcTemplate().query(sql, new Object [] {receiptNo}, new SuspendedOrderLineMapper());
	}
	
	
	/**
	 * Method to find a Suspended Order Line (by ReceiptNo and LineNo) from the database and return it
	 * @param receiptNo The receiptNo of the Suspended Order whose line we want to return
	 * @param lineNo The line of the Suspended Order that we want to find
	 * @return Returns a SuspendedOrderLine object with the details of the line requested
	 */
	public SuspendedOrderLine findSuspendOrderLineByReceiptNoLineNo(int receiptNo, int lineNo){
		
		String sql = "SELECT * FROM suspenddetail WHERE receiptNo = :receiptNo AND lineNo = :lineNo";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("receiptNo", receiptNo)
		.addValue("lineNo", lineNo);
		
		return this.getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, new SuspendedOrderLineMapper());

	}
	
	
	/**
	 * Method to change an order line (receiptNo & lineNo passed) by an amount that is passed in
	 * @param receiptNo The receiptNo of the Suspended Order to be changed
	 * @param lineNo The line of the Suspended Order to be changed
	 * @param changeQty The Qty dispatched
	 */
	public void changeSuspendOrderLineDispatched(int receiptNo, int lineNo, int changeQty){
		
		String sql = "UPDATE suspenddetail SET dispatched = dispatched + " + changeQty + " WHERE receiptNo = :receiptNo AND lineNo = :lineNo";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("receiptNo", receiptNo)
		.addValue("lineNo", lineNo);
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
	}

	
	/**
	 * Method to stamp todays date in the dispatchedDate field of a suspended order line in the database (receiptNo & lineNo passed)
	 * @param receiptNo The receiptNo of the Suspended Order whose line is to be stamped
	 * @param lineNo The line of the Suspended Order to be stamped
	 */
	public void stampSuspendOrderLineDispatchedDate(int receiptNo, int lineNo){
		
		java.sql.Date today = new java.sql.Date(new Date().getTime());
		
		String sql = "UPDATE suspenddetail SET dispatchedDate = '" + today + "' WHERE receiptNo = :receiptNo AND lineNo = :lineNo";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("receiptNo", receiptNo)
		.addValue("lineNo", lineNo);
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
	}
	
	
	/**
	 * Method to return the number of lines on a Suspended Order that have not been dispatched yet
	 * @param receiptNo The receiptNo of the Suspended Order whose non dispatched lines to be counted
	 * @return Returns the number of lines as an int
	 */
	public int countNonDispatchedSuspendedOrderLines(int receiptNo){
		
		String sql = "SELECT COUNT(*) FROM suspenddetail WHERE receiptNo = ? AND dispatchedDate IS NULL";
		
		return this.getJdbcTemplate().queryForObject(sql, new Object[] {receiptNo}, Integer.class);

	}

	
	/**
	 * Method to stamp todays date on an order whose receiptNo is passed in
	 * @param receiptNo The receiptNo of the Suspended Order to be stamped
	 */
	public void stampSuspendOrderDispatchedDate(int receiptNo){
		
		java.sql.Date today = new java.sql.Date(new Date().getTime());
		
		String sql = "UPDATE suspendheader SET dispatchedDate = '" + today + "' WHERE receiptNo = ?";
		
		this.getJdbcTemplate().update(sql, new Object[] {receiptNo});
	}

	
	/**
	 * Method to save a payment to the suspendpayment table and reduce the suspended order's balance by the payment amount
	 * @param payment The SuspendedPayment object to be saved to the database
	 * @return Returns the balance of the Suspended Order after the payment
	 */
	public float processPayment(SuspendedPayment payment){
		
		// Save the payment to the suspendpayment table
		String sql = "INSERT INTO suspendpayment (suspendOrder, payDate, payType, amount, comment) VALUES(:suspendOrder, :payDate, :payType, :amount, :comment)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("suspendOrder", payment.getSuspendOrder())
		.addValue("payDate", payment.getPayDate())
		.addValue("payType", payment.getPayType())
		.addValue("amount", payment.getAmount())
		.addValue("comment", payment.getComment());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
		sql = "UPDATE suspendheader SET balance = balance - ? WHERE receiptNo = ?";
		this.getJdbcTemplate().update(sql, new Object[]{payment.getAmount(), payment.getSuspendOrder()});
		
		sql = "SELECT balance FROM suspendheader WHERE receiptNo = :receiptNo";
		namedParameters = new MapSqlParameterSource("receiptNo", payment.getSuspendOrder());
		return this.getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, Float.class);
	}
		

	/**
	 * Static Inner RowMapper Class that maps a Suspended Order members to the columns of the suspendheader table
	 * @author Robert Forde
	 *
	 */
	private static final class SuspendedOrderMapper implements RowMapper<SuspendedOrder>{

		@Override
		public SuspendedOrder mapRow(ResultSet resultSet, int rownum) throws SQLException {

			SuspendedOrder order = new SuspendedOrder();
			order.setReceiptNo(resultSet.getInt("receiptNo"));
			order.setRepNo(resultSet.getString("repNo"));
			order.setTotalExVat(Utilities.floatToString2Dec(resultSet.getFloat("totalExVat")));
			order.setTotalVat(Utilities.floatToString2Dec(resultSet.getFloat("totalVat")));
			order.setTotalPreRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPreRounding")));
			order.setRounding(Utilities.floatToString2Dec(resultSet.getFloat("rounding")));
			order.setTotalPostRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPostRounding")));
			order.setOrderDate(resultSet.getDate("orderDate"));
			order.setSaleType(resultSet.getString("saleType"));
			order.setTotalCost(resultSet.getFloat("totalCostPrice"));
			order.setCustId(resultSet.getInt("custId"));
			order.setName(resultSet.getString("name"));
			order.setAddress1(resultSet.getString("addressLine1"));
			order.setAddress2(resultSet.getString("addressLine2"));
			order.setPhone(resultSet.getString("phone"));
			order.setBalance(resultSet.getFloat("balance"));
			order.setDeleted(resultSet.getInt("deleted"));
			order.setDeletedDate(resultSet.getDate("deletedDate"));
			
			return order;
		}
		
	}	

	
	/**
	 * Static Inner RowMapper Class that maps a SuspendedOrderLine members to the columns of the suspenddetail table
	 * @author Robert Forde
	 *
	 */
	private static final class SuspendedOrderLineMapper implements RowMapper<SuspendedOrderLine>{

		@Override
		public SuspendedOrderLine mapRow(ResultSet resultSet, int rownum) throws SQLException {

			SuspendedOrderLine orderLine = new SuspendedOrderLine();
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
			orderLine.setDispatched(resultSet.getInt("dispatched"));
			orderLine.setDispatchedDate(resultSet.getDate("dispatchedDate"));
			orderLine.setDeleted(resultSet.getInt("deleted"));
			orderLine.setDeleteDate(resultSet.getDate("deletedDate"));
			
			return orderLine;
		}
	}
	
}