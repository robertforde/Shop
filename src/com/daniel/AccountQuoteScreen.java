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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
 * 	This class opens a JFrame that allows a user to enter a quote for a particular account
 * @author Robert Forde
 *
 */
public class AccountQuoteScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InvoiceSettings invoiceSettings;
	private TradeOrder tradeQuote;
	private TradeOrderLine tradeQuoteLine;
	private Account acc;

	private JTextField txtAccountQuoteLineRep;
	private JTextField txtAccountQuoteLineCode;
	private JTextField txtAccountQuoteLineDesc;
	private JTextField txtAccountQuoteLinePrice;
	private JTextField txtAccountQuoteLineQty;
	private JTable tblAccountQuoteItemDescription;
	private JTable tblAccountQuoteOrder;
	private JLabel lblAccountQuoteTotalPrice;
	private JLabel lblAccountQuoteTotalExVat;
	private JPanel panelAccountQuoteItemDescriptionChoice;
	private JButton btnAccountQuoteNewOrderLine;
	private JLabel lblAccountQuoteGrossProfitValue;
	private JLabel lblAccountQuoteGrossProfitPercent;
	private JPanel panelAccountQuoteCurrentQuote;
	private JPanel panelAccountQuoteRounding;
	private JPanel panelAccountQuoteGrossProfit;
	private JPanel panelAccountQuoteNewQuote;
	private JPanel panelAccountQuotePrintQuote;
	private JPanel panelAccountQuoteCustDetails;

	private float txtAccountQuoteLineCost;
	
	private TableColumnModel columnModelOrder;

	private ProductDaoImpl productDao;
	private StaffDaoImpl staffDao;
	private AccountDao accountDao;
	private OrderDao orderDao;
	
	
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

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
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
	
	public TradeOrder getTradeQuote() {
		return tradeQuote;
	}

	public void setTradeQuote(TradeOrder tradeQuote) {
		this.tradeQuote = tradeQuote;
	}

	public TradeOrderLine getTradeQuoteLine() {
		return tradeQuoteLine;
	}

	public void setTradeQuoteLine(TradeOrderLine tradeQuoteLine) {
		this.tradeQuoteLine = tradeQuoteLine;
	}
	
	public Account getAcc() {
		return acc;
	}

	public void setAcc(Account acc) {
		this.acc = acc;
	}

	
	/**
	 * 
	 * @param invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 * @param acc The account that this quote is being entered for
	 */
	public AccountQuoteScreen(InvoiceSettings invoiceSettings, Account acc){
		
		setProductDao(Dan.ctx.getBean("productDaoImpl", ProductDaoImpl.class));
		setStaffDao(Dan.ctx.getBean("staffDaoImpl", StaffDaoImpl.class));
		setAccountDao(Dan.ctx.getBean("accountDaoImpl", AccountDaoImpl.class));
		setOrderDao(Dan.ctx.getBean("orderDaoImpl", OrderDaoImpl.class));
		
		getContentPane().setBackground(Color.DARK_GRAY);
		setInvoiceSettings(invoiceSettings);
		setAcc(acc);
		
		setBounds(30, 50, 1310, 629);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("ACCOUNT QUOTATION");
		setVisible(true);
	
		// Create the logo images for the application
		Image logo = new ImageIcon(this.getClass().getResource("/Logo199X198.jpg")).getImage();
		ImageIcon imageIcon = new ImageIcon(logo);
	
		// Create the up arrow ImageIcon
		Image upArrow = new ImageIcon(this.getClass().getResource("/Up.jpg")).getImage();
		ImageIcon imageIconUpArrow = new ImageIcon(upArrow);
	
		// Create the down arrow ImageIcon
		Image downArrow = new ImageIcon(this.getClass().getResource("/Down.jpg")).getImage();
		ImageIcon imageIconDownArrow = new ImageIcon(downArrow);
		
		JPanel panelAccountQuoteLine = new JPanel();
		panelAccountQuoteLine.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountQuoteLine.setBounds(28, 21, 1217, 71);
		getContentPane().add(panelAccountQuoteLine);
		panelAccountQuoteLine.setLayout(null);
		
		JLabel lblAccountQuoteRepCodeText = new JLabel("Rep Code");
		lblAccountQuoteRepCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountQuoteRepCodeText.setBounds(34, 9, 86, 18);
		panelAccountQuoteLine.add(lblAccountQuoteRepCodeText);
		
		JLabel lblAccountQuoteItemDescriptionText = new JLabel("Item Description");
		lblAccountQuoteItemDescriptionText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountQuoteItemDescriptionText.setBounds(318, 9, 214, 18);
		panelAccountQuoteLine.add(lblAccountQuoteItemDescriptionText);
		
		JLabel lblAccountQuoteItemCodeText = new JLabel("Item Code");
		lblAccountQuoteItemCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountQuoteItemCodeText.setBounds(152, 9, 132, 18);
		panelAccountQuoteLine.add(lblAccountQuoteItemCodeText);
		
		JLabel lblAccountQuoteItemPriceText = new JLabel("Item Price");
		lblAccountQuoteItemPriceText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountQuoteItemPriceText.setBounds(678, 9, 111, 18);
		panelAccountQuoteLine.add(lblAccountQuoteItemPriceText);
		
		JLabel lblAccountQuoteItemQuantityText = new JLabel("Quantity");
		lblAccountQuoteItemQuantityText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountQuoteItemQuantityText.setBounds(828, 9, 75, 18);
		panelAccountQuoteLine.add(lblAccountQuoteItemQuantityText);
		
		txtAccountQuoteLineRep = new JTextField();
		txtAccountQuoteLineRep.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountQuoteLineRep.setBounds(34, 31, 86, 28);
		txtAccountQuoteLineRep.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountQuoteLine.add(txtAccountQuoteLineRep);
		txtAccountQuoteLineRep.setColumns(10);
		
		txtAccountQuoteLineCode = new JTextField();
		txtAccountQuoteLineCode.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountQuoteLineCode.setEditable(false);
		txtAccountQuoteLineCode.setColumns(10);
		txtAccountQuoteLineCode.setBounds(152, 31, 132, 28);
		txtAccountQuoteLineCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountQuoteLine.add(txtAccountQuoteLineCode);
		
		txtAccountQuoteLineDesc = new JTextField();
		txtAccountQuoteLineDesc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// Disable the normal tab key focus on the Item Description textbox
				txtAccountQuoteLineDesc.setFocusTraversalKeysEnabled(false);
				
				// Uppercase the entered description
				txtAccountQuoteLineDesc.setText(txtAccountQuoteLineDesc.getText().toUpperCase());
				
				// Make ENTER button and the list of descriptions invisible
				panelAccountQuoteItemDescriptionChoice.setVisible(false);
				btnAccountQuoteNewOrderLine.setVisible(false);
				
				if (txtAccountQuoteLineDesc.getText().equals("") == false) {  
					// Search in database for items that have this exact description in database and if found enable qty textfield and fill in code and price
					try {
						
						// Find item based on it's description
						Item foundItem = getProductDao().findItemByDesc(txtAccountQuoteLineDesc.getText());

						if(foundItem.getItemCode() != null){
							txtAccountQuoteLineCode.setText(foundItem.getItemCode());
							txtAccountQuoteLinePrice.setText(Utilities.stringToDec(String.valueOf(foundItem.getTradePrice())));
							txtAccountQuoteLineQty.setEnabled(true);
						} else {
							txtAccountQuoteLineCode.setText("");
							txtAccountQuoteLinePrice.setText("");
							txtAccountQuoteLineQty.setText("");
							txtAccountQuoteLineQty.setEnabled(false);
							
							// Exact match not found so bring up list of descriptions that contain this description and are not set as deleted
							List<Item> itemList = getProductDao().findPossibleItemMatches(txtAccountQuoteLineDesc.getText().toUpperCase());
							
							if(itemList.size() != 0) {
								
								String columns[] = {"DESCRIPTION"};
								DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
								
								for(Item item : itemList){
									String tableDesc = item.getItemDescription();
									Object[] line = {tableDesc};
									tableModel.addRow(line);
								}
								
								tblAccountQuoteItemDescription.setModel(tableModel);								
								tblAccountQuoteItemDescription.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
								TableColumnModel columnModel = tblAccountQuoteItemDescription.getColumnModel();
								columnModel.getColumn(0).setPreferredWidth(340);
								panelAccountQuoteItemDescriptionChoice.setVisible(true);
								
								// Set the tab key to the item description possible matches table
								if(arg0.getKeyCode() == KeyEvent.VK_TAB){
									tblAccountQuoteItemDescription.requestFocus();
								} 
								
							} else {
								txtAccountQuoteLineCode.setText("");
								txtAccountQuoteLinePrice.setText("");
								txtAccountQuoteLineQty.setText("");
								txtAccountQuoteLineQty.setEnabled(false);
							}
								
						}
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}else {
					// Make ENTER button and the list of descriptions invisible
					panelAccountQuoteItemDescriptionChoice.setVisible(false);
					btnAccountQuoteNewOrderLine.setVisible(false);
				}
			}
		});
		txtAccountQuoteLineDesc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountQuoteLineDesc.setColumns(10);
		txtAccountQuoteLineDesc.setBounds(318, 31, 341, 28);
		txtAccountQuoteLineDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountQuoteLine.add(txtAccountQuoteLineDesc);
		
		txtAccountQuoteLinePrice = new JTextField();
		txtAccountQuoteLinePrice.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountQuoteLinePrice.setEditable(false);
		txtAccountQuoteLinePrice.setColumns(10);
		txtAccountQuoteLinePrice.setBounds(678, 31, 132, 28);
		txtAccountQuoteLinePrice.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountQuoteLine.add(txtAccountQuoteLinePrice);
		
		txtAccountQuoteLineQty = new JTextField();
		txtAccountQuoteLineQty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				panelAccountQuoteItemDescriptionChoice.setVisible(false);
				btnAccountQuoteNewOrderLine.setVisible(true);
			}
			
		});
		txtAccountQuoteLineQty.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountQuoteLineQty.setEnabled(false);
		txtAccountQuoteLineQty.setColumns(10);
		txtAccountQuoteLineQty.setBounds(828, 31, 57, 28);
		txtAccountQuoteLineQty.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountQuoteLine.add(txtAccountQuoteLineQty);
		
		panelAccountQuoteItemDescriptionChoice = new JPanel();
		panelAccountQuoteItemDescriptionChoice.setBounds(350, 91, 338, 398);
		getContentPane().add(panelAccountQuoteItemDescriptionChoice);
		panelAccountQuoteItemDescriptionChoice.setLayout(null);
		
		JScrollPane scrlAccountQuoteItemDescription = new JScrollPane();
		scrlAccountQuoteItemDescription.setBounds(0, 0, 338, 398);
		panelAccountQuoteItemDescriptionChoice.add(scrlAccountQuoteItemDescription);
		
		tblAccountQuoteItemDescription = new JTable();
		tblAccountQuoteItemDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// If enter pressed call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make ENTER visible 
				if(arg0.getKeyCode() == 10) {
					ordLineAccountItemSelected();					
				}
			}
		});
		tblAccountQuoteItemDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ordLineAccountItemSelected();
			}
		});
		tblAccountQuoteItemDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblAccountQuoteItemDescription.setCellSelectionEnabled(true);
		tblAccountQuoteItemDescription.setColumnSelectionAllowed(false);
		scrlAccountQuoteItemDescription.setViewportView(tblAccountQuoteItemDescription);
		panelAccountQuoteItemDescriptionChoice.setVisible(false);
		
		btnAccountQuoteNewOrderLine = new JButton("ENTER");
		btnAccountQuoteNewOrderLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// First check that the Rep is valid
				try {
					
					Staff checkStaff = getStaffDao().staffExists(txtAccountQuoteLineRep.getText());
					
					// If this repCode is okay
					if(checkStaff.getRepCode() != null) {
						
						txtAccountQuoteLineRep.setEditable(false);
						txtAccountQuoteLineRep.setEnabled(false);
						// Check if Quote exists and if not create one
						if(tradeQuote== null){
							tradeQuote= new TradeOrder();
							tradeQuote.setOrderDate(new Date());
							tradeQuote.setSaleType("AccQuote");
							tradeQuote.setCustId(getAcc().getId());
							tradeQuote.setName(getAcc().getName());
							tradeQuote.setAddress1(getAcc().getAddressLine1());
							tradeQuote.setAddress2(getAcc().getAddressLine2());
							tradeQuote.setPhone(getAcc().getPhone());
							tradeQuote.setRepNo(txtAccountQuoteLineRep.getText());
						}
	
						try {
	
							// Check if an item is entered
							if(!txtAccountQuoteLineDesc.getText().isEmpty()) {
								// Check that the Quantity entered is an Integer and > 0.
								if(Integer.parseInt(txtAccountQuoteLineQty.getText()) > 0) {
															
									// Create a TradeQuoteLine and call the addTradeOrderLine() method to calculate it's values and save it to the arraylist  
									tradeQuoteLine = new TradeOrderLine();
	
									tradeQuoteLine.addTradeOrderLine(txtAccountQuoteLineCode, txtAccountQuoteLineDesc, txtAccountQuoteLineQty, txtAccountQuoteLinePrice, 
											lblAccountQuoteGrossProfitValue, lblAccountQuoteGrossProfitPercent, tradeQuote, txtAccountQuoteLineCost, getInvoiceSettings());
									
									// Clear the order textfield boxes and disable the quantity textfield and make the ENTER button invisible
									txtAccountQuoteLineCode.setText("");
									txtAccountQuoteLineDesc.setText("");
									txtAccountQuoteLinePrice.setText("");
									txtAccountQuoteLineQty.setText("");
									txtAccountQuoteLineQty.setEnabled(false);
									btnAccountQuoteNewOrderLine.setVisible(false);
										
									// Make the Account: Current Quote, Print Quote, GrossProfit, Rounding panels visible
									panelAccountQuoteCurrentQuote.setVisible(true);
									panelAccountQuotePrintQuote.setVisible(true);
									panelAccountQuoteGrossProfit.setVisible(true);
									panelAccountQuoteRounding.setVisible(true);
										
									refreshCurrentOrderTable();
									
									// Set the focus back to the item description
									txtAccountQuoteLineDesc.requestFocus();
								} else {
									JOptionPane.showMessageDialog(null, "You must enter a Quantity greater than 0 !");
								}
							}else {
								JOptionPane.showMessageDialog(null, "You must enter an Item !");	
							}
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, "You must enter a number for the Quantity !" + ex);
						}
					}else{
						JOptionPane.showMessageDialog(null, "That Rep Code doesn't exist, please try again !");
					}
					
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnAccountQuoteNewOrderLine.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAccountQuoteNewOrderLine.setBounds(964, 17, 167, 38);
		btnAccountQuoteNewOrderLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelAccountQuoteLine.add(btnAccountQuoteNewOrderLine);
		
		panelAccountQuoteCurrentQuote = new JPanel();
		panelAccountQuoteCurrentQuote.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountQuoteCurrentQuote.setBounds(28, 138, 800, 447);
		panelAccountQuoteCurrentQuote.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(panelAccountQuoteCurrentQuote);
		panelAccountQuoteCurrentQuote.setLayout(null);
		panelAccountQuoteCurrentQuote.setVisible(false);
		
		JLabel lblAccountQuoteCurrentTransaction = new JLabel("ACCOUNT QUOTATION");
		lblAccountQuoteCurrentTransaction.setFont(new Font("Georgia", Font.BOLD, 18));
		lblAccountQuoteCurrentTransaction.setBounds(10, 11, 244, 31);
		panelAccountQuoteCurrentQuote.add(lblAccountQuoteCurrentTransaction);
		
		JLabel lblAccountQuoteTotalExVatText = new JLabel("TOTAL EX-VAT");
		lblAccountQuoteTotalExVatText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblAccountQuoteTotalExVatText.setBounds(297, 11, 148, 31);
		panelAccountQuoteCurrentQuote.add(lblAccountQuoteTotalExVatText);
		
		JLabel lblAccountQuoteTotalPriceText = new JLabel("TOTAL INC-VAT");
		lblAccountQuoteTotalPriceText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblAccountQuoteTotalPriceText.setBounds(634, 11, 156, 31);
		panelAccountQuoteCurrentQuote.add(lblAccountQuoteTotalPriceText);
		
		lblAccountQuoteTotalPrice = new JLabel("0.00", SwingConstants.RIGHT);
		lblAccountQuoteTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAccountQuoteTotalPrice.setBounds(634, 37, 156, 31);
		panelAccountQuoteCurrentQuote.add(lblAccountQuoteTotalPrice);
		
		lblAccountQuoteTotalExVat = new JLabel("0.00", SwingConstants.RIGHT);
		lblAccountQuoteTotalExVat.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAccountQuoteTotalExVat.setBounds(297, 37, 144, 31);
		panelAccountQuoteCurrentQuote.add(lblAccountQuoteTotalExVat);
		
		JScrollPane scrlAccountQuoteOrder = new JScrollPane();
		scrlAccountQuoteOrder.setBounds(10, 68, 780, 368);
		panelAccountQuoteCurrentQuote.add(scrlAccountQuoteOrder);
		
		tblAccountQuoteOrder = new JTable(){
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
		tblAccountQuoteOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				if(tradeQuote.getOrderList().size() > 1) {
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblAccountQuoteOrder.getSelectedRow();
					
					// We use this row to find the element in the arraylist that the user selected
					tradeQuote.getOrderList().remove(row);
					
					// Recalculate everything for the Order
					tradeQuote.recalculateAfterDelete(getInvoiceSettings().getReceiptVatRate(), lblAccountQuoteGrossProfitValue, lblAccountQuoteGrossProfitPercent);
					
					// Load the current order lines onto the screen
					refreshCurrentOrderTable();
					
				} else{
					startNewQuote();
				}
			}
		});
		tblAccountQuoteOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblAccountQuoteOrder.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tblAccountQuoteOrder.setShowGrid(false);
		tblAccountQuoteOrder.setShowHorizontalLines(false);
		tblAccountQuoteOrder.setShowVerticalLines(false);
		tblAccountQuoteOrder.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		scrlAccountQuoteOrder.setViewportView(tblAccountQuoteOrder);
		
		panelAccountQuoteRounding = new JPanel();
		panelAccountQuoteRounding.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountQuoteRounding.setBounds(855, 173, 164, 81);
		panelAccountQuoteRounding.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelAccountQuoteRounding);
		panelAccountQuoteRounding.setLayout(null);
		panelAccountQuoteRounding.setVisible(false);
		
		JLabel lblAccountQuoteRoundText = new JLabel("Round");
		lblAccountQuoteRoundText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountQuoteRoundText.setBounds(55, 11, 55, 17);
		panelAccountQuoteRounding.add(lblAccountQuoteRoundText);
		
		JLabel lblAccountQuoteRoundUp = new JLabel("Round Up");
		lblAccountQuoteRoundUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String beforeRound = tradeQuote.getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(String.valueOf(Float.parseFloat(beforeRound.substring(0, theDot)) + 1));
				tradeQuote.setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				tradeQuote.setRounding(String.valueOf(rounding));
	
				// Save the rounding and post Rounding values if the invoice has been printed
				if(tradeQuote.getReceiptNo() != 0) {
					try {
						
						getOrderDao().updateRoundingHeader("quoteheader", rounding, tradeQuote.getTotalPostRounding(), tradeQuote.getReceiptNo());
							
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblAccountQuoteTotalPrice.setText("\u20AC " + tradeQuote.getTotalPostRounding());
	
			}
		});
		lblAccountQuoteRoundUp.setBounds(36, 39, 35, 31);
		panelAccountQuoteRounding.add(lblAccountQuoteRoundUp);
		lblAccountQuoteRoundUp.setIcon(imageIconUpArrow);
		
		JLabel lblAccountQuoteRoundDown = new JLabel("Round Down");
		lblAccountQuoteRoundDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String beforeRound = tradeQuote.getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(beforeRound.substring(0, theDot));
				tradeQuote.setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				tradeQuote.setRounding(String.valueOf(rounding));
	
				// Save the rounding and post Rounding values if the invoice has been printed
				if(tradeQuote.getReceiptNo() != 0) {
					try {
						
						getOrderDao().updateRoundingHeader("quoteheader", rounding, tradeQuote.getTotalPostRounding(), tradeQuote.getReceiptNo());
	
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblAccountQuoteTotalPrice.setText("\u20AC " + tradeQuote.getTotalPostRounding());
			}
		});
		lblAccountQuoteRoundDown.setBounds(92, 39, 35, 31);
		panelAccountQuoteRounding.add(lblAccountQuoteRoundDown);
		lblAccountQuoteRoundDown.setIcon(imageIconDownArrow);
		
		panelAccountQuoteGrossProfit = new JPanel();
		panelAccountQuoteGrossProfit.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountQuoteGrossProfit.setBounds(855, 347, 163, 142);
		panelAccountQuoteGrossProfit.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelAccountQuoteGrossProfit);
		panelAccountQuoteGrossProfit.setLayout(null);
		panelAccountQuoteGrossProfit.setVisible(false);
		
		JLabel lblAccountQuoteGrossProfitText = new JLabel("Gross Profit");
		lblAccountQuoteGrossProfitText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblAccountQuoteGrossProfitText.setBounds(27, 23, 111, 24);
		panelAccountQuoteGrossProfit.add(lblAccountQuoteGrossProfitText);
		
		lblAccountQuoteGrossProfitValue = new JLabel("\u20AC 0.00", SwingConstants.RIGHT);
		lblAccountQuoteGrossProfitValue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAccountQuoteGrossProfitValue.setBounds(25, 58, 102, 24);
		panelAccountQuoteGrossProfit.add(lblAccountQuoteGrossProfitValue);
		
		lblAccountQuoteGrossProfitPercent = new JLabel("0.00 %", SwingConstants.RIGHT);
		lblAccountQuoteGrossProfitPercent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAccountQuoteGrossProfitPercent.setBounds(25, 92, 102, 24);
		panelAccountQuoteGrossProfit.add(lblAccountQuoteGrossProfitPercent);

