package com.daniel.model;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import com.daniel.AccountDetailScreen;
import com.daniel.AccountListScreen;
import com.daniel.Dan;
import com.daniel.dao.CustomerDao;
import com.daniel.dao.CustomerDaoImpl;
import com.daniel.dao.OrderDao;
import com.daniel.dao.OrderDaoImpl;
import com.daniel.utilities.Utilities;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * This class represents retail order objects which are held in the database in both the orderheader and the orderdetail tables. It extends the Order class 
 * @author Robert Forde
 *
 */
public class RetailOrder extends Order implements Printable{

	private InvoiceSettings invoiceSettings;
	private ArrayList<RetailOrderLine> orderList = new ArrayList<RetailOrderLine>();
	
	private CustomerDao customerDao;
	private OrderDao orderDao;
	

	public ArrayList<RetailOrderLine> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList<RetailOrderLine> orderList) {
		this.orderList = orderList;
	} 

	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}
	
	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	
	
	public void addOrderLine(RetailOrderLine retailOrderLine) {
		orderList.add(retailOrderLine);
	}
	
	
	/**
	 * Pre-printing method that ensures the arraylists are updated and that saves the retail order or quote to the appropriate tables.
	 * It implements this method from the order abstract class and therefore implements the method with all of it's parameters
	 * @param invoiceSettings The invoiceSettings object that determines settings in relation to receipt printing
	 * @param accountDetailScreen A reference to the accountDetailScreen so it can be updated after printing account orders 
	 * @param accountListScreen A reference to the accountListScreen so it can be updated after printing account orders
	 * @param acc A reference to the account object that is being updated if this is an account order that is being printed
	 * @throws Exception The exception that will be thrown if an exception occurs
	 */
	public void printOrder(InvoiceSettings invoiceSettings, AccountDetailScreen accountDetailScreen, AccountListScreen accountListScreen, Account acc) throws Exception{
	
		int detailLinesPerPage = 29;
		String headerTable;
		String detailTable;
		String typeNo;

		setCustomerDao(Dan.ctx.getBean("customerDaoImpl", CustomerDaoImpl.class));
		setOrderDao(Dan.ctx.getBean("orderDaoImpl", OrderDaoImpl.class));
		
		// Set the headerTable, detailTable and typeNo appropriately based on whether this is an order or a quote
		if(this.getSaleType().equals("Retail")) {
			headerTable = "orderheader";
			detailTable = "orderdetail";
			typeNo = "receiptNo";
		}else {
			headerTable = "quoteheader";
			detailTable = "quotedetail";
			typeNo = "quotationNo";
		}
		
		// Set the invoice printing settings for this invoice to the current settings when this print was called
		setInvoiceSettings(invoiceSettings);
		
		// Firstly check if the order hasn't got a receipt number and if not get one, save all order lines and order header
		if(this.getReceiptNo() == 0) {
			
			// Call a method to get the next receipt number
			this.setReceiptNo(getOrderDao().findNextReceiptNo(typeNo, headerTable));
			
			// Save the header info into the header table
			getOrderDao().insertHeaderOrder(typeNo, headerTable, this);

			// Save the order lines into the detail table
			int receiptNo = this.getReceiptNo();
			int line = 0;
			for(RetailOrderLine orderLine : this.getOrderList()){
				orderLine.setReceiptNo(receiptNo);
				orderLine.setLineNo(++line);
				getOrderDao().insertRetailDetailOrder(typeNo, detailTable, orderLine);
			}
			
			// add this order line to the number of lines to be printed
			this.setOrderLinesToPrint(line);
			
		}else{
			// Order saved before but to make sure we have all of the correct current info for the order we must save the header and all of the lines again
				
			// Update the header info into the header table
			getOrderDao().updateHeaderOrder(typeNo, headerTable, this);
			
			// Firstly, delete all order lines for this order
			getOrderDao().deleteDetailOrder(typeNo, detailTable, this.getReceiptNo());

			// Then loop through the order lines in the orderLine array and save them all
			int receiptNo = this.getReceiptNo();
			int line = 0;
			for(RetailOrderLine orderLine : this.getOrderList()){
				orderLine.setReceiptNo(receiptNo);
				orderLine.setLineNo(++line);
				getOrderDao().insertRetailDetailOrder(typeNo, detailTable, orderLine);
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

	
	/**
	 * Print method to do the actual printing
	 * @param graphics The graphics object
	 * @param pageFormat The pageFormat
	 * @param pageIndex The pageIndex
	 * @return Returns an int to indicate if the printing was successful
	 * @throws This method can throw a Printer Exception
	 */
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {

        String strOrderLinePrice = "";
		float orderLinePrice = 0.00f;
		float itemPrice = 0.00f;
		int qty = 0;
		String strdiscPercent = "";
		float discPercent = 0.00f;
		int y = 240;
		int currentOrderLine = 1;
		String strTotalExVat = "";
        float totalExVat = 0.00f;
        String strTotalVat = "";
        float totalVat = 0.00f;
        String strTotalPostRound = "";
        float TotalPostRound = 0.00f;
        int detailLinesPerPage = 29;
        
		if (pageIndex >= this.getTotalPage()) 
            return NO_SUCH_PAGE;
        

		Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        // Create the logo ImageIcon
		Image printLogo = new ImageIcon(this.getClass().getResource("/Logo110X109.jpg")).getImage();
		ImageIcon imageIconPrint = new ImageIcon(printLogo);
		
		// Create the faded body logo
		Image printFadeLogo = new ImageIcon(this.getClass().getResource("/Logo Fade 220X219.jpg")).getImage();
		ImageIcon imageIconFadePrint = new ImageIcon(printFadeLogo);
                
        Calendar c = Calendar.getInstance();
        String thisDate = String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(c.get(Calendar.MONTH) + 1) + "/" + String.valueOf(c.get(Calendar.YEAR));  
        
        if(invoiceSettings.getReceiptTopBanner()==1) {
        	g2d.drawImage(imageIconPrint.getImage(), invoiceSettings.getReceiptTopBannerX(), invoiceSettings.getReceiptTopBannerY(), null);
        	//g2d.drawImage(imageIconPrint.getImage(), 30, 20, null);
        }
        
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 28));
        g2d.drawString("D & S Plumbing & Fixings", 150, 40);
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 14));
        g2d.drawString("Unit 101, Malahide Road Industrial Estate, Dublin 17", 150, 80);
        g2d.drawString("Phone: 01 8473868 / 01 8473869",150 ,100 );
        g2d.drawString("Email: info@coolocktileoutlet.com",150 ,120 );
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 16));
        g2d.drawString("Date: " + thisDate, 30, 165);
        g2d.drawString("Invoice No: " + this.getReceiptNo(), 30, 189);
        g2d.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 10));
        g2d.drawRoundRect(200, 140, 200, 50, 20, 20);
        g2d.drawString(this.getName(), 205, 152);
        g2d.drawString(this.getAddress1(), 205, 164);
        g2d.drawString(this.getAddress2(), 205, 176);
        g2d.drawString(this.getPhone(), 205, 188);
        g2d.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 12));
        g2d.drawString("Page " + (pageIndex+1) + " of " + this.getTotalPage(), 480, 177);

		if(invoiceSettings.getReceiptBodyImage()==1) {
			g2d.drawImage(imageIconFadePrint.getImage(), invoiceSettings.getReceiptBodyImageX(), invoiceSettings.getReceiptBodyImageY(), null);
			//g2d.drawImage(imageIconFadePrint.getImage(), 195, 310, null);
		}
		
               
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 12));
        
        if(invoiceSettings.getReceiptVerticalGridLines()==1) {
        	g2d.drawLine(30, 230, 30, 670);
        }
        
        g2d.drawString("ITEM ",32 ,220 );
        
        if(invoiceSettings.getReceiptVerticalGridLines()==1) {
        	g2d.drawLine(115, 230, 115, 670);
        }

        g2d.drawString("DESCRIPTION",120 ,220 );
        
        if(invoiceSettings.getReceiptVerticalGridLines()==1) {
        	g2d.drawLine(380, 230, 380, 670);
        }
        
        g2d.drawString("PRICE",385 ,220 );
        
        if(invoiceSettings.getReceiptVerticalGridLines()==1) {
        	g2d.drawLine(430, 230, 430, 670);
        }
        
        g2d.drawString("QTY",435 ,220 );
        
        if(invoiceSettings.getReceiptVerticalGridLines()==1) {
        	g2d.drawLine(470, 230, 470, 670);
        }
        
        g2d.drawString("DISC",482 ,220 );
        
        if(invoiceSettings.getReceiptVerticalGridLines()==1) {
        	g2d.drawLine(520, 230, 520, 670);
        }
        
        g2d.drawString("TOTAL",530 ,220 );
        
        if(invoiceSettings.getReceiptVerticalGridLines()==1) {
        	g2d.drawLine(570, 230, 570, 670);
        }
        
        g2d.drawLine(30, 230, 570, 230);
		
		// Loop through order and print the order lines
        
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		for(RetailOrderLine ol : this.getOrderList()) {
			
			// Only print order lines that are meant for the current page
			if(currentOrderLine > pageIndex *detailLinesPerPage && currentOrderLine <= (pageIndex+1)*detailLinesPerPage) {
			
				g2d.drawString(ol.getItemCode(), 32 ,y );
				
				g2d.drawString(ol.getItemDescription(), 120 ,y );
				
				String strItemPrice = ol.getItemPrice();
				itemPrice = Float.parseFloat(strItemPrice);
				if(itemPrice > 999.99 && itemPrice < 10000) {
					g2d.drawString(strItemPrice, 385 ,y );
				}
				else if(itemPrice > 99.99 && itemPrice < 1000) {
					g2d.drawString(strItemPrice, 391 ,y );
				}
				else if(itemPrice > 9.99 && itemPrice < 100) {
					g2d.drawString(strItemPrice, 398 ,y );
				}
				else if(itemPrice < 10) {
					g2d.drawString(strItemPrice, 404 ,y );
				}
				
				String strQty = ol.getOrderQty();
				qty =  Integer.parseInt(strQty);
				if(qty < 10) {
					g2d.drawString(strQty, 457 ,y );				
				}else if(qty < 100) {
					g2d.drawString(strQty, 450 ,y );
				}else {
					g2d.drawString(strQty, 443 ,y );
				}

				strdiscPercent = Utilities.stringToDec(ol.getDiscountPercent());
				discPercent = Float.parseFloat(strdiscPercent);
				if(discPercent < 10) {
					g2d.drawString(strdiscPercent + " %", 481 ,y );
				} else {
					g2d.drawString(strdiscPercent + " %", 475 ,y );
				}
				
				strOrderLinePrice = ol.getValueExVat();
				orderLinePrice = Float.parseFloat(ol.getValueExVat());
				if(orderLinePrice>9999.99) {
					g2d.drawString(strOrderLinePrice, 522 ,y );
				}
				else if(orderLinePrice > 999.99 && orderLinePrice < 10000) {
					g2d.drawString(strOrderLinePrice, 529 ,y );
				}
				else if(orderLinePrice > 99.99 && orderLinePrice < 1000) {
					g2d.drawString(strOrderLinePrice, 535 ,y );
				}
				else if(orderLinePrice > 9.99 && orderLinePrice < 100) {
					g2d.drawString(strOrderLinePrice, 541 ,y );
				}
				else if(orderLinePrice < 10) {
					g2d.drawString(strOrderLinePrice, 547 ,y );
				}
				y += 15;
			}
			currentOrderLine++;
		}
		
		// Print the total if this is the last page
		if(pageIndex + 1 == this.getTotalPage()){
			
			g2d.drawString("Total Excl VAT", 435 ,684 );
			strTotalExVat = this.getTotalExVat();
			totalExVat = Float.parseFloat(strTotalExVat);
			if(totalExVat>9999.99) {
				g2d.drawString(strTotalExVat, 522 ,684 );
			}
			else if(totalExVat > 999.99 && totalExVat < 10000) {
				g2d.drawString(strTotalExVat, 525 ,684 );
			}
			else if(totalExVat > 99.99 && totalExVat < 1000) {
				g2d.drawString(strTotalExVat, 532 ,684 );
			}
			else if(totalExVat > 9.99 && totalExVat < 100) {
				g2d.drawString(strTotalExVat, 539 ,684 );
			}
			else if(totalExVat < 10) {
				g2d.drawString(strTotalExVat, 545 ,684 );
			}
			
			g2d.drawLine(430, 689, 570, 689);
			
			g2d.drawString("VAT @ " + Utilities.stringToDec(Float.toString(invoiceSettings.getReceiptVatRate())) + "%", 433 ,702 );
			strTotalVat = this.getTotalVat();
			totalVat = Float.parseFloat(strTotalVat);
			if(totalVat>9999.99) {
				g2d.drawString(strTotalVat, 522 ,702 );
			}
			else if(totalVat > 999.99 && totalVat < 10000) {
				g2d.drawString(strTotalVat, 525 ,702 );
			}
			else if(totalVat > 99.99 && totalVat < 1000) {
				g2d.drawString(strTotalVat, 532 ,702 );
			}
			else if(totalVat > 9.99 && totalVat < 100) {
				g2d.drawString(strTotalVat, 539 ,702 );
			}
			else if(totalVat < 10) {
				g2d.drawString(strTotalVat, 545 ,702 );
			}
			
			g2d.drawLine(430, 708, 570, 708);

			g2d.setFont(new Font("TimesRoman", Font.BOLD, 14));
			g2d.drawString("TOTAL", 460 ,722 );
			strTotalPostRound = this.getTotalPostRounding();
			TotalPostRound = Float.parseFloat(strTotalPostRound);
			if(TotalPostRound>9999.99) {
				g2d.drawString(strTotalPostRound, 522, 722 );
			}
			else if(TotalPostRound > 999.99 && TotalPostRound < 10000) {
				g2d.drawString(strTotalPostRound, 522, 722 );
			}
			else if(TotalPostRound > 99.99 && TotalPostRound < 1000) {
				g2d.drawString(strTotalPostRound, 530, 722 );
			}
			else if(TotalPostRound > 9.99 && TotalPostRound < 100) {
				g2d.drawString(strTotalPostRound, 537, 722 );
			}
			else if(TotalPostRound < 10) {
				g2d.drawString(strTotalPostRound, 544, 722 );
			}
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			
			if(invoiceSettings.getReceiptVerticalGridLines()==1) {
				g2d.drawLine(430, 670, 430, 728);
			}
			if(invoiceSettings.getReceiptVerticalGridLines()==1) {
				g2d.drawLine(520, 670, 520, 728);
			}
			if(invoiceSettings.getReceiptVerticalGridLines()==1) {
				g2d.drawLine(570, 670, 570, 728);
			}
			
			g2d.drawLine(430, 728, 570, 728);
		}
		
        g2d.drawLine(30, 670, 570, 670);

        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g2d.drawString(invoiceSettings.getReceiptFooterLine1(), 30 ,720 );
        g2d.drawString(invoiceSettings.getReceiptFooterLine2(), 30 ,730 );
        g2d.drawString(invoiceSettings.getReceiptFooterLine3(), 30 ,740 );
        /*g2d.drawString("Returns are accepted up to 30 days from time of purchase", 30 ,720 );
        g2d.drawString("Returns must be complete, unused, in their original packaging and accompanied by a receipt", 30 ,730 );
        g2d.drawString("Please note, there is no return on powered goods", 30 ,740 );
        */
		
		/* tell the caller that this page is part of the printed document */

        return PAGE_EXISTS;
	}

	
	/**
	 * Method to set and to update the values based on a change in the overall discount of the order
	 * @param txtCustDiscount The JTextfield holding the overall discount for the order
	 * @param vatRate The vat rate of the order
	 * @param oldOverallDiscount The previous overall discount
	 * @param lblGrossProfitValue The label that holds the gross profit value
	 * @param lblGrossProfitPercent The label that holds the gross profit percent
	 */
	public void recalculateDiscount(JTextField txtCustDiscount, float vatRate, String oldOverallDiscount, 
									JLabel lblGrossProfitValue, JLabel lblGrossProfitPercent){
		
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
		for(RetailOrderLine retailOrderLine : getOrderList()){
							
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

			for(RetailOrderLine retailOrderLine : getOrderList()){

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
	
	
	/**
	 * This method re-calculates the gross profoit value and the gross profit percent
	 * @param lblGrossProfitValue The label that holds the gross profit value
	 * @param lblGrossProfitPercent The label that holds the gross profit percent
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
	 * Method to re-calculate an order's values, overall discount and gross profit value and percentage after a line has been deleted 
	 * @param vatRate The vat rate of the order
	 * @param oldOverallDiscount The overall discount prior to the deletion
	 * @param lblGrossProfitValue The label that holds the gross profit value
	 * @param lblGrossProfitPercent The label that holds the gross profit percent
	 */
	public void recalculateAfterDelete(float vatRate, String oldOverallDiscount, JLabel lblGrossProfitValue, JLabel lblGrossProfitPercent){
		
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
		//float lineTradeExVat = 0.00f;
		//float totalTradeExVat = 0.00f;
		float totalCost = 0.00f;
		
		// Zeroise the total orderline and order values so they can be re-calculated
		setTotalExVat("0.00");
		setTotalVat("0.00");
		setTotalPreRounding("0.00");
		setTotalPostRounding("0.00");
		
		// Loop through the order lines and recalculate the relevant line and header values
		for(RetailOrderLine retailOrderLine : getOrderList()){
							
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
			
			// Calculate what the ex-vat value of this order would be at the trade price so we can see that this is not cheaper.
			//lineTradeExVat = retailOrderLine.getItemTradePrice() * Integer.parseInt(retailOrderLine.getOrderQty());
			//totalTradeExVat += lineTradeExVat;
			
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

}