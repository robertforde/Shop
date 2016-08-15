package com.daniel;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import javax.swing.ListSelectionModel;

/**
 * This class designs and handles all of the operations on the Stock panel of the main tabbed pane
 * @author Robert Forde
 *
 */
public class StockScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable tblItemStock;
	
	private JTextField textField;

	
	/**
	 * Constructs the Stock Screen
	 * @param tabbedPane tabbedPane The main program tabbed pane so this panel can be added to it
	 */
	public StockScreen(JTabbedPane tabbedPane){
		
		// Create the logo images for the application
		Image logo = new ImageIcon(this.getClass().getResource("/Logo199X198.jpg")).getImage();
		ImageIcon imageIcon = new ImageIcon(logo);
						
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Stock", null, this, null);
		setLayout(null);

		JPanel panelItemStockList = new JPanel();
		panelItemStockList.setBackground(UIManager.getColor("List.dropLineColor"));
		panelItemStockList.setBounds(23, 27, 668, 522);
		this.add(panelItemStockList);
		panelItemStockList.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(26, 84, 616, 398);
		panelItemStockList.add(scrollPane_3);
		
		tblItemStock = new JTable();
		tblItemStock.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_3.setViewportView(tblItemStock);
		
		textField = new JTextField();
		textField.setBounds(26, 29, 242, 29);
		panelItemStockList.add(textField);
		textField.setColumns(10);
		
		JLabel lblLogo4 = new JLabel("New label");
		// Put Logo image on products tab
		lblLogo4.setIcon(imageIcon);

		lblLogo4.setBounds(756, 339, 199, 210);
		this.add(lblLogo4);
	}
	
}