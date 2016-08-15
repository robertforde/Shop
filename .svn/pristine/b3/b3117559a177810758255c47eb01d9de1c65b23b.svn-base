package com.daniel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.daniel.model.Account;
import com.daniel.model.AccountDetail;
import com.daniel.model.Payment;
import com.daniel.model.QuoteHeader;
import com.daniel.model.TradeOrder;
import com.daniel.model.TradeOrderLine;
import com.daniel.utilities.Utilities;


/**
 * This class holds all of the Data Access methods necessary when dealing with Accounts.  
 * @author Robert Forde
 *
 */
public class AccountDaoImpl extends NamedParameterJdbcDaoSupport implements AccountDao{

	/**
	 * This method finds a quote header from the database and loads it's details into a TradeOrder object.
	 * It also finds all of it's quote lines and loads them as trade order lines that are associated with the TradeOrder object.  
	 * @param  selectedQuoteNo The Quote No (int) that you want to find
	 * @return tradeOrder The TradeOrder object that the method created
	 */
 	public TradeOrder loadTradeQuote(int selectedQuoteNo) {
		
		String sql = "SELECT * FROM quoteheader WHERE quotationNo = ?";
		
		TradeOrder tradeOrder = this.getJdbcTemplate().queryForObject(sql, new Object[] {selectedQuoteNo}, new TradeOrderMapper());

		
		sql = "SELECT * FROM quotedetail WHERE quotationNo = ?";

		ArrayList<TradeOrderLine> tradeOrderLines = (ArrayList<TradeOrderLine>) this.getJdbcTemplate().query(sql, new Object[] {selectedQuoteNo}, new TradeOrderLineMapper());
		tradeOrder.setOrderList(tradeOrderLines);
		tradeOrder.setSaleType("Account");

		return tradeOrder;
	}

 	
 	/**
 	 * Method that retrieves and returns a list of quotes from the database based on an account id
 	 * @param accId An account's id as an int
 	 * @return List of quotes for this account
 	 */
	public List<QuoteHeader> accountRefreshConvertQuoteTable(int accId) {
		
		String sql = "SELECT * FROM quoteheader WHERE saleType = 'AccQuote' AND custId = ?";
		
		return this.getJdbcTemplate().query(sql, new Object[]{accId}, new QuoteHeaderMapper());
			
	}

	
	/**
	 * Method that retrieves the last 20 transactions from the database for a particular account 
	 * and returns them as a List of AccountDetail
	 * @param accId An account's id as an int
	 * @return List of transactions of type AccountDetail
	 */
	public List<AccountDetail> accountDetailRefreshLatestTrans(int accId) {
		
		String sql = "SELECT orderDate AS DATE, receiptNo AS NUMBER, 'ORDER' AS TYPE, totalPostRounding AS AMOUNT FROM orderheader WHERE custId=? " + 
				"UNION " + 
				"SELECT payDate AS DATE, paymentId AS NUMBER, 'PAYMENT' AS TYPE, amount AS AMOUNT FROM accountPayment WHERE accountId=? " + 
				"ORDER BY DATE DESC LIMIT 20 ";
		
		return this.getJdbcTemplate().query(sql, new Object[] {accId, accId}, new AccountDetailMapper());

	}

	
	/**
	 * Method to return an account from the database based on the id passed to it
	 * @param accId An account's id as an int
	 * @return The account whose id was passed in
	 */
	public Account accountListClickAccount(int id) {
		
		String sql = "SELECT * FROM account WHERE accountID= ? ";
		
		return this.getJdbcTemplate().queryForObject(sql, new Object[] {id}, new AccountMapper());
		
	}

	
	/**
	 * Method to check if an account (passed in) exists in the database
	 * @param account The Account object to check
	 * @return If found return the Account otherwise a new Account 
	 */
	public Account accountExists(Account account) {
		
		try {
			String sql = "SELECT * FROM account WHERE name = :name AND addressLine1 = :addressLine1 AND addressLine2 = :addressLine2 AND phone = :phone";
			
			SqlParameterSource namedParameters = new MapSqlParameterSource("name", account.getName())
			.addValue("addressLine1", account.getAddressLine1())
			.addValue("addressLine2", account.getAddressLine2())
			.addValue("phone", account.getPhone());
			
			return this.getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, new AccountMapper());
			
		}catch(EmptyResultDataAccessException e){
		
			return new Account();
		}

	}


	/**
	 * Method to insert an account into the database
	 * @param account The Account that is to be inserted
	 * @return Returns 1 for successful insert and a 0 for a failure 
	 */
	public int accountInsert(Account account) {
		
		String sql = "INSERT INTO account (name, addressLine1, addressLine2, phone, balance) VALUES(:name, :addressLine1, :addressLine2, :phone, :balance)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("name", account.getName())
													.addValue("addressLine1", account.getAddressLine1())
													.addValue("addressLine2", account.getAddressLine2())
													.addValue("phone", account.getPhone())
													.addValue("balance", account.getBalance());
		
		return this.getNamedParameterJdbcTemplate().update(sql, namedParameters);

	}
	
	
	/**
	 * Method to check if an account exists in the database
	 * @param account The Account to check for
	 * @return Returns the account if it is found in the database, otherwise it returns a new account 
	 */
	public Account accountIdExists(Account account) {
			
		try {
			String sql = "SELECT * FROM account WHERE accountId = ?";
				
			return this.getJdbcTemplate().queryForObject(sql, new Object[]{account.getId()}, new AccountMapper());
				
		}catch(EmptyResultDataAccessException e){
			
			return new Account();
		}

	}

	
	/**
	 * Method to update an accounts name, address and phone in the database
	 * @param account The Account to be updated in the database
	 * @return Returns an int representing if the update was successful or not (1 success, 0 failure)
	 */
	public int accountUpdate(Account account) {
		
		String sql = "UPDATE account SET name = :name, addressLine1 = :addressLine1, addressLine2 = :addressLine2, phone = :phone WHERE accountID = :accountId";

		SqlParameterSource namedParameters = new MapSqlParameterSource("name", account.getName())
		.addValue("addressLine1", account.getAddressLine1())
		.addValue("addressLine2", account.getAddressLine2())
		.addValue("phone", account.getPhone())
		.addValue("accountId", account.getId());

		return this.getNamedParameterJdbcTemplate().update(sql, namedParameters);

	}
	
	
	/**
	 * Method to check if an account exists in the database based on it's id
	 * @param id The ID of the account to check for
	 * @return Returns the account if it is found in the database, otherwise it returns a new account 
	 */
	public Account accountExists(int id) {
			
		try {
			String sql = "SELECT * FROM account WHERE accountId = ?";
			
			return this.getJdbcTemplate().queryForObject(sql, new Object[]{id}, new AccountMapper());
				
		}catch(EmptyResultDataAccessException e){
			
			return new Account();
		}

	}

	
	/**
	 * Method to Delete the account from the database
	 * @param id The id of the account to be deleted
	 * @return Returns an int representing the success of the delete (1 successful, 0 failure)
	 */
	public int accountDelete(int id) {
		
		String sql = "UPDATE account SET deleted=1 WHERE accountId = ? AND deleted = 0";

		return this.getJdbcTemplate().update(sql, new Object[] {id});
		
	}

	
	/**
	 * Method to retrieve accounts based on a selection and a filter from the database
	 * @param selection The field name to be filtered on
	 * @param filter The Filter to be applied
	 */
	public List<Account> accountRefreshAccountsTable(String selection, String filter) {
			
		String sql = "SELECT * FROM account WHERE " + selection + " LIKE '" + filter + "' AND deleted=0 ORDER BY " + selection;

		return this.getJdbcTemplate().query(sql, new AccountMapper());

	}

	
	/**
	 * Method to save a payment to the database table and reduce the account balance by the payment amount in the database
	 * @param payment The payment object to be applied
	 * @return Returns the new ammended balance
	 */
	public float processPayment(Payment payment) {
		
		// Save the payment to the accountpayment table
		String sql = "INSERT INTO accountpayment (accountId, payDate, payType, amount, comment) VALUES(:accountId, :payDate, :payType, :amount, :comment)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("accountId", payment.getAccountId())
		.addValue("payDate", payment.getPayDate())
		.addValue("payType", payment.getPayType())
		.addValue("amount", payment.getAmount())
		.addValue("comment", payment.getComment());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
		sql = "UPDATE account SET balance = balance - ? WHERE accountId = ?";
		this.getJdbcTemplate().update(sql, new Object[]{payment.getAmount(), payment.getAccountId()});
		
		sql = "SELECT balance FROM account WHERE accountId = :accountId";
		namedParameters = new MapSqlParameterSource("accountId", payment.getAccountId());
		return this.getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, Float.class);
	}
	
	/**
	 * Method to return a list of all of an account's orders and payments from the database
	 * @param accId The id of the account whose transactions we want to retrieve
	 * @return Returns the List of AccountDetail objects for this account
	 */
	public List<AccountDetail> accountStatementGetTrans(int accId) {
		
		String sql = "SELECT orderDate AS DATE, receiptNo AS NUMBER, 'ORDER' AS TYPE, totalPostRounding AS AMOUNT FROM orderheader WHERE custId=? " + 
				"UNION " + 
				"SELECT payDate AS DATE, paymentId AS NUMBER, 'PAYMENT' AS TYPE, amount AS AMOUNT FROM accountPayment WHERE accountId=? " + 
				"ORDER BY DATE ASC ";
		
		return this.getJdbcTemplate().query(sql, new Object[] {accId, accId}, new AccountDetailMapper());
	
	}
	
	
	/**
	 * Method to retrieve the account's balance based on an account's Id
	 * @param accId The is of the account whose balance we want to retrieve from the database
	 * @return Returns the balance of this account
	 */
	public float accountRetrieveBalance(int accId) {
		
		String sql = "SELECT balance FROM account WHERE accountId = ?";
		
		return this.getJdbcTemplate().queryForObject(sql, new Object[]{accId}, Float.class);
		
	}
	
	
	/**
	 * Method to update the balance on an account
	 * @param accId The id of the account in the database to be updated
	 * @param balance The balance to go on the account in the database 
	 */
	public void accountUpdateBalance(int accId, float balance){

		String sql = "UPDATE account SET balance = :balance WHERE accountId = :accountId";

		SqlParameterSource namedParameters = new MapSqlParameterSource("balance", balance)
		.addValue("accountId", accId);
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
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
			tradeOrder.setTotalExVat(resultSet.getString("totalExVat"));
			tradeOrder.setTotalVat(Utilities.floatToString2Dec(resultSet.getFloat("totalVat")));
			tradeOrder.setTotalPreRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPreRounding")));
			tradeOrder.setRounding(Utilities.floatToString2Dec(resultSet.getFloat("rounding")));
			tradeOrder.setTotalPostRounding(Utilities.floatToString2Dec(resultSet.getFloat("totalPostRounding")));
			tradeOrder.setOrderDate(resultSet.getDate("orderDate"));
			tradeOrder.setPayType(resultSet.getString("payType"));
			tradeOrder.setSaleType(resultSet.getString("saleType"));
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
	 * Static Inner RowMapper Class that maps TradeOrderLine members to the columns of the  table
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
			tradeOrderLine.setValueExVat(String.valueOf(resultSet.getFloat("valueExVat")));
			tradeOrderLine.setLineCostValue(resultSet.getFloat("costPrice"));
			
			return tradeOrderLine;
		}
		
	}
	
	
	/**
	 * Static Inner RowMapper Class that maps QuoteHeader members to the columns of the quoteheader table
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
			quoteHeader.setSaleType(resultSet.getString("saleType"));
			quoteHeader.setTotalCost(resultSet.getFloat("totalCostPrice"));
			quoteHeader.setCustId(resultSet.getInt("custId"));
			quoteHeader.setName(resultSet.getString("name"));
			quoteHeader.setAddress1(resultSet.getString("addressLine1"));
			quoteHeader.setAddress2(resultSet.getString("addressLine2"));
			quoteHeader.setPhone(resultSet.getString("phone"));
			
			return quoteHeader;
		}
		
	}
	
	
	/**
	 * Static Inner RowMapper Class that maps AccountDetail members to query results from columns of the accountpayment and orderheader tables
	 * @author Robert Forde
	 *
	 */
	private static final class AccountDetailMapper implements RowMapper<AccountDetail>{

		@Override
		public AccountDetail mapRow(ResultSet resultSet, int rownum) throws SQLException {

			AccountDetail accountDetail = new AccountDetail();
			accountDetail.setTheDate(resultSet.getDate("DATE"));
			accountDetail.setNumber(resultSet.getInt("NUMBER"));
			accountDetail.setType(resultSet.getString("TYPE"));
			accountDetail.setAmount(resultSet.getFloat("AMOUNT"));
			
		
			return accountDetail;
		}
	
	}
	
	
	/**
	 * Static Inner RowMapper Class that maps Account members to the columns of the account table
	 * @author Robert Forde
	 *
	 */
	private static final class AccountMapper implements RowMapper<Account>{

		@Override
		public Account mapRow(ResultSet resultSet, int rownum) throws SQLException {

			Account account = new Account();
			account.setId(resultSet.getInt("accountId"));
			account.setName(resultSet.getString("name"));
			account.setAddressLine1(resultSet.getString("addressLine1"));
			account.setAddressLine2(resultSet.getString("addressLine2"));
			account.setPhone(resultSet.getString("phone"));
			account.setBalance(resultSet.getFloat("balance"));
			
		
			return account;
		}
	}
	
}
