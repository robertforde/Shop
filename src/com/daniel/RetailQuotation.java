package com.daniel;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.daniel.dao.OrderDao;
import com.daniel.dao.OrderDaoImpl;
import com.daniel.dao.ProductDao;
import com.daniel.dao.ProductDaoImpl;
import com.daniel.dao.StaffDao;
import com.daniel.dao.StaffDaoImpl;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.Item;
import com.daniel.model.RetailOrder;
import com.daniel.model.RetailOrderLine;
import com.daniel.model.Staff;
import com.daniel.utilities.Utilities;

import javax.swing.UIManager;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * This class designs and handles the entry of Retail Quotes.
 * @author Robert Forde
 *
 */
public class RetailQuotation extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RetailOrder retailQuote;
	private RetailOrderLine retailQuoteLine;
	private InvoiceSettings invoiceSettings;
	
	private JTextField txtRetailQuoteLineRep;
	private JTextField txtRetailQuoteLineCode;
	private JTextField txtRetailQuoteLineDesc;
	private JTextField txtRetailQuoteLinePrice;
	private JTextField txtRetailQuoteLineQty;
	private JTextField txtRetailQuoteLineDisc;
	private JTable tblRetailQuoteItemDescription;
	private JTable tblRetailQuoteOrder;
	private JTextField txtRetailQuoteDiscount;
	private JLabel lblRetailQuoteTotalPrice;
	private JLabel lblRetailQuoteTotalExVat;
	private JPanel panelRetailQuoteItemDescriptionChoice;
	private JButton btnRetailQuoteNewOrderLine;
	private JLabel lblRetailQuoteGrossProfitValue;
	private JLabel lblRetailQuoteGrossProfitPercent;
	private JPanel panelRetailQuoteCurrentQuote;
	private JPanel panelRetailQuoteDiscount;
	private JPanel panelRetailQuoteRounding;
	private JPanel panelRetailQuoteGrossProfit;
	private JPanel panelRetailQuoteNewQuote;
	private JPanel panelRetailQuotePrintQuote;
	private JPanel panelRetailQuoteCustDetails;

	private float RetailQuoteTradeLinePrice;
	private float txtRetailQuoteLineCost;
	private String oldOverallDiscount;
	
	private TableColumnModel columnModelOrder;

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

	
	/**
	 * Constructs the RetailQuotation Screen
	 * @param invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 */
	public RetailQuotation(InvoiceSettings invoiceSettings){
		
		setProductDao(Dan.ctx.getBean("productDaoImpl", ProductDaoImpl.class));
		setStaffDao(Dan.ctx.getBean("staffDaoImpl", StaffDaoImpl.class));
		setOrderDao(Dan.ctx.getBean("orderDaoImpl", OrderDaoImpl.class));
		
		getContentPane().setBackground(Color.DARK_GRAY);
		
		setInvoiceSettings(invoiceSettings);
		
		setBounds(30, 50, 1310, 629);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Retail Quotation");
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
		
		JPanel panelRetailQuoteLine = new JPanel();
		panelRetailQuoteLine.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRetailQuoteLine.setBounds(28, 21, 1217, 71);
		panelRetailQuoteLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelRetailQuoteLine);
		panelRetailQuoteLine.setLayout(null);
		
		JLabel lblRetailQuoteRepCodeText = new JLabel("Rep Code");
		lblRetailQuoteRepCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRetailQuoteRepCodeText.setBounds(34, 9, 86, 18);
		panelRetailQuoteLine.add(lblRetailQuoteRepCodeText);
		
		JLabel lblRetailQuoteItemDescriptionText = new JLabel("Item Description");
		lblRetailQuoteItemDescriptionText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRetailQuoteItemDescriptionText.setBounds(318, 9, 186, 18);
		panelRetailQuoteLine.add(lblRetailQuoteItemDescriptionText);
		
		JLabel lblRetailQuoteItemCodeText = new JLabel("Item Code");
		lblRetailQuoteItemCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRetailQuoteItemCodeText.setBounds(152, 9, 132, 18);
		panelRetailQuoteLine.add(lblRetailQuoteItemCodeText);
		
		JLabel lblRetailQuoteItemPriceText = new JLabel("Item Price");
		lblRetailQuoteItemPriceText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRetailQuoteItemPriceText.setBounds(678, 9, 108, 18);
		panelRetailQuoteLine.add(lblRetailQuoteItemPriceText);
		
		JLabel lblRetailQuoteItemQuantityText = new JLabel("Quantity");
		lblRetailQuoteItemQuantityText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRetailQuoteItemQuantityText.setBounds(828, 9, 75, 18);
		panelRetailQuoteLine.add(lblRetailQuoteItemQuantityText);
		
		JLabel lblRetailQuoteItemDiscText = new JLabel("Discount %");
		lblRetailQuoteItemDiscText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRetailQuoteItemDiscText.setBounds(922, 9, 93, 18);
		panelRetailQuoteLine.add(lblRetailQuoteItemDiscText);
		
		txtRetailQuoteLineRep = new JTextField();
		txtRetailQuoteLineRep.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRetailQuoteLineRep.setBounds(34, 31, 86, 28);
		txtRetailQuoteLineRep.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRetailQuoteLine.add(txtRetailQuoteLineRep);
		txtRetailQuoteLineRep.setColumns(10);
		
		txtRetailQuoteLineCode = new JTextField();
		txtRetailQuoteLineCode.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRetailQuoteLineCode.setEditable(false);
		txtRetailQuoteLineCode.setColumns(10);
		txtRetailQuoteLineCode.setBounds(152, 31, 132, 28);
		txtRetailQuoteLineCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRetailQuoteLine.add(txtRetailQuoteLineCode);
		
		txtRetailQuoteLineDesc = new JTextField();
		txtRetailQuoteLineDesc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// Disable the normal tab key focus on the Item Description textbox
				txtRetailQuoteLineDesc.setFocusTraversalKeysEnabled(false);
				
				// Uppercase the entered description
				txtRetailQuoteLineDesc.setText(txtRetailQuoteLineDesc.getText().toUpperCase());
				
				// Make ENTER button and the list of descriptions invisible
				panelRetailQuoteItemDescriptionChoice.setVisible(false);
				btnRetailQuoteNewOrderLine.setVisible(false);
				
				if (txtRetailQuoteLineDesc.getText().equals("") == false) {  
					// Search in database for items that have this exact description in database and if found enable qty textfield and fill in code and price
					try {

						// Find item based on it's description
						Item foundItem = getProductDao().findItemByDesc(txtRetailQuoteLineDesc.getText());

						if(foundItem.getItemCode() != null){

							txtRetailQuoteLineCode.setText(foundItem.getItemCode());
							txtRetailQuoteLinePrice.setText(Utilities.stringToDec(String.valueOf(foundItem.getRetailPrice())));
							txtRetailQuoteLineCost = foundItem.getCostPrice();
							RetailQuoteTradeLinePrice = foundItem.getTradePrice();
							txtRetailQuoteLineQty.setEnabled(true);
							txtRetailQuoteLineDisc.setEnabled(true);
						
						} else {
							txtRetailQuoteLineCode.setText("");
							txtRetailQuoteLinePrice.setText("");
							txtRetailQuoteLineQty.setText("");
							txtRetailQuoteLineQty.setEnabled(false);
							txtRetailQuoteLineDisc.setText("");
							txtRetailQuoteLineDisc.setEnabled(false);
							
							// Exact match not found so bring up list of descriptions that contain this description and are not set as deleted
							List<Item> itemList = getProductDao().findPossibleItemMatches(txtRetailQuoteLineDesc.getText().toUpperCase());
							
							if(itemList.size() != 0) {
								
								String columns[] = {"DESCRIPTION"};
								DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
								
								for(Item item : itemList){
									String tableDesc = item.getItemDescription();
									Object[] line = {tableDesc};
									tableModel.addRow(line);
								}
								
								tblRetailQuoteItemDescription.setModel(tableModel);								
								tblRetailQuoteItemDescription.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
								TableColumnModel columnModel = tblRetailQuoteItemDescription.getColumnModel();
								columnModel.getColumn(0).setPreferredWidth(340);
								panelRetailQuoteItemDescriptionChoice.setVisible(true);
								
								// Set the tab key to the item description possible matches table
								if(arg0.getKeyCode() == KeyEvent.VK_TAB){
									tblRetailQuoteItemDescription.requestFocus();
								} 
								
							} else {
								txtRetailQuoteLineCode.setText("");
								txtRetailQuoteLinePrice.setText("");
								txtRetailQuoteLineQty.setText("");
								txtRetailQuoteLineQty.setEnabled(false);
								txtRetailQuoteLineDisc.setText("");
								txtRetailQuoteLineDisc.setEnabled(false);
							}
								
						}
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}else {
					// Make ENTER button and the list of descriptions invisible
					panelRetailQuoteItemDescriptionChoice.setVisible(false);
					btnRetailQuoteNewOrderLine.setVisible(false);
				}
			}
		});
		txtRetailQuoteLineDesc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRetailQuoteLineDesc.setColumns(10);
		txtRetailQuoteLineDesc.setBounds(318, 31, 341, 28);
		txtRetailQuoteLineDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRetailQuoteLine.add(txtRetailQuoteLineDesc);
		
		txtRetailQuoteLinePrice = new JTextField();
		txtRetailQuoteLinePrice.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRetailQuoteLinePrice.setEditable(false);
		txtRetailQuoteLinePrice.setColumns(10);
		txtRetailQuoteLinePrice.setBounds(678, 31, 132, 28);
		txtRetailQuoteLinePrice.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRetailQuoteLine.add(txtRetailQuoteLinePrice);
		
		txtRetailQuoteLineQty = new JTextField();
		txtRetailQuoteLineQty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				panelRetailQuoteItemDescriptionChoice.setVisible(false);
				btnRetailQuoteNewOrderLine.setVisible(true);
			}
			
		});
		txtRetailQuoteLineQty.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRetailQuoteLineQty.setEnabled(false);
		txtRetailQuoteLineQty.setColumns(10);
		txtRetailQuoteLineQty.setBounds(828, 31, 57, 28);
		txtRetailQuoteLineQty.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRetailQuoteLine.add(txtRetailQuoteLineQty);
		
		txtRetailQuoteLineDisc = new JTextField();
		txtRetailQuoteLineDisc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRetailQuoteLineDisc.setEnabled(false);
		txtRetailQuoteLineDisc.setColumns(10);
		txtRetailQuoteLineDisc.setBounds(922, 31, 57, 28);
		txtRetailQuoteLineDisc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRetailQuoteLine.add(txtRetailQuoteLineDisc);
		
		panelRetailQuoteItemDescriptionChoice = new JPanel();
		panelRetailQuoteItemDescriptionChoice.setBounds(350, 91, 338, 398);
		getContentPane().add(panelRetailQuoteItemDescriptionChoice);
		panelRetailQuoteItemDescriptionChoice.setLayout(null);
		
		JScrollPane scrlRetailQuoteItemDescription = new JScrollPane();
		scrlRetailQuoteItemDescription.setBounds(0, 0, 338, 398);
		panelRetailQuoteItemDescriptionChoice.add(scrlRetailQuoteItemDescription);
		
		tblRetailQuoteItemDescription = new JTable();
		tblRetailQuoteItemDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// If enter pressed call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make discount and ENTER visible 
				if(arg0.getKeyCode() == 10) {
					ordLineItemSelected();					
				}
			}
		});
		tblRetailQuoteItemDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ordLineItemSelected();
			}
		});
		tblRetailQuoteItemDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblRetailQuoteItemDescription.setCellSelectionEnabled(true);
		tblRetailQuoteItemDescription.setColumnSelectionAllowed(false);
		scrlRetailQuoteItemDescription.setViewportView(tblRetailQuoteItemDescription);
		panelRetailQuoteItemDescriptionChoice.setVisible(false);
		
		btnRetailQuoteNewOrderLine = new JButton("ENTER");
		btnRetailQuoteNewOrderLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// First check that the Rep is valid
				try {
					
					Staff checkStaff = getStaffDao().staffExists(txtRetailQuoteLineRep.getText());
					
					// If this repCode is okay
					if(checkStaff.getRepCode() != null) {

						txtRetailQuoteLineRep.setEditable(false);
						txtRetailQuoteLineRep.setEnabled(false);
						// Check if Order exists and if not create one
						if(retailQuote == null){
							retailQuote = new RetailOrder();
							retailQuote.setOrderDate(new Date());
							retailQuote.setSaleType("RetailQuote");
							retailQuote.setRepNo(txtRetailQuoteLineRep.getText());
						}

						try {

							// Check if an item is entered
							if(!txtRetailQuoteLineDesc.getText().isEmpty()) {
								// Check that the Quantity entered is an Integer and > 0.
								if(Integer.parseInt(txtRetailQuoteLineQty.getText()) > 0) {
															
									float retailLinePrice = Float.parseFloat(txtRetailQuoteLinePrice.getText());
									String strDiscountLinePrice = txtRetailQuoteLineDisc.getText().equals("")?"0.00":txtRetailQuoteLineDisc.getText();
									float discountLinePrice = retailLinePrice * Float.parseFloat(strDiscountLinePrice) / 100;
									
									if(retailLinePrice - discountLinePrice >= (RetailQuoteTradeLinePrice)+0.01) {
									
										// Create a RetailQuoteLine and call the addRetailOrderLine() method to calculate it's values and save it to the arraylist  
										retailQuoteLine = new RetailOrderLine();
	
										retailQuoteLine.addRetailOrderLine(txtRetailQuoteLineCode, txtRetailQuoteLineDesc, txtRetailQuoteLineQty, txtRetailQuoteLinePrice, 
																			txtRetailQuoteLineDisc, txtRetailQuoteDiscount, lblRetailQuoteGrossProfitValue, 
																			lblRetailQuoteGrossProfitPercent, retailQuote, RetailQuoteTradeLinePrice,txtRetailQuoteLineCost, 
																			getInvoiceSettings().getReceiptVatRate());
										
										// Clear the order textfield boxes and disable the quantity+discount textfields and make the ENTER button invisible
										txtRetailQuoteLineCode.setText("");
										txtRetailQuoteLineDesc.setText("");
										txtRetailQuoteLinePrice.setText("");
										txtRetailQuoteLineQty.setText("");
										txtRetailQuoteLineQty.setEnabled(false);
										txtRetailQuoteLineDisc.setText("");
										txtRetailQuoteLineDisc.setEnabled(false);
										btnRetailQuoteNewOrderLine.setVisible(false);
											
										// Make the Cust: CurrentOrder, PrintOrder, PaymentMethod, DiscountPanel, GrossProfit, Rounding, CustDetails and NewOrder panels visible
										panelRetailQuoteCurrentQuote.setVisible(true);
										panelRetailQuotePrintQuote.setVisible(true);
										panelRetailQuoteDiscount.setVisible(false);
										panelRetailQuoteGrossProfit.setVisible(true);
										panelRetailQuoteRounding.setVisible(true);
										panelRetailQuoteCustDetails.setVisible(true);
										panelRetailQuoteNewQuote.setVisible(true);
											
										// Load the current Order table with the Order Lines
										String columns[] = {"ITEM", "DESCRIPTION", "QTY", "PRICE", "DISC", "TOTAL"};
										DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
										
										for(RetailOrderLine o : retailQuote.getOrderList()){
											String tableCode= o.getItemCode();
											String tableDesc = o.getItemDescription();
											String tableQty = o.getOrderQty();
											String tableItemPrice = o.getItemPrice();
											String tableItemDiscount = o.getDiscountPercent() + " %";
											String tableOrderPrice = o.getValueExVat();
											Object[] line = {tableCode, tableDesc, tableQty, tableItemPrice, tableItemDiscount, tableOrderPrice};
											tableModel.addRow(line);
										}
										tblRetailQuoteOrder.setModel(tableModel);
										lblRetailQuoteTotalPrice.setText("\u20AC " + String.valueOf(retailQuote.getTotalPreRounding()));
										lblRetailQuoteTotalExVat.setText("\u20AC " + retailQuote.getTotalExVat());
	
	
										// Set the column widths for the table
										tblRetailQuoteOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
										columnModelOrder = tblRetailQuoteOrder.getColumnModel();
										columnModelOrder.getColumn(0).setPreferredWidth(90);
										columnModelOrder.getColumn(1).setPreferredWidth(409);
										columnModelOrder.getColumn(2).setPreferredWidth(40);
										columnModelOrder.getColumn(3).setPreferredWidth(70);	
										columnModelOrder.getColumn(4).setPreferredWidth(70);
										columnModelOrder.getColumn(5).setPreferredWidth(83);
										
										// Set the focus back to the item description
										txtRetailQuoteLineDesc.requestFocus();

									}else {
										JOptionPane.showMessageDialog(null, "Discount too large !");	
									}
								}else {
									JOptionPane.showMessageDialog(null, "You must enter a Quantity greater than 0 !");
								}
							}else {
								JOptionPane.showMessageDialog(null, "You must enter an Item !");	
							}
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, "You must enter a number for the Quantity and the Discount % !" + ex);
						}
					}else{
						JOptionPane.showMessageDialog(null, "That Rep Code doesn't exist, please try again !");
					}
					
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnRetailQuoteNewOrderLine.setFont(new Font("Georgia", Font.BOLD, 16));
		btnRetailQuoteNewOrderLine.setBounds(1025, 17, 167, 38);
		panelRetailQuoteLine.add(btnRetailQuoteNewOrderLine);
		
		panelRetailQuoteCurrentQuote = new JPanel();
		panelRetailQuoteCurrentQuote.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRetailQuoteCurrentQuote.setBounds(28, 138, 800, 447);
		panelRetailQuoteCurrentQuote.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(panelRetailQuoteCurrentQuote);
		panelRetailQuoteCurrentQuote.setLayout(null);
		panelRetailQuoteCurrentQuote.setVisible(false);
		
		JLabel lblRetailQuoteCurrentTransaction = new JLabel("RETAIL QUOTATION");
		lblRetailQuoteCurrentTransaction.setFont(new Font("Georgia", Font.BOLD, 18));
		lblRetailQuoteCurrentTransaction.setBounds(10, 11, 221, 31);
		panelRetailQuoteCurrentQuote.add(lblRetailQuoteCurrentTransaction);
		
		JLabel lblRetailQuoteTotalExVatText = new JLabel("TOTAL EX-VAT");
		lblRetailQuoteTotalExVatText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblRetailQuoteTotalExVatText.setBounds(297, 11, 148, 31);
		panelRetailQuoteCurrentQuote.add(lblRetailQuoteTotalExVatText);
		
		JLabel lblRetailQuoteTotalPriceText = new JLabel("TOTAL INC-VAT");
		lblRetailQuoteTotalPriceText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblRetailQuoteTotalPriceText.setBounds(634, 11, 156, 31);
		panelRetailQuoteCurrentQuote.add(lblRetailQuoteTotalPriceText);
		
		lblRetailQuoteTotalPrice = new JLabel("0.00", SwingConstants.RIGHT);
		lblRetailQuoteTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRetailQuoteTotalPrice.setBounds(634, 37, 156, 31);
		panelRetailQuoteCurrentQuote.add(lblRetailQuoteTotalPrice);
		
		lblRetailQuoteTotalExVat = new JLabel("0.00", SwingConstants.RIGHT);
		lblRetailQuoteTotalExVat.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRetailQuoteTotalExVat.setBounds(297, 37, 144, 31);
		panelRetailQuoteCurrentQuote.add(lblRetailQuoteTotalExVat);
		
		JScrollPane scrlRetailQuoteOrder = new JScrollPane();
		scrlRetailQuoteOrder.setBounds(10, 68, 780, 368);
		panelRetailQuoteCurrentQuote.add(scrlRetailQuoteOrder);
		
		tblRetailQuoteOrder = new JTable(){
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
		tblRetailQuoteOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(retailQuote.getOrderList().size() > 1) {
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblRetailQuoteOrder.getSelectedRow();
						
					// We use this row to find the element in the arraylist that the user selected
					retailQuote.getOrderList().remove(row);
						
					// Recalculate everything for the Order
					retailQuote.recalculateAfterDelete(getInvoiceSettings().getReceiptVatRate(), oldOverallDiscount, lblRetailQuoteGrossProfitValue, lblRetailQuoteGrossProfitPercent);
						
					// Load the current order lines onto the screen
					refreshCurrentOrderTable();
				
				} else{
					startNewQuote();
				}
			}
		});
		tblRetailQuoteOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblRetailQuoteOrder.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tblRetailQuoteOrder.setShowGrid(false);
		tblRetailQuoteOrder.setShowHorizontalLines(false);
		tblRetailQuoteOrder.setShowVerticalLines(false);
		tblRetailQuoteOrder.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		scrlRetailQuoteOrder.setViewportView(tblRetailQuoteOrder);
		
		panelRetailQuoteDiscount = new JPanel();
		panelRetailQuoteDiscount.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRetailQuoteDiscount.setBounds(877, 292, 114, 91);
		panelRetailQuoteDiscount.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelRetailQuoteDiscount);
		panelRetailQuoteDiscount.setLayout(null);
		panelRetailQuoteDiscount.setVisible(false);
		
		JLabel lblRetailQuoteDiscountPercentage = new JLabel("Discount %");
		lblRetailQuoteDiscountPercentage.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRetailQuoteDiscountPercentage.setBounds(10, 11, 104, 25);
		panelRetailQuoteDiscount.add(lblRetailQuoteDiscountPercentage);
		
		txtRetailQuoteDiscount = new JTextField();
		txtRetailQuoteDiscount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {

					retailQuote.recalculateDiscount(txtRetailQuoteDiscount, getInvoiceSettings().getReceiptVatRate(), oldOverallDiscount, lblRetailQuoteGrossProfitValue, lblRetailQuoteGrossProfitPercent);
					
					// Load the current Order table with the Order Lines
					String columns[] = {"ITEM", "DESCRIPTION", "QTY", "PRICE", "DISC", "TOTAL"};
					DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
					
					for(RetailOrderLine o : retailQuote.getOrderList()){
						String tableCode= o.getItemCode();
						String tableDesc = o.getItemDescription();
						String tableQty = o.getOrderQty();
						String tableItemPrice = o.getItemPrice();
						String tableItemDiscount = o.getDiscountPercent() + " %";
						String tableOrderPrice = o.getValueExVat();
						Object[] line = {tableCode, tableDesc, tableQty, tableItemPrice, tableItemDiscount, tableOrderPrice};
						tableModel.addRow(line);
					}
					tblRetailQuoteOrder.setModel(tableModel);
					lblRetailQuoteTotalPrice.setText("\u20AC " + String.valueOf(retailQuote.getTotalPreRounding()));
					lblRetailQuoteTotalExVat.setText("\u20AC " + retailQuote.getTotalExVat());
			
			
					// Set the column widths for the table
					tblRetailQuoteOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					columnModelOrder = tblRetailQuoteOrder.getColumnModel();
					columnModelOrder.getColumn(0).setPreferredWidth(90);
					columnModelOrder.getColumn(1).setPreferredWidth(409);
					columnModelOrder.getColumn(2).setPreferredWidth(40);
					columnModelOrder.getColumn(3).setPreferredWidth(70);
					columnModelOrder.getColumn(4).setPreferredWidth(70);
					columnModelOrder.getColumn(5).setPreferredWidth(83);

					
				}catch(NumberFormatException ex) {
					txtRetailQuoteDiscount.setText("0.00");
					retailQuote.recalculateDiscount(txtRetailQuoteDiscount, getInvoiceSettings().getReceiptVatRate(), oldOverallDiscount, lblRetailQuoteGrossProfitValue, lblRetailQuoteGrossProfitPercent);
					JOptionPane.showMessageDialog(null, "You must enter a number for the Overall Discount!");
					
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Exception: " + ex);
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				oldOverallDiscount = txtRetailQuoteDiscount.getText();
			}
		});
		txtRetailQuoteDiscount.setBounds(33, 47, 44, 28);
		panelRetailQuoteDiscount.add(txtRetailQuoteDiscount);
		txtRetailQuoteDiscount.setColumns(10);
		
		panelRetailQuoteRounding = new JPanel();
		panelRetailQuoteRounding.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRetailQuoteRounding.setBounds(854, 204, 164, 81);
		panelRetailQuoteRounding.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelRetailQuoteRounding);
		panelRetailQuoteRounding.setLayout(null);
		panelRetailQuoteRounding.setVisible(false);
		
		JLabel lblRetailQuoteRoundText = new JLabel("Round");
		lblRetailQuoteRoundText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRetailQuoteRoundText.setBounds(55, 11, 55, 17);
		panelRetailQuoteRounding.add(lblRetailQuoteRoundText);
		
		JLabel lblRetailQuoteRoundUp = new JLabel("Round Up");
		lblRetailQuoteRoundUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String beforeRound = retailQuote.getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(String.valueOf(Float.parseFloat(beforeRound.substring(0, theDot)) + 1));
				retailQuote.setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				retailQuote.setRounding(String.valueOf(rounding));

				// Save the rounding and post Rounding values if the invoice has been printed
				if(retailQuote.getReceiptNo() != 0) {
					try {
						
						getOrderDao().updateRoundingHeader("quoteheader", rounding, retailQuote.getTotalPostRounding(), retailQuote.getReceiptNo());
	
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblRetailQuoteTotalPrice.setText("\u20AC " + retailQuote.getTotalPostRounding());

			}
		});
		lblRetailQuoteRoundUp.setBounds(36, 39, 35, 31);
		panelRetailQuoteRounding.add(lblRetailQuoteRoundUp);
		lblRetailQuoteRoundUp.setIcon(imageIconUpArrow);
		
		JLabel lblRetailQuoteRoundDown = new JLabel("Round Down");
		lblRetailQuoteRoundDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String beforeRound = retailQuote.getTotalPreRounding();
				int theDot = beforeRound.indexOf(".");
				String afterRound = Utilities.stringToDec(beforeRound.substring(0, theDot));
				retailQuote.setTotalPostRounding(afterRound);
				float rounding = Utilities.floatToNumDec(Float.parseFloat(afterRound) - Float.parseFloat(beforeRound),2);
				retailQuote.setRounding(String.valueOf(rounding));

				// Save the rounding and post Rounding values if the invoice has been printed
				if(retailQuote.getReceiptNo() != 0) {
					try {

						getOrderDao().updateRoundingHeader("quoteheader", rounding, retailQuote.getTotalPostRounding(), retailQuote.getReceiptNo());
	
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);					
					}
				}
						
				// Update the screen
				lblRetailQuoteTotalPrice.setText("\u20AC " + retailQuote.getTotalPostRounding());
			}
		});
		lblRetailQuoteRoundDown.setBounds(92, 39, 35, 31);
		panelRetailQuoteRounding.add(lblRetailQuoteRoundDown);
		lblRetailQuoteRoundDown.setIcon(imageIconDownArrow);
		
		panelRetailQuoteGrossProfit = new JPanel();
		panelRetailQuoteGrossProfit.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRetailQuoteGrossProfit.setBounds(855, 443, 163, 142);
		panelRetailQuoteGrossProfit.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelRetailQuoteGrossProfit);
		panelRetailQuoteGrossProfit.setLayout(null);
		panelRetailQuoteGrossProfit.setVisible(false);
		
		JLabel lblRetailQuoteGrossProfitText = new JLabel("Gross Profit");
		lblRetailQuoteGrossProfitText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblRetailQuoteGrossProfitText.setBounds(27, 23, 111, 24);
		panelRetailQuoteGrossProfit.add(lblRetailQuoteGrossProfitText);
		
		lblRetailQuoteGrossProfitValue = new JLabel("\u20AC 0.00", SwingConstants.RIGHT);
		lblRetailQuoteGrossProfitValue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRetailQuoteGrossProfitValue.setBounds(25, 58, 102, 24);
		panelRetailQuoteGrossProfit.add(lblRetailQuoteGrossProfitValue);
		
		lblRetailQuoteGrossProfitPercent = new JLabel("0.00 %", SwingConstants.RIGHT);
		lblRetailQuoteGrossProfitPercent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRetailQuoteGrossProfitPercent.setBounds(25, 92, 102, 24);
		panelRetailQuoteGrossProfit.add(lblRetailQuoteGrossProfitPercent);
		
		panelRetailQuoteNewQuote = new JPanel();
		panelRetailQuoteNewQuote.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRetailQuoteNewQuote.setBounds(1046, 113, 199, 71);
		panelRetailQuoteNewQuote.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(panelRetailQuoteNewQuote);
		panelRetailQuoteNewQuote.setLayout(null);
		panelRetailQuoteNewQuote.setVisible(false);
		
		JButton btnRetailQuoteNewQuote = new JButton("NEW QUOTE");
		btnRetailQuoteNewQuote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				startNewQuote();
			}
		});
		btnRetailQuoteNewQuote.setFont(new Font("Georgia", Font.BOLD, 16));
		btnRetailQuoteNewQuote.setBounds(17, 17, 167, 38);
		btnRetailQuoteNewQuote.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelRetailQuoteNewQuote.add(btnRetailQuoteNewQuote);
		
		panelRetailQuotePrintQuote = new JPanel();
		panelRetailQuotePrintQuote.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRetailQuotePrintQuote.setBounds(1046, 204, 199, 71);
		panelRetailQuotePrintQuote.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(panelRetailQuotePrintQuote);
		panelRetailQuotePrintQuote.setLayout(null);
		panelRetailQuotePrintQuote.setVisible(false);
		
		JButton btnRetailQuotePrintQuote = new JButton("PRINT QUOTE");
		btnRetailQuotePrintQuote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				retailQuote.setOrderLinesToPrint(0);
								
				try {
					retailQuote.printOrder(getInvoiceSettings(), null, null, null);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnRetailQuotePrintQuote.setFont(new Font("Georgia", Font.BOLD, 16));
		btnRetailQuotePrintQuote.setBounds(17, 17, 167, 38);
		btnRetailQuotePrintQuote.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelRetailQuotePrintQuote.add(btnRetailQuotePrintQuote);
		
		panelRetailQuoteCustDetails = new JPanel();
		panelRetailQuoteCustDetails.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRetailQuoteCustDetails.setBounds(1046, 295, 199, 71);
		panelRetailQuoteCustDetails.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(panelRetailQuoteCustDetails);
		panelRetailQuoteCustDetails.setLayout(null);
		panelRetailQuoteCustDetails.setVisible(false);
		
		JButton btnRetailQuoteCustDetails = new JButton("CUST DETAILS");
		btnRetailQuoteCustDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CustomerDetails(retailQuote);
			}
		});
		btnRetailQuoteCustDetails.setFont(new Font("Georgia", Font.BOLD, 16));
		btnRetailQuoteCustDetails.setBounds(17, 17, 167, 38);
		btnRetailQuoteCustDetails.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelRetailQuoteCustDetails.add(btnRetailQuoteCustDetails);
		
		JLabel lblRetailQuoteLogo = new JLabel("New label");
		lblRetailQuoteLogo.setBounds(1046, 385, 199, 200);
		// Put Logo image on order tab by adding an image to the Logo Label
		lblRetailQuoteLogo.setIcon(imageIcon);
		getContentPane().add(lblRetailQuoteLogo);
	
	}

	
	/**
	 * This method takes the item selected from the item dropdown and fills textfields with it's values (code, description,price), it also 
	 * retrieves the item's trade price and cost price and enables the quantity and discount fields.
	 */
	public void ordLineItemSelected() {
		try {
		
			// We use the table's getSelectedRow() method to find the row that the User selected. 
			int row = tblRetailQuoteItemDescription.getSelectedRow();
	
			// Then we can get the description of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
			// row and column that we require.
			// This returns an Object so we get the toString of it.
			String descString = tblRetailQuoteItemDescription.getModel().getValueAt(row, 0).toString();
			
			Item selectedItem = getProductDao().findItemByDesc(descString);
			
			if(selectedItem.getItemCode() != null){
				// Put selected item's code, description and price into the oderline entry fields and Enable the quantity+discount fields and make the enter button visible. 
				txtRetailQuoteLineCode.setText(selectedItem.getItemCode());
				txtRetailQuoteLineDesc.setText(selectedItem.getItemDescription());
				txtRetailQuoteLinePrice.setText(Utilities.stringToDec( String.valueOf(selectedItem.getRetailPrice()) ));
				RetailQuoteTradeLinePrice = selectedItem.getTradePrice();
				txtRetailQuoteLineCost = selectedItem.getCostPrice();
				txtRetailQuoteLineQty.setEnabled(true);
				txtRetailQuoteLineDisc.setEnabled(true);
			}
			
			txtRetailQuoteLineQty.requestFocus();
			panelRetailQuoteItemDescriptionChoice.setVisible(false);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	
	
	/**
	 * This method loads the current retail quote lines into the current quote table that the user sees on the Retail Quote Screen. 
	 */
	public void refreshCurrentOrderTable(){
			
		String columns[] = {"ITEM", "DESCRIPTION", "QTY", "PRICE", "DISC", "TOTAL"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		
		for(RetailOrderLine o : retailQuote.getOrderList()){
			String tableCode= o.getItemCode();
			String tableDesc = o.getItemDescription();
			String tableQty = o.getOrderQty();
			String tableItemPrice = o.getItemPrice();
			String tableItemDiscount = o.getDiscountPercent() + " %";
			String tableOrderPrice = o.getValueExVat();
			Object[] line = {tableCode, tableDesc, tableQty, tableItemPrice, tableItemDiscount, tableOrderPrice};
			tableModel.addRow(line);
		}
		tblRetailQuoteOrder.setModel(tableModel);
		lblRetailQuoteTotalPrice.setText("\u20AC " + String.valueOf(retailQuote.getTotalPreRounding()));
		lblRetailQuoteTotalExVat.setText("\u20AC " + retailQuote.getTotalExVat());
	
	
		// Set the column widths for the table
		tblRetailQuoteOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnModelOrder = tblRetailQuoteOrder.getColumnModel();
		columnModelOrder.getColumn(0).setPreferredWidth(90);
		columnModelOrder.getColumn(1).setPreferredWidth(409);
		columnModelOrder.getColumn(2).setPreferredWidth(40);
		columnModelOrder.getColumn(3).setPreferredWidth(70);	
		columnModelOrder.getColumn(4).setPreferredWidth(70);
		columnModelOrder.getColumn(5).setPreferredWidth(83);
	}

	
	/**
	 * This method removes the current Retail Quote, clears all of the screen's textfields and hides panels so as to start a new Retail Quote
	 */
	public void startNewQuote() {
		
		// Make the current Current Order panel, Print Order panel, DiscountPanel visible and the GrossProfit panel invisible
		panelRetailQuoteCurrentQuote.setVisible(false);
		panelRetailQuotePrintQuote.setVisible(false);
		panelRetailQuoteDiscount.setVisible(false);
		panelRetailQuoteGrossProfit.setVisible(false);
		
		// Remove the current order from the order object
		retailQuote= null;
		
		// Blank the current order line entry fields
		txtRetailQuoteLineCode.setText(null);
		txtRetailQuoteLineDesc.setText(null);
		txtRetailQuoteLinePrice.setText(null);
		txtRetailQuoteLineQty.setText(null);
		txtRetailQuoteLineDisc.setText(null);
		
		// Disable quantity+discount fields and make ENTER button invisible - because they may be accessible when NEW ORDER button pressed 
		txtRetailQuoteLineQty.setEnabled(false);
		txtRetailQuoteLineDisc.setEnabled(false);
		btnRetailQuoteNewOrderLine.setVisible(false);
		
		// Make the Current Order, Print Order, DiscountPanel, GrossProfit, Rounding, CustDetails and NewOrder panels invisible
		panelRetailQuoteCurrentQuote.setVisible(false);
		panelRetailQuotePrintQuote.setVisible(false);
		panelRetailQuoteDiscount.setVisible(false);
		panelRetailQuoteGrossProfit.setVisible(false);
		panelRetailQuoteRounding.setVisible(false);
		panelRetailQuoteCustDetails.setVisible(false);
		panelRetailQuoteNewQuote.setVisible(false);
		
		// Blank the Overall Discount field
		txtRetailQuoteDiscount.setText("");
		
		// Blank the Rep Code field, enable it and make it editable
		txtRetailQuoteLineRep.setEditable(true);
		txtRetailQuoteLineRep.setEnabled(true);
		txtRetailQuoteLineRep.setText("");
	}
		
}