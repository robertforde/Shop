package com.daniel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import com.daniel.dao.ReceiptSettingsDao;
import com.daniel.dao.ReceiptSettingsDaoImpl;
import com.daniel.model.InvoiceSettings;

/**
 * This class designs and handles all of the operations on the Settings panel of the main tabbed pane
 * @author Robert Forde
 *
 */
public class SettingsScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InvoiceSettings invoiceSettings;
	
	private JToggleButton btnPrintTopIcon;
	private JToggleButton btnPrintBodyImage;
	private JToggleButton btnPrintVerticalGrid;

	private JButton btnSaveChanges;
	private JButton btnCancelChanges;
	
	private JTextField txtTopIconXPosition;
	private JTextField txtTopIconYPosition;
	private JTextField txtBodyImageXPosition;
	private JTextField txtBodyImageYPosition;
	private JTextField txtFooterTextLine1;
	private JTextField txtFooterTextLine2;
	private JTextField txtFooterTextLine3;
	private JTextField txtVatRate;
	
	private Image yesImgPrintIcon;
	private Image noImgPrintIcon;

	private ReceiptSettingsDao receiptSettingsDao;


	public ReceiptSettingsDao getReceiptSettingsDao() {
		return receiptSettingsDao;
	}

	public void setReceiptSettingsDao(ReceiptSettingsDao receiptSettingsDao) {
		this.receiptSettingsDao = receiptSettingsDao;
	}

	public InvoiceSettings getInvoiceSettings() {
		return invoiceSettings;
	}

	public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
		this.invoiceSettings = invoiceSettings;
	}

	
	/**
	 * Constructs the Settings Panel
	 * @param tabbedPane tabbedPane The main program tabbed pane so this panel can be added to it
	 * @param invoiceSettings The current InvoiceSettings for Vat, Invoice Printing e.t.c
	 */
	public SettingsScreen(JTabbedPane tabbedPane, InvoiceSettings invoiceSettings){
		
		setReceiptSettingsDao(Dan.ctx.getBean("receiptSettingsDaoImpl", ReceiptSettingsDaoImpl.class));
		
		// Create the logo images for the application
		Image logo = new ImageIcon(this.getClass().getResource("/Logo199X198.jpg")).getImage();
		ImageIcon imageIcon = new ImageIcon(logo);
				
		setInvoiceSettings(invoiceSettings);
	
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Settings", null, this, null);
		setLayout(null);
		
		yesImgPrintIcon = new ImageIcon(this.getClass().getResource("/yesPrintImage.jpg")).getImage();
		noImgPrintIcon = new ImageIcon(this.getClass().getResource("/noPrintImage.jpg")).getImage();
				
		JPanel panelTopIcon = new JPanel();
		panelTopIcon.setBackground(Color.WHITE);
		panelTopIcon.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelTopIcon.setBounds(58, 57, 269, 174);
		panelTopIcon.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		this.add(panelTopIcon);
		panelTopIcon.setLayout(null);
		
		btnSaveChanges = new JButton("SAVE CHANGES");
		btnSaveChanges.setForeground(Color.BLACK);
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (saveReceiptPrintingChanges()){
					btnSaveChanges.setEnabled(false);
					btnCancelChanges.setEnabled(false);
				}
			}
		});
		btnSaveChanges.setEnabled(false);
		btnSaveChanges.setFont(new Font("Georgia", Font.BOLD, 16));
		btnSaveChanges.setBounds(333, 523, 167, 38);
		btnSaveChanges.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		this.add(btnSaveChanges);
		
		JLabel lblTopIcon = new JLabel("Top Icon");
		lblTopIcon.setForeground(Color.WHITE);
		lblTopIcon.setFont(new Font("Georgia", Font.BOLD, 18));
		lblTopIcon.setBounds(58, 27, 89, 19);
		this.add(lblTopIcon);
		
		txtTopIconXPosition = new JTextField();
		txtTopIconXPosition.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		txtTopIconXPosition.setBounds(167, 46, 86, 20);
		txtTopIconXPosition.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTopIcon.add(txtTopIconXPosition);
		txtTopIconXPosition.setColumns(10);
		
		JLabel lblTopIconXPosition = new JLabel("X Position");
		lblTopIconXPosition.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblTopIconXPosition.setBounds(167, 21, 74, 14);
		panelTopIcon.add(lblTopIconXPosition);
		
		JLabel lblTopIconYPosition = new JLabel("Y Position");
		lblTopIconYPosition.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblTopIconYPosition.setBounds(167, 104, 74, 14);
		panelTopIcon.add(lblTopIconYPosition);
		
		txtTopIconYPosition = new JTextField();
		txtTopIconYPosition.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		txtTopIconYPosition.setColumns(10);
		txtTopIconYPosition.setBounds(167, 129, 86, 20);
		txtTopIconYPosition.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTopIcon.add(txtTopIconYPosition);
		
		btnPrintTopIcon = new JToggleButton("Print");
		
		// Remove border and assure that we have no extra background
		btnPrintTopIcon.setBorderPainted(false);
		btnPrintTopIcon.setContentAreaFilled(false);
		btnPrintTopIcon.setFocusPainted(false);
		btnPrintTopIcon.setOpaque(false);
		
		btnPrintTopIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		if(btnPrintTopIcon.getText().compareTo("Print")==0){
			btnPrintTopIcon.setIcon(new ImageIcon(noImgPrintIcon));
			txtTopIconXPosition.setEnabled(false);
			txtTopIconYPosition.setEnabled(false);
			btnPrintTopIcon.setText("noPrint");
		} else {
			btnPrintTopIcon.setIcon(new ImageIcon(yesImgPrintIcon));
			txtTopIconXPosition.setEnabled(true);
			txtTopIconYPosition.setEnabled(true);
			btnPrintTopIcon.setText("Print");
		}
		btnSaveChanges.setEnabled(true);
		btnCancelChanges.setEnabled(true);
			}
		});
		btnPrintTopIcon.setBounds(21, 29, 120, 120);
		panelTopIcon.add(btnPrintTopIcon);
		
		JPanel panelBodyImage = new JPanel();
		panelBodyImage.setBackground(Color.WHITE);
		panelBodyImage.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelBodyImage.setBounds(530, 57, 269, 174);
		panelBodyImage.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		this.add(panelBodyImage);
		panelBodyImage.setLayout(null);
		
		JLabel lblBodyImageXPosition = new JLabel("X Position");
		lblBodyImageXPosition.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblBodyImageXPosition.setBounds(167, 21, 74, 14);
		panelBodyImage.add(lblBodyImageXPosition);
		
		JLabel lblBodyImageYPosition = new JLabel("Y Position");
		lblBodyImageYPosition.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblBodyImageYPosition.setBounds(167, 104, 74, 14);
		panelBodyImage.add(lblBodyImageYPosition);
		
		txtBodyImageXPosition = new JTextField();
		txtBodyImageXPosition.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		txtBodyImageXPosition.setColumns(10);
		txtBodyImageXPosition.setBounds(167, 46, 86, 20);
		txtBodyImageXPosition.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelBodyImage.add(txtBodyImageXPosition);
		
		txtBodyImageYPosition = new JTextField();
		txtBodyImageYPosition.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		txtBodyImageYPosition.setColumns(10);
		txtBodyImageYPosition.setBounds(167, 129, 86, 20);
		txtBodyImageYPosition.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelBodyImage.add(txtBodyImageYPosition);
		
		btnPrintBodyImage = new JToggleButton("Print");
		
		// Remove border and assure that we have no extra background
		btnPrintBodyImage.setBorderPainted(false);
		btnPrintBodyImage.setContentAreaFilled(false);
		btnPrintBodyImage.setFocusPainted(false);
		btnPrintBodyImage.setOpaque(false);
		
		btnPrintBodyImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnPrintBodyImage.getText().compareTo("Print")==0){
					btnPrintBodyImage.setIcon(new ImageIcon(noImgPrintIcon));
					txtBodyImageXPosition.setEnabled(false);
					txtBodyImageYPosition.setEnabled(false);
					btnPrintBodyImage.setText("noPrint");
				} else {
					btnPrintBodyImage.setIcon(new ImageIcon(yesImgPrintIcon));
					txtBodyImageXPosition.setEnabled(true);
					txtBodyImageYPosition.setEnabled(true);
					btnPrintBodyImage.setText("Print");
				}
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		
		btnPrintBodyImage.setBounds(21, 29, 120, 120);
		panelBodyImage.add(btnPrintBodyImage);
		
		JLabel lblVerticalGridLines = new JLabel("VerticalGridLines");
		lblVerticalGridLines.setForeground(Color.WHITE);
		lblVerticalGridLines.setFont(new Font("Georgia", Font.BOLD, 18));
		lblVerticalGridLines.setBounds(1029, 28, 166, 19);
		this.add(lblVerticalGridLines);
		
		JPanel panelFooter = new JPanel();
		panelFooter.setBackground(Color.WHITE);
		panelFooter.setBounds(58, 308, 624, 174);
		panelFooter.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		this.add(panelFooter);
		panelFooter.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelFooter.setLayout(null);
		
		txtFooterTextLine1 = new JTextField();
		txtFooterTextLine1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		txtFooterTextLine1.setBounds(84, 22, 493, 20);
		txtFooterTextLine1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelFooter.add(txtFooterTextLine1);
		txtFooterTextLine1.setColumns(10);
		
		JLabel lblFooter1 = new JLabel("Footer 1");
		lblFooter1.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblFooter1.setBounds(10, 20, 66, 20);
		panelFooter.add(lblFooter1);
		
		txtFooterTextLine2 = new JTextField();
		txtFooterTextLine2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		txtFooterTextLine2.setColumns(10);
		txtFooterTextLine2.setBounds(84, 55, 493, 20);
		txtFooterTextLine2.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelFooter.add(txtFooterTextLine2);
		
		JLabel lblFooter2 = new JLabel("Footer 2");
		lblFooter2.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblFooter2.setBounds(10, 53, 66, 20);
		panelFooter.add(lblFooter2);
		
		txtFooterTextLine3 = new JTextField();
		txtFooterTextLine3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		txtFooterTextLine3.setColumns(10);
		txtFooterTextLine3.setBounds(84, 88, 493, 20);
		txtFooterTextLine3.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelFooter.add(txtFooterTextLine3);
		
		JLabel lblFooter3 = new JLabel("Footer 3");
		lblFooter3.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblFooter3.setBounds(10, 86, 66, 20);
		panelFooter.add(lblFooter3);
		
		JLabel lblVatRate = new JLabel("Vat Rate");
		lblVatRate.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblVatRate.setBounds(10, 119, 66, 20);
		panelFooter.add(lblVatRate);
		
		txtVatRate = new JTextField();
		txtVatRate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		txtVatRate.setBounds(84, 121, 86, 20);
		txtVatRate.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelFooter.add(txtVatRate);
		txtVatRate.setColumns(10);
		
		JPanel panelVerticalGridLines = new JPanel();
		panelVerticalGridLines.setBackground(Color.WHITE);
		panelVerticalGridLines.setBounds(1029, 57, 199, 174);
		panelVerticalGridLines.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		this.add(panelVerticalGridLines);
		panelVerticalGridLines.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelVerticalGridLines.setLayout(null);
		
		btnPrintVerticalGrid = new JToggleButton("Print");
		
		// Remove border and assure that we have no extra background
		btnPrintVerticalGrid.setBorderPainted(false);
		btnPrintVerticalGrid.setContentAreaFilled(false);
		btnPrintVerticalGrid.setFocusPainted(false);
		btnPrintVerticalGrid.setOpaque(false);
		
		btnPrintVerticalGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnPrintVerticalGrid.getText().compareTo("Print")==0){
					btnPrintVerticalGrid.setIcon(new ImageIcon(noImgPrintIcon));
					btnPrintVerticalGrid.setText("noPrint");
				} else {
					btnPrintVerticalGrid.setIcon(new ImageIcon(yesImgPrintIcon));
					btnPrintVerticalGrid.setText("Print");
				}
				btnSaveChanges.setEnabled(true);
				btnCancelChanges.setEnabled(true);
			}
		});
		
		btnPrintVerticalGrid.setBounds(42, 26, 120, 120);
		panelVerticalGridLines.add(btnPrintVerticalGrid);
		
		JLabel lblBodyImage = new JLabel("Body Image");
		lblBodyImage.setForeground(Color.WHITE);
		lblBodyImage.setFont(new Font("Georgia", Font.BOLD, 18));
		lblBodyImage.setBounds(530, 27, 117, 19);
		this.add(lblBodyImage);
		
		JLabel lblFooterText = new JLabel("Footer Text");
		lblFooterText.setForeground(Color.WHITE);
		lblFooterText.setFont(new Font("Georgia", Font.BOLD, 18));
		lblFooterText.setBounds(58, 278, 117, 19);
		this.add(lblFooterText);
		
		btnCancelChanges = new JButton("CANCEL CHANGES");
		btnCancelChanges.setForeground(Color.BLACK);
		btnCancelChanges.setEnabled(false);
		btnCancelChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get invoice settings from the database and update the icons/fields on the settings tab
				try {
					
					// Retrieve the current InvoiceSettings from the table and update the current invoiceSettings object
					setInvoiceSettings(getReceiptSettingsDao().retrieveDatabasePrintSettings(getInvoiceSettings()));
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
				updatePrintSettingsTab();
				btnSaveChanges.setEnabled(false);
				btnCancelChanges.setEnabled(false);
			}
		});
		btnCancelChanges.setFont(new Font("Georgia", Font.BOLD, 16));
		btnCancelChanges.setBounds(634, 523, 210, 38);
		btnCancelChanges.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		this.add(btnCancelChanges);
		
		JLabel lblLogo3 = new JLabel();
		// Put Logo image on order tab by adding an image to the Logo Label
		lblLogo3.setIcon(imageIcon);
		lblLogo3.setBounds(1029, 351, 199, 210);
		this.add(lblLogo3);
		
		// Get invoice settings from the database and update the icons/fields on the settings tab
		try {
			
			// Retrieve the current InvoiceSettings from the table and update the current invoiceSettings object
			setInvoiceSettings(getReceiptSettingsDao().retrieveDatabasePrintSettings(getInvoiceSettings()));
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		updatePrintSettingsTab();
	}
	

	/**
	 * Method to save the screen settings to the database and to the invoiceSettings member variable
	 * @return Boolean to indicate if it was saved correctly
	 */
	public boolean saveReceiptPrintingChanges(){
		
		try {

			// Save the settings to our invoiceSettings object and make it update the database
			getInvoiceSettings().setReceiptTopBanner(btnPrintTopIcon.getText().compareTo("Print")==0?1:0);
			getInvoiceSettings().setReceiptTopBannerX(Integer.parseInt(txtTopIconXPosition.getText())); 
			getInvoiceSettings().setReceiptTopBannerY(Integer.parseInt(txtTopIconYPosition.getText())); 
			getInvoiceSettings().setReceiptVerticalGridLines(btnPrintVerticalGrid.getText().compareTo("Print")==0?1:0); 
			getInvoiceSettings().setReceiptBodyImage(btnPrintBodyImage.getText().compareTo("Print")==0?1:0);
			getInvoiceSettings().setReceiptBodyImageX(Integer.parseInt(txtBodyImageXPosition.getText())); 
			getInvoiceSettings().setReceiptBodyImageY(Integer.parseInt(txtBodyImageYPosition.getText())); 
			getInvoiceSettings().setReceiptVatRate(Float.parseFloat(txtVatRate.getText()));
			getInvoiceSettings().setReceiptFooterLine1(txtFooterTextLine1.getText()); 
			getInvoiceSettings().setReceiptFooterLine2(txtFooterTextLine2.getText()); 
			getInvoiceSettings().setReceiptFooterLine3(txtFooterTextLine3.getText()); 
			
			// Call a method to update the receiptsettings table
			getReceiptSettingsDao().savePrintSettingsToDatabase(getInvoiceSettings());
			//getInvoiceSettings().savePrintSettingsToDatabase();
			
			return true;
		
		}catch (NumberFormatException ex){
			JOptionPane.showMessageDialog(null, "X and Y Positions and Vat Rate must be numbers ");
			return false;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
			return false;
		}
	}
	
	
	/**
	 *  Method to set the screen up based on the settings in the invoiceSettings member variable
	 */
	public void updatePrintSettingsTab() {
		if(invoiceSettings.getReceiptTopBanner()==1){
			btnPrintTopIcon.setIcon(new ImageIcon(yesImgPrintIcon));
			btnPrintTopIcon.setText("Print");
		}else{
			btnPrintTopIcon.setIcon(new ImageIcon(noImgPrintIcon));
			btnPrintTopIcon.setText("NoPrint");
		}
		
		txtTopIconXPosition.setText(Integer.toString(invoiceSettings.getReceiptTopBannerX()));
		txtTopIconYPosition.setText(Integer.toString(invoiceSettings.getReceiptTopBannerY()));
		
		if(invoiceSettings.getReceiptBodyImage()==1){
			btnPrintBodyImage.setIcon(new ImageIcon(yesImgPrintIcon));
			btnPrintBodyImage.setText("Print");
		}else{
			btnPrintBodyImage.setIcon(new ImageIcon(noImgPrintIcon));
			btnPrintBodyImage.setText("NoPrint");
		}
		
		txtBodyImageXPosition.setText(Integer.toString(invoiceSettings.getReceiptBodyImageX()));
		txtBodyImageYPosition.setText(Integer.toString(invoiceSettings.getReceiptBodyImageY()));
		
		if(invoiceSettings.getReceiptVerticalGridLines()==1){
			btnPrintVerticalGrid.setIcon(new ImageIcon(yesImgPrintIcon));
			btnPrintVerticalGrid.setText("Print");
		}else{
			btnPrintVerticalGrid.setIcon(new ImageIcon(noImgPrintIcon));
			btnPrintVerticalGrid.setText("NoPrint");
		}

		txtFooterTextLine1.setText(invoiceSettings.getReceiptFooterLine1());
		txtFooterTextLine2.setText(invoiceSettings.getReceiptFooterLine2());
		txtFooterTextLine3.setText(invoiceSettings.getReceiptFooterLine3());
		txtVatRate.setText(Float.toString(invoiceSettings.getReceiptVatRate()));
	}

}