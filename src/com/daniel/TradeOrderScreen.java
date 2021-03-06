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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.daniel.dao.OrderDao;
import com.daniel.dao.OrderDaoImpl;
import com.daniel.dao.ProductDao;
import com.daniel.dao.ProductDaoImpl;
import com.daniel.dao.StaffDao;
import com.daniel.dao.StaffDaoImpl;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.Item;
import com.daniel.model.Staff;
import com.daniel.model.TradeOrder;
import com.daniel.model.TradeOrderLine;
import com.daniel.utilities.Utilities;

import javax.swing.ListSelectionModel;


/**
 * This class designs and handles all of the operations that are performed on the Trade Order Tab of the main screen's tabbed pane.
 * @author Robert Forde
 *
 */
public class TradeOrderScreen extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InvoiceSettings invoiceSettings;
	private TradeOrder tradeOrder;
	private TradeOrderLine tradeOrderLine;
	
	private JPanel panelTradeCurrentOrder;
	private JPanel panelTradePaymentMethod;
	private JPanel panelTradeRounding; 
	private JPanel panelTradeGrossProfit; 
	private JPanel panelTradeNewOrder; 
	private JPanel panelTradePrintOrder; 
	private JPanel panelTradeDetails;
	private JLabel lblTradeTotalPrice;
	private JLabel lblTradeTotalExVat;
	private JPanel panelTradeItemDescriptionChoice;
	
	private JTable tblTradeItemDescription;
	private JTable tblTradeOrder;
	
	private JButton btnTradeNewOrderLine;
	
	private JRadioButton rdbtnTradeCash;
	private JRadioButton rdbtnTradeCreditCard;
	private JRadioButton rdbtnTradeCheque;
	private JRadioButton rdbtnTradeBankTransfer;
	
	private JLabel lblTradeGrossProfitValue;
	private JLabel lblTradeGrossProfitPercent;
	
	private JTextField txtTradeLineRep;
	private JTextField txtTradeLineCode;
	private JTextField txtTradeLineDesc;
	private JTextField txtTradeLinePrice;
	private JTextField txtTradeLineQty;

	private TableColumnModel columnModelOrder;
	
	private float txtTradeLineCost;

	private ProductDao productDao;
	private StaffDao staffDao;
	private OrderDao orderDao;

	
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
	
	public JPanel getPanelTradeCurrentOrder() {
		return panelTradeCurrentOrder;
	}

	public void setPanelTradeCurrentOrder(JPanel panelTradeCurrentOrder) {
		this.panelTradeCurrentOrder = panelTradeCurrentOrder;
	}

	public JPanel getPanelTradePaymentMethod() {
		return panelTradePaymentMethod;
	}

	public void setPanelTradePaymentMethod(JPanel panelTradePaymentMethod) {
		this.panelTradePaymentMethod = panelTradePaymentMethod;
	}

	public JPanel getPanelTradeGrossProfit() {
		return panelTradeGrossProfit;
	}

	public void setPanelTradeGrossProfit(JPanel panelTradeGrossProfit) {
		this.panelTradeGrossProfit = panelTradeGrossProfit;
	}

	public JPanel getPanelTradeNewOrder() {
		return panelTradeNewOrder;
	}

	public void setPanelTradeNewOrder(JPanel panelTradeNewOrder) {
		this.panelTradeNewOrder = panelTradeNewOrder;
	}

	public JPanel getPanelTradePrintOrder() {
		return panelTradePrintOrder;
	}

	public JPanel getPanelTradeRounding() {
		return panelTradeRounding;
	}

	public JLabel getLblTradeTotalPrice() {
		return lblTradeTotalPrice;
	}

	public void setLblTradeTotalPrice(JLabel lblTradeTotalPrice) {
		this.lblTradeTotalPrice = lblTradeTotalPrice;
	}

	public JLabel getLblTradeTotalExVat() {
		return lblTradeTotalExVat;
	}

	public void setLblTradeTotalExVat(JLabel lblTradeTotalExVat) {
		this.lblTradeTotalExVat = lblTradeTotalExVat;
	}

	public void setPanelTradeRounding(JPanel panelTradeRounding) {
		this.panelTradeRounding = panelTradeRounding;
	}

	public JPanel getPanelTradeDetails() {
		return panelTradeDetails;
	}

	public void setPanelTradeDetails(JPanel panelTradeDetails) {
		this.panelTradeDetails = panelTradeDetails;
	}

	public void setPanelTradePrintOrder(JPanel panelTradePrintOrder) {
		this.panelTradePrintOrder = panelTradePrintOrder;
	}
	
	public JLabel getLblTradeGrossProfitValue() {
		return lblTradeGrossProfitValue;
	}

	public void setLblTradeGrossProfitValue(JLabel lblTradeGrossProfitValue) {
		this.lblTradeGrossProfitValue = lblTradeGrossProfitValue;
	}

	public JLabel getLblTradeGrossProfitPercent() {
		return lblTradeGrossProfitPercent;
	}

	public void setLblTradeGrossProfitPercent(JLabel lblTradeGrossProfitPercent) {
		this.lblTradeGrossProfitPercent = lblTradeGrossProfitPercent;
	}
	
	public JTextField getTxtTradeLineRep() {
		return txtTradeLineRep;
	}

	public void setTxtTradeLineRep(JTextField txtTradeLineRep) {
		this.txtTradeLineRep = txtTradeLineRep;
	}

	
	/**
	 * This constructs the Trade Order Screen that is used for entering Trade Orders
	 * @param tabbedPane This is tab on the the main screen of the application and is passed in so we can add this screen to it
	 * @param invoiceSettings This are the current print settings, vat rate etc. that is passed in for use by the retail order screen operations
	 */
	public TradeOrderScreen(JTabbedPane tabbedPane, InvoiceSettings invoiceSettings){
		
		setProductDao(Dan.ctx.getBean("productDaoImpl", ProductDaoImpl.class));
		setStaffDao(Dan.ctx.getBean("staffDaoImpl", StaffDaoImpl.class));
		setOrderDao(Dan.ctx.getBean("orderDaoImpl", OrderDaoImpl.class));
		
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
		
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Trade Order", null, this, null);
		setLayout(null);
		
		JPanel panelTradeOrderLine = new JPanel();
		panelTradeOrderLine.setForeground(Color.BLACK);
		panelTradeOrderLine.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradeOrderLine.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeOrderLine.setBounds(28, 21, 1217, 71);
		this.add(panelTradeOrderLine);
		panelTradeOrderLine.setLayout(null);
		
		JLabel lblTradeRepCodeText = new JLabel("Rep Code");
		lblTradeRepCodeText.setForeground(Color.BLACK);
		lblTradeRepCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradeRepCodeText.setBounds(34, 9, 96, 18);
		panelTradeOrderLine.add(lblTradeRepCodeText);
		
		txtTradeLineRep = new JTextField();
		txtTradeLineRep.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradeLineRep.setBounds(34, 31, 86, 28);
		txtTradeLineRep.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeOrderLine.add(txtTradeLineRep);
		txtTradeLineRep.setColumns(10);
		
		JLabel lblTradeItemCodeText = new JLabel("Item Code");
		lblTradeItemCodeText.setForeground(Color.BLACK);
		lblTradeItemCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradeItemCodeText.setBounds(152, 9, 132, 18);
		panelTradeOrderLine.add(lblTradeItemCodeText);
		
		txtTradeLineCode = new JTextField();
		txtTradeLineCode.setEditable(false);
		txtTradeLineCode.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradeLineCode.setBounds(152, 31, 132, 28);
		txtTradeLineCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeOrderLine.add(txtTradeLineCode);
		txtTradeLineCode.setColumns(10);
		
		JLabel lblTradeItemDescText = new JLabel("Item Description");
		lblTradeItemDescText.setForeground(Color.BLACK);
		lblTradeItemDescText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradeItemDescText.setBounds(318, 9, 175, 18);
		panelTradeOrderLine.add(lblTradeItemDescText);
		
		txtTradeLineDesc = new JTextField();
		txtTradeLineDesc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				
				// Disable the normal tab key focus on the Item Description textbox
				txtTradeLineDesc.setFocusTraversalKeysEnabled(false);
				
				// Uppercase the entered description
				txtTradeLineDesc.setText(txtTradeLineDesc.getText().toUpperCase());
				
				// Make ENTER button and the list of descriptions invisible
				panelTradeItemDescriptionChoice.setVisible(false);
				btnTradeNewOrderLine.setVisible(false);
				
				if (txtTradeLineDesc.getText().equals("") == false) {  
					// Search in database for items that have this exact description in database and if found enable qty textfield and fill in code and trade price
					try {
						
						// Find item based on it's description
						Item foundItem = getProductDao().findItemByDesc(txtTradeLineDesc.getText());

						if(foundItem.getItemCode() != null){
							txtTradeLineCode.setText(foundItem.getItemCode());
							txtTradeLinePrice.setText(Utilities.stringToDec(String.valueOf(foundItem.getTradePrice())));
							txtTradeLineCost = foundItem.getCostPrice();
							txtTradeLineQty.setEnabled(true);
						} else {
							txtTradeLineCode.setText("");
							txtTradeLinePrice.setText("");
							txtTradeLineQty.setText("");
							txtTradeLineQty.setEnabled(false);

							// Exact match not found so bring up list of descriptions that contain this description and are not set as deleted
							List<Item> itemList = getProductDao().findPossibleItemMatches(txtTradeLineDesc.getText().toUpperCase());
							
							if(itemList.size() != 0) {
								
								String columns[] = {"DESCRIPTION"};
								DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
								
								for(Item item : itemList){
									String tableDesc = item.getItemDescription();
									Object[] line = {tableDesc};
									tableModel.addRow(line);
								}
								
								tblTradeItemDescription.setModel(tableModel);
								tblTradeItemDescription.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
								TableColumnModel columnModel = tblTradeItemDescription.getColumnModel();
								columnModel.getColumn(0).setPreferredWidth(340);
								panelTradeItemDescriptionChoice.setVisible(true);
								
								// Set the tab key to the item description possible matches table
								if(arg0.getKeyCode() == KeyEvent.VK_TAB){
									tblTradeItemDescription.requestFocus();
								} 
									
							} else {
								txtTradeLineCode.setText("");
								txtTradeLinePrice.setText("");
								txtTradeLineQty.setText("");
								txtTradeLineQty.setEnabled(false);
							}
								
						}
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}else {
					// Make ENTER button and the list of descriptions invisible
					panelTradeItemDescriptionChoice.setVisible(false);
					btnTradeNewOrderLine.setVisible(false);
				}
			}
		});
		txtTradeLineDesc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradeLineDesc.setBounds(318, 31, 341, 28);
		txtTradeLineDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeOrderLine.add(txtTradeLineDesc);
		txtTradeLineDesc.setColumns(10);
		
		JLabel lblTradeItemPriceText = new JLabel("Item Price");
		lblTradeItemPriceText.setForeground(Color.BLACK);
		lblTradeItemPriceText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradeItemPriceText.setBounds(678, 9, 106, 18);
		panelTradeOrderLine.add(lblTradeItemPriceText);
		
		txtTradeLinePrice = new JTextField();
		txtTradeLinePrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				btnTradeNewOrderLine.setVisible(true);
			}
		});
		txtTradeLinePrice.setEditable(false);
		txtTradeLinePrice.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradeLinePrice.setBounds(678, 31, 132, 28);
		txtTradeLinePrice.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeOrderLine.add(txtTradeLinePrice);
		txtTradeLinePrice.setColumns(10);
		
		txtTradeLineQty = new JTextField();
		txtTradeLineQty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnTradeNewOrderLine.setVisible(true);
			}
		});
		txtTradeLineQty.setEnabled(false);
		txtTradeLineQty.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradeLineQty.setBounds(828, 31, 57, 28);
		txtTradeLineQty.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeOrderLine.add(txtTradeLineQty);
		txtTradeLineQty.setColumns(10);
		
		JLabel lblTradeItemQtyText = new JLabel("Quantity");
		lblTradeItemQtyText.setForeground(Color.BLACK);
		lblTradeItemQtyText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradeItemQtyText.setBounds(828, 9, 75, 18);
		panelTradeOrderLine.add(lblTradeItemQtyText);
		
		btnTradeNewOrderLine = new JButton("ENTER");
		btnTradeNewOrderLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		btnTradeNewOrderLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// First check that the Rep is valid
				try {
					Staff checkStaff = getStaffDao().staffExists(txtTradeLineRep.getText());
					
					// If this repCode is okay
					if(checkStaff.getRepCode() != null) {
						txtTradeLineRep.setEditable(false);
						txtTradeLineRep.setEnabled(false);
						
						if(tradeOrder == null){
							tradeOrder = new TradeOrder();
							tradeOrder.setOrderDate(new Date());
							tradeOrder.setSaleType("Trade");
							tradeOrder.setRepNo(txtTradeLineRep.getText());
						}

						try {

							// Check if an item is entered
							if(!txtTradeLineDesc.getText().isEmpty()) {
								// Check that the Quantity entered is an Integer and > 0.
								if(Integer.parseInt(txtTradeLineQty.getText()) > 0) {
															
									// Create a TradeOrderLine and call the addTradeOrderLine() method to calculate it's values and save it to the arraylist  
									tradeOrderLine = new TradeOrderLine();

									tradeOrderLine.addTradeOrderLine(txtTradeLineCode, txtTradeLineDesc, txtTradeLineQty, txtTradeLinePrice, lblTradeGrossProfitValue, 
										lblTradeGrossProfitPercent, tradeOrder, txtTradeLineCost, getInvoiceSettings());
									
									// Clear the order textfield boxes and disable the quantity textfield and make the ENTER button invisible
									txtTradeLineCode.setText("");
									txtTradeLineDesc.setText("");
									txtTradeLinePrice.setText("");
									txtTradeLineQty.setText("");
									txtTradeLineQty.setEnabled(false);
									btnTradeNewOrderLine.setVisible(false);
										
									// Make the Trade: Current Order, Print Order, PaymentMethod, GrossProfit, Rounding, TradeDetails and NewOrder panels visible
									panelTradeCurrentOrder.setVisible(true);
									panelTradePrintOrder.setVisible(true);
									panelTradePaymentMethod.setVisible(true);
									panelTradeGrossProfit.setVisible(true);
									panelTradeRounding.setVisible(true);
									panelTradeDetails.setVisible(true);
									panelTradeNewOrder.setVisible(true);
									
									refreshCurrentOrderTable();
									
									// Set the focus back to the item description
									txtTradeLineDesc.requestFocus();

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
		btnTradeNewOrderLine.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradeNewOrderLine.setBounds(964, 17, 167, 38);
		btnTradeNewOrderLine.setVisible(false);
		panelTradeOrderLine.add(btnTradeNewOrderLine);
		
		panelTradeItemDescriptionChoice = new JPanel();
		panelTradeItemDescriptionChoice.setBounds(350, 91, 338, 398);
		this.add(panelTradeItemDescriptionChoice);
		panelTradeItemDescriptionChoice.setLayout(null);
		
		JScrollPane scrlTradeItemDescription = new JScrollPane();
		scrlTradeItemDescription.setBounds(0, 0, 338, 398);
		panelTradeItemDescriptionChoice.add(scrlTradeItemDescription);
		
		// When creating the JTable for the description dropdown I over-ride the isCellEditable method to make the table un-editable
		tblTradeItemDescription = new JTable(){
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
		tblTradeItemDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrlTradeItemDescription.setViewportView(tblTradeItemDescription);
		
		tblTradeItemDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// If enter pressed call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make ENTER visible 
				if(arg0.getKeyCode() == 10) {
					ordLineTradeItemSelected();					
				}
			}
		});
		tblTradeItemDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
								
				// Call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make the enter button visible
				ordLineTradeItemSelected();
			}
		});
		panelTradeItemDescriptionChoice.setVisible(false);
		
		panelTradeCurrentOrder = new JPanel();
		panelTradeCurrentOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradeCurrentOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeCurrentOrder.setBounds(28, 138, 800, 447);
		this.add(panelTradeCurrentOrder);
		panelTradeCurrentOrder.setLayout(null);
		panelTradeCurrentOrder.setVisible(false);
		
		JLabel lblTradeCurrentTransaction = new JLabel("TRADE ORDER");
		lblTradeCurrentTransaction.setFont(new Font("Georgia", Font.BOLD, 18));
		lblTradeCurrentTransaction.setBounds(10, 11, 179, 31);
		panelTradeCurrentOrder.add(lblTradeCurrentTransaction);
		
		JLabel lblTradeTotalPriceText = new JLabel("TOTAL INC-VAT");
		lblTradeTotalPriceText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblTradeTotalPriceText.setBounds(634, 11, 156, 31);
		panelTradeCurrentOrder.add(lblTradeTotalPriceText);
		
		lblTradeTotalPrice = new JLabel("0.00", SwingConstants.RIGHT);
		lblTradeTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTradeTotalPrice.setBounds(634, 37, 156, 31);
		panelTradeCurrentOrder.add(lblTradeTotalPrice);
		
		JLabel lblTradeTotalExVatText = new JLabel("TOTAL EX-VAT");
		lblTradeTotalExVatText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblTradeTotalExVatText.setBounds(297, 11, 148, 31);
		panelTradeCurrentOrder.add(lblTradeTotalExVatText);
		
		lblTradeTotalExVat = new JLabel("0.00", SwingConstants.RIGHT);
		lblTradeTotalExVat.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTradeTotalExVat.setBounds(297, 37, 144, 31);
		panelTradeCurrentOrder.add(lblTradeTotalExVat);
		
		JScrollPane scrlTradeOrder = new JScrollPane();
		scrlTradeOrder.setBounds(10, 68, 780, 368);
		panelTradeCurrentOrder.add(scrlTradeOrder);
		
		tblTradeOrder = new JTable() {
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
		tblTradeOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(tradeOrder.getOrderList().size() > 1) {
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblTradeOrder.getSelectedRow();
					
					// We use this row to find the element in the arraylist that the user selected
					tradeOrder.getOrderList().remove(row);
					
					// Recalculate everything for the Order
					tradeOrder.recalculateAfterDelete(getInvoiceSettings().getReceiptVatRate(), lblTradeGrossProfitValue, lblTradeGrossProfitPercent);
					
					// Load the current order lines onto the screen
					refreshCurrentOrderTable();
				
				} else{
					
					startNewOrder();
				}
			}
		});
		tblTradeOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblTradeOrder.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tblTradeOrder.setShowGrid(false);
		tblTradeOrder.setShowHorizontalLines(false);
		tblTradeOrder.setShowVerticalLines(false);
		tblTradeOrder.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));

		scrlTradeOrder.setViewportView(tblTradeOrder);
		
		panelTradePaymentMethod = new JPanel();
		panelTradePaymentMethod.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradePaymentMethod.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradePaymentMethod.setBounds(855, 113, 163, 118);
		this.add(panelTradePaymentMethod);
		panelTradePaymentMethod.setLayout(null);
		panelTradePaymentMethod.setVisible(false);
		
		rdbtnTradeCash = new JRadioButton("Cash");
		rdbtnTradeCash.setSelected(true);
		rdbtnTradeCash.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnTradeCash.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnTradeCash.setBounds(25, 7, 109, 23);
		panelTradePaymentMethod.add(rdbtnTradeCash);
		
		rdbtnTradeCreditCard = new JRadioButton("Credit Card");
		rdbtnTradeCreditCard.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnTradeCreditCard.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnTradeCreditCard.setBounds(25, 33, 132, 23);
		panelTradePaymentMethod.add(rdbtnTradeCreditCard);
		
		rdbtnTradeCheque = new JRadioButton("Cheque");
		rdbtnTradeCheque.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnTradeCheque.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnTradeCheque.setBounds(25, 59, 109, 23);
		panelTradePaymentMethod.add(rdbtnTradeCheque);
		
		rdbtnTradeBankTransfer = new JRadioButton("Transfer");
		rdbtnTradeBankTransfer.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnTradeBankTransfer.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnTradeBankTransfer.setBounds(25, 85, 109, 23);
		panelTradePaymentMethod.add(rdbtnTradeBankTransfer);
		
		// Group the radio buttons.
		ButtonGroup tradePayTypegroup = new ButtonGroup();
		tradePayTypegroup.add(rdbtnTradeCash);
		tradePayTypegroup.add(rdbtnTradeCreditCard);
		tradePayTypegroup.add(rdbtnTradeCheque);
		tradePayTypegroup.add(rdbtnTradeBankTransfer);
		
		panelTradeRounding = new JPanel();
		panelTradeRounding.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeRounding.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradeRounding.setBounds(855, 297, 164, 81);
		this.add(panelTradeRounding);
		panelTradeRounding.setLayout(null);
		panelTradeRounding.setVisible(false);
		
		JLabel lblTradeRoundText = new JLabel("Round");
		lblTradeRoundText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradeRoundText.setBounds(50, 11, 55, 17);
		panelTradeRounding.add(lblTradeRoundText);
		
		JLabel lblTradeRoundUp = new JLabel("New label");
		lblTradeRoundUp.addMouseListener(new MouseAdapter() {
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
				lblTradeTotalPrice.setText("\u20AC " + tradeOrder.getTotalPostRounding());
			}
		});
		lblTradeRoundUp.setBounds(36, 39, 35, 31);
		lblTradeRoundUp.setIcon(imageIconUpArrow);
		panelTradeRounding.add(lblTradeRoundUp);
		
		JLabel lblTradeRoundDown = new JLabel("New label");
		lblTradeRoundDown.addMouseListener(new MouseAdapter() {
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
				lblTradeTotalPrice.setText("\u20AC " + tradeOrder.getTotalPostRounding());
			}
		});
		lblTradeRoundDown.setBounds(92, 39, 35, 31);
		lblTradeRoundDown.setIcon(imageIconDownArrow);
		panelTradeRounding.add(lblTradeRoundDown);
		
		panelTradeGrossProfit = new JPanel();
		panelTradeGrossProfit.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeGrossProfit.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradeGrossProfit.setBounds(855, 443, 163, 142);
		this.add(panelTradeGrossProfit);
		panelTradeGrossProfit.setLayout(null);
		panelTradeGrossProfit.setVisible(false);
		
		JLabel lblTradeGrossProfitText = new JLabel("Gross Profit");
		lblTradeGrossProfitText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblTradeGrossProfitText.setBounds(27, 23, 111, 24);
		panelTradeGrossProfit.add(lblTradeGrossProfitText);
		
		lblTradeGrossProfitValue = new JLabel("\u20AC 0.00");
		lblTradeGrossProfitValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTradeGrossProfitValue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTradeGrossProfitValue.setBounds(25, 58, 102, 24);
		panelTradeGrossProfit.add(lblTradeGrossProfitValue);
		
		lblTradeGrossProfitPercent = new JLabel("0.00 %");
		lblTradeGrossProfitPercent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTradeGrossProfitPercent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTradeGrossProfitPercent.setBounds(25, 92, 102, 24);
		panelTradeGrossProfit.add(lblTradeGrossProfitPercent);
		
		panelTradeNewOrder = new JPanel();
		panelTradeNewOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeNewOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradeNewOrder.setBounds(1046, 113, 199, 71);
		this.add(panelTradeNewOrder);
		panelTradeNewOrder.setLayout(null);
		panelTradeNewOrder.setVisible(false);
		
		JButton btnTradeNewOrder = new JButton("NEW ORDER");
		btnTradeNewOrder.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		btnTradeNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startNewOrder();
			}
		});
		btnTradeNewOrder.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradeNewOrder.setBounds(17, 17, 167, 38);
		panelTradeNewOrder.add(btnTradeNewOrder);
		
		panelTradePrintOrder = new JPanel();
		panelTradePrintOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradePrintOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradePrintOrder.setBounds(1046, 204, 199, 71);
		this.add(panelTradePrintOrder);
		panelTradePrintOrder.setLayout(null);
		panelTradePrintOrder.setVisible(false);
		
		JButton btnTradePrintOrder = new JButton("PRINT ORDER");
		btnTradePrintOrder.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		btnTradePrintOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tradeOrder.setOrderLinesToPrint(0);
				// Set the PayType as per the user's selection
				String paytype = "";
				if(rdbtnTradeCash.isSelected())
					paytype = "Cash";
				else if(rdbtnTradeCreditCard.isSelected())
					paytype = "Credit Card";
				else if(rdbtnTradeCheque.isSelected())
					paytype = "Cheque";
				else
					paytype = "Bank Transfer";
					
				tradeOrder.setPayType(paytype);
				
				try {
					tradeOrder.printOrder(getInvoiceSettings(), null, null, null);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnTradePrintOrder.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradePrintOrder.setBounds(17, 17, 167, 38);
		panelTradePrintOrder.add(btnTradePrintOrder);
		
		panelTradeDetails = new JPanel();
		panelTradeDetails.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradeDetails.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradeDetails.setBounds(1046, 295, 199, 71);
		this.add(panelTradeDetails);
		panelTradeDetails.setLayout(null);
		panelTradeDetails.setVisible(false);
		
		JButton btnTradeCustDetails = new JButton("CUST DETAILS");
		btnTradeCustDetails.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		btnTradeCustDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CustomerDetails(tradeOrder);
			}
		});
		btnTradeCustDetails.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradeCustDetails.setBounds(17, 17, 167, 38);
		panelTradeDetails.add(btnTradeCustDetails);
		
		JLabel lblTradeLogo = new JLabel("New label");
		lblTradeLogo.setBounds(1046, 385, 199, 200);
		// Put Logo image on order tab by adding an image to the Logo Label
		lblTradeLogo.setIcon(imageIcon);
		this.add(lblTradeLogo);

	}

	
	/**
	 * This method takes the item selected from the item dropdown and fills textfields with it's values (code, description,price), it also 
	 * retrieves the item's cost price and enables the quantity field.
	 */
	public void ordLineTradeItemSelected() {
		try {
		
			// We use the table's getSelectedRow() method to find the row that the User selected. 
			int row = tblTradeItemDescription.getSelectedRow();
	
			// Then we can get the description of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
			// row and column that we require.
			// This returns an Object so we get the toString of it.
			String descString = tblTradeItemDescription.getModel().getValueAt(row, 0).toString();
			
			Item selectedItem = getProductDao().findItemByDesc(descString);
			
			if(selectedItem.getItemCode() != null){
				// Put selected item's code, description and price into the oderline entry fields and enable the quantity field and make the enter button visible. 
				txtTradeLineCode.setText(selectedItem.getItemCode());
				txtTradeLineDesc.setText(selectedItem.getItemDescription());
				txtTradeLinePrice.setText(Utilities.stringToDec( String.valueOf(selectedItem.getTradePrice()) ));
				txtTradeLineCost = selectedItem.getCostPrice();
				txtTradeLineQty.setEnabled(true);
			}
			
			txtTradeLineQty.requestFocus();
			panelTradeItemDescriptionChoice.setVisible(false);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	
	/**
	 * This method loads the current trade order lines into the current order table that the user sees on the Trade Order Screen.
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
		tblTradeOrder.setModel(tableModel);
		lblTradeTotalPrice.setText("\u20AC " + String.valueOf(tradeOrder.getTotalPreRounding()));
		lblTradeTotalExVat.setText("\u20AC " + tradeOrder.getTotalExVat());
	
	
		// Set the column widths for the table
		tblTradeOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnModelOrder = tblTradeOrder.getColumnModel();
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
		panelTradeCurrentOrder.setVisible(false);
		panelTradePrintOrder.setVisible(false);
		panelTradePaymentMethod.setVisible(false);
		panelTradeGrossProfit.setVisible(false);
		
		// Remove the current order from the trade order object
		tradeOrder = null;
		
		// Blank the current order line entry fields
		txtTradeLineCode.setText(null);
		txtTradeLineDesc.setText(null);
		txtTradeLinePrice.setText(null);
		txtTradeLineQty.setText(null);
		
		// Disable quantity field and make ENTER button invisible - because they may be accessible when NEW ORDER button pressed 
		txtTradeLineQty.setEnabled(false);
		btnTradeNewOrderLine.setVisible(false);
		
		// Make the TradeCurrentOrder, TradePrintOrder, TradePaymentMethod, TradeGrossProfit, TradeRounding, TradeDetails and TradeNewOrder panels invisible
		panelTradeCurrentOrder.setVisible(false);
		panelTradePrintOrder.setVisible(false);
		panelTradePaymentMethod.setVisible(false);
		panelTradeGrossProfit.setVisible(false);
		panelTradeRounding.setVisible(false);
		panelTradeDetails.setVisible(false);
		panelTradeNewOrder.setVisible(false);
		
		// Set the Payment Type back to Cash
		rdbtnTradeCash.setSelected(true);
		
		// Blank the Trade Rep Code field, enable it and make it editable
		txtTradeLineRep.setEditable(true);
		txtTradeLineRep.setEnabled(true);
		txtTradeLineRep.setText("");
	}
	
}