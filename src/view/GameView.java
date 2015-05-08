package view;

import game.Character;
import game.Game;
import game.Settings;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import rooms.MagicRoom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 * GameView.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 11.11.2014
 * Last change: 07.02.2015
 * Description: Represents the main JFrame, handles the user interface and all dialoges/error messages.
 */

public class GameView extends JFrame implements Observer {
	
	private static final long serialVersionUID = -2994323922369479506L;
	
	/**
	 * The controller according to the MVC pattern.
	 */
	private Game controller;
	
	/**
	 * The JPanel that contains the worldmap.
	 */
	private WorldmapPanel map;
	
	/**
	 * contentPane
	 */
	private JPanel contentPane;
	
	/**
	 * The console that the user will see on the UI.
	 */
	private JTextArea console;
	
	/**
	 * If false, only the simple version of the GUI (to be able show error messages during the game initialization, for instance) is loaded.
	 */
	private Boolean guiLoaded = false;
	
	/**
	 * GameView
	 * Constructor.
	 * @param controller contains the controller (game), according to the MVC pattern.
	 */
	public GameView() {
		super(Settings.getGameTitle());
		this.controller = Settings.getController();
		this.pack();
		this.setLocationRelativeTo(null); // Centralize the window relativ to the desktop
	}
	
	/**
	 * loadGUI
	 * Load the complete GUI with world map, user input etc.
	 */
	public void loadGUI() {
		if (this.guiLoaded == false) {
			createWorldmap();
			initGui(); // Initialize the GUI components.
			this.pack();
			this.setLocationRelativeTo(null); // Centralize the window relativ to the desktop
			this.setVisible(true);
		}
	}

