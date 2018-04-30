package view;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import controller.Game;

public class BottomPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextPane textPane;
	private JPanel statsPanel, equipmentPanel;
	private JLabel healthLabel, attackLabel, defenseLabel,
					headLabel, torsoLabel, leftHandLabel, rightHandLabel,
					armsLabel, legsLabel, feetLabel;
	private List<String> lines;
	private int width, height, sidePanelWidth, textPaneWidth, labelHeight;
	
	public BottomPanel() {
		lines = new ArrayList<String>(View.MAX_DIALOGUE_LINES);
		width = View.BOTTOM_PANEL_WIDTH;
		height = View.BOTTOM_PANEL_HEIGHT;
		sidePanelWidth = width/4;
		textPaneWidth = width/2;
		labelHeight = 15;
		build();
		format();
	}
	
	private void build() {
		//Text Pane
		textPane = new JTextPane();
		textPane.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textPane.setEditable(false);
		textPane.setFocusable(false);
		textPane.setBounds(width/4, 0, textPaneWidth, height);
		add(textPane);
		
		//Stats Panel
		statsPanel = new JPanel();
		statsPanel.setLayout(null);
		statsPanel.setBounds(0, 0, sidePanelWidth, height);
		add(statsPanel);
		
		healthLabel = new JLabel("Health: " + Game.getInstance().getPlayer().getHealth() + "/" + Game.getInstance().getPlayer().getMaxHealth());
		healthLabel.setBounds(0, 10, sidePanelWidth, labelHeight);
		statsPanel.add(healthLabel);
		
		attackLabel = new JLabel("Attack: " + Game.getInstance().getPlayer().getAttack());
		attackLabel.setBounds(0, healthLabel.getY() + labelHeight + 10, sidePanelWidth, labelHeight);
		statsPanel.add(attackLabel);
		
		defenseLabel = new JLabel("Defense: "  + Game.getInstance().getPlayer().getDefense());
		defenseLabel.setBounds(0, attackLabel.getY() + labelHeight + 10, sidePanelWidth, labelHeight);
		statsPanel.add(defenseLabel);		
		
		//Equipment panel
		equipmentPanel = new JPanel();
		equipmentPanel.setLayout(null);
		equipmentPanel.setBounds(3*width/4, 0, sidePanelWidth, height);
		add(equipmentPanel);

		headLabel = new JLabel("Head: " + Game.getInstance().getPlayer().getEquipmentOn("head"));
		headLabel.setBounds(0, 5, sidePanelWidth, labelHeight);
		equipmentPanel.add(headLabel);
		
		torsoLabel = new JLabel("Torso: " + Game.getInstance().getPlayer().getEquipmentOn("torso"));
		torsoLabel.setBounds(0, headLabel.getY() + labelHeight + 10, sidePanelWidth, labelHeight);
		equipmentPanel.add(torsoLabel);
		
		leftHandLabel = new JLabel("Left Hand: " + Game.getInstance().getPlayer().getEquipmentOn("left hand"));
		leftHandLabel.setBounds(0, torsoLabel.getY() + labelHeight + 10, sidePanelWidth, labelHeight);
		equipmentPanel.add(leftHandLabel);
		
		rightHandLabel = new JLabel("Right Hand: " + Game.getInstance().getPlayer().getEquipmentOn("right hand"));
		rightHandLabel.setBounds(0, leftHandLabel.getY() + labelHeight + 10, sidePanelWidth, labelHeight);
		equipmentPanel.add(rightHandLabel);
		
		armsLabel = new JLabel("Arms: " + Game.getInstance().getPlayer().getEquipmentOn("arms"));
		armsLabel.setBounds(0, rightHandLabel.getY() + labelHeight + 10, sidePanelWidth, labelHeight);
		equipmentPanel.add(armsLabel);
		
		legsLabel = new JLabel("Legs: " + Game.getInstance().getPlayer().getEquipmentOn("legs"));
		legsLabel.setBounds(0, armsLabel.getY() + labelHeight + 10, sidePanelWidth, labelHeight);
		equipmentPanel.add(legsLabel);
		
		feetLabel = new JLabel("Feet: " + Game.getInstance().getPlayer().getEquipmentOn("feet"));
		feetLabel.setBounds(0, legsLabel.getY() + labelHeight + 10, sidePanelWidth, labelHeight);
		equipmentPanel.add(feetLabel);
	}
	
	private void format() {
		setLayout(null);
		setSize(View.BOTTOM_PANEL_WIDTH, View.BOTTOM_PANEL_HEIGHT);
		setLocation(0, View.WORLD_CANVAS_HEIGHT);
	}
	
	private boolean isFull() {
		return lines.size() == View.MAX_DIALOGUE_LINES;
	}

	public void display(String message) {
		if(isFull()) {
			lines.remove(0);
		}
		lines.add(message);
		String textToDisplay = "";
		for(String line : lines) {
			textToDisplay += line + "\n";
		}
		textPane.setText(textToDisplay);
	}
	
	public void updateStatsPanel() {
		healthLabel.setText("Health: " + Game.getInstance().getPlayer().getHealth() + "/" + Game.getInstance().getPlayer().getMaxHealth());
		attackLabel.setText("Attack: " + Game.getInstance().getPlayer().getAttack());
		defenseLabel.setText("Defense: " + Game.getInstance().getPlayer().getDefense());
	}
	
	public void updateEquipmentPanel() {
		headLabel.setText("Head: " + Game.getInstance().getPlayer().getEquipmentOn("head"));
		torsoLabel.setText("Torso: " + Game.getInstance().getPlayer().getEquipmentOn("torso"));
		leftHandLabel.setText("Left Hand: " + Game.getInstance().getPlayer().getEquipmentOn("left hand"));
		rightHandLabel.setText("Right Hand: " + Game.getInstance().getPlayer().getEquipmentOn("right hand"));
		armsLabel.setText("Arms: " + Game.getInstance().getPlayer().getEquipmentOn("arms"));
		legsLabel.setText("Legs: " + Game.getInstance().getPlayer().getEquipmentOn("legs"));
		feetLabel.setText("Feet: " + Game.getInstance().getPlayer().getEquipmentOn("feet"));
	}
}
