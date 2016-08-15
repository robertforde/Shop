package com.daniel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.daniel.model.Item;

/**
 * This class contains all of the Order methods for interacting with the Database
 * @author Robert Forde
 *
 */
public class ProductDaoImpl extends NamedParameterJdbcDaoSupport implements ProductDao{

	/**
	 * Method to find an Item in the database based on it's code
	 * @param code 
	 * @return Returns the Item object that is found in the database and if it is not found then a new Item is returned
	 */
	public Item findItemByCode(String code) {
		
		try {
			String sql = "SELECT * FROM item WHERE itemCode = ? AND deleted = 0";
			
			return this.getJdbcTemplate().queryForObject(sql, new Object[] {code}, new ItemMapper());
			
		}catch(EmptyResultDataAccessException e){
		
			return new Item();
		}
				
	}
	
	
	/**
	 * Method to find an Item in the database based on it's description
	 * @param desc The description of the item to search for in the database
	 * @return Returns the Item object that is found in the database and if it is not found then a new Item is returned
	 */
	public Item findItemByDesc(String desc) {
		
		try {
			String sql = "SELECT * FROM item WHERE itemDescription = ? AND deleted = 0";

			return this.getJdbcTemplate().queryForObject(sql, new Object[] {desc}, new ItemMapper());
			
		}catch(EmptyResultDataAccessException e){
		
			return new Item();
		}
					
	}
	
	
	/**
	 * Method to insert an item into the item table in the database
	 * @param item The item to be inserted
	 */
	public void insertItem(Item item) {
		
		String sql = "INSERT INTO item (itemCode, itemDescription, costPrice, tradePrice, retailPrice) VALUES(:code, :desc, :cost, :trade, :retail)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("code", item.getItemCode())
													.addValue("desc", item.getItemDescription())
													.addValue("cost", item.getCostPrice())
													.addValue("trade", item.getTradePrice())
													.addValue("retail", item.getRetailPrice());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
	}
	
	
	/**
	 * Method to update an item in the item table in the database
	 * @param item The item to be updated
	 */
	public void updateItem(Item item) {
		
		String sql = "UPDATE item SET itemDescription = :desc, costPrice = :cost, tradePrice = :trade, retailPrice = :retail WHERE itemCode = :code AND deleted = 0";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("code", item.getItemCode())
		.addValue("desc", item.getItemDescription())
		.addValue("cost", item.getCostPrice())
		.addValue("trade", item.getTradePrice())
		.addValue("retail", item.getRetailPrice())
		.addValue("code", item.getItemCode());

		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);
		
	}
	
	
	/**
	 * Method to delete an item from the item table in the database
	 * @param itemCode The String itemCode of the item that is to be deleted 
	 */
	public void deleteItem(String itemCode) {
		
		String sql = "UPDATE ITEM SET deleted=1 WHERE itemCode = ? AND deleted = 0";

		this.getJdbcTemplate().update(sql, new Object[] {itemCode});
		
	}
	
	
	/**
	 * Method to return an item from the item table based on the Item Code that is passed in
	 * @param itemCode The String itemCode of the item to be loaded
	 * @return Returns the Item object that is found
	 */
	public Item loadItem(String itemCode) {
		
		String sql = "SELECT * FROM item WHERE itemCode = ? AND deleted = 0";
		
		return this.getJdbcTemplate().queryForObject(sql, new Object[] {itemCode}, new ItemMapper());
		
	}

	/**
	 * Method to return a list of all items in the database that match the selection criteria
	 * @param selection The field to be filtered on
	 * @param filter The filter to be used
	 * @return Returns a List of Item objects that match the filter criteria
	 */
	public List<Item> findFilteredItem(String selection, String filter) {
		
		String sql = "SELECT * FROM item WHERE " + selection + " LIKE '" + filter + "' AND deleted=0 ORDER BY " + 
						selection;

		return this.getJdbcTemplate().query(sql, new ItemMapper());
		
	}
		
	/**
	 * Method to return a list of items that match the Item Description that is passed in
	 * @param desc The description filter to be used
	 * @return Returns a list of Item objects representing the Items in the database that match the filter passed in
	 */
	public List<Item> findPossibleItemMatches(String desc) {
		
		String ordSrchText = "'%" + desc + "%'";
		String sql = "SELECT * FROM item WHERE itemDescription LIKE " + ordSrchText + " AND deleted=0 ORDER BY itemDescription";
		
		return this.getJdbcTemplate().query(sql, new ItemMapper());

	}

	
	/**
	 * Static Inner RowMapper Class that maps Item members to the columns of the item table
	 * @author Robert Forde
	 *
	 */
	private static final class ItemMapper implements RowMapper<Item>{

		@Override
		public Item mapRow(ResultSet resultSet, int rownum) throws SQLException {

			Item item = new Item();
			item.setItemId(resultSet.getInt("itemId"));
			item.setItemCode(resultSet.getString("itemCode"));
			item.setItemDescription(resultSet.getString("itemDescription"));
			item.setRetailPrice(resultSet.getFloat("retailPrice"));
			item.setTradePrice(resultSet.getFloat("tradePrice"));
			item.setCostPrice(resultSet.getFloat("costPrice"));
			item.setDeleted(resultSet.getInt("deleted"));
			item.setStock(resultSet.getInt("stock"));
			item.setStockAlert(resultSet.getInt("stockAlert"));
			
			return item;
		}
	}

}
