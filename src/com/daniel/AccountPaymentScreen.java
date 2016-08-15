package com.daniel;

import java.awt.Color;

import javax.swing.JFrame;

import com.daniel.dao.AccountDao;
import com.daniel.dao.AccountDaoImpl;
import com.daniel.model.Account;
import com.daniel.model.Payment;
import com.daniel.utilities.Utilities;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JRadioButton;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * This class opens a Jframe that allows the user to enter a payment made against a particular account
 * @author Robert Forde
 *
 */
public class AccountPaymentScreen extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Account acc;
	private JTextField txtPaymentAmount;
	private JTextField txtPaymentComment;
	private AccountDetailScreen accountDetailScreen;
	private AccountListScreen accountListScreen;
	
	private JRadioButton rdbtnPaymentCash;
	private JRadioButton rdbtnPaymentCreditCard;
	private JRadioButton rdbtnPaymentCheque;
	private JRadioButton rdbtnPaymentBankTransfer;
	
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

	public AccountDetailScreen getAccountDetailScreen() {
		return accountDetailScreen;
	}

	public void setAccountDetailScreen(AccountDetailScreen accountDetailScreen) {
		this.accountDetailScreen = accountDetailScreen;
	}

	
	public AccountListScreen getAccountListScreen() {
		return accountListScreen;
	}

	public void setAccountListScreen(AccountListScreen accountListScreen) {
		this.accountListScreen = accountListScreen;
	}

	
	/**
	 * 
	 * @param acc The account that the payment is to be paid against 
	 * @param accountDetailScreen A reference to the accountDetailScreen so as to update the details on that screen
	 * @param accountListScreen A reference to the accountListScreen so as to update the account's balance on that screen
	 */
	public AccountPaymentScreen(Account acc, AccountDetailScreen accountDetailScreen, AccountListScreen accountListScreen){
		
		setAccountDao(Dan.ctx.getBean("accountDaoImpl", AccountDaoImpl.class));
		
		getContentPane().setBackground(Color.DARK_GRAY);

		setAcc(acc);
		setAccountDetailScreen(accountDetailScreen);
		setAccountListScreen(accountListScreen);
		
		setBounds(410, 250, 500, 302);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);
		setTitle("PAYMENT");
		
		JPanel panelPaymentType = new JPanel();
		panelPaymentType.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelPaymentType.setBackground(UIManager.getColor("List.dropLineColor"));
		panelPaymentType.setBounds(38, 21, 163, 118);
		getContentPane().add(panelPaymentType);
		panelPaymentType.setLayout(null);
		
		rdbtnPaymentCash = new JRadioButton("Cash");
		rdbtnPaymentCash.setSelected(true);
		rdbtnPaymentCash.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnPaymentCash.setForeground(Color.BLACK);
		rdbtnPaymentCash.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnPaymentCash.setBounds(25, 7, 109, 23);
		panelPaymentType.add(rdbtnPaymentCash);
		
		rdbtnPaymentCreditCard = new JRadioButton("Credit Card");
		rdbtnPaymentCreditCard.setForeground(Color.BLACK);
		rdbtnPaymentCreditCard.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnPaymentCreditCard.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnPaymentCreditCard.setBounds(25, 33, 132, 23);
		panelPaymentType.add(rdbtnPaymentCreditCard);
		
		rdbtnPaymentCheque= new JRadioButton("Cheque");
		rdbtnPaymentCheque.setForeground(Color.BLACK);
		rdbtnPaymentCheque.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnPaymentCheque.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnPaymentCheque.setBounds(25, 59, 109, 23);
		panelPaymentType.add(rdbtnPaymentCheque);
		
		rdbtnPaymentBankTransfer = new JRadioButton("Transfer");
		rdbtnPaymentBankTransfer.setForeground(Color.BLACK);
		rdbtnPaymentBankTransfer.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnPaymentBankTransfer.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnPaymentBankTransfer.setBounds(25, 85, 109, 23);
		panelPaymentType.add(rdbtnPaymentBankTransfer);
		
		JLabel lblPaymentAmount = new JLabel("Amount");
		lblPaymentAmount.setForeground(Color.BLACK);
		lblPaymentAmount.setFont(new Font("Georgia", Font.BOLD, 16));
		lblPaymentAmount.setBounds(241, 21, 97, 29);
		getContentPane().add(lblPaymentAmount);
		
		txtPaymentAmount = new JTextField();
		txtPaymentAmount.setForeground(Color.BLACK);
		txtPaymentAmount.setFont(new Font("Georgia", Font.BOLD, 16));
		txtPaymentAmount.setBounds(241, 50, 100, 28);
		txtPaymentAmount.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(txtPaymentAmount);
		txtPaymentAmount.setColumns(10);
		
		txtPaymentComment = new JTextField();
		txtPaymentComment.setForeground(Color.BLACK);
		txtPaymentComment.setBounds(241, 136, 200, 28);
		txtPaymentComment.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(txtPaymentComment);
		txtPaymentComment.setColumns(10);
		
		JButton btnPaymentSave = new JButton("SAVE");
		btnPaymentSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				float balance = 0.00f;
				
				if(!txtPaymentAmount.getText().isEmpty()){
					try {
						
						if(Float.parseFloat(txtPaymentAmount.getText()) != 0.0){
							
							// Get the payType
							String payType = "";
							if(rdbtnPaymentCash.isSelected())
								payType = "Cash";
							else if(rdbtnPaymentCreditCard.isSelected())
								payType = "Credit Card";
							else if(rdbtnPaymentCheque.isSelected())
								payType = "Cheque";
							else if(rdbtnPaymentBankTransfer.isSelected())
								payType = "Bank Transfer";
							
							// Create a Payment Object
							Payment payment = new Payment();
							payment.setAccountId(getAcc().getId());
							payment.setPayDate(new Date());
							payment.setAmount(Utilities.floatToNumDec(Float.parseFloat(txtPaymentAmount.getText()),2));
							payment.setComment(txtPaymentComment.getText());
							payment.setPayType(payType);
							
							// Save the payment to the acountpayment table, update the balance and return the current balance
							balance = getAccountDao().processPayment(payment);
							
							// Update the acc object
							getAcc().setBalance(balance);
							
							
						}else{
							JOptionPane.showMessageDialog(null, "You cannot enter a payment of 0.00 !");	
						}
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);//"You must enter a number for the amount !");
					}
				}

				// Refresh the accounts on the previous screens to reflect the new balance
				getAccountListScreen().refreshAccountsTable();
				getAccountDetailScreen().refreshAccountDetailsTable();
				getAccountDetailScreen().getLblAccountDetailBalance().setText(Utilities.floatToString2Dec(getAcc().getBalance()));
				AccountPaymentScreen.this.dispose();
			}
		});
		btnPaymentSave.setForeground(Color.BLACK);
		btnPaymentSave.setFont(new Font("Georgia", Font.BOLD, 16));
		btnPaymentSave.setBounds(65, 198, 120, 38);
		btnPaymentSave.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(btnPaymentSave);
		
		JLabel lblPaymentComment = new JLabel("Comment");
		lblPaymentComment.setForeground(Color.BLACK);
		lblPaymentComment.setFont(new Font("Georgia", Font.BOLD, 16));
		lblPaymentComment.setBounds(241, 107, 97, 29);
		getContentPane().add(lblPaymentComment);
		
		JButton btnPaymentCancel = new JButton("CANCEL");
		btnPaymentCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AccountPaymentScreen.this.dispose();
			}
		});
		btnPaymentCancel.setForeground(Color.BLACK);
		btnPaymentCancel.setFont(new Font("Georgia", Font.BOLD, 16));
		btnPaymentCancel.setBounds(281, 198, 120, 38);
		btnPaymentCancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(btnPaymentCancel);
		
		// Group the radio buttons.
		ButtonGroup paymentPayTypegroup = new ButtonGroup();
		paymentPayTypegroup.add(rdbtnPaymentCash);
		paymentPayTypegroup.add(rdbtnPaymentCreditCard);
		paymentPayTypegroup.add(rdbtnPaymentCheque);
		paymentPayTypegroup.add(rdbtnPaymentBankTransfer);

	}
}
