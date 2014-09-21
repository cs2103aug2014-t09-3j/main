/**
 * 
 */

/**
 * @author Khanh
 *
 */
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneLayout;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
//import javax.swing.border.LineBorder;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class EzGUI extends JFrame {
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_WIDTH = 156;
	private static final Color SHOW_AREA_BACKGROUND = new Color(239, 239, 239);
	private static final Color BUTTON_TEXT_COLOR = new Color(0, 168, 133);
	private static final String BUTTON_FONT = "Courier New";
	private static final Color SELECTED_BUTTON_BG_COLOR = new Color(239, 239, 239);
	private static final Color UNSELECTED_BUTTON_BG_COLOR = new Color(209, 213, 216);
	
	private static final Color BACKGROUND_COLOR = new Color(65, 168, 95);
	private static final int APP_HEIGHT = 640;
	private static final int APP_WIDTH = 960;
	private static final int START_LOCATION_Y = 50;
	private static final int START_LOCATION_X = 50;
	
	
	private JPanel contentPane;
	private Calendar cal;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private JEditorPane showArea;
	private JButton selectedButton = null;
	private JEditorPane commandField;
	/**
	 * Create the frame.
	 */
	public EzGUI() {
		initiateCalendar();
		createMainPanel();
		createButtonPanel();
		createCommandPanel();
		createShowPanel();
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
		contentPane.add(buttonPanel, BorderLayout.WEST);
		
		JButton btnAll = new JButton("All");
		initButton(btnAll);
		
		JButton btnDone = new JButton("Done");
		initButton(btnDone);
		
		JButton btnUndone = new JButton("Undone");
		initButton(btnUndone);
		
		JButton btnToday = new JButton("Today");
		initButton(btnToday);
		
		goToNextDay();
		JButton btnTomorrow = new JButton("Tomorrow");
		initButton(btnTomorrow);
		
		goToNextDay();
		JButton btnDay2 = new JButton(getDate());
		initButton(btnDay2);
		
		goToNextDay();
		JButton btnDay3 = new JButton(getDate());
		initButton(btnDay3);
		
		goToNextDay();
		JButton btnDay4 = new JButton(getDate());
		initButton(btnDay4);
		
		goToNextDay();
		JButton btnDay5 = new JButton(getDate());
		initButton(btnDay5);
		
		goToNextDay();
		JButton btnDay6 = new JButton(getDate());
		initButton(btnDay6);
		
		goToNextDay();
		JButton btnDay7 = new JButton(getDate());
		initButton(btnDay7);
		
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAll		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDone		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUndone		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnToday		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnTomorrow	, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDay2		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDay3		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDay4		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDay5		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDay6		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDay7		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)))
		);
		
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(10)
					.addComponent(btnAll		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDone		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnUndone		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnToday		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTomorrow	, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDay2		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDay3		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDay4		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDay5		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDay6		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDay7		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addGap(0))
		);
		buttonPanel.setLayout(gl_buttonPanel);
	}

	/**
	 * @param btnAll
	 */
	private void initButton(JButton button) {
		button.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		button.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		button.setBorderPainted(false);
		button.setForeground(BUTTON_TEXT_COLOR);
		button.setFocusPainted(false);
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
		showArea.setBackground(SHOW_AREA_BACKGROUND);
		showArea.setEditable(false);
		//showArea.setContentType("text/html");
		showArea.setText("<i>My First Heading</i><b>My first paragraph.</b>");
		showArea.setFocusable(false);
		
		JScrollPane showPanel = new JScrollPane(showArea);
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
		
		commandField = new JEditorPane();
		commandFieldpanel.add(commandField);
		commandField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
					showArea.setText(removeHTML(commandField.getText()));
					commandField.setText("");
					arg0.consume();
					
				}
				try{
					//if (commandField.getText().length()>1) commandField.setCaretPosition(1);
				} catch (Exception e){
					
				}
				
			}
		});
		commandField.setContentType("text/html");
		commandField.setFont(new Font(BUTTON_FONT, Font.PLAIN, 17));
		commandField.setBackground(SHOW_AREA_BACKGROUND);
		commandField.setText("<i>aaaaaaa</i>aaaaa");
		commandField.setPreferredSize(new Dimension(0,0));
	}

	private String removeHTML(String text) {
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
				if (symbolName.equals("quot;")){
					result = result + "\"";
				} else if (symbolName.equals("amp;")){
					result = result + "&";
				} else if (symbolName.equals("lt;")){
					result = result + "<";
				} else if (symbolName.equals("gt;")){
					result = result + ">";
				}	
				break;
			case '\n':
				break;
			default:
				result = result + text.charAt(i);
				break;
			}
		}
		return result.trim();
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
		commandPanel.add(commandLabelPanel);
		
		JTextPane commandLabel = new JTextPane();
		commandLabel.setEditable(false);
		commandLabel.setForeground(new Color(255, 255, 255));
		commandLabel.setFont(new Font(BUTTON_FONT, Font.BOLD, 17));
		commandLabel.setBackground(BACKGROUND_COLOR);
		commandLabel.setText(" Enter Command:");
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
	
	public static void showContent(String content){
		
	}
}

