package view;


import io.GameCreationMode;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import controller.Game;


public class MainMenu {
	
	private JTextField playerNameTextField;
	private JFrame frame;
	private JLabel label;
	private JButton newGameButton, loadGameButton, exitButton;
	private String filePath = null;
	private GameCreationMode loadOrCreate = null;
	private final int width = 300,
						height = 300,
						buttonsWidth = 250,
						buttonsHeight = 25;
	
	public MainMenu() {
		frame = new JFrame(Game.GAME_NAME);
		format();
		build();		
		frame.setVisible(true);
	}
	
	public String getUserChosenFilePath() {
		return filePath;
	}
	
	public GameCreationMode loadOrCreatePlayer() {
		return loadOrCreate;
	}
	
	public void dispose() {
		frame.dispose();
	}
	
	private void format() {
		frame.setSize(width, height);
		frame.setLocation(0, 0);
		frame.setBackground(new Color(128, 128, 0));
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void build() {
		newGameButton = new JButton("New character");
		newGameButton.setBounds((width-buttonsWidth)/2, height/4, buttonsWidth, buttonsHeight);
		newGameButton.setEnabled(false);
		newGameButton.setFocusable(false);
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(playerNameTextField.getText().isEmpty()) {
					display("Name your hero first!");
				}
				else {
					String directory = chooseDirectory();
					if(directory == null) {
						display("Invalid save location chosen.");
						return;
					}
					filePath = directory + playerNameTextField.getText() + ".player";
					loadOrCreate = GameCreationMode.CREATE_NEW_GAME;
				}
			}
		});
		frame.getContentPane().add(newGameButton);

		loadGameButton = new JButton("Load character");
		loadGameButton.setBounds((width-buttonsWidth)/2, height/4+buttonsHeight+10, buttonsWidth, buttonsHeight);
		loadGameButton.setFocusable(false);
		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chosenPath = choosePlayerFile();
				if(chosenPath == null) {
					display("Invalid file chosen.");
					return;
				}
				filePath = chosenPath;
				loadOrCreate = GameCreationMode.LOAD_GAME;
			}
		});
		frame.getContentPane().add(loadGameButton);
		
		exitButton = new JButton("Exit");
		exitButton.setFocusable(false);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setBounds((width-buttonsWidth)/2, height/4+2*buttonsHeight+20, buttonsWidth, buttonsHeight);
		frame.getContentPane().add(exitButton);
		
		playerNameTextField = new JTextField();
		playerNameTextField.setBounds(50, 30, 200, 30);
		PlainDocument textFieldDoc = (PlainDocument) playerNameTextField.getDocument();
		textFieldDoc.setDocumentFilter(new PlayerNameDocumentFilter());	//To limit user input
		playerNameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(playerNameTextField.getText().isEmpty()) {
					newGameButton.setEnabled(false);
				}
				else {
					newGameButton.setEnabled(true);
				}
			}
		});
		frame.getContentPane().add(playerNameTextField);
		//playerNameTextField.setColumns(10);
		
		label = new JLabel("Enter your desired player name");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon("textures/Mobs/HellAssasin.png"));
		label.setBounds(0, 5, width, 20);
		frame.getContentPane().add(label);
	}
	
	private void display(String message) {
		label.setText(message);
	}
	
	private String choosePlayerFile() {
		JFileChooser filec = new JFileChooser("saves");
		filec.setSize(500,500);
		filec.setVisible(true);
		frame.getContentPane().add(filec);
		filec.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal=filec.showOpenDialog(frame.getContentPane());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String result = filec.getSelectedFile().getAbsolutePath();
			return result.substring(result.lastIndexOf(".")).equals(".player") ? result : null;
		}
		else {
			return null;
		}
	}
	
	private String chooseDirectory() {
		JFileChooser filec = new JFileChooser("saves");
		filec.setSize(500,500);
		filec.setVisible(true);
		filec.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		frame.getContentPane().add(filec);
		int returnVal = filec.showOpenDialog(frame.getContentPane());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return filec.getSelectedFile().getAbsolutePath() + File.separator;
		}
		else {
			return null;
		}
	}
}
