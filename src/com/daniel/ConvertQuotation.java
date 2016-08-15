package com.daniel;
import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JFrame;

import com.daniel.dao.QuoteDao;
import com.daniel.dao.QuoteDaoImpl;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.QuoteHeader;
import com.daniel.model.RetailOrder;
import com.daniel.model.RetailOrderLine;
import com.daniel.model.TradeOrder;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JRadioButton;

import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;

import com.toedter.calendar.JDateChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * This class loads all of the quotations that are in the database based into a table on screen. The user can enter various selection criteria 
 * to filter the quotations displayed. The user can select any of the quotes on screen to see the lines on the quotation and from there can
 * easily convert the quote into an order
 * @author Robert Forde
 *
 */
public class ConvertQuotation extends JFrame{

	private InvoiceSettings invoiceSettings;
	private RetailOrderScreen retailOrderScreen;
	private TradeOrderScreen tradeOrderScreen;
	private JTabbedPane tabbedPane;
	
	private static final long serialVersionUID = 1L;
	
	private JFrame quoteDetailList;
	
	private JTextField txtConvertQuotationNo;
	private JTextField txtConvertName;
	private JTextField txtConvertPhone;
	private JTextField txtConvertValue;
	private JTextField txtConvertAddress;
	
	private JTable tblQuotationsFiltered;
	
	private JRadioButton rdbtnConvertRetailQuotations;
	private JRadioButton rdbtnConvertTradeQuotations;
	
	private JDateChooser txtConvertFromDate;
	private JDateChooser txtConvertToDate;
	
	private int selectedQuoteNo;
	private JTextField txtConvertSrchItemCode;
	private JTextField txtConvertSrchItemDesc;

	private QuoteDao quoteDao;
	
	
	public QuoteDao getQuoteDao() {
		return quoteDao;
	}

