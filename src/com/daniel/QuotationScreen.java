package com.daniel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import com.daniel.model.InvoiceSettings;


/**
 * This class presents the user with 3 options, via buttons. Trade Quotes, Retail Quotes or Convert Quotes. On selection is opens 
 * the appropriate screen.
 * @author Robert Forde
 *
 */
public class QuotationScreen extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InvoiceSettings invoiceSettings;
	private RetailOrderScreen retailOrderScreen;
	private TradeOrderScreen tradeOrderScreen;


	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}

	public RetailOrderScreen getRetailOrderScreen() {
		return retailOrderScreen;
	}

	public void setRetailOrderScreen(RetailOrderScreen retailOrderScreen) {
		this.retailOrderScreen = retailOrderScreen;
	}
	
	public TradeOrderScreen getTradeOrderScreen() {
		return tradeOrderScreen;
	}

	public void setTradeOrderScreen(TradeOrderScreen tradeOrderScreen) {
		this.tradeOrderScreen = tradeOrderScreen;
	}

	
	/**
	 * 
	 * @param tabbedPane The main program tabbed pane so this panel can be added to it
	 * @param invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 * @param retailOrderScreen A reference to the retail order screen so that a converted retail quote can be placed on the retail order screen. 
	 * @param tradeOrderScreenA reference to the trade order screen so that a converted trade quote can be placed on the trade order screen.
	 */
	public QuotationScreen(final JTabbedPane tabbedPane, InvoiceSettings invoiceSettings, RetailOrderScreen retailOrderScreen, TradeOrderScreen tradeOrderScreen){
		
		setInvoiceSettings(invoiceSettings);
		setRetailOrderScreen(retailOrderScreen);
		setTradeOrderScreen(tradeOrderScreen);
		
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Quotes", null, this, null);
		setLayout(null);
		
		JPanel panelQuotationOptions = new JPanel();
		panelQuotationOptions.setBackground(UIManager.getColor("List.dropLineColor"));
		panelQuotationOptions.setBounds(112, 93, 1068, 197);
		panelQuotationOptions.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(panelQuotationOptions);
		panelQuotationOptions.setLayout(null);
		
		JButton btnRetailQuotation = new JButton("RETAIL");
		btnRetailQuotation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RetailQuotation(getInvoiceSettings());
			}
		});
		btnRetailQuotation.setBounds(93, 66, 221, 70);
		btnRetailQuotation.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelQuotationOptions.add(btnRetailQuotation);
		btnRetailQuotation.setFont(new Font("Georgia", Font.BOLD, 24));
		
		JButton btnTradeQuotation = new JButton("TRADE");
		btnTradeQuotation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TradeQuotation(getInvoiceSettings());
			}
		});
		btnTradeQuotation.setBounds(418, 66, 221, 70);
		btnTradeQuotation.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelQuotationOptions.add(btnTradeQuotation);
		btnTradeQuotation.setFont(new Font("Georgia", Font.BOLD, 24));
		
		JButton btnConvertQuotation = new JButton("CONVERT");
		btnConvertQuotation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConvertQuotation(getInvoiceSettings(), getRetailOrderScreen(), getTradeOrderScreen(), tabbedPane); 
			}
		});
		btnConvertQuotation.setBounds(747, 66, 221, 70);
		btnConvertQuotation.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelQuotationOptions.add(btnConvertQuotation);
		btnConvertQuotation.setFont(new Font("Georgia", Font.BOLD, 24));

	}
}