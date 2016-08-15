package com.daniel.dao;

import java.util.List;

import com.daniel.model.Account;
import com.daniel.model.AccountDetail;
import com.daniel.model.Payment;
import com.daniel.model.QuoteHeader;
import com.daniel.model.TradeOrder;


public interface AccountDao {

	/**
	 * This method finds a quote header (number passed in) and loads it's details into a TradeOrder object.
	 * It also finds all of it's quote lines and loads them as trade order lines that are associated with the TradeOrder object.  
	 * @param  selectedQuoteNo The Quote No (int) that you want to find
	 * @return tradeOrder The TradeOrder object that the method created
	 */
	public TradeOrder loadTradeQuote(int selectedQuoteNo);
	
	/**
 	 * Method that retrieves and returns a list of quotes from the database based on an account id
 	 * @param accId An account's id as an int
 	 * @return List of quotes for this account
 	 */
	public List<QuoteHeader> accountRefreshConvertQuoteTable(int accId);
	
	/**
	 * Method that retrieves the last 20 transactions from the database for a particular account 
	 * and returns them as a List of AccountDetail
	 * @param accId An account's id as an int
	 * @return List of transactions of type AccountDetail
	 */ 
	public List<AccountDetail> accountDetailRefreshLatestTrans(int accId);

	/**
	 * Method to return an account from the database based on the id passed to it
	 * @param accId An account's id as an int
	 * @return The account whose id was passed in
	 */
	public Account accountListClickAccount(int id);
	
	/**
	 * Method to insert an account into the database
	 * @param account The Account that is to be inserted
	 * @return Returns 1 for successful insert and a 0 for a failure 
	 */
	public int accountInsert(Account account);
	
	/**
	 * Method to check if an account (passed in) exists in the database
	 * @param account The Account object to check
	 * @return If found return the Account otherwise a new Account 
	 */
	public Account accountExists(Account account);
	
	/**
	 * Method to check if an account exists in the database
	 * @param account The Account to check for
	 * @return Returns the account if it is found in the database, otherwise it returns a new account 
	 */
	public Account accountIdExists(Account account);
		
	/**
	 * Method to update an accounts name, address and phone in the database
	 * @param account The Account to be updated in the database
	 * @return Returns an int representing if the update was successful or not (1 success, 0 failure)
	 */
	public int accountUpdate(Account account);

	/**
	 * Method to check if an account (passed in) exists in the database
	 * @param account The Account object to check
	 * @return If found return the Account otherwise a new Account 
	 */
	public Account accountExists(int id);
	
	/**
	 * Method to Delete the account from the database
	 * @param id The id of the account to be deleted
	 * @return Returns an int representing the success of the delete (1 successful, 0 failure)
	 */
	public int accountDelete(int id);
	
	/**
	 * Method to retrieve accounts based on a selection and a filter from the database
	 * @param selection The field name to be filtered on
	 * @param filter The Filter to be applied
	 */
	public List<Account> accountRefreshAccountsTable(String selection, String filter);
	
	/**
	 * Method to save a payment to the database table and reduce the account balance by the payment amount in the database
	 * @param payment The payment object to be applied
	 * @return Returns the new ammended balance
	 */
	public float processPayment(Payment payment);

	/**
	 * Method to return a list of all of an account's orders and payments from the database
	 * @param accId The id of the account whose transactions we want to retrieve
	 * @return Returns the List of AccountDetail objects for this account
	 */
	public List<AccountDetail> accountStatementGetTrans(int accId);

	/**
	 * Method to retrieve the account's balance based on an account's Id
	 * @param accId The is of the account whose balance we want to retrieve from the database
	 * @return Returns the balance of this account
	 */
	public float accountRetrieveBalance(int accId);
	
	/**
	 * Method to update the balance on an account
	 * @param accId The id of the account in the database to be updated
	 * @param balance The balance to go on the account in the database 
	 */
	public void accountUpdateBalance(int accId, float balance);
	
}