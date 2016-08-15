package com.daniel.model;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.daniel.utilities.Utilities;

/**
 * This class represents retail order line objects i.e the detail lines on a retail order. It extends the OrderLine class 
 * @author Robert Forde
 *
 */
public class RetailOrderLine extends OrderLine{

	private String discountPercent;
	private String discountValue;
	
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

	
	/**
	 * Method to add a retail order line to an order. It calculates order header values and adds the order line to the order and re-calculates gross profit values
	 * @param txtCustLineCode The JTextfield holding the Item Code
	 * @param txtCustLineDesc The JTextfield holding the Item Description
	 * @param txtCustLineQty The JTextfield holding the Item Quantity
	 * @param txtCustLinePrice The JTextfield holding the Item Trade Price
	 * @param txtCustLineDisc
	 * @param txtCustDiscount
	 * @param lblGrossProfitValue The Label holding the Gross Profit Value 
	 * @param lblGrossProfitPercent The Label holding the Gross Profit Percent
	 * @param retailOrder The retail order that this line has to be added to
	 * @param CustTradeLinePrice The trade price of the item on this line
	 * @param orderLineCost The cost value of one of this item 
	 * @param vatRate The vat Rate Value
	 */
	public void addRetailOrderLine(JTextField txtCustLineCode, JTextField txtCustLineDesc, JTextField txtCustLineQty, JTextField txtCustLinePrice, 
									JTextField txtCustLineDisc, JTextField txtCustDiscount, JLabel lblGrossProfitValue, JLabel lblGrossProfitPercent, 
									RetailOrder retailOrder, float CustTradeLinePrice, float orderLineCost, float vatRate){

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
		float totalPreRounding;
		float sellingPrice;
		float costPrice;
		float grossProfit;
		float grossProfitPercent;

		
		setItemCode(txtCustLineCode.getText());
		setItemDescription(txtCustLineDesc.getText());
		setOrderQty(txtCustLineQty.getText());
		
		// Convert Item Price to 2 decimal string
		String strItemPrice = Utilities.stringToDec(txtCustLinePrice.getText());
		setItemPrice(strItemPrice);
		
		// Save the Item's Trade Price as a float
		setItemTradePrice(CustTradeLinePrice);
		
		// Calculate and Save the Line Cost Value
		setLineCostValue(Integer.valueOf(getOrderQty()) * orderLineCost);
		
		// Get Order Line Price exclusive of discount and covert it to a 2 decimal string
		valExDisc = Float.parseFloat(getItemPrice()) * Integer.parseInt(getOrderQty());
		strvalExDisc = String.format("%.2f", valExDisc);
		strvalExDisc = Utilities.stringToDec(strvalExDisc);
		setValueExDiscount(strvalExDisc);
		
		if(txtCustDiscount.getText().equals("")){
			// Get Order Line Discount Percentage and covert it to a 2 decimal string
			if(!txtCustLineDisc.getText().trim().equals("")) {
				discPercent = Float.parseFloat(txtCustLineDisc.getText());
				strDiscPercent = String.format("%.2f", discPercent);
				strDiscPercent = Utilities.stringToDec(strDiscPercent);
			}else{
				discPercent = 0.0f;
				strDiscPercent = "0.00";
			}
			
		}else{
			discPercent = Float.parseFloat(txtCustDiscount.getText());
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
		
		retailOrder.addOrderLine(this);
				
		// Add the Order Line total to the Order's total ex-vat value and round to 2 Decimals
		orderTotExVat = Utilities.floatToNumDec(Float.parseFloat(retailOrder.getTotalExVat()) + valExVat,2);
		strorderTotExVat = String.format("%.2f",orderTotExVat);
		strorderTotExVat = Utilities.stringToDec(strorderTotExVat);
		retailOrder.setTotalExVat(strorderTotExVat);
		
		// Calculate the vat and add it to the order
		totalVat = Utilities.floatToNumDec(orderTotExVat/100*vatRate,2);
		totalPreRounding = Utilities.floatToNumDec(orderTotExVat + totalVat,2);
		retailOrder.setTotalVat(Utilities.stringToDec(String.valueOf(totalVat)));
		retailOrder.setTotalPreRounding(Utilities.stringToDec(String.valueOf(totalPreRounding)));
		
		// Set the Post Rounding price to the same as the Pre Rounding price as there is no rounding with new order lines and clear the order's rounding value
		retailOrder.setTotalPostRounding(Utilities.stringToDec(retailOrder.getTotalPreRounding()));
		retailOrder.setRounding("0.00");
				
		// Add the Line Cost Value to the order's total cost
		retailOrder.setTotalCost(retailOrder.getTotalCost() + getLineCostValue());

		
		sellingPrice = Float.valueOf(retailOrder.getTotalExVat());
		costPrice = retailOrder.getTotalCost();
		grossProfit = Utilities.floatToNumDec(sellingPrice - costPrice,2);
		grossProfitPercent = Utilities.floatToNumDec(grossProfit / costPrice * 100,2);
		lblGrossProfitValue.setText("\u20AC " + Utilities.stringToDec(String.valueOf(grossProfit)));
		lblGrossProfitPercent.setText(Utilities.stringToDec(String.valueOf(grossProfitPercent)) + " %");
		
	}
	
}