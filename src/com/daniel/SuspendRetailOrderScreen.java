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
 * This class designs the Suspended Retail Order's screen and handles all of the operations to enable the User to enter a Suspended 
 * Retail Order
 * @author Robert Forde
 *
 */
public class SuspendRetailOrderScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InvoiceSettings invoiceSettings;
	
	private SuspendedOrder order;
	private SuspendedOrderLine orderLine;

	private JPanel panelSuspendRetailOrderLine;
	private JPanel panelSuspendRetailCurrentOrder;
	private JPanel panelSuspendRetailPrintOrder;
	private JPanel panelSuspendRetailDiscount;
	private JPanel panelSuspendRetailGrossProfit;
	private JPanel panelSuspendRetailRounding;
	private JPanel panelSuspendRetailCustDetails;
	private JPanel panelSuspendRetailNewOrder;
	private JPanel panelSuspendRetailItemDescriptionChoice;

	private JButton btnSuspendRetailNewOrderLine;

	private JTable tblSuspendRetailOrder;
	private JTable tblSuspendRetailItemDescription;

	private TableColumnModel columnModelOrder;
	
	private JLabel lblSuspendRetailTotalPrice;
	private JLabel lblSuspendRetailTotalExVat;
	private JLabel lblSuspendRetailGrossProfitValue;
	private JLabel lblSuspendRetailGrossProfitPercent;
	
	private JTextField txtSuspendRetailDiscount;
	private JTextField txtSuspendRetailLineDisc;
	private JTextField txtSuspendRetailLineRep;
	private JTextField txtSuspendRetailLineCode;
	private JTextField txtSuspendRetailLineDesc;
	private JTextField txtSuspendRetailLinePrice;
	private JTextField txtSuspendRetailLineQty;

	private float txtSuspendLineCost;
	private float suspendTradeLinePrice;
	private String oldOverallDiscount;
	
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

	public void setSuspendedDao(SuspendedDao suspendedDao) {
		this.suspendedDao = suspendedDao;
	}

	public SuspendedOrdersScreen getSuspendedOrdersScreen() {
		return suspendedOrdersScreen;
	}

	public void setSuspendedOrdersScreen(SuspendedOrdersScreen suspendedOrdersScreen) {
		this.suspendedOrdersScreen = suspendedOrdersScreen;
	}

	public SuspendedOrder getOrder() {
		return order;
	}

	public void setOrder(SuspendedOrder order) {
		this.order = order;
	}
	
	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}

	
	/**
	 * Constructs the Suspended Retail Order Screen
	 * @param invoiceSettings invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 * @param suspendedOrdersScreen A reference to the Suspended Orders Screen so the balance can be updated
	 */
	public SuspendRetailOrderScreen(InvoiceSettings invoiceSettings, SuspendedOrdersScreen suspendedOrdersScreen){

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
		
		setTitle("Suspended Retail Order");
		getContentPane().setBackground(Color.DARK_GRAY);
		setBounds(30, 50, 1310, 629);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);

		panelSuspendRetailNewOrder = new JPanel();
		panelSuspendRetailNewOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendRetailNewOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailNewOrder.setBounds(1046, 113, 199, 71);
		getContentPane().add(panelSuspendRetailNewOrder);
		panelSuspendRetailNewOrder.setLayout(null);
		panelSuspendRetailNewOrder.setVisible(false);
		
		JButton btnSuspendRetailNewOrder = new JButton("NEW ORDER");
		btnSuspendRetailNewOrder.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		btnSuspendRetailNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startNewOrder();
			}
		});
		btnSuspendRetailNewOrder.setFont(new Font("Georgia", Font.BOLD, 16));
		btnSuspendRetailNewOrder.setBounds(17, 17, 167, 38);
		panelSuspendRetailNewOrder.add(btnSuspendRetailNewOrder);
		
		panelSuspendRetailOrderLine = new JPanel();
		panelSuspendRetailOrderLine.setForeground(Color.BLACK);
		panelSuspendRetailOrderLine.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendRetailOrderLine.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailOrderLine.setBounds(28, 21, 1217, 71);
		getContentPane().add(panelSuspendRetailOrderLine);
		panelSuspendRetailOrderLine.setLayout(null);
		
		JLabel lblSuspendRetailItemCodeText = new JLabel("Item Code");
		lblSuspendRetailItemCodeText.setBounds(152, 9, 132, 18);
		lblSuspendRetailItemCodeText.setForeground(Color.BLACK);
		lblSuspendRetailItemCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendRetailOrderLine.add(lblSuspendRetailItemCodeText);
		
		txtSuspendRetailLineCode = new JTextField();
		txtSuspendRetailLineCode.setBounds(152, 31, 132, 28);
		txtSuspendRetailLineCode.setEditable(false);
		txtSuspendRetailLineCode.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendRetailLineCode.setColumns(10);
		txtSuspendRetailLineCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailOrderLine.add(txtSuspendRetailLineCode);
		
		JLabel lblSuspendRetailItemDescText = new JLabel("Item Description");
		lblSuspendRetailItemDescText.setBounds(318, 9, 175, 18);
		lblSuspendRetailItemDescText.setForeground(Color.BLACK);
		lblSuspendRetailItemDescText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendRetailOrderLine.add(lblSuspendRetailItemDescText);
		
		txtSuspendRetailLineDesc = new JTextField();
		txtSuspendRetailLineDesc.setBounds(318, 31, 341, 28);
		txtSuspendRetailLineDesc.addKeyListener (new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				
				// Disable the normal tab key focus on the Item Description textbox
				txtSuspendRetailLineDesc.setFocusTraversalKeysEnabled(false);
				
				// Uppercase the entered description
				txtSuspendRetailLineDesc.setText(txtSuspendRetailLineDesc.getText().toUpperCase());
				
				// Make ENTER button and the list of descriptions invisible
				panelSuspendRetailItemDescriptionChoice.setVisible(false);
				btnSuspendRetailNewOrderLine.setVisible(false);
				
				if (txtSuspendRetailLineDesc.getText().equals("") == false) {  
					// Search in database for items that have this exact description in database and if found enable qty textfield and fill in code and price
					try {
							
						// Find item based on it's description
						Item foundItem = getProductDao().findItemByDesc(txtSuspendRetailLineDesc.getText());

						if(foundItem.getItemCode() != null){
							txtSuspendRetailLineCode.setText(foundItem.getItemCode());
							txtSuspendRetailLinePrice.setText(Utilities.stringToDec(String.valueOf(foundItem.getRetailPrice())));
							txtSuspendLineCost = foundItem.getCostPrice();
							suspendTradeLinePrice = foundItem.getTradePrice();
							txtSuspendRetailLineQty.setEnabled(true);
							txtSuspendRetailLineDisc.setEnabled(true);
						} else {
							txtSuspendRetailLineCode.setText("");
							txtSuspendRetailLinePrice.setText("");
							txtSuspendRetailLineQty.setText("");
							txtSuspendRetailLineQty.setEnabled(false);
							txtSuspendRetailLineDisc.setText("");
							txtSuspendRetailLineDisc.setEnabled(false);
						
							// Exact match not found so bring up list of descriptions that contain this description and are not set as deleted
							List<Item> itemList = getProductDao().findPossibleItemMatches(txtSuspendRetailLineDesc.getText().toUpperCase());
							
							if(itemList.size() != 0) {
								
								String columns[] = {"DESCRIPTION"};
								DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
								
								for(Item item : itemList){
									String tableDesc = item.getItemDescription();
									Object[] line = {tableDesc};
									tableModel.addRow(line);
								}
								
								tblSuspendRetailItemDescription.setModel(tableModel);
								
								tblSuspendRetailItemDescription.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
								TableColumnModel columnModel = tblSuspendRetailItemDescription.getColumnModel();
								columnModel.getColumn(0).setPreferredWidth(340);
								panelSuspendRetailItemDescriptionChoice.setVisible(true);
								
								// Set the tab key to the item description possible matches table
								if(arg0.getKeyCode() == KeyEvent.VK_TAB){
									tblSuspendRetailItemDescription.requestFocus();
								} 
								
							} else {
								txtSuspendRetailLineCode.setText("");
								txtSuspendRetailLinePrice.setText("");
								txtSuspendRetailLineQty.setText("");
								txtSuspendRetailLineQty.setEnabled(false);
								txtSuspendRetailLineDisc.setText("");
								txtSuspendRetailLineDisc.setEnabled(false);
							}
								
						}
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}else {
					// Make ENTER button and the list of descriptions invisible
					panelSuspendRetailItemDescriptionChoice.setVisible(false);
					btnSuspendRetailNewOrderLine.setVisible(false);
				}
			}
		});
		txtSuspendRetailLineDesc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendRetailLineDesc.setColumns(50);
		txtSuspendRetailLineDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailOrderLine.add(txtSuspendRetailLineDesc);
		
		JLabel lblSuspendRetailItemPriceText = new JLabel("Item Price");
		lblSuspendRetailItemPriceText.setBounds(678, 9, 104, 18);
		lblSuspendRetailItemPriceText.setForeground(Color.BLACK);
		lblSuspendRetailItemPriceText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendRetailOrderLine.add(lblSuspendRetailItemPriceText);
		
		txtSuspendRetailLinePrice = new JTextField();
		txtSuspendRetailLinePrice.setBounds(678, 31, 132, 28);
		txtSuspendRetailLinePrice.setEditable(false);
		txtSuspendRetailLinePrice.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendRetailLinePrice.setColumns(10);
		txtSuspendRetailLinePrice.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailOrderLine.add(txtSuspendRetailLinePrice);
		
		JLabel lblSuspendRetailItemQtyText = new JLabel("Quantity");
		lblSuspendRetailItemQtyText.setBounds(828, 9, 75, 18);
		lblSuspendRetailItemQtyText.setForeground(Color.BLACK);
		lblSuspendRetailItemQtyText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendRetailOrderLine.add(lblSuspendRetailItemQtyText);
		
		txtSuspendRetailLineQty = new JTextField();
		txtSuspendRetailLineQty.setBounds(828, 31, 57, 28);
		txtSuspendRetailLineQty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				panelSuspendRetailItemDescriptionChoice.setVisible(false);
				btnSuspendRetailNewOrderLine.setVisible(true);
			}
		});
		txtSuspendRetailLineQty.setEnabled(false);
		txtSuspendRetailLineQty.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendRetailLineQty.setColumns(10);
		txtSuspendRetailLineQty.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailOrderLine.add(txtSuspendRetailLineQty);

		btnSuspendRetailNewOrderLine = new JButton("ENTER");
		btnSuspendRetailNewOrderLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		btnSuspendRetailNewOrderLine.setBounds(1025, 17, 167, 38);
		btnSuspendRetailNewOrderLine.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// First check that the Rep is valid
				try {
					
					Staff checkStaff = getStaffDao().staffExists(txtSuspendRetailLineRep.getText());
					
					// If this repCode is okay
					if(checkStaff.getRepCode() != null) {

						txtSuspendRetailLineRep.setEditable(false);
						txtSuspendRetailLineRep.setEnabled(false);
						// Check if Order exists and if not create one
						if(getOrder() == null){
							setOrder(new SuspendedOrder());
							getOrder().setOrderDate(new Date());
							getOrder().setSaleType("Retail");
							getOrder().setRepNo(txtSuspendRetailLineRep.getText());
						}

						try {

							// Check if an item is entered
							if(!txtSuspendRetailLineDesc.getText().isEmpty()) {
								// Check that the Quantity entered is an Integer and > 0.
								if(Integer.parseInt(txtSuspendRetailLineQty.getText()) > 0) {
									
									float retailLinePrice = Float.parseFloat(txtSuspendRetailLinePrice.getText());
									String strDiscountLinePrice = txtSuspendRetailLineDisc.getText().equals("")?"0.00":txtSuspendRetailLineDisc.getText();
									float discountLinePrice = retailLinePrice * Float.parseFloat(strDiscountLinePrice) / 100;
									
									if(retailLinePrice - discountLinePrice >= (suspendTradeLinePrice)+0.01) {
															
										// Create a RetailOrderLine and call the addRetailOrderLine() method to calculate it's values and save it to the arraylist  
										orderLine = new SuspendedOrderLine();
	
										orderLine.addRetailOrderLine(txtSuspendRetailLineCode, txtSuspendRetailLineDesc, txtSuspendRetailLineQty, txtSuspendRetailLinePrice, 
																			txtSuspendRetailLineDisc, txtSuspendRetailDiscount, lblSuspendRetailGrossProfitValue, 
																			lblSuspendRetailGrossProfitPercent, getOrder(), suspendTradeLinePrice, txtSuspendLineCost, 
																			getInvoiceSettings().getReceiptVatRate(), false);
										
										// Clear the order textfield boxes and disable the quantity+discount textfields and make the ENTER button invisible
										txtSuspendRetailLineCode.setText("");
										txtSuspendRetailLineDesc.setText("");
										txtSuspendRetailLinePrice.setText("");
										txtSuspendRetailLineQty.setText("");
										txtSuspendRetailLineQty.setEnabled(false);
										txtSuspendRetailLineDisc.setText("");
										txtSuspendRetailLineDisc.setEnabled(false);
										btnSuspendRetailNewOrderLine.setVisible(false);
											
										// Make the Cust: CurrentOrder, PrintOrder, PaymentMethod, DiscountPanel, GrossProfit, Rounding, CustDetails and NewOrder panels visible
										panelSuspendRetailCurrentOrder.setVisible(true);
										panelSuspendRetailPrintOrder.setVisible(true);
										panelSuspendRetailDiscount.setVisible(false);
										panelSuspendRetailGrossProfit.setVisible(true);
										panelSuspendRetailRounding.setVisible(true);
										panelSuspendRetailCustDetails.setVisible(true);
										panelSuspendRetailNewOrder.setVisible(true);
											
										// Load the current order lines onto the screen
										refreshCurrentOrderTable();
																			
										
										// Set the focus back to the item description
										txtSuspendRetailLineDesc.requestFocus();
									
									}else {
										JOptionPane.showMessageDialog(null, "Discount too large !");
									}
								} else {
									JOptionPane.showMessageDialog(null, "You must enter a Quantity greater than 0 !");
								}
							}else {
								JOptionPane.showMessageDialog(null, "You must enter an Item !");	
							}
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, "You must enter a number for the Quantity and the Discount % !");
						}
					}else{
						JOptionPane.showMessageDialog(null, "That Rep Code doesn't exist, please try again !");
					}
					
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnSuspendRetailNewOrderLine.setFont(new Font("Georgia", Font.BOLD, 16));
		btnSuspendRetailNewOrderLine.setVisible(false);
		panelSuspendRetailOrderLine.add(btnSuspendRetailNewOrderLine);

		JLabel lblSuspendRetailItemDiscText = new JLabel("Discount %");
		lblSuspendRetailItemDiscText.setForeground(Color.BLACK);
		lblSuspendRetailItemDiscText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendRetailItemDiscText.setBounds(913, 9, 104, 18);
		panelSuspendRetailOrderLine.add(lblSuspendRetailItemDiscText);
		
		txtSuspendRetailLineDisc = new JTextField();
		txtSuspendRetailLineDisc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendRetailLineDisc.setEnabled(false);
		txtSuspendRetailLineDisc.setColumns(10);
		txtSuspendRetailLineDisc.setBounds(913, 31, 57, 28);
		txtSuspendRetailLineDisc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailOrderLine.add(txtSuspendRetailLineDisc);
		
		JLabel lblSuspendRetailRepCodeText = new JLabel("Rep Code");
		lblSuspendRetailRepCodeText.setForeground(Color.BLACK);
		lblSuspendRetailRepCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendRetailRepCodeText.setBounds(34, 9, 96, 18);
		panelSuspendRetailOrderLine.add(lblSuspendRetailRepCodeText);
		
		txtSuspendRetailLineRep = new JTextField();
		txtSuspendRetailLineRep.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendRetailLineRep.setBounds(34, 31, 86, 28);
		txtSuspendRetailLineRep.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailOrderLine.add(txtSuspendRetailLineRep);
		txtSuspendRetailLineRep.setColumns(10);
		
		txtSuspendRetailLineDisc.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				panelSuspendRetailItemDescriptionChoice.setVisible(false);
				btnSuspendRetailNewOrderLine.setVisible(true);
			}
		});
		
		panelSuspendRetailItemDescriptionChoice = new JPanel();
		panelSuspendRetailItemDescriptionChoice.setBounds(350, 91, 338, 398);
		getContentPane().add(panelSuspendRetailItemDescriptionChoice);
		panelSuspendRetailItemDescriptionChoice.setLayout(null);
		
		JScrollPane scrlPanelSuspendRetailItemDescription = new JScrollPane();
		scrlPanelSuspendRetailItemDescription.setBounds(0, 0, 337, 397);
		panelSuspendRetailItemDescriptionChoice.add(scrlPanelSuspendRetailItemDescription);
		
		// When creating the JTable for the description dropdown I over-ride the isCellEditable method to make the table un-editable
		tblSuspendRetailItemDescription = new JTable(){
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
		scrlPanelSuspendRetailItemDescription.setViewportView(tblSuspendRetailItemDescription);
		
		tblSuspendRetailItemDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// If enter pressed call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make ENTER visible 
				if(arg0.getKeyCode() == 10) {
					ordLineItemSelected();					
				}
			}
		});
		tblSuspendRetailItemDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
								
				// Call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make the enter button visible
				ordLineItemSelected();
			}
		});
		tblSuspendRetailItemDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSuspendRetailItemDescription.setCellSelectionEnabled(true);
		tblSuspendRetailItemDescription.setColumnSelectionAllowed(false);
		
		// Initially make the description dropdown invisible
		panelSuspendRetailItemDescriptionChoice.setVisible(false);
		
		panelSuspendRetailCurrentOrder = new JPanel();
		panelSuspendRetailCurrentOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendRetailCurrentOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailCurrentOrder.setBounds(28, 138, 800, 447);
		getContentPane().add(panelSuspendRetailCurrentOrder);
		panelSuspendRetailCurrentOrder.setLayout(null);
		panelSuspendRetailCurrentOrder.setVisible(false);
		
		JLabel lblSuspendRetailCurrentTransaction = new JLabel("SUSPENDED");
		lblSuspendRetailCurrentTransaction.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendRetailCurrentTransaction.setBounds(10, 11, 256, 25);
		panelSuspendRetailCurrentOrder.add(lblSuspendRetailCurrentTransaction);
		
		JLabel lblSuspendRetailTotalPriceText = new JLabel("TOTAL INC-VAT");
		lblSuspendRetailTotalPriceText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendRetailTotalPriceText.setBounds(634, 11, 156, 31);
		panelSuspendRetailCurrentOrder.add(lblSuspendRetailTotalPriceText);
		
		lblSuspendRetailTotalPrice = new JLabel("0.00", SwingConstants.RIGHT);
		lblSuspendRetailTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendRetailTotalPrice.setBounds(634, 37, 156, 31);
		panelSuspendRetailCurrentOrder.add(lblSuspendRetailTotalPrice);
		
		JScrollPane scrlSuspendRetailOrder = new JScrollPane();
		scrlSuspendRetailOrder.setBounds(10, 68, 780, 368);
		panelSuspendRetailCurrentOrder.add(scrlSuspendRetailOrder);
		
		tblSuspendRetailOrder = new JTable(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public TableCellRenderer getCellRenderer( int row, int col ) { 
				TableCellRenderer renderer = super.getCellRenderer(row,col);
				if ( col == dataModel.getColumnCount() - 1  || col == dataModel.getColumnCount() - 2 || col == dataModel.getColumnCount() - 3 || 
					 col == dataModel.getColumnCount() - 4) 
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
		tblSuspendRetailOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSuspendRetailOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
							
				if(getOrder().getOrderList().size() > 1) {
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblSuspendRetailOrder.getSelectedRow();
						
					// We use this row to find the element in the arraylist that the user selected
					getOrder().getOrderList().remove(row);
						
					// Recalculate everything for the Order
					getOrder().recalculateAfterDelete(getInvoiceSettings().getReceiptVatRate(), lblSuspendRetailGrossProfitValue, lblSuspendRetailGrossProfitPercent);
						
					// Load the current order lines onto the screen
					refreshCurrentOrderTable();
				
				} else{

					startNewOrder();
				}
										
			}
		});
		tblSuspendRetailOrder.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tblSuspendRetailOrder.setShowGrid(false);
		tblSuspendRetailOrder.setShowHorizontalLines(false);
		tblSuspendRetailOrder.setShowVerticalLines(false);
		tblSuspendRetailOrder.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		scrlSuspendRetailOrder.setViewportView(tblSuspendRetailOrder);
		
		JLabel lblSuspendRetailTotalExVatText = new JLabel("TOTAL EX-VAT");
		lblSuspendRetailTotalExVatText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendRetailTotalExVatText.setBounds(297, 11, 148, 31);
		panelSuspendRetailCurrentOrder.add(lblSuspendRetailTotalExVatText);
		
		lblSuspendRetailTotalExVat = new JLabel("0.00", SwingConstants.RIGHT);
		lblSuspendRetailTotalExVat.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendRetailTotalExVat.setBounds(297, 37, 144, 31);
		panelSuspendRetailCurrentOrder.add(lblSuspendRetailTotalExVat);
		
		JLabel lblSuspendRetailCurrentTransaction2 = new JLabel("RETAIL ORDER");
		lblSuspendRetailCurrentTransaction2.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendRetailCurrentTransaction2.setBounds(10, 37, 256, 25);
		panelSuspendRetailCurrentOrder.add(lblSuspendRetailCurrentTransaction2);
		
		panelSuspendRetailPrintOrder = new JPanel();
		panelSuspendRetailPrintOrder.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailPrintOrder.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendRetailPrintOrder.setBounds(1046, 204, 199, 71);
		panelSuspendRetailPrintOrder.setVisible(false);
		getContentPane().add(panelSuspendRetailPrintOrder);
		panelSuspendRetailPrintOrder.setLayout(null);
		
		JButton btnCustPrintOrder = new JButton("PRINT ORDER");
		btnCustPrintOrder.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		btnCustPrintOrder.setBounds(17, 17, 167, 38);
		panelSuspendRetailPrintOrder.add(btnCustPrintOrder);
		btnCustPrintOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				getOrder().setOrderLinesToPrint(0);
								
				try {
					getOrder().printOrder(getInvoiceSettings(), null, null, null);
					getSuspendedOrdersScreen().refreshSuspendedOrderHeaders();
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnCustPrintOrder.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblSuspendRetailLogo = new JLabel();
		// Put Logo image on order tab by adding an image to the Logo Label
		lblSuspendRetailLogo.setIcon(imageIcon);
		
		lblSuspendRetailLogo.setBounds(1046, 385, 199, 200);
		getContentPane().add(lblSuspendRetailLogo);
		
		panelSuspendRetailDiscount = new JPanel();
		panelSuspendRetailDiscount.setBorder(new BevelBorder(BevelBorder.RAISED, null, UIManager.getColor("Button.shadow"), UIManager.getColor("Button.foreground"), UIManager.getColor("Button.darkShadow")));
		panelSuspendRetailDiscount.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendRetailDiscount.setBounds(880, 226, 114, 91);
		getContentPane().add(panelSuspendRetailDiscount);
		panelSuspendRetailDiscount.setLayout(null);
		panelSuspendRetailDiscount.setVisible(false);
		
		txtSuspendRetailDiscount = new JTextField();
		txtSuspendRetailDiscount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {

					getOrder().recalculateDiscount(txtSuspendRetailDiscount, getInvoiceSettings().getReceiptVatRate(), oldOverallDiscount, lblSuspendRetailGrossProfitValue, lblSuspendRetailGrossProfitPercent);
	
					refreshCurrentOrderTable();	
					
				}catch(NumberFormatException ex) {
					txtSuspendRetailDiscount.setText("0.00");
					getOrder().recalculateDiscount(txtSuspendRetailDiscount, getInvoiceSettings().getReceiptVatRate(), oldOverallDiscount, lblSuspendRetailGrossProfitValue, lblSuspendRetailGrossProfitPercent);
					JOptionPane.showMessageDialog(null, "You must enter a number for the Overall Discount!");
					
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Exception: " + ex);
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				oldOverallDiscount = txtSuspendRetailDiscount.getText();
			}
		});

		txtSuspendRetailDiscount.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendRetailDiscount.setBounds(33, 47, 44, 28);
		panelSuspendRetailDiscount.add(txtSuspendRetailDiscount);
		txtSuspendRetailDiscount.setColumns(10);
		
		JLabel lblSuspendRetailDiscountPercentage = new JLabel("Discount %");
		lblSuspendRetailDiscountPercentage.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendRetailDiscountPercentage.setBounds(10, 11, 104, 25);
		panelSuspendRetailDiscount.add(lblSuspendRetailDiscountPercentage);
		
		panelSuspendRetailGrossProfit = new JPanel();
		panelSuspendRetailGrossProfit.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailGrossProfit.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendRetailGrossProfit.setBounds(855, 443, 163, 142);
		getContentPane().add(panelSuspendRetailGrossProfit);
		panelSuspendRetailGrossProfit.setLayout(null);
		panelSuspendRetailGrossProfit.setVisible(false);
		
		lblSuspendRetailGrossProfitValue = new JLabel("\u20AC 0.00", SwingConstants.RIGHT);
		lblSuspendRetailGrossProfitValue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendRetailGrossProfitValue.setForeground(Color.BLACK);
		lblSuspendRetailGrossProfitValue.setBounds(25, 58, 102, 24);
		panelSuspendRetailGrossProfit.add(lblSuspendRetailGrossProfitValue);
		
		JLabel lblSuspendRetailGrossProfitText = new JLabel("Gross Profit");
		lblSuspendRetailGrossProfitText.setForeground(Color.BLACK);
		lblSuspendRetailGrossProfitText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendRetailGrossProfitText.setBounds(27, 23, 111, 24);
		panelSuspendRetailGrossProfit.add(lblSuspendRetailGrossProfitText);
		
		lblSuspendRetailGrossProfitPercent = new JLabel("0.00 %", SwingConstants.RIGHT);
		lblSuspendRetailGrossProfitPercent.setForeground(Color.BLACK);
		lblSuspendRetailGrossProfitPercent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendRetailGrossProfitPercent.setBounds(25, 92, 102, 24);
		panelSuspendRetailGrossProfit.add(lblSuspendRetailGrossProfitPercent);
		
		panelSuspendRetailRounding = new JPanel();
		panelSuspendRetailRounding.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailRounding.setBackground(UIManager.getColor("List.dropLineColor"));
		// Put Logo image on order tab by adding an image to the Logo Label
		lblSuspendRetailLogo.setIcon(imageIcon);
		panelSuspendRetailRounding.setBounds(855, 167, 164, 81);
		getContentPane().add(panelSuspendRetailRounding);
		panelSuspendRetailRounding.setLayout(null);
		panelSuspendRetailRounding.setVisible(false);
		
		JLabel lblSuspendRetailRoundText = new JLabel("Round");
		lblSuspendRetailRoundText.setForeground(Color.BLACK);
		lblSuspendRetailRoundText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendRetailRoundText.setBounds(55, 11, 55, 17);
		panelSuspendRetailRounding.add(lblSuspendRetailRoundText);
				
		JLabel lblSuspendRetailRoundUp = new JLabel("Round Up");
		lblSuspendRetailRoundUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
						
				String beforeRound = getOrder().getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(String.valueOf(Float.parseFloat(beforeRound.substring(0, theDot)) + 1));
				getOrder().setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				getOrder().setRounding(String.valueOf(rounding));

				// Save the rounding and post Rounding values if the invoice has been printed
				if(getOrder().getReceiptNo() != 0) {
					try {

						getOrder().setBalance(Float.valueOf(getOrder().getTotalPostRounding()));
						getSuspendedDao().updateRoundingHeader(rounding, getOrder().getTotalPostRounding(), getOrder().getBalance(), getOrder().getReceiptNo());
	
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblSuspendRetailTotalPrice.setText("\u20AC " + getOrder().getTotalPostRounding());
			}
		});
		
		lblSuspendRetailRoundUp.setBounds(36, 39, 35, 31);
		panelSuspendRetailRounding.add(lblSuspendRetailRoundUp);
		lblSuspendRetailRoundUp.setIcon(imageIconUpArrow);
				
		JLabel lblSuspendRetailRoundDown = new JLabel("Round Down");
		lblSuspendRetailRoundDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
						
				String beforeRound = getOrder().getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(beforeRound.substring(0, theDot));
				getOrder().setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				getOrder().setRounding(String.valueOf(rounding));

				// Save the rounding and post Rounding values if the invoice has been printed
				if(getOrder().getReceiptNo() != 0) {
					try {

						getOrder().setBalance(Float.valueOf(getOrder().getTotalPostRounding()));
						getSuspendedDao().updateRoundingHeader(rounding, getOrder().getTotalPostRounding(), getOrder().getBalance(), getOrder().getReceiptNo());
	
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblSuspendRetailTotalPrice.setText("\u20AC " + getOrder().getTotalPostRounding());
			}
		});
				
		lblSuspendRetailRoundDown.setBounds(92, 39, 35, 31);
		panelSuspendRetailRounding.add(lblSuspendRetailRoundDown);
		lblSuspendRetailRoundDown.setIcon(imageIconDownArrow);
		
		tblSuspendRetailItemDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSuspendRetailItemDescription.setCellSelectionEnabled(true);
		tblSuspendRetailItemDescription.setColumnSelectionAllowed(true);
		
		panelSuspendRetailCustDetails = new JPanel();
		panelSuspendRetailCustDetails.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendRetailCustDetails.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendRetailCustDetails.setBounds(1046, 295, 199, 71);
		getContentPane().add(panelSuspendRetailCustDetails);
		panelSuspendRetailCustDetails.setLayout(null);
		panelSuspendRetailCustDetails.setVisible(false);
		
		JButton btnSuspendRetailCustDetails = new JButton("CUST DETAILS");
		btnSuspendRetailCustDetails.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		btnSuspendRetailCustDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CustomerDetails(getOrder()); 
			}
		});
		btnSuspendRetailCustDetails.setFont(new Font("Georgia", Font.BOLD, 16));
		btnSuspendRetailCustDetails.setBounds(17, 17, 167, 38);
		panelSuspendRetailCustDetails.add(btnSuspendRetailCustDetails);
		
		// Initially make the description dropdown invisible
		panelSuspendRetailItemDescriptionChoice.setVisible(false);

	}
	
	
	/**
	 * This method removes the current Retail Order, clears all of the screen's textfields and hides panels so as to start a new Retail Order
	 */
	public void startNewOrder() {
		panelSuspendRetailCurrentOrder.setVisible(false);
		panelSuspendRetailPrintOrder.setVisible(false);
		panelSuspendRetailDiscount.setVisible(false);
		panelSuspendRetailGrossProfit.setVisible(false);
		
		// Remove the current order from the order object
		setOrder(null);
		
		// Blank the current order line entry fields
		txtSuspendRetailLineCode.setText(null);
		txtSuspendRetailLineDesc.setText(null);
		txtSuspendRetailLinePrice.setText(null);
		txtSuspendRetailLineQty.setText(null);
		txtSuspendRetailLineDisc.setText(null);
		
		// Disable quantity+discount fields and make ENTER button invisible - because they may be accessible when NEW ORDER button pressed 
		txtSuspendRetailLineQty.setEnabled(false);
		txtSuspendRetailLineDisc.setEnabled(false);
		btnSuspendRetailNewOrderLine.setVisible(false);
		
		// Make the Current Order, Print Order, CustomerPaymentMethod, DiscountPanelvisible, CustomerGrossProfit, Rounding, CustDetails and NewOrder panels 
		// invisible
		panelSuspendRetailCurrentOrder.setVisible(false);
		panelSuspendRetailPrintOrder.setVisible(false);
		panelSuspendRetailDiscount.setVisible(false);
		panelSuspendRetailGrossProfit.setVisible(false);
		panelSuspendRetailRounding.setVisible(false);
		panelSuspendRetailCustDetails.setVisible(false);
		panelSuspendRetailNewOrder.setVisible(false);
		
		// Blank the Overall Discount field and set the Payment Type back to Cash
		txtSuspendRetailDiscount.setText("");
		
		// Blank the Rep Code field, enable it and make it editable
		txtSuspendRetailLineRep.setEditable(true);
		txtSuspendRetailLineRep.setEnabled(true);
		txtSuspendRetailLineRep.setText("");
	}

	
	/**
	 * This method loads the current retail order lines into the current order table that the user sees on the Suspended Retail Order Screen.
	 */
	public void refreshCurrentOrderTable(){
		
		String columns[] = {"ITEM", "DESCRIPTION", "QTY", "PRICE", "DISC", "TOTAL"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		
		for(SuspendedOrderLine o : order.getOrderList()){
			String tableCode= o.getItemCode();
			String tableDesc = o.getItemDescription();
			String tableQty = o.getOrderQty();
			String tableItemPrice = o.getItemPrice();
			String tableItemDiscount = o.getDiscountPercent() + " %";
			String tableOrderPrice = o.getValueExVat();
			Object[] line = {tableCode, tableDesc, tableQty, tableItemPrice, tableItemDiscount, tableOrderPrice};
			tableModel.addRow(line);
		}
		tblSuspendRetailOrder.setModel(tableModel);
		lblSuspendRetailTotalPrice.setText("\u20AC " + String.valueOf(order.getTotalPreRounding()));
		lblSuspendRetailTotalExVat.setText("\u20AC " + order.getTotalExVat());
	
	
		// Set the column widths for the table
		tblSuspendRetailOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnModelOrder = tblSuspendRetailOrder.getColumnModel();
		columnModelOrder.getColumn(0).setPreferredWidth(90);
		columnModelOrder.getColumn(1).setPreferredWidth(409);
		columnModelOrder.getColumn(2).setPreferredWidth(40);
		columnModelOrder.getColumn(3).setPreferredWidth(70);	
		columnModelOrder.getColumn(4).setPreferredWidth(70);
		columnModelOrder.getColumn(5).setPreferredWidth(83);
	}


	/**
	 * This method takes the item selected from the item dropdown and fills textfields with it's values (code, description,price), it also 
	 * retrieves the item's trade price and cost price and enables the quantity and discount fields. 
 	*/
	public void ordLineItemSelected() {
		try {
		
			// We use the table's getSelectedRow() method to find the row that the User selected. 
			int row = tblSuspendRetailItemDescription.getSelectedRow();
	
			// Then we can get the description of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
			// row and column that we require.
			// This returns an Object so we get the toString of it.
			String descString = tblSuspendRetailItemDescription.getModel().getValueAt(row, 0).toString();
			
			Item selectedItem = getProductDao().findItemByDesc(descString);
			
			if(selectedItem.getItemCode() != null){
				// Put selected item's code, description and price into the oderline entry fields and Enable the quantity+discount fields and make the enter button visible. 
				txtSuspendRetailLineCode.setText(selectedItem.getItemCode());
				txtSuspendRetailLineDesc.setText(selectedItem.getItemDescription());
				txtSuspendRetailLinePrice.setText(Utilities.stringToDec( String.valueOf(selectedItem.getRetailPrice()) ));
				suspendTradeLinePrice = selectedItem.getTradePrice();
				txtSuspendLineCost = selectedItem.getCostPrice();
				txtSuspendRetailLineQty.setEnabled(true);
				txtSuspendRetailLineDisc.setEnabled(true);
			}
			
			txtSuspendRetailLineQty.requestFocus();
			panelSuspendRetailItemDescriptionChoice.setVisible(false);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	
}
