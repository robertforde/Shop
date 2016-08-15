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

import com.daniel.dao.ProductDao;
import com.daniel.dao.ProductDaoImpl;
import com.daniel.dao.StaffDao;
import com.daniel.dao.StaffDaoImpl;
import com.daniel.dao.SuspendedDao;
import com.daniel.dao.SuspendedDaoImpl;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.Item;
import com.daniel.model.Staff;
import com.daniel.model.SuspendedOrder;
import com.daniel.model.SuspendedOrderLine;
import com.daniel.utilities.Utilities;

/**
 * This class designs the Suspended Trade Order's screen and handles all of the operations to enable the User to enter a Suspended 
 * Trade Order
 * @author Robert Forde
 *
 */
public class SuspendTradeOrderScreen extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InvoiceSettings invoiceSettings;
	
	private SuspendedOrder order;
	private SuspendedOrderLine orderLine;

	private JPanel panelSuspendTradeCurrentOrder;
	private JPanel panelSuspendTradeRounding; 
	private JPanel panelSuspendTradeGrossProfit; 
	private JPanel panelSuspendTradeNewOrder; 
	private JPanel panelSuspendTradePrintOrder; 
	private JPanel panelSuspendTradeDetails;
	private JLabel lblSuspendTradeTotalPrice;
	private JLabel lblSuspendTradeTotalExVat;
	private JPanel panelSuspendTradeItemDescriptionChoice;
	
	private JTable tblSuspendTradeItemDescription;
	private JTable tblSuspendTradeOrder;
	
	private JButton btnSuspendTradeNewOrderLine;
	
	private JLabel lblTradeGrossProfitValue;
	private JLabel lblTradeGrossProfitPercent;
	
	private JTextField txtSuspendTradeLineRep;
	private JTextField txtSuspendTradeLineCode;
	private JTextField txtSuspendTradeLineDesc;
	private JTextField txtSuspendTradeLinePrice;
	private JTextField txtSuspendTradeLineQty;

	private TableColumnModel columnModelOrder;
	
	private float txtTradeLineCost;
	
	private SuspendedOrdersScreen suspendedOrdersScreen;

	private ProductDao productDao;
	private StaffDao staffDao;
	private SuspendedDao suspendedDao;

	
	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public StaffDao getStaffDao() {
		return staffDao;
	}

	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	public SuspendedDao getSuspendedDao() {
		return suspendedDao;
	}

	public void setSuspendedDao(SuspendedDao suspendDao) {
		this.suspendedDao = suspendDao;
	}

	public SuspendedOrdersScreen getSuspendedOrdersScreen() {
		return suspendedOrdersScreen;
	}

	public void setSuspendedOrdersScreen(SuspendedOrdersScreen suspendedOrdersScreen) {
		this.suspendedOrdersScreen = suspendedOrdersScreen;
	}
	
	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}

	public SuspendedOrder getOrder() {
		return order;
	}

	public void setOrder(SuspendedOrder order) {
		this.order = order;
	}

	public SuspendedOrderLine getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(SuspendedOrderLine orderLine) {
		this.orderLine = orderLine;
	}

	
	/**
	 * Constructs the Suspended Trade Order Screen
	 * @param invoiceSettings invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 * @param suspendedOrdersScreen A reference to the Suspended Orders Screen so the balance can be updated
	 */
	public SuspendTradeOrderScreen(InvoiceSettings invoiceSettings, SuspendedOrdersScreen suspendedOrdersScreen){
		
		setSuspendedOrdersScreen(suspendedOrdersScreen);
		
		setProductDao(Dan.ctx.getBean("productDaoImpl", ProductDaoImpl.class));
		setStaffDao(Dan.ctx.getBean("staffDaoImpl", StaffDaoImpl.class));
		setSuspendedDao(Dan.ctx.getBean("suspendedDaoImpl", SuspendedDaoImpl.class));
		
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

		setTitle("Suspended Trade Order");
		getContentPane().setBackground(Color.DARK_GRAY);
		setBounds(30, 50, 1310, 629);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);
		
		JPanel panelSuspendTradeOrderLine = new JPanel();
		panelSuspendTradeOrderLine.setForeground(Color.BLACK);
		panelSuspendTradeOrderLine.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendTradeOrderLine.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeOrderLine.setBounds(28, 21, 1217, 71);
		getContentPane().add(panelSuspendTradeOrderLine);
		panelSuspendTradeOrderLine.setLayout(null);
		
		JLabel lblSuspendTradeRepCodeText = new JLabel("Rep Code");
		lblSuspendTradeRepCodeText.setForeground(Color.BLACK);
		lblSuspendTradeRepCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendTradeRepCodeText.setBounds(34, 9, 96, 18);
		panelSuspendTradeOrderLine.add(lblSuspendTradeRepCodeText);
		
		txtSuspendTradeLineRep = new JTextField();
		txtSuspendTradeLineRep.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLineRep.setBounds(34, 31, 86, 28);
		txtSuspendTradeLineRep.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeOrderLine.add(txtSuspendTradeLineRep);
		txtSuspendTradeLineRep.setColumns(10);
		
		JLabel lblSuspendTradeItemCodeText = new JLabel("Item Code");
		lblSuspendTradeItemCodeText.setForeground(Color.BLACK);
		lblSuspendTradeItemCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendTradeItemCodeText.setBounds(152, 9, 132, 18);
		panelSuspendTradeOrderLine.add(lblSuspendTradeItemCodeText);
		
		txtSuspendTradeLineCode = new JTextField();
		txtSuspendTradeLineCode.setEditable(false);
		txtSuspendTradeLineCode.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLineCode.setBounds(152, 31, 132, 28);
		txtSuspendTradeLineCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeOrderLine.add(txtSuspendTradeLineCode);
		txtSuspendTradeLineCode.setColumns(10);
		
		JLabel lblSuspendTradeItemDescText = new JLabel("Item Description");
		lblSuspendTradeItemDescText.setForeground(Color.BLACK);
		lblSuspendTradeItemDescText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendTradeItemDescText.setBounds(318, 9, 175, 18);
		panelSuspendTradeOrderLine.add(lblSuspendTradeItemDescText);
		
		txtSuspendTradeLineDesc = new JTextField();
		txtSuspendTradeLineDesc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				
				// Disable the normal tab key focus on the Item Description textbox
				txtSuspendTradeLineDesc.setFocusTraversalKeysEnabled(false);
				
				// Uppercase the entered description
				txtSuspendTradeLineDesc.setText(txtSuspendTradeLineDesc.getText().toUpperCase());
				
				// Make ENTER button and the list of descriptions invisible
				panelSuspendTradeItemDescriptionChoice.setVisible(false);
				btnSuspendTradeNewOrderLine.setVisible(false);
				
				if (txtSuspendTradeLineDesc.getText().equals("") == false) {  
					// Search in database for items that have this exact description in database and if found enable qty textfield and fill in code and trade price
					try {
						
						// Find item based on it's description
						Item foundItem = getProductDao().findItemByDesc(txtSuspendTradeLineDesc.getText());

						if(foundItem.getItemCode() != null){
							txtSuspendTradeLineCode.setText(foundItem.getItemCode());
							txtSuspendTradeLinePrice.setText(Utilities.stringToDec(String.valueOf(foundItem.getTradePrice())));
							txtTradeLineCost = foundItem.getCostPrice();
							txtSuspendTradeLineQty.setEnabled(true);
						} else {
							txtSuspendTradeLineCode.setText("");
							txtSuspendTradeLinePrice.setText("");
							txtSuspendTradeLineQty.setText("");
							txtSuspendTradeLineQty.setEnabled(false);

							// Exact match not found so bring up list of descriptions that contain this description and are not set as deleted
							List<Item> itemList = getProductDao().findPossibleItemMatches(txtSuspendTradeLineDesc.getText().toUpperCase());
							
							if(itemList.size() != 0) {
								
								String columns[] = {"DESCRIPTION"};
								DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
								
								for(Item item : itemList){
									String tableDesc = item.getItemDescription();
									Object[] line = {tableDesc};
									tableModel.addRow(line);
								}
								
								tblSuspendTradeItemDescription.setModel(tableModel);
								tblSuspendTradeItemDescription.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
								TableColumnModel columnModel = tblSuspendTradeItemDescription.getColumnModel();
								columnModel.getColumn(0).setPreferredWidth(340);
								panelSuspendTradeItemDescriptionChoice.setVisible(true);
								
								// Set the tab key to the item description possible matches table
								if(arg0.getKeyCode() == KeyEvent.VK_TAB){
									tblSuspendTradeItemDescription.requestFocus();
								} 
									
							} else {
								txtSuspendTradeLineCode.setText("");
								txtSuspendTradeLinePrice.setText("");
								txtSuspendTradeLineQty.setText("");
								txtSuspendTradeLineQty.setEnabled(false);
							}
								
						}
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}else {
					// Make ENTER button and the list of descriptions invisible
					panelSuspendTradeItemDescriptionChoice.setVisible(false);
					btnSuspendTradeNewOrderLine.setVisible(false);
				}
			}
		});
		txtSuspendTradeLineDesc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLineDesc.setBounds(318, 31, 341, 28);
		txtSuspendTradeLineDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeOrderLine.add(txtSuspendTradeLineDesc);
		txtSuspendTradeLineDesc.setColumns(10);
		
		JLabel lblSuspendTradeItemPriceText = new JLabel("Item Price");
		lblSuspendTradeItemPriceText.setForeground(Color.BLACK);
		lblSuspendTradeItemPriceText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendTradeItemPriceText.setBounds(678, 9, 106, 18);
		panelSuspendTradeOrderLine.add(lblSuspendTradeItemPriceText);
		
		txtSuspendTradeLinePrice = new JTextField();
		txtSuspendTradeLinePrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				btnSuspendTradeNewOrderLine.setVisible(true);
			}
		});
		txtSuspendTradeLinePrice.setEditable(false);
		txtSuspendTradeLinePrice.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLinePrice.setBounds(678, 31, 132, 28);
		txtSuspendTradeLinePrice.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeOrderLine.add(txtSuspendTradeLinePrice);
		txtSuspendTradeLinePrice.setColumns(10);
		
		txtSuspendTradeLineQty = new JTextField();
		txtSuspendTradeLineQty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnSuspendTradeNewOrderLine.setVisible(true);
			}
		});
		txtSuspendTradeLineQty.setEnabled(false);
		txtSuspendTradeLineQty.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLineQty.setBounds(828, 31, 57, 28);
		txtSuspendTradeLineQty.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeOrderLine.add(txtSuspendTradeLineQty);
		txtSuspendTradeLineQty.setColumns(10);
		
		JLabel lblSuspendTradeItemQtyText = new JLabel("Quantity");
		lblSuspendTradeItemQtyText.setForeground(Color.BLACK);
		lblSuspendTradeItemQtyText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendTradeItemQtyText.setBounds(828, 9, 75, 18);
		panelSuspendTradeOrderLine.add(lblSuspendTradeItemQtyText);
		
		btnSuspendTradeNewOrderLine = new JButton("ENTER");
		btnSuspendTradeNewOrderLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		btnSuspendTradeNewOrderLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// First check that the Rep is valid
				try {
					Staff checkStaff = getStaffDao().staffExists(txtSuspendTradeLineRep.getText());
					
					// If this repCode is okay
					if(checkStaff.getRepCode() != null) {
						txtSuspendTradeLineRep.setEditable(false);
						txtSuspendTradeLineRep.setEnabled(false);
						
						if(order == null){
							order = new SuspendedOrder();
							order.setOrderDate(new Date());
							order.setSaleType("Trade");
							order.setRepNo(txtSuspendTradeLineRep.getText());
						}

						try {

							// Check if an item is entered
							if(!txtSuspendTradeLineDesc.getText().isEmpty()) {
								// Check that the Quantity entered is an Integer and > 0.
								if(Integer.parseInt(txtSuspendTradeLineQty.getText()) > 0) {
															
									// Create a TradeOrderLine and call the addTradeOrderLine() method to calculate it's values and save it to the arraylist  
									orderLine = new SuspendedOrderLine();

									orderLine.addTradeOrderLine(txtSuspendTradeLineCode, txtSuspendTradeLineDesc, txtSuspendTradeLineQty, txtSuspendTradeLinePrice, lblTradeGrossProfitValue, 
										lblTradeGrossProfitPercent, order, txtTradeLineCost, getInvoiceSettings().getReceiptVatRate(), false);
									
									// Clear the order textfield boxes and disable the quantity textfield and make the ENTER button invisible
									txtSuspendTradeLineCode.setText("");
									txtSuspendTradeLineDesc.setText("");
									txtSuspendTradeLinePrice.setText("");
									txtSuspendTradeLineQty.setText("");
									txtSuspendTradeLineQty.setEnabled(false);
									btnSuspendTradeNewOrderLine.setVisible(false);
										
									// Make the Trade: Current Order, Print Order, PaymentMethod, GrossProfit, Rounding, TradeDetails and NewOrder panels visible
									panelSuspendTradeCurrentOrder.setVisible(true);
									panelSuspendTradePrintOrder.setVisible(true);
									panelSuspendTradeGrossProfit.setVisible(true);
									panelSuspendTradeRounding.setVisible(true);
									panelSuspendTradeDetails.setVisible(true);
									panelSuspendTradeNewOrder.setVisible(true);
									
									refreshCurrentOrderTable();
									
									// Set the focus back to the item description
									txtSuspendTradeLineDesc.requestFocus();

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
		btnSuspendTradeNewOrderLine.setFont(new Font("Georgia", Font.BOLD, 16));
		btnSuspendTradeNewOrderLine.setBounds(964, 17, 167, 38);
		btnSuspendTradeNewOrderLine.setVisible(false);
		panelSuspendTradeOrderLine.add(btnSuspendTradeNewOrderLine);
		
		panelSuspendTradeItemDescriptionChoice = new JPanel();
		panelSuspendTradeItemDescriptionChoice.setBounds(350, 91, 338, 398);
		getContentPane().add(panelSuspendTradeItemDescriptionChoice);
		panelSuspendTradeItemDescriptionChoice.setLayout(null);
		
		JScrollPane scrlSuspendTradeItemDescription = new JScrollPane();
		scrlSuspendTradeItemDescription.setBounds(0, 0, 338, 398);
		panelSuspendTradeItemDescriptionChoice.add(scrlSuspendTradeItemDescription);
		
		// When creating the JTable for the description dropdown I over-ride the isCellEditable method to make the table un-editable
		tblSuspendTradeItemDescription = new JTable(){
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
		scrlSuspendTradeItemDescription.setViewportView(tblSuspendTradeItemDescription);
		
		tblSuspendTradeItemDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// If enter pressed call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make ENTER visible 
				if(arg0.getKeyCode() == 10) {
					ordLineTradeItemSelected();					
				}
			}
		});
		tblSuspendTradeItemDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
								
				// Call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make the enter button visible
				ordLineTradeItemSelected();
			}
		});
		panelSuspendTradeItemDescriptionChoice.setVisible(false);
		
		panelSuspendTradeCurrentOrder = new JPanel();
		panelSuspendTradeCurrentOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendTradeCurrentOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeCurrentOrder.setBounds(28, 138, 800, 447);
		getContentPane().add(panelSuspendTradeCurrentOrder);
		panelSuspendTradeCurrentOrder.setLayout(null);
		panelSuspendTradeCurrentOrder.setVisible(false);
		
		JLabel lblSuspendTradeCurrentTransaction = new JLabel("SUSPENDED");
		lblSuspendTradeCurrentTransaction.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendTradeCurrentTransaction.setBounds(10, 11, 218, 21);
		panelSuspendTradeCurrentOrder.add(lblSuspendTradeCurrentTransaction);
		
		JLabel lblSuspendTradeTotalPriceText = new JLabel("TOTAL INC-VAT");
		lblSuspendTradeTotalPriceText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendTradeTotalPriceText.setBounds(634, 11, 156, 31);
		panelSuspendTradeCurrentOrder.add(lblSuspendTradeTotalPriceText);
		
		lblSuspendTradeTotalPrice = new JLabel("0.00", SwingConstants.RIGHT);
		lblSuspendTradeTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendTradeTotalPrice.setBounds(634, 37, 156, 31);
		panelSuspendTradeCurrentOrder.add(lblSuspendTradeTotalPrice);
		
		JLabel lblSuspendTradeTotalExVatText = new JLabel("TOTAL EX-VAT");
		lblSuspendTradeTotalExVatText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendTradeTotalExVatText.setBounds(297, 11, 148, 31);
		panelSuspendTradeCurrentOrder.add(lblSuspendTradeTotalExVatText);
		
		lblSuspendTradeTotalExVat = new JLabel("0.00", SwingConstants.RIGHT);
		lblSuspendTradeTotalExVat.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendTradeTotalExVat.setBounds(297, 37, 144, 31);
		panelSuspendTradeCurrentOrder.add(lblSuspendTradeTotalExVat);
		
		JScrollPane scrlSuspendTradeOrder = new JScrollPane();
		scrlSuspendTradeOrder.setBounds(10, 68, 780, 368);
		panelSuspendTradeCurrentOrder.add(scrlSuspendTradeOrder);
		
		tblSuspendTradeOrder = new JTable() {
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
		tblSuspendTradeOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(order.getOrderList().size() > 1) {
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblSuspendTradeOrder.getSelectedRow();
					
					// We use this row to find the element in the arraylist that the user selected
					order.getOrderList().remove(row);
					
					// Recalculate everything for the Order
					order.recalculateAfterDelete(getInvoiceSettings().getReceiptVatRate(), lblTradeGrossProfitValue, lblTradeGrossProfitPercent);
					
					// Load the current order lines onto the screen
					refreshCurrentOrderTable();
				
				} else{
					
					startNewOrder();
				}
			}
		});
		tblSuspendTradeOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSuspendTradeOrder.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tblSuspendTradeOrder.setShowGrid(false);
		tblSuspendTradeOrder.setShowHorizontalLines(false);
		tblSuspendTradeOrder.setShowVerticalLines(false);
		tblSuspendTradeOrder.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));

		scrlSuspendTradeOrder.setViewportView(tblSuspendTradeOrder);
		
		JLabel lblSuspendTradeCurrentTransaction2 = new JLabel("TRADE ORDER");
		lblSuspendTradeCurrentTransaction2.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendTradeCurrentTransaction2.setBounds(10, 35, 179, 21);
		panelSuspendTradeCurrentOrder.add(lblSuspendTradeCurrentTransaction2);
		
		panelSuspendTradeRounding = new JPanel();
		panelSuspendTradeRounding.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeRounding.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendTradeRounding.setBounds(854, 160, 164, 81);
		getContentPane().add(panelSuspendTradeRounding);
		panelSuspendTradeRounding.setLayout(null);
		panelSuspendTradeRounding.setVisible(false);
		
		JLabel lblTradeRoundText = new JLabel("Round");
		lblTradeRoundText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradeRoundText.setBounds(50, 11, 55, 17);
		panelSuspendTradeRounding.add(lblTradeRoundText);
		
		JLabel lblTradeRoundUp = new JLabel("New label");
		lblTradeRoundUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String beforeRound = order.getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(String.valueOf(Float.parseFloat(beforeRound.substring(0, theDot)) + 1));
				order.setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				order.setRounding(String.valueOf(rounding));

				// Save the rounding and post Rounding values if the invoice has been printed
				if(order.getReceiptNo() != 0) {
					try {
						
						getOrder().setBalance(Float.valueOf(getOrder().getTotalPostRounding()));
						getSuspendedDao().updateRoundingHeader(rounding, order.getTotalPostRounding(), order.getBalance(), order.getReceiptNo());
	
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblSuspendTradeTotalPrice.setText("\u20AC " + order.getTotalPostRounding());
			}
		});
		lblTradeRoundUp.setBounds(36, 39, 35, 31);
		lblTradeRoundUp.setIcon(imageIconUpArrow);
		panelSuspendTradeRounding.add(lblTradeRoundUp);
		
		JLabel lblTradeRoundDown = new JLabel("New label");
		lblTradeRoundDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String beforeRound = order.getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(beforeRound.substring(0, theDot));
				order.setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				order.setRounding(String.valueOf(rounding));

				// Save the rounding and post Rounding values if the invoice has been printed
				if(order.getReceiptNo() != 0) {
					try {
						
						getOrder().setBalance(Float.valueOf(getOrder().getTotalPostRounding()));
						getSuspendedDao().updateRoundingHeader(rounding, order.getTotalPostRounding(), order.getBalance(), order.getReceiptNo());
	
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblSuspendTradeTotalPrice.setText("\u20AC " + order.getTotalPostRounding());
			}
		});
		lblTradeRoundDown.setBounds(92, 39, 35, 31);
		lblTradeRoundDown.setIcon(imageIconDownArrow);
		panelSuspendTradeRounding.add(lblTradeRoundDown);
		
		panelSuspendTradeGrossProfit = new JPanel();
		panelSuspendTradeGrossProfit.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeGrossProfit.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendTradeGrossProfit.setBounds(855, 443, 163, 142);
		getContentPane().add(panelSuspendTradeGrossProfit);
		panelSuspendTradeGrossProfit.setLayout(null);
		panelSuspendTradeGrossProfit.setVisible(false);
		
		JLabel lblTradeGrossProfitText = new JLabel("Gross Profit");
		lblTradeGrossProfitText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblTradeGrossProfitText.setBounds(27, 23, 111, 24);
		panelSuspendTradeGrossProfit.add(lblTradeGrossProfitText);
		
		lblTradeGrossProfitValue = new JLabel("\u20AC 0.00");
		lblTradeGrossProfitValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTradeGrossProfitValue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTradeGrossProfitValue.setBounds(25, 58, 102, 24);
		panelSuspendTradeGrossProfit.add(lblTradeGrossProfitValue);
		
		lblTradeGrossProfitPercent = new JLabel("0.00 %");
		lblTradeGrossProfitPercent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTradeGrossProfitPercent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTradeGrossProfitPercent.setBounds(25, 92, 102, 24);
		panelSuspendTradeGrossProfit.add(lblTradeGrossProfitPercent);
		
		panelSuspendTradeNewOrder = new JPanel();
		panelSuspendTradeNewOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeNewOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendTradeNewOrder.setBounds(1046, 113, 199, 71);
		getContentPane().add(panelSuspendTradeNewOrder);
		panelSuspendTradeNewOrder.setLayout(null);
		panelSuspendTradeNewOrder.setVisible(false);
		
		JButton btnTradeNewOrder = new JButton("NEW ORDER");
		btnTradeNewOrder.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		btnTradeNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startNewOrder();
			}
		});
		btnTradeNewOrder.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradeNewOrder.setBounds(17, 17, 167, 38);
		panelSuspendTradeNewOrder.add(btnTradeNewOrder);
		
		panelSuspendTradePrintOrder = new JPanel();
		panelSuspendTradePrintOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradePrintOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendTradePrintOrder.setBounds(1046, 204, 199, 71);
		getContentPane().add(panelSuspendTradePrintOrder);
		panelSuspendTradePrintOrder.setLayout(null);
		panelSuspendTradePrintOrder.setVisible(false);
		
		JButton btnTradePrintOrder = new JButton("PRINT ORDER");
		btnTradePrintOrder.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		btnTradePrintOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				order.setOrderLinesToPrint(0);
				
				try {
					order.printOrder(getInvoiceSettings(), null, null, null);
					getSuspendedOrdersScreen().refreshSuspendedOrderHeaders();
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnTradePrintOrder.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradePrintOrder.setBounds(17, 17, 167, 38);
		panelSuspendTradePrintOrder.add(btnTradePrintOrder);
		
		panelSuspendTradeDetails = new JPanel();
		panelSuspendTradeDetails.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeDetails.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendTradeDetails.setBounds(1046, 295, 199, 71);
		getContentPane().add(panelSuspendTradeDetails);
		panelSuspendTradeDetails.setLayout(null);
		panelSuspendTradeDetails.setVisible(false);
		
		JButton btnTradeCustDetails = new JButton("CUST DETAILS");
		btnTradeCustDetails.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		btnTradeCustDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CustomerDetails(order);
			}
		});
		btnTradeCustDetails.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradeCustDetails.setBounds(17, 17, 167, 38);
		panelSuspendTradeDetails.add(btnTradeCustDetails);
		
		JLabel lblSuspendTradeLogo = new JLabel("New label");
		lblSuspendTradeLogo.setBounds(1046, 385, 199, 200);
		// Put Logo image on order tab by adding an image to the Logo Label
		lblSuspendTradeLogo.setIcon(imageIcon);
		getContentPane().add(lblSuspendTradeLogo);

	}
	
	
	/**
	 * This method takes the item selected from the item dropdown and fills textfields with it's values (code, description,price), it also 
	 * retrieves the item's cost price and enables the quantity field.
	 */
	public void ordLineTradeItemSelected() {
		try {
		
			// We use the table's getSelectedRow() method to find the row that the User selected. 
			int row = tblSuspendTradeItemDescription.getSelectedRow();
	
			// Then we can get the description of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
			// row and column that we require.
			// This returns an Object so we get the toString of it.
			String descString = tblSuspendTradeItemDescription.getModel().getValueAt(row, 0).toString();
			
			Item selectedItem = getProductDao().findItemByDesc(descString);
			
			if(selectedItem.getItemCode() != null){
				// Put selected item's code, description and price into the oderline entry fields and enable the quantity field and make the enter button visible. 
				txtSuspendTradeLineCode.setText(selectedItem.getItemCode());
				txtSuspendTradeLineDesc.setText(selectedItem.getItemDescription());
				txtSuspendTradeLinePrice.setText(Utilities.stringToDec( String.valueOf(selectedItem.getTradePrice()) ));
				txtTradeLineCost = selectedItem.getCostPrice();
				txtSuspendTradeLineQty.setEnabled(true);
			}
			
			txtSuspendTradeLineQty.requestFocus();
			panelSuspendTradeItemDescriptionChoice.setVisible(false);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	
	/**
	 * This method loads the current trade order lines into the current order table that the user sees on the Suspended Trade Order Screen.
	 */
	public void refreshCurrentOrderTable(){
		
		String columns[] = {"ITEM", "DESCRIPTION", "QTY", "PRICE", "TOTAL"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		
		for(SuspendedOrderLine o : order.getOrderList()){
			String tableCode= o.getItemCode();
			String tableDesc = o.getItemDescription();
			String tableQty = o.getOrderQty();
			String tableItemPrice = o.getItemPrice();
			String tableOrderPrice = o.getValueExVat();
			Object[] line = {tableCode, tableDesc, tableQty, tableItemPrice, tableOrderPrice};
			tableModel.addRow(line);
		}
		tblSuspendTradeOrder.setModel(tableModel);
		lblSuspendTradeTotalPrice.setText("\u20AC " + String.valueOf(order.getTotalPreRounding()));
		lblSuspendTradeTotalExVat.setText("\u20AC " + order.getTotalExVat());
	
	
		// Set the column widths for the table
		tblSuspendTradeOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnModelOrder = tblSuspendTradeOrder.getColumnModel();
		columnModelOrder.getColumn(0).setPreferredWidth(90);
		columnModelOrder.getColumn(1).setPreferredWidth(479);
		columnModelOrder.getColumn(2).setPreferredWidth(40);
		columnModelOrder.getColumn(3).setPreferredWidth(70);	
		columnModelOrder.getColumn(4).setPreferredWidth(83);

	}

	
	/**
	 * This method removes the current Trade Order, clears all of the screen's textfields and hides panels so as to start a new Trade Order
	 */
	public void startNewOrder(){
		
		// Make the current TradeCurrentOrder, TradePrintOrder, TradePaymentMethod and the TradeGrossProfit panel invisible
		panelSuspendTradeCurrentOrder.setVisible(false);
		panelSuspendTradePrintOrder.setVisible(false);
		panelSuspendTradeGrossProfit.setVisible(false);
		
		// Remove the current order from the trade order object
		order = null;
		
		// Blank the current order line entry fields
		txtSuspendTradeLineCode.setText(null);
		txtSuspendTradeLineDesc.setText(null);
		txtSuspendTradeLinePrice.setText(null);
		txtSuspendTradeLineQty.setText(null);
		
		// Disable quantity field and make ENTER button invisible - because they may be accessible when NEW ORDER button pressed 
		txtSuspendTradeLineQty.setEnabled(false);
		btnSuspendTradeNewOrderLine.setVisible(false);
		
		// Make the TradeCurrentOrder, TradePrintOrder, TradePaymentMethod, TradeGrossProfit, TradeRounding, TradeDetails and TradeNewOrder panels invisible
		panelSuspendTradeCurrentOrder.setVisible(false);
		panelSuspendTradePrintOrder.setVisible(false);
		panelSuspendTradeGrossProfit.setVisible(false);
		panelSuspendTradeRounding.setVisible(false);
		panelSuspendTradeDetails.setVisible(false);
		panelSuspendTradeNewOrder.setVisible(false);
		
		// Blank the Trade Rep Code field, enable it and make it editable
		txtSuspendTradeLineRep.setEditable(true);
		txtSuspendTradeLineRep.setEnabled(true);
		txtSuspendTradeLineRep.setText("");

	}
}