/**
 * 
 */

/**
 * @author Khanh
 *
 */
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import javax.swing.GroupLayout.*;
import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class EzGUI extends JFrame {
	private final static Logger LOGGER = Logger.getLogger(EzGUI.class.getName());
	
	private static final String PROGRAM_TITLE = "EzTask";
	
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_WIDTH = 156;
	private static final Color BUTTON_TEXT_COLOR = EzConstants.PERSIAN_GREEN_COLOR;
	private static final String BUTTON_FONT = "Arial";
	private static final Color SELECTED_BUTTON_BG_COLOR = EzConstants.WHITE_SMOKE_COLOR;
	private static final Color UNSELECTED_BUTTON_BG_COLOR = EzConstants.IRON_COLOR;
	private static final String COMMAND_FIELD_FONT = "Arial";
	private static final int COMMAND_FIELD_SIZE = 17;
	
	private static final Color BACKGROUND_COLOR = EzConstants.CHATEAU_GREEN_COLOR;
	private static final int APP_HEIGHT = 640;
	private static final int APP_WIDTH = 960;
	private static final int START_LOCATION_Y = 0;
	private static final int START_LOCATION_X = 50;
	
	private static final String[] KEYWORDS = {"add","delete","update","show","done","undone","undo","redo","on","at","from","to","today","tomorrow"
												,"set","title","date","time","start","end","venue","priority","all","have","help","y","n"}; 
	
	private static final String[] DOUBLE_QUOTE_KEYWORDS = {"add","at","title","venue"}; 
	
	private static final String[] LIST_OF_BUTTON_NAMES = {	"All", 
															"Done", 
															"Not Done", 
															"Today",
															"Tomorrow",
															"Coming",
															"Past",
															"No Date",
															"Help"};
	
	private JPanel mainPanel;
	private static JScrollPane displayPanel;
	private static JEditorPane displayArea;
	private JButton selectedButton = null;
	private JTextPane commandField;
	private SimpleAttributeSet[] commandAttributeSet = new SimpleAttributeSet[3];
	private ArrayList<String> commandHistory = new ArrayList<String>();
	private int historyPos = 0;
	JDialog suggestPanel;
	private JEditorPane editorPane;
	private static ArrayList<JButton> listOfButtons;
	/**
	 * Create the frame.
	 */
	public EzGUI() {
		setTitle(PROGRAM_TITLE);
		createMainPanel();
		createButtonPanel();
		createDisplayPanel();
		createCommandPanel();
		createSuggestPanel();
		registerFont();
		try {
			EzController.loadFromFile();
			LOGGER.log(Level.INFO,"Loaded file successfully");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING,"Data file not found");
			e.printStackTrace();
		}
	}

	private void createSuggestPanel() {
		suggestPanel = new JDialog(this, "Suggest", false);

		int x = this.getLocation().x + 167;
		int y = this.getLocation().y + this.getHeight() - 10;
		suggestPanel.setLocation(x, y);
		suggestPanel.setMinimumSize(new Dimension(779,0));
		editorPane = new JEditorPane();
		
		suggestPanel.getContentPane().add(editorPane, BorderLayout.NORTH);
		
		suggestPanel.setUndecorated(true);
		suggestPanel.pack();
		suggestPanel.setFocusableWindowState(false);
		suggestPanel.setVisible(false);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				JFrame frame = (JFrame) arg0.getSource();
				
				int x = frame.getLocation().x + 167;
				int y = frame.getLocation().y + frame.getHeight() - 10;
				suggestPanel.setLocation(x, y);
			      
			}
		});
	}

	/**
	 * Create main panel
	 */
	private void createMainPanel() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(START_LOCATION_X, START_LOCATION_Y, APP_WIDTH, APP_HEIGHT);
		mainPanel = new JPanel();
		mainPanel.setFocusable(false);
		mainPanel.setBackground(BACKGROUND_COLOR);
		mainPanel.setBorder(new EmptyBorder(10,10,10,10));
		mainPanel.setLayout(new BorderLayout(0, 10));
		setContentPane(mainPanel);
		LOGGER.log(Level.INFO, "Created Main Panel");
	}

	/**
	 * create button panel
	 */
	private void createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(BACKGROUND_COLOR);
		buttonPanel.setBorder(null);
		buttonPanel.setFocusable(false);
		mainPanel.add(buttonPanel, BorderLayout.WEST);
		
		listOfButtons = new ArrayList<JButton>();
		for(int i=0;i<LIST_OF_BUTTON_NAMES.length;i++){
			JButton button = initButton(LIST_OF_BUTTON_NAMES[i]);
			listOfButtons.add(button);
		}
		
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		ParallelGroup prGroup = gl_buttonPanel.createParallelGroup(Alignment.LEADING);
		for(int i=0;i<LIST_OF_BUTTON_NAMES.length;i++){
			prGroup.addComponent(listOfButtons.get(i),GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE);
		}
		
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(prGroup))
		);
		
		SequentialGroup sqGroup = gl_buttonPanel.createSequentialGroup();
		sqGroup.addGap(60);
		for(int i=0;i<LIST_OF_BUTTON_NAMES.length;i++){
			sqGroup.addComponent(listOfButtons.get(i), GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addGap(7);
		}
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(sqGroup)
		);
		buttonPanel.setLayout(gl_buttonPanel);
		
		LOGGER.log(Level.INFO, "Created Button Panel");
	}

	/**
	 * @param btnAll
	 */
	private JButton initButton(String nameOfButton) {
		assert(nameOfButton!=null);
		
		JButton button = new JButton(nameOfButton);
		button.setName(nameOfButton);
		
		button.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		button.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		button.setBorderPainted(false);
		button.setForeground(BUTTON_TEXT_COLOR);
		button.setFocusPainted(false);
		button.setFocusable(false);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (selectedButton!=null){
					selectedButton.setBackground(UNSELECTED_BUTTON_BG_COLOR);
				}
				JButton button = (JButton) arg0.getSource();
				button.setBackground(SELECTED_BUTTON_BG_COLOR);
				selectedButton = button;
				
				EzStorage storage = EzController.getStorage();
				assert(storage!=null);
				
				if (button.getName().equalsIgnoreCase("All")){
					showContent("All tasks", EzSort.sortById(storage.getListOfAllTasks()));
				} else if (button.getName().equalsIgnoreCase("Done")){
					showContent("Done tasks", EzSort.sortById(storage.getDoneTasks()));
				} else if (button.getName().equalsIgnoreCase("Not done")){
					showContent("Not Done tasks", EzSort.sortById(storage.getUndoneTasks()));
				} else if (button.getName().equalsIgnoreCase("Today")){
					showContent("Today tasks", EzSort.sortById(storage.getTasksByDate(getToday())));
				} else if (button.getName().equalsIgnoreCase("Tomorrow")){
					showContent("Tomorrow tasks", EzSort.sortById(storage.getTasksByDate(getTomorrow())));
				} else if (button.getName().equalsIgnoreCase("Coming")){
					//showContent("Coming tasks", EzSort.sortById(storage.getComingTasks()));
				} else if (button.getName().equalsIgnoreCase("Past")){
					showContent("Past tasks", EzSort.sortById(storage.getPastTasks()));
				} else if (button.getName().equalsIgnoreCase("No Date")){
					showContent("No Date tasks", EzSort.sortById(storage.getNoDateTasks()));
				} else if (button.getName().equalsIgnoreCase("Help")){
					String text = readHelpDocument();
					showContent("Help - All commands", text);
				} 
				
				commandField.requestFocus();
			}

			private String readHelpDocument() {
				File file = new File("help.txt");
				assert(file!=null);
				BufferedReader in;
				String text = "";
				try {
					String line;
					in = new BufferedReader(new InputStreamReader(file.toURL().openStream()));
					while ((line = in.readLine()) != null) {
						text += line;
					}
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return text;
			}
		});
		return button;
	}

	/**
	 * create show panel
	 */
	private void createDisplayPanel() {
		displayArea = new JEditorPane();
		displayArea.setBackground(EzConstants.SHOW_AREA_BACKGROUND);
		displayArea.setEditable(false);
		displayArea.setContentType("text/html");
		displayArea.setFocusable(false);
		
		displayPanel = new JScrollPane(displayArea);
		displayPanel.setFocusable(false);
		displayPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		displayPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		displayPanel.setLayout(new ScrollPaneLayout());
		displayPanel.setBorder(null);
		
		mainPanel.add(displayPanel, BorderLayout.CENTER);
		LOGGER.log(Level.INFO, "Created Display Panel");
	}
	
	/**
	 * create command panel
	 */
	private void createCommandPanel() {
		JPanel commandPanel = new JPanel();
		commandPanel.setBackground(BACKGROUND_COLOR);
		commandPanel.setBorder(null);
		commandPanel.setFocusable(false);
		commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.X_AXIS));
		mainPanel.add(commandPanel, BorderLayout.SOUTH);
		
		createCommandLabel(commandPanel);
		createCommandInputField(commandPanel);
	}

	/**
	 * @param commandPanel
	 */
	private void createCommandInputField(JPanel commandPanel) {
		JPanel commandFieldpanel = new JPanel();
		commandFieldpanel.setLayout(new BoxLayout(commandFieldpanel, BoxLayout.X_AXIS));
		commandPanel.add(commandFieldpanel);
		
		commandField = new JTextPane();
		/*commandField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				//commandField.grabFocus();
			}
		})*/;
		commandFieldpanel.add(commandField);
		commandField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.isControlDown()){
					switch (arg0.getKeyChar()){
					case 22: case 25: case 26:
						arg0.consume();
						break;
					}
					
					switch (arg0.getKeyCode()){
					case KeyEvent.VK_UP:
						scrollUp();
						break;
					case KeyEvent.VK_DOWN:
						scrollDown();
						break;
					}
				} else {
					switch (arg0.getKeyChar()){
					case KeyEvent.VK_ENTER:
						enterCommand();
						arg0.consume();
						break;
					case KeyEvent.VK_BACK_SPACE: case KeyEvent.VK_DELETE:
						arg0.consume();
						break;
					}
					
					switch (arg0.getKeyCode()){
					case KeyEvent.VK_UP:
						goToPreviousCommand();
						break;
					case KeyEvent.VK_DOWN:
						goToNextCommand();
						break;
					}
				}
			}

			private void scrollDown() {
				displayPanel.getVerticalScrollBar().setValue(displayPanel.getVerticalScrollBar().getValue()+20);
			}

			private void scrollUp() {
				displayPanel.getVerticalScrollBar().setValue(displayPanel.getVerticalScrollBar().getValue()-20);
			}

			private void enterCommand() {
				EzController.execute(commandField.getText());
				commandHistory.add(commandField.getText());
				historyPos = commandHistory.size(); 
				commandField.setText("");
			}

			private void goToNextCommand() {
				if (historyPos<commandHistory.size()){
					historyPos++;
					if (historyPos<commandHistory.size()){
						addColorForCommandField(commandHistory.get(historyPos), commandField.getStyledDocument());
					} else {
						addColorForCommandField("", commandField.getStyledDocument());
					}
				}
			}

			private void goToPreviousCommand() {
				if (historyPos>0){
					historyPos--;
					addColorForCommandField(commandHistory.get(historyPos), commandField.getStyledDocument());
				}
			}
			
			String deleteString(String text,int startPos, int endPos){
				return text.substring(0, startPos) + text.substring(endPos);
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				int caretPos = commandField.getCaretPosition();
				if (!e.isControlDown()){
					switch (e.getKeyChar()){
					case KeyEvent.VK_ENTER:
						break;
					case KeyEvent.VK_DELETE:
						deleteSelection();
						break;
					case KeyEvent.VK_BACK_SPACE:
						backSpaceSelection();
						break;
					case KeyEvent.VK_SPACE:
						typeSpace(caretPos);
						break;			
					default:
						typeNormal(e, caretPos);
						break;	
					}
					e.consume();
				} else {
					switch (e.getKeyChar()){
					case 26:	// CTRL + Z
						undo();
						e.consume();
						break;
					case 25:	// CTRL + Y
						redo();
						e.consume();
						break;
					case 22:	// CTRL + V
						pasteText();
						e.consume();
						break;
					default:
						break;
					}
				}
			}

			private void redo() {
				EzController.execute("redo");
			}

			private void undo() {
				EzController.execute("undo");
			}

			private void pasteText() {
				int caretPos;
				commandField.paste();
				caretPos = commandField.getCaretPosition();
				addColorForCommandField(commandField.getText(), commandField.getStyledDocument());
				commandField.setCaretPosition(caretPos);
			}

			private void typeNormal(KeyEvent e, int caretPos) {
				String contentInputField;
				String typedText = "" + e.getKeyChar();
				contentInputField = commandField.getText().substring(0, caretPos) + typedText + commandField.getText().substring(caretPos, commandField.getText().length());
				addColorForCommandField(contentInputField, commandField.getStyledDocument());
				commandField.setCaretPosition(caretPos+1);
				//showArea.setText(contentInputField);
			}

			private void typeSpace(int caretPos) {
				String contentInputField;
				contentInputField = commandField.getText();
				String result = "";
				String lastWord = "";
				
				for(int i=0;i<caretPos;i++){
					//String word = "";
					if (contentInputField.charAt(i)==' '){
						result = result + " ";
						while ((i+1<caretPos) && (contentInputField.charAt(i+1)==' ')){
							i++;
							result = result + " ";
						}
					} else if (contentInputField.charAt(i)=='\"'){
						lastWord = "\"";
						while ((i+1<caretPos) && (contentInputField.charAt(i+1)!='\"')){
							i++;
							lastWord = lastWord + contentInputField.charAt(i);
						}
						if (i+1<caretPos){
							i++;
							lastWord = lastWord + contentInputField.charAt(i);
						}
						result = result + lastWord;
					} else {
						lastWord = "" + contentInputField.charAt(i);
						while ((i+1<caretPos) && (contentInputField.charAt(i+1)!=' ')){
							i++;
							lastWord = lastWord + contentInputField.charAt(i);
						}
						result = result + lastWord;
					}
				}
				if (isDoubleQuoteKeyword(lastWord)){
					result = result + " \"\"" + contentInputField.substring(caretPos, contentInputField.length());	
					addColorForCommandField(result, commandField.getStyledDocument());
					commandField.setCaretPosition(caretPos+2);
				} else {
					result = result + " " + contentInputField.substring(caretPos, contentInputField.length());	
					addColorForCommandField(result, commandField.getStyledDocument());
					commandField.setCaretPosition(caretPos+1);
				}
			}
			
			private boolean isDoubleQuoteKeyword(String word){
				for(int i=0;i<DOUBLE_QUOTE_KEYWORDS.length;i++){
					if (word.equalsIgnoreCase(DOUBLE_QUOTE_KEYWORDS[i])){
						return true;
					}
				}
				return false;
			}
			
			private void backSpaceSelection() {
				String contentInputField;
				int startPos = commandField.getSelectionStart();
				if (commandField.getSelectionStart() == commandField.getSelectionEnd()){
					if (commandField.getSelectionStart()>0){
						if ((commandField.getText().charAt(commandField.getSelectionStart()-1) == '\"') 
							&& (commandField.getSelectionStart()<commandField.getText().length()) 
							&& (commandField.getText().charAt(commandField.getSelectionStart()) == '\"')){
							contentInputField = deleteString(commandField.getText(),commandField.getSelectionStart()-1,commandField.getSelectionStart()+1); 
						} else {
							contentInputField = deleteString(commandField.getText(),commandField.getSelectionStart()-1,commandField.getSelectionStart()); 
						}
						addColorForCommandField(contentInputField, commandField.getStyledDocument());
						commandField.setCaretPosition(startPos-1);
					}
				} else {
					contentInputField = deleteString(commandField.getText(),commandField.getSelectionStart(),commandField.getSelectionEnd()); 
					addColorForCommandField(contentInputField, commandField.getStyledDocument());
					commandField.setCaretPosition(startPos);
				}
			}

			private void deleteSelection() {
				String contentInputField;
				int endPos = commandField.getSelectionStart();
				if (commandField.getSelectionStart() == commandField.getSelectionEnd()){
					if (commandField.getSelectionStart()<commandField.getText().length()){
						contentInputField = deleteString(commandField.getText(),commandField.getSelectionStart(),commandField.getSelectionEnd()+1); 
						addColorForCommandField(contentInputField, commandField.getStyledDocument());
						commandField.setCaretPosition(endPos);
					}
				} else {
					contentInputField = deleteString(commandField.getText(),commandField.getSelectionStart(),commandField.getSelectionEnd()); 
					addColorForCommandField(contentInputField, commandField.getStyledDocument());
					commandField.setCaretPosition(endPos);
				}
			}

			
		});
		loadCommandAttributeSet();
		//commandField.setContentType("text/html");
		//commandField.setFont(new Font(BUTTON_FONT, Font.PLAIN, 17));
		commandField.setBackground(EzConstants.SHOW_AREA_BACKGROUND);
		commandField.setPreferredSize(new Dimension(0,0));
		commandField.grabFocus();
	}
	
	/**
	 * @param contentInputField
	 * @param doc
	 */
	private void addColorForCommandField(String contentInputField,
			StyledDocument doc) {
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e2) {
			e2.printStackTrace();
		}
		for(int i=0;i<contentInputField.length();i++){
			String word = "";
			if (contentInputField.charAt(i)==' '){
				word = " ";
				while ((i+1<contentInputField.length()) && (contentInputField.charAt(i+1)==' ')){
					i++;
					word = word + ' ';
				}
				try {
					doc.insertString(doc.getLength(), word, commandAttributeSet[0]);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			} else if (contentInputField.charAt(i)=='\"'){
				word = "\"";
				while ((i+1<contentInputField.length()) && (contentInputField.charAt(i+1)!='\"')){
					i++;
					word = word + contentInputField.charAt(i);
				}
				if (i+1<contentInputField.length()){
					i++;
					word = word + contentInputField.charAt(i);
				}
				try {
					doc.insertString(doc.getLength(), word, commandAttributeSet[2]);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			} else {
				word = "" + contentInputField.charAt(i);
				while ((i+1<contentInputField.length()) && (contentInputField.charAt(i+1)!=' ')){
					i++;
					word = word + contentInputField.charAt(i);
				}
				if (isKeyword(word)){
					try {
						doc.insertString(doc.getLength(), word, commandAttributeSet[1]);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						doc.insertString(doc.getLength(), word, commandAttributeSet[0]);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	private void loadCommandAttributeSet() {
		commandAttributeSet[0] = new SimpleAttributeSet();
		StyleConstants.setFontFamily(commandAttributeSet[0], COMMAND_FIELD_FONT);
        StyleConstants.setFontSize(commandAttributeSet[0], COMMAND_FIELD_SIZE);

        commandAttributeSet[1] = new SimpleAttributeSet(commandAttributeSet[0]);
        StyleConstants.setForeground(commandAttributeSet[1], EzConstants.MARINER_COLOR);
        
        commandAttributeSet[2] = new SimpleAttributeSet(commandAttributeSet[0]);
        StyleConstants.setForeground(commandAttributeSet[2], EzConstants.IRON_GRAY_COLOR);   
	}
	
	private boolean isKeyword(String word) {
		for(int i=0;i<KEYWORDS.length;i++){
			if (KEYWORDS[i].equalsIgnoreCase(word)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param commandPanel
	 */
	private void createCommandLabel(JPanel commandPanel) {
		JPanel commandLabelPanel = new JPanel();
		FlowLayout fl_commandLabelPanel = (FlowLayout) commandLabelPanel.getLayout();
		fl_commandLabelPanel.setVgap(0);
		fl_commandLabelPanel.setHgap(0);
		commandLabelPanel.setBackground(BACKGROUND_COLOR);
		commandLabelPanel.setFocusable(false);
		commandPanel.add(commandLabelPanel);
		
		JTextPane commandLabel = new JTextPane();
		commandLabel.setEditable(false);
		commandLabel.setForeground(Color.WHITE);
		commandLabel.setFont(new Font(BUTTON_FONT, Font.BOLD, 17));
		commandLabel.setBackground(BACKGROUND_COLOR);
		commandLabel.setText("  Enter Command: ");
		commandLabel.setFocusable(false);
		commandLabelPanel.add(commandLabel);
	}
	
	private static void registerFont(){
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Digital Dismay.otf")).deriveFont(16f);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			LOGGER.log(Level.INFO, "Registered Font Digital Dismay Successfully");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Font Digital Dismay Not Found");
		}
		
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/ARLRDBD.TTF")).deriveFont(16f);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			LOGGER.log(Level.INFO, "Registered Font ARLRDBD Successfully");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Font ARLRDBD Not Found");
		}
		
	}
	
	public static void showContent(String header, ArrayList<EzTask> listOfTasks){
		assert(listOfTasks!=null);
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<listOfTasks.size();i++){
			list.add(EzHtmlGenerator.createHtmlEzTask(listOfTasks.get(i),i%2));
		}
		String content = EzHtmlGenerator.createHtmlTable(list.size(),1,list,"border=0 cellspacing=4 cellpadding=1");
		showContent(header, content);
	}
	
	public static void showContent(String header, String content){
		String text = EzHtmlGenerator.createHtmlTableWithHeader(header, content, "border=0 cellspacing=0 cellpadding=0 width=\"100%\"");
		displayArea.setText(text);
		displayArea.setCaretPosition(0);
		refreshButton();
		
	}

	private static Date getToday(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	private static Date getTomorrow(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	/**
	 * 	"All", 
		"Done", 
		"Undone", 
		"Today",
		"Tomorrow",
		"Coming",
		"Past",
		"No Date",
		"Help"
	 */
	private static void refreshButton() {
		EzStorage storage = EzController.getStorage();
		for(int i=0;i < listOfButtons.size();i++){
			JButton button = listOfButtons.get(i);
			int numTask = 0;
			if (button.getName().equalsIgnoreCase("All")){
				numTask = storage.getListOfAllTasks().size();
			} else if (button.getName().equalsIgnoreCase("Done")){
				numTask = storage.getDoneTasks().size();
			} else if (button.getName().equalsIgnoreCase("Not Done")){
				numTask = storage.getUndoneTasks().size();
			} else if (button.getName().equalsIgnoreCase("Today")){
				numTask = storage.getTasksByDate(getToday()).size();
			} else if (button.getName().equalsIgnoreCase("Tomorrow")){
				numTask = storage.getTasksByDate(getTomorrow()).size();
			} else if (button.getName().equalsIgnoreCase("Coming")){
				numTask = 0;//storage.getComingTasks().size();
			} else if (button.getName().equalsIgnoreCase("Past")){
				numTask = storage.getPastTasks().size();
			} else if (button.getName().equalsIgnoreCase("No Date")){
				numTask = storage.getNoDateTasks().size();
			}
			if (!button.getName().equalsIgnoreCase("Help")){
				button.setText(button.getName() + "....[ " + String.valueOf(numTask) + " ]");
			}
		}
	}
}
