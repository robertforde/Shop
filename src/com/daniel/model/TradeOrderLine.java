package com.daniel.model;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.daniel.utilities.Utilities;

/**
 * This class represents trade order line objects i.e the detail lines on a trade order. It extends the OrderLine class 
 * @author Robert Forde
 *
 */
public class TradeOrderLine extends OrderLine{

	/**
	 * Method to add a trade order line to an order. It calculates order header values and adds the order line to the order
	 * @param txtTradeLineCode The JTextfield holding the Item Code
	 * @param txtTradeLineDesc The JTextfield holding the Item Description
	 * @param txtTradeLineQty The JTextfield holding the Item Quantity
	 * @param txtTradeLinePrice The JTextfield holding the Item Trade Price
	 * @param lblTradeGrossProfitValue The Label holding the Gross Profit Value
	 * @param lblTradeGrossProfitPercent The Label holding the Gross Profit Percent
	 * @param tradeOrder The trade order that this line has to be added to
	 * @param txtTradeLineCost The cost value of one of this item
	 * @param invoiceSettings The invoiceSettings object that determines settings in relation to receipt printing
	 */
	public void addTradeOrderLine(JTextField txtTradeLineCode, JTextField txtTradeLineDesc, JTextField txtTradeLineQty, JTextField txtTradeLinePrice, 
									JLabel lblTradeGrossProfitValue, JLabel lblTradeGrossProfitPercent, TradeOrder tradeOrder, float txtTradeLineCost, 
									InvoiceSettings invoiceSettings){

		float valExDisc;
		String strvalExDisc;
		float valExVat;
		String strValExVat;
		float orderTotExVat;
		String strorderTotExVat;
		float vatRate;
		float totalVat;
		float totalPreRounding;
		float sellingPrice;
		float costPrice;
		float grossProfit;
		float grossProfitPercent;

		
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
		
		tradeOrder.addOrderLine(this);
				
		// Add the Order Line total to the Order's total ex-vat value and round to 2 Decimals
		orderTotExVat = Utilities.floatToNumDec(Float.parseFloat(tradeOrder.getTotalExVat()) + valExVat,2);
		strorderTotExVat = String.format("%.2f",orderTotExVat);
		strorderTotExVat = Utilities.stringToDec(strorderTotExVat);
		tradeOrder.setTotalExVat(strorderTotExVat);
		
		// Calculate the vat and add it to the order
		vatRate = invoiceSettings.getReceiptVatRate();
		totalVat = Utilities.floatToNumDec(orderTotExVat/100*vatRate,2);
		totalPreRounding = Utilities.floatToNumDec(orderTotExVat + totalVat,2);
		tradeOrder.setTotalVat(Utilities.stringToDec(String.valueOf(totalVat)));
		tradeOrder.setTotalPreRounding(Utilities.stringToDec(String.valueOf(totalPreRounding)));
		
		// Set the Post Rounding price to the same as the Pre Rounding price as there is no rounding with new order lines and clear the order's rounding value
		tradeOrder.setTotalPostRounding(Utilities.stringToDec(tradeOrder.getTotalPreRounding()));
		tradeOrder.setRounding("0.00");
				
		// Add the Line Cost Value to the order's total cost
		tradeOrder.setTotalCost(tradeOrder.getTotalCost() + getLineCostValue());

		
		sellingPrice = Float.valueOf(tradeOrder.getTotalExVat());
		costPrice = tradeOrder.getTotalCost();
		grossProfit = Utilities.floatToNumDec(sellingPrice - costPrice,2);
		grossProfitPercent = Utilities.floatToNumDec(grossProfit / costPrice * 100,2);
		lblTradeGrossProfitValue.setText("\u20AC " + Utilities.stringToDec(String.valueOf(grossProfit)));
		lblTradeGrossProfitPercent.setText(Utilities.stringToDec(String.valueOf(grossProfitPercent)) + " %");
	}
	
}