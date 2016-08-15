package com.daniel;

import java.awt.Color;

import javax.swing.JFrame;

import com.daniel.dao.AccountDao;
import com.daniel.dao.AccountDaoImpl;
import com.daniel.model.Account;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.QuoteHeader;
import com.daniel.model.TradeOrder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * This class opens a JFrame containing the current quotes for an account.
 * It allows the user to select any of these quotes and convert it to an order.
 * @author Robert Forde
 *
 */
public class AccountConvertListScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InvoiceSettings invoiceSettings;
	private AccountDetailScreen accountDetailScreen;
	private AccountListScreen accountListScreen;
	private Account acc;
	
	private JTable tblAccountConvertQuote;
	
	private AccountDao accountDao;
	
	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}
	
	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}
	
	public Account getAcc() {
		return acc;
	}
	
	public void setAcc(Account acc) {
		this.acc = acc;
	}
	
	public AccountDetailScreen getAccountDetailScreen() {
		return accountDetailScreen;
	}

	public void setAccountDetailScreen(AccountDetailScreen accountDetailScreen) {
		this.accountDetailScreen = accountDetailScreen;
	}

	public AccountListScreen getAccountListScreen() {
		return accountListScreen;
	}

	public void setAccountListScreen(AccountListScreen accountListScreen) {
		this.accountListScreen = accountListScreen;
	}


	/**
	 * This constructs the AccountConvertListScreen screen 
	 * @param invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 * @param acc The Account whose details this class/screen will be using
	 * @param accountDetailScreen A reference to the accountDetailScreen so as to update the details on that screen
	 * @param accountListScreen A reference to the accountListScreen so as to update the account's balance on that screen
	 */
	public AccountConvertListScreen(InvoiceSettings invoiceSettings, Account acc, AccountDetailScreen accountDetailScreen, AccountListScreen accountListScreen){

		setAccountDao(Dan.ctx.getBean("accountDaoImpl", AccountDaoImpl.class));
		
		setInvoiceSettings(invoiceSettings);
		setAcc(acc);
		setAccountDetailScreen(accountDetailScreen);
		setAccountListScreen(accountListScreen);
		
		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(null);
		
		JPanel panelAccountConvertList = new JPanel();
		panelAccountConvertList.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountConvertList.setBounds(37, 11, 407, 229);
		panelAccountConvertList.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(panelAccountConvertList);
		panelAccountConvertList.setLayout(null);
		
		JScrollPane scrlAccountConvertQuote = new JScrollPane();
		scrlAccountConvertQuote.setBounds(51, 11, 305, 207);
		panelAccountConvertList.add(scrlAccountConvertQuote);
		
		tblAccountConvertQuote = new JTable(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
			
			public TableCellRenderer getCellRenderer( int row, int col ) { 
				
				TableCellRenderer renderer = super.getCellRenderer(row,col);
				
				// Right justify the last column (Price)
				if ( col == dataModel.getColumnCount() - 1 ) 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
				
				// Left justify the other columns
				else 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
				
				return renderer; 
			}
			
		};
		tblAccountConvertQuote.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblAccountConvertQuote.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		// This formats the Headers
		tblAccountConvertQuote.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		scrlAccountConvertQuote.setViewportView(tblAccountConvertQuote);
		scrlAccountConvertQuote.setViewportView(tblAccountConvertQuote);
		
		JButton btnAccountConvert = new JButton("CONVERT");
		btnAccountConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				TradeOrder tradeOrder = null;
				int row;
				int selectedQuoteNo;

				// We use the table's getSelectedRow() method to find the row that the User selected. 
				row = tblAccountConvertQuote.getSelectedRow();
				
				if(row != -1){
					// Then we can get the id of the selected quote by getting the model of the table and then using the getValueAt() method we can get the specific
					// row and column that we require. This returns an Object so we get the toString and then convert it to an Integer.
					selectedQuoteNo = Integer.parseInt(tblAccountConvertQuote.getModel().getValueAt(row, 1).toString());
	
					try {

						// Call a method that retrieves the selectedQuoteNo and saves it as a Trade Order with it's lines and returns it
						tradeOrder = getAccountDao().loadTradeQuote(selectedQuoteNo);
					
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex);
					}

					// Dispose of this screen and open the account order screen with this quote
					AccountConvertListScreen.this.dispose();
					AccountOrderScreen accOrderScreen = new AccountOrderScreen(getInvoiceSettings(), getAcc(), getAccountDetailScreen(), getAccountListScreen(), tradeOrder);
					
					// Make all of the appropriate panels visible on the AccountOrderScreen
					accOrderScreen.getPanelAccountCurrentOrder().setVisible(true);
					accOrderScreen.getPanelAccountGrossProfit().setVisible(true);
					accOrderScreen.getPanelAccountPaymentMethod().setVisible(true);
					accOrderScreen.getPanelAccountPrintOrder().setVisible(true);
					accOrderScreen.getPanelAccountRounding().setVisible(true);
						
					// Update the gross profit labels and the total labels on the AccountOrderScreen 
					accOrderScreen.getLblAccountTotalExVat().setText(tradeOrder.getTotalExVat());
					accOrderScreen.getLblAccountTotalPrice().setText(tradeOrder.getTotalPostRounding());
					tradeOrder.calcGrossProfit(accOrderScreen.getLblAccountGrossProfitValue(), accOrderScreen.getLblAccountGrossProfitPercent());
						
					// Update the rep code on the AccountOrderScreen
					accOrderScreen.getTxtAccountLineRep().setText((tradeOrder.getRepNo()));
					accOrderScreen.getTxtAccountLineRep().setEnabled(false);
					accOrderScreen.getTxtAccountLineRep().setEditable(false);
				
				} else {
					JOptionPane.showMessageDialog(null, "You must first select a Quote from the List !");
				}
			}	
		});
		btnAccountConvert.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAccountConvert.setBounds(180, 266, 125, 38);
		btnAccountConvert.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(btnAccountConvert);

		setInvoiceSettings(invoiceSettings);
		setAcc(acc);

		setBounds(410, 250, 500, 350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setTitle(getAcc().getName() + " QUOTES");
		
		refreshAccountConvertQuoteTable();
		
	}


	/**
	 * Method to retrieve the current Quotes, for this account, from the database and refresh the list in the table on screen
	 */
	public void refreshAccountConvertQuoteTable(){
		
		try {
			
			List<QuoteHeader> quoteHeaders = getAccountDao().accountRefreshConvertQuoteTable(getAcc().getId());

			String columns[] = {"DATE", "NUMBER", "AMOUNT"};
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						
			for(QuoteHeader quoteHeader : quoteHeaders){
				String tableDate= df.format(quoteHeader.getOrderDate());
				String tableNumber = String.valueOf(quoteHeader.getQuotationNo());
				String tableAmount = quoteHeader.getTotalPostRounding();
				Object[] line = {tableDate, tableNumber, tableAmount};
				tableModel.addRow(line);
			}
						
			tblAccountConvertQuote.setModel(tableModel);
			
			// Set the column widths
			tblAccountConvertQuote.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel columnModel = tblAccountConvertQuote.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(100);
			columnModel.getColumn(1).setPreferredWidth(90);
			columnModel.getColumn(2).setPreferredWidth(95);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

}
