package com.daniel;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daniel.dao.ReceiptSettingsDao;
import com.daniel.dao.ReceiptSettingsDaoImpl;
import com.daniel.model.InvoiceSettings;

/**
 * The main application class that initiates everything
 * @author Robert Forde
 *
 */
public class Dan{

	private JFrame frame;
	private JTabbedPane tabbedPane;
	private RetailOrderScreen retailOrderScreen;
	private TradeOrderScreen tradeOrderScreen;
	
	private InvoiceSettings invoiceSettings;
	
	public static AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
	
	private ReceiptSettingsDao receiptSettingsDao;


	public ReceiptSettingsDao getReceiptSettingsDao() {
		return receiptSettingsDao;
	}

	public void setReceiptSettingsDao(ReceiptSettingsDao receiptSettingsDao) {
		this.receiptSettingsDao = receiptSettingsDao;
	}

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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dan window = new Dan();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Dan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		ctx.registerShutdownHook();
		
		setReceiptSettingsDao(Dan.ctx.getBean("receiptSettingsDaoImpl", ReceiptSettingsDaoImpl.class));
		
		// Load the invoicesettings
		try {
			setInvoiceSettings(getReceiptSettingsDao().retrieveDatabasePrintSettings(invoiceSettings));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		
		frame = new JFrame();
		frame.setBounds(30, 50, 1310, 660);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("D & S Plumbing & Fixings - Version 2.31");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1294, 624);
		frame.getContentPane().add(tabbedPane);
		
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				// Close the connection to the database
				frame.dispose();
			}
		});
								
		// Load the screens for the various tabs and activate their listeners
		setRetailOrderScreen(new RetailOrderScreen(tabbedPane, invoiceSettings));

		setTradeOrderScreen(new TradeOrderScreen(tabbedPane, invoiceSettings));
		
		new AccountListScreen(tabbedPane, invoiceSettings);
		
		new QuotationScreen(tabbedPane, invoiceSettings, getRetailOrderScreen(), getTradeOrderScreen());
		
		new SuspendedOrdersScreen(tabbedPane);
		
		new CustomersScreen(tabbedPane);
		
		new ProductsScreen(tabbedPane);
		
		new TradesmenScreen(tabbedPane);
		
		new StockScreen(tabbedPane);
		
		new RefundScreen(tabbedPane, invoiceSettings);
		
		new SettingsScreen(tabbedPane, invoiceSettings);
	}

}