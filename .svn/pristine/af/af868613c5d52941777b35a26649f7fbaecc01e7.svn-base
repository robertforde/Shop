package com.daniel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.daniel.model.InvoiceSettings;

/**
 * This class contains all of the ReceiptSettings methods for interacting with the Database
 * @author Robert Forde
 *
 */
public class ReceiptSettingsDaoImpl  extends NamedParameterJdbcDaoSupport implements ReceiptSettingsDao{

	/**
	 * Method to retrieve the settings from the database and return them in a InvoiceSettings object
	 * @param invoiceSettings An InvoiceSettings object that the settings from the database will be loaded into
	 * @return Return the InvoiceSettings object with the settings as found in the database, if not found then return a new InvoiceSettings object
	 */
	public InvoiceSettings retrieveDatabasePrintSettings(InvoiceSettings invoiceSettings){
		
		try {
			String sql = "SELECT * FROM receiptsettings";
			
			return this.getJdbcTemplate().queryForObject(sql, new InvoiceSettingsMapper());
			
		}catch(EmptyResultDataAccessException e){
		
			return new InvoiceSettings();
		}
	}
	
	/**
	 * Method to save the settings, that are passed in, to the database
	 * @param invoiceSettings An InvoiceSettings object that contains the settings that will be saved to the database
	 */
	public void savePrintSettingsToDatabase(InvoiceSettings invoiceSettings) {

		String sql = "UPDATE receiptsettings SET topBanner = :topBanner,  topBannerX = :topBannerX, topBannerY = :topBannerY, verticalGridLines = :verticalGridLines, " + 
				"bodyImage = :bodyImage, bodyImageX = :bodyImageX, bodyImageY = :bodyImageY, vatRate = :vatRate, footerLine1 = :footerLine1, footerLine2 = :footerLine2, " +
				"footerLine3 = :footerLine3";
			
		SqlParameterSource namedParameters = new MapSqlParameterSource("topBanner", invoiceSettings.getReceiptTopBanner())
		.addValue("topBannerX", invoiceSettings.getReceiptTopBannerX())
		.addValue("topBannerY", invoiceSettings.getReceiptTopBannerY())
		.addValue("verticalGridLines", invoiceSettings.getReceiptVerticalGridLines())
		.addValue("bodyImage", invoiceSettings.getReceiptBodyImage())
		.addValue("bodyImageX", invoiceSettings.getReceiptBodyImageX())
		.addValue("bodyImageY", invoiceSettings.getReceiptBodyImageY())
		.addValue("vatRate", invoiceSettings.getReceiptVatRate())
		.addValue("footerLine1", invoiceSettings.getReceiptFooterLine1())
		.addValue("footerLine2", invoiceSettings.getReceiptFooterLine2())
		.addValue("footerLine3", invoiceSettings.getReceiptFooterLine3());

		this.getNamedParameterJdbcTemplate().update(sql, namedParameters);

	}
		
	
	/**
	 * Static Inner RowMapper Class that maps Customer members to the columns of the receiptsettings table
	 * @author Robert Forde
	 *
	 */
	private static final class InvoiceSettingsMapper implements RowMapper<InvoiceSettings>{

		@Override
		public InvoiceSettings mapRow(ResultSet resultSet, int rownum) throws SQLException {

			InvoiceSettings invoiceSettings = new InvoiceSettings();
			invoiceSettings.setReceiptTopBanner(resultSet.getInt("topBanner"));
			invoiceSettings.setReceiptTopBannerX(resultSet.getInt("topBannerX"));
			invoiceSettings.setReceiptTopBannerY(resultSet.getInt("topBannerY"));
			invoiceSettings.setReceiptVerticalGridLines(resultSet.getInt("verticalGridLines"));
			invoiceSettings.setReceiptBodyImage(resultSet.getInt("bodyImage"));
			invoiceSettings.setReceiptBodyImageX(resultSet.getInt("bodyImageX"));
			invoiceSettings.setReceiptBodyImageY(resultSet.getInt("bodyImageY"));
			invoiceSettings.setReceiptVatRate(resultSet.getFloat("vatRate"));
			invoiceSettings.setReceiptFooterLine1(resultSet.getString("footerLine1"));
			invoiceSettings.setReceiptFooterLine2(resultSet.getString("footerLine2"));
			invoiceSettings.setReceiptFooterLine3(resultSet.getString("footerLine3"));
			
			return invoiceSettings;
		}
	}

}
