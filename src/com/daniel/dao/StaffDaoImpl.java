package com.daniel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.daniel.model.Staff;

/**
 * This class contains all of the Staff methods for interacting with the Database
 * @author Robert Forde
 *
 */
public class StaffDaoImpl extends NamedParameterJdbcDaoSupport implements StaffDao{

	/**
	 * Method to check if a staff repCode exists in the database
	 * @param repCode The repcode to check in the database
	 * @return Returns the Staff object found in the database or else returns a new Staff object 
	 */
	public Staff staffExists(String repCode){
		
		try {
			String sql = "SELECT * FROM staff WHERE repCode = ?";
			
			return this.getJdbcTemplate().queryForObject(sql, new Object[] {repCode}, new StaffMapper());
			
		}catch(EmptyResultDataAccessException e){
		
			return new Staff();
		}

	}
	
	/**
	 * Static Inner RowMapper Class that maps Staff members to the columns of the staff table
	 * @author Robert Forde
	 *
	 */
	private static final class StaffMapper implements RowMapper<Staff>{

		@Override
		public Staff mapRow(ResultSet resultSet, int rownum) throws SQLException {

			Staff staff = new Staff();
			staff.setRepCode(resultSet.getString("repCode"));
			staff.setForename(resultSet.getString("forename"));
			staff.setSurname(resultSet.getString("surname"));
						
			return staff;
		}
		
	}
}