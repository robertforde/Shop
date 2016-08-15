package com.daniel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.daniel.dao.AccountDao;
import com.daniel.dao.AccountDaoImpl;
import com.daniel.dao.OrderDao;
import com.daniel.dao.OrderDaoImpl;
import com.daniel.dao.ProductDaoImpl;
import com.daniel.dao.StaffDaoImpl;
import com.daniel.model.Account;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.Item;
import com.daniel.model.Staff;
import com.daniel.model.TradeOrder;
import com.daniel.model.TradeOrderLine;
import com.daniel.utilities.Utilities;

/**
 * This class opens a JFrame to allow the user to enter a trade order for a particular account  
 * @author Robert Forde
 *
 */
public class AccountOrderScreen extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private InvoiceSettings invoiceSettings;
	private TradeOrder tradeOrder;
	private TradeOrderLine tradeOrderLine;
	private Account acc;
	private AccountDetailScreen accountDetailScreen;
	private AccountListScreen accountListScreen; 
	
	private JPanel panelAccountCurrentOrder;
	private JPanel panelAccountPaymentMethod;
	private JPanel panelAccountRounding; 
	private JPanel panelAccountGrossProfit; 
	private JPanel panelAccountPrintOrder; 
	private JPanel panelAccountDetails;
	private JPanel panelAccountItemDescriptionChoice;
	private JPanel panelAccountBillAccount;
	
	private JTable tblAccountItemDescription;
	private JTable tblAccountOrder;
	
	private JButton btnAccountNewOrderLine;
	
	private JRadioButton rdbtnAccountCash;
	private JRadioButton rdbtnAccountCreditCard;
	private JRadioButton rdbtnAccountCheque;
	private JRadioButton rdbtnAccountBankTransfer;
	
	private JLabel lblAccountGrossProfitValue;
	private JLabel lblAccountGrossProfitPercent;
	private JLabel lblAccountTotalPrice;
	private JLabel lblAccountTotalExVat;
	
	private JTextField txtAccountLineRep;
	private JTextField txtAccountLineCode;
	private JTextField txtAccountLineDesc;
	private JTextField txtAccountLinePrice;
	private JTextField txtAccountLineQty;

	private TableColumnModel columnModelOrder;
	
	private float txtTradeLineCost;
	
	private ProductDaoImpl productDao;
	private StaffDaoImpl staffDao;

	private AccountDao accountDao;
	private OrderDao orderDao;
	
	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
 
	public ProductDaoImpl getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDaoImpl productDao) {
		this.productDao = productDao;
	}

	public StaffDaoImpl getStaffDao() {
		return staffDao;
	}

	public void setStaffDao(StaffDaoImpl staffDao) {
		this.staffDao = staffDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}

	public TradeOrder getTradeOrder() {
		return tradeOrder;
	}

	public void setTradeOrder(TradeOrder tradeOrder) {
		this.tradeOrder = tradeOrder;
	}

	public TradeOrderLine getTradeOrderLine() {
		return tradeOrderLine;
	}

	public void setTradeOrderLine(TradeOrderLine tradeOrderLine) {
		this.tradeOrderLine = tradeOrderLine;
	}
	
	public JPanel getPanelAccountCurrentOrder() {
		return panelAccountCurrentOrder;
	}

	public void setPanelAccountCurrentOrder(JPanel panelAccountCurrentOrder) {
		this.panelAccountCurrentOrder = panelAccountCurrentOrder;
	}

	public JPanel getPanelAccountPaymentMethod() {
		return panelAccountPaymentMethod;
	}

	public void setPanelAccountPaymentMethod(JPanel panelAccountPaymentMethod) {
		this.panelAccountPaymentMethod = panelAccountPaymentMethod;
	}

	public JPanel getPanelAccountGrossProfit() {
		return panelAccountGrossProfit;
	}

	public void setPanelAccountGrossProfit(JPanel panelAccountGrossProfit) {
		this.panelAccountGrossProfit = panelAccountGrossProfit;
	}

	public JPanel getPanelAccountPrintOrder() {
		return panelAccountPrintOrder;
	}

	public JPanel getPanelAccountRounding() {
		return panelAccountRounding;
	}

	public JLabel getLblAccountTotalPrice() {
		return lblAccountTotalPrice;
	}

	public void setLblAccountTotalPrice(JLabel lblAccountTotalPrice) {
		this.lblAccountTotalPrice = lblAccountTotalPrice;
	}

	public JLabel getLblAccountTotalExVat() {
		return lblAccountTotalExVat;
	}

	public void setLblAccountTotalExVat(JLabel lblAccountTotalExVat) {
		this.lblAccountTotalExVat = lblAccountTotalExVat;
	}

	public void setPanelAccountRounding(JPanel panelAccountRounding) {
		this.panelAccountRounding = panelAccountRounding;
	}

	public JPanel getPanelAccountDetails() {
		return panelAccountDetails;
	}

	public void setPanelAccountDetails(JPanel panelAccountDetails) {
		this.panelAccountDetails = panelAccountDetails;
	}

	public void setPanelAccountPrintOrder(JPanel panelAccountPrintOrder) {
		this.panelAccountPrintOrder = panelAccountPrintOrder;
	}
	
	public JLabel getLblAccountGrossProfitValue() {
		return lblAccountGrossProfitValue;
	}

	public void setLblAccountGrossProfitValue(JLabel lblAccountGrossProfitValue) {
		this.lblAccountGrossProfitValue = lblAccountGrossProfitValue;
	}

	public JLabel getLblAccountGrossProfitPercent() {
		return lblAccountGrossProfitPercent;
	}

	public void setLblAccountGrossProfitPercent(JLabel lblAccountGrossProfitPercent) {
		this.lblAccountGrossProfitPercent = lblAccountGrossProfitPercent;
	}
	
	public JTextField getTxtAccountLineRep() {
		return txtAccountLineRep;
	}

	public Account getAcc() {
		return acc;
	}

	public void setAcc(Account acc) {
		this.acc = acc;
	}

	public void setTxtAccountLineRep(JTextField txtAccountLineRep) {
		this.txtAccountLineRep = txtAccountLineRep;
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

	public JPanel getPanelAccountBillAccount() {
		return panelAccountBillAccount;
	}

	public void setPanelAccountBillAccount(JPanel panelAccountBillAccount) {
		this.panelAccountBillAccount = panelAccountBillAccount;
	}

	
	/**
	 * 
	 * @param invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 * @param acc The account that this order is for
	 * @param accountDetailScreen A reference to the accountDetailScreen so as to update the details on that screen
	 * @param accountListScreen A reference to the accountListScreen so as to update the account's balance on that screen
	 * @param tradeQuote If this is a quote that is being converted to an order then it is passed in here, else it is null  
	 */
	public AccountOrderScreen(InvoiceSettings invoiceSettings, Account acc, AccountDetailScreen accountDetailScreen, AccountListScreen accountListScreen, TradeOrder tradeQuote){
		
		setProductDao(Dan.ctx.getBean("productDaoImpl", ProductDaoImpl.class));
		setStaffDao(Dan.ctx.getBean("staffDaoImpl", StaffDaoImpl.class));
		setAccountDao(Dan.ctx.getBean("accountDaoImpl", AccountDaoImpl.class));
		setOrderDao(Dan.ctx.getBean("orderDaoImpl", OrderDaoImpl.class));
		
		setInvoiceSettings(invoiceSettings);
		setAcc(acc);
		setAccountDetailScreen(accountDetailScreen);
		setAccountListScreen(accountListScreen);
		
		// Create the logo images for the application
		Image logo = new ImageIcon(this.getClass().getResource("/Logo199X198.jpg")).getImage();
		ImageIcon imageIcon = new ImageIcon(logo);
		
		// Create the up arrow ImageIcon
		Image upArrow = new ImageIcon(this.getClass().getResource("/Up.jpg")).getImage();
		ImageIcon imageIconUpArrow = new ImageIcon(upArrow);

		// Create the up arrow ImageIcon
		Image downArrow = new ImageIcon(this.getClass().getResource("/Down.jpg")).getImage();
		ImageIcon imageIconDownArrow = new ImageIcon(downArrow);
		
		setInvoiceSettings(invoiceSettings);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(35, 78, 1294, 624);
		setTitle("ACCOUNT ORDER");
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.DARK_GRAY);
		setVisible(true);
		
		JPanel panelAccountOrderLine = new JPanel();
		panelAccountOrderLine.setForeground(Color.BLACK);
		panelAccountOrderLine.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountOrderLine.setBorder(BorderFactory.createRaisedBevelBorder());
		panelAccountOrderLine.setBounds(28, 21, 1217, 71);
		panelAccountOrderLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelAccountOrderLine);
		panelAccountOrderLine.setLayout(null);
		
		JLabel lblAccountRepCodeText = new JLabel("Rep Code");
		lblAccountRepCodeText.setForeground(Color.BLACK);
		lblAccountRepCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountRepCodeText.setBounds(34, 9, 96, 18);
		panelAccountOrderLine.add(lblAccountRepCodeText);
		
		txtAccountLineRep = new JTextField();
		txtAccountLineRep.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountLineRep.setBounds(34, 31, 86, 28);
		txtAccountLineRep.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountOrderLine.add(txtAccountLineRep);
		txtAccountLineRep.setColumns(10);
		
		JLabel lblAccountItemCodeText = new JLabel("Item Code");
		lblAccountItemCodeText.setForeground(Color.BLACK);
		lblAccountItemCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountItemCodeText.setBounds(152, 9, 132, 18);
		panelAccountOrderLine.add(lblAccountItemCodeText);
		
		txtAccountLineCode = new JTextField();
		txtAccountLineCode.setEditable(false);
		txtAccountLineCode.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountLineCode.setBounds(152, 31, 132, 28);
		txtAccountLineCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountOrderLine.add(txtAccountLineCode);
		txtAccountLineCode.setColumns(10);
		
		JLabel lblAccountItemDescText = new JLabel("Item Description");
		lblAccountItemDescText.setForeground(Color.BLACK);
		lblAccountItemDescText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountItemDescText.setBounds(318, 9, 175, 18);
		panelAccountOrderLine.add(lblAccountItemDescText);
		
		txtAccountLineDesc = new JTextField();
		txtAccountLineDesc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				
				// Disable the normal tab key focus on the Item Description textbox
				txtAccountLineDesc.setFocusTraversalKeysEnabled(false);
				
				// Uppercase the entered description
				txtAccountLineDesc.setText(txtAccountLineDesc.getText().toUpperCase());
				
				// Make ENTER button and the list of descriptions invisible
				panelAccountItemDescriptionChoice.setVisible(false);
				btnAccountNewOrderLine.setVisible(false);
				
				if (txtAccountLineDesc.getText().equals("") == false) {  
					// Search in database for items that have this exact description in database and if found enable qty textfield and fill in code and trade price
					try {
						
						// Find item based on it's description
						Item foundItem = getProductDao().findItemByDesc(txtAccountLineDesc.getText());

						if(foundItem.getItemCode() != null){
							txtAccountLineCode.setText(foundItem.getItemCode());
							txtAccountLinePrice.setText(Utilities.stringToDec(String.valueOf(foundItem.getTradePrice())));
							txtTradeLineCost = foundItem.getCostPrice();
							txtAccountLineQty.setEnabled(true);
						} else {
							txtAccountLineCode.setText("");
							txtAccountLinePrice.setText("");
							txtAccountLineQty.setText("");
							txtAccountLineQty.setEnabled(false);
							
							// Exact match not found so bring up list of descriptions that contain this description and are not set as deleted
							List<Item> itemList = getProductDao().findPossibleItemMatches(txtAccountLineDesc.getText().toUpperCase());
							
							if(itemList.size() != 0) {
								
								String columns[] = {"DESCRIPTION"};
								DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
								
								for(Item item : itemList){
									String tableDesc = item.getItemDescription();
									Object[] line = {tableDesc};
									tableModel.addRow(line);
								}
								
								tblAccountItemDescription.setModel(tableModel);									
								tblAccountItemDescription.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
								TableColumnModel columnModel = tblAccountItemDescription.getColumnModel();
								columnModel.getColumn(0).setPreferredWidth(340);
								panelAccountItemDescriptionChoice.setVisible(true);
								
								// Set the tab key to the item description possible matches table
								if(arg0.getKeyCode() == KeyEvent.VK_TAB){
									tblAccountItemDescription.requestFocus();
								} 
									
							} else {
								txtAccountLineCode.setText("");
								txtAccountLinePrice.setText("");
								txtAccountLineQty.setText("");
								txtAccountLineQty.setEnabled(false);
							}
								
						}
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}else {
					// Make ENTER button and the list of descriptions invisible
					panelAccountItemDescriptionChoice.setVisible(false);
					btnAccountNewOrderLine.setVisible(false);
				}
			}
		});
		txtAccountLineDesc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountLineDesc.setBounds(318, 31, 341, 28);
		txtAccountLineDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountOrderLine.add(txtAccountLineDesc);
		txtAccountLineDesc.setColumns(10);
		
		JLabel lblAccountItemPriceText = new JLabel("Item Price");
		lblAccountItemPriceText.setForeground(Color.BLACK);
		lblAccountItemPriceText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountItemPriceText.setBounds(678, 9, 112, 18);
		panelAccountOrderLine.add(lblAccountItemPriceText);
		
		txtAccountLinePrice = new JTextField();
		txtAccountLinePrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				btnAccountNewOrderLine.setVisible(true);
			}
		});
		txtAccountLinePrice.setEditable(false);
		txtAccountLinePrice.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountLinePrice.setBounds(678, 31, 132, 28);
		txtAccountLinePrice.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountOrderLine.add(txtAccountLinePrice);
		txtAccountLinePrice.setColumns(10);
		
		txtAccountLineQty = new JTextField();
		txtAccountLineQty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnAccountNewOrderLine.setVisible(true);
			}
		});
		txtAccountLineQty.setEnabled(false);
		txtAccountLineQty.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountLineQty.setBounds(828, 31, 57, 28);
		txtAccountLineQty.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountOrderLine.add(txtAccountLineQty);
		txtAccountLineQty.setColumns(10);
		
		JLabel lblAccountItemQtyText = new JLabel("Quantity");
		lblAccountItemQtyText.setForeground(Color.BLACK);
		lblAccountItemQtyText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountItemQtyText.setBounds(828, 9, 75, 18);
		panelAccountOrderLine.add(lblAccountItemQtyText);
		
		btnAccountNewOrderLine = new JButton("ENTER");
		btnAccountNewOrderLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// First check that the Rep is valid
				try {
					
					Staff checkStaff = getStaffDao().staffExists(txtAccountLineRep.getText());
					
					// If this repCode is okay
					if(checkStaff.getRepCode() != null) {

						txtAccountLineRep.setEditable(false);
						txtAccountLineRep.setEnabled(false);
						
						if(tradeOrder == null){
							tradeOrder = new TradeOrder();
							tradeOrder.setOrderDate(new Date());
							tradeOrder.setSaleType("Account");
							tradeOrder.setCustId(getAcc().getId());
							tradeOrder.setName(getAcc().getName());
							tradeOrder.setAddress1(getAcc().getAddressLine1());
							tradeOrder.setAddress2(getAcc().getAddressLine2());
							tradeOrder.setPhone(getAcc().getPhone());
							tradeOrder.setRepNo(txtAccountLineRep.getText());
						}

						try {

							// Check if an item is entered
							if(!txtAccountLineDesc.getText().isEmpty()) {
								// Check that the Quantity entered is an Integer and > 0.
								if(Integer.parseInt(txtAccountLineQty.getText()) > 0) {
															
									// Create a TradeOrderLine and call the addTradeOrderLine() method to calculate it's values and save it to the arraylist  
									tradeOrderLine = new TradeOrderLine();

									tradeOrderLine.addTradeOrderLine(txtAccountLineCode, txtAccountLineDesc, txtAccountLineQty, txtAccountLinePrice, lblAccountGrossProfitValue, 
										lblAccountGrossProfitPercent, tradeOrder, txtTradeLineCost, getInvoiceSettings());
									
									// Clear the order textfield boxes and disable the quantity textfield and make the ENTER button invisible
									txtAccountLineCode.setText("");
									txtAccountLineDesc.setText("");
									txtAccountLinePrice.setText("");
									txtAccountLineQty.setText("");
									txtAccountLineQty.setEnabled(false);
									btnAccountNewOrderLine.setVisible(false);
										
									// Make the Account: Current Order, Print Order, PaymentMethod, GrossProfit, Rounding and AccountDetails panels visible
									panelAccountCurrentOrder.setVisible(true);
									panelAccountPrintOrder.setVisible(true);
									panelAccountPaymentMethod.setVisible(true);
									panelAccountGrossProfit.setVisible(true);
									panelAccountRounding.setVisible(true);
									
									refreshCurrentOrderTable();
									
									// Set the focus back to the item description
									txtAccountLineDesc.requestFocus();

								} else {
									JOptionPane.showMessageDialog(null, "You must enter a Quantity greater than 0 !");
								}
							}else {
								JOptionPane.showMessageDialog(null, "You must enter an Item !");	
							}
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, "You must enter a number for the Quantity !");
						}
					}else{
						JOptionPane.showMessageDialog(null, "That Rep Code doesn't exist, please try again !");
					}
					
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnAccountNewOrderLine.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAccountNewOrderLine.setBounds(964, 17, 167, 38);
		btnAccountNewOrderLine.setVisible(false);
		btnAccountNewOrderLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelAccountOrderLine.add(btnAccountNewOrderLine);
		
		panelAccountItemDescriptionChoice = new JPanel();
		panelAccountItemDescriptionChoice.setBounds(350, 91, 338, 398);
		getContentPane().add(panelAccountItemDescriptionChoice);
		panelAccountItemDescriptionChoice.setLayout(null);
		
		JScrollPane scrlAccountItemDescription = new JScrollPane();
		scrlAccountItemDescription.setBounds(0, 0, 338, 398);
		panelAccountItemDescriptionChoice.add(scrlAccountItemDescription);
		
		// When creating the JTable for the description dropdown I over-ride the isCellEditable method to make the table un-editable
		tblAccountItemDescription = new JTable(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
		};
		scrlAccountItemDescription.setViewportView(tblAccountItemDescription);
		
		tblAccountItemDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// If enter pressed call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make ENTER visible 
				if(arg0.getKeyCode() == 10) {
					ordLineAccountItemSelected();					
				}
			}
		});
		tblAccountItemDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
								
				// Call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make the enter button visible
				ordLineAccountItemSelected();
			}
		});
		panelAccountItemDescriptionChoice.setVisible(false);
		
		panelAccountCurrentOrder = new JPanel();
		panelAccountCurrentOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountCurrentOrder.setBorder(BorderFactory.createRaisedBevelBorder());
		panelAccountCurrentOrder.setBounds(28, 138, 800, 447);
		panelAccountCurrentOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(panelAccountCurrentOrder);
		panelAccountCurrentOrder.setLayout(null);
		panelAccountCurrentOrder.setVisible(false);
		
		JLabel lblAccountCurrentTransaction = new JLabel("ACCOUNT ORDER");
		lblAccountCurrentTransaction.setFont(new Font("Georgia", Font.BOLD, 18));
		lblAccountCurrentTransaction.setBounds(10, 11, 179, 31);
		panelAccountCurrentOrder.add(lblAccountCurrentTransaction);
		
		JLabel lblAccountTotalPriceText = new JLabel("TOTAL INC-VAT");
		lblAccountTotalPriceText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblAccountTotalPriceText.setBounds(634, 11, 156, 31);
		panelAccountCurrentOrder.add(lblAccountTotalPriceText);
		
		lblAccountTotalPrice = new JLabel("0.00", SwingConstants.RIGHT);
		lblAccountTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAccountTotalPrice.setBounds(634, 37, 156, 31);
		panelAccountCurrentOrder.add(lblAccountTotalPrice);
		
		JLabel lblAccountTotalExVatText = new JLabel("TOTAL EX-VAT");
		lblAccountTotalExVatText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblAccountTotalExVatText.setBounds(297, 11, 148, 31);
		panelAccountCurrentOrder.add(lblAccountTotalExVatText);
		
		lblAccountTotalExVat = new JLabel("0.00", SwingConstants.RIGHT);
		lblAccountTotalExVat.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAccountTotalExVat.setBounds(297, 37, 144, 31);
		panelAccountCurrentOrder.add(lblAccountTotalExVat);
		
		JScrollPane scrlAccountOrder = new JScrollPane();
		scrlAccountOrder.setBounds(10, 68, 780, 368);
		panelAccountCurrentOrder.add(scrlAccountOrder);
		
		tblAccountOrder = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public TableCellRenderer getCellRenderer( int row, int col ) { 
				TableCellRenderer renderer = super.getCellRenderer(row,col);
				if ( col == dataModel.getColumnCount() - 1  || col == dataModel.getColumnCount() - 2 || col == dataModel.getColumnCount() - 3) 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
				
				// Left justify the other columns
				else 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
				
				return renderer; 
			}
	
			// Override method so that table is not editable
			@Override
		    public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
		};
		tblAccountOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(tradeOrder.getOrderList().size() > 1) {
					// We use the table's getSelectedRow() method to find the row that the User selected. 
				
					int row = tblAccountOrder.getSelectedRow();
				
					// We use this row to find the element in the arraylist that the user selected
					tradeOrder.getOrderList().remove(row);
				
					// Recalculate everything for the Order
					tradeOrder.recalculateAfterDelete(getInvoiceSettings().getReceiptVatRate(), lblAccountGrossProfitValue, lblAccountGrossProfitPercent);
				
					// Load the current order lines onto the screen
					refreshCurrentOrderTable();
				
				}else{
					startNewOrder();
				}
				
			}
		});
		tblAccountOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblAccountOrder.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tblAccountOrder.setShowGrid(false);
		tblAccountOrder.setShowHorizontalLines(false);
		tblAccountOrder.setShowVerticalLines(false);
		tblAccountOrder.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));

		scrlAccountOrder.setViewportView(tblAccountOrder);
		
		panelAccountPaymentMethod = new JPanel();
		panelAccountPaymentMethod.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountPaymentMethod.setBounds(855, 138, 163, 118);
		panelAccountPaymentMethod.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelAccountPaymentMethod);
		panelAccountPaymentMethod.setLayout(null);
		panelAccountPaymentMethod.setVisible(false);
		
		rdbtnAccountCash = new JRadioButton("Cash");
		rdbtnAccountCash.setSelected(true);
		rdbtnAccountCash.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnAccountCash.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnAccountCash.setBounds(25, 7, 109, 23);
		panelAccountPaymentMethod.add(rdbtnAccountCash);
		
		rdbtnAccountCreditCard = new JRadioButton("Credit Card");
		rdbtnAccountCreditCard.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnAccountCreditCard.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnAccountCreditCard.setBounds(25, 33, 132, 23);
		panelAccountPaymentMethod.add(rdbtnAccountCreditCard);
		
		rdbtnAccountCheque = new JRadioButton("Cheque");
		rdbtnAccountCheque.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnAccountCheque.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnAccountCheque.setBounds(25, 59, 109, 23);
		panelAccountPaymentMethod.add(rdbtnAccountCheque);
		
		rdbtnAccountBankTransfer = new JRadioButton("Transfer");
		rdbtnAccountBankTransfer.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnAccountBankTransfer.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnAccountBankTransfer.setBounds(25, 85, 109, 23);
		panelAccountPaymentMethod.add(rdbtnAccountBankTransfer);
		
		// Group the radio buttons.
		ButtonGroup accountPayTypegroup = new ButtonGroup();
		accountPayTypegroup.add(rdbtnAccountCash);
		accountPayTypegroup.add(rdbtnAccountCreditCard);
		accountPayTypegroup.add(rdbtnAccountCheque);
		accountPayTypegroup.add(rdbtnAccountBankTransfer);
		
		panelAccountRounding = new JPanel();
		panelAccountRounding.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountRounding.setBounds(855, 310, 164, 81);
		panelAccountRounding.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelAccountRounding);
		panelAccountRounding.setLayout(null);
		panelAccountRounding.setVisible(false);
		
		JLabel lblAccountRoundText = new JLabel("Round");
		lblAccountRoundText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountRoundText.setBounds(50, 11, 55, 17);
		panelAccountRounding.add(lblAccountRoundText);
		
		JLabel lblAccountRoundUp = new JLabel("New label");
		lblAccountRoundUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String beforeRound = tradeOrder.getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(String.valueOf(Float.parseFloat(beforeRound.substring(0, theDot)) + 1));
				tradeOrder.setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				tradeOrder.setRounding(String.valueOf(rounding));

				// Save the rounding and post Rounding values if the invoice has been printed
				if(tradeOrder.getReceiptNo() != 0) {
					try {
						
						getOrderDao().updateRoundingHeader("orderheader", rounding, tradeOrder.getTotalPostRounding(), tradeOrder.getReceiptNo());
	
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblAccountTotalPrice.setText("\u20AC " + tradeOrder.getTotalPostRounding());
			}
		});
		lblAccountRoundUp.setBounds(36, 39, 35, 31);
		lblAccountRoundUp.setIcon(imageIconUpArrow);
		panelAccountRounding.add(lblAccountRoundUp);
		
		JLabel lblAccountRoundDown = new JLabel("New label");
		lblAccountRoundDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String beforeRound = tradeOrder.getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(beforeRound.substring(0, theDot));
				tradeOrder.setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				tradeOrder.setRounding(String.valueOf(rounding));

				// Save the rounding and post Rounding values if the invoice has been printed
				if(tradeOrder.getReceiptNo() != 0) {
					try {
						
						getOrderDao().updateRoundingHeader("orderheader", rounding, tradeOrder.getTotalPostRounding(), tradeOrder.getReceiptNo());
	
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblAccountTotalPrice.setText("\u20AC " + tradeOrder.getTotalPostRounding());
			}
		});
		lblAccountRoundDown.setBounds(92, 39, 35, 31);
		lblAccountRoundDown.setIcon(imageIconDownArrow);
		panelAccountRounding.add(lblAccountRoundDown);
		
		panelAccountGrossProfit = new JPanel();
		panelAccountGrossProfit.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountGrossProfit.setBounds(855, 443, 163, 142);
		panelAccountGrossProfit.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelAccountGrossProfit);
		panelAccountGrossProfit.setLayout(null);
		panelAccountGrossProfit.setVisible(false);
		
		JLabel lblAccountGrossProfitText = new JLabel("Gross Profit");
		lblAccountGrossProfitText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblAccountGrossProfitText.setBounds(27, 23, 111, 24);
		panelAccountGrossProfit.add(lblAccountGrossProfitText);
		
		lblAccountGrossProfitValue = new JLabel("\u20AC 0.00");
		lblAccountGrossProfitValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAccountGrossProfitValue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAccountGrossProfitValue.setBounds(25, 58, 102, 24);
		panelAccountGrossProfit.add(lblAccountGrossProfitValue);
		
		lblAccountGrossProfitPercent = new JLabel("0.00 %");
		lblAccountGrossProfitPercent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAccountGrossProfitPercent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAccountGrossProfitPercent.setBounds(25, 92, 102, 24);
		panelAccountGrossProfit.add(lblAccountGrossProfitPercent);
		
		panelAccountPrintOrder = new JPanel();
		panelAccountPrintOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountPrintOrder.setBounds(1046, 220, 199, 71);
		getContentPane().add(panelAccountPrintOrder);
		panelAccountPrintOrder.setLayout(null);
		panelAccountPrintOrder.setVisible(false);
		
		JButton btnAccountPrintOrder = new JButton("PRINT ORDER");
		btnAccountPrintOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tradeOrder.setOrderLinesToPrint(0);
				// Set the PayType as per the user's selection
				String paytype = "";
				if(rdbtnAccountCash.isSelected())
					paytype = "Cash";
				else if(rdbtnAccountCreditCard.isSelected())
					paytype = "Credit Card";
				else if(rdbtnAccountCheque.isSelected())
					paytype = "Cheque";
				else
					paytype = "Bank Transfer";
					
				tradeOrder.setPayType(paytype);
				
				try {
					tradeOrder.printOrder(getInvoiceSettings(), getAccountDetailScreen(), getAccountListScreen(), getAcc());
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnAccountPrintOrder.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAccountPrintOrder.setBounds(17, 17, 167, 38);
		btnAccountPrintOrder.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelAccountPrintOrder.add(btnAccountPrintOrder);
		
		JLabel lblAccountLogo = new JLabel("New label");
		lblAccountLogo.setBounds(1046, 385, 199, 200);
		// Put Logo image on order tab by adding an image to the Logo Label
		lblAccountLogo.setIcon(imageIcon);
		getContentPane().add(lblAccountLogo);
		
		// If this is an account quote conversion 
		if(tradeQuote != null) {
			setTradeOrder(tradeQuote);
			// Clear the order textfield boxes and disable the quantity textfield and make the ENTER button invisible
			txtAccountLineCode.setText("");
			txtAccountLineDesc.setText("");
			txtAccountLinePrice.setText("");
			txtAccountLineQty.setText("");
			txtAccountLineQty.setEnabled(false);
			btnAccountNewOrderLine.setVisible(false);
						
			// Make the Account: Current Order, Print Order, PaymentMethod, GrossProfit, Rounding and AccountDetails panels visible
			panelAccountCurrentOrder.setVisible(true);
			panelAccountPrintOrder.setVisible(true);
			panelAccountPaymentMethod.setVisible(true);
			panelAccountGrossProfit.setVisible(true);
			panelAccountRounding.setVisible(true);
					
			refreshCurrentOrderTable();
					
			// Set the focus back to the item description
			txtAccountLineDesc.requestFocus();
		}
	}
	
	
	/**
	 * This method finds the Item in the database that the user has selected from the table of Items and updates the appropriate fields on
	 * the screen
	 */
	public void ordLineAccountItemSelected() {
		try {
		
			// We use the table's getSelectedRow() method to find the row that the User selected. 
			int row = tblAccountItemDescription.getSelectedRow();
	
			// Then we can get the description of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
			// row and column that we require.
			// This returns an Object so we get the toString of it.
			String descString = tblAccountItemDescription.getModel().getValueAt(row, 0).toString();
			
			Item selectedItem = getProductDao().findItemByDesc(descString);
						
			if(selectedItem.getItemCode() != null){
				// Put selected item's code, description and price into the oderline entry fields and enable the quantity field and make the enter button visible. 
				txtAccountLineCode.setText(selectedItem.getItemCode());
				txtAccountLineDesc.setText(selectedItem.getItemDescription());
				txtAccountLinePrice.setText(Utilities.stringToDec( String.valueOf(selectedItem.getTradePrice()) ));
				txtTradeLineCost = selectedItem.getCostPrice();
				txtAccountLineQty.setEnabled(true);
			}
			
			txtAccountLineQty.requestFocus();
			panelAccountItemDescriptionChoice.setVisible(false);
			
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}


	/**
	 * This method loads the current orderline objects of the current order into the current order table on the screen
	 */
	public void refreshCurrentOrderTable(){
		
		String columns[] = {"ITEM", "DESCRIPTION", "QTY", "PRICE", "TOTAL"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		
		for(TradeOrderLine o : tradeOrder.getOrderList()){
			String tableCode= o.getItemCode();
			String tableDesc = o.getItemDescription();
			String tableQty = o.getOrderQty();
			String tableItemPrice = o.getItemPrice();
			String tableOrderPrice = o.getValueExVat();
			Object[] line = {tableCode, tableDesc, tableQty, tableItemPrice, tableOrderPrice};
			tableModel.addRow(line);
		}
		tblAccountOrder.setModel(tableModel);
		lblAccountTotalPrice.setText("\u20AC " + String.valueOf(tradeOrder.getTotalPreRounding()));
		lblAccountTotalExVat.setText("\u20AC " + tradeOrder.getTotalExVat());
	
	
		// Set the column widths for the table
		tblAccountOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnModelOrder = tblAccountOrder.getColumnModel();
		columnModelOrder.getColumn(0).setPreferredWidth(90);
		columnModelOrder.getColumn(1).setPreferredWidth(479);
		columnModelOrder.getColumn(2).setPreferredWidth(40);
		columnModelOrder.getColumn(3).setPreferredWidth(70);	
		columnModelOrder.getColumn(4).setPreferredWidth(83);
	}
	
	
	/**
	 * This method starts a new order on the screen by setting the current trade order object to null and setting up the screen
	 * to accept a new order
	 */
	public void startNewOrder(){
		
		// Make the current Account CurrentOrder, PrintOrder, PaymentMethod and the GrossProfit panel invisible
		panelAccountCurrentOrder.setVisible(false);
		panelAccountPrintOrder.setVisible(false);
		panelAccountPaymentMethod.setVisible(false);
		panelAccountGrossProfit.setVisible(false);
		
		// Remove the current order from the trade order object
		tradeOrder = null;
		
		// Blank the current order line entry fields
		txtAccountLineCode.setText(null);
		txtAccountLineDesc.setText(null);
		txtAccountLinePrice.setText(null);
		txtAccountLineQty.setText(null);
		
		// Disable quantity field and make ENTER button invisible - because they may be accessible when NEW ORDER button pressed 
		txtAccountLineQty.setEnabled(false);
		btnAccountNewOrderLine.setVisible(false);
		
		// Make the Account: CurrentOrder, PrintOrder, PaymentMethod, GrossProfit, Rounding and Details panels invisible
		panelAccountCurrentOrder.setVisible(false);
		panelAccountPrintOrder.setVisible(false);
		panelAccountPaymentMethod.setVisible(false);
		panelAccountGrossProfit.setVisible(false);
		panelAccountRounding.setVisible(false);
		
		// Set the Payment Type back to Cash
		rdbtnAccountCash.setSelected(true);
		
		// Blank the Account Rep Code field, enable it and make it editable
		txtAccountLineRep.setEditable(true);
		txtAccountLineRep.setEnabled(true);
		txtAccountLineRep.setText("");

	}
}