/*
		panelAccountQuoteNewQuote = new JPanel();
		panelAccountQuoteNewQuote.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountQuoteNewQuote.setBounds(1046, 113, 199, 71);
		getContentPane().add(panelAccountQuoteNewQuote);
		panelAccountQuoteNewQuote.setLayout(null);
		panelAccountQuoteNewQuote.setVisible(false);
		
		JButton btnTradeQuoteNewQuote = new JButton("NEW QUOTE");
		btnTradeQuoteNewQuote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				startNewQuote();
			}
		});
		btnTradeQuoteNewQuote.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradeQuoteNewQuote.setBounds(17, 17, 167, 38);
		panelAccountQuoteNewQuote.add(btnTradeQuoteNewQuote);
*/		
		
		panelAccountQuotePrintQuote = new JPanel();
		panelAccountQuotePrintQuote.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountQuotePrintQuote.setBounds(1046, 204, 199, 71);
		panelAccountQuotePrintQuote.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(panelAccountQuotePrintQuote);
		panelAccountQuotePrintQuote.setLayout(null);
		panelAccountQuotePrintQuote.setVisible(false);
		
		JButton btnAccountQuotePrintQuote = new JButton("PRINT QUOTE");
		btnAccountQuotePrintQuote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				tradeQuote.setOrderLinesToPrint(0);
								
				try {
					tradeQuote.printOrder(getInvoiceSettings(), null, null, null);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnAccountQuotePrintQuote.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAccountQuotePrintQuote.setBounds(17, 17, 167, 38);
		btnAccountQuotePrintQuote.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelAccountQuotePrintQuote.add(btnAccountQuotePrintQuote);
		
		JLabel lblAccountQuoteLogo = new JLabel("New label");
		lblAccountQuoteLogo.setBounds(1046, 385, 199, 200);
		// Put Logo image on order tab by adding an image to the Logo Label
		lblAccountQuoteLogo.setIcon(imageIcon);
		getContentPane().add(lblAccountQuoteLogo);
	}


	/**
	 * This method finds the Item in the database that the user has selected from the table of Items and updates the appropriate fields on
	 * the screen
	 */
	public void ordLineAccountItemSelected() {
		try {
		
			// We use the table's getSelectedRow() method to find the row that the User selected. 
			int row = tblAccountQuoteItemDescription.getSelectedRow();
	
			// Then we can get the description of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
			// row and column that we require.
			// This returns an Object so we get the toString of it.
			String descString = tblAccountQuoteItemDescription.getModel().getValueAt(row, 0).toString();
			
			Item selectedItem = getProductDao().findItemByDesc(descString);
			
			if(selectedItem.getItemCode() != null){
				// Put selected item's code, description and price into the oderline entry fields and enable the quantity field and make the enter button visible. 
				txtAccountQuoteLineCode.setText(selectedItem.getItemCode());
				txtAccountQuoteLineDesc.setText(selectedItem.getItemDescription());
				txtAccountQuoteLinePrice.setText(Utilities.stringToDec( String.valueOf(selectedItem.getTradePrice()) ));
				txtAccountQuoteLineCost = selectedItem.getCostPrice();
				txtAccountQuoteLineQty.setEnabled(true);
			}
			
			txtAccountQuoteLineQty.requestFocus();
			panelAccountQuoteItemDescriptionChoice.setVisible(false);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	
	/**
	 * This method loads the current orderline objects of the current quote into the current quote table on the screen
	 */
	public void refreshCurrentOrderTable(){
			
		String columns[] = {"ITEM", "DESCRIPTION", "QTY", "PRICE", "TOTAL"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		
		for(TradeOrderLine o : tradeQuote.getOrderList()){
			String tableCode= o.getItemCode();
			String tableDesc = o.getItemDescription();
			String tableQty = o.getOrderQty();
			String tableItemPrice = o.getItemPrice();
			String tableOrderPrice = o.getValueExVat();
			Object[] line = {tableCode, tableDesc, tableQty, tableItemPrice, tableOrderPrice};
			tableModel.addRow(line);
		}
		tblAccountQuoteOrder.setModel(tableModel);
		lblAccountQuoteTotalPrice.setText("\u20AC " + String.valueOf(tradeQuote.getTotalPreRounding()));
		lblAccountQuoteTotalExVat.setText("\u20AC " + tradeQuote.getTotalExVat());
	
	
		// Set the column widths for the table
		tblAccountQuoteOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnModelOrder = tblAccountQuoteOrder.getColumnModel();
		columnModelOrder.getColumn(0).setPreferredWidth(90);
		columnModelOrder.getColumn(1).setPreferredWidth(479);
		columnModelOrder.getColumn(2).setPreferredWidth(40);
		columnModelOrder.getColumn(3).setPreferredWidth(70);	
		columnModelOrder.getColumn(4).setPreferredWidth(83);
	}
	
	/**
	 * This method starts a new quote on the screen by setting the current trade quote object to null and setting up the screen
	 * to accept a new quote
	 */
	public void startNewQuote(){
		
		// Make the current Current Order panel, Print Order panel and the GrossProfit panel invisible
		panelAccountQuoteCurrentQuote.setVisible(false);
		panelAccountQuotePrintQuote.setVisible(false);
		panelAccountQuoteGrossProfit.setVisible(false);
		
		// Remove the current quote from the order object
		tradeQuote= null;
		
		// Blank the current order line entry fields
		txtAccountQuoteLineCode.setText(null);
		txtAccountQuoteLineDesc.setText(null);
		txtAccountQuoteLinePrice.setText(null);
		txtAccountQuoteLineQty.setText(null);
		
		// Disable the quantity field and make ENTER button invisible - because they may be accessible when NEW ORDER button pressed 
		txtAccountQuoteLineQty.setEnabled(false);
		btnAccountQuoteNewOrderLine.setVisible(false);
		
		// Make the Current Order, Print Order, GrossProfit, Rounding, CustDetails and NewOrder panels invisible
		panelAccountQuoteCurrentQuote.setVisible(false);
		panelAccountQuotePrintQuote.setVisible(false);
		panelAccountQuoteGrossProfit.setVisible(false);
		panelAccountQuoteRounding.setVisible(false);
		panelAccountQuoteCustDetails.setVisible(false);
		panelAccountQuoteNewQuote.setVisible(false);
		
		// Blank the Rep Code field, enable it and make it editable
		txtAccountQuoteLineRep.setEditable(true);
		txtAccountQuoteLineRep.setEnabled(true);
		txtAccountQuoteLineRep.setText("");
	
	}
}
