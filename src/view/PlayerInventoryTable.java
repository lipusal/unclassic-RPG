package view;

import items.Inventory;
import items.InventorySlot;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import entities.creatures.player.Player;


/**
 * Class used to represent the player's inventory in table form. It has a reference
 * to the player's inventory and is built and updated based on it.
 */
public class PlayerInventoryTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private DefaultTableCellRenderer centerRenderer;
	private Inventory inventory;
	
	public PlayerInventoryTable(Inventory inventory) {
		this.inventory = inventory;
		model  = new DefaultTableModel();
		setModel(model);
		centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		build();
		format();
	}
	
	public void updateAll() {
		int i = 1;
		for(InventorySlot slot : inventory) {
			setValueAt(slot.toString(), i, 0);
			i++;
		}
	}
	
	/**
	 * Builds the structure of the table and fills it up with any items
	 * already present in the Inventory.
	 */
	private void build() {
		model.addColumn("Items");
		model.addRow(new Object[] {"Inventory"} );
		getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		
		int i = 0;
		for(InventorySlot slot : inventory) {
			model.addRow(new Object[] { slot.toString() });
			i++;
		}
		//Fill remaining rows with empty Strings
		while(i < Player.PLAYER_INVENTORY_SLOTS) {
			model.addRow(new Object[] { "" });
			i++;
		}
		
		for (int c = 0; c < this.getColumnCount(); c++)
		{
		    Class<?> col_class = this.getColumnClass(c);
		    this.setDefaultEditor(col_class, null);        // remove editor
		}
		
	}
	
	private void format() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setShowGrid(true);
		setShowVerticalLines(false);
		setFont(new Font("Monospaced", Font.PLAIN, 13));
		setBounds(500, 0, View.TABLES_WIDTH, View.INVENTORY_TABLE_HEIGHT);
		setRowHeight(View.INVENTORY_TABLE_HEIGHT / (Player.PLAYER_INVENTORY_SLOTS+1));
		setFocusable(false);
		setRowSelectionAllowed(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

}
