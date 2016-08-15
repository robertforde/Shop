package com.daniel;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.daniel.dao.ReceiptSettingsDao;
import com.daniel.dao.ReceiptSettingsDaoImpl;
import com.daniel.dao.SuspendedDao;
import com.daniel.dao.SuspendedDaoImpl;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.SuspendedOrder;
import com.daniel.model.SuspendedOrderLine;
import com.daniel.utilities.Utilities;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * This class designs the suspended process screen (selection of a Suspended Order) JFrame which is the maintenance screen for suspended 
 * orders
 * @author Robert Forde
 *
 */
public class SuspendedProcessScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SuspendedOrdersScreen suspendedOrdersScreen;
	private SuspendedRetailOrderLineAdd suspendedRetailOrderLineAdd;
	private SuspendedTradeOrderLineAdd suspendedTradeOrderLineAdd;
	private SuspendedPaymentScreen suspendedPaymentScreen;
	
	private SuspendedOrder order;
	
	private SuspendedOrderLine releaseLine;
	
	private JFrame releaseOrderDetails;
	
	private JLabel lblSuspendProcessBalance;
	
	private JSpinner spinnSuspendProcessReleaseQty;
	
	private SuspendedDao suspendedDao;
	private ReceiptSettingsDao receiptSettingsDao;
	
	private JTable tblSuspendProcessOrderLines;
	
	
	public SuspendedDao getSuspendedDao() {
		return suspendedDao;
	}
	
	public void setSuspendedDao(SuspendedDao suspendedDao) {
		this.suspendedDao = suspendedDao;
	}
	
	public ReceiptSettingsDao getReceiptSettingsDao() {
		return receiptSettingsDao;
	}
	
	public void setReceiptSettingsDao(ReceiptSettingsDao receiptSettingsDao) {
		this.receiptSettingsDao = receiptSettingsDao;
	}
	
	public SuspendedOrder getOrder() {
		return order;
	}

	public void setOrder(SuspendedOrder order) {
		this.order = order;
	}

	public SuspendedOrderLine getReleaseLine() {
		return releaseLine;
	}

	public void setReleaseLine(SuspendedOrderLine releaseLine) {
		this.releaseLine = releaseLine;
	}

	public JSpinner getSpinnSuspendProcessReleaseQty() {
		return spinnSuspendProcessReleaseQty;
	}

	public void setSpinnSuspendProcessReleaseQty(
			JSpinner spinnSuspendProcessReleaseQty) {
		this.spinnSuspendProcessReleaseQty = spinnSuspendProcessReleaseQty;
	}

	public SuspendedOrdersScreen getSuspendedOrdersScreen() {
		return suspendedOrdersScreen;
	}

	public SuspendedRetailOrderLineAdd getSuspendedRetailOrderLineAdd() {
		return suspendedRetailOrderLineAdd;
	}

	public void setSuspendedRetailOrderLineAdd(
			SuspendedRetailOrderLineAdd suspendedRetailOrderLineAdd) {
		this.suspendedRetailOrderLineAdd = suspendedRetailOrderLineAdd;
	}

	public void setSuspendedOrdersScreen(SuspendedOrdersScreen suspendedOrdersScreen) {
		this.suspendedOrdersScreen = suspendedOrdersScreen;
	}
	
	public JLabel getLblSuspendProcessBalance() {
		return lblSuspendProcessBalance;
	}

	public void setLblSuspendProcessBalance(JLabel lblSuspendProcessBalance) {
		this.lblSuspendProcessBalance = lblSuspendProcessBalance;
	}
	
	public SuspendedPaymentScreen getSuspendedPaymentScreen() {
		return suspendedPaymentScreen;
	}

	public void setSuspendedPaymentScreen(
			SuspendedPaymentScreen suspendedPaymentScreen) {
		this.suspendedPaymentScreen = suspendedPaymentScreen;
	}
	
	public SuspendedTradeOrderLineAdd getSuspendedTradeOrderLineAdd() {
		return suspendedTradeOrderLineAdd;
	}

	public void setSuspendedTradeOrderLineAdd(
			SuspendedTradeOrderLineAdd suspendedTradeOrderLineAdd) {
		this.suspendedTradeOrderLineAdd = suspendedTradeOrderLineAdd;
	}

	
	/**
	 * Constructs the Suspended Process Screen
	 * @param order The Suspended Order that is being processed
	 * @param suspendedOrdersScreen A reference to the Suspended Orders Screen so the balance can be updated
	 */
	public SuspendedProcessScreen(SuspendedOrder order, SuspendedOrdersScreen suspendedOrdersScreen){

		setOrder(order);
		setSuspendedOrdersScreen(suspendedOrdersScreen);
		
		setSuspendedDao(Dan.ctx.getBean("suspendedDaoImpl", SuspendedDaoImpl.class));
		setReceiptSettingsDao(Dan.ctx.getBean("receiptSettingsDaoImpl", ReceiptSettingsDaoImpl.class));
		
		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(null);
		setBounds(70, 80, 1250, 570);
		setVisible(true);
		setTitle("Suspended Order Details");

		JLabel lblSuspendProcessName = new JLabel(order.getName());
		lblSuspendProcessName.setForeground(Color.WHITE);
		lblSuspendProcessName.setFont(new Font("Monotype Corsiva", Font.BOLD, 32));
		lblSuspendProcessName.setBounds(21, 11, 731, 38);
		getContentPane().add(lblSuspendProcessName);
		
		JLabel lblSuspendProcessOrderNumber = new JLabel("Suspended Order Number: " + getOrder().getReceiptNo());
		lblSuspendProcessOrderNumber.setForeground(Color.WHITE);
		lblSuspendProcessOrderNumber.setFont(new Font("Monotype Corsiva", Font.BOLD, 32));
		lblSuspendProcessOrderNumber.setBounds(21, 55, 390, 38);
		getContentPane().add(lblSuspendProcessOrderNumber);
		
		JButton btnSuspendProcessPayment = new JButton("PAYMENT");
		btnSuspendProcessPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Open the Suspended Payment screen and if there is one already open then put focus on it 
				if(suspendedPaymentScreen == null) {
					suspendedPaymentScreen = new SuspendedPaymentScreen(getOrder(), getSuspendedOrdersScreen(), SuspendedProcessScreen.this);
				}else {
					suspendedPaymentScreen.requestFocus();
				}
			}
		});
		btnSuspendProcessPayment.setFont(new Font("Georgia", Font.BOLD, 18));
		btnSuspendProcessPayment.setBounds(1065, 141, 133, 38);
		btnSuspendProcessPayment.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(btnSuspendProcessPayment);
		
		JButton btnSuspendProcessRelease = new JButton("RELEASE");
		btnSuspendProcessRelease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// We use the table's getSelectedRow() method to find the row that the User selected. 
				int row = tblSuspendProcessOrderLines.getSelectedRow();
					
				if(row != -1) {

					// We can get the line number of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific row 
					// and column that we require.
					// This returns an Object so we get the toString of it and convert it to an integer.
					int lineNo = Integer.valueOf(tblSuspendProcessOrderLines.getModel().getValueAt(row, 0).toString());
					
					// We get the order line from the suspenddetail table
					setReleaseLine(getSuspendedDao().findSuspendOrderLineByReceiptNoLineNo(getOrder().getReceiptNo(), lineNo));

					int remaining = Integer.valueOf(releaseLine.getOrderQty()) - releaseLine.getDispatched();
					
					if(remaining > 0) { 
						releaseOrderDetails = new JFrame("RELEASE ITEMS");
						releaseOrderDetails.getContentPane().setBackground(Color.DARK_GRAY);
						releaseOrderDetails.getContentPane().setLayout(null);
						releaseOrderDetails.setVisible(true);
						releaseOrderDetails.setBounds(200, 20, 900, 280);
						releaseOrderDetails.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						
						JLabel lblSuspendProcessReleaseTextRemainingQty = new JLabel("Qty Outstanding:");
						lblSuspendProcessReleaseTextRemainingQty.setForeground(Color.WHITE);
						lblSuspendProcessReleaseTextRemainingQty.setFont(new Font("Georgia", Font.BOLD, 22));
						lblSuspendProcessReleaseTextRemainingQty.setBounds(249, 25, 200, 26);
						releaseOrderDetails.getContentPane().add(lblSuspendProcessReleaseTextRemainingQty);
	
						JLabel lblSuspendProcessReleaseQtyRemaining = new JLabel(String.valueOf(remaining));
						lblSuspendProcessReleaseQtyRemaining.setForeground(Color.WHITE);
						lblSuspendProcessReleaseQtyRemaining.setFont(new Font("Georgia", Font.BOLD, 22));
						lblSuspendProcessReleaseQtyRemaining.setBounds(480, 25, 519, 26);
						releaseOrderDetails.getContentPane().add(lblSuspendProcessReleaseQtyRemaining);
						
						JLabel lblSuspendProcessReleaseTextEnterQty = new JLabel("Release: ");
						lblSuspendProcessReleaseTextEnterQty.setForeground(Color.WHITE);
						lblSuspendProcessReleaseTextEnterQty.setFont(new Font("Georgia", Font.BOLD, 22));
						lblSuspendProcessReleaseTextEnterQty.setBounds(346, 73, 200, 26);
						releaseOrderDetails.getContentPane().add(lblSuspendProcessReleaseTextEnterQty);
						
						spinnSuspendProcessReleaseQty = new JSpinner();
						spinnSuspendProcessReleaseQty.setFont(new Font("Times New Roman", Font.BOLD, 22));
						spinnSuspendProcessReleaseQty.setModel(new SpinnerNumberModel(1, 1, remaining, 1));
						spinnSuspendProcessReleaseQty.setBounds(470, 73, 67, 32);
						releaseOrderDetails.getContentPane().add(spinnSuspendProcessReleaseQty);
						
						JButton btnSuspendProcessReleaseCancel = new JButton("CANCEL");
						btnSuspendProcessReleaseCancel.setFont(new Font("Georgia", Font.BOLD, 16));
						btnSuspendProcessReleaseCancel.setBounds(275, 162, 140, 38);
						btnSuspendProcessReleaseCancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
						releaseOrderDetails.getContentPane().add(btnSuspendProcessReleaseCancel);
						btnSuspendProcessReleaseCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								releaseOrderDetails.dispose();
							}
						});
						
						JButton btnSuspendProcessReleaseProceed = new JButton("RELEASE");
						btnSuspendProcessReleaseProceed.setFont(new Font("Georgia", Font.BOLD, 16));
						btnSuspendProcessReleaseProceed.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
						btnSuspendProcessReleaseProceed.setBounds(485, 162, 140, 38);
						releaseOrderDetails.getContentPane().add(btnSuspendProcessReleaseProceed);
						btnSuspendProcessReleaseProceed.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								int qty = (Integer) getSpinnSuspendProcessReleaseQty().getValue();
								getSuspendedDao().changeSuspendOrderLineDispatched(Integer.valueOf(getOrder().getReceiptNo()), getReleaseLine().getLineNo(), qty);
								
								// Set the dispatchedDate to today, if more are dispatched then this will be wrote over
								getSuspendedDao().stampSuspendOrderLineDispatchedDate(getOrder().getReceiptNo(), getReleaseLine().getLineNo());
								
								int remaining = Integer.valueOf(getReleaseLine().getOrderQty()) - (getReleaseLine().getDispatched() + qty); 
								if(remaining == 0){
									
									// We also need to check if all lines on this order are now dispatched
									int openLines = getSuspendedDao().countNonDispatchedSuspendedOrderLines(getOrder().getReceiptNo());
									// If all lines are now dispatched then we stamp today's date in the dispatched field of this order
									if(openLines == 0){
										getSuspendedDao().stampSuspendOrderDispatchedDate(getOrder().getReceiptNo());
										// If the balance of this order is also 0 then refresh the open suspended orders table and close this screen
										if(getOrder().getBalance() == 0) {
											getSuspendedOrdersScreen().refreshSuspendedOrderHeaders();
											SuspendedProcessScreen.this.dispose();
										}
									}
								}
								
								refreshSuspendedLines();
								releaseOrderDetails.dispose();
							}
						});
						
					}else{
						JOptionPane.showMessageDialog(null, "This order line has already been dispatched !");	
					}
					
				} else {					
					JOptionPane.showMessageDialog(null, "You must select a Line to Release !");
				}
					

				
			}
		});
		btnSuspendProcessRelease.setFont(new Font("Georgia", Font.BOLD, 18));
		btnSuspendProcessRelease.setBounds(1065, 231, 133, 38);
		btnSuspendProcessRelease.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(btnSuspendProcessRelease);
		
		JButton btnSuspendProcessAdd = new JButton("ADD");
		btnSuspendProcessAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Open the Suspended Add Line screen and if there is one already open then put focus on it
				if(getOrder().getSaleType().equals("Retail")){
					if(suspendedRetailOrderLineAdd == null) {
						suspendedRetailOrderLineAdd = new SuspendedRetailOrderLineAdd(getOrder(), getSuspendedOrdersScreen(), SuspendedProcessScreen.this);
					}else{
						suspendedRetailOrderLineAdd.getAddRetailOrderLine().requestFocus();
					}
					
				} else {
					if(suspendedTradeOrderLineAdd == null) {
						suspendedTradeOrderLineAdd = new SuspendedTradeOrderLineAdd(getOrder(), getSuspendedOrdersScreen(), SuspendedProcessScreen.this);
					}else{
						suspendedTradeOrderLineAdd.getAddTradeOrderLine().requestFocus();
					}
				}
				
			}
		});
		btnSuspendProcessAdd.setFont(new Font("Georgia", Font.BOLD, 18));
		btnSuspendProcessAdd.setBounds(1065, 321, 133, 38);
		btnSuspendProcessAdd.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(btnSuspendProcessAdd);
		
		JButton btnSuspendProcessRemove = new JButton("REMOVE");
		btnSuspendProcessRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// We use the table's getSelectedRow() method to find the row that the User selected. 
				int row = tblSuspendProcessOrderLines.getSelectedRow();
		
				// Then we can get the description of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
				// row and column that we require.
				// This returns an Object so we get the toString of it and the Integer of the result.
				int line = Integer.valueOf(tblSuspendProcessOrderLines.getModel().getValueAt(row, 0).toString());

				// First we need to get the qty and remaining qty of the order line
				int qty = Integer.valueOf(tblSuspendProcessOrderLines.getModel().getValueAt(row, 3).toString());
				int remaining = Integer.valueOf(tblSuspendProcessOrderLines.getModel().getValueAt(row, 6).toString());
				
				// If there is nothing to be dispatched then we can't delete it
				if (remaining == 0) {
					JOptionPane.showMessageDialog(null, "This order line has already been dispatched !");
					
				// If some of the qty on this line has already been dispatched
				}else if (remaining != qty ){
					deleteSelectedLineNoRemaining(line, qty, remaining);
				// If nothing of this line has been dispatched yet	
				}else {
					deleteSelectedLineNo(line);
				}
			}
		});
		btnSuspendProcessRemove.setFont(new Font("Georgia", Font.BOLD, 18));
		btnSuspendProcessRemove.setBounds(1065, 411, 133, 38);
		btnSuspendProcessRemove.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(btnSuspendProcessRemove);
		
		JPanel panelSuspendProcessOrderLines = new JPanel();
		panelSuspendProcessOrderLines.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendProcessOrderLines.setBounds(21, 99, 977, 415);
		panelSuspendProcessOrderLines.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(panelSuspendProcessOrderLines);
		panelSuspendProcessOrderLines.setLayout(null);
		
		JScrollPane scrlSuspendProcessOrderLines = new JScrollPane();
		scrlSuspendProcessOrderLines.setBounds(40, 29, 890, 379);
		scrlSuspendProcessOrderLines.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelSuspendProcessOrderLines.add(scrlSuspendProcessOrderLines);
		
		tblSuspendProcessOrderLines = new JTable(){
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
				
				// Right justify the last 4 columns
				if ( col == dataModel.getColumnCount() - 1 || col == dataModel.getColumnCount() - 2 || col == dataModel.getColumnCount() - 3 || 
					col == dataModel.getColumnCount() - 4 ) ((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
				
				// Left justify the other columns
				else 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
				
				return renderer; 
			}
		};
		
		tblSuspendProcessOrderLines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSuspendProcessOrderLines.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tblSuspendProcessOrderLines.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		scrlSuspendProcessOrderLines.setViewportView(tblSuspendProcessOrderLines);
		
		JLabel lblSuspendProcessBalanceText = new JLabel("Balance");
		lblSuspendProcessBalanceText.setForeground(Color.WHITE);
		lblSuspendProcessBalanceText.setFont(new Font("Monotype Corsiva", Font.BOLD, 32));
		lblSuspendProcessBalanceText.setBounds(939, 26, 116, 38);
		getContentPane().add(lblSuspendProcessBalanceText);
		
		lblSuspendProcessBalance = new JLabel(Utilities.floatToString2Dec(getOrder().getBalance()));
		lblSuspendProcessBalance.setForeground(Color.WHITE);
		lblSuspendProcessBalance.setFont(new Font("Monotype Corsiva", Font.BOLD, 32));
		lblSuspendProcessBalance.setBounds(1065, 26, 147, 38);
		getContentPane().add(lblSuspendProcessBalance);
		
		// Window Listener for a close on this screen so we can check/close all of it's sub-windows
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){

				if(releaseOrderDetails != null) {
					releaseOrderDetails.dispose();
				}
				
				if(suspendedRetailOrderLineAdd != null) {
					getSuspendedRetailOrderLineAdd().getAddRetailOrderLine().dispose();
					suspendedRetailOrderLineAdd = null;
				}
				
				if(suspendedTradeOrderLineAdd != null) {
					getSuspendedTradeOrderLineAdd().getAddTradeOrderLine().dispose();
					suspendedTradeOrderLineAdd = null;
				}
				
				if(suspendedPaymentScreen != null) {
					getSuspendedPaymentScreen().dispose();
					suspendedPaymentScreen = null;
				}
			}
		});
		
		refreshSuspendedLines();
		
	} 
	
	
	/**
	 * Method to retrieve the Suspended Order Lines from the database and refresh the current Suspended Order lines on screen in a table 
	 */
	public void refreshSuspendedLines(){
	
		// Return a List of suspended detail order lines for this suspended order header in order of qty
		List<SuspendedOrderLine> suspendedLinesList = getSuspendedDao().findSuspendedOrderLinesByReceiptNo(order.getReceiptNo());
		
		// Set this List as the orderList for thiis order 
		order.setOrderList(suspendedLinesList);
		
		String columns[] = {"LINE", "CODE", "DESCRIPTION", "QTY", "EX VAT", "INC VAT", "REMAINING"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		InvoiceSettings invoiceSettings = getReceiptSettingsDao().retrieveDatabasePrintSettings(new InvoiceSettings());
		float incVatRate = 1 + (invoiceSettings.getReceiptVatRate()/100);
			
		for(SuspendedOrderLine orderLine : suspendedLinesList){
			String incVat = Utilities.floatToString2Dec( Float.valueOf(orderLine.getValueExVat()) * incVatRate );
			String remaining = String.valueOf( Integer.valueOf(orderLine.getOrderQty()) - Integer.valueOf(orderLine.getDispatched()) );
			String tableLine = String.valueOf(orderLine.getLineNo());
			String tableCode = String.valueOf(orderLine.getItemCode());
			String tableDescription = orderLine.getItemDescription();
			String tableQty = orderLine.getOrderQty();
			String tableExVat = orderLine.getValueExVat();
			String tableIncVat = incVat;
			String tableRemaining = remaining;
			Object[] line = {tableLine, tableCode, tableDescription, tableQty, tableExVat, tableIncVat, tableRemaining};
			tableModel.addRow(line);
		}
		
		tblSuspendProcessOrderLines.setModel(tableModel);
			
		tblSuspendProcessOrderLines.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumnModel columnModel = tblSuspendProcessOrderLines.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(1).setPreferredWidth(90);
		columnModel.getColumn(2).setPreferredWidth(430);
		columnModel.getColumn(3).setPreferredWidth(40);
		columnModel.getColumn(4).setPreferredWidth(80);
		columnModel.getColumn(5).setPreferredWidth(80);
		columnModel.getColumn(6).setPreferredWidth(109);
		
	}
	
	
	/**
	 * This method handles the deletion of an order line if nopart of this line has been dispatched yet
	 * It deletes the line of the current Suspended Order in the database and to change the balance of the current Suspended Order. 
	 * It also updates the order object and it's order lines list as well as updating the balance of the order on the relevant screens
	 * @param line The selected line to be deleted
	 */
	public void deleteSelectedLineNo(int line){
		
		float delLineValueExVat;
		float delLineCostPrice;
		float delLineVat;
		float delLineValueIncVat;
		float delOrderRounding;
		
		// Retrieve the Suspended Order Line from the table and get it's values
		SuspendedOrderLine suspendedOrderLine = getSuspendedDao().findSuspendOrderLineByReceiptNoLineNo(order.getReceiptNo(), line);
		delLineValueExVat = Float.valueOf(suspendedOrderLine.getValueExVat());
		delLineValueExVat = Utilities.floatToNumDec(delLineValueExVat,2);
		delLineCostPrice = suspendedOrderLine.getLineCostValue();
		delLineCostPrice = Utilities.floatToNumDec(delLineCostPrice,2);
		delLineVat = delLineValueExVat * (getReceiptSettingsDao().retrieveDatabasePrintSettings(new InvoiceSettings()).getReceiptVatRate()) / 100;
		delLineVat = Utilities.floatToNumDec(delLineVat, 2);
		delLineValueIncVat = delLineValueExVat + delLineVat;
		delLineValueIncVat = Utilities.floatToNumDec(delLineValueIncVat,2); 
		
		// Reduce order totals (of the order object) by the amounts of the deleted order line
		order.setTotalExVat(Utilities.floatToString2Dec(Utilities.floatToNumDec(Float.valueOf(order.getTotalExVat()),2) - delLineValueExVat));
		order.setTotalVat(Utilities.floatToString2Dec(Utilities.floatToNumDec(Float.valueOf(order.getTotalVat()),2) - delLineVat));
		order.setTotalPreRounding(Utilities.floatToString2Dec(Utilities.floatToNumDec(Float.valueOf(order.getTotalPreRounding()),2) - delLineValueIncVat));
		delOrderRounding = Utilities.floatToNumDec(Float.valueOf(order.getRounding()),2);
		order.setRounding("0.00");
		order.setTotalPostRounding(order.getTotalPreRounding());
		order.setTotalCost(order.getTotalCost() - delLineCostPrice);
		order.setBalance(order.getBalance() - delLineValueIncVat - delOrderRounding);
		
		// Remove this orderline from the order's order list
		List<SuspendedOrderLine> listSuspendedOrders = order.getOrderList();
		suspendedOrderLine = listSuspendedOrders.get(line - 1);
		suspendedOrderLine.setDeleted(1);
		suspendedOrderLine.setDeleteDate(new Date());
		listSuspendedOrders.remove(line - 1);
		listSuspendedOrders.add(line - 1, suspendedOrderLine);
		
		// Update the Suspended Order table (suspendedheader )and it's lines suspendeddetail
		order.updateOrderAndLines();
		
		// Refresh screens and update screen balance
		getSuspendedOrdersScreen().refreshSuspendedOrderHeaders();
		refreshSuspendedLines();
		getLblSuspendProcessBalance().setText(Utilities.floatToString2Dec(order.getBalance()));
		
	}
	

	// Method to mark an orderline as deleted and change the order totals accordingly, if the order is partly dispatched
	/**
	 * If an order line is partly dispatched then this method will handle the deletion of a line 
	 * It deletes the line of the current Suspended Order in the database and to change the balance of the current Suspended Order. 
	 * It also updates the order object and it's order lines list as well as updating the balance of the order on the relevant screens.
	 * If the deletion completes the the order line then the part of the line that was dispatched is marked as dispatched and the part that 
	 * was deleted is marked as deleted
	 * @param line The line to be deleted
	 * @param qty The qty on this order line 
	 * @param remaining The qty remaining on the order line prior to deletion
	 */
	public void deleteSelectedLineNoRemaining(int line, int qty, int remaining){
		
		SuspendedOrderLine suspendedOrderLine1;
		SuspendedOrderLine suspendedOrderLine2;
		
		int deleteQty;
		float totalDiscountValue;
		float deleteDiscountValue;
		float totalValueExDiscount;
		float deleteValueExDiscount;
		float deleteLineValueExVat;
		float totalLineValueExVat;
		float deleteLineCostPrice;
		float totalLineCostPrice;
		float deleteLineVat;
		float totalLineVat;
		float deleteLineValueIncVat;
		float deleteOrderRounding;
		
		int dispatchedQty;
		float dispatchedValueExDiscount;
		float dispatchedDiscValue;
		float dispatchedValueExVat;  
		float dispatchedCostPrice;
		
		// Retrieve the Suspended Order Line 
		suspendedOrderLine1 = getSuspendedDao().findSuspendOrderLineByReceiptNoLineNo(order.getReceiptNo(), line);
		
		// Get the values that will be marked as deleted and get the deleted Values that will be deducted from the order totals
		deleteQty = remaining;
		totalDiscountValue = Utilities.floatToNumDec(Float.valueOf(suspendedOrderLine1.getDiscountValue()),2);
		deleteDiscountValue = Utilities.floatToNumDec(totalDiscountValue / qty * remaining,2);
		totalValueExDiscount = Utilities.floatToNumDec(Float.valueOf(suspendedOrderLine1.getValueExDiscount()),2);
		deleteValueExDiscount = Utilities.floatToNumDec(totalValueExDiscount / qty * remaining,2);
		totalLineValueExVat = Utilities.floatToNumDec(Float.valueOf(suspendedOrderLine1.getValueExVat()),2);
		deleteLineValueExVat = Utilities.floatToNumDec(totalLineValueExVat / qty * remaining,2);
		totalLineCostPrice = Utilities.floatToNumDec(suspendedOrderLine1.getLineCostValue(),2);
		deleteLineCostPrice = Utilities.floatToNumDec(totalLineCostPrice / qty * remaining,2);
		totalLineVat = Utilities.floatToNumDec(totalLineValueExVat * (getReceiptSettingsDao().retrieveDatabasePrintSettings(new InvoiceSettings()).getReceiptVatRate()) / 100,2);
		deleteLineVat = Utilities.floatToNumDec(totalLineVat / qty * remaining,2);
		deleteLineValueIncVat = deleteLineValueExVat + deleteLineVat;
		deleteLineValueIncVat = Utilities.floatToNumDec(deleteLineValueIncVat,2);
		
		// Get the values of the order line that was dispatched
		dispatchedQty = qty - remaining;
		dispatchedValueExDiscount = totalValueExDiscount - deleteValueExDiscount; 
		dispatchedDiscValue = totalDiscountValue - deleteDiscountValue;
		dispatchedValueExVat = totalLineValueExVat - deleteLineValueExVat;  
		dispatchedCostPrice = totalLineCostPrice - deleteLineCostPrice;
		
		// Reduce order totals (of the order object) by the amounts of the deleted order line
		order.setTotalExVat(Utilities.floatToString2Dec(Utilities.floatToNumDec(Float.valueOf(order.getTotalExVat()),2) - deleteLineValueExVat));
		order.setTotalVat(Utilities.floatToString2Dec(Utilities.floatToNumDec(Float.valueOf(order.getTotalVat()),2) - deleteLineVat));
		order.setTotalPreRounding(Utilities.floatToString2Dec(Utilities.floatToNumDec(Float.valueOf(order.getTotalPreRounding()),2) - deleteLineValueIncVat));
		deleteOrderRounding = Utilities.floatToNumDec(Float.valueOf(order.getRounding()),2);
		order.setTotalPostRounding(Utilities.floatToString2Dec(Utilities.floatToNumDec(Float.valueOf(order.getTotalPreRounding()),2) + deleteOrderRounding));
		order.setTotalCost(order.getTotalCost() - deleteLineCostPrice);
		order.setBalance(order.getBalance() - deleteLineValueIncVat - deleteOrderRounding);
		
		// Do a check to see if there is anything remaining on this order
		int openLines = getSuspendedDao().countNonDispatchedSuspendedOrderLines(getOrder().getReceiptNo());
		
		// If all lines are now dispatched then we stamp today's date in the dispatched field of this order
		if(openLines == 0){
			getSuspendedDao().stampSuspendOrderDispatchedDate(getOrder().getReceiptNo());
		}

		
		// Remove this order line from the order's order list
		List<SuspendedOrderLine> listSuspendedOrders = order.getOrderList();
		listSuspendedOrders.remove(line - 1);
		
		// Create another suspended order line that has the same values as suspendedOrderLine1
		suspendedOrderLine2 = getSuspendedDao().findSuspendOrderLineByReceiptNoLineNo(order.getReceiptNo(), line); 
		
		// Change suspendedOrderLine1 object to be the line that was dispatched
		suspendedOrderLine1.setOrderQty(String.valueOf(dispatchedQty));
		suspendedOrderLine1.setValueExDiscount(Utilities.floatToString2Dec(dispatchedValueExDiscount));
		suspendedOrderLine1.setDiscountValue(Utilities.floatToString2Dec(dispatchedDiscValue));
		suspendedOrderLine1.setValueExVat(Utilities.floatToString2Dec(dispatchedValueExVat));
		suspendedOrderLine1.setLineCostValue(dispatchedCostPrice);
		suspendedOrderLine1.setDispatched(dispatchedQty);
		suspendedOrderLine1.setDispatchedDate(new Date());
		suspendedOrderLine1.setDeleted(0);
		
		
		// Change suspendedOrderLine2 object to be the line that was deleted
		suspendedOrderLine2.setOrderQty(String.valueOf(deleteQty));
		suspendedOrderLine2.setValueExDiscount(Utilities.floatToString2Dec(deleteValueExDiscount));
		suspendedOrderLine2.setDiscountValue(Utilities.floatToString2Dec(deleteDiscountValue));
		suspendedOrderLine2.setValueExVat(Utilities.floatToString2Dec(deleteLineValueExVat));
		suspendedOrderLine2.setLineCostValue(deleteLineCostPrice);
		suspendedOrderLine2.setDeleted(deleteQty);
		suspendedOrderLine2.setDeleteDate(new Date());
		suspendedOrderLine2.setDispatched(0);
		suspendedOrderLine2.setDispatchedDate(null);
		
		// Save both lines back to the order's order list
		listSuspendedOrders.add(line - 1, suspendedOrderLine1);
		listSuspendedOrders.add(line - 1, suspendedOrderLine2);
		
		// Update the Suspended Order table (suspendedheader )and it's lines suspendeddetail
		order.updateOrderAndLines();
		
		// Refresh screens and update screen balance
		getSuspendedOrdersScreen().refreshSuspendedOrderHeaders();
		refreshSuspendedLines();
		getLblSuspendProcessBalance().setText(Utilities.floatToString2Dec(order.getBalance()));
	}
	
}
