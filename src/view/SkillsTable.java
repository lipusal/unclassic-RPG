package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import skills.Skill;
import skills.SkillSet;

public class SkillsTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private DefaultTableCellRenderer centerRenderer;
	private SkillSet skillSet;
	
	public SkillsTable(SkillSet skills) {
		this.skillSet = skills;
		this.model = new DefaultTableModel();
		setModel(model);
		centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		build();
		format();
	}
	
	/**
	 * Updates the entire table by fetching all the Skills in the
	 * SkillSet and putting their information on the table.
	 */
	public void updateAll() {
		int i = 1;
		for(Entry<String, Skill> e : skillSet.getEntrySet())
		{
			setValueAt(e.getKey(), i, 0);
			Skill s = e.getValue();
			setValueAt(s.getLevel() + " (" + s.getProgress() + ")", i, 1);
			i++;
		}
	}
	private void build() {
		model.addColumn("Skills");
		model.addColumn("Levels");
		model.addRow(new Object[] {"Skill", "Level"} );
		getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		
		for(Entry<String, Skill> e : skillSet.getEntrySet())
		{
			Skill temp = e.getValue();
			model.addRow(new Object[] { e.getKey(), temp.getLevel() + " (" + temp.getProgress() + ")" });
		}
	}
	
	private void format() {		
		setBorder(new LineBorder(Color.BLACK));
		setFont(new Font("Monospaced", Font.PLAIN, 13));
		setEnabled(false);
		setBounds(500, 340, View.TABLES_WIDTH, View.SKILLS_TABLE_HEIGHT);
		setRowHeight(View.SKILLS_TABLE_HEIGHT / (skillSet.getNumberOfSkills()+1));
	}

}
