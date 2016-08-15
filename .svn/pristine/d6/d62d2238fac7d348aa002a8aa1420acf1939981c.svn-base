package com.daniel.model;

import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.daniel.utilities.Utilities;

/**
 * This class represents suspended order line objects i.e the detail lines on a suspended order. It extends the OrderLine class
 * @author Robert Forde
 *
 */
public class SuspendedOrderLine extends OrderLine{

	private String discountPercent = "0.00";
	private String discountValue = "0.00";
	private int dispatched;
	private Date dispatchedDate;
	private int deleted;
	private Date deleteDate;
	
	
	public String getDiscountPercent() {
		return discountPercent;
	}
	
	public void setDiscountPercent(String discountPercent) {
		this.discountPercent = discountPercent;
	}
	
	public String getDiscountValue() {
		return discountValue;
	}
	
	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}
	
	public int getDispatched() {
		return dispatched;
	}

	public void setDispatched(int dispatched) {
		this.dispatched = dispatched;
	}

	public Date getDispatchedDate() {
		return dispatchedDate;
	}

	public void setDispatchedDate(Date dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}

	public int getDeleted() {
		return deleted;
	}
	
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	public Date getDeleteDate() {
		return deleteDate;
	}
	
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	
	/**
	 * Method to add a suspended retial order line to an order. It calculates retail order header values and adds the order line to the order and re-calculates gross profit values
	 * @param txtSuspendLineCode JTextfield holding the Item Code
	 * @param txtSuspendLineDesc JTextfield holding the Item Description
	 * @param txtSuspendLineQty JTextfield holding the Line Quantity
	 * @param txtSuspendLinePrice JTextfield holding the Item Price
	 * @param txtSuspendLineDisc JTextfield holding the Line Discount
	 * @param txtSuspendDiscount JTextfield holding the Overall Discount
	 * @param lblGrossProfitValue The Label holding the Gross Profit Value
	 * @param lblGrossProfitPercent The Label holding the Gross Profit Percent
	 * @param order The Suspended Order that this line belongs to
	 * @param suspendTradeLinePrice The Trade Price of this item
	 * @param orderLineCost It Cost of one of this item
	 * @param vatRate The vat rate to be applied to this order
	 * @param afterAdd A boolean to determine whether or not to add the value of this line to the order value
	 */
	public void addRetailOrderLine(JTextField txtSuspendLineCode, JTextField txtSuspendLineDesc, JTextField txtSuspendLineQty, JTextField txtSuspendLinePrice, 
			JTextField txtSuspendLineDisc, JTextField txtSuspendDiscount, JLabel lblGrossProfitValue, JLabel lblGrossProfitPercent, 
			SuspendedOrder order, float suspendTradeLinePrice, float orderLineCost, float vatRate, boolean afterAdd){

		float valExDisc;
		String strvalExDisc;
		float discPercent;
		String strDiscPercent;
		float discValue;
		String strDiscValue;
		float valExVat;
		String strValExVat;
		float orderTotExVat;
		String strorderTotExVat;
		float totalVat;
		float lineIncVat;
		float totalPreRounding;
		float sellingPrice;
		float costPrice;
		float grossProfit;
		float grossProfitPercent;
		float balance;
		
		
		setItemCode(txtSuspendLineCode.getText());
		setItemDescription(txtSuspendLineDesc.getText());
		setOrderQty(txtSuspendLineQty.getText());
		
		// Convert Item Price to 2 decimal string
		String strItemPrice = Utilities.stringToDec(txtSuspendLinePrice.getText());
		setItemPrice(strItemPrice);
		
		// Save the Item's Trade Price as a float
		setItemTradePrice(suspendTradeLinePrice);
		
		// Calculate and Save the Line Cost Value
		setLineCostValue(Integer.valueOf(getOrderQty()) * orderLineCost);
		
		// Get Order Line Price exclusive of discount and covert it to a 2 decimal string
		valExDisc = Float.parseFloat(getItemPrice()) * Integer.parseInt(getOrderQty());
		strvalExDisc = String.format("%.2f", valExDisc);
		strvalExDisc = Utilities.stringToDec(strvalExDisc);
		setValueExDiscount(strvalExDisc);
		
		if(txtSuspendDiscount.getText().equals("")){
			// Get Order Line Discount Percentage and covert it to a 2 decimal string
			if(!txtSuspendLineDisc.getText().trim().equals("")) {
				discPercent = Float.parseFloat(txtSuspendLineDisc.getText());
				strDiscPercent = String.format("%.2f", discPercent);
				strDiscPercent = Utilities.stringToDec(strDiscPercent);
			}else{
				discPercent = 0.0f;
				strDiscPercent = "0.00";
			}
		
		}else{
			discPercent = Float.parseFloat(txtSuspendDiscount.getText());
			strDiscPercent = String.format("%.2f", discPercent);
			strDiscPercent = Utilities.stringToDec(strDiscPercent);
		}
		setDiscountPercent(strDiscPercent);
		
		// Get Order Line Discount Value and convert it to a 2 decimal string
		discValue = (valExDisc/100) * discPercent;
		discValue = Utilities.floatToNumDec(discValue,2);
		strDiscValue = String.format("%.2f", discValue);
		strDiscValue = Utilities.stringToDec(strDiscValue);
		setDiscountValue(strDiscValue);
		
		// Get Order Line Price - Discount exclusive of vat and covert it to a 2 decimal string
		valExVat = valExDisc - discValue;
		strValExVat = String.format("%.2f", valExVat);
		strValExVat = Utilities.stringToDec(strValExVat);
		setValueExVat(strValExVat);
		
		order.addOrderLine(this);
		
		// Add the Order Line total to the Order's total ex-vat value and round to 2 Decimals
		orderTotExVat = Utilities.floatToNumDec(Float.parseFloat(order.getTotalExVat()) + valExVat,2);
		strorderTotExVat = String.format("%.2f",orderTotExVat);
		strorderTotExVat = Utilities.stringToDec(strorderTotExVat);
		order.setTotalExVat(strorderTotExVat);
		
		// Calculate the vat and add it to the order
		totalVat = Utilities.floatToNumDec(orderTotExVat/100*vatRate,2);
		totalPreRounding = Utilities.floatToNumDec(orderTotExVat + totalVat,2);
		order.setTotalVat(Utilities.stringToDec(String.valueOf(totalVat)));
		order.setTotalPreRounding(Utilities.stringToDec(String.valueOf(totalPreRounding)));
		
		// Set the Post Rounding price to the same as the Pre Rounding price as there is no rounding with new order lines and clear the order's rounding value
		order.setTotalPostRounding(Utilities.stringToDec(order.getTotalPreRounding()));
		order.setRounding("0.00");
		
		// Add the Line Cost Value to the order's total cost
		order.setTotalCost(order.getTotalCost() + getLineCostValue());
		
		sellingPrice = Float.valueOf(order.getTotalExVat());
		costPrice = order.getTotalCost();
		grossProfit = Utilities.floatToNumDec(sellingPrice - costPrice,2);
		grossProfitPercent = Utilities.floatToNumDec(grossProfit / costPrice * 100,2);
		lblGrossProfitValue.setText("\u20AC " + Utilities.stringToDec(String.valueOf(grossProfit)));
		lblGrossProfitPercent.setText(Utilities.stringToDec(String.valueOf(grossProfitPercent)) + " %");
	
		if(afterAdd) {
			// Add the Order Line total to the Order's balance
			balance = order.getBalance();
			lineIncVat = valExVat * (1 + (vatRate/100));
			order.setBalance(balance + lineIncVat);
			
			// Call the suspended order's method that saves the current order and it's lines to the tables
			order.updateOrderAndLines();
		}
	}
	
	
	/**
	 * Method to add a suspended trade order line to an order. It calculates trade order header values and adds the order line to the order and re-calculates gross profit values
	 * @param txtTradeLineCode JTextfield holding the Item Code
	 * @param txtTradeLineDesc JTextfield holding the Item Description
	 * @param txtTradeLineQty JTextfield holding the Line Quantity
	 * @param txtTradeLinePrice JTextfield holding the Item Price
	 * @param lblTradeGrossProfitValue The Label holding the Gross Profit Value
	 * @param lblTradeGrossProfitPercent The Label holding the Gross Profit Percent
	 * @param order The Suspended Trade Order that this line belongs to
	 * @param txtTradeLineCost The Item's Cost Value
	 * @param vatRate The vat rate for this order
	 * @param afterAdd A boolean to determine whether or not to add the value of this line to the order value
	 */
	public void addTradeOrderLine(JTextField txtTradeLineCode, JTextField txtTradeLineDesc, JTextField txtTradeLineQty, JTextField txtTradeLinePrice, 
			JLabel lblTradeGrossProfitValue, JLabel lblTradeGrossProfitPercent, SuspendedOrder order, float txtTradeLineCost, float vatRate, boolean afterAdd){

		float valExDisc;
		String strvalExDisc;
		float valExVat;
		String strValExVat;
		float orderTotExVat;
		String strorderTotExVat;
		float totalVat;
		float lineIncVat;
		float totalPreRounding;
		float sellingPrice;
		float costPrice;
		float grossProfit;
		float grossProfitPercent;
		float balance;
		
		
		setItemCode(txtTradeLineCode.getText());
		setItemDescription(txtTradeLineDesc.getText());
		setOrderQty(txtTradeLineQty.getText());
		
		// Convert Item Price to 2 decimal string
		String strItemPrice = Utilities.stringToDec(txtTradeLinePrice.getText());
		setItemPrice(strItemPrice);
		
		// Calculate and Save the Line Cost Value
		setLineCostValue(Integer.valueOf(getOrderQty()) * txtTradeLineCost);
		
		// Get Order Line Price exclusive of discount and covert it to a 2 decimal string
		valExDisc = Float.parseFloat(getItemPrice()) * Integer.parseInt(getOrderQty());
		strvalExDisc = String.format("%.2f", valExDisc);
		strvalExDisc = Utilities.stringToDec(strvalExDisc);
		setValueExDiscount(strvalExDisc);
		
		// Get Order Line Price exclusive of vat and covert it to a 2 decimal string
		valExVat = valExDisc;
		strValExVat = String.format("%.2f", valExVat);
		strValExVat = Utilities.stringToDec(strValExVat);
		setValueExVat(strValExVat);
		
		order.addOrderLine(this);
		
		// Add the Order Line total to the Order's total ex-vat value and round to 2 Decimals
		orderTotExVat = Utilities.floatToNumDec(Float.parseFloat(order.getTotalExVat()) + valExVat,2);
		strorderTotExVat = String.format("%.2f",orderTotExVat);
		strorderTotExVat = Utilities.stringToDec(strorderTotExVat);
		order.setTotalExVat(strorderTotExVat);
		
		// Calculate the vat and add it to the order
		totalVat = Utilities.floatToNumDec(orderTotExVat/100*vatRate,2);
		totalPreRounding = Utilities.floatToNumDec(orderTotExVat + totalVat,2);
		order.setTotalVat(Utilities.stringToDec(String.valueOf(totalVat)));
		order.setTotalPreRounding(Utilities.stringToDec(String.valueOf(totalPreRounding)));
		
		// Set the Post Rounding price to the same as the Pre Rounding price as there is no rounding with new order lines and clear the order's rounding value
		order.setTotalPostRounding(Utilities.stringToDec(order.getTotalPreRounding()));
		order.setRounding("0.00");
		
		// Add the Line Cost Value to the order's total cost
		order.setTotalCost(order.getTotalCost() + getLineCostValue());
		
		
		sellingPrice = Float.valueOf(order.getTotalExVat());
		costPrice = order.getTotalCost();
		grossProfit = Utilities.floatToNumDec(sellingPrice - costPrice,2);
		grossProfitPercent = Utilities.floatToNumDec(grossProfit / costPrice * 100,2);
		lblTradeGrossProfitValue.setText("\u20AC " + Utilities.stringToDec(String.valueOf(grossProfit)));
		lblTradeGrossProfitPercent.setText(Utilities.stringToDec(String.valueOf(grossProfitPercent)) + " %");
		
		if(afterAdd) {
			// Add the Order Line total to the Order's balance
			balance = order.getBalance();
			lineIncVat = valExVat * (1 + (vatRate/100));
			order.setBalance(balance + lineIncVat);
			
			// Call the suspended order's method that saves the current order and it's lines to the tables
			order.updateOrderAndLines();
		}
	}

}