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

import java.io.File;
import java.io.IOException;


public class EzGUI extends JFrame {
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
	private static final int START_LOCATION_Y = 50;
	private static final int START_LOCATION_X = 50;
	
	private static final String[] KEYWORDS = {"add","delete","update","show","done","undone","undo","redo","on","at","from","to","today","tomorrow"
												,"set","title","date","time","start","end","venue","priority","all","have","help","y","n"}; 
	
	private static final String[] LIST_OF_BUTTON_NAMES = {	"All", 
															"Done", 
															"Undone", 
															"Today",
															"Tomorrow",
															"Coming",
															"Past",
															"No Date",
															"Help"};
	
	private JPanel mainPanel;
	private JScrollPane displayPanel;
	private Calendar cal = Calendar.getInstance();
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static JEditorPane displayArea;
	private JButton selectedButton = null;
	private JTextPane commandField;
	private SimpleAttributeSet[] commandAttributeSet = new SimpleAttributeSet[3];
	private ArrayList<String> commandHistory = new ArrayList<String>();
	private int historyPos = 0;
	/**
	 * Create the frame.
	 */
	public EzGUI() {
		setTitle(PROGRAM_TITLE);
		createMainPanel();
		createButtonPanel();
		createDisplayPanel();
		createCommandPanel();
		registerFont();
		try {
			EzController.loadFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private String getDate() {
		return dateFormat.format(cal.getTime());
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
		
		ArrayList<JButton> listOfButtons = new ArrayList<JButton>();
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
	}

	/**
	 * @param btnAll
	 */
	private JButton initButton(String nameOfButton) {
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
				
				if (button.getName().equalsIgnoreCase("All")){
					// ADD your code here
					
				} else if (button.getName().equalsIgnoreCase("Done")){
					
				} else if (button.getName().equalsIgnoreCase("Undone")){
					
				} else if (button.getName().equalsIgnoreCase("Today")){
					
				} else if (button.getName().equalsIgnoreCase("Tomorrow")){
					
				} else if (button.getName().equalsIgnoreCase("Coming")){
					
				} else if (button.getName().equalsIgnoreCase("Past")){
					
				} else if (button.getName().equalsIgnoreCase("No Date")){
					
				} else if (button.getName().equalsIgnoreCase("Help")){
					
				} 
				
				commandField.requestFocus();
			}
		});
		return button;
	}

	/**
	 * 
	 */
	private void goToNextDay() {
		cal.roll(Calendar.DATE, true);
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
						
					default:
						break;
					}
					
					switch (arg0.getKeyCode()){
					case KeyEvent.VK_UP:
						displayPanel.getVerticalScrollBar().setValue(displayPanel.getVerticalScrollBar().getValue()-20);;
						//showArea.setText("Ctrl+Up Pressed." + String.valueOf((int)arg0.getKeyCode()));
						break;
					case KeyEvent.VK_DOWN:
						displayPanel.getVerticalScrollBar().setValue(displayPanel.getVerticalScrollBar().getValue()+20);;
						//showArea.setText("Ctrl+Down Pressed." + String.valueOf((int)arg0.getKeyCode()));
						break;

					}
				} else {
					switch (arg0.getKeyChar()){
					case KeyEvent.VK_ENTER:
						EzController.execute(commandField.getText());
						commandHistory.add(commandField.getText());
						historyPos = commandHistory.size(); 
						commandField.setText("");
						arg0.consume();
						break;
					case KeyEvent.VK_BACK_SPACE:
					case KeyEvent.VK_DELETE:
						arg0.consume();
						break;
					default:
						break;
					}
					
					switch (arg0.getKeyCode()){
					case KeyEvent.VK_UP:
						if (historyPos>0){
							historyPos--;
							addColorForCommandField(commandHistory.get(historyPos), commandField.getStyledDocument());
						}
						//showArea.setText("Up Pressed." + String.valueOf((int)arg0.getKeyCode()));
						break;
					case KeyEvent.VK_DOWN:
						if (historyPos<commandHistory.size()){
							historyPos++;
							if (historyPos<commandHistory.size()){
								addColorForCommandField(commandHistory.get(historyPos), commandField.getStyledDocument());
							} else {
								addColorForCommandField("", commandField.getStyledDocument());
							}
						}
						//showArea.setText("Down Pressed." + String.valueOf((int)arg0.getKeyCode()));
						break;

					}
					
				}
			}
			
			String deleteString(String text,int startPos, int endPos){
				return text.substring(0, startPos) + text.substring(endPos);
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				int caretPos = commandField.getCaretPosition();
				String contentInputField;
				if (!e.isControlDown()){
					switch (e.getKeyChar()){
					case KeyEvent.VK_ENTER:
						e.consume();
						break;
					case KeyEvent.VK_DELETE:
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
						e.consume();
						break;
					case KeyEvent.VK_BACK_SPACE:
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
						e.consume();
						break;
					case KeyEvent.VK_SPACE:
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
						if (lastWord.equalsIgnoreCase("add") 
								|| lastWord.equalsIgnoreCase("at") 
								|| lastWord.equalsIgnoreCase("title")
								|| lastWord.equalsIgnoreCase("venue")){
							result = result + " \"\"" + contentInputField.substring(caretPos, contentInputField.length());	
							addColorForCommandField(result, commandField.getStyledDocument());
							commandField.setCaretPosition(caretPos+2);
						} else {
							result = result + " " + contentInputField.substring(caretPos, contentInputField.length());	
							addColorForCommandField(result, commandField.getStyledDocument());
							commandField.setCaretPosition(caretPos+1);
						}
						e.consume();
						break;
						
					default:
						String typedText = "" + e.getKeyChar();
						contentInputField = commandField.getText().substring(0, caretPos) + typedText + commandField.getText().substring(caretPos, commandField.getText().length());
						addColorForCommandField(contentInputField, commandField.getStyledDocument());
						commandField.setCaretPosition(caretPos+1);
						//showArea.setText(contentInputField);
						e.consume();
						break;	
					}
				} else {
					switch (e.getKeyChar()){
					case 26:	// CTRL + Z
						EzController.execute("undo");
						e.consume();
						break;
					case 25:	// CTRL + Y
						EzController.execute("redo");
						e.consume();
						break;
					
					case 22:	// CTRL + V
						commandField.paste();
						caretPos = commandField.getCaretPosition();
						addColorForCommandField(commandField.getText(), commandField.getStyledDocument());
						commandField.setCaretPosition(caretPos);
						e.consume();
						break;
					
					default:
						break;
					}
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

	/*private String removeHtml(String text) {
		String result="";
		for(int i=0;i<text.length();i++){
			switch(text.charAt(i)){
			case '<':
				while ((i<text.length()) && (text.charAt(i)!='>')) i++;
				break;
			case '&':
				String symbolName = "";
				while ((i<text.length()) && (text.charAt(i)!=';')) {
					i++;
					symbolName = symbolName + text.charAt(i);
				}
				/*if (symbolName.equals("quot;")){
					result = result + "\"";
				} else if (symbolName.equals("amp;")){
					result = result + "&";
				} else if (symbolName.equals("lt;")){
					result = result + "<";
				} else if (symbolName.equals("gt;")){
					result = result + ">";
				} else if (symbolName.equals("nbsp;")){
					result = result + " ";
				}
				if (symbolName.equals("#160;")){
					result = result + "&nbsp;";
				} 
				break;
			case '\n':
				break;
			case 13:
				break;
			default:
				result = result + text.charAt(i);
				break;
			}
		}
		//return result;
		if (result.length()>16){
			return result.substring(14, result.length()-2);
		} else {
			return "";
		}
	}*/

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
		} catch (Exception e) {
		}
		
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/ARLRDBD.TTF")).deriveFont(16f);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
		} catch (Exception e) {
		}
	}
	
	public static void showContent(String header, ArrayList<EzTask> listOfTasks){
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
	}
}