	/**
	 * initGUI
	 * Initializes all components of the GUI.
	 */
	private void initGui() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.contentPane = new JPanel(); // Add general JPanel which includes the content of this JFrame
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 5));
		this.setContentPane(contentPane);
		
		// Initialization of the top menu
		initTopMenu();
		
		// Initialization of the JPanels
		JPanel center = map; // Contains the worldmap
		contentPane.add(center, BorderLayout.CENTER);

		JPanel bottom = new JPanel(); // Contains the JPanel at the bottom
		bottom.setLayout(new BorderLayout(0,0));
		contentPane.add(bottom, BorderLayout.SOUTH);
		
		JPanel buttonPanel = new JPanel (); // Contains the control buttons
		buttonPanel.setLayout(new BorderLayout(5,0));
		bottom.add(buttonPanel, BorderLayout.WEST);
		
		JPanel bottomRight = new JPanel(); // Contains the JPanel at the bottom
		bottomRight.setLayout(new BorderLayout(0,0));
		bottom.add(bottomRight, BorderLayout.EAST);
		
		// Initialization of the text console
		console = new JTextArea();
		console.setEditable(false);
		
		JScrollPane consoleScrollPane = new JScrollPane(console); 
		consoleScrollPane.setPreferredSize(new Dimension(500, 75));
		bottomRight.add(consoleScrollPane, BorderLayout.NORTH);
		
		// Initialization of the text input field
		JTextField textField = new JTextField(10);
		textField.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
		    controller.consoleInput(textField.getText());
		    textField.setText("");
		  }
		});
		
		textField.setPreferredSize(new Dimension(500, 25));
		bottomRight.add(textField, BorderLayout.SOUTH);
		
		// Initialization of the direction buttons
		Dimension btDimension = new Dimension(120, 35);
		
		JButton btNorth = new JButton("North");
		btNorth.setPreferredSize(btDimension);
		buttonPanel.add(btNorth, BorderLayout.NORTH);
		btNorth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonEvent(1);
			}
		});

		JButton btEast = new JButton("East");
		btEast.setPreferredSize(btDimension);
		buttonPanel.add(btEast, BorderLayout.EAST);
		btEast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonEvent(2);
			}
		});

		JButton btSouth = new JButton("South");
		btSouth.setPreferredSize(btDimension);
		buttonPanel.add(btSouth, BorderLayout.SOUTH);
		btSouth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonEvent(3);
			}
		});

		JButton btWest = new JButton("West");
		btWest.setPreferredSize(btDimension);
		buttonPanel.add(btWest, BorderLayout.WEST);
		btWest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonEvent(4);
			}
		});	
	}
	
	/**
	 * initTopMenu
	 * Initialize the main menu on the top.
	 */
	private void initTopMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu file = new JMenu("File");
		menuBar.add(file);
		
		JMenuItem reset = new JMenuItem("Restart game");
		file.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				controller.resetGame();
			}
		});
		
		JMenuItem loadWorld = new JMenuItem("Load new world");
		file.add(loadWorld);
		loadWorld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadWorld();
			}
		});
		
		JMenuItem loadChar = new JMenuItem("Change character");
		file.add(loadChar);
		loadChar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadCharacter();
			}
		});
		
		JMenuItem exit = new JMenuItem("Exit game");
		file.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.closeGame();
			}
		});
		
		JMenu about = new JMenu("About");
		menuBar.add(about);
		
		JMenuItem authors = new JMenuItem("Authors");
		about.add(authors);
		authors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = controller.importTextFile(Settings.getTextFileAuthors());
				showPlainTextPopup(text);
			}
		});
		
		JMenuItem help = new JMenuItem("Help");
		about.add(help);
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = controller.importTextFile(Settings.getTextFileHelp());
				showPlainTextPopup(text);
				}
		});		
	}
	
	/**
	 * createWorldmap
	 * Create an instance of the graphical worldmap.
	 */
	private void createWorldmap() {
		this.map = new WorldmapPanel(controller.getRooms());
	}

	/**
	 * showPlainTextPopup
	 * Show a popup with any simple text content.
	 * @param text contains the complete text string.
	 */
	private void showPlainTextPopup(String text) {
		Dialog dialog = new Dialog();
		dialog.showTextArea(text);
	}

	/**
	 * update
	 * Tell the map to redraw the player position.
	 */
	@Override
	public void update(Observable o, Object arg) {
		map.drawPlayer((Character) arg);
	}

	/**
	 * getAnswer
	 * Open a dialog where the player has to answer a question.
	 * @param room object that contains the question & answer.
	 * @return if the player has answered the question right.
	 */
	public Boolean getAnswer(MagicRoom room) {
		Dialog dialog = new Dialog();
		Boolean answered = dialog.showQuestionDialog(room);
		return answered;
	}
	
	/**
	 * loadWorld
	 * Use JFileChooser to import a new map file.
	 */
	public void loadWorld() {
		// URL worldsPath = getClass().getResource(Settings.getWorldFile());
		// System.out.println(worldsPath.toString());
		JFileChooser chooser = new JFileChooser();
        int value = chooser.showOpenDialog(this);
        
        if (value == JFileChooser.APPROVE_OPTION) {
        	controller.changeWorld(chooser.getSelectedFile().getAbsolutePath());
        }
	}
	
	/**
	 * loadCharacter
	 * Use JFileChooser to import a new character image.
	 */
	/*
	private void loadCharacterOld() {
	       JFileChooser chooser = new JFileChooser(Settings.getCharactersFilePath());
	        int value = chooser.showOpenDialog(this);
	        
	        if (value == JFileChooser.APPROVE_OPTION) {
	        	controller.changeCharacter(chooser.getSelectedFile().getName());
	        }		
	        
	        map.refreshCharacterGraphic();
	}*/
	
	public void loadCharacter() {
		Dialog dialog = new Dialog();
		dialog.showCharacterSelection();
		// System.out.println(Settings.getCharactersFilePath() + Settings.getCharacters()[selection].toLowerCase() + Settings.getCharactersImageFormat());
		// controller.changeCharacter(Settings.getCharactersFilePath() + Settings.getCharacters()[selection].toLowerCase() + Settings.getCharactersImageFormat());
	}
	
	public void refreshCharacterGraphic() {
        map.refreshCharacterGraphic();
	}

	/**
	 * showError
	 * Show an error dialog with any text content.
	 * @param string contains the error message.
	 */
	public void showError(String string) {
		String message = string;
		JOptionPane.showMessageDialog(this, message, "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
		this.setVisible(true);
	}
	
	/**
	 * consoleOutput
	 * Show any text on the UI console that can be read by the player.
	 * @param text contains a string.
	 */
	public void consoleOutput(String text) {
		console.setText(text + "\n"+ console.getText());
		console.setCaretPosition(0);
	}
	
}

