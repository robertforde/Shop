package com.daniel;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.daniel.dao.RefundedDetailDao;
import com.daniel.dao.RefundedDetailDaoImpl;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.RefundDetail;
import com.daniel.model.RefundedDetail;
import com.daniel.model.RetailOrder;
import com.daniel.model.RetailOrderLine;
import com.daniel.utilities.Utilities;
import com.toedter.calendar.JDateChooser;

/**
 * This class handles the refunds screen. It allows the User to list and select orders based on a number of selection criteria
 * to do with they type of order, the order details and also the order contents. On selection of an order the User is presented with the
 * items' details that are on the order so as to confirm selection and from here can refund itema on the order. 
 * @author Robert Forde
 *
 */
public class RefundScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InvoiceSettings invoiceSettings;

	private JFrame refundInvoiceDetailList;
	private JFrame refundDetailList;

	JPanel panelRefundDetailTable;
	
	private JTable tblRefundInvoicesFiltered;
	private JTable tblInvoiceDetail;
	private JTable tblRefundDetail;
	
	private JRadioButton rdbtnRetailRefund;
	private JRadioButton rdbtnTradeRefund;
	private JRadioButton rdbtnAccountRefund;
	
	private JLabel lblTxtRefundTotalIncVat;
	private JLabel lblRefundTotalIncVat;

	private JTextField txtRefundSrchItemCode;
	private JTextField txtRefundSrchItemDesc;
	
	private JTextField txtRefundInvoiceNo;
	private JTextField txtRefundName;
	private JTextField txtRefundAddress;
	private JTextField txtRefundPhone;
	private JTextField txtRefundValue;

	private JDateChooser txtRefundFromDate;
	private JDateChooser txtRefundToDate;
	
	private JButton btnRefundItems; 

	private DefaultTableModel tableModelRefundedDetail;
	private DefaultTableModel tableModelInvoiceDetail;
	
	private int selectedInvoiceNo;
	private float refundIncVat;
	private String selectedLine;
	private String selectedItem;
	private String selectedDesc;
	private String selectedPrice;
	private String selectedDisc;
	private String selectedIncVat;
	private String refundSaleType;
	
	private ArrayList<RefundDetail> refundLines = new ArrayList<RefundDetail>();
	
	private RefundedDetailDao refundedDetailDao; 
	
	
	public RefundedDetailDao getRefundedDetailDao() {
		return refundedDetailDao;
	}

	public void setRefundedDetailDao(RefundedDetailDao refundedDetailDao) {
		this.refundedDetailDao = refundedDetailDao;
	}

	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}
	
	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}

	public JDateChooser getTxtRefundFromDate() {
		return txtRefundFromDate;
	}

	public void setTxtRefundFromDate(JDateChooser txtRefundFromDate) {
		this.txtRefundFromDate = txtRefundFromDate;
	}

	public JDateChooser getTxtRefundToDate() {
		return txtRefundToDate;
	}

	public void setTxtRefundToDate(JDateChooser txtRefundToDate) {
		this.txtRefundToDate = txtRefundToDate;
	}
	
	public JRadioButton getRdbtnRetailRefund() {
		return rdbtnRetailRefund;
	}

	public void setRdbtnRetailRefund(JRadioButton rdbtnRetailRefund) {
		this.rdbtnRetailRefund = rdbtnRetailRefund;
	}

	public JRadioButton getRdbtnTradeRefund() {
		return rdbtnTradeRefund;
	}

	public void setRdbtnTradeRefund(JRadioButton rdbtnTradeRefund) {
		this.rdbtnTradeRefund = rdbtnTradeRefund;
	}

	public JRadioButton getRdbtnAccountRefund() {
		return rdbtnAccountRefund;
	}

	public void setRdbtnAccountRefund(JRadioButton rdbtnAccountRefund) {
		this.rdbtnAccountRefund = rdbtnAccountRefund;
	}

	/**
	 * Constructs the 
	 * @param tabbedPane The main program tabbed pane so this panel can be added to it
	 * @param invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 */
	public RefundScreen(final JTabbedPane tabbedPane, InvoiceSettings invoiceSettings){
	
		setRefundedDetailDao(Dan.ctx.getBean("refundedDetailDaoImpl", RefundedDetailDaoImpl.class));
		
		setInvoiceSettings(invoiceSettings);
		
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Refund", null, this, null);
		setLayout(null);

		JPanel panelRefundSalesTypeChoice = new JPanel();
		panelRefundSalesTypeChoice.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRefundSalesTypeChoice.setBounds(128, 25, 311, 38);
		panelRefundSalesTypeChoice.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		this.add(panelRefundSalesTypeChoice);
		panelRefundSalesTypeChoice.setLayout(null);
		
		rdbtnRetailRefund = new JRadioButton("Retail");
		rdbtnRetailRefund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(null);
				else
					filterInvoicesByItem(null);
			}
		});
		rdbtnRetailRefund.setSelected(true);
		rdbtnRetailRefund.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnRetailRefund.setFont(new Font("Georgia", Font.BOLD, 18));
		rdbtnRetailRefund.setBounds(16, 7, 81, 23);
		panelRefundSalesTypeChoice.add(rdbtnRetailRefund);
		
		rdbtnTradeRefund = new JRadioButton("Trade");
		rdbtnTradeRefund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(null);
				else
					filterInvoicesByItem(null);
			}
		});
		rdbtnTradeRefund.setFont(new Font("Georgia", Font.BOLD, 18));
		rdbtnTradeRefund.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnTradeRefund.setBounds(107, 7, 81, 23);
		panelRefundSalesTypeChoice.add(rdbtnTradeRefund);
		
		rdbtnAccountRefund = new JRadioButton("Account");
		rdbtnAccountRefund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(null);
				else
					filterInvoicesByItem(null);
			}
		});
		rdbtnAccountRefund.setFont(new Font("Georgia", Font.BOLD, 18));
		rdbtnAccountRefund.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnAccountRefund.setBounds(190, 7, 115, 23);
		panelRefundSalesTypeChoice.add(rdbtnAccountRefund);

		// Group the radio buttons.
		ButtonGroup refundInvoiceTypegroup = new ButtonGroup();
		refundInvoiceTypegroup.add(rdbtnRetailRefund);
		refundInvoiceTypegroup.add(rdbtnTradeRefund);
		refundInvoiceTypegroup.add(rdbtnAccountRefund);

		
		JPanel panelRefundInvoiceList = new JPanel();
		panelRefundInvoiceList.setBackground(UIManager.getColor("List.dropLineColor"));
		panelRefundInvoiceList.setBounds(44, 85, 1195, 497);
		this.add(panelRefundInvoiceList);
		panelRefundInvoiceList.setLayout(null);
		
		JLabel lblRefundToDate = new JLabel("To Date");
		lblRefundToDate.setBounds(830, 26, 90, 25);
		panelRefundInvoiceList.add(lblRefundToDate);
		lblRefundToDate.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblRefundFromDate = new JLabel("From Date");
		lblRefundFromDate.setBounds(726, 26, 94, 25);
		panelRefundInvoiceList.add(lblRefundFromDate);
		lblRefundFromDate.setFont(new Font("Georgia", Font.BOLD, 16));
		
		txtRefundName = new JTextField();
		txtRefundName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(e);
				else
					filterInvoicesByItem(e);
			}
		});
		txtRefundName.setBounds(125, 51, 226, 25);
		txtRefundName.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRefundInvoiceList.add(txtRefundName);
		txtRefundName.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRefundName.setColumns(10);
		
		JLabel lblRefundName = new JLabel("Name");
		lblRefundName.setBounds(125, 26, 226, 25);
		panelRefundInvoiceList.add(lblRefundName);
		lblRefundName.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblRefundInvoiceNo = new JLabel("Number");
		lblRefundInvoiceNo.setBounds(29, 26, 94, 25);
		panelRefundInvoiceList.add(lblRefundInvoiceNo);
		lblRefundInvoiceNo.setFont(new Font("Georgia", Font.BOLD, 16));
		
		txtRefundInvoiceNo = new JTextField();
		txtRefundInvoiceNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(arg0);
				else
					filterInvoicesByItem(arg0);
			}
		});
		txtRefundInvoiceNo.setBounds(29, 51, 86, 25);
		txtRefundInvoiceNo.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRefundInvoiceList.add(txtRefundInvoiceNo);
		txtRefundInvoiceNo.setColumns(10);
		
		JLabel lblRefundPhone = new JLabel("Phone");
		lblRefundPhone.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRefundPhone.setBounds(934, 24, 128, 25);
		panelRefundInvoiceList.add(lblRefundPhone);
		
		JLabel lblRefundValue = new JLabel("Value");
		lblRefundValue.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRefundValue.setBounds(1072, 24, 94, 25);
		panelRefundInvoiceList.add(lblRefundValue);
		
		txtRefundPhone = new JTextField();
		txtRefundPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(e);
				else
					filterInvoicesByItem(e);
			}
		});
		txtRefundPhone.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRefundPhone.setColumns(10);
		txtRefundPhone.setBounds(934, 51, 127, 25);
		txtRefundPhone.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRefundInvoiceList.add(txtRefundPhone);
		
		txtRefundValue = new JTextField();
		txtRefundValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(e);
				else
					filterInvoicesByItem(e);
			}
		});
		txtRefundValue.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRefundValue.setColumns(10);
		txtRefundValue.setBounds(1072, 51, 94, 25);
		txtRefundValue.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRefundInvoiceList.add(txtRefundValue);
		
		JPanel panelRefundInvoicesFiltered = new JPanel();
		panelRefundInvoicesFiltered.setBackground(Color.WHITE);
		panelRefundInvoicesFiltered.setBounds(29, 106, 1137, 360);
		panelRefundInvoiceList.add(panelRefundInvoicesFiltered);
		panelRefundInvoicesFiltered.setLayout(null);
		
		JScrollPane scrlRefundInvoicesFiltered = new JScrollPane();
		scrlRefundInvoicesFiltered.setBounds(0, 0, 1137, 360);
		scrlRefundInvoicesFiltered.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRefundInvoicesFiltered.add(scrlRefundInvoicesFiltered);
		
		tblRefundInvoicesFiltered = new JTable(){
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
				
				// Right justify the last column (Total Value)
				if ( col == dataModel.getColumnCount() - 1 ) 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
				
				// Left justify the other columns
				else 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
				
				return renderer; 
			}
			
		};
		tblRefundInvoicesFiltered.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tblRefundInvoicesFiltered.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		tblRefundInvoicesFiltered.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				int row;
				String invoiceDetailColumns[] = {"LINE", "ITEM", "DESCRIPTION", "PRICE", "DISC%", "INC VAT"};
				tableModelInvoiceDetail = new DefaultTableModel(invoiceDetailColumns, 0);
				String invoiceRefundedColumns[] = {"LINE", "ITEM", "DESCRIPTION", "PRICE", "DISC%", "INC VAT"};
				tableModelRefundedDetail = new DefaultTableModel(invoiceRefundedColumns, 0);

				// We use the table's getSelectedRow() method to find the row that the User selected. 
				row = tblRefundInvoicesFiltered.getSelectedRow();
				
				// Then we can get the id of the selected invoice by getting the model of the table and then using the getValueAt() method we can get the specific
				// row and column that we require. This returns an Object so we get the toString and then convert it to an Integer.
				selectedInvoiceNo = Integer.parseInt(tblRefundInvoicesFiltered.getModel().getValueAt(row, 0).toString());
				
				// If another invoice is selected then remove dispose of the current one first
				if(refundInvoiceDetailList != null) {
					refundInvoiceDetailList.dispose();
					if(refundDetailList != null) {
						refundDetailList.dispose();
						refundDetailList = null;
						lblRefundTotalIncVat.setText(Utilities.floatToString2Dec(0.00f));
					}
					refundLines = new ArrayList<RefundDetail>();
					refundIncVat = 0.00f;
				}
				
				refundInvoiceDetailList = new JFrame("INVOICE ITEMS");
				refundInvoiceDetailList.setBounds(200, 20, 900, 280);
				refundInvoiceDetailList.setVisible(true);
				// Window Listener for a close on the refundInvoiceDetailList frame to clean up
				refundInvoiceDetailList.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						// If there is an InvoiceDetail frame and possibly a refundDetailList then dispose of them
						if(refundInvoiceDetailList != null) {
							refundInvoiceDetailList.dispose();
							if(refundDetailList != null) {
								refundDetailList.dispose();
								refundDetailList = null;
								lblRefundTotalIncVat.setText(Utilities.floatToString2Dec(0.00f));
							}
							refundLines = new ArrayList<RefundDetail>();
							refundIncVat = 0.00f;
						}
					}
				});
				
				JPanel paneInvoiceDetailTable = new JPanel();
				paneInvoiceDetailTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, SystemColor.controlShadow, null, new Color(105, 105, 105)));
				paneInvoiceDetailTable.setBounds(0, 0, 900, 280);
				//paneInvoiceDetailTable.setBounds(30, 30, 648, 200);
				paneInvoiceDetailTable.setBackground(Color.DARK_GRAY);
				refundInvoiceDetailList.getContentPane().add(paneInvoiceDetailTable);
				paneInvoiceDetailTable.setLayout(null);
								
				tblInvoiceDetail = new JTable(){
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
						
						// Right justify the last column (Total Value)
						if ( col == dataModel.getColumnCount() - 1 || col == dataModel.getColumnCount() - 2 || col == dataModel.getColumnCount() - 3 ) 
							((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
						
						// Left justify the other columns
						else 
							((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
						
						return renderer; 
					}
					
				};
				tblInvoiceDetail.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				tblInvoiceDetail.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
				tblInvoiceDetail.addMouseListener(new MouseAdapter() {
					// If someone adds an item to be refunded
					@Override
					public void mouseClicked(MouseEvent arg0) {
						
						int row;
				
						// We use the table's getSelectedRow() method to find the row that the User selected. 
						row = tblInvoiceDetail.getSelectedRow();
						
						// Then we can get the item of the selected invoice by getting the model of the table and then using the getValueAt() method we can get the specific
						// row and column that we require. This returns Objects so we get the toString of them to convert them to Strings.
						selectedLine  = tblInvoiceDetail.getModel().getValueAt(row, 0).toString();
						selectedItem  = tblInvoiceDetail.getModel().getValueAt(row, 1).toString();
						selectedDesc  = tblInvoiceDetail.getModel().getValueAt(row, 2).toString();
						selectedPrice = tblInvoiceDetail.getModel().getValueAt(row, 3).toString();
						selectedDisc  = tblInvoiceDetail.getModel().getValueAt(row, 4).toString();
						selectedIncVat = tblInvoiceDetail.getModel().getValueAt(row, 5).toString();
						
						// Remove the selected row from the Invoice detail table on screen
						tableModelInvoiceDetail.removeRow(row);
						
						if(refundDetailList == null){
							refundDetailList = new JFrame("ITEMS TO BE REFUNDED");
							refundDetailList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							refundDetailList.setBounds(200, 320, 900, 320);
							refundDetailList.setVisible(true);
							
							// Window Listener for a close on the refundDetailList to move lines back to invoice detail list 
							refundDetailList.addWindowListener(new WindowAdapter(){
								public void windowClosing(WindowEvent e){
									refundDetailList = null;
									
									
									for(int counter=0;counter<refundLines.size();counter++){
										// Retrieve the details from the refundDetail model and remove the row then add it to the invoice detail model
										selectedLine  = tblRefundDetail.getModel().getValueAt(0, 0).toString();
										selectedItem  = tblRefundDetail.getModel().getValueAt(0, 1).toString();
										selectedDesc  = tblRefundDetail.getModel().getValueAt(0, 2).toString();
										selectedPrice = tblRefundDetail.getModel().getValueAt(0, 3).toString();
										selectedDisc  = tblRefundDetail.getModel().getValueAt(0, 4).toString();
										selectedIncVat = tblRefundDetail.getModel().getValueAt(0, 5).toString();
										
										tableModelRefundedDetail.removeRow(0);
										
										Object[] line = {selectedLine, selectedItem, selectedDesc, selectedPrice, selectedDisc, selectedIncVat};
										tableModelInvoiceDetail.addRow(line);
										
									}
									
									// Remove it from the arraylist when all of the lines are moved and reset the refundIncVat value
									refundLines = new ArrayList<RefundDetail>();
									refundIncVat = 0.00f;
									lblRefundTotalIncVat.setText(Utilities.floatToString2Dec(refundIncVat));
									
								}
							});
						
							panelRefundDetailTable = new JPanel();
							panelRefundDetailTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, SystemColor.controlShadow, null, new Color(105, 105, 105)));
							panelRefundDetailTable.setBounds(0, 0, 900, 320);
							//panelRefundDetailTable.setBounds(30, 30, 648, 200);
							panelRefundDetailTable.setBackground(Color.DARK_GRAY);
							refundDetailList.getContentPane().add(panelRefundDetailTable);
							panelRefundDetailTable.setLayout(null);
					
							lblTxtRefundTotalIncVat = new JLabel("TOTAL VALUE OF REFUND:");
							lblTxtRefundTotalIncVat.setBounds(30, 230, 400, 50);
							panelRefundDetailTable.add(lblTxtRefundTotalIncVat);
							lblTxtRefundTotalIncVat.setForeground(Color.WHITE);
							lblTxtRefundTotalIncVat.setFont(new Font("Georgia", Font.BOLD, 24));
	
							lblRefundTotalIncVat = new JLabel("???????.??");
							lblRefundTotalIncVat.setBounds(450, 224, 200, 50);
							panelRefundDetailTable.add(lblRefundTotalIncVat);
							lblRefundTotalIncVat.setForeground(Color.WHITE);
							lblRefundTotalIncVat.setFont(new Font("Georgia", Font.BOLD, 30));
							lblRefundTotalIncVat.setHorizontalAlignment(SwingConstants.LEFT);
							
							btnRefundItems = new JButton("REFUND");
							btnRefundItems.setBounds(700, 230, 126, 38);
							btnRefundItems.setFont(new Font("Georgia", Font.BOLD, 16));
							btnRefundItems.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
							panelRefundDetailTable.add(btnRefundItems);
							btnRefundItems.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									doRefund();
								}
							});
							
							if(tblRefundDetail == null) {
								tblRefundDetail = new JTable(){
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
										
										// Right justify the last column (Total Value)
										if ( col == dataModel.getColumnCount() - 1 || col == dataModel.getColumnCount() - 2 || col == dataModel.getColumnCount() - 3 ) 
											((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
										
										// Left justify the other columns
										else 
											((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
										
										return renderer; 
									}
									
								};
							}
							tblRefundDetail.setFont(new Font("Times New Roman", Font.PLAIN, 14));
							tblRefundDetail.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
							tblRefundDetail.addMouseListener(new MouseAdapter() {
								// If someone removes an item to be refunded
								@Override
								public void mouseClicked(MouseEvent arg0) {
									
									int row;
									
									// We use the table's getSelectedRow() method to find the row that the User selected. 
									row = tblRefundDetail.getSelectedRow();
									
									if(row != -1) {
										// Then we can get the item of the selected invoice by getting the model of the table and then using the getValueAt() method we can get the specific
										// row and column that we require. This returns Objects so we get the toString of them to convert them to Strings.
										selectedLine   = tblRefundDetail.getModel().getValueAt(row, 0).toString();
										selectedItem   = tblRefundDetail.getModel().getValueAt(row, 1).toString();
										selectedDesc   = tblRefundDetail.getModel().getValueAt(row, 2).toString();
										selectedPrice  = tblRefundDetail.getModel().getValueAt(row, 3).toString();
										selectedDisc   = tblRefundDetail.getModel().getValueAt(row, 4).toString();
										selectedIncVat = tblRefundDetail.getModel().getValueAt(row, 5).toString();
										
										// Remove the selected row from the refund detail table on screen
										tableModelRefundedDetail.removeRow(row);
										
										// Remove the entry from the refundLines arraylist
										refundLines.remove(row);
										
										// Add the line back to the invoice detail table
										Object[] line = {selectedLine, selectedItem, selectedDesc, selectedPrice, selectedDisc, selectedIncVat};
										tableModelInvoiceDetail.addRow(line);
										
										refundIncVat -= Utilities.floatToNumDec(Float.parseFloat(selectedIncVat),2);
										lblRefundTotalIncVat.setText(Utilities.floatToString2Dec(refundIncVat));
									}
								}
							});
							
							JScrollPane scrlRefundDetail = new JScrollPane();
							scrlRefundDetail.setBounds(30, 30, 814, 190);
							scrlRefundDetail.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
							panelRefundDetailTable.add(scrlRefundDetail);
							scrlRefundDetail.setViewportView(tblRefundDetail);
						}
						
						refundIncVat += Utilities.floatToNumDec(Float.parseFloat(selectedIncVat),2);
						lblRefundTotalIncVat.setText(Utilities.floatToString2Dec(refundIncVat));
						
						RefundDetail refundDetail = new RefundDetail(Integer.parseInt(selectedLine), selectedItem, selectedDesc, Float.parseFloat(selectedPrice), 
																	Float.parseFloat(selectedDisc), Float.parseFloat(selectedIncVat));
						refundLines.add(refundDetail);
						
						Object[] refundedItems = {selectedLine, selectedItem, selectedDesc, selectedPrice, selectedDisc, selectedIncVat};
						tableModelRefundedDetail.addRow(refundedItems);
						
						tblRefundDetail.setModel(tableModelRefundedDetail);
						tblRefundDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					
						TableColumnModel columnModel = tblRefundDetail.getColumnModel();
						columnModel.getColumn(0).setPreferredWidth(60);
						columnModel.getColumn(1).setPreferredWidth(117);
						columnModel.getColumn(2).setPreferredWidth(417);
						columnModel.getColumn(3).setPreferredWidth(60);
						columnModel.getColumn(4).setPreferredWidth(60);
						columnModel.getColumn(5).setPreferredWidth(80);

					}
				});
				
				
				try {

					// Get +Vat multiplyer
					float plusVat = 1 + (getInvoiceSettings().getReceiptVatRate() / 100); 

					List<RetailOrderLine> orderLineList = getRefundedDetailDao().refundLoadInvoiceDetail(selectedInvoiceNo);
					
					// Loop through the order lines and if we have any lines with quantity > 1 then split them into single lines 
					List<RetailOrderLine> seperatedLines = new ArrayList<RetailOrderLine>();
					
					for(RetailOrderLine orderLine: orderLineList){
						
						int qty = Integer.parseInt(orderLine.getOrderQty());
						if(qty == 1) {
							seperatedLines.add(orderLine);
													
						}else {
							int lines = 1;
							while(lines <= qty){
								orderLine.setOrderQty("1");
								seperatedLines.add(orderLine);
								lines++;
							}
						}
					}
					
					for(RetailOrderLine orderLine : seperatedLines) {
						
						float price = Float.parseFloat(orderLine.getItemPrice());
						float discPercent = Float.parseFloat(orderLine.getDiscountPercent());
						float incVat = (price - (price*discPercent/100)) * plusVat;
						
						String tableLine= String.valueOf(orderLine.getLineNo());
						String tableItem = orderLine.getItemCode();
						String tableDescription = orderLine.getItemDescription();
						String tablePrice = orderLine.getItemPrice();
						String tableDisc = orderLine.getDiscountPercent();
						String tableIncVat = Utilities.floatToString2Dec(incVat);
						
						Object[] line = {tableLine, tableItem, tableDescription, tablePrice, tableDisc, tableIncVat};
						tableModelInvoiceDetail.addRow(line);
					}
				
					tblInvoiceDetail.setModel(tableModelInvoiceDetail);
					
					tblInvoiceDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				
					TableColumnModel columnModel = tblInvoiceDetail.getColumnModel();
					columnModel.getColumn(0).setPreferredWidth(60);
					columnModel.getColumn(1).setPreferredWidth(117);
					columnModel.getColumn(2).setPreferredWidth(417);
					columnModel.getColumn(3).setPreferredWidth(60);
					columnModel.getColumn(4).setPreferredWidth(60);
					columnModel.getColumn(5).setPreferredWidth(80);
				
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			
				JScrollPane scrlInvoiceDetail = new JScrollPane();
				scrlInvoiceDetail.setBounds(30, 30, 814, 190);
				scrlInvoiceDetail.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
				paneInvoiceDetailTable.add(scrlInvoiceDetail);
				scrlInvoiceDetail.setViewportView(tblInvoiceDetail);
			}
		});
		tblRefundInvoicesFiltered.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrlRefundInvoicesFiltered.setViewportView(tblRefundInvoicesFiltered);
		
		JLabel lblRefundAddress = new JLabel("Address");
		lblRefundAddress.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRefundAddress.setBounds(361, 26, 355, 25);
		panelRefundInvoiceList.add(lblRefundAddress);
		
		txtRefundAddress = new JTextField();
		txtRefundAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(e);
				else
					filterInvoicesByItem(e);
			}
		});
		txtRefundAddress.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtRefundAddress.setColumns(10);
		txtRefundAddress.setBounds(361, 51, 355, 25);
		txtRefundAddress.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRefundInvoiceList.add(txtRefundAddress);
		
		txtRefundFromDate = new JDateChooser();
		txtRefundFromDate.setBounds(726, 51, 91, 25);
		txtRefundFromDate.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRefundInvoiceList.add(txtRefundFromDate);
		txtRefundFromDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(arg0);
				else
					filterInvoicesByItem(arg0);
			}
		});
		txtRefundFromDate.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {

				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(null);
				else
					filterInvoicesByItem(null);
			}
		});
		txtRefundFromDate.setDateFormatString("dd/MM/yyyy");
		
		txtRefundToDate = new JDateChooser();
		txtRefundToDate.setBounds(830, 51, 91, 25);
		txtRefundToDate.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelRefundInvoiceList.add(txtRefundToDate);
		txtRefundToDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(arg0);
				else
					filterInvoicesByItem(arg0);
			}
		});
		txtRefundToDate.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(null);
				else
					filterInvoicesByItem(null);
			}
		});
		txtRefundToDate.setDateFormatString("dd/MM/yyyy");
		
		JPanel panelSalesTypeChoice = new JPanel();
		panelSalesTypeChoice.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSalesTypeChoice.setBounds(650, 25, 589, 38);
		panelSalesTypeChoice.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		this.add(panelSalesTypeChoice);
		panelSalesTypeChoice.setLayout(null);
		
		JLabel lblRefundSrchItem = new JLabel("Search for Item");
		lblRefundSrchItem.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRefundSrchItem.setBounds(10, 7, 137, 23);
		panelSalesTypeChoice.add(lblRefundSrchItem);
		
		txtRefundSrchItemCode = new JTextField("");
		txtRefundSrchItemCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(null);
				else
					filterInvoicesByItem(null);
			}
		});
		txtRefundSrchItemCode.setBounds(157, 7, 86, 25);
		txtRefundSrchItemCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSalesTypeChoice.add(txtRefundSrchItemCode);
		txtRefundSrchItemCode.setColumns(10);
		
		txtRefundSrchItemDesc = new JTextField();
		txtRefundSrchItemDesc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtRefundSrchItemCode.getText().equals("") && txtRefundSrchItemDesc.getText().equals(""))
					filterInvoices(null);
				else
					filterInvoicesByItem(null);
			}
		});
		txtRefundSrchItemDesc.setBounds(275, 7, 278, 25);
		txtRefundSrchItemDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSalesTypeChoice.add(txtRefundSrchItemDesc);
		txtRefundSrchItemDesc.setColumns(10);


		filterInvoices(null);
	}
	

	/**
	 * Method to filter the invoices by their type, invoice no, name, date, address, phone or value but not be containing items
	 * Invoices are retrieved from the database and represented in the table on screen for selection
	 * @param arg0 If the Tabbed key was last pressed then focus is placed on the invoice list
	 */
	public void filterInvoices(KeyEvent arg0){

		try {

			List<RetailOrder> orderList = getRefundedDetailDao().refundFilterInvoices(rdbtnRetailRefund.isSelected(), rdbtnTradeRefund.isSelected(), 
					txtRefundInvoiceNo.getText(), txtRefundName.getText(), txtRefundFromDate.getDateEditor().getDate(), txtRefundToDate.getDateEditor().getDate(), 
					txtRefundAddress.getText(), txtRefundPhone.getText(), txtRefundValue.getText());
			
			String columns[] = {"INVOICE", "NAME", "ADDRESS", "DATE", "PHONE", "VALUE"};
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			for(RetailOrder order : orderList){
				String tableInvoice= String.valueOf(order.getReceiptNo());
				String tableName = order.getName();
				String tableAddress = order.getAddress1();
				String tableDate = df.format(order.getOrderDate());
				String tablePhone = order.getPhone();
				String tableValue = order.getTotalPostRounding();
				Object[] line = {tableInvoice, tableName, tableAddress, tableDate, tablePhone, tableValue};
				tableModel.addRow(line);
			}
		
			tblRefundInvoicesFiltered.setModel(tableModel);
			
			tblRefundInvoicesFiltered.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
			TableColumnModel columnModel = tblRefundInvoicesFiltered.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(80);
			columnModel.getColumn(1).setPreferredWidth(216);
			columnModel.getColumn(2).setPreferredWidth(520);
			columnModel.getColumn(3).setPreferredWidth(80);
			columnModel.getColumn(4).setPreferredWidth(140);
			columnModel.getColumn(5).setPreferredWidth(80);
		
			// Set the tab key to the customer details possible matches table
			if(arg0 != null && arg0.getKeyCode() == KeyEvent.VK_TAB){
				tblRefundInvoicesFiltered.requestFocus();
			}
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}

	}
	
	
	/**
	 * Method to filter the invoices by their type, invoice no, name, date, address, phone, value or containing items
	 * Invoices are retrieved from the database and represented in the table on screen for selection
	 * @param arg0 If the Tabbed key was last pressed then focus is placed on the invoice list
	 */
	public void filterInvoicesByItem(KeyEvent arg0){

		try {
			
			// Call a method to find the matching invoices in the orderheader table and return their details as a List of Orders
			List<RetailOrder> orderList = getRefundedDetailDao().refundFilterInvoicesByItem(rdbtnRetailRefund.isSelected(), rdbtnTradeRefund.isSelected(), txtRefundInvoiceNo.getText(), 
					txtRefundName.getText(), txtRefundFromDate.getDateEditor().getDate(), txtRefundToDate.getDateEditor().getDate(), txtRefundAddress.getText(), 
					txtRefundPhone.getText(), txtRefundValue.getText(), txtRefundSrchItemCode.getText(), txtRefundSrchItemDesc.getText());
			
			String columns[] = {"INVOICE", "NAME", "ADDRESS", "DATE", "PHONE", "VALUE"};
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			for(RetailOrder order: orderList){
				String tableInvoice= String.valueOf(order.getReceiptNo());
				String tableName = order.getName();
				String tableAddress = order.getAddress1();
				String tableDate = df.format(order.getOrderDate());
				String tablePhone = order.getPhone();
				String tableValue = order.getTotalPostRounding();
				Object[] line = {tableInvoice, tableName, tableAddress, tableDate, tablePhone, tableValue};
				tableModel.addRow(line);
			}
		
			tblRefundInvoicesFiltered.setModel(tableModel);
			
			tblRefundInvoicesFiltered.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
			TableColumnModel columnModel = tblRefundInvoicesFiltered.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(80);
			columnModel.getColumn(1).setPreferredWidth(200);
			columnModel.getColumn(2).setPreferredWidth(480);
			columnModel.getColumn(3).setPreferredWidth(80);
			columnModel.getColumn(4).setPreferredWidth(200);
			columnModel.getColumn(5).setPreferredWidth(80);
		
			// Set the tab key to the refund invoices matches table
			if(arg0 != null && arg0.getKeyCode() == KeyEvent.VK_TAB){
				tblRefundInvoicesFiltered.requestFocus();
			}

		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	
	// Method to refund the chosen lines and update the orderheader and orderdetail tables
	/**
	 * Method that calls the updates to the relevant tables based on a refund being performed
	 */
	public void doRefund(){
		
		float refValueDisc;
		float refValueValueExcVat;
		float refVat;
		
		float totalRefunded = 0.00f;
		float totExVat = 0.00f;
		float totVat = 0.00f;
		float totCost = 0.00f;
		
		
		try {
			for(int counter=0;counter<refundLines.size();counter++){
		
				
				// This object will hold all of the details to be updated
				RefundedDetail refundedDetail = new RefundedDetail();
				
				RefundDetail refundDetail = refundLines.get(counter);

				// All refunds are qty 1 so price is valueExDisc
				refValueDisc        = Utilities.floatToNumDec(refundDetail.getPrice()/100*refundDetail.getDiscPercent(),2);
				refValueValueExcVat = Utilities.floatToNumDec(refundDetail.getPrice() - refValueDisc,2);
				refVat              = refundDetail.getIncVat() - refValueValueExcVat;
				
				// Get the saleType of this order
				if(getRdbtnRetailRefund().isSelected())
					refundSaleType = "Retail";
				else if(getRdbtnTradeRefund().isSelected())
					refundSaleType = "Trade";
				else
					refundSaleType = "Account";
				
				// Save the details we have to the refundedDetail object
				refundedDetail.setReceiptNo(selectedInvoiceNo);
				refundedDetail.setRefundDate(new Date());
				refundedDetail.setSaleType(refundSaleType);
				refundedDetail.setItemCode(refundDetail.getItemCode());
				refundedDetail.setItemDescription(refundDetail.getItemDescription());
				refundedDetail.setLineNo(refundDetail.getLineNo());
				refundedDetail.setQty(1);
				refundedDetail.setPrice(refundDetail.getPrice());
				refundedDetail.setDiscValue(refValueDisc);
				refundedDetail.setValueExVat(refValueValueExcVat);
				refundedDetail.setVat(refVat);
				refundedDetail.setValueIncVat(refundDetail.getIncVat());
	
				getRefundedDetailDao().refundUpdateDetail(refundedDetail);
				
				totalRefunded += refundedDetail.getValueIncVat();
				totExVat += refundedDetail.getValueExVat();
				totVat += refundedDetail.getVat();
				totCost += refundedDetail.getCostPrice();

			}

			getRefundedDetailDao().refundUpdateHeader(selectedInvoiceNo, totExVat, totVat, totalRefunded, totCost);
		
			// clear - Re-initialize the refundLines Arraylist
			refundLines = new ArrayList<RefundDetail>();
			
			refundInvoiceDetailList.dispose();
			refundInvoiceDetailList = null;
			refundDetailList.dispose();
			refundDetailList = null;
			refundIncVat = 0.00f;
			lblRefundTotalIncVat.setText(Utilities.floatToString2Dec(refundIncVat));
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Failed to refund items: " + e);
		}
	}
}