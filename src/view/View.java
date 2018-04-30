package view;

import items.Inventory;

import java.awt.Canvas;

import javax.swing.JFrame;

import skills.SkillSet;
import controller.Game;

/**
 * Class used to hold all GUI-related containers.
 */
public class View {

	public static final int
							WINDOW_HEIGHT = 700,
							WINDOW_WIDTH = 700,
							WORLD_CANVAS_WIDTH = 500,
							WORLD_CANVAS_HEIGHT = 500,
							TILE_SIZE = 64,
							VIEW_SIZE_IN_TILES = WORLD_CANVAS_WIDTH / TILE_SIZE,
							TABLES_WIDTH = 194,
							INVENTORY_TABLE_HEIGHT = 340,
							SKILLS_TABLE_HEIGHT = 160,
							LIFE_PANEL_WIDTH = 50,
							LIFE_PANEL_HEIGHT = 172,
							BOTTOM_PANEL_WIDTH = WINDOW_WIDTH,
							BOTTOM_PANEL_HEIGHT = 172,
							MAX_DIALOGUE_LINES = 9;
	
	private JFrame frame;
	private Canvas canvas;
	private Renderer renderer;
	private SkillsTable skillsTable;
	private InventoryPanel inventoryPanel;
	private BottomPanel bottomPanel;
	
	private boolean inFight;
	
	/**
	 * Creates a new View, once the game has started,
	 * displaying the world, the player's inventory
	 * and skill set as provided by the parameters.
	 * @param skills The game's player's skill set.
	 * @param inventory The game's player's inventory.
	 */
	public View(SkillSet skills, Inventory inventory) {
		//this.state = s;
		this.inFight = false;
		setUpView(skills, inventory);
		this.frame.requestFocusInWindow();
	}
	
	/**
	 * Sets up the view's skill set and inventory components
	 * to display the appropriate info.
	 * @param skills The skill set to initially display.
	 * @param inventory The inventory to initially display.
	 */
	private void setUpView(SkillSet skills, Inventory inventory) {
		
		frame = new JFrame();
		format();
		
		canvas = new Canvas();
		canvas.setEnabled(false);
		canvas.setBounds(0, 0, WORLD_CANVAS_WIDTH, WORLD_CANVAS_HEIGHT);
		canvas.setFocusable(false);
		//canvas.setBackground(Color.BLACK);
		frame.getContentPane().add(canvas);
		
		inventoryPanel = new InventoryPanel();
		frame.getContentPane().add(inventoryPanel);
		
		skillsTable = new SkillsTable(skills);
		frame.getContentPane().add(skillsTable);
		
		//Default dialog panel displayed 
		bottomPanel = new BottomPanel();
		frame.getContentPane().add(bottomPanel);
		
		frame.setVisible(true);
		renderer = new Renderer(this);
	}
	
	public void draw(DrawableWorld gameState) {
		renderer.render(gameState);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public Canvas getCanvas() {
		return this.canvas;
	}
	
	public void updateSkillsTable() {
		skillsTable.updateAll();
	}
	
	public void updateInventoryTable() {
		inventoryPanel.updateAll();
	}
	
	public void updateStatsPanel() {
		bottomPanel.updateStatsPanel();
	}
	
	public void updateEquipmentPanel() {
		bottomPanel.updateEquipmentPanel();
	}
	
	public void display(String message) {
		bottomPanel.display(message);
	}
	
	public boolean isInFightMode() {
		return this.inFight;
	}	
	
	private void format() {
		frame.setSize(View.WINDOW_WIDTH, View.WINDOW_HEIGHT);
		frame.setResizable(false);
		frame.setTitle(Game.GAME_NAME);
		frame.setLocationRelativeTo(null);	//Makes the frame appear in the center of the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
}
