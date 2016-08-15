package com.daniel.model;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.daniel.AccountDetailScreen;
import com.daniel.AccountListScreen;
import com.daniel.Dan;
import com.daniel.dao.CustomerDao;
import com.daniel.dao.CustomerDaoImpl;
import com.daniel.dao.SuspendedDao;
import com.daniel.dao.SuspendedDaoImpl;
import com.daniel.utilities.Utilities;

/**
 * This class represents suspended order objects which are held in the database in both the suspendheader and the suspenddetail tables. It extends the Order class
 * @author Robert Forde
 *
 */
public class SuspendedOrder  extends Order implements Printable{

	private InvoiceSettings invoiceSettings;
	
	private List<SuspendedOrderLine> orderList = new ArrayList<SuspendedOrderLine>();
	
	private float balance;
	private float paid;
	private int deleted;
	private Date deletedDate;
	
	private CustomerDao customerDao;
	private SuspendedDao suspendedDao;
		
	
	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	public SuspendedDao getSuspendedDao() {
		return suspendedDao;
	}

	public void setSuspendedDao(SuspendedDao suspendedDao) {
		this.suspendedDao = suspendedDao;
	}
	
	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}
	
	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}
	
	public List<SuspendedOrderLine> getOrderList() {
		return orderList;
	}
	
	public void setOrderList(List<SuspendedOrderLine> suspendedLinesList) {
		this.orderList = suspendedLinesList;
	}
	
	public float getBalance() {
		return balance;
	}
	
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public float getPaid() {
		return paid;
	}

	public void setPaid(float paid) {
		this.paid = paid;
	}

	public int getDeleted() {
		return deleted;
	}
	
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	public Date getDeletedDate() {
		return deletedDate;
	}
	
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	
	
	
	public void addOrderLine(SuspendedOrderLine suspendedOrderLine) {
		orderList.add(suspendedOrderLine);
	}
	

	/**
	 * Pre-printing method that ensures the arraylists are updated and that saves the suspended order to the appropriate tables.
	 * It implements this method from the order abstract class and therefore implements the method with all of it's parameters
	 * @param invoiceSettings The invoiceSettings object that determines settings in relation to receipt printing
	 * @param accountDetailScreen A reference to the accountDetailScreen so it can be updated after printing account orders 
	 * @param accountListScreen A reference to the accountListScreen so it can be updated after printing account orders
	 * @param acc A reference to the account object that is being updated if this is an account order that is being printed
	 * @throws Exception The exception that will be thrown if an exception occurs
	 */
	@Override
	public void printOrder(InvoiceSettings invoiceSettings, AccountDetailScreen accountDetailScreen, AccountListScreen accountListScreen, Account acc) throws Exception {
		
		int detailLinesPerPage = 29;

		setCustomerDao(Dan.ctx.getBean("customerDaoImpl", CustomerDaoImpl.class));
		setSuspendedDao(Dan.ctx.getBean("suspendedDaoImpl", SuspendedDaoImpl.class));
		
		// Set the invoice printing settings for this invoice to the current settings when this print was called
		setInvoiceSettings(invoiceSettings);
		
		// Firstly check if the order hasn't got a receipt number and if not get one, save all order lines and order header
		if(this.getReceiptNo() == 0) {
			
			// Call a method to get the next receipt number
			this.setReceiptNo(getSuspendedDao().findNextReceiptNo());
			
			// Update the balance of the order with the totalPostRounding and save the header info into the header table
			this.setBalance(Float.valueOf(this.getTotalPostRounding()));
			getSuspendedDao().insertHeaderOrder(this);

			// Save the order lines into the detail table
			int receiptNo = this.getReceiptNo();
			int line = 0;
			for(SuspendedOrderLine orderLine : this.getOrderList()){
				orderLine.setReceiptNo(receiptNo);
				orderLine.setLineNo(++line);
				getSuspendedDao().insertRetailDetailOrder(orderLine);
			}
			
			// add this order line to the number of lines to be printed
			this.setOrderLinesToPrint(line);
			
		}else{
			// Order saved before but to make sure we have all of the correct current info for the order we must save the header and all of the lines again
				
			// Re-set the balance and update the header info into the suspendheader table
			this.setBalance(Float.valueOf(this.getTotalPostRounding()));
			getSuspendedDao().updateHeaderOrder(this);
			
			// Firstly, delete all order lines for this order
			getSuspendedDao().deleteDetailOrder(this.getReceiptNo());

			// Then loop through the order lines in the orderLine array and save them all
			int receiptNo = this.getReceiptNo();
			int line = 0;
			for(SuspendedOrderLine orderLine : this.getOrderList()){
				orderLine.setReceiptNo(receiptNo);
				orderLine.setLineNo(++line);
				getSuspendedDao().insertRetailDetailOrder(orderLine);
			}
			
			// update the order line to the number of lines to be printed
			this.setOrderLinesToPrint(line);
			
		}
			
		// Save the Customer Details to the customer table if new customer details have been entered 
		if(getCustId()==0 && (!getName().isEmpty() || !getAddress1().isEmpty() || !getAddress2().isEmpty() || !getPhone().isEmpty()) ){

			Customer customer = new Customer(getName(), getAddress1(), getAddress2(), getPhone(), 0);
			getCustomerDao().customerInsert(customer);
					
		}else if(getCustId()!=0){

			Customer customer = new Customer(getCustId(), getName(), getAddress1(), getAddress2(), getPhone(), 0);
			getCustomerDao().customerUpdate(customer);
	
		}
				
		PrinterJob job = PrinterJob.getPrinterJob();
	    job.setPrintable(this);
	    boolean ok = job.printDialog();
	    
	    // Calculate the total number of pages
	    this.setTotalPage( (this.getOrderLinesToPrint()/detailLinesPerPage) + (this.getOrderLinesToPrint()%detailLinesPerPage==0 ? 0:1) );
	    
	    if (ok) {
	       	try {
	       		job.print();
	       	} catch (PrinterException ex) {
	       		JOptionPane.showMessageDialog(null, ex); 
	       	}
	    }
		
	}
	
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	/**
	 * Print method to do the actual printing
	 * @param graphics The graphics object
	 * @param pageFormat The pageFormat
	 * @param pageIndex The pageIndex
	 * @return Returns an int to indicate if the printing was successful
	 * @throws This method can throw a Printer Exception
	 */
	public void recalculateAfterDelete(float vatRate, JLabel lblGrossProfitValue, JLabel lblGrossProfitPercent){
			
		float valExDisc = 0.00f;
		String strvalExDisc = "";
		float discPercent = 0.00f;
		String strDiscPercent = "";
		float discValue = 0.00f;
		String strDiscValue = "";
		float valExVat = 0.00f;
		String strValExVat = "";
		float orderTotExVat = 0.00f;
		String strOrderTotExVat = "";
		float totalVat = 0.00f;
		float totalPreRounding = 0.00f;
		float totalCost = 0.00f;
			
		// Zeroise the total orderline and order values so they can be re-calculated
		setTotalExVat("0.00");
		setTotalVat("0.00");
		setTotalPreRounding("0.00");
		setTotalPostRounding("0.00");
			
		// Loop through the order lines and recalculate the relevant line and header values
		for(SuspendedOrderLine retailOrderLine : getOrderList()){
								
			// Get Order Line Price exclusive of discount and covert it to a 2 decimal string
			valExDisc = Float.parseFloat(retailOrderLine.getItemPrice()) * Integer.parseInt(retailOrderLine.getOrderQty());
			strvalExDisc = String.format("%.2f", valExDisc);
			strvalExDisc = Utilities.stringToDec(strvalExDisc);
				
			discPercent = Float.parseFloat(retailOrderLine.getDiscountPercent());

			strDiscPercent = String.format("%.2f", discPercent);
			strDiscPercent = Utilities.stringToDec(strDiscPercent);
				
			// Get Order Line Discount Value and convert it to a 2 decimal string
			discValue = (valExDisc/100) * discPercent;
			discValue = Utilities.floatToNumDec(discValue,2);
			strDiscValue = String.format("%.2f", discValue);
			strDiscValue = Utilities.stringToDec(strDiscValue);
				
			// Get Order Line Price - Discount exclusive of vat and covert it to a 2 decimal string
			valExVat = valExDisc - discValue;
			strValExVat = String.format("%.2f", valExVat);
			strValExVat = Utilities.stringToDec(strValExVat);
				
			// Add the Order Line total to the Order's total ex-vat value and round to 2 Decimals
			orderTotExVat += Utilities.floatToNumDec(valExVat,2);
			strOrderTotExVat = String.format("%.2f",orderTotExVat);
			strOrderTotExVat = Utilities.stringToDec(strOrderTotExVat);
				
			// Calculate the vat and add it to the order
			totalVat = Utilities.floatToNumDec(orderTotExVat/100*vatRate,2);
			totalPreRounding = Utilities.floatToNumDec(orderTotExVat + totalVat,2);
				
			// Add up the cost of the remaining items
			totalCost += retailOrderLine.getLineCostValue();
		}

		// Update the cost price
		setTotalCost(totalCost);
			
		setTotalExVat(strOrderTotExVat);
		setTotalVat(Utilities.stringToDec(String.valueOf(totalVat)));
		
		// Re-calculate the Gross Profit
		calcGrossProfit(lblGrossProfitValue, lblGrossProfitPercent);

		// Remove any rounding that exists because the price is being re-calculated
		setTotalPreRounding(Utilities.stringToDec(String.valueOf(totalPreRounding)));
		setTotalPostRounding(Utilities.stringToDec(getTotalPreRounding()));
		setRounding("0.00");

	}
	

	/**
	 * Method that calculates the gross profit on the suspended order
	 * @param lblGrossProfitValue The label that holds the Gross profit value
	 * @param lblGrossProfitPercent The label that holds the Gross profit percent
	 */
	public void calcGrossProfit(JLabel lblGrossProfitValue, JLabel lblGrossProfitPercent) {
		
		float sellingPrice = Float.valueOf(getTotalExVat());
		float costPrice = getTotalCost();
		float grossProfit = Utilities.floatToNumDec(sellingPrice - costPrice,2);
		float grossProfitPercent = Utilities.floatToNumDec(grossProfit / costPrice * 100,2);
		lblGrossProfitValue.setText("\u20AC " + Utilities.stringToDec(String.valueOf(grossProfit)));
		lblGrossProfitPercent.setText(Utilities.stringToDec(String.valueOf(grossProfitPercent)) + " %");
	}
	
	
	/**
	 * Method to 
	 * @param txtCustDiscount
	 * @param vatRate
	 * @param oldOverallDiscount
	 * @param lblGrossProfitValue
	 * @param lblGrossProfitPercent
	 */
	public void recalculateDiscount(JTextField txtCustDiscount, float vatRate, String oldOverallDiscount, JLabel lblGrossProfitValue, JLabel lblGrossProfitPercent){
			
		float valExDisc = 0.00f;
		String strvalExDisc = "";
		float discPercent = 0.00f;
		String strDiscPercent = "";
		float discValue = 0.00f;
		String strDiscValue = "";
		float valExVat = 0.00f;
		String strValExVat = "";
		float orderTotExVat = 0.00f;
		String strOrderTotExVat = "";
		float totalVat = 0.00f;
		float totalPreRounding = 0.00f;
		float lineTradeExVat = 0.00f;
		float totalTradeExVat = 0.00f;
			
		// Zeroise the total orderline and order values so they can be re-calculated
		setTotalExVat("0.00");
		setTotalVat("0.00");
		setTotalPreRounding("0.00");
		setTotalPostRounding("0.00");
			
		// Loop through the order lines and recalculate the relevant line and header values
		for(SuspendedOrderLine retailOrderLine : getOrderList()){
								
			// Get Order Line Price exclusive of discount and covert it to a 2 decimal string
			valExDisc = Float.parseFloat(retailOrderLine.getItemPrice()) * Integer.parseInt(retailOrderLine.getOrderQty());
			strvalExDisc = String.format("%.2f", valExDisc);
			strvalExDisc = Utilities.stringToDec(strvalExDisc);
			
			if(txtCustDiscount.getText().trim().equals("")){
				discPercent = 0.00f;
			}
			else{
				discPercent = Float.parseFloat(txtCustDiscount.getText());
			}
			strDiscPercent = String.format("%.2f", discPercent);
			strDiscPercent = Utilities.stringToDec(strDiscPercent);
				
			// Get Order Line Discount Value and convert it to a 2 decimal string
			discValue = (valExDisc/100) * discPercent;
			discValue = Utilities.floatToNumDec(discValue,2);
			strDiscValue = String.format("%.2f", discValue);
			strDiscValue = Utilities.stringToDec(strDiscValue);
				
			// Get Order Line Price - Discount exclusive of vat and covert it to a 2 decimal string
			valExVat = valExDisc - discValue;
			strValExVat = String.format("%.2f", valExVat);
			strValExVat = Utilities.stringToDec(strValExVat);
				
			// Add the Order Line total to the Order's total ex-vat value and round to 2 Decimals
			orderTotExVat += Utilities.floatToNumDec(valExVat,2);
			strOrderTotExVat = String.format("%.2f",orderTotExVat);
			strOrderTotExVat = Utilities.stringToDec(strOrderTotExVat);
				
			// Calculate the vat and add it to the order
			totalVat = Utilities.floatToNumDec(orderTotExVat/100*vatRate,2);
			totalPreRounding = Utilities.floatToNumDec(orderTotExVat + totalVat,2);
				
			// Calculate what the ex-vat value of this order would be at the trade price so we can see that this is not cheaper.
			lineTradeExVat = retailOrderLine.getItemTradePrice() * Integer.parseInt(retailOrderLine.getOrderQty());
			totalTradeExVat += lineTradeExVat; 
		}
				
		// Remove any rounding that exists because the price is being re-calculated
		setTotalPreRounding(Utilities.stringToDec(String.valueOf(totalPreRounding)));
		setTotalPostRounding(Utilities.stringToDec(getTotalPreRounding()));
		setRounding("0.00");

		// Check that the customer ex vat price after discount is more than the trade value
		if(orderTotExVat > totalTradeExVat) {
				
			// If the ex-vat price is okay then make the discount changes
			valExDisc = discPercent = discValue = valExVat = orderTotExVat = totalVat = totalPreRounding = 0.00f;
			strvalExDisc = strDiscPercent = strDiscValue = strValExVat = strOrderTotExVat = "";

			for(SuspendedOrderLine retailOrderLine : getOrderList()){

				// Get Order Line Price exclusive of discount and covert it to a 2 decimal string
				valExDisc = Float.parseFloat(retailOrderLine.getItemPrice()) * Integer.parseInt(retailOrderLine.getOrderQty());
				strvalExDisc = String.format("%.2f", valExDisc);
				strvalExDisc = Utilities.stringToDec(strvalExDisc);
						
				if(txtCustDiscount.getText().trim().equals("")){
					discPercent = 0.00f;
				}
				else{
					discPercent = Float.parseFloat(txtCustDiscount.getText());
				}
				strDiscPercent = String.format("%.2f", discPercent);
				strDiscPercent = Utilities.stringToDec(strDiscPercent);
						
				// Get Order Line Discount Value and convert it to a 2 decimal string
				discValue = (valExDisc/100) * discPercent;
				discValue = Utilities.floatToNumDec(discValue,2);
				strDiscValue = String.format("%.2f", discValue);
				strDiscValue = Utilities.stringToDec(strDiscValue);
						
				// Get Order Line Price - Discount exclusive of vat and covert it to a 2 decimal string
				valExVat = valExDisc - discValue;
				strValExVat = String.format("%.2f", valExVat);
				strValExVat = Utilities.stringToDec(strValExVat);
						
				// Add the Order Line total to the Order's total ex-vat value and round to 2 Decimals
				orderTotExVat += Utilities.floatToNumDec(valExVat,2);
				strOrderTotExVat = String.format("%.2f",orderTotExVat);
				strOrderTotExVat = Utilities.stringToDec(strOrderTotExVat);
						
				// Calculate the vat and add it to the order
				totalVat = Utilities.floatToNumDec(orderTotExVat/100*vatRate,2);
				totalPreRounding = Utilities.floatToNumDec(orderTotExVat + totalVat,2);
					
				retailOrderLine.setValueExDiscount(strvalExDisc);
				retailOrderLine.setDiscountPercent(strDiscPercent);
				retailOrderLine.setDiscountValue(strDiscValue);
				retailOrderLine.setValueExVat(strValExVat);
			}
				
			setTotalExVat(strOrderTotExVat);
			setTotalVat(Utilities.stringToDec(String.valueOf(totalVat)));
			
			// Re-calculate the Gross Profit
			calcGrossProfit(lblGrossProfitValue, lblGrossProfitPercent);
				
										
			// Remove any rounding that exists because the price is being re-calculated
			setTotalPreRounding(Utilities.stringToDec(String.valueOf(totalPreRounding)));
			setTotalPostRounding(Utilities.stringToDec(getTotalPreRounding()));
			setRounding("0.00");
				
		} else {
			JOptionPane.showMessageDialog(null, txtCustDiscount.getText() + "% is too much discount !");
			txtCustDiscount.setText(oldOverallDiscount);
		}
	}
	
	
	// Method to add or delete lines for a suspended order and to update the suspended order header appropriately, Changes made already to the order and orderList objects
	public void updateOrderAndLines() {

		setSuspendedDao(Dan.ctx.getBean("suspendedDaoImpl", SuspendedDaoImpl.class));
		
		// Update the suspended order header table
		getSuspendedDao().updateHeaderOrder(this);
		
		// Then, delete all order lines for this order
		getSuspendedDao().deleteDetailOrder(this.getReceiptNo());
	
		// Then loop through the order lines in the orderLine array and save them all
		int receiptNo = this.getReceiptNo();
		int line = 0;
		for(SuspendedOrderLine orderLine : this.getOrderList()){
			orderLine.setReceiptNo(receiptNo);
			orderLine.setLineNo(++line);
			getSuspendedDao().insertRetailDetailOrder(orderLine);
		}
	}
	
}