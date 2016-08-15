package com.daniel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.daniel.model.Order;
import com.daniel.model.Tradesman;


public class TradesmanDaoImpl extends NamedParameterJdbcDaoSupport implements TradesmanDao{

	/**
	 * Method to check if a tradesman already exists in the database
	 * @param tradesman The Tradesman object to be checked for in the database
	 * @return Returns
	 */
	public Tradesman tradesmanExists(Tradesman tradesman){
		
		try {
			String sql = "SELECT * FROM tradesman WHERE name = :name AND addressLine1 = :addressLine1 AND addressLine2 = :addressLine2 AND phone = :phone AND deleted = 0";
			
			SqlParameterSource namedParameters = new MapSqlParameterSource("name", tradesman.getName())
			.addValue("addressLine1", tradesman.getAddressLine1())
			.addValue("addressLine2", tradesman.getAddressLine2())
			.addValue("phone", tradesman.getPhone());
			
			return this.getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, new TradesmanMapper());
			
		}catch(EmptyResultDataAccessException e){
		
			return new Tradesman();
		}

	}
	
	
	/**
	 * Method to insert a tradesman into the tradesman table in the database
	 * @param tradesman The Tradesman object to be inserted
	 */
	public void tradesmanInsert(Tradesman tradesman) {
		
		String sql = "INSERT INTO tradesman (name, addressLine1, addressLine2, phone) VALUES(:name, :addressLine1, :addressLine2, :phone)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("name", tradesman.getName())
													.addValue("addressLine1", tradesman.getAddressLine1())
													.addValue("addressLine2", tradesman.getAddressLine2())
													.addValue("phone", tradesman.getPhone());
		
		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);

	}

	
	/**
	 * Method to retrieve a tradesman from the database based on its ID
	 * @param id The id of the tradesman to retrieve
	 * @return A Tradesman object representing the tradesman found in the database  
	 */
	public Tradesman tradesmanRetrieve(int id) {

		try {
			String sql = "SELECT * FROM tradesman WHERE tradesManId = ? AND deleted = 0";
			
			return this.getJdbcTemplate().queryForObject(sql, new Object[] {id}, new TradesmanMapper());
			
		}catch(EmptyResultDataAccessException e){
		
			return new Tradesman();
		}

	}

	
	/**
	 * Method to update a tradesman in the tradesmen table of the database
	 * @param tradesman The Tradesman object whose details are to be updated in the database
	 * @return Returns an int representing the success of the update: 1=success, 0=failure
	 */
	public int tradesmanUpdate(Tradesman tradesman) {
		
		String sql = "UPDATE tradesman SET name = :name, addressLine1 = :addressLine1, addressLine2 = :addressLine2, phone = :phone WHERE tradesManId = :id AND deleted = 0";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", tradesman.getTradesManId())
		.addValue("name", tradesman.getName())
		.addValue("addressLine1", tradesman.getAddressLine1())
		.addValue("addressLine2", tradesman.getAddressLine2())
		.addValue("phone", tradesman.getPhone());

		return this.getNamedParameterJdbcTemplate().update(sql, namedParameters);

	}

	
	/**
	 *  Method to delete a tradesman from the tradesman table in the database
	 *  @param id The id of the tradesman to delete
	 *  @return Returns an int representing the success of the delete: 1=success, 0=failure
	 */
	public int tradesmanDelete(String id) {
		
		String sql = "UPDATE tradesman SET deleted=1 WHERE tradesManId = ? AND deleted = 0";

		return this.getJdbcTemplate().update(sql, new Object[] {id});
		
	}

	
	/**
	 * Method to return a list of all tradesmen that a certain (passed in) selection criteria
	 * @param selection The field in the tradesman table of the database to be used as a filter
	 * @param filter The filter to use
	 * @return A List of Tradesman objects representing the tradesmen retrieved from the database that match the selection criteria
	 */
	public List<Tradesman> tradesmenLoadFiltered(String selection, String filter) {
		
		String sql = "SELECT * FROM tradesman WHERE " + selection + " LIKE '" + filter + "' AND deleted=0 ORDER BY " + selection;

		return this.getJdbcTemplate().query(sql, new TradesmanMapper());
		
	}

	
	/**
	 * Method to find return a list of tradesmen from the database based on a possible 4 selection filters
	 * @param name The name filter, if empty String then don't filter 
	 * @param address1 The address1 filter, if empty String then don't filter
	 * @param address2 The address2 filter, if empty String then don't filter
	 * @param phone The phone filter, if empty String then don't filter
	 * @return A List of Tradesman objects representing the tradesmen retrieved from the database that match the selection criteria
	 */
	public List<Tradesman> findFilteredTradesmen(String name, String address1, String address2, String phone) {

		// Find the users filters entered and build the sql query string

		boolean first = true;
		
		String sql = "SELECT * FROM tradesman";
			
		if(!name.equals("")) {
			if(first){
				sql += " WHERE name LIKE '%" + name + "%'";
				first = false;
			} else{
				sql += " AND name LIKE '%" + name + "%'";
			}
		}
		
		if(!address1.equals("")) {
			if(first){
				sql += " WHERE addressLine1 LIKE '%" + address1 + "%'";
				first = false;
			} else{
				sql += " AND addressLine1 LIKE '%" + address1 + "%'";
			}
		}
		
		if(!address2.equals("")) {
			if(first){
				sql += " WHERE addressLine2 LIKE '%" + address2 + "%'";
				first = false;
			} else{
				sql += " AND addressLine2 LIKE '%" + address2 + "%'";
			}
		}
		
		if(!phone.equals("")) {
			if(first){
				sql += " WHERE phone LIKE '%" + phone + "%'";
				first = false;
			} else{
				sql += " AND phone LIKE '%" + phone + "%'";
			}
		}
		
		// If no filters
		if(first) {
			sql += " WHERE deleted=0 ORDER BY name DESC";	
		}else {
			sql += " AND deleted=0 ORDER BY name DESC";						
		}
		
		return this.getJdbcTemplate().query(sql, new TradesmanMapper());
		
	}

	/**
	 * Method to find the tradesman details with the passed in id and return it
	 * @param id The id of the tradesman to find in the database
	 * @return Returns a tradesman object that was found in the database
	 */
	public Tradesman selectFilteredTradesman(int id) {

		String sql = "SELECT * FROM tradesman WHERE tradesmanID  = :tradesmanID";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("tradesmanID", id);

		return this.getNamedParameterJdbcTemplate().queryForObject(sql, namedParameters, new TradesmanMapper());
		
	}
	
	
	/**
	 * Static Inner RowMapper Class that maps Item members to the columns of the tradesman table
	 * @author Robert Forde
	 *
	 */
	private static final class TradesmanMapper implements RowMapper<Tradesman>{

		@Override
		public Tradesman mapRow(ResultSet resultSet, int rownum) throws SQLException {

			Tradesman tradesman = new Tradesman();
			tradesman.setTradesManId(resultSet.getInt("tradesManId"));
			tradesman.setName(resultSet.getString("name"));
			tradesman.setAddressLine1(resultSet.getString("addressLine1"));
			tradesman.setAddressLine2(resultSet.getString("addressLine2"));
			tradesman.setPhone(resultSet.getString("phone"));
			tradesman.setDeleted(resultSet.getInt("deleted"));
			
			return tradesman;
		}
	}	
	
}