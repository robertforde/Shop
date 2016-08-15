package com.daniel.dao;

import com.daniel.model.InvoiceSettings;

public interface ReceiptSettingsDao {

	/**
	 * Method to retrieve the settings from the database and return them in a InvoiceSettings object
	 * @param invoiceSettings An InvoiceSettings object thatthe settings from the database will be loaded into
	 * @return Return the InvoiceSettings object with the settings as found in the database, if not found then return a new InvoiceSettings object
	 */
	public InvoiceSettings retrieveDatabasePrintSettings(InvoiceSettings invoiceSettings);
	
	/**
	 * Method to save the settings, that are passed in, to the database
	 * @param invoiceSettings An InvoiceSettings object that contains the settings that will be saved to the database
	 */
	public void savePrintSettingsToDatabase(InvoiceSettings invoiceSettings);
	
}