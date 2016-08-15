package com.daniel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import com.daniel.dao.SuspendedDao;
import com.daniel.dao.SuspendedDaoImpl;
import com.daniel.model.SuspendedOrder;
import com.daniel.model.SuspendedPayment;
import com.daniel.utilities.Utilities;

/**
 * This class designs the suspended payments JFrame which handles the entry of payments on suspended orders
 * @author Robert Forde
 *
 */
public class SuspendedPaymentScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SuspendedOrder order;
	
	private JTextField txtSuspendPaymentAmount;
	private JTextField txtSuspendPaymentComment;

	private SuspendedOrdersScreen suspendedOrdersScreen;
	private SuspendedProcessScreen suspendedProcessScreen;
		
	private JRadioButton rdbtnSuspendPaymentCash;
	private JRadioButton rdbtnSuspendPaymentCreditCard;
	private JRadioButton rdbtnSuspendPaymentCheque;
	private JRadioButton rdbtnSuspendPaymentBankTransfer;
		
	private SuspendedDao suspendedDao;
		
		
	public SuspendedDao getSuspendedDao() {
		return suspendedDao;
	}

	public void setSuspendedDao(SuspendedDao suspendedDao) {
		this.suspendedDao = suspendedDao;
	}
	 
	public SuspendedOrder getOrder() {
		return order;
	}
		 
	public void setOrder(SuspendedOrder order) {
		this.order = order;
	}
	
	public void setSuspendedOrder(SuspendedOrder order) {
		this.order = order;
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

	public void setSuspendedProcessScreen(SuspendedProcessScreen suspendedProcessScreen) { 
		this.suspendedProcessScreen = suspendedProcessScreen;
	}


	/**
	 * Constructs the Suspended Payments Screen
	 * @param order The Suspended Order that the payments are to go against
	 * @param suspendedOrdersScreen A reference to the Suspended Orders Screen so the balance can be updated
	 * @param suspendedProcessScreen A reference to the Suspended Process Screen so the balance can be updated
	 */
	public SuspendedPaymentScreen(SuspendedOrder order, SuspendedOrdersScreen suspendedOrdersScreen, SuspendedProcessScreen suspendedProcessScreen){
			
		setSuspendedDao(Dan.ctx.getBean("suspendedDaoImpl", SuspendedDaoImpl.class));
			
		getContentPane().setBackground(Color.DARK_GRAY);

		setSuspendedOrder(order);
			
		setSuspendedOrdersScreen(suspendedOrdersScreen);
		setSuspendedProcessScreen(suspendedProcessScreen);
			
		setBounds(410, 250, 500, 302);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);
		setTitle("PAYMENT");
			
		JPanel panelSuspendPaymentType = new JPanel();
		panelSuspendPaymentType.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelSuspendPaymentType.setBackground(UIManager.getColor("List.dropLineColor"));
		panelSuspendPaymentType.setBounds(38, 21, 163, 118);
		getContentPane().add(panelSuspendPaymentType);
		panelSuspendPaymentType.setLayout(null);
			
		rdbtnSuspendPaymentCash = new JRadioButton("Cash");
		rdbtnSuspendPaymentCash.setSelected(true);
		rdbtnSuspendPaymentCash.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnSuspendPaymentCash.setForeground(Color.BLACK);
		rdbtnSuspendPaymentCash.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnSuspendPaymentCash.setBounds(25, 7, 109, 23);
		panelSuspendPaymentType.add(rdbtnSuspendPaymentCash);
			
		rdbtnSuspendPaymentCreditCard = new JRadioButton("Credit Card");
		rdbtnSuspendPaymentCreditCard.setForeground(Color.BLACK);
		rdbtnSuspendPaymentCreditCard.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnSuspendPaymentCreditCard.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnSuspendPaymentCreditCard.setBounds(25, 33, 132, 23);
		panelSuspendPaymentType.add(rdbtnSuspendPaymentCreditCard);
			
		rdbtnSuspendPaymentCheque= new JRadioButton("Cheque");
		rdbtnSuspendPaymentCheque.setForeground(Color.BLACK);
		rdbtnSuspendPaymentCheque.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnSuspendPaymentCheque.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnSuspendPaymentCheque.setBounds(25, 59, 109, 23);
		panelSuspendPaymentType.add(rdbtnSuspendPaymentCheque);
			
		rdbtnSuspendPaymentBankTransfer = new JRadioButton("Transfer");
		rdbtnSuspendPaymentBankTransfer.setForeground(Color.BLACK);
		rdbtnSuspendPaymentBankTransfer.setBackground(UIManager.getColor("List.dropLineColor"));
		rdbtnSuspendPaymentBankTransfer.setFont(new Font("Georgia", Font.BOLD, 16));
		rdbtnSuspendPaymentBankTransfer.setBounds(25, 85, 109, 23);
		panelSuspendPaymentType.add(rdbtnSuspendPaymentBankTransfer);
			
		JLabel lblSuspendPaymentAmount = new JLabel("Amount");
		lblSuspendPaymentAmount.setForeground(Color.BLACK);
		lblSuspendPaymentAmount.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendPaymentAmount.setBounds(241, 21, 97, 29);
		getContentPane().add(lblSuspendPaymentAmount);
			
		txtSuspendPaymentAmount = new JTextField();
		txtSuspendPaymentAmount.setForeground(Color.BLACK);
		txtSuspendPaymentAmount.setFont(new Font("Georgia", Font.BOLD, 16));
		txtSuspendPaymentAmount.setBounds(241, 50, 100, 28);
		txtSuspendPaymentAmount.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(txtSuspendPaymentAmount);
		txtSuspendPaymentAmount.setColumns(10);
			
		txtSuspendPaymentComment = new JTextField();
		txtSuspendPaymentComment.setForeground(Color.BLACK);
		txtSuspendPaymentComment.setBounds(241, 136, 200, 28);
		txtSuspendPaymentComment.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		getContentPane().add(txtSuspendPaymentComment);
		txtSuspendPaymentComment.setColumns(10);
			
		JButton btnSuspendPaymentSave = new JButton("SAVE");
		btnSuspendPaymentSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				float balance = 0.00f;
					
				if(!txtSuspendPaymentAmount.getText().isEmpty()){
					try {
							
						if(Float.parseFloat(txtSuspendPaymentAmount.getText()) != 0.0){
								
							// Get the payType
							String payType = "";
							if(rdbtnSuspendPaymentCash.isSelected())
								payType = "Cash";
							else if(rdbtnSuspendPaymentCreditCard.isSelected())
								payType = "Credit Card";
							else if(rdbtnSuspendPaymentCheque.isSelected())
								payType = "Cheque";
							else if(rdbtnSuspendPaymentBankTransfer.isSelected())
								payType = "Bank Transfer";
							
							// Create a Payment Object
							SuspendedPayment payment = new SuspendedPayment();
							payment.setSuspendOrder(getOrder().getReceiptNo());
							payment.setPayDate(new Date());
							payment.setPayType(payType);
							payment.setAmount(Utilities.floatToNumDec(Float.parseFloat(txtSuspendPaymentAmount.getText()),2));
							payment.setComment(txtSuspendPaymentComment.getText());
								
							// Save the payment to the acountpayment table, update the balance and return the current balance
							balance = getSuspendedDao().processPayment(payment);
								
							// Update the acc object
							getOrder().setBalance(balance);
							
								
						}else{
							JOptionPane.showMessageDialog(null, "You cannot enter a payment of 0.00 !");	
						}
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);//"You must enter a number for the amount !");
					}
				}

				// Refresh the accounts on the previous screens to reflect the new balance
				getSuspendedOrdersScreen().refreshSuspendedOrderHeaders();
				getSuspendedProcessScreen().getLblSuspendProcessBalance().setText(Utilities.floatToString2Dec(balance));
				SuspendedPaymentScreen.this.dispose();
			}
		});
		btnSuspendPaymentSave.setForeground(Color.BLACK);
		btnSuspendPaymentSave.setFont(new Font("Georgia", Font.BOLD, 16));
		btnSuspendPaymentSave.setBounds(65, 198, 120, 38);
		btnSuspendPaymentSave.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(btnSuspendPaymentSave);
			
		JLabel lblSuspendPaymentComment = new JLabel("Comment");
		lblSuspendPaymentComment.setForeground(Color.BLACK);
		lblSuspendPaymentComment.setFont(new Font("Georgia", Font.BOLD, 16));
		lblSuspendPaymentComment.setBounds(241, 107, 97, 29);
		getContentPane().add(lblSuspendPaymentComment);
			
		JButton btnSuspendPaymentCancel = new JButton("CANCEL");
		btnSuspendPaymentCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SuspendedPaymentScreen.this.dispose();
			}
		});
		btnSuspendPaymentCancel.setForeground(Color.BLACK);
		btnSuspendPaymentCancel.setFont(new Font("Georgia", Font.BOLD, 16));
		btnSuspendPaymentCancel.setBounds(281, 198, 120, 38);
		btnSuspendPaymentCancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		getContentPane().add(btnSuspendPaymentCancel);
			
		// Group the radio buttons.
		ButtonGroup suspendPaymentPayTypegroup = new ButtonGroup();
		suspendPaymentPayTypegroup.add(rdbtnSuspendPaymentCash);
		suspendPaymentPayTypegroup.add(rdbtnSuspendPaymentCreditCard);
		suspendPaymentPayTypegroup.add(rdbtnSuspendPaymentCheque);
		suspendPaymentPayTypegroup.add(rdbtnSuspendPaymentBankTransfer);
	}
	
}