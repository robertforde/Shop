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

import javax.swing.BorderFactory;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.daniel.dao.AccountDao;
import com.daniel.dao.AccountDaoImpl;
import com.daniel.model.Account;
import com.daniel.model.InvoiceSettings;
import com.daniel.utilities.Utilities;

/**
 * This class opens a JPanel that list all of the accounts with their balances.
 * The user has the option of selecting an account from the list and view/perform transactions on the account 
 * @author Robert Forde
 *
 */
public class AccountListScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InvoiceSettings invoiceSettings;
	
	private JComboBox<String> comboBoxAccountListSrch;
	
	private JTable tblAccountListList;
	
	private JTextField txtAccountListSrch;
	private JTextField txtAccountListID;
	private JTextField txtAccountListName;
	private JTextField txtAccountListAddressLine1;
	private JTextField txtAccountListAddressLine2;
	private JTextField txtAccountListPhone;
	
	private Account acc;
	
	private AccountDao accountDao;
	
	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
 
	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}
	
	public Account getAcc() {
		return acc;
	}

	public void setAcc(Account acc) {
		this.acc = acc;
	}

	
	/**
	 * 
	 * @param tabbedPane The main program tabbed pane so this panel can be added to it
	 * @param invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 */
	public AccountListScreen(final JTabbedPane tabbedPane, InvoiceSettings invoiceSettings){
		
		setAccountDao(Dan.ctx.getBean("accountDaoImpl", AccountDaoImpl.class));
		
		// Create the logo images for the application
		Image logo = new ImageIcon(this.getClass().getResource("/Logo199X198.jpg")).getImage();
		ImageIcon imageIcon = new ImageIcon(logo);
						
		setInvoiceSettings(invoiceSettings);
				
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Accounts", null, this, null);
		setLayout(null);
		
		JPanel panelAccountListList = new JPanel();
		panelAccountListList.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountListList.setBorder(BorderFactory.createRaisedBevelBorder());
		panelAccountListList.setBounds(23, 11, 1237, 375);
		this.add(panelAccountListList);
		panelAccountListList.setLayout(null);
		
		comboBoxAccountListSrch = new JComboBox<String>();
		comboBoxAccountListSrch.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				txtAccountListSrch.setText("");
				refreshAccountsTable();
			}
		});
		comboBoxAccountListSrch.setFont(new Font("Georgia", Font.BOLD, 16));
		comboBoxAccountListSrch.setModel(new DefaultComboBoxModel<String>(new String[] {"NAME", "ADDRESS LINE 1", "ADDRESS LINE 2", "PHONE"}));
		comboBoxAccountListSrch.setBounds(32, 26, 175, 38);
		comboBoxAccountListSrch.setSelectedItem("NAME");
		comboBoxAccountListSrch.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListList.add(comboBoxAccountListSrch);
		
		txtAccountListSrch = new JTextField();
		txtAccountListSrch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				refreshAccountsTable();
			}
		});
		txtAccountListSrch.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountListSrch.setBounds(309, 30, 313, 32);
		txtAccountListSrch.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListList.add(txtAccountListSrch);
		txtAccountListSrch.setColumns(10);
		
		JButton btnAccountListLoadAll = new JButton("LOAD ALL ACCOUNTS");
		btnAccountListLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAccountListSrch.setText("");
				refreshAccountsTable();
			}
		});
		btnAccountListLoadAll.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAccountListLoadAll.setBounds(715, 26, 266, 38);
		btnAccountListLoadAll.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListList.add(btnAccountListLoadAll);
		
		JScrollPane scrlAccountListList = new JScrollPane();
		scrlAccountListList.setBounds(32, 80, 949, 284);
		scrlAccountListList.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListList.add(scrlAccountListList);
		
		tblAccountListList = new JTable(){
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
				
				// Right justify the last column (Price)
				if ( col == dataModel.getColumnCount() - 1 ) 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
				
				// Left justify the other columns
				else 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
				
				return renderer; 
			}
			
		};
		tblAccountListList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblAccountListList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try {
					
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblAccountListList.getSelectedRow();
					
					// Then we can get the id of the row that we require.
					// We then get the value of column 0 for that row in the model of our table, which returns an Object so we get the String of it and convert to integer
					String idString = tblAccountListList.getModel().getValueAt(row, 0).toString();
					int id = Integer.valueOf(idString);
					
					acc = getAccountDao().accountListClickAccount(id);
					txtAccountListID.setText(String.valueOf((acc.getId())));
					txtAccountListName.setText(acc.getName());
					txtAccountListAddressLine1.setText(acc.getAddressLine1());
					txtAccountListAddressLine2.setText(acc.getAddressLine2());
					txtAccountListPhone.setText(acc.getPhone());

					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		tblAccountListList.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		// This formats the Headers
		tblAccountListList.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		scrlAccountListList.setViewportView(tblAccountListList);
		
		JLabel lblAccountListLogo = new JLabel("New label");
		lblAccountListLogo.setBounds(1007, 164, 199, 200);
		// Put Logo image on accounts tab
		lblAccountListLogo.setIcon(imageIcon);
		panelAccountListList.add(lblAccountListLogo);
		
		JButton btnAccountListAccount = new JButton("ACCOUNT");
		btnAccountListAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Check if there is a row selected
				int id = tblAccountListList.getSelectedRow();
				if(id!= -1) {
					new AccountDetailScreen(getInvoiceSettings(),AccountListScreen.this, getAcc());
				}
				else
					JOptionPane.showMessageDialog(null, "You must select an account !");
			}
		});
		btnAccountListAccount.setFont(new Font("Georgia", Font.BOLD, 18));
		btnAccountListAccount.setBounds(1007, 80, 199, 46);
		btnAccountListAccount.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListList.add(btnAccountListAccount);
		
		JPanel panelAccountListMaintenance = new JPanel();
		panelAccountListMaintenance.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountListMaintenance.setBorder(BorderFactory.createRaisedBevelBorder());
		panelAccountListMaintenance.setBounds(23, 424, 1237, 161);
		this.add(panelAccountListMaintenance);
		panelAccountListMaintenance.setLayout(null);
		
		JLabel lblAccountListName = new JLabel("Name");
		lblAccountListName.setForeground(Color.BLACK);
		lblAccountListName.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountListName.setBounds(38, 19, 72, 19);
		panelAccountListMaintenance.add(lblAccountListName);
		
		JLabel lblAccountListAddressLine1 = new JLabel("Address Line 1");
		lblAccountListAddressLine1.setForeground(Color.BLACK);
		lblAccountListAddressLine1.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountListAddressLine1.setBounds(217, 19, 135, 19);
		panelAccountListMaintenance.add(lblAccountListAddressLine1);
		
		JLabel lblAccountListAddressLine2 = new JLabel("Address Line 2");
		lblAccountListAddressLine2.setForeground(Color.BLACK);
		lblAccountListAddressLine2.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountListAddressLine2.setBounds(627, 19, 135, 19);
		panelAccountListMaintenance.add(lblAccountListAddressLine2);
		
		JLabel lblAccountListPhone = new JLabel("Phone");
		lblAccountListPhone.setForeground(Color.BLACK);
		lblAccountListPhone.setFont(new Font("Georgia", Font.BOLD, 16));
		lblAccountListPhone.setBounds(1055, 19, 72, 19);
		panelAccountListMaintenance.add(lblAccountListPhone);
		
		txtAccountListID = new JTextField();
		txtAccountListID.setEnabled(false);
		txtAccountListID.setEditable(false);
		txtAccountListID.setVisible(false);
		txtAccountListID.setBounds(10, 49, 4, 20);
		panelAccountListMaintenance.add(txtAccountListID);
		txtAccountListID.setColumns(10);
		
		txtAccountListName = new JTextField();
		txtAccountListName.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountListName.setBounds(38, 49, 144, 28);
		txtAccountListName.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListMaintenance.add(txtAccountListName);
		txtAccountListName.setColumns(10);
		
		txtAccountListAddressLine1 = new JTextField();
		txtAccountListAddressLine1.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountListAddressLine1.setColumns(10);
		txtAccountListAddressLine1.setBounds(217, 49, 400, 28);
		txtAccountListAddressLine1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListMaintenance.add(txtAccountListAddressLine1);
		
		txtAccountListAddressLine2 = new JTextField();
		txtAccountListAddressLine2.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountListAddressLine2.setColumns(10);
		txtAccountListAddressLine2.setBounds(627, 49, 400, 28);
		txtAccountListAddressLine2.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListMaintenance.add(txtAccountListAddressLine2);
		
		txtAccountListPhone = new JTextField();
		txtAccountListPhone.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtAccountListPhone.setColumns(10);
		txtAccountListPhone.setBounds(1055, 49, 144, 28);
		txtAccountListPhone.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListMaintenance.add(txtAccountListPhone);
		
		JButton btnAccountListInsert = new JButton("INSERT");
		btnAccountListInsert.setForeground(Color.BLACK);
		btnAccountListInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// First check that something was entered in the Customer fields
					if(!txtAccountListName.getText().isEmpty()) {

						Account accountCheck = new Account(txtAccountListName.getText(), txtAccountListAddressLine1.getText(), txtAccountListAddressLine2.getText(), 
								   txtAccountListPhone.getText(), 0.00f); 
						accountCheck = getAccountDao().accountExists(accountCheck);

						Account account = new Account(txtAccountListName.getText(), txtAccountListAddressLine1.getText(), txtAccountListAddressLine2.getText(), 
								   txtAccountListPhone.getText(), 0.00f);
						
						// Check if this account already exists
						if(accountCheck.getName() == null) {
								
							int result = getAccountDao().accountInsert(account);
							
							if(result == 1) {
								refreshAccountsTable();
								txtAccountListSrch.setText("");
								txtAccountListID.setText("");
								txtAccountListName.setText("");
								txtAccountListAddressLine1.setText("");
								txtAccountListAddressLine2.setText("");
								txtAccountListPhone.setText("");
								
								JOptionPane.showMessageDialog(null, "Account " + account.getName() + " has been inserted !");
							
							}else{
								JOptionPane.showMessageDialog(null, "Account not Inerted !");	
							}
							
						}else{
							JOptionPane.showMessageDialog(null, "Account " + account.getName() + " is already in the database !" );
						}
							
					} else {
						JOptionPane.showMessageDialog(null, "You must enter a Account Name !");
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnAccountListInsert.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAccountListInsert.setBounds(310, 104, 128, 38);
		btnAccountListInsert.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListMaintenance.add(btnAccountListInsert);
		
		JButton btnAccountListUpdate = new JButton("UPDATE");
		btnAccountListUpdate.setForeground(Color.BLACK);
		btnAccountListUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					// First check that something was entered in the Customer fields
					if( !(txtAccountListName.getText().isEmpty() && txtAccountListPhone.getText().isEmpty()) ) {
					
						// If no account ever selected then prevent the program from crashing 
						int id = txtAccountListID.getText().equals("") ? 0 : Integer.parseInt(txtAccountListID.getText());
						
						Account accountCheck = new Account(id, txtAccountListName.getText(), txtAccountListAddressLine1.getText(), 
								txtAccountListAddressLine2.getText(), txtAccountListPhone.getText(), 0.00f); 
						accountCheck = getAccountDao().accountIdExists(accountCheck);

						Account account = new Account(id, txtAccountListName.getText(), txtAccountListAddressLine1.getText(), 
								txtAccountListAddressLine2.getText(), txtAccountListPhone.getText(), 0.00f);

						// Check if this account already exists
						if(accountCheck.getName() != null) {
							
							int result = getAccountDao().accountUpdate(account);
						
							if (result == 1) {
								refreshAccountsTable();
								txtAccountListID.setText("");
								txtAccountListName.setText("");
								txtAccountListAddressLine1.setText("");
								txtAccountListAddressLine2.setText("");
								txtAccountListPhone.setText("");
								
								JOptionPane.showMessageDialog(null, "Account " + account.getName() + " has been updated !");
							
							}else {
								JOptionPane.showMessageDialog(null, "Account not updated !");
							}
							
						}else{
							JOptionPane.showMessageDialog(null, "Account " + account.getName() + " is not in the database !" );
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "You must select an account before you update !");	
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnAccountListUpdate.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAccountListUpdate.setBounds(552, 104, 128, 38);
		btnAccountListUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListMaintenance.add(btnAccountListUpdate);
		
		JButton btnAccountListDelete = new JButton("DELETE");
		btnAccountListDelete.setForeground(Color.BLACK);
		btnAccountListDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					// To cover if the txtAccountListId is empty
					int chosenid = txtAccountListID.getText().equals("")?0:Integer.parseInt(txtAccountListID.getText());
					
					Account account = getAccountDao().accountExists(chosenid);
					if(account.getId() != 0) {
					
						int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record","Delete",JOptionPane.YES_NO_OPTION);
						
						if(action == 0){
							
							int result = getAccountDao().accountDelete(account.getId());
							
							if(result == 1) {
								refreshAccountsTable();
								txtAccountListID.setText("");
								txtAccountListName.setText("");
								txtAccountListAddressLine1.setText("");
								txtAccountListAddressLine2.setText("");
								txtAccountListPhone.setText("");
								
								JOptionPane.showMessageDialog(null, "Account " + account.getName() + " has been deleted !");
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Account is not in the database!");
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnAccountListDelete.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAccountListDelete.setBounds(790, 104, 128, 38);
		btnAccountListDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountListMaintenance.add(btnAccountListDelete);

		refreshAccountsTable();
	}
	
	
	/**
	 * This Method retrieves the appropriate accounts from the database based o the user's selection criteria and loads the
	 * resulting list into the accounts table on screen so that the user can select from the up to date accounts 
	 */
	public void refreshAccountsTable() {
		String selection = comboBoxAccountListSrch.getSelectedItem().toString();
		String filter = "%" + txtAccountListSrch.getText() + "%";
		
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
			
			List<Account> accountList = getAccountDao().accountRefreshAccountsTable(selection, filter);
			
			String columns[] = {"ID", "NAME", "ADDRESS LINE 1", "ADDRESS LINE 2", "PHONE", "BALANCE"};
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
						
			for(Account account: accountList){
				String tableId= String.valueOf(account.getId());
				String tableName = account.getName();
				String tableAddress1 = account.getAddressLine1();
				String tableAddress2 = account.getAddressLine2();
				String tablePhone = account.getPhone();
				String tableBalance = Utilities.floatToString2Dec(account.getBalance());
				Object[] line = {tableId, tableName, tableAddress1, tableAddress2, tablePhone, tableBalance};
				tableModel.addRow(line);
			}
						
			tblAccountListList.setModel(tableModel);
			
			// Set the column widths
			tblAccountListList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel columnModel = tblAccountListList.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(25);
			columnModel.getColumn(1).setPreferredWidth(140);
			columnModel.getColumn(2).setPreferredWidth(260);
			columnModel.getColumn(3).setPreferredWidth(260);
			columnModel.getColumn(4).setPreferredWidth(135);
			columnModel.getColumn(5).setPreferredWidth(110);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}		
	}

}