	public void setQuoteDao(QuoteDao quoteDao) {
		this.quoteDao = quoteDao;
	}

	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}

	public RetailOrderScreen getRetailOrderScreen() {
		return retailOrderScreen;
	}

	public void setRetailOrderScreen(RetailOrderScreen retailOrderScreen) {
		this.retailOrderScreen = retailOrderScreen;
	}
	
	public TradeOrderScreen getTradeOrderScreen() {
		return tradeOrderScreen;
	}

	public void setTradeOrderScreen(TradeOrderScreen tradeOrderScreen) {
		this.tradeOrderScreen = tradeOrderScreen;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JDateChooser getTxtConvertFromDate() {
		return txtConvertFromDate;
	}

	public void setTxtConvertFromDate(JDateChooser txtConvertFromDate) {
		this.txtConvertFromDate = txtConvertFromDate;
	}

	public JDateChooser getTxtConvertToDate() {
		return txtConvertToDate;
	}

	public void setTxtConvertToDate(JDateChooser txtConvertToDate) {
		this.txtConvertToDate = txtConvertToDate;
	}

	
	/**
	 * 
	 * @param invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c 
	 * @param retailOrderScreen The retailOrderScreen so a retail quote can be converted to a retail order
	 * @param tradeOrderScreen The tradeOrderScreen so a trade quote can be converted to a trade order
	 * @param tabbedPane The main program tabbed pane so this class can set focus to the appropriate order screen if a quote is being converted
	 */
	public ConvertQuotation(InvoiceSettings invoiceSettings, RetailOrderScreen retailOrderScreen, TradeOrderScreen tradeOrderScreen, JTabbedPane tabbedPane){
		
		setQuoteDao(Dan.ctx.getBean("quoteDaoImpl", QuoteDaoImpl.class));
		
		getContentPane().setBackground(Color.DARK_GRAY);
		
		setInvoiceSettings(invoiceSettings);
		setRetailOrderScreen(retailOrderScreen);
		setTradeOrderScreen(tradeOrderScreen);
		setTabbedPane(tabbedPane);
		
		setBounds(30, 50, 1310, 629);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panelConvertSalesTypeChoice = new JPanel();
		panelConvertSalesTypeChoice.setBackground(UIManager.getColor("List.dropLineColor"));
		panelConvertSalesTypeChoice.setBounds(128, 25, 199, 38);
		panelConvertSalesTypeChoice.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelConvertSalesTypeChoice);
		panelConvertSalesTypeChoice.setLayout(null);
		
		rdbtnConvertRetailQuotations = new JRadioButton("Retail");
		rdbtnConvertRetailQuotations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		rdbtnConvertRetailQuotations.setSelected(true);
		rdbtnConvertRetailQuotations.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnConvertRetailQuotations.setFont(new Font("Georgia", Font.BOLD, 18));
		rdbtnConvertRetailQuotations.setBounds(16, 7, 81, 23);
		panelConvertSalesTypeChoice.add(rdbtnConvertRetailQuotations);
		
		rdbtnConvertTradeQuotations = new JRadioButton("Trade");
		rdbtnConvertTradeQuotations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		rdbtnConvertTradeQuotations.setFont(new Font("Georgia", Font.BOLD, 18));
		rdbtnConvertTradeQuotations.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnConvertTradeQuotations.setBounds(107, 7, 81, 23);
		panelConvertSalesTypeChoice.add(rdbtnConvertTradeQuotations);
		
		// Group the radio buttons.
		ButtonGroup quoteTypegroup = new ButtonGroup();
		quoteTypegroup.add(rdbtnConvertRetailQuotations);
		quoteTypegroup.add(rdbtnConvertTradeQuotations);
		
		JPanel panelConvertQuotationList = new JPanel();
		panelConvertQuotationList.setBackground(UIManager.getColor("List.dropLineColor"));
		panelConvertQuotationList.setBounds(44, 85, 1195, 497);
		panelConvertQuotationList.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelConvertQuotationList);
		panelConvertQuotationList.setLayout(null);
		
		JLabel lblConvertToDate = new JLabel("To Date");
		lblConvertToDate.setBounds(830, 26, 90, 25);
		panelConvertQuotationList.add(lblConvertToDate);
		lblConvertToDate.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblConvertFromDate = new JLabel("From Date");
		lblConvertFromDate.setBounds(726, 26, 94, 25);
		panelConvertQuotationList.add(lblConvertFromDate);
		lblConvertFromDate.setFont(new Font("Georgia", Font.BOLD, 16));
		
		txtConvertName = new JTextField();
		txtConvertName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertName.setBounds(125, 51, 226, 25);
		txtConvertName.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelConvertQuotationList.add(txtConvertName);
		txtConvertName.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtConvertName.setColumns(10);
		
		JLabel lblConvertName = new JLabel("Name");
		lblConvertName.setBounds(125, 26, 226, 25);
		panelConvertQuotationList.add(lblConvertName);
		lblConvertName.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblConvertQuotationNo = new JLabel("Quote No.");
		lblConvertQuotationNo.setBounds(29, 24, 86, 25);
		panelConvertQuotationList.add(lblConvertQuotationNo);
		lblConvertQuotationNo.setFont(new Font("Georgia", Font.BOLD, 16));
		
		txtConvertQuotationNo = new JTextField();
		txtConvertQuotationNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertQuotationNo.setBounds(29, 51, 86, 25);
		txtConvertQuotationNo.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelConvertQuotationList.add(txtConvertQuotationNo);
		txtConvertQuotationNo.setColumns(10);
		
		JLabel lblConvertPhone = new JLabel("Phone");
		lblConvertPhone.setFont(new Font("Georgia", Font.BOLD, 16));
		lblConvertPhone.setBounds(934, 24, 128, 25);
		panelConvertQuotationList.add(lblConvertPhone);
		
		JLabel lblConvertValue = new JLabel("Value");
		lblConvertValue.setFont(new Font("Georgia", Font.BOLD, 16));
		lblConvertValue.setBounds(1072, 24, 94, 25);
		panelConvertQuotationList.add(lblConvertValue);
		
		txtConvertPhone = new JTextField();
		txtConvertPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertPhone.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtConvertPhone.setColumns(10);
		txtConvertPhone.setBounds(934, 51, 127, 25);
		txtConvertPhone.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelConvertQuotationList.add(txtConvertPhone);
		
		txtConvertValue = new JTextField();
		txtConvertValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertValue.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtConvertValue.setColumns(10);
		txtConvertValue.setBounds(1072, 51, 94, 25);
		txtConvertValue.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelConvertQuotationList.add(txtConvertValue);
		
		JPanel panelQuotationFiltered = new JPanel();
		panelQuotationFiltered.setBackground(Color.WHITE);
		panelQuotationFiltered.setBounds(29, 106, 1137, 360);
		panelConvertQuotationList.add(panelQuotationFiltered);
		panelQuotationFiltered.setLayout(null);
		
		JScrollPane scrlQuotationsFiltered = new JScrollPane();
		scrlQuotationsFiltered.setBounds(0, 0, 1137, 360);
		scrlQuotationsFiltered.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelQuotationFiltered.add(scrlQuotationsFiltered);
		
		tblQuotationsFiltered = new JTable(){
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
		tblQuotationsFiltered.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tblQuotationsFiltered.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		tblQuotationsFiltered.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				int row;

				// We use the table's getSelectedRow() method to find the row that the User selected. 
				row = tblQuotationsFiltered.getSelectedRow();
				
				// Then we can get the id of the selected quote by getting the model of the table and then using the getValueAt() method we can get the specific
				// row and column that we require. This returns an Object so we get the toString and then convert it to an Integer.
				selectedQuoteNo = Integer.parseInt(tblQuotationsFiltered.getModel().getValueAt(row, 0).toString());
				
				quoteDetailList = new JFrame("Quotation Lines");
				quoteDetailList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				quoteDetailList.setBounds(200, 200, 900, 383);
				quoteDetailList.setVisible(true);
				
				JButton btnQuoteDetailConvert = new JButton("CONVERT");
				btnQuoteDetailConvert.setBounds(353, 300, 126, 38);
				btnQuoteDetailConvert.setFont(new Font("Georgia", Font.BOLD, 16));
				quoteDetailList.getContentPane().add(btnQuoteDetailConvert);
				btnQuoteDetailConvert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(ConvertQuotation.this.rdbtnConvertRetailQuotations.isSelected()){
							RetailOrder retailOrder = new RetailOrder();
			
							try {
	
								// Call a method that finds the quoteheader (based on number passed) and loads it into a retail order and returns it
								retailOrder = getQuoteDao().loadRetailQuote(selectedQuoteNo);
								
								// Set the detail arraylist of this retail order to the arraylist returned by calling a method that finds the quotedetail records (based 
								// on number passed), loads them into retail order lines and returns them 
								retailOrder.setOrderList(getQuoteDao().loadRetailQuoteDetail(selectedQuoteNo));
								
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, ex);
							}
							
							// Make this retailOrder the retailOrder for the retailOrderScreen
							getRetailOrderScreen().setRetailOrder(retailOrder);
								
							// Run the refreshCurrentOrder() method of the retailOrderScreen to update the current order table on the retailOrderScreen
							getRetailOrderScreen().refreshCurrentOrderTable();
								
							// Make all of the appropriate panels visible on the RetailOrderScreen
							getRetailOrderScreen().getPanelCustCurrentOrder().setVisible(true);
							getRetailOrderScreen().getPanelCustDetails().setVisible(true);
							getRetailOrderScreen().getPanelCustGrossProfit().setVisible(true);
							getRetailOrderScreen().getPanelCustNewOrder().setVisible(true);
							getRetailOrderScreen().getPanelCustPaymentMethod().setVisible(true);
							getRetailOrderScreen().getPanelCustPrintOrder().setVisible(true);
							getRetailOrderScreen().getPanelCustRounding().setVisible(true);
								
							// Update the gross profit labels and the total labels on the RetailOrderScreen 
							getRetailOrderScreen().getLblCustTotalExVat().setText(retailOrder.getTotalExVat());
							getRetailOrderScreen().getLblCustTotalPrice().setText(retailOrder.getTotalPostRounding());
							retailOrder.calcGrossProfit(getRetailOrderScreen().getLblCustGrossProfitValue(), getRetailOrderScreen().getLblCustGrossProfitPercent());
								
							// Update the rep code on the RetailOrderScreen
							getRetailOrderScreen().getTxtCustLineRep().setText((retailOrder.getRepNo()));
							getRetailOrderScreen().getTxtCustLineRep().setEnabled(false);
							getRetailOrderScreen().getTxtCustLineRep().setEditable(false);
								
							// Set the tab on the tabbed pane to the Retail Order Screen
							getTabbedPane().setSelectedIndex(0);
							
						}else{
							
							TradeOrder tradeOrder = new TradeOrder();
			
							try {
	
								// Call a method that finds the quoteheader (based on number passed) and loads it into a trade order and returns it
								tradeOrder = getQuoteDao().loadTradeQuote(selectedQuoteNo);
								
								// Set the detail arraylist of this trade order to the arraylist returned by calling a method that finds the quotedetail records (based 
								// on number passed), loads them into trade order lines and returns them 
								tradeOrder.setOrderList(getQuoteDao().loadTradeQuoteDetail(selectedQuoteNo));
							
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, ex);
							}
							
							// Make this tradeOrder the tradeOrder for the tradeOrderScreen
							getTradeOrderScreen().setTradeOrder(tradeOrder);
								
							// Run the refreshCurrentOrder() method of the tradeOrderScreen to update the current order table on the tradeOrderScreen
							getTradeOrderScreen().refreshCurrentOrderTable();
								
							// Make all of the appropriate panels visible on the TradeOrderScreen
							getTradeOrderScreen().getPanelTradeCurrentOrder().setVisible(true);
							getTradeOrderScreen().getPanelTradeDetails().setVisible(true);
							getTradeOrderScreen().getPanelTradeGrossProfit().setVisible(true);
							getTradeOrderScreen().getPanelTradeNewOrder().setVisible(true);
							getTradeOrderScreen().getPanelTradePaymentMethod().setVisible(true);
							getTradeOrderScreen().getPanelTradePrintOrder().setVisible(true);
							getTradeOrderScreen().getPanelTradeRounding().setVisible(true);
								
							// Update the gross profit labels and the total labels on the TradeOrderScreen 
							getTradeOrderScreen().getLblTradeTotalExVat().setText(tradeOrder.getTotalExVat());
							getTradeOrderScreen().getLblTradeTotalPrice().setText(tradeOrder.getTotalPostRounding());
							tradeOrder.calcGrossProfit(getTradeOrderScreen().getLblTradeGrossProfitValue(), getTradeOrderScreen().getLblTradeGrossProfitPercent());
								
							// Update the rep code on the TradeOrderScreen
							getTradeOrderScreen().getTxtTradeLineRep().setText((tradeOrder.getRepNo()));
							getTradeOrderScreen().getTxtTradeLineRep().setEnabled(false);
							getTradeOrderScreen().getTxtTradeLineRep().setEditable(false);
								
							// Set the tab on the tabbed pane to the Trade Order Screen
							getTabbedPane().setSelectedIndex(1);
							
						}	
						
						// Close this JFrame and the ConvertQuotation screen 
						ConvertQuotation.this.dispose();
						quoteDetailList.dispose();
					}
				});
				
				
				JPanel paneQuoteDetailTable = new JPanel();
				paneQuoteDetailTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, SystemColor.controlShadow, null, new Color(105, 105, 105)));
				paneQuoteDetailTable.setBounds(30, 30, 648, 264);
				paneQuoteDetailTable.setBackground(Color.DARK_GRAY);
				quoteDetailList.getContentPane().add(paneQuoteDetailTable);
				paneQuoteDetailTable.setLayout(null);
								
				JTable tblQuoteDetail = new JTable(){
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
				tblQuoteDetail.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				tblQuoteDetail.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
				
				try {
					
					// Call a method that retrieves the detail lines of this quotation as a List 
					List<RetailOrderLine> quoteDetailList = getQuoteDao().loadQuoteDetail(selectedQuoteNo);
					
					String columns[] = {"ITEM", "DESCRIPTION", "QTY", "PRICE", "DISCOUNT", "EX VAT"};
					DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
								
					for(RetailOrderLine quoteDetail: quoteDetailList){
						String tableItem = quoteDetail.getItemCode();
						String tableDesc = quoteDetail.getItemDescription();
						String tableQty = quoteDetail.getOrderQty();
						String tablePrice = quoteDetail.getItemPrice();
						String tableDiscount = quoteDetail.getDiscountPercent();
						String tableExVat = quoteDetail.getValueExVat();
						Object[] line = {tableItem, tableDesc, tableQty, tablePrice, tableDiscount, tableExVat};
						tableModel.addRow(line);
					}
								
					tblQuoteDetail.setModel(tableModel);

					
					tblQuoteDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					
					TableColumnModel columnModel = tblQuoteDetail.getColumnModel();
					columnModel.getColumn(0).setPreferredWidth(117);
					columnModel.getColumn(1).setPreferredWidth(400);
					columnModel.getColumn(2).setPreferredWidth(37);
					columnModel.getColumn(3).setPreferredWidth(60);
					columnModel.getColumn(4).setPreferredWidth(80);
					columnModel.getColumn(5).setPreferredWidth(100);
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			
				JScrollPane scrlQuoteDetail = new JScrollPane();
				scrlQuoteDetail.setBounds(30, 30, 814, 264);
				paneQuoteDetailTable.add(scrlQuoteDetail);
				scrlQuoteDetail.setViewportView(tblQuoteDetail);
			}
		});
		tblQuotationsFiltered.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrlQuotationsFiltered.setViewportView(tblQuotationsFiltered);
		
		JLabel lblConvertAddress = new JLabel("Address");
		lblConvertAddress.setFont(new Font("Georgia", Font.BOLD, 16));
		lblConvertAddress.setBounds(361, 26, 355, 25);
		panelConvertQuotationList.add(lblConvertAddress);
		
		txtConvertAddress = new JTextField();
		txtConvertAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertAddress.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtConvertAddress.setColumns(10);
		txtConvertAddress.setBounds(361, 51, 355, 25);
		txtConvertAddress.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelConvertQuotationList.add(txtConvertAddress);
		
		txtConvertFromDate = new JDateChooser();
		txtConvertFromDate.setBounds(726, 51, 91, 25);
		txtConvertFromDate.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelConvertQuotationList.add(txtConvertFromDate);
		txtConvertFromDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertFromDate.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {

				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertFromDate.setDateFormatString("dd/MM/yyyy");

		txtConvertToDate = new JDateChooser();
		txtConvertToDate.setBounds(830, 51, 91, 25);
		txtConvertToDate.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelConvertQuotationList.add(txtConvertToDate);
		txtConvertToDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertToDate.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertToDate.setDateFormatString("dd/MM/yyyy");
		
		JPanel panelSalsTypeChoice = new JPanel();
		panelSalsTypeChoice.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSalsTypeChoice.setBounds(650, 25, 589, 38);
		panelSalsTypeChoice.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelSalsTypeChoice);
		panelSalsTypeChoice.setLayout(null);
		
		JLabel lblConvertSrchItem = new JLabel("Search for Item");
		lblConvertSrchItem.setFont(new Font("Georgia", Font.BOLD, 16));
		lblConvertSrchItem.setBounds(10, 7, 137, 23);
		panelSalsTypeChoice.add(lblConvertSrchItem);
		
		txtConvertSrchItemCode = new JTextField();
		txtConvertSrchItemCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertSrchItemCode.setBounds(157, 7, 86, 25);
		txtConvertSrchItemCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSalsTypeChoice.add(txtConvertSrchItemCode);
		txtConvertSrchItemCode.setColumns(10);
		
		txtConvertSrchItemDesc = new JTextField();
		txtConvertSrchItemDesc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(txtConvertSrchItemCode.getText().equals("") && txtConvertSrchItemDesc.getText().equals(""))
					filterQuotations();
				else
					filterQuotationsByItem();
			}
		});
		txtConvertSrchItemDesc.setBounds(275, 7, 278, 25);
		txtConvertSrchItemDesc.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSalsTypeChoice.add(txtConvertSrchItemDesc);
		txtConvertSrchItemDesc.setColumns(10);
		setTitle("Quotation Conversion");
		setVisible(true);

		filterQuotations();
	}
	

	/**
	 * This method finds a list of quotations based on the selection criteria on the screen (with the exception of item filters). 
	 * It then loads them into the table of quotations on the screen
	 */
	public void filterQuotations(){

		try {

			// Return a list of quotes that match the selection criteria chosen
			List<QuoteHeader> quoteList = getQuoteDao().filterQuotes(rdbtnConvertRetailQuotations.isSelected(), txtConvertQuotationNo.getText(), txtConvertName.getText(), txtConvertAddress.getText(), 
					txtConvertFromDate.getDateEditor().getDate(), txtConvertToDate.getDateEditor().getDate(), txtConvertPhone.getText(), txtConvertValue.getText());
			
			String columns[] = {"QUOTE_NO", "NAME", "ADDRESS", "DATE", "PHONE", "VALUE"};
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			for(QuoteHeader quote : quoteList){
				String tableQuoteNo = String.valueOf(quote.getQuotationNo());
				String tableName = quote.getName();
				String tableAddress = quote.getAddress1();
				String tableDate = df.format(quote.getOrderDate());
				String tablePhone = quote.getPhone();
				String tableValue = quote.getTotalPostRounding();
				Object[] line = {tableQuoteNo, tableName, tableAddress, tableDate, tablePhone, tableValue};
				tableModel.addRow(line);
			}
		
			tblQuotationsFiltered.setModel(tableModel);
			
			tblQuotationsFiltered.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
			TableColumnModel columnModel = tblQuotationsFiltered.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(80);
			columnModel.getColumn(1).setPreferredWidth(200);
			columnModel.getColumn(2).setPreferredWidth(510);
			columnModel.getColumn(3).setPreferredWidth(80);
			columnModel.getColumn(4).setPreferredWidth(165);
			columnModel.getColumn(5).setPreferredWidth(80);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}

	}
	
	
	/**
	 * This method finds a list of quotations based on the selection criteria on the screen including item filters. 
	 * It then loads them into the table of quotations on the screen
	 */
	public void filterQuotationsByItem(){

		try {

			// Return a list of quotes that match the selection criteria chosen
			List<QuoteHeader> quoteList = getQuoteDao().filterQuotesByItem(rdbtnConvertRetailQuotations.isSelected(), txtConvertSrchItemCode.getText(), 
					txtConvertSrchItemDesc.getText(), txtConvertQuotationNo.getText(), txtConvertName.getText(), txtConvertAddress.getText(), 
					txtConvertFromDate.getDateEditor().getDate(), txtConvertToDate.getDateEditor().getDate(), txtConvertPhone.getText(), txtConvertValue.getText());			
			
			String columns[] = {"QUOTE_NO", "NAME", "ADDRESS", "DATE", "PHONE", "VALUE"};
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			for(QuoteHeader quote : quoteList){
				String tableQuoteNo = String.valueOf(quote.getQuotationNo());
				String tableName = quote.getName();
				String tableAddress = quote.getAddress1();
				String tableDate = df.format(quote.getOrderDate());
				String tablePhone = quote.getPhone();
				String tableValue = quote.getTotalPostRounding();
				Object[] line = {tableQuoteNo, tableName, tableAddress, tableDate, tablePhone, tableValue};
				tableModel.addRow(line);
			}
		
			tblQuotationsFiltered.setModel(tableModel);
			
			tblQuotationsFiltered.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
			TableColumnModel columnModel = tblQuotationsFiltered.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(80);
			columnModel.getColumn(1).setPreferredWidth(200);
			columnModel.getColumn(2).setPreferredWidth(480);
			columnModel.getColumn(3).setPreferredWidth(80);
			columnModel.getColumn(4).setPreferredWidth(200);
			columnModel.getColumn(5).setPreferredWidth(80);
		
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}
}
