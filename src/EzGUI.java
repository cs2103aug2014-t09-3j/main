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
	private static final Color MAIN_TITLE_FONT_COLOR = new Color(231,76,60);
	private static final String MAIN_TITLE_FONT_FONT = "Arial";
	
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_WIDTH = 156;
	private static final Color SHOW_AREA_BACKGROUND = EzConstants.WHITE_SMOKE_COLOR;
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
	
	private static final String[] KEYWORDS = {"add","on","at","from","to","today","tomorrow"}; 
	
	private static final String[] LIST_OF_BUTTON_NAMES = {	"All", 
															"Done", 
															"Undone", 
															"Today",
															"Tomorrow",
															"This Week",
															"Next Week",
															"This Month",
															"Next Month",
															"No Date",
															"Help"};
	
	private JPanel contentPane;
	private Calendar cal;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static JEditorPane showArea;
	private JButton selectedButton = null;
	private JTextPane commandField;
	private SimpleAttributeSet[] commandAttributeSet = new SimpleAttributeSet[3];
	/**
	 * Create the frame.
	 */
	public EzGUI() {
		setTitle("EzTask");
		initiateCalendar();
		createMainPanel();
		createButtonPanel();
		createShowPanel();
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
	private void initiateCalendar() {
		cal = Calendar.getInstance();
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
		contentPane = new JPanel();
		contentPane.setFocusable(false);
		contentPane.setBackground(BACKGROUND_COLOR);
		contentPane.setBorder(new EmptyBorder(10,10,10,10));
		contentPane.setLayout(new BorderLayout(0, 10));
		setContentPane(contentPane);
	}

	/**
	 * create button panel
	 */
	private void createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(BACKGROUND_COLOR);
		buttonPanel.setBorder(null);
		buttonPanel.setFocusable(false);
		contentPane.add(buttonPanel, BorderLayout.WEST);
		
		ArrayList<JButton> listOfButtons = new ArrayList<JButton>();
		for(int i=0;i<LIST_OF_BUTTON_NAMES.length;i++){
			JButton button = new JButton(LIST_OF_BUTTON_NAMES[i]);
			initButton(button);
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
		sqGroup.addGap(10);
		for(int i=0;i<LIST_OF_BUTTON_NAMES.length;i++){
			sqGroup.addComponent(listOfButtons.get(i), GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED);
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
	private void initButton(final JButton button) {
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
				button.setBackground(SELECTED_BUTTON_BG_COLOR);
				selectedButton = button;
				commandField.requestFocus();
			}
		});
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
	private void createShowPanel() {
		showArea = new JEditorPane();
		showArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && (e.getKeyCode()==KeyEvent.VK_Z)){
					showArea.setText("Ctrl+Z Pressed.");
				}
			}
		});
		showArea.setBackground(SHOW_AREA_BACKGROUND);
		showArea.setEditable(false);
		showArea.setContentType("text/html");
		showArea.setFocusable(false);
		
		JScrollPane showPanel = new JScrollPane(showArea);
		showPanel.setFocusable(false);
		showPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		showPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//showPanel.setBorder(new LineBorder(BUTTON_TEXT_COLOR, 2, true));
		contentPane.add(showPanel, BorderLayout.CENTER);
		showPanel.setLayout(new ScrollPaneLayout());
		showPanel.setBorder(null);
	}
	
	/**
	 * create command panel
	 */
	private void createCommandPanel() {
		JPanel commandPanel = new JPanel();
		commandPanel.setBackground(BACKGROUND_COLOR);
		commandPanel.setBorder(null);
		commandPanel.setFocusable(false);
		contentPane.add(commandPanel, BorderLayout.SOUTH);
		commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.X_AXIS));

		createCommandLabel(commandPanel);
		createCommandInputField(commandPanel);
	}

	/**
	 * @param commandPanel
	 */
	private void createCommandInputField(JPanel commandPanel) {
		JPanel commandFieldpanel = new JPanel();
		commandPanel.add(commandFieldpanel);
		//commandFieldpanel.setBorder(new LineBorder(BUTTON_TEXT_COLOR, 2));
		commandFieldpanel.setLayout(new BoxLayout(commandFieldpanel, BoxLayout.X_AXIS));
		
		commandField = new JTextPane();
		commandField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				//commandField.grabFocus();
			}
		});
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
				} else {
					if (arg0.getKeyChar()==KeyEvent.VK_ENTER){
						EzController.execute(commandField.getText());
						commandField.setText("");
					}
					if ((arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE) 
							|| (arg0.getKeyChar()==KeyEvent.VK_DELETE)
							|| (arg0.getKeyChar()==KeyEvent.VK_ENTER)){
						arg0.consume();
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
					//showArea.setText("Ctrl+Z Pressed." + String.valueOf((int)e.getKeyChar()));
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
		commandField.setBackground(SHOW_AREA_BACKGROUND);
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
	
	public static void initiateGUI(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EzGUI frame = new EzGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
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
		
		String text = "<table border=0 cellspacing=0 cellpadding=0 width=\"100%\"><tr><td height=\"44px\">";
		text = text + EzHtmlGenerator.createHtmlText("__",MAIN_TITLE_FONT_FONT,2,SHOW_AREA_BACKGROUND);
		text = text + EzHtmlGenerator.createHtmlText(header,MAIN_TITLE_FONT_FONT, 8, MAIN_TITLE_FONT_COLOR);
		text = text +"</td></tr><tr><td>" + EzHtmlGenerator.right(content) +"</td></tr></table>";
		showArea.setText(text);
	}
}
