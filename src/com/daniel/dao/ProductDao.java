package com.daniel.dao;

import java.util.List;

import com.daniel.model.Item;

public interface ProductDao {

	/**
	 * Method to find an Item in the database based on it's code
	 * @param code 
	 * @return Returns the Item object that is found in the database and if it is not found then a new Item is returned
	 */
	public Item findItemByCode(String code);
	
	/**
	 * Method to find an Item in the database based on it's description
	 * @param desc The description of the item to search for in the database
	 * @return Returns the Item object that is found in the database and if it is not found then a new Item is returned
	 */
	public Item findItemByDesc(String desc);

	/**
	 * Method to insert an item into the item table in the database
	 * @param item The item to be inserted
	 */
	public void insertItem(Item item);
	
	/**
	 * Method to update an item in the item table in the database
	 * @param item The item to be updated
	 */
	public void updateItem(Item item);
	
	/**
	 * Method to delete an item from the item table
	 * @param itemCode The String itemCode of the item that is to be deleted 
	 */
	public void deleteItem(String itemCode);
	
	/**
	 * Method to return an item from the item table based on the Item Code that is passed in
	 * @param itemCode The String itemCode of the item to be loaded
	 * @return Returns the Item object that is found
	 */
	public Item loadItem(String itemCode);
	
	/**
	 * Method to return a list of all items in the database that match the selection criteria
	 * @param selection The field to be filtered on
	 * @param filter The filter to be used
	 * @return Returns a List of Item objects that match the filter criteria
	 */
	public List<Item> findFilteredItem(String selection, String filter);
	
	/**
	 * Method to return a list of items that match the Item Description that is passed in
	 * @param desc The description filter to be used
	 * @return Returns a list of Item objects representing the Items in the database that match the filter passed in
	 */
	public List<Item> findPossibleItemMatches(String desc);

}