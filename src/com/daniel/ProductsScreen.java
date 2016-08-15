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

import com.daniel.dao.ProductDao;
import com.daniel.dao.ProductDaoImpl;
import com.daniel.model.Item;
import com.daniel.utilities.Utilities;

import javax.swing.ListSelectionModel;


/**
 * This class handles the Products Tab on the main tabbed panel. It retrieves and displays all of the products from the database.
 * The screen has filters to allow the user to filter by code or description. It also gives the user the ability to perform CRUD
 * operations on products in the database
 * @author Robert Forde
 *
 */
public class ProductsScreen extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable tblProductsItem;
	
	private JComboBox<String> comboBoxProductsSrch;
	
	private JTextField txtProductsSrch;
	private JTextField txtProductsCode;
	private JTextField txtProductsDescription;
	private JTextField txtProductsRetail;
	private JTextField txtProductsTrade;
	private JTextField txtProductsCost;

	private ProductDao dao;

	
	public ProductDao getDao() {
		return dao;
	}

	public void setDao(ProductDao dao) {
		this.dao = dao;
	}

	
	/**
	 * Constructs the Products Screen
	 * @param tabbedPane The main program tabbed pane so this panel can be added to it
	 */
	public ProductsScreen(JTabbedPane tabbedPane){

		setDao(Dan.ctx.getBean("productDaoImpl", ProductDaoImpl.class));
		
		// Create the logo images for the application
		Image logo = new ImageIcon(this.getClass().getResource("/Logo199X198.jpg")).getImage();
		ImageIcon imageIcon = new ImageIcon(logo);
		
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Products", null, this, null);
		setLayout(null);

		JPanel panelItemMaintenance = new JPanel();
		panelItemMaintenance.setBounds(64, 413, 1166, 161);
		panelItemMaintenance.setBackground(UIManager.getColor("List.dropLineColor"));
		panelItemMaintenance.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(227, 227, 227), new Color(227, 227, 227), UIManager.getColor("Button.foreground"), new Color(105, 105, 105)));
		this.add(panelItemMaintenance);
		panelItemMaintenance.setLayout(null);
		
		txtProductsCode = new JTextField();
		txtProductsCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				// Search for item in database and if found fill in description and price
				try {
					
					// Call a method that will try to find the item whose code we have entered, if found it will set its members as per the item table
					Item item = getDao().findItemByCode(txtProductsCode.getText());
					
					// If item found then fill it's details, else leave it as is
					txtProductsDescription.setText(item.getItemCode() == null ? txtProductsDescription.getText() : item.getItemDescription());
					txtProductsRetail.setText(item.getItemCode() == null ? "" : Utilities.floatToString2Dec(item.getRetailPrice()));
					txtProductsCost.setText(item.getItemCode() == null ? "" : Utilities.floatToString2Dec(item.getCostPrice()));
					txtProductsTrade.setText(item.getItemCode() == null ? "" : Utilities.floatToString2Dec(item.getTradePrice()));
									
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		txtProductsCode.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtProductsCode.setBounds(56, 49, 132, 32);
		txtProductsCode.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelItemMaintenance.add(txtProductsCode);
		txtProductsCode.setColumns(10);
		
		txtProductsDescription = new JTextField();
		txtProductsDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				txtProductsDescription.setText(txtProductsDescription.getText().toUpperCase());
				
				// Search for item in database and if found fill in description and price
				try {
					
					// Call a method that will try to find the item whose code we have entered, if found it will set its members as per the item table
					Item item = getDao().findItemByDesc(txtProductsDescription.getText());
					txtProductsCode.setText(item.getItemDescription() == null ? txtProductsCode.getText() : item.getItemCode());
					txtProductsRetail.setText(item.getItemDescription() == null ? "" : Utilities.floatToString2Dec(item.getRetailPrice()));
					txtProductsCost.setText(item.getItemDescription() == null ? "" : Utilities.floatToString2Dec(item.getCostPrice()));
					txtProductsTrade.setText(item.getItemDescription() == null ? "" : Utilities.floatToString2Dec(item.getTradePrice()));
									
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}

			}
		});
		txtProductsDescription.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtProductsDescription.setBounds(253, 49, 302, 32);
		
		panelItemMaintenance.add(txtProductsDescription);
		txtProductsDescription.setColumns(10);
		txtProductsDescription.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		txtProductsRetail = new JTextField();
		txtProductsRetail.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtProductsRetail.setBounds(972, 49, 132, 32);
		txtProductsRetail.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelItemMaintenance.add(txtProductsRetail);
		txtProductsRetail.setColumns(10);
		
		JLabel lblItemCode = new JLabel("Item Code");
		lblItemCode.setFont(new Font("Georgia", Font.BOLD, 16));
		lblItemCode.setBounds(56, 24, 132, 14);
		panelItemMaintenance.add(lblItemCode);
		
		JLabel lblItemDescription = new JLabel("Item Description");
		lblItemDescription.setFont(new Font("Georgia", Font.BOLD, 16));
		lblItemDescription.setBounds(253, 24, 175, 14);
		panelItemMaintenance.add(lblItemDescription);
		
		JLabel lblRetailPrice = new JLabel("Retail Price");
		lblRetailPrice.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRetailPrice.setBounds(972, 24, 115, 14);
		panelItemMaintenance.add(lblRetailPrice);
		
		JButton btnProductInsert = new JButton("INSERT");
		btnProductInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					// First check that something was entered in the Item fields
					if(!txtProductsCode.getText().isEmpty() && !txtProductsDescription.getText().isEmpty() && !txtProductsCost.getText().isEmpty() && 
							!txtProductsTrade.getText().isEmpty() && !txtProductsRetail.getText().isEmpty()) {
						
						try{
						
							// Call a method to see if this item already exists
							Item item = getDao().findItemByCode(txtProductsCode.getText());
							if(item.getItemCode() == null){
								
								item = new Item(txtProductsCode.getText(), txtProductsDescription.getText(), Float.parseFloat(txtProductsRetail.getText()), 
										Float.parseFloat(txtProductsTrade.getText()), Float.parseFloat(txtProductsCost.getText()));
								
								getDao().insertItem(item);
								txtProductsSrch.setText("");
								refreshProductsTable();
								String msg = "Item " + txtProductsCode.getText() + " has been inserted !"; 
								txtProductsCode.setText("");
								txtProductsDescription.setText("");
								txtProductsCost.setText("");
								txtProductsTrade.setText("");
								txtProductsRetail.setText("");
								JOptionPane.showMessageDialog(null, msg);
								
							}else {
								JOptionPane.showMessageDialog(null, "Item " + txtProductsCode.getText() + " is already in the Database !");
							}
 
						}catch(Exception ex) {
							JOptionPane.showMessageDialog(null, "Not a Valid Price !" + ex);
						}
					} else {
						JOptionPane.showMessageDialog(null, "You must enter an Item Code, Description, Cost Price, Trade Price and a Retail Price !");
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnProductInsert.setFont(new Font("Georgia", Font.BOLD, 16));
		btnProductInsert.setBounds(358, 102, 115, 38);
		btnProductInsert.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelItemMaintenance.add(btnProductInsert);
		
		JButton btnProductUpdate = new JButton("UPDATE");
		btnProductUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {

					// See it this item exists before we try and update it
					Item item = getDao().findItemByCode(txtProductsCode.getText());
					
					if(item.getItemCode() != null) {

						item = new Item(txtProductsCode.getText(), txtProductsDescription.getText(), Float.parseFloat(txtProductsRetail.getText()), 
								Float.parseFloat(txtProductsTrade.getText()), Float.parseFloat(txtProductsCost.getText()));

						getDao().updateItem(item);

						refreshProductsTable();
						String msg = "Item " + txtProductsCode.getText() + " has been updated !"; 
						txtProductsCode.setText("");
						txtProductsDescription.setText("");
						txtProductsCost.setText("");
						txtProductsTrade.setText("");
						txtProductsRetail.setText("");
						JOptionPane.showMessageDialog(null, msg);
					}else {
						JOptionPane.showMessageDialog(null, "Item " + txtProductsCode.getText() + " is not in the Database !");
					}
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnProductUpdate.setFont(new Font("Georgia", Font.BOLD, 16));
		btnProductUpdate.setBounds(526, 102, 115, 38);
		btnProductUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelItemMaintenance.add(btnProductUpdate);
		
		JButton btnProductDelete = new JButton("DELETE");
		btnProductDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {

					// Find this item first before we try and delete it
					Item item = getDao().findItemByCode(txtProductsCode.getText());
					if(item.getItemCode() != null){
					
						// We use the showConfirmDialog method of the JOptionPane object to show a confirmation dialog.
						// This method's parameters: parent object, the message, the title, type of JOptionPane (yes=0,no=1)
						int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record","Delete",JOptionPane.YES_NO_OPTION);
						
						if (action==0) {

							getDao().deleteItem(txtProductsCode.getText());

							refreshProductsTable();
							String msg = "Item " + txtProductsCode.getText() + " has been deleted !"; 
							txtProductsCode.setText("");
							txtProductsDescription.setText("");
							txtProductsCost.setText("");
							txtProductsTrade.setText("");
							txtProductsRetail.setText("");
							JOptionPane.showMessageDialog(null, msg);
							
						}
					}else{
						JOptionPane.showMessageDialog(null, "Item " + txtProductsCode.getText() + " is not in the Database !");
					}
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
					
				
			}
		});
		btnProductDelete.setFont(new Font("Georgia", Font.BOLD, 16));
		btnProductDelete.setBounds(697, 102, 115, 38);
		btnProductDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelItemMaintenance.add(btnProductDelete);
		
		txtProductsTrade = new JTextField();
		txtProductsTrade.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtProductsTrade.setColumns(10);
		txtProductsTrade.setBounds(792, 49, 132, 32);
		txtProductsTrade.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelItemMaintenance.add(txtProductsTrade);
		
		JLabel lblTradePrice = new JLabel("Trade Price");
		lblTradePrice.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradePrice.setBounds(792, 24, 115, 14);
		panelItemMaintenance.add(lblTradePrice);
		
		txtProductsCost = new JTextField();
		txtProductsCost.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtProductsCost.setColumns(10);
		txtProductsCost.setBounds(610, 49, 132, 32);
		txtProductsCost.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelItemMaintenance.add(txtProductsCost);
		
		JLabel lblCostprice = new JLabel("Cost Price");
		lblCostprice.setFont(new Font("Georgia", Font.BOLD, 16));
		lblCostprice.setBounds(610, 24, 86, 14);
		panelItemMaintenance.add(lblCostprice);
		
		JPanel panelItemList = new JPanel();
		panelItemList.setBackground(UIManager.getColor("List.dropLineColor"));
		panelItemList.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(227, 227, 227), new Color(227, 227, 227), UIManager.getColor("Button.foreground"), new Color(105, 105, 105)));
		panelItemList.setBounds(64, 11, 1166, 379);
		this.add(panelItemList);
		panelItemList.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 83, 894, 281);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelItemList.add(scrollPane);
		
		tblProductsItem = new JTable(){
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
				if ( col == dataModel.getColumnCount() - 1 || col == dataModel.getColumnCount() - 2 || col == dataModel.getColumnCount() - 3) 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.RIGHT );
				
				// Left justify the other columns
				else 
					((JLabel) renderer).setHorizontalAlignment( SwingConstants.LEFT );
				
				return renderer; 
			}
			
		};
		tblProductsItem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblProductsItem.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		// This formats the Headers
		tblProductsItem.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		tblProductsItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblProductsItem.getSelectedRow();
					
					// Then we can get the itemID of the selected row by getting the model of the table and then using the getValueAt() method we can get the specific
					// row and column that we require.
					// This returns an Object so we get the toString of it.
					String itemCode = tblProductsItem.getModel().getValueAt(row, 0).toString();
					
					Item item = getDao().loadItem(itemCode);
					
					txtProductsCode.setText(item.getItemCode());
					txtProductsDescription.setText(item.getItemDescription());
					txtProductsCost.setText(Utilities.stringToDec( String.valueOf(item.getCostPrice())) );
					txtProductsTrade.setText(Utilities.stringToDec( String.valueOf(item.getTradePrice())) );
					txtProductsRetail.setText(Utilities.stringToDec( String.valueOf(item.getRetailPrice())) );

				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		scrollPane.setViewportView(tblProductsItem);
		
		comboBoxProductsSrch = new JComboBox<String>();
		comboBoxProductsSrch.setBounds(28, 26, 162, 38);
		comboBoxProductsSrch.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelItemList.add(comboBoxProductsSrch);
		comboBoxProductsSrch.setFont(new Font("Georgia", Font.BOLD, 16));
		comboBoxProductsSrch.setModel(new DefaultComboBoxModel<String>(new String[] {"ITEM", "DESCRIPTION"}));
		comboBoxProductsSrch.setSelectedItem("DESCRIPTION");
		comboBoxProductsSrch.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				txtProductsSrch.setText("");
				refreshProductsTable();
			}
		});

		txtProductsSrch = new JTextField();
		txtProductsSrch.setBounds(402, 28, 242, 32);
		txtProductsSrch.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelItemList.add(txtProductsSrch);
		txtProductsSrch.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtProductsSrch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				txtProductsSrch.setText(txtProductsSrch.getText().toUpperCase());
				refreshProductsTable();
			}
		});
		txtProductsSrch.setColumns(10);
		
		JButton btnLoadAll = new JButton("LOAD ALL PRODUCTS");
		btnLoadAll.setBounds(858, 26, 242, 38);
		btnLoadAll.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelItemList.add(btnLoadAll);
		btnLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txtProductsSrch.setText("");
				refreshProductsTable();
			}
		});
		btnLoadAll.setFont(new Font("Georgia", Font.BOLD, 16));
		
		JLabel lblProductsLogo = new JLabel();
		// Put Logo image on products tab
		lblProductsLogo.setIcon(imageIcon);

		lblProductsLogo.setBounds(935, 114, 199, 210);
		panelItemList.add(lblProductsLogo);
				
		comboBoxProductsSrch.setSelectedItem("NAME");
		comboBoxProductsSrch.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				refreshProductsTable();
			}
		});

		refreshProductsTable();
	}
	
	/**
	 * Method that retrieves a list of products from the database based on the user's selection criteria and loads them into a table
	 * that is displayed on the screen 
	 */
	public void refreshProductsTable(){
		String selection = comboBoxProductsSrch.getSelectedItem().toString();
		String filter = "%" + txtProductsSrch.getText() + "%";
		
		if(selection=="ITEM"){
			selection = "itemCode";
		} else if(selection=="DESCRIPTION"){
			selection = "itemDescription";
		} else {
			selection = "retailPrice";
		}

		try {
			
			List<Item> itemList = getDao().findFilteredItem(selection, filter);
			
			String columns[] = {"ITEM", "DESCRIPTION", "COST", "TRADE", "RETAIL"};
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
			
			for(Item item : itemList){
				String tableCode= item.getItemCode();
				String tableDesc = item.getItemDescription();
				String tableCost = Utilities.floatToString2Dec(item.getCostPrice());
				String tableTrade = Utilities.floatToString2Dec(item.getTradePrice());
				String tableRetail = Utilities.floatToString2Dec(item.getRetailPrice());
				Object[] line = {tableCode, tableDesc, tableCost, tableTrade, tableRetail};
				tableModel.addRow(line);
			}
			
			tblProductsItem.setModel(tableModel);
			
			tblProductsItem.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel columnModel = tblProductsItem.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(100);
			columnModel.getColumn(1).setPreferredWidth(479);
			columnModel.getColumn(2).setPreferredWidth(99);
			columnModel.getColumn(3).setPreferredWidth(99);
			columnModel.getColumn(4).setPreferredWidth(99);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	
}