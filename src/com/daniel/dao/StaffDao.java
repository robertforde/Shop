package com.daniel.dao;

import com.daniel.model.Staff;

public interface StaffDao {

	/**
	 * Method to check if a staff repCode exists in the database
	 * @param repCode The repcode to check in the database
	 * @return Returns the Staff object found in the database or else returns a new Staff object 
	 */
	public Staff staffExists(String repCode);
}