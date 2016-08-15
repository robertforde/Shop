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

import com.daniel.dao.TradesmanDao;
import com.daniel.dao.TradesmanDaoImpl;
import com.daniel.model.Tradesman;

import javax.swing.ListSelectionModel;

/**
 * This class handles the Tradesmen Tab on the main tabbed panel. It retrieves and displays all of the tradesmen from the database.
 * The screen has various filters to allow the user to filter by name, address and phone number. It also gives the user the ability to
 * perform CRUD operations on tradesmen in the database
 * @author Robert Forde
 *
 */
public class TradesmenScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> comboBoxTradesmenSrch;
	
	private JTable tblTradesmenList;
	
	private JTextField txtTradesmenSrch;
	private JTextField txtTradesmenID;
	private JTextField txtTradesmenName;
	private JTextField txtTradesmenAddressLine1;
	private JTextField txtTradesmenAddressLine2;
	private JTextField txtTradesmenPhone;

	private TradesmanDao tradesmanDao;

	
	public TradesmanDao getTradesmanDao() {
		return tradesmanDao;
	}

	public void setTradesmanDao(TradesmanDao tradesmanDao) {
		this.tradesmanDao = tradesmanDao;
	}

	
	/**
	 * Constructs the Tradesmen Screen
	 * @param tabbedPane This is tab on the the main screen of the application and is passed in so we can add this screen to it
	 */
	public TradesmenScreen(JTabbedPane tabbedPane){
		
		setTradesmanDao(Dan.ctx.getBean("tradesmanDaoImpl", TradesmanDaoImpl.class));
		
		// Create the logo images for the application
		Image logo = new ImageIcon(this.getClass().getResource("/Logo199X198.jpg")).getImage();
		ImageIcon imageIcon = new ImageIcon(logo);
						
		setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 1294, 624);
		tabbedPane.addTab("Tradesmen", null, this, null);
		setLayout(null);
		
		JPanel panelTradesmenList = new JPanel();
		panelTradesmenList.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradesmenList.setBounds(23, 11, 1237, 375);
		this.add(panelTradesmenList);
		panelTradesmenList.setLayout(null);
		
		comboBoxTradesmenSrch = new JComboBox<String>();
		comboBoxTradesmenSrch.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				txtTradesmenSrch.setText("");
				refreshTradesmenTable();
			}
		});
		comboBoxTradesmenSrch.setFont(new Font("Georgia", Font.BOLD, 16));
		comboBoxTradesmenSrch.setModel(new DefaultComboBoxModel<String>(new String[] {"NAME", "ADDRESS LINE 1", "ADDRESS LINE 2", "PHONE"}));
		comboBoxTradesmenSrch.setBounds(32, 26, 175, 38);
		comboBoxTradesmenSrch.setSelectedItem("NAME");
		comboBoxTradesmenSrch.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradesmenList.add(comboBoxTradesmenSrch);
		
		txtTradesmenSrch = new JTextField();
		txtTradesmenSrch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				refreshTradesmenTable();
			}
		});
		txtTradesmenSrch.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradesmenSrch.setBounds(309, 30, 313, 32);
		txtTradesmenSrch.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradesmenList.add(txtTradesmenSrch);
		txtTradesmenSrch.setColumns(10);
		
		JButton btnTradesmenLoadAll = new JButton("LOAD ALL TRADESMEN");
		btnTradesmenLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtTradesmenSrch.setText("");
				refreshTradesmenTable();
			}
		});
		btnTradesmenLoadAll.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradesmenLoadAll.setBounds(837, 26, 266, 38);
		btnTradesmenLoadAll.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelTradesmenList.add(btnTradesmenLoadAll);
		
		JScrollPane scrTradesmenList = new JScrollPane();
		scrTradesmenList.setBounds(32, 80, 949, 284);
		scrTradesmenList.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradesmenList.add(scrTradesmenList);
		
		tblTradesmenList = new JTable(){
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
		tblTradesmenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblTradesmenList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try {
					
					// We use the table's getSelectedRow() method to find the row that the User selected. 
					int row = tblTradesmenList.getSelectedRow();
					
					// Then we can get the id of the row that we require.
					// We then get the value of column 0 for that row in the model of our table, which returns an Object so we get the String of it and convert to integer
					String idString = tblTradesmenList.getModel().getValueAt(row, 0).toString();
					int idRow = Integer.valueOf(idString);
	
					Tradesman tradesman = getTradesmanDao().tradesmanRetrieve(idRow);
					
					txtTradesmenID.setText(idString);
					txtTradesmenName.setText(tradesman.getName());
					txtTradesmenAddressLine1.setText(tradesman.getAddressLine1());
					txtTradesmenAddressLine2.setText(tradesman.getAddressLine2());
					txtTradesmenPhone.setText(tradesman.getPhone());
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		tblTradesmenList.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		// This formats the Headers
		tblTradesmenList.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		scrTradesmenList.setViewportView(tblTradesmenList);
		
		JLabel lblTradesmenLogo = new JLabel("New label");
		lblTradesmenLogo.setBounds(1006, 120, 199, 200);
		// Put Logo image on tradesmen tab
		lblTradesmenLogo.setIcon(imageIcon);
		panelTradesmenList.add(lblTradesmenLogo);
		
		JPanel panelTradesmenMaintenance = new JPanel();
		panelTradesmenMaintenance.setBackground(UIManager.getColor("List.dropLineColor"));
		panelTradesmenMaintenance.setBounds(23, 424, 1237, 161);
		this.add(panelTradesmenMaintenance);
		panelTradesmenMaintenance.setLayout(null);
		
		JLabel lblTradesmenName = new JLabel("Name");
		lblTradesmenName.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradesmenName.setBounds(38, 19, 72, 19);
		panelTradesmenMaintenance.add(lblTradesmenName);
		
		JLabel lblTradesmenAddressLine1 = new JLabel("Address Line 1");
		lblTradesmenAddressLine1.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradesmenAddressLine1.setBounds(217, 19, 135, 19);
		panelTradesmenMaintenance.add(lblTradesmenAddressLine1);
		
		JLabel lblTradesmenAddressLine2 = new JLabel("Address Line 2");
		lblTradesmenAddressLine2.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradesmenAddressLine2.setBounds(627, 19, 135, 19);
		panelTradesmenMaintenance.add(lblTradesmenAddressLine2);
		
		JLabel lblTradesmenPhone = new JLabel("Phone");
		lblTradesmenPhone.setFont(new Font("Georgia", Font.BOLD, 16));
		lblTradesmenPhone.setBounds(1055, 19, 72, 19);
		panelTradesmenMaintenance.add(lblTradesmenPhone);
		
		txtTradesmenID = new JTextField();
		txtTradesmenID.setEnabled(false);
		txtTradesmenID.setEditable(false);
		txtTradesmenID.setVisible(false);
		txtTradesmenID.setBounds(10, 49, 4, 20);
		panelTradesmenMaintenance.add(txtTradesmenID);
		txtTradesmenID.setColumns(10);
		
		txtTradesmenName = new JTextField();
		txtTradesmenName.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradesmenName.setBounds(38, 49, 144, 28);
		txtTradesmenName.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradesmenMaintenance.add(txtTradesmenName);
		txtTradesmenName.setColumns(10);
		
		txtTradesmenAddressLine1 = new JTextField();
		txtTradesmenAddressLine1.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradesmenAddressLine1.setColumns(10);
		txtTradesmenAddressLine1.setBounds(217, 49, 400, 28);
		txtTradesmenAddressLine1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradesmenMaintenance.add(txtTradesmenAddressLine1);
		
		txtTradesmenAddressLine2 = new JTextField();
		txtTradesmenAddressLine2.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradesmenAddressLine2.setColumns(10);
		txtTradesmenAddressLine2.setBounds(627, 49, 400, 28);
		txtTradesmenAddressLine2.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradesmenMaintenance.add(txtTradesmenAddressLine2);
		
		txtTradesmenPhone = new JTextField();
		txtTradesmenPhone.setFont(new Font("Georgia", Font.PLAIN, 16));
		txtTradesmenPhone.setColumns(10);
		txtTradesmenPhone.setBounds(1055, 49, 144, 28);
		txtTradesmenPhone.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(160, 160, 160), new Color(160, 160, 160), new Color(0, 0, 0), new Color(105, 105, 105)));
		panelTradesmenMaintenance.add(txtTradesmenPhone);
		
		JButton btnTradesmenInsert = new JButton("INSERT");
		btnTradesmenInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// First check that something was entered in the Tradesman Name field
					if(!txtTradesmenName.getText().isEmpty()) {
						
						// Check if the tradesman already exists
						Tradesman tradesman = new Tradesman(txtTradesmenName.getText(), txtTradesmenAddressLine1.getText(), txtTradesmenAddressLine2.getText(), 
								txtTradesmenPhone.getText(), 0);
						
						Tradesman checkTradesman = getTradesmanDao().tradesmanExists(tradesman);
						
						// If this tradesman was not found in the table already
						if(checkTradesman.getName() == null) {
							
							getTradesmanDao().tradesmanInsert(tradesman);
							
							refreshTradesmenTable();
							txtTradesmenSrch.setText("");
							txtTradesmenID.setText("");
							txtTradesmenName.setText("");
							txtTradesmenAddressLine1.setText("");
							txtTradesmenAddressLine2.setText("");
							txtTradesmenPhone.setText("");
							
						}else {
							JOptionPane.showMessageDialog(null, "Tradesman " + checkTradesman.getName() + " is already in the Database !");	
						}
							
					} else {
						JOptionPane.showMessageDialog(null, "You must enter a Tradesman Name !");
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnTradesmenInsert.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradesmenInsert.setBounds(310, 104, 128, 38);
		btnTradesmenInsert.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelTradesmenMaintenance.add(btnTradesmenInsert);
		
		JButton btnTradesmenUpdate = new JButton("UPDATE");
		btnTradesmenUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					// First check that something was entered in the Customer fields
					if( !(txtTradesmenName.getText().isEmpty() && txtTradesmenPhone.getText().isEmpty()) ) {
	
						Tradesman tradesman = new Tradesman(Integer.valueOf(txtTradesmenID.getText()), txtTradesmenName.getText(), txtTradesmenAddressLine1.getText(), 
								txtTradesmenAddressLine2.getText(), txtTradesmenPhone.getText(),0);
						
						int result = getTradesmanDao().tradesmanUpdate(tradesman);
						
						if (result == 1) {
							
							JOptionPane.showMessageDialog(null, "Tradesman " + tradesman.getName() + " has been updated !");
							
							refreshTradesmenTable();
							txtTradesmenID.setText("");
							txtTradesmenName.setText("");
							txtTradesmenAddressLine1.setText("");
							txtTradesmenAddressLine2.setText("");
							txtTradesmenPhone.setText("");
						
						}else{
							JOptionPane.showMessageDialog(null, "Tradesman " + tradesman.getName() + " is not in the Database !");
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "You must select a tradesman before you update !");	
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnTradesmenUpdate.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradesmenUpdate.setBounds(552, 104, 128, 38);
		btnTradesmenUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelTradesmenMaintenance.add(btnTradesmenUpdate);
		
		JButton btnTradesmenDelete = new JButton("DELETE");
		btnTradesmenDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int result = getTradesmanDao().tradesmanDelete(txtTradesmenID.getText());
					
					if(result == 1){
						
						JOptionPane.showMessageDialog(null, "Tradesman " + txtTradesmenName.getText() + " has been deleted !");
						
						refreshTradesmenTable();
						txtTradesmenID.setText("");
						txtTradesmenName.setText("");
						txtTradesmenAddressLine1.setText("");
						txtTradesmenAddressLine2.setText("");
						txtTradesmenPhone.setText("");
						
					}else{
						JOptionPane.showMessageDialog(null, "Tradesman " + txtTradesmenName.getText() + " is not in the Database !");
					}
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnTradesmenDelete.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTradesmenDelete.setBounds(790, 104, 128, 38);
		btnTradesmenDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, new Color(160, 160, 160), new Color(0, 0, 0), UIManager.getColor("Button.darkShadow")));
		panelTradesmenMaintenance.add(btnTradesmenDelete);

		refreshTradesmenTable();
	}
	
	
	/**
	 * Method that retrieves a list of tradesmen from the database based on the user's selection criteria and loads them into a table
	 * that is displayed on the screen 
	 */
	public void refreshTradesmenTable() {
		String selection = comboBoxTradesmenSrch.getSelectedItem().toString();
		String filter = "%" + txtTradesmenSrch.getText() + "%";
		
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
			List<Tradesman> tradesmenList = getTradesmanDao().tradesmenLoadFiltered(selection, filter);
			
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
			
			tblTradesmenList.setModel(tableModel);
			
			// Set the column widths
			tblTradesmenList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel columnModel = tblTradesmenList.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(25);
			columnModel.getColumn(1).setPreferredWidth(180);
			columnModel.getColumn(2).setPreferredWidth(295);
			columnModel.getColumn(3).setPreferredWidth(295);
			columnModel.getColumn(4).setPreferredWidth(135);
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}		
	}
	
}
