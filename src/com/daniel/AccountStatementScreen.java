package com.daniel;

import javax.swing.JFrame;

import com.daniel.dao.AccountDao;
import com.daniel.dao.AccountDaoImpl;
import com.daniel.model.Account;
import com.daniel.model.AccountDetail;
import com.daniel.model.Transaction;
import com.daniel.utilities.Utilities;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import com.toedter.calendar.JDateChooser;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class opens a JFrame so the user can create and print statements for particular dates on a particluar account  
 * @author Robert Forde
 *
 */
public class AccountStatementScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Account acc;
	private JTable tblAccountStatement;
	
	private JDateChooser txtAccountStatementFrom;
	private JDateChooser txtAccountStatementTo;
	
	private TableColumnModel columnModelOrder;

	private AccountDao accountDao;

	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public Account getAcc() {
		return acc;
	}

	public void setAcc(Account acc) {
		this.acc = acc;
	}

	public JDateChooser getDateAccountStatementFrom() {
		return txtAccountStatementFrom;
	}

	public void setDateAccountStatementFrom(JDateChooser dateAccountStatementFrom) {
		this.txtAccountStatementFrom = dateAccountStatementFrom;
	}

	public JDateChooser getDateAccountStatementTo() {
		return txtAccountStatementTo;
	}

	public void setDateAccountStatementTo(JDateChooser dateAccountStatementTo) {
		this.txtAccountStatementTo = dateAccountStatementTo;
	}

	
	/**
	 * 
	 * @param acc The account that the statement is to be created for
	 */
	public AccountStatementScreen(Account acc){

		setAccountDao(Dan.ctx.getBean("accountDaoImpl", AccountDaoImpl.class));
		
		getContentPane().setBackground(Color.DARK_GRAY);
		
		setAcc(acc);
		
		setBounds(220, 80, 950, 570);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setTitle("ACCOUNT STATEMENT");
		getContentPane().setLayout(null);
		
		JLabel lblAccountStatementName = new JLabel("New label");
		lblAccountStatementName.setForeground(Color.WHITE);
		lblAccountStatementName.setFont(new Font("Monotype Corsiva", Font.BOLD, 44));
		lblAccountStatementName.setBounds(28, 29, 430, 50);
		getContentPane().add(lblAccountStatementName);
		
		JPanel panelAccountStatementList = new JPanel();
		panelAccountStatementList.setBackground(UIManager.getColor("List.dropLineColor"));
		panelAccountStatementList.setBounds(121, 122, 687, 389);
		panelAccountStatementList.setBorder(BorderFactory.createRaisedBevelBorder());
		getContentPane().add(panelAccountStatementList);
		panelAccountStatementList.setLayout(null);
		
		JScrollPane scrlAccountStatement = new JScrollPane();
		scrlAccountStatement.setBounds(89, 11, 509, 367);
		scrlAccountStatement.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelAccountStatementList.add(scrlAccountStatement);
		
		tblAccountStatement = new JTable(){
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
				if ( col == dataModel.getColumnCount() - 1 || col == dataModel.getColumnCount() - 2) 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
				
				// Left justify the other columns
				else 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
				
				return renderer; 
			}
			
		};
		tblAccountStatement.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tblAccountStatement.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		scrlAccountStatement.setViewportView(tblAccountStatement);
		
		lblAccountStatementName.setText(getAcc().getName());
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("List.dropLineColor"));
		panel.setBounds(481, 11, 443, 87);
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		getContentPane().add(panel);
		panel.setLayout(null);
		
		txtAccountStatementFrom = new JDateChooser();
		txtAccountStatementFrom.setBounds(20, 46, 108, 28);
		txtAccountStatementFrom.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panel.add(txtAccountStatementFrom);
		txtAccountStatementFrom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				displayStatement();
			}
		});
		txtAccountStatementFrom.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				displayStatement();
			}
		});
		txtAccountStatementFrom.setDateFormatString("dd/MM/yyyy");
		
		txtAccountStatementTo = new JDateChooser();
		txtAccountStatementTo.setBounds(160, 46, 108, 28);
		txtAccountStatementTo.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panel.add(txtAccountStatementTo);
		txtAccountStatementTo.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				displayStatement();
			}
		});
		txtAccountStatementTo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				displayStatement();
			}
		});
		txtAccountStatementTo.setDateFormatString("dd/MM/yyyy");
		
		JLabel lblAccountStatementFromDate = new JLabel("From Date");
		lblAccountStatementFromDate.setBounds(20, 19, 87, 20);
		panel.add(lblAccountStatementFromDate);
		lblAccountStatementFromDate.setForeground(Color.BLACK);
		lblAccountStatementFromDate.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblAccountStatementToDate = new JLabel("To Date");
		lblAccountStatementToDate.setBounds(160, 19, 87, 20);
		panel.add(lblAccountStatementToDate);
		lblAccountStatementToDate.setForeground(Color.BLACK);
		lblAccountStatementToDate.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JButton btnAccountStatementPrint = new JButton("PRINT");
		btnAccountStatementPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAccountStatementPrint.setBounds(298, 36, 122, 38);
		btnAccountStatementPrint.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panel.add(btnAccountStatementPrint);
		btnAccountStatementPrint.setFont(new Font("Georgia", Font.BOLD, 16));
		
		displayStatement();
	}
	
	
	/**
	 * Method that retrieves the list of transactions for the account with (as of that time balances) and loads them into the statment table on the screen
	 */
	public void displayStatement(){
		
		Date chosenFromDate;
		Date chosenToDate;
		Date fromDate = null;
		Date toDate = null;
		
		String columns[] = {"NUMBER", "DATE", "TYPE", "AMOUNT", "BALANCE"};
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		
		chosenFromDate = txtAccountStatementFrom.getDateEditor().getDate();
		chosenToDate = txtAccountStatementTo.getDateEditor().getDate();

		if(fromDate == null) {
			// Get the dates selected, if dates are empty assign dates to them
			if(chosenFromDate == null || chosenFromDate.equals(""))
				fromDate = new Date(0);
			else
				fromDate = getDateAccountStatementFrom().getDate();
			
			if(chosenToDate == null || chosenToDate.equals(""))
				toDate = new Date();
			else
				toDate = getDateAccountStatementTo().getDate();
			
			// Get from date as of 00:00:00 on the particular day so any transactions on that day will be included 
			Calendar c = Calendar.getInstance(); 
			c.setTime(fromDate);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			fromDate = c.getTime();
			
			// Get to date as of 24:59:59 on the particular day so any transactions on that day will be included 
			c = Calendar.getInstance(); 
			c.setTime(toDate);
			c.set(Calendar.HOUR_OF_DAY, 24);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			toDate = c.getTime();
		}
		
		try {
			
			float balance = 0.00f;
			String stringBalance;
			String transDate;
			ArrayList<Transaction> transactions = new ArrayList<Transaction>();
			DateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
			
			// Retrieve all of the accounts transactions
			List<AccountDetail> transList = getAccountDao().accountStatementGetTrans(getAcc().getId());
			
			// Loop through the account's transactions and calculate the balance as of that transaction
			for(AccountDetail transDetail : transList){
				
				// Calculate the balance as of after this transaction
				if(transDetail.getType().equals("ORDER")) {
					balance += transDetail.getAmount();
					stringBalance = Utilities.floatToString2Dec(balance);
				
				}else{
					balance -= transDetail.getAmount();
					stringBalance = Utilities.floatToString2Dec(balance);
				}
				
				transDate = fmt.format(transDetail.getTheDate());
				Date dayStart = new Date(transDetail.getTheDate().getTime());
				
				Transaction transaction = new Transaction(transDetail.getNumber(), transDate, dayStart, transDetail.getType(), 
						Utilities.floatToString2Dec(transDetail.getAmount()), stringBalance);
				
				transactions.add(transaction);
			}

			for(Transaction transaction : transactions){
	
				// if this transaction is to be included in the statement Load it into an Object array and add it to the tablemodel object
				if( fromDate.compareTo(transaction.getUtilDate()) <= 0 && toDate.compareTo(transaction.getUtilDate()) >= 0 ) {
					Object[] line = {transaction.getNumber(), transaction.getDate(), transaction.getType(), transaction.getAmount(), transaction.getBalance()};
		
					tableModel.addRow(line);							
				}
			}
	
		
			tblAccountStatement.setModel(tableModel);
					
			// Set the column widths for the table
			tblAccountStatement.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			columnModelOrder = tblAccountStatement.getColumnModel();
			columnModelOrder.getColumn(0).setPreferredWidth(80);
			columnModelOrder.getColumn(1).setPreferredWidth(101);
			columnModelOrder.getColumn(2).setPreferredWidth(120);
			columnModelOrder.getColumn(3).setPreferredWidth(90);
			columnModelOrder.getColumn(4).setPreferredWidth(100);
					
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}

	}
}