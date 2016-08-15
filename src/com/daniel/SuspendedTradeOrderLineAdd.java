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
 * This class designs the screen and allows the User to add order lines onto a suspended trade order 
 * @author Robert Forde
 *
 */
public class SuspendedTradeOrderLineAdd {

	private SuspendedOrder suspendedOrder;
	private SuspendedOrderLine suspendedOrderLine;
	private InvoiceSettings invoiceSettings;
	
	private SuspendedOrdersScreen suspendedOrdersScreen;
	private SuspendedProcessScreen suspendedProcessScreen;

	private JFrame addTradeOrderLine;
	
	private JTextField txtSuspendTradeLineCode;
	private JTextField txtSuspendTradeLineDesc;
	private JTextField txtSuspendTradeLinePrice;
	private JTextField txtSuspendTradeLineQty;
	private JTextField txtSuspendTradeLineRep;
	
	private JLabel lblSuspendTradeGrossProfitValue;
	private JLabel lblSuspendTradeGrossProfitPercent;
	
	private JButton btnSuspendNewTradeOrderLine;
	
	private JPanel panelSuspendTradeItemDescriptionChoice;
	private JPanel panelSuspendTradeGrossProfit;
	
	private JTable tblSuspendTradeItemDescription;
	
	private float txtSuspendLineCost;

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
	
	public JFrame getAddTradeOrderLine() {
		return addTradeOrderLine;
	}

	public void setAddTradeOrderLine(JFrame addTradeOrderLine) {
		this.addTradeOrderLine = addTradeOrderLine;
	}

	
	/**
	 * Constructs the Screen for Adding order lines onto a Suspended Trade Order
	 * @param suspendedOrder The Suspended Order that lines are to added to
	 * @param suspendedOrdersScreen A reference to the Suspended Orders Screen so the balance can be updated
	 * @param suspendedProcessScreen A reference to the Suspended Process Screen so the balance can be updated
	 */
	public SuspendedTradeOrderLineAdd(SuspendedOrder suspendedOrder, SuspendedOrdersScreen suspendedOrdersScreen, SuspendedProcessScreen suspendedProcessScreen){
		
		setProductDao(Dan.ctx.getBean("productDaoImpl", ProductDaoImpl.class));
		setStaffDao(Dan.ctx.getBean("staffDaoImpl", StaffDaoImpl.class));
		setOrderDao(Dan.ctx.getBean("orderDaoImpl", OrderDaoImpl.class));
		setReceiptSettingsDao(Dan.ctx.getBean("receiptSettingsDaoImpl", ReceiptSettingsDaoImpl.class));
		
		setSuspendedOrder(suspendedOrder);
		setSuspendedOrdersScreen(suspendedOrdersScreen);
		setSuspendedProcessScreen(suspendedProcessScreen);
		setInvoiceSettings(getReceiptSettingsDao().retrieveDatabasePrintSettings(new InvoiceSettings()));
		
		addTradeOrderLine = new JFrame();
		addTradeOrderLine.getContentPane().setBackground(Color.DARK_GRAY);
		addTradeOrderLine.setBackground(Color.DARK_GRAY);
		addTradeOrderLine.getContentPane().setLayout(null);
		addTradeOrderLine.setVisible(true);
		addTradeOrderLine.setBounds(30, 150, 1294, 534);
		addTradeOrderLine.setTitle("ADD TRADE ORDER LINE: " + getSuspendedOrder().getName() + ": Suspended Order: " + getSuspendedOrder().getReceiptNo());
	
		JPanel panelSuspendProcessTradeAddLine = new JPanel();
		panelSuspendProcessTradeAddLine.setForeground(Color.BLACK);
		panelSuspendProcessTradeAddLine.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendProcessTradeAddLine.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessTradeAddLine.setBounds(28, 21, 1217, 71);
		addTradeOrderLine.getContentPane().add(panelSuspendProcessTradeAddLine);
		panelSuspendProcessTradeAddLine.setLayout(null);
		
		JLabel lblSuspendTradeItemCodeText = new JLabel("Item Code");
		lblSuspendTradeItemCodeText.setBounds(152, 9, 132, 18);
		lblSuspendTradeItemCodeText.setForeground(Color.BLACK);
		lblSuspendTradeItemCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendProcessTradeAddLine.add(lblSuspendTradeItemCodeText);
		
		txtSuspendTradeLineCode = new JTextField();
		txtSuspendTradeLineCode.setBounds(152, 31, 132, 28);
		txtSuspendTradeLineCode.setEditable(false);
		txtSuspendTradeLineCode.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLineCode.setColumns(10);
		txtSuspendTradeLineCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessTradeAddLine.add(txtSuspendTradeLineCode);
		
		JLabel lblSuspendTradeItemDescText = new JLabel("Item Description");
		lblSuspendTradeItemDescText.setBounds(318, 9, 175, 18);
		lblSuspendTradeItemDescText.setForeground(Color.BLACK);
		lblSuspendTradeItemDescText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendProcessTradeAddLine.add(lblSuspendTradeItemDescText);
		
		txtSuspendTradeLineDesc = new JTextField();
		txtSuspendTradeLineDesc.setBounds(318, 31, 341, 28);
		txtSuspendTradeLineDesc.addKeyListener (new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0){
				
				// Disable the normal tab key focus on the Item Description textbox
				txtSuspendTradeLineDesc.setFocusTraversalKeysEnabled(false);
				
				// Uppercase the entered description
				txtSuspendTradeLineDesc.setText(txtSuspendTradeLineDesc.getText().toUpperCase());
				
				// Make ENTER button and the list of descriptions invisible
				panelSuspendTradeItemDescriptionChoice.setVisible(false);
				btnSuspendNewTradeOrderLine.setVisible(false);
				
				if (txtSuspendTradeLineDesc.getText().equals("") == false) {  
					// Search in database for items that have this exact description in database and if found enable qty textfield and fill in code and price
					try {
							
						// Find item based on it's description
						Item foundItem = getProductDao().findItemByDesc(txtSuspendTradeLineDesc.getText());
	
						if(foundItem.getItemCode() != null){
							txtSuspendTradeLineCode.setText(foundItem.getItemCode());
							txtSuspendTradeLinePrice.setText(Utilities.stringToDec(String.valueOf(foundItem.getRetailPrice())));
							txtSuspendLineCost = foundItem.getCostPrice();
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
					btnSuspendNewTradeOrderLine.setVisible(false);
				}
			}
		});
		txtSuspendTradeLineDesc.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLineDesc.setColumns(50);
		txtSuspendTradeLineDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessTradeAddLine.add(txtSuspendTradeLineDesc);
		
		JLabel lblSuspendTradeItemPriceText = new JLabel("Item Price");
		lblSuspendTradeItemPriceText.setBounds(678, 9, 104, 18);
		lblSuspendTradeItemPriceText.setForeground(Color.BLACK);
		lblSuspendTradeItemPriceText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendProcessTradeAddLine.add(lblSuspendTradeItemPriceText);
		
		txtSuspendTradeLinePrice = new JTextField();
		txtSuspendTradeLinePrice.setBounds(678, 31, 132, 28);
		txtSuspendTradeLinePrice.setEditable(false);
		txtSuspendTradeLinePrice.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLinePrice.setColumns(10);
		txtSuspendTradeLinePrice.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessTradeAddLine.add(txtSuspendTradeLinePrice);
		
		JLabel lblSuspendTradeItemQtyText = new JLabel("Quantity");
		lblSuspendTradeItemQtyText.setBounds(828, 9, 75, 18);
		lblSuspendTradeItemQtyText.setForeground(Color.BLACK);
		lblSuspendTradeItemQtyText.setFont(new Font("Georgia", Font.BOLD, 16));
		panelSuspendProcessTradeAddLine.add(lblSuspendTradeItemQtyText);
		
		txtSuspendTradeLineQty = new JTextField();
		txtSuspendTradeLineQty.setBounds(828, 31, 57, 28);
		txtSuspendTradeLineQty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				panelSuspendTradeItemDescriptionChoice.setVisible(false);
				btnSuspendNewTradeOrderLine.setVisible(true);
			}
		});
		txtSuspendTradeLineQty.setEnabled(false);
		txtSuspendTradeLineQty.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLineQty.setColumns(10);
		txtSuspendTradeLineQty.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessTradeAddLine.add(txtSuspendTradeLineQty);
		
		btnSuspendNewTradeOrderLine = new JButton("ENTER");
		btnSuspendNewTradeOrderLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		btnSuspendNewTradeOrderLine.setBounds(1025, 17, 167, 38);
		btnSuspendNewTradeOrderLine.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// First check that the Rep is valid
				try {
					
					Staff checkStaff = getStaffDao().staffExists(txtSuspendTradeLineRep.getText());
					
					// If this repCode is okay
					if(checkStaff.getRepCode() != null) {
	
						txtSuspendTradeLineRep.setEditable(false);
						txtSuspendTradeLineRep.setEnabled(false);
						// Check if Order exists and if not create one
						if(getSuspendedOrder() == null){
							setSuspendedOrder(new SuspendedOrder());
							getSuspendedOrder().setOrderDate(new Date());
							getSuspendedOrder().setSaleType("Retail");
							getSuspendedOrder().setRepNo(txtSuspendTradeLineRep.getText());
						}
	
						try {
	
							// Check if an item is entered
							if(!txtSuspendTradeLineDesc.getText().isEmpty()) {
								// Check that the Quantity entered is an Integer and > 0.
								if(Integer.parseInt(txtSuspendTradeLineQty.getText()) > 0) {
									
										// Create a SuspendedOrderLine and call it's addRetailOrderLine() method to calculate it's values and save it   
										suspendedOrderLine = new SuspendedOrderLine();
	
										suspendedOrderLine.addTradeOrderLine(txtSuspendTradeLineCode, txtSuspendTradeLineDesc, txtSuspendTradeLineQty, txtSuspendTradeLinePrice,  
																			lblSuspendTradeGrossProfitValue, lblSuspendTradeGrossProfitPercent, getSuspendedOrder(), 
																			txtSuspendLineCost, getInvoiceSettings().getReceiptVatRate(), true);
										
										// Refresh the SuspendedOrdersScreen and the SuspendedProcessScreen
										getSuspendedProcessScreen().refreshSuspendedLines();
										getSuspendedProcessScreen().getLblSuspendProcessBalance().setText(Utilities.floatToString2Dec(getSuspendedOrder().getBalance()));
										getSuspendedOrdersScreen().refreshSuspendedOrderHeaders();
										
										// Clear the order textfield boxes and disable the quantity+discount textfields and make the ENTER button invisible
										txtSuspendTradeLineCode.setText("");
										txtSuspendTradeLineDesc.setText("");
										txtSuspendTradeLinePrice.setText("");
										txtSuspendTradeLineQty.setText("");
										txtSuspendTradeLineQty.setEnabled(false);
										btnSuspendNewTradeOrderLine.setVisible(false);
											
										// Set the focus back to the item description
										txtSuspendTradeLineDesc.requestFocus();
									
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
		btnSuspendNewTradeOrderLine.setFont(new Font("Georgia", Font.BOLD, 16));
		btnSuspendNewTradeOrderLine.setVisible(false);
		panelSuspendProcessTradeAddLine.add(btnSuspendNewTradeOrderLine);
		
		JLabel lblSuspendTradeRepCodeText = new JLabel("Rep Code");
		lblSuspendTradeRepCodeText.setForeground(Color.BLACK);
		lblSuspendTradeRepCodeText.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendTradeRepCodeText.setBounds(34, 9, 96, 18);
		panelSuspendProcessTradeAddLine.add(lblSuspendTradeRepCodeText);
		
		txtSuspendTradeLineRep = new JTextField();
		txtSuspendTradeLineRep.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtSuspendTradeLineRep.setBounds(34, 31, 86, 28);
		txtSuspendTradeLineRep.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessTradeAddLine.add(txtSuspendTradeLineRep);
		txtSuspendTradeLineRep.setColumns(10);
		
		panelSuspendTradeItemDescriptionChoice = new JPanel();
		panelSuspendTradeItemDescriptionChoice.setBounds(350, 91, 338, 398);
		addTradeOrderLine.getContentPane().add(panelSuspendTradeItemDescriptionChoice);
		panelSuspendTradeItemDescriptionChoice.setLayout(null);
		
		JScrollPane scrlPanelTblSuspendTradeItemDescription = new JScrollPane();
		scrlPanelTblSuspendTradeItemDescription.setBounds(0, 0, 337, 397);
		panelSuspendTradeItemDescriptionChoice.add(scrlPanelTblSuspendTradeItemDescription);
		
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
		scrlPanelTblSuspendTradeItemDescription.setViewportView(tblSuspendTradeItemDescription);
		
		tblSuspendTradeItemDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// If enter pressed call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make ENTER visible 
				if(arg0.getKeyCode() == 10) {
					ordLineItemSelected();					
				}
			}
		});
		tblSuspendTradeItemDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
								
				// Call a method to fill the orderline fields with this item, hide the panel with this table, set focus to qty and make the enter button visible
				ordLineItemSelected();
			}
		});
		tblSuspendTradeItemDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSuspendTradeItemDescription.setCellSelectionEnabled(true);
		tblSuspendTradeItemDescription.setColumnSelectionAllowed(false);
		
		// Initially make the description dropdown invisible
		panelSuspendTradeItemDescriptionChoice.setVisible(false);
	
		panelSuspendTradeGrossProfit = new JPanel();
		panelSuspendTradeGrossProfit.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendTradeGrossProfit.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendTradeGrossProfit.setBounds(1054, 159, 163, 142);
		addTradeOrderLine.getContentPane().add(panelSuspendTradeGrossProfit);
		panelSuspendTradeGrossProfit.setLayout(null);
		panelSuspendTradeGrossProfit.setVisible(false);
		
		lblSuspendTradeGrossProfitValue = new JLabel("\u20AC 0.00", SwingConstants.RIGHT);
		lblSuspendTradeGrossProfitValue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendTradeGrossProfitValue.setForeground(Color.BLACK);
		lblSuspendTradeGrossProfitValue.setBounds(25, 58, 102, 24);
		panelSuspendTradeGrossProfit.add(lblSuspendTradeGrossProfitValue);
		
		JLabel lblSuspendTradeGrossProfitText = new JLabel("Gross Profit");
		lblSuspendTradeGrossProfitText.setForeground(Color.BLACK);
		lblSuspendTradeGrossProfitText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblSuspendTradeGrossProfitText.setBounds(27, 23, 111, 24);
		panelSuspendTradeGrossProfit.add(lblSuspendTradeGrossProfitText);
		
		lblSuspendTradeGrossProfitPercent = new JLabel("0.00 %", SwingConstants.RIGHT);
		lblSuspendTradeGrossProfitPercent.setForeground(Color.BLACK);
		lblSuspendTradeGrossProfitPercent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSuspendTradeGrossProfitPercent.setBounds(25, 92, 102, 24);
		panelSuspendTradeGrossProfit.add(lblSuspendTradeGrossProfitPercent);


	}

	
	/**
	 * This method takes the item selected from the item dropdown and fills textfields with it's values (code, description,price), it also 
	 * retrieves the item's cost price and enables the quantity field.
	 */
	public void ordLineItemSelected() {
		try {
		
			// We use the table's getSelectedRow() method to find the row that the User selected. 
			int row = tblSuspendTradeItemDescription.getSelectedRow();
	
			// Then we can get the description of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
			// row and column that we require.
			// This returns an Object so we get the toString of it.
			String descString = tblSuspendTradeItemDescription.getModel().getValueAt(row, 0).toString();
			
			Item selectedItem = getProductDao().findItemByDesc(descString);
			
			if(selectedItem.getItemCode() != null){
				// Put selected item's code, description and price into the oderline entry fields and Enable the quantity+discount fields and make the enter button visible. 
				txtSuspendTradeLineCode.setText(selectedItem.getItemCode());
				txtSuspendTradeLineDesc.setText(selectedItem.getItemDescription());
				txtSuspendTradeLinePrice.setText(Utilities.stringToDec( String.valueOf(selectedItem.getRetailPrice()) ));
				txtSuspendLineCost = selectedItem.getCostPrice();
				txtSuspendTradeLineQty.setEnabled(true);
			}
			
			txtSuspendTradeLineQty.requestFocus();
			panelSuspendTradeItemDescriptionChoice.setVisible(false);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}

}
