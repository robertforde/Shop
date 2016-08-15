package com.daniel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;

import com.daniel.model.Customer;
import com.daniel.model.Order;
import com.daniel.model.Tradesman;
import com.daniel.dao.CustomerDao;
import com.daniel.dao.CustomerDaoImpl;
import com.daniel.dao.TradesmanDao;
import com.daniel.dao.TradesmanDaoImpl;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import java.util.List;

import javax.swing.ListSelectionModel;

/**
 * This class opens a JFrame that displays customers or tradesmen (depending on the order's saletype) based on various selection criteria.
 * The user can select any of these people or indeed enter a new customer or tradesman. When selected the cusomer's details are saved
 * onto the order object 
 * @author Robert Forde
 *
 */
public class CustomerDetails extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtCustDetailsName;
	private JTextField txtCustDetailsAddress1;
	private JTextField txtCustDetailsAddress2;
	private JTextField txtCustDetailsPhone;
	private JButton btnCustDetailsSave;
	private JButton btnCustDetailsCancel;
	private JLabel lblCustDetailsAddress2;
	
	private Order order;
	private JTable tblCustDetails;
	private JTextField txtCustDetailsId;

	private TradesmanDao tradesmanDao;
	
	private CustomerDao customerDao;

	
	public TradesmanDao getTradesmanDao() {
		return tradesmanDao;
	}

	public void setTradesmanDao(TradesmanDao tradesmanDao) {
		this.tradesmanDao = tradesmanDao;
	}
	
	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	
	/**
	 * 
	 * @param order The current order that a selected customer/tradesman will be saved onto  
	 */
	public CustomerDetails(Order order){

		setTradesmanDao(Dan.ctx.getBean("tradesmanDaoImpl", TradesmanDaoImpl.class));
		setCustomerDao(Dan.ctx.getBean("customerDaoImpl", CustomerDaoImpl.class));
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		new JFrame();
		setBounds(150, 150, 1079, 475);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);
		
		JLabel lblCustDetailsName = new JLabel("Name");
		lblCustDetailsName.setFont(new Font("Georgia", Font.BOLD, 16));
		lblCustDetailsName.setBounds(22, 23, 71, 14);
		getContentPane().add(lblCustDetailsName);
		
		JLabel lblCustDetailsAddress1 = new JLabel("Address Line 1");
		lblCustDetailsAddress1.setFont(new Font("Georgia", Font.BOLD, 16));
		lblCustDetailsAddress1.setBounds(271, 23, 154, 14);
		getContentPane().add(lblCustDetailsAddress1);
		
		txtCustDetailsName = new JTextField();
		txtCustDetailsName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// Disable the normal tab key focus on the Name textbox
				txtCustDetailsName.setFocusTraversalKeysEnabled(false);
				
				filterCustomers(arg0);
			}
		});
		txtCustDetailsName.setBounds(22, 48, 226, 20);
		getContentPane().add(txtCustDetailsName);
		txtCustDetailsName.setColumns(10);
		
		txtCustDetailsAddress1 = new JTextField();
		txtCustDetailsAddress1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// Disable the normal tab key focus on the address line1 textbox
				txtCustDetailsAddress1.setFocusTraversalKeysEnabled(false);
				
				filterCustomers(arg0);
			}
		});
		txtCustDetailsAddress1.setBounds(271, 48, 300, 20);
		getContentPane().add(txtCustDetailsAddress1);
		txtCustDetailsAddress1.setColumns(10);
		
		txtCustDetailsAddress2 = new JTextField();
		txtCustDetailsAddress2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// Disable the normal tab key focus on the address line 2
				txtCustDetailsAddress2.setFocusTraversalKeysEnabled(false);
				
				filterCustomers(arg0);
			}
		});
		txtCustDetailsAddress2.setBounds(581, 48, 300, 20);
		getContentPane().add(txtCustDetailsAddress2);
		txtCustDetailsAddress2.setColumns(10);
		
		JLabel lblCustDetailsPhone = new JLabel("Phone");
		lblCustDetailsPhone.setFont(new Font("Georgia", Font.BOLD, 16));
		lblCustDetailsPhone.setBounds(911, 23, 71, 14);
		getContentPane().add(lblCustDetailsPhone);
		
		txtCustDetailsPhone = new JTextField();
		txtCustDetailsPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// Disable the normal tab key focus on the address line1 textbox
				txtCustDetailsPhone.setFocusTraversalKeysEnabled(false);
				
				filterCustomers(arg0);
			}
		});
		txtCustDetailsPhone.setBounds(911, 48, 127, 20);
		getContentPane().add(txtCustDetailsPhone);
		txtCustDetailsPhone.setColumns(10);
		
		btnCustDetailsSave = new JButton("SAVE");
		btnCustDetailsSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = getOrder().getCustId();

				// If new customer then leave the id as 0
				if(id == 0 && !txtCustDetailsId.getText().equals("")){
					id = Integer.parseInt(txtCustDetailsId.getText());
				}
				
				getOrder().setCustId(id);
				getOrder().setName(txtCustDetailsName.getText());
				getOrder().setAddress1(txtCustDetailsAddress1.getText());
				getOrder().setAddress2(txtCustDetailsAddress2.getText());
				getOrder().setPhone(txtCustDetailsPhone.getText());
				dispose();
			}
		});
		btnCustDetailsSave.setFont(new Font("Georgia", Font.BOLD, 16));
		btnCustDetailsSave.setBounds(331, 390, 120, 38);
		getContentPane().add(btnCustDetailsSave);
		
		btnCustDetailsCancel = new JButton("CANCEL");
		btnCustDetailsCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCustDetailsCancel.setFont(new Font("Georgia", Font.BOLD, 16));
		btnCustDetailsCancel.setBounds(615, 390, 120, 38);
		getContentPane().add(btnCustDetailsCancel);
		
		lblCustDetailsAddress2 = new JLabel("Address Line 2");
		lblCustDetailsAddress2.setFont(new Font("Georgia", Font.BOLD, 16));
		lblCustDetailsAddress2.setBounds(581, 23, 154, 14);
		getContentPane().add(lblCustDetailsAddress2);
		
		JPanel panelCustDetails = new JPanel();
		panelCustDetails.setBounds(22, 96, 1016, 281);
		getContentPane().add(panelCustDetails);
		panelCustDetails.setLayout(null);
		
		JScrollPane scrCustDetails = new JScrollPane();
		scrCustDetails.setBounds(0, 0, 1028, 281);
		panelCustDetails.add(scrCustDetails);
		
		tblCustDetails = new JTable();
		tblCustDetails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblCustDetails.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// If enter pressed call a method to fill the customer fields with this their name, address1, address2 and phone  
				if(arg0.getKeyCode() == 10) {
					customerSelected();					
				}
			}
		});
		tblCustDetails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				customerSelected();
			}
		});
		scrCustDetails.setViewportView(tblCustDetails);
		
		txtCustDetailsId = new JTextField();
		txtCustDetailsId.setEditable(false);
		txtCustDetailsId.setEnabled(false);
		txtCustDetailsId.setBounds(10, 48, 10, 20);
		getContentPane().add(txtCustDetailsId);
		txtCustDetailsId.setColumns(10);
		txtCustDetailsId.setVisible(false);
		
		setTitle("Customer Details");
		
		this.setOrder(order);
		//If the order has details for the name address or phone then put them into the fields
		if (!order.getName().isEmpty())
			txtCustDetailsName.setText(order.getName());
		if (!order.getAddress1().isEmpty())
			txtCustDetailsAddress1.setText(order.getAddress1());
		if (!order.getAddress2().isEmpty())
			txtCustDetailsAddress2.setText(order.getAddress2());
		if (!order.getPhone().isEmpty())
			txtCustDetailsPhone.setText(order.getPhone());
		
		// Call the filter method to display the table based on what is entered
		filterCustomers(null);
	}

	
	/**
	 * Method to retrieve, from the database, all of the customers/tradesmen that are found based on the user's selections and to load them
	 * into a table on screen for the user
	 * @param arg0 A Key Event if this is a tab then the focus will go onto the customer details table
	 */
	public void filterCustomers(KeyEvent arg0){
		try {
		
			// Call a method to retrieve the customers from the relevant table based on the users filtered criteria
			if(getOrder().getSaleType() == "Trade" || getOrder().getSaleType() == "TradeQuote") {
				
				List<Tradesman> tradesmenList = getTradesmanDao().findFilteredTradesmen(txtCustDetailsName.getText(), txtCustDetailsAddress1.getText(), txtCustDetailsAddress2.getText(), 
						txtCustDetailsPhone.getText());
				
				String columns[] = {"ID", "NAME", "ADDRESS LINE 1", "ADDRESS LINE 2", "PHONE"};
				DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
				
				for(Tradesman tradesman: tradesmenList){
					String tableId= String.valueOf(tradesman.getTradesManId());
					String tableName = tradesman.getName();
					String tableAddress1 = tradesman.getAddressLine1();
					String tableAddress2 = tradesman.getAddressLine2();
					String tablePhone = tradesman.getPhone();
					Object[] line = {tableId, tableName, tableAddress1, tableAddress2, tablePhone};
					tableModel.addRow(line);
				}
			
				tblCustDetails.setModel(tableModel);
				
			} else if(getOrder().getSaleType() == "Retail" || getOrder().getSaleType() == "RetailQuote") {
				
				List<Customer> customerList = getCustomerDao().findFilteredCustomer(txtCustDetailsName.getText(), txtCustDetailsAddress1.getText(), txtCustDetailsAddress2.getText(), 
						txtCustDetailsPhone.getText());
				
				String columns[] = {"ID", "NAME", "ADDRESS LINE 1", "ADDRESS LINE 2", "PHONE"};
				DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
				
				for(Customer customer: customerList){
					String tableId= String.valueOf(customer.getCustomerId());
					String tableName = customer.getName();
					String tableAddress1 = customer.getAddressLine1();
					String tableAddress2 = customer.getAddressLine2();
					String tablePhone = customer.getPhone();
					Object[] line = {tableId, tableName, tableAddress1, tableAddress2, tablePhone};
					tableModel.addRow(line);
				}
			
				tblCustDetails.setModel(tableModel);
			}
			
			
			tblCustDetails.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			TableColumnModel columnModel = tblCustDetails.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(10);
			columnModel.getColumn(1).setPreferredWidth(226);
			columnModel.getColumn(2).setPreferredWidth(300);
			columnModel.getColumn(3).setPreferredWidth(300);
			columnModel.getColumn(4).setPreferredWidth(173);
			
			// Set the tab key to the customer details possible matches table
			if(arg0 != null && arg0.getKeyCode() == KeyEvent.VK_TAB){
				tblCustDetails.requestFocus();
			}


				
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	
	
	/**
	 * Method to retrieve and show the selected customer's/tradesman's details in the fields at the top of the scrren
	 */
	public void customerSelected() {
		
		try {
		
			// We use the table's getSelectedRow() method to find the row that the User selected. 
			int row = tblCustDetails.getSelectedRow();
	
			// Then we can get the id of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
			// row and column that we require.
			// This returns an Object so we get the toString and then integer of it.
			int id = Integer.valueOf(tblCustDetails.getModel().getValueAt(row, 0).toString());
	
			// Call a method to find the customer details in the appropriate table (based on the order's saletype) and return it
			
			if(order.getSaleType().equals("Retail") || order.getSaleType().equals("RetailQuote")) {
				Customer customer = getCustomerDao().selectFilteredCustomer(id);
				txtCustDetailsId.setText(String.valueOf(id));
				txtCustDetailsName.setText(customer.getName());
				txtCustDetailsAddress1.setText(customer.getAddressLine1());
				txtCustDetailsAddress2.setText(customer.getAddressLine2());
				txtCustDetailsPhone.setText(customer.getPhone());

			} else if(order.getSaleType().equals("Trade") || order.getSaleType().equals("TradeQuote")) {
				Tradesman tradesman = getTradesmanDao().selectFilteredTradesman(id);
				txtCustDetailsId.setText(String.valueOf(id));
				txtCustDetailsName.setText(tradesman.getName());
				txtCustDetailsAddress1.setText(tradesman.getAddressLine1());
				txtCustDetailsAddress2.setText(tradesman.getAddressLine2());
				txtCustDetailsPhone.setText(tradesman.getPhone());
			}		
			
			txtCustDetailsName.requestFocus();
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}
}