package com.daniel;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.daniel.dao.ReceiptSettingsDao;
import com.daniel.dao.ReceiptSettingsDaoImpl;
import com.daniel.dao.SuspendedDao;
import com.daniel.dao.SuspendedDaoImpl;
import com.daniel.model.InvoiceSettings;
import com.daniel.model.SuspendedOrder;
import com.daniel.utilities.Utilities;

import javax.swing.JRadioButton;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

/**
 * This class designs and handles all of the operations that are performed on the Retail Order Tab of the main screen's tabbed pane.
 * @author Robert Forde
 *
 */
public class SuspendedOrdersScreen extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable tblSuspendedOrderHeaders;
	
	private JRadioButton rdbtnSuspendedRetail;
	private JRadioButton rdbtnSuspendedTrade;
	
	private SuspendedDao suspendedDao;
	private ReceiptSettingsDao receiptSettingsDao;
	
	private JButton btnSuspendedNewInvoice;
	private JButton btnSuspendedCancelInvoice;
	private JButton btnSuspendedProcessInvoice;
	private JPanel panelSuspendedOrdersOptions;
	private JPanel panelSuspendedOrdersType;
	
	
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

	
	/**
	 * Constructs the Suspended Orders Screen
	 * @param tabbedPane The main program tabbed pane so this panel can be added to it
	 */
	public SuspendedOrdersScreen(JTabbedPane tabbedPane) {
	
		setSuspendedDao(Dan.ctx.getBean("suspendedDaoImpl", SuspendedDaoImpl.class));
		setReceiptSettingsDao(Dan.ctx.getBean("receiptSettingsDaoImpl", ReceiptSettingsDaoImpl.class));
		
		
		// Create the logo images for the application
		Image logo = new ImageIcon(this.getClass().getResource("/Logo199X198.jpg")).getImage();
		ImageIcon imageIcon = new ImageIcon(logo);
		
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Suspended Orders", null, this, null);
		
		// Group the radio buttons.
		ButtonGroup suspendedTypegroup = new ButtonGroup();
		setLayout(null);
		
		JLabel lblSuspendedLogo = new JLabel("New label");
		lblSuspendedLogo.setBounds(1033, 367, 199, 210);
		lblSuspendedLogo.setIcon(imageIcon);
		add(lblSuspendedLogo);
		
		JPanel panelSuspendedOrderHeaders = new JPanel();
		panelSuspendedOrderHeaders.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendedOrderHeaders.setBounds(27, 86, 890, 491);
		panelSuspendedOrderHeaders.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		add(panelSuspendedOrderHeaders);
		panelSuspendedOrderHeaders.setLayout(null);
		
		JScrollPane scrlSuspendedOrderHeaders = new JScrollPane();
		scrlSuspendedOrderHeaders.setBounds(33, 32, 820, 429);
		panelSuspendedOrderHeaders.add(scrlSuspendedOrderHeaders);
		scrlSuspendedOrderHeaders.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		
		tblSuspendedOrderHeaders = new JTable(){
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
				
				// Right justify the last 3 columns
				if ( col == dataModel.getColumnCount() - 1 || col == dataModel.getColumnCount() - 2 || col == dataModel.getColumnCount() - 3 ) 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
				
				// Left justify the other columns
				else 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
				
				return renderer; 
			}
		};
		tblSuspendedOrderHeaders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSuspendedOrderHeaders.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tblSuspendedOrderHeaders.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		scrlSuspendedOrderHeaders.setViewportView(tblSuspendedOrderHeaders);
				
		panelSuspendedOrdersOptions = new JPanel();
		panelSuspendedOrdersOptions.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendedOrdersOptions.setBounds(1033, 86, 199, 255);
		panelSuspendedOrdersOptions.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		add(panelSuspendedOrdersOptions);
		panelSuspendedOrdersOptions.setLayout(null);
				
		btnSuspendedNewInvoice = new JButton("NEW");
		btnSuspendedNewInvoice.setBounds(24, 32, 150, 38);
		panelSuspendedOrdersOptions.add(btnSuspendedNewInvoice);
		btnSuspendedNewInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(rdbtnSuspendedRetail.isSelected()) {
					new SuspendRetailOrderScreen(getReceiptSettingsDao().retrieveDatabasePrintSettings(new InvoiceSettings()), SuspendedOrdersScreen.this);

				} else {
					new SuspendTradeOrderScreen(getReceiptSettingsDao().retrieveDatabasePrintSettings(new InvoiceSettings()), SuspendedOrdersScreen.this);

				}
			}
		});
		btnSuspendedNewInvoice.setFont(new Font("Georgia", Font.BOLD, 18));
		btnSuspendedNewInvoice.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
				
		btnSuspendedCancelInvoice = new JButton("CANCEL");
		btnSuspendedCancelInvoice.setBounds(24, 110, 150, 38);
		panelSuspendedOrdersOptions.add(btnSuspendedCancelInvoice);
		btnSuspendedCancelInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				try {
							
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblSuspendedOrderHeaders.getSelectedRow();
					
					if(row != -1) {
						
						// First ask the user to confirm that they want to cancel this order
						int action = JOptionPane.showConfirmDialog(null, "Do you really want to cancel this Suspended Order record","Cancel",JOptionPane.YES_NO_OPTION);
						
						if (action == 0) {
							// Then we can get the receipt number of the selected row by getting the model of the table and then using the getValueAt() method we can get 
							// the specific row and column that we require.
							// This returns an Object so we get the toString of it and convert it to an integer.
							int receiptNo = Integer.valueOf(tblSuspendedOrderHeaders.getModel().getValueAt(row, 0).toString());
									
							// Call a method to delete the suspended order and it's orderlines 
							int result = getSuspendedDao().makeOrderDeleted(receiptNo);
							
							if(result == 1){
								JOptionPane.showMessageDialog(null, "Suspended Invoice No. " + receiptNo + " has been deleted !");
								refreshSuspendedOrderHeaders();
								
							}else{
								JOptionPane.showMessageDialog(null, "Unable to delete this suspended order !");
							}
							
						}
					} else {					
						JOptionPane.showMessageDialog(null, "You must select a Suspended Order to be cancelled !");
					}
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnSuspendedCancelInvoice.setFont(new Font("Georgia", Font.BOLD, 18));
		btnSuspendedCancelInvoice.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
				
		btnSuspendedProcessInvoice = new JButton("PROCESS");
		btnSuspendedProcessInvoice.setBounds(24, 188, 150, 38);
		panelSuspendedOrdersOptions.add(btnSuspendedProcessInvoice);
		btnSuspendedProcessInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Check if there is a row selected
				int row = tblSuspendedOrderHeaders.getSelectedRow();
										
				if(row!= -1) {
					
					// Get the suspended order number using the getValue method of the table model which returns an object 
					int receiptNo = Integer.valueOf(tblSuspendedOrderHeaders.getValueAt(row, 0).toString());
					
					// Get the selected suspended order from the supendheader table
					SuspendedOrder order = getSuspendedDao().findSuspendOrderByNumber(receiptNo);
					
					// Open the Suspended Orders Processing screen
					new SuspendedProcessScreen(order, SuspendedOrdersScreen.this);
					
				}else {
					
					JOptionPane.showMessageDialog(null, "You must select a Suspended Order to Process !");
				}
			}
		});
		btnSuspendedProcessInvoice.setFont(new Font("Georgia", Font.BOLD, 18));
		btnSuspendedProcessInvoice.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
				
		panelSuspendedOrdersType = new JPanel();
		panelSuspendedOrdersType.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendedOrdersType.setBounds(140, 17, 264, 49);
		panelSuspendedOrdersType.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		add(panelSuspendedOrdersType);
		panelSuspendedOrdersType.setLayout(null);
				
		rdbtnSuspendedRetail = new JRadioButton("RETAIL");
		rdbtnSuspendedRetail.setBounds(6, 14, 132, 23);
		panelSuspendedOrdersType.add(rdbtnSuspendedRetail);
		rdbtnSuspendedRetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshSuspendedOrderHeaders();
			}
		});
		rdbtnSuspendedRetail.setSelected(true);
		rdbtnSuspendedRetail.setForeground(Color.BLACK);
		rdbtnSuspendedRetail.setFont(new Font("Georgia", Font.BOLD, 22));
		rdbtnSuspendedRetail.setBackground(UIManager.getColor("List.dropLineColor"));
		suspendedTypegroup.add(rdbtnSuspendedRetail);
				
		rdbtnSuspendedTrade = new JRadioButton("TRADE");
		rdbtnSuspendedTrade.setBounds(140, 14, 109, 23);
		panelSuspendedOrdersType.add(rdbtnSuspendedTrade);
		rdbtnSuspendedTrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshSuspendedOrderHeaders();
			}
		});
		rdbtnSuspendedTrade.setForeground(Color.BLACK);
		rdbtnSuspendedTrade.setFont(new Font("Georgia", Font.BOLD, 22));
		rdbtnSuspendedTrade.setBackground(UIManager.getColor("List.dropLineColor"));
		suspendedTypegroup.add(rdbtnSuspendedTrade);
		
		refreshSuspendedOrderHeaders();
	}
	
	
	/**
	 * This method retrieves all of the open suspended orders that are of the type that is selected and loads then into a table on screen  
	 */
	public void refreshSuspendedOrderHeaders(){
		
		String type = rdbtnSuspendedRetail.isSelected() ? "Retail" : "Trade"; 
					
		// Return a list of open suspended order headers
		List<SuspendedOrder> suspendedList = getSuspendedDao().suspendedHeadersListOpen(type);
			
		String columns[] = {"NUMBER", "DATE", "CUSTOMER", "EX VAT", "INC VAT", "BALANCE"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
		for(SuspendedOrder order : suspendedList){
			String tableNumber = String.valueOf(order.getReceiptNo());
			String tableDate = df.format(order.getOrderDate());
			String tableCustomer = order.getName();
			String tableExVat = order.getTotalExVat();
			String tableIncVat = order.getTotalPostRounding();
			String tableBalance = Utilities.floatToString2Dec(order.getBalance());
			Object[] line = {tableNumber, tableDate, tableCustomer, tableExVat, tableIncVat, tableBalance};
			tableModel.addRow(line);
		}
		
		tblSuspendedOrderHeaders.setModel(tableModel);
			
		tblSuspendedOrderHeaders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumnModel columnModel = tblSuspendedOrderHeaders.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(80);
		columnModel.getColumn(1).setPreferredWidth(90);
		columnModel.getColumn(2).setPreferredWidth(360);
		columnModel.getColumn(3).setPreferredWidth(90);
		columnModel.getColumn(4).setPreferredWidth(90);
		columnModel.getColumn(5).setPreferredWidth(90);
	}
}