/**
 * Dialog
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 11.11.2014
 * Last change: 25.01.2015
 * Description: Represents the main JDialog frame, handles dialoges and other popup windows.
 */
class Dialog extends JDialog {

	private static final long serialVersionUID = -7014455567792386286L;
	
	/**
	 * contentPane
	 */
	private JPanel contentPane = new JPanel(); // Add general JPanel which includes the content of this JFrame
		
	/**
	 * Dialog
	 * Constructor, initializes the default values.
	 */
	public Dialog() {
		this.setVisible(false);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
	}
	
	private void closeDialog() {
		this.setVisible(false);
	}
	
	/**
	 * showTextArea
	 * Show a text popup with unformatted text.
	 * @param text is a string.
	 */
	public void showTextArea(String text) {	
		JTextArea textArea = new JTextArea(20, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
		contentPane.add(textArea);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(false);
		contentPane.add(scrollPane);
		textArea.append(text);
		this.pack();
		this.setLocationRelativeTo(null); // Centralize the window relative to the desktop
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setVisible(true);
	}
	
	public void showCharacterSelection() {
		this.setTitle("Choose character");
		contentPane.setLayout(new FlowLayout());
		String[] characters = Settings.getCharacters();
		JButton[] buttons = new JButton[characters.length];
		
		for (int i = 0; i < characters.length; i++) {
			URL imgURL = getClass().getResource(Settings.getCharactersFilePath() + characters[i].toLowerCase() + Settings.getCharactersImageFormat());
			JButton button = new JButton();
			button.setText(characters[i]);
			button.setIcon(new ImageIcon(imgURL));
			contentPane.add(button);
			buttons[i] = button;
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == buttons[0]) {
						Settings.getController().changeCharacter(Settings.getCharacters()[0], Settings.getCharactersFilePath() + characters[0].toLowerCase() + Settings.getCharactersImageFormat());
					} else if (e.getSource() == buttons[1]) {
						Settings.getController().changeCharacter(Settings.getCharacters()[1], Settings.getCharactersFilePath() + characters[1].toLowerCase() + Settings.getCharactersImageFormat());
					}
					closeDialog();
				}
			});
		}
		this.pack();
		this.setLocationRelativeTo(null); // Centralize the window relative to the desktop
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setVisible(true);
	}
	
	/**
	 * showQuestionDialog
	 * Open a dialog where the player has to answer a question.
	 * @param room object that contains the question & answer.
	 * @return true/false, depending on the player's answer.
	 */
	public Boolean showQuestionDialog(MagicRoom room) {
		Boolean cancel = false;
		Boolean answered = false;
		String question = room.getQuestion();
		String answer = room.getAnswer();
		String convertedAnswer = answer.toLowerCase();
		
		do {
			String dialog = JOptionPane.showInputDialog(this,question,
							"Magic room",
							JOptionPane.PLAIN_MESSAGE);
			this.setVisible(true);
			
			if (dialog != null) {
				if (dialog.toLowerCase().equals(convertedAnswer)) {
			        setVisible(false);
					cancel = true;
					answered = true;
				} else {
					int input = JOptionPane.showConfirmDialog(this,
		                    	"Wrong answer! Do you want to try it again?",
		                    	"Magic room",
		                    	JOptionPane.YES_NO_OPTION,
		                    	JOptionPane.QUESTION_MESSAGE);
					if (input == 0) {
						cancel = false;
						continue;
					} else {
				        setVisible(false);
						cancel = true;
						answered = false;
					}
				}
			} else {
		        setVisible(false);
				cancel = true;
			}

		} while (cancel == false);
		
		return answered;
	}
}