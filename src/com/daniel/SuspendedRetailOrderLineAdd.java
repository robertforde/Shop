package com.daniel;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daniel.dao.OrderDao;
import com.daniel.dao.OrderDaoImpl;
import com.daniel.dao.ProductDao;
import com.daniel.dao.ProductDaoImpl;
import com.daniel.dao.ReceiptSettingsDao;
import com.daniel.dao.ReceiptSettingsDaoImpl;
import com.daniel.dao.StaffDao;
import com.daniel.dao.StaffDaoImpl;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.Item;
import com.daniel.model.Staff;
import com.daniel.model.SuspendedOrder;
import com.daniel.model.SuspendedOrderLine;
import com.daniel.utilities.Utilities;

/**
 * This class designs the screen and allows the User to add order lines onto a suspended retail order 
 * @author Robert Forde
 *
 */
public class SuspendedRetailOrderLineAdd {

	private SuspendedOrder suspendedOrder;
	private SuspendedOrderLine suspendedOrderLine;
	private InvoiceSettings invoiceSettings;
	
	private SuspendedOrdersScreen suspendedOrdersScreen;
	private SuspendedProcessScreen suspendedProcessScreen;

	private JFrame addRetailOrderLine;
	
	private JTextField txtSuspendLineCode;
	private JTextField txtSuspendLineDesc;
	private JTextField txtSuspendLinePrice;
	private JTextField txtSuspendLineQty;
	private JTextField txtSuspendLineDisc;
	private JTextField txtSuspendLineRep;
	private JTextField txtSuspendOrderDiscount;
	
	private JLabel lblSuspendGrossProfitValue;
	private JLabel lblSuspendGrossProfitPercent;
	
	private JButton btnSuspendNewOrderLine;
	
	private JPanel panelSuspendProcessItemDescriptionChoice;
	private JPanel panelSuspendGrossProfit;
	private JPanel panelSuspendOrderDiscount;
	
	private JTable tblSuspendProcessItemDescription;
	
	private float txtSuspendLineCost;
	private float SuspendTradeLinePrice;
	private String oldOverallDiscount;

	public static AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
	
	private ProductDao productDao;
	private StaffDao staffDao;
	private OrderDao orderDao;
	private ReceiptSettingsDao receiptSettingsDao;

	
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
	
	public ReceiptSettingsDao getReceiptSettingsDao() {
		return receiptSettingsDao;
	}

	public void setReceiptSettingsDao(ReceiptSettingsDao receiptSettingsDao) {
		this.receiptSettingsDao = receiptSettingsDao;
	}

	public SuspendedOrder getSuspendedOrder() {
		return suspendedOrder;
	}

	public void setSuspendedOrder(SuspendedOrder suspendedOrder) {
		this.suspendedOrder = suspendedOrder;
	}

	public SuspendedOrderLine getSuspendedOrderLine() {
		return suspendedOrderLine;
	}

	public void setSuspendedOrderLine(SuspendedOrderLine suspendedOrderLine) {
		this.suspendedOrderLine = suspendedOrderLine;
	}

	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}
		
	public SuspendedOrdersScreen getSuspendedOrdersScreen() {
		return suspendedOrdersScreen;
	}

	public void setSuspendedOrdersScreen(SuspendedOrdersScreen suspendedOrdersScreen) {
		this.suspendedOrdersScreen = suspendedOrdersScreen;
	}
	
	public SuspendedProcessScreen getSuspendedProcessScreen() {
		return suspendedProcessScreen;
	}

	public void setSuspendedProcessScreen(
			SuspendedProcessScreen suspendedProcessScreen) {
		this.suspendedProcessScreen = suspendedProcessScreen;
	}
	
	public JFrame getAddRetailOrderLine() {
		return addRetailOrderLine;
	}

	public void setAddRetailOrderLine(JFrame addRetailOrderLine) {
		this.addRetailOrderLine = addRetailOrderLine;
	}

	
	/**
	 * Constructs the Screen for Adding order lines onto a Suspended Retail Order
	 * @param suspendedOrder The Suspended Order that lines are to added to
	 * @param suspendedOrdersScreen A reference to the Suspended Orders Screen so the balance can be updated
	 * @param suspendedProcessScreen A reference to the Suspended Process Screen so the balance can be updated
	 */
	public SuspendedRetailOrderLineAdd(SuspendedOrder suspendedOrder, SuspendedOrdersScreen suspendedOrdersScreen, SuspendedProcessScreen suspendedProcessScreen){
		
		setProductDao(Dan.ctx.getBean("productDaoImpl", ProductDaoImpl.class));
		setStaffDao(Dan.ctx.getBean("staffDaoImpl", StaffDaoImpl.class));
		setOrderDao(Dan.ctx.getBean("orderDaoImpl", OrderDaoImpl.class));
		setReceiptSettingsDao(Dan.ctx.getBean("receiptSettingsDaoImpl", ReceiptSettingsDaoImpl.class));
		
		setSuspendedOrder(suspendedOrder);
		setSuspendedOrdersScreen(suspendedOrdersScreen);
		setSuspendedProcessScreen(suspendedProcessScreen);
		setInvoiceSettings(getReceiptSettingsDao().retrieveDatabasePrintSettings(new InvoiceSettings()));
		
		addRetailOrderLine = new JFrame();
		addRetailOrderLine.getContentPane().setBackground(Color.DARK_GRAY);
		addRetailOrderLine.setBackground(Color.DARK_GRAY);
		addRetailOrderLine.getContentPane().setLayout(null);
		addRetailOrderLine.setVisible(true);
		addRetailOrderLine.setBounds(30, 150, 1294, 534);
	
		JPanel panelSuspendProcessRetailAddLine = new JPanel();
		panelSuspendProcessRetailAddLine.setForeground(Color.BLACK);
		panelSuspendProcessRetailAddLine.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendProcessRetailAddLine.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessRetailAddLine.setBounds(28, 21, 1217, 71);
		addRetailOrderLine.getContentPane().add(panelSuspendProcessRetailAddLine);
		panelSuspendProcessRetailAddLine.setLayout(null);
		
		JLabel lblSuspendItemCodeText = new JLabel("Item Code");
		lblSuspendItemCodeText.setBounds(152, 9, 132, 18);
		lblSuspendItemCodeText.setForeground(Color.BLACK);
		lblSuspendItemCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendProcessRetailAddLine.add(lblSuspendItemCodeText);
		
		txtSuspendLineCode = new JTextField();
		txtSuspendLineCode.setBounds(152, 31, 132, 28);
		txtSuspendLineCode.setEditable(false);
		txtSuspendLineCode.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendLineCode.setColumns(10);
		txtSuspendLineCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessRetailAddLine.add(txtSuspendLineCode);
		
		JLabel lblSuspendItemDescText = new JLabel("Item Description");
		lblSuspendItemDescText.setBounds(318, 9, 175, 18);
		lblSuspendItemDescText.setForeground(Color.BLACK);
		lblSuspendItemDescText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendProcessRetailAddLine.add(lblSuspendItemDescText);
		
		txtSuspendLineDesc = new JTextField();
		txtSuspendLineDesc.setBounds(318, 31, 341, 28);
		txtSuspendLineDesc.addKeyListener (new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				
				// Disable the normal tab key focus on the Item Description textbox
				txtSuspendLineDesc.setFocusTraversalKeysEnabled(false);
				
				// Uppercase the entered description
				txtSuspendLineDesc.setText(txtSuspendLineDesc.getText().toUpperCase());
				
				// Make ENTER button and the list of descriptions invisible
				panelSuspendProcessItemDescriptionChoice.setVisible(false);
				btnSuspendNewOrderLine.setVisible(false);
				
				if (txtSuspendLineDesc.getText().equals("") == false) {  
					// Search in database for items that have this exact description in database and if found enable qty textfield and fill in code and price
					try {
							
						// Find item based on it's description
						Item foundItem = getProductDao().findItemByDesc(txtSuspendLineDesc.getText());
	
						if(foundItem.getItemCode() != null){
							txtSuspendLineCode.setText(foundItem.getItemCode());
							txtSuspendLinePrice.setText(Utilities.stringToDec(String.valueOf(foundItem.getRetailPrice())));
							txtSuspendLineCost = foundItem.getCostPrice();
							SuspendTradeLinePrice = foundItem.getTradePrice();
							txtSuspendLineQty.setEnabled(true);
							txtSuspendLineDisc.setEnabled(true);
						} else {
							txtSuspendLineCode.setText("");
							txtSuspendLinePrice.setText("");
							txtSuspendLineQty.setText("");
							txtSuspendLineQty.setEnabled(false);
							txtSuspendLineDisc.setText("");
							txtSuspendLineDisc.setEnabled(false);
						
							// Exact match not found so bring up list of descriptions that contain this description and are not set as deleted
							List<Item> itemList = getProductDao().findPossibleItemMatches(txtSuspendLineDesc.getText().toUpperCase());
							
							if(itemList.size() != 0) {
								
								String columns[] = {"DESCRIPTION"};
								DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
								
								for(Item item : itemList){
									String tableDesc = item.getItemDescription();
									Object[] line = {tableDesc};
									tableModel.addRow(line);
								}
								
								tblSuspendProcessItemDescription.setModel(tableModel);
								
								tblSuspendProcessItemDescription.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
								TableColumnModel columnModel = tblSuspendProcessItemDescription.getColumnModel();
								columnModel.getColumn(0).setPreferredWidth(340);
								panelSuspendProcessItemDescriptionChoice.setVisible(true);
								
								// Set the tab key to the item description possible matches table
								if(arg0.getKeyCode() == KeyEvent.VK_TAB){
									tblSuspendProcessItemDescription.requestFocus();
								} 
								
							} else {
								txtSuspendLineCode.setText("");
								txtSuspendLinePrice.setText("");
								txtSuspendLineQty.setText("");
								txtSuspendLineQty.setEnabled(false);
								txtSuspendLineDisc.setText("");
								txtSuspendLineDisc.setEnabled(false);
							}
								
						}
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
					}
				}else {
					// Make ENTER button and the list of descriptions invisible
					panelSuspendProcessItemDescriptionChoice.setVisible(false);
					btnSuspendNewOrderLine.setVisible(false);
				}
			}
		});
		txtSuspendLineDesc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendLineDesc.setColumns(50);
		txtSuspendLineDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessRetailAddLine.add(txtSuspendLineDesc);
		
		JLabel lblSuspendItemPriceText = new JLabel("Item Price");
		lblSuspendItemPriceText.setBounds(678, 9, 104, 18);
		lblSuspendItemPriceText.setForeground(Color.BLACK);
		lblSuspendItemPriceText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendProcessRetailAddLine.add(lblSuspendItemPriceText);
		
		txtSuspendLinePrice = new JTextField();
		txtSuspendLinePrice.setBounds(678, 31, 132, 28);
		txtSuspendLinePrice.setEditable(false);
		txtSuspendLinePrice.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendLinePrice.setColumns(10);
		txtSuspendLinePrice.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessRetailAddLine.add(txtSuspendLinePrice);
		
		JLabel lblSuspendItemQtyText = new JLabel("Quantity");
		lblSuspendItemQtyText.setBounds(828, 9, 75, 18);
		lblSuspendItemQtyText.setForeground(Color.BLACK);
		lblSuspendItemQtyText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendProcessRetailAddLine.add(lblSuspendItemQtyText);
		
		txtSuspendLineQty = new JTextField();
		txtSuspendLineQty.setBounds(828, 31, 57, 28);
		txtSuspendLineQty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				panelSuspendProcessItemDescriptionChoice.setVisible(false);
				btnSuspendNewOrderLine.setVisible(true);
			}
		});
		txtSuspendLineQty.setEnabled(false);
		txtSuspendLineQty.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendLineQty.setColumns(10);
		txtSuspendLineQty.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessRetailAddLine.add(txtSuspendLineQty);
		
		btnSuspendNewOrderLine = new JButton("ENTER");
		btnSuspendNewOrderLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		btnSuspendNewOrderLine.setBounds(1025, 17, 167, 38);
		btnSuspendNewOrderLine.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// First check that the Rep is valid
				try {
					
					Staff checkStaff = getStaffDao().staffExists(txtSuspendLineRep.getText());
					
					// If this repCode is okay
					if(checkStaff.getRepCode() != null) {
	
						txtSuspendLineRep.setEditable(false);
						txtSuspendLineRep.setEnabled(false);
						// Check if Order exists and if not create one
						if(getSuspendedOrder() == null){
							setSuspendedOrder(new SuspendedOrder());
							getSuspendedOrder().setOrderDate(new Date());
							getSuspendedOrder().setSaleType("Retail");
							getSuspendedOrder().setRepNo(txtSuspendLineRep.getText());
						}
	
						try {
	
							// Check if an item is entered
							if(!txtSuspendLineDesc.getText().isEmpty()) {
								// Check that the Quantity entered is an Integer and > 0.
								if(Integer.parseInt(txtSuspendLineQty.getText()) > 0) {
									
									float retailLinePrice = Float.parseFloat(txtSuspendLinePrice.getText());
									String strDiscountLinePrice = txtSuspendLineDisc.getText().equals("")?"0.00":txtSuspendLineDisc.getText();
									float discountLinePrice = retailLinePrice * Float.parseFloat(strDiscountLinePrice) / 100;
									
									if(retailLinePrice - discountLinePrice >= (SuspendTradeLinePrice)+0.01) {
															
										// Create a SuspendedOrderLine and call it's addRetailOrderLine() method to calculate it's values and save it   
										suspendedOrderLine = new SuspendedOrderLine();
	
										suspendedOrderLine.addRetailOrderLine(txtSuspendLineCode, txtSuspendLineDesc, txtSuspendLineQty, txtSuspendLinePrice, txtSuspendLineDisc, txtSuspendOrderDiscount, 
																			lblSuspendGrossProfitValue, lblSuspendGrossProfitPercent, getSuspendedOrder(), SuspendTradeLinePrice,
																			txtSuspendLineCost, getInvoiceSettings().getReceiptVatRate(), true);
										
										// Refresh the SuspendedOrdersScreen and the SuspendedProcessScreen
										getSuspendedProcessScreen().refreshSuspendedLines();
										getSuspendedProcessScreen().getLblSuspendProcessBalance().setText(Utilities.floatToString2Dec(getSuspendedOrder().getBalance()));
										getSuspendedOrdersScreen().refreshSuspendedOrderHeaders();
										
										// Clear the order textfield boxes and disable the quantity+discount textfields and make the ENTER button invisible
										txtSuspendLineCode.setText("");
										txtSuspendLineDesc.setText("");
										txtSuspendLinePrice.setText("");
										txtSuspendLineQty.setText("");
										txtSuspendLineQty.setEnabled(false);
										txtSuspendLineDisc.setText("");
										txtSuspendLineDisc.setEnabled(false);
										btnSuspendNewOrderLine.setVisible(false);
											
										// Set the focus back to the item description
										txtSuspendLineDesc.requestFocus();
									
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
		btnSuspendNewOrderLine.setFont(new Font("Georgia", Font.BOLD, 16));
		btnSuspendNewOrderLine.setVisible(false);
		panelSuspendProcessRetailAddLine.add(btnSuspendNewOrderLine);
	
		JLabel lblSuspendItemDiscText = new JLabel("Discount %");
		lblSuspendItemDiscText.setForeground(Color.BLACK);
		lblSuspendItemDiscText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendItemDiscText.setBounds(913, 9, 104, 18);
		panelSuspendProcessRetailAddLine.add(lblSuspendItemDiscText);
		
		txtSuspendLineDisc = new JTextField();
		txtSuspendLineDisc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendLineDisc.setEnabled(false);
		txtSuspendLineDisc.setColumns(10);
		txtSuspendLineDisc.setBounds(913, 31, 57, 28);
		txtSuspendLineDisc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessRetailAddLine.add(txtSuspendLineDisc);
		
		JLabel lblSuspendRepCodeText = new JLabel("Rep Code");
		lblSuspendRepCodeText.setForeground(Color.BLACK);
		lblSuspendRepCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendRepCodeText.setBounds(34, 9, 96, 18);
		panelSuspendProcessRetailAddLine.add(lblSuspendRepCodeText);
		
		txtSuspendLineRep = new JTextField();
		txtSuspendLineRep.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendLineRep.setBounds(34, 31, 86, 28);
		txtSuspendLineRep.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessRetailAddLine.add(txtSuspendLineRep);
		txtSuspendLineRep.setColumns(10);
		
		txtSuspendLineDisc.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				panelSuspendProcessItemDescriptionChoice.setVisible(false);
				btnSuspendNewOrderLine.setVisible(true);
			}
		});
		
		panelSuspendProcessItemDescriptionChoice = new JPanel();
		panelSuspendProcessItemDescriptionChoice.setBounds(350, 91, 338, 398);
		addRetailOrderLine.getContentPane().add(panelSuspendProcessItemDescriptionChoice);
		panelSuspendProcessItemDescriptionChoice.setLayout(null);
		
		JScrollPane scrlPanelTblSuspendProcessItemDescription = new JScrollPane();
		scrlPanelTblSuspendProcessItemDescription.setBounds(0, 0, 337, 397);
		panelSuspendProcessItemDescriptionChoice.add(scrlPanelTblSuspendProcessItemDescription);
		
		// When creating the JTable for the description dropdown I over-ride the isCellEditable method to make the table un-editable
		tblSuspendProcessItemDescription = new JTable(){
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
		scrlPanelTblSuspendProcessItemDescription.setViewportView(tblSuspendProcessItemDescription);
		
		tblSuspendProcessItemDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// If enter pressed call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make ENTER visible 
				if(arg0.getKeyCode() == 10) {
					ordLineItemSelected();					
				}
			}
		});
		tblSuspendProcessItemDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
								
				// Call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make the enter button visible
				ordLineItemSelected();
			}
		});
		tblSuspendProcessItemDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSuspendProcessItemDescription.setCellSelectionEnabled(true);
		tblSuspendProcessItemDescription.setColumnSelectionAllowed(false);
		
		// Initially make the description dropdown invisible
		panelSuspendProcessItemDescriptionChoice.setVisible(false);
	
		panelSuspendOrderDiscount = new JPanel();
		panelSuspendOrderDiscount.setBorder(new BevelBorder(BevelBorder.RAISED, null, UIManager.getColor("Button.shadow"), UIManager.getColor("Button.foreground"), UIManager.getColor("Button.darkShadow")));
		panelSuspendOrderDiscount.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendOrderDiscount.setBounds(880, 226, 114, 91);
		addRetailOrderLine.add(panelSuspendOrderDiscount);
		panelSuspendOrderDiscount.setLayout(null);
		panelSuspendOrderDiscount.setVisible(false);
		
		txtSuspendOrderDiscount = new JTextField();
		txtSuspendOrderDiscount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {

					getSuspendedOrder().recalculateDiscount(txtSuspendOrderDiscount, getInvoiceSettings().getReceiptVatRate(), oldOverallDiscount, lblSuspendGrossProfitValue, lblSuspendGrossProfitPercent);
					
				}catch(NumberFormatException ex) {
					txtSuspendOrderDiscount.setText("0.00");
					getSuspendedOrder().recalculateDiscount(txtSuspendOrderDiscount, getInvoiceSettings().getReceiptVatRate(), oldOverallDiscount, lblSuspendGrossProfitValue, lblSuspendGrossProfitPercent);
					JOptionPane.showMessageDialog(null, "You must enter a number for the Overall Discount!");
					
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Exception: " + ex);
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				oldOverallDiscount = txtSuspendOrderDiscount.getText();
			}
		});

		txtSuspendOrderDiscount.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendOrderDiscount.setBounds(33, 47, 44, 28);
		panelSuspendOrderDiscount.add(txtSuspendOrderDiscount);
		txtSuspendOrderDiscount.setColumns(10);
		
		JLabel lblCustDiscountPercentage = new JLabel("Discount %");
		lblCustDiscountPercentage.setFont(new Font("Georgia", Font.BOLD, 16));
		lblCustDiscountPercentage.setBounds(10, 11, 104, 25);
		panelSuspendOrderDiscount.add(lblCustDiscountPercentage);

		
		panelSuspendGrossProfit = new JPanel();
		panelSuspendGrossProfit.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendGrossProfit.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendGrossProfit.setBounds(1054, 159, 163, 142);
		addRetailOrderLine.getContentPane().add(panelSuspendGrossProfit);
		panelSuspendGrossProfit.setLayout(null);
		panelSuspendGrossProfit.setVisible(false);
		
		lblSuspendGrossProfitValue = new JLabel("\u20AC 0.00", SwingConstants.RIGHT);
		lblSuspendGrossProfitValue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendGrossProfitValue.setForeground(Color.BLACK);
		lblSuspendGrossProfitValue.setBounds(25, 58, 102, 24);
		panelSuspendGrossProfit.add(lblSuspendGrossProfitValue);
		
		JLabel lblSuspendGrossProfitText = new JLabel("Gross Profit");
		lblSuspendGrossProfitText.setForeground(Color.BLACK);
		lblSuspendGrossProfitText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendGrossProfitText.setBounds(27, 23, 111, 24);
		panelSuspendGrossProfit.add(lblSuspendGrossProfitText);
		
		lblSuspendGrossProfitPercent = new JLabel("0.00 %", SwingConstants.RIGHT);
		lblSuspendGrossProfitPercent.setForeground(Color.BLACK);
		lblSuspendGrossProfitPercent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendGrossProfitPercent.setBounds(25, 92, 102, 24);
		panelSuspendGrossProfit.add(lblSuspendGrossProfitPercent);


	}

	
	/**
	 * This method takes the item selected from the item dropdown and fills textfields with it's values (code, description,price), it also 
	 * retrieves the item's trade price and cost price and enables the quantity and discount fields.
	 */
	public void ordLineItemSelected() {
		try {
		
			// We use the table's getSelectedRow() method to find the row that the User selected. 
			int row = tblSuspendProcessItemDescription.getSelectedRow();
	
			// Then we can get the description of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
			// row and column that we require.
			// This returns an Object so we get the toString of it.
			String descString = tblSuspendProcessItemDescription.getModel().getValueAt(row, 0).toString();
			
			Item selectedItem = getProductDao().findItemByDesc(descString);
			
			if(selectedItem.getItemCode() != null){
				// Put selected item's code, description and price into the oderline entry fields and Enable the quantity field and make the enter button visible. 
				txtSuspendLineCode.setText(selectedItem.getItemCode());
				txtSuspendLineDesc.setText(selectedItem.getItemDescription());
				txtSuspendLinePrice.setText(Utilities.stringToDec( String.valueOf(selectedItem.getRetailPrice()) ));
				SuspendTradeLinePrice = selectedItem.getTradePrice();
				txtSuspendLineCost = selectedItem.getCostPrice();
				txtSuspendLineQty.setEnabled(true);
				txtSuspendLineDisc.setEnabled(true);
			}
			
			txtSuspendLineQty.requestFocus();
			panelSuspendProcessItemDescriptionChoice.setVisible(false);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}

}
