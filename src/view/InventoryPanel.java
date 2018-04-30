package view;

import items.Inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.Game;
import entities.creatures.player.Player;

public class InventoryPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Inventory inventory;
	JTable inventoryTable;

	public InventoryPanel() {
		setBounds(500, 0, View.TABLES_WIDTH, View.INVENTORY_TABLE_HEIGHT);
		this.inventory = Game.getInstance().getPlayer().getInventory();
		this.inventoryTable = createTable();
		setLayout(new BorderLayout());
		this.add(this.inventoryTable.getTableHeader(),BorderLayout.NORTH);
		this.add(this.inventoryTable,BorderLayout.CENTER);
		setVisible(true);
		setFocusable(false);
		// This must be done here. Won't compile if it's done in method createTable()
		inventoryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        @Override
			public void valueChanged(ListSelectionEvent e) {
	        	//Avoids triggering event when mouse is being pressed instead of released
	        	if (e.getValueIsAdjusting()) {
	        		return; 
	        	}
	        	int rowSelected = inventoryTable.getSelectedRow();
	        	Object value = (rowSelected == -1) ? null : inventoryTable.getValueAt(rowSelected, 0);
	        	String selectedItemName = (value != null) ? value.toString() : null;
	            Game.getInstance().setSelectedItem(selectedItemName); 
	        }	
	    });	
	}
	
	
	public void updateAll() {
		updateData();
		inventoryTable.getSelectionModel().clearSelection();
	}
	
	private JTable createTable() {	
		
		//Builds the table
		JTable table = new JTable(new DefaultTableModel(19,2));
		table.setBounds(0, 0, 170, 330);
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		table.setShowGrid(true);
		table.setBorder(new LineBorder(Color.BLACK));	
		
		//Sets the table columns
		DefaultTableColumnModel columns = new DefaultTableColumnModel();
		TableColumn itemColumn = new TableColumn(0,140);
		itemColumn.setHeaderValue("Items");
		TableColumn qtyColumn = new TableColumn(1,30);
		qtyColumn.setHeaderValue("Qty");
		columns.addColumn(itemColumn);
		columns.addColumn(qtyColumn);
		table.setColumnModel(columns);
		
		//Sets properties of the table
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFocusable(false);
		table.setVisible(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		
		//Makes the table be non-editable
		for (int c = 0; c < table.getColumnCount(); c++) {
		    Class<?> col_class = table.getColumnClass(c);
		    table.setDefaultEditor(col_class, null);
		}	
		return table;
	}
	
	
	public void updateData() {	
		Vector<Vector<String>> vector = inventory.getDataVector();
		int i = 0;
		for (Vector<String> v : vector) {
			int j = 0;
			for (String s : v) {		
				inventoryTable.setValueAt(s, i, j);
				j++;
			}
			i++;
		}		
		for ( ; i < Player.PLAYER_INVENTORY_SLOTS ; i++) {
			inventoryTable.setValueAt(null, i, 0);
			inventoryTable.setValueAt(null, i, 1);
		}		
	}

}
