package com.daniel.dao;

import java.util.List;

import com.daniel.model.Order;
import com.daniel.model.Tradesman;

/**
 * This class contains all of the Tradesman methods for interacting with the Database
 * @author Robert Forde
 *
 */

public interface TradesmanDao {

	/**
	 * Method to check if a tradesman already exists in the database
	 * @param tradesman The Tradesman object to be checked for in the database
	 * @return Returns
	 */
	public Tradesman tradesmanExists(Tradesman tradesman);
	
	/**
	 * Method to insert a tradesman into the tradesman table in the database
	 * @param tradesman The Tradesman object to be inserted
	 */
	public void tradesmanInsert(Tradesman tradesman);

	/**
	 * Method to retrieve a tradesman from the database based on its ID
	 * @param id The id of the tradesman to retrieve
	 * @return A Tradesman object representing the tradesman found in the database  
	 */ 
	public Tradesman tradesmanRetrieve(int id);
	
	/**
	 * Method to update a tradesman in the tradesmen table of the database
	 * @param tradesman The Tradesman object whose details are to be updated in the database
	 * @return Returns an int representing the success of the update: 1=success, 0=failure
	 */
	public int tradesmanUpdate(Tradesman tradesman);
	
	/**
	 *  Method to delete a tradesman from the tradesman table in the database
	 *  @param id The id of the tradesman to delete
	 *  @return Returns an int representing the success of the delete: 1=success, 0=failure
	 */
	public int tradesmanDelete(String id);

	/**
	 * Method to return a list of all tradesmen that a certain (passed in) selection criteria
	 * @param selection The field in the tradesman table of the database to be used as a filter
	 * @param filter The filter to use
	 * @return A List of Tradesman objects representing the tradesmen retrieved from the database that match the selection criteria
	 */
	public List<Tradesman> tradesmenLoadFiltered(String selection, String filter);
			
	/**
	 * Method to find return a list of tradesmen from the database based on a possible 4 selection filters
	 * @param name The name filter, if empty String then don't filter 
	 * @param address1 The address1 filter, if empty String then don't filter
	 * @param address2 The address2 filter, if empty String then don't filter
	 * @param phone The phone filter, if empty String then don't filter
	 * @return A List of Tradesman objects representing the tradesmen retrieved from the database that match the selection criteria
	 */ 
	public List<Tradesman> findFilteredTradesmen(String name, String address1, String address2, String phone);

	/**
	 * Method to find the tradesman details with the passed in id and return it
	 * @param id The id of the tradesman to find in the database
	 * @return Returns a tradesman object that was found in the database
	 */
	public Tradesman selectFilteredTradesman(int id);
}