package com.daniel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

import com.daniel.dao.CustomerDao;
import com.daniel.dao.CustomerDaoImpl;
import com.daniel.model.Customer;

import javax.swing.ListSelectionModel;

/**
 * This class handles the Customers Tab on the main tabbed panel. It retrieves and displays all of the customers from the database.
 * The screen has various filters to allow the user to filter by name, address and phone number. It also gives the user the ability to
 * perform CRUD operations on customers in the database
 * @author Robert Forde
 *
 */
public class CustomersScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> comboBoxCustomersSrch;
	
	private JTable tblCustomersList;
	
	private JTextField txtCustomersName;
	private JTextField txtCustomersAddressLine1;
	private JTextField txtCustomersAddressLine2;
	private JTextField txtCustomersPhone;
	private JTextField txtCustomersSrch;
	private JTextField txtCustomersID;

	private CustomerDao customerDao;

	
	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	
	/**
	 * Constructs the customers screen
	 * @param tabbedPane The main program tabbed pane so this panel can be added to it
	 */
	public CustomersScreen(JTabbedPane tabbedPane){

		setCustomerDao(Dan.ctx.getBean("customerDaoImpl", CustomerDaoImpl.class));
		
		// Create the logo images for the application
		Image logo = new ImageIcon(this.getClass().getResource("/Logo199X198.jpg")).getImage();
		ImageIcon imageIcon = new ImageIcon(logo);
				
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Customers", null, this, null);
		setLayout(null);
		
		JPanel panelCustomerMaintenance = new JPanel();
		panelCustomerMaintenance.setBackground(UIManager.getColor("List.dropLineColor"));
		panelCustomerMaintenance.setBounds(23, 424, 1237, 161);
		this.add(panelCustomerMaintenance);
		panelCustomerMaintenance.setLayout(null);
		
		txtCustomersName = new JTextField();
		txtCustomersName.setBounds(38, 49, 144, 32);
		txtCustomersName.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelCustomerMaintenance.add(txtCustomersName);
		txtCustomersName.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtCustomersName.setColumns(10);
		
		txtCustomersAddressLine1 = new JTextField();
		txtCustomersAddressLine1.setBounds(217, 49, 400, 32);
		txtCustomersAddressLine1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelCustomerMaintenance.add(txtCustomersAddressLine1);
		txtCustomersAddressLine1.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtCustomersAddressLine1.setColumns(10);
		
		txtCustomersAddressLine2 = new JTextField();
		txtCustomersAddressLine2.setBounds(627, 49, 400, 32);
		txtCustomersAddressLine2.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelCustomerMaintenance.add(txtCustomersAddressLine2);
		txtCustomersAddressLine2.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtCustomersAddressLine2.setColumns(10);
		
		txtCustomersPhone = new JTextField();
		txtCustomersPhone.setBounds(1055, 49, 144, 32);
		txtCustomersPhone.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelCustomerMaintenance.add(txtCustomersPhone);
		txtCustomersPhone.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtCustomersPhone.setColumns(10);
		
		JLabel lblCustomersName = new JLabel("Name");
		lblCustomersName.setBounds(38, 19, 72, 19);
		panelCustomerMaintenance.add(lblCustomersName);
		lblCustomersName.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblCustomersAddressLine1 = new JLabel("Address Line 1");
		lblCustomersAddressLine1.setBounds(217, 19, 135, 19);
		panelCustomerMaintenance.add(lblCustomersAddressLine1);
		lblCustomersAddressLine1.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblCustomersAddressLine2 = new JLabel("Address Line 2");
		lblCustomersAddressLine2.setBounds(627, 19, 135, 19);
		panelCustomerMaintenance.add(lblCustomersAddressLine2);
		lblCustomersAddressLine2.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblCustomersPhone = new JLabel("Phone");
		lblCustomersPhone.setBounds(1055, 19, 72, 19);
		panelCustomerMaintenance.add(lblCustomersPhone);
		lblCustomersPhone.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JButton btnCustomersInsert = new JButton("INSERT");
		btnCustomersInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// First check that something was entered in the Customer fields
					if(!txtCustomersName.getText().isEmpty()) {
						
						// Check if the customer already exists
						Customer customer = new Customer(txtCustomersName.getText(), txtCustomersAddressLine1.getText(), txtCustomersAddressLine2.getText(), 
								txtCustomersPhone.getText(), 0);
						
						Customer checkCustomer = getCustomerDao().customerExists(customer);
						
						// If this customer was not found in the table already
						if(checkCustomer.getName() == null) {

							getCustomerDao().customerInsert(customer);
							
							refreshCustomersTable();
							txtCustomersSrch.setText("");
							txtCustomersID.setText("");
							txtCustomersName.setText("");
							txtCustomersAddressLine1.setText("");
							txtCustomersAddressLine2.setText("");
							txtCustomersPhone.setText("");
							
							JOptionPane.showMessageDialog(null, "Customer " + checkCustomer + " has been inserted !");
							
						}else {
							JOptionPane.showMessageDialog(null, "Customer " + checkCustomer.getName() + " is already in the Database !");	
						}
							
					} else {
						JOptionPane.showMessageDialog(null, "You must enter a Customer Name !");
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnCustomersInsert.setFont(new Font("Georgia", Font.BOLD, 16));
		btnCustomersInsert.setBounds(310, 104, 128, 38);
		btnCustomersInsert.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelCustomerMaintenance.add(btnCustomersInsert);
		
		JButton btnCustomersUpdate = new JButton("UPDATE");
		btnCustomersUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					// First check that something was entered in the Customer fields
					if( !(txtCustomersName.getText().isEmpty() && txtCustomersPhone.getText().isEmpty()) ) {
					
						Customer customer = new Customer(Integer.valueOf(txtCustomersID.getText()), txtCustomersName.getText(), txtCustomersAddressLine1.getText(), 
								txtCustomersAddressLine2.getText(), txtCustomersPhone.getText(),0);
						
						int result = getCustomerDao().customerUpdate(customer);
						
						if (result == 1) {
							
							JOptionPane.showMessageDialog(null, "Customer " + customer.getName() + " has been updated !");
							

							refreshCustomersTable();
							txtCustomersID.setText("");
							txtCustomersName.setText("");
							txtCustomersAddressLine1.setText("");
							txtCustomersAddressLine2.setText("");
							txtCustomersPhone.setText("");
							
						}else{
							JOptionPane.showMessageDialog(null, "Customer " + customer.getName() + " is not in the Database !");
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "You must select a customer before you update !");	
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnCustomersUpdate.setFont(new Font("Georgia", Font.BOLD, 16));
		btnCustomersUpdate.setBounds(552, 104, 128, 38);
		btnCustomersUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelCustomerMaintenance.add(btnCustomersUpdate);
		
		JButton btnCustomersDelete = new JButton("DELETE");
		btnCustomersDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int result = getCustomerDao().customerDelete(txtCustomersID.getText());
					
					if(result == 1){
						
						JOptionPane.showMessageDialog(null, "Customer " + txtCustomersName.getText() + " has been deleted !");
						
						refreshCustomersTable();
						txtCustomersID.setText("");
						txtCustomersName.setText("");
						txtCustomersAddressLine1.setText("");
						txtCustomersAddressLine2.setText("");
						txtCustomersPhone.setText("");
						
					}else{
						JOptionPane.showMessageDialog(null, "Customer " + txtCustomersName.getText() + " is not in the Database !");
					}
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnCustomersDelete.setFont(new Font("Georgia", Font.BOLD, 16));
		btnCustomersDelete.setBounds(790, 104, 128, 38);
		btnCustomersDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelCustomerMaintenance.add(btnCustomersDelete);
		
		txtCustomersID = new JTextField();
		txtCustomersID.setEnabled(false);
		txtCustomersID.setEditable(false);
		txtCustomersID.setBounds(10, 49, 4, 20);
		panelCustomerMaintenance.add(txtCustomersID);
		txtCustomersID.setColumns(10);
		txtCustomersID.setVisible(false);
		
		JPanel panelCustomerList = new JPanel();
		panelCustomerList.setBackground(UIManager.getColor("List.dropLineColor"));
		panelCustomerList.setBounds(23, 11, 1237, 375);
		this.add(panelCustomerList);
		panelCustomerList.setLayout(null);
		
		comboBoxCustomersSrch = new JComboBox<String>();
		comboBoxCustomersSrch.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				refreshCustomersTable();
			}
		});
		comboBoxCustomersSrch.setModel(new DefaultComboBoxModel<String>(new String[] {"NAME", "ADDRESS LINE 1", "ADDRESS LINE 2", "PHONE"}));
		comboBoxCustomersSrch.setFont(new Font("Georgia", Font.BOLD, 16));
		comboBoxCustomersSrch.setBounds(32, 26, 175, 38);
		comboBoxCustomersSrch.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelCustomerList.add(comboBoxCustomersSrch);

		JLabel lblCustomersLogo = new JLabel("New label");
		lblCustomersLogo.setBounds(1006, 114, 199, 210);
		// Put Logo image on customers tab
		lblCustomersLogo.setIcon(imageIcon);
		panelCustomerList.add(lblCustomersLogo);
		
		txtCustomersSrch = new JTextField();
		txtCustomersSrch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				refreshCustomersTable();
			}
		});
		txtCustomersSrch.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtCustomersSrch.setBounds(356, 30, 313, 32);
		txtCustomersSrch.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelCustomerList.add(txtCustomersSrch);
		txtCustomersSrch.setColumns(10);
		
		JButton btnCustomersLoadAll = new JButton("LOAD ALL CUSTOMERS");
		btnCustomersLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtCustomersSrch.setText("");
				refreshCustomersTable();
			}
		});
		btnCustomersLoadAll.setFont(new Font("Georgia", Font.BOLD, 16));
		btnCustomersLoadAll.setBounds(854, 26, 266, 38);
		btnCustomersLoadAll.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelCustomerList.add(btnCustomersLoadAll);
		
		JScrollPane scrlCustomersList = new JScrollPane();
		scrlCustomersList.setBounds(32, 80, 949, 284);
		scrlCustomersList.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelCustomerList.add(scrlCustomersList);
		
		tblCustomersList = new JTable(){
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
				
				// Left justify all of the columns
				((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
				
				return renderer; 
			}
			
		};
		tblCustomersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblCustomersList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblCustomersList.getSelectedRow();
					
					// Then we can get the id of the row that we require.
					// We then get the value of column 0 for that row in the model of our table, which returns an Object so we get the String of it and convert to integer
					String idString = tblCustomersList.getModel().getValueAt(row, 0).toString();
					int idRow = Integer.valueOf(idString);
					
					Customer customer = getCustomerDao().customerRetrieve(idRow);
					
					txtCustomersID.setText(idString);
					txtCustomersName.setText(customer.getName());
					txtCustomersAddressLine1.setText(customer.getAddressLine1());
					txtCustomersAddressLine2.setText(customer.getAddressLine2());
					txtCustomersPhone.setText(customer.getPhone());
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		tblCustomersList.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		// This formats the Headers
		tblCustomersList.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));

		scrlCustomersList.setViewportView(tblCustomersList);

		refreshCustomersTable();
	}

	
	/**
	 * Method that retrieves a list of customers from the database based on the user's selection criteria and loads them into a table
	 * that is displayed on the screen 
	 */
	public void refreshCustomersTable() {
		String selection = comboBoxCustomersSrch.getSelectedItem().toString();
		String filter = "%" + txtCustomersSrch.getText() + "%";
		
		if(selection=="NAME"){
			selection = "name";
		} else if(selection=="ADDRESS LINE 1"){
			selection = "addressLine1";
		} else if(selection=="ADDRESS LINE 2") {
			selection = "addressLine2";
		} else {
			selection = "phone";
		}

		try {
			
			// Call a method to retrieve a list of the tradesmen from the tradesman table based on the selected filter 
			List<Customer> customerList = getCustomerDao().customerLoadFiltered(selection, filter);
						
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
						
			tblCustomersList.setModel(tableModel);
			
			// Set the column widths
			tblCustomersList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel columnModel = tblCustomersList.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(25);
			columnModel.getColumn(1).setPreferredWidth(180);
			columnModel.getColumn(2).setPreferredWidth(295);
			columnModel.getColumn(3).setPreferredWidth(295);
			columnModel.getColumn(4).setPreferredWidth(133);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}		
	}

}