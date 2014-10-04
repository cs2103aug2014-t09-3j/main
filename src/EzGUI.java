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
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
//import javax.swing.border.LineBorder;
import javax.swing.ScrollPaneConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class EzGUI extends JFrame {
	private static final Color FERN_COLOR = new Color(97,189,109);
	private static final Color CHATEAU_GREEN_COLOR = new Color(65,168,95);
	
	private static final Color MOUNTAIN_MEADOW_COLOR = new Color(26,188,156);
	private static final Color PERSIAN_GREEN_COLOR = new Color(0,168,133);
	
	private static final Color PICTION_BLUE_COLOR = new Color(84,172,210);
	private static final Color CURIOUS_BLUE_COLOR = new Color(61,142,185);
	
	private static final Color MARINER_COLOR = new Color(44,130,201);
	private static final Color DENIM_COLOR = new Color(41,105,176);
	
	private static final Color WISTERIA_COLOR = new Color(147,101,184);
	private static final Color BLUE_GEM_COLOR = new Color(85,57,130);
	
	private static final Color CHAMBRAY_COLOR = new Color(71,85,119);
	private static final Color BLUE_WHALE_COLOR = new Color(40,50,78);
	
	private static final Color ENERGY_COLOR = new Color(247,218,100);
	private static final Color TURBO_COLOR = new Color(250,197,28);
	
	private static final Color NEON_CARROT_COLOR = new Color(251,160,38);
	private static final Color SUN_COLOR = new Color(243,121,52);
	
	private static final Color TERRA_COTTA_COLOR = new Color(235,107,86);
	private static final Color VALENCIA_COLOR = new Color(209,72,65);
	
	private static final Color CINNABAR_COLOR = new Color(225,73,56);
	private static final Color WELL_READ_COLOR = new Color(184,49,47);
	
	private static final Color ALMOND_FROST_COLOR = new Color(163,143,132);
	private static final Color IRON_GRAY_COLOR = new Color(117,112,107);
	
	private static final Color WHITE_SMOKE_COLOR = new Color(239,239,239);
	private static final Color IRON_COLOR = new Color(209,213,216);
	
	
	private static final int STAR_PER_LINE = 5;
	private static final String TITLE_FONT_FONT = "Arial";
	private static final int TITLE_FONT_SIZE = 5;
	private static final Color TITLE_FONT_COLOR = WHITE_SMOKE_COLOR;
	
	private static final String VENUE_FONT_FONT = "Arial Rounded MT Bold";
	private static final int VENUE_FONT_SIZE = 4;
	private static final Color VENUE_FONT_COLOR = WHITE_SMOKE_COLOR;
	
	private static final String ID_FONT_FONT = "Arial Rounded MT Bold";
	private static final int ID_FONT_SIZE = 4;
	private static final Color ID_FONT_COLOR = WHITE_SMOKE_COLOR;
	
	private static final String IMAGE_CALENDAR_PNG = "image/calendar.png";
	private static final String IMAGE_CLOCK_PNG = "image/clock.png";
	//private static final Color[] TASK_BG_COLOR = {new Color(84,172,210) //LightBlue
	//												,new Color(61, 142, 185)}; //Blue
	private static final Color[] TASK_BG_COLOR = {FERN_COLOR,CHATEAU_GREEN_COLOR};
	private static final Color[] ID_BG_COLOR = {WISTERIA_COLOR,BLUE_GEM_COLOR};
	
	private static final Color CALENDAR_DATE_FONT_COLOR = new Color(231,76,60);
	private static final Color MAIN_TITLE_FONT_COLOR = new Color(231,76,60);
	
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_WIDTH = 156;
	private static final Color SHOW_AREA_BACKGROUND = WHITE_SMOKE_COLOR;
	private static final Color BUTTON_TEXT_COLOR = PERSIAN_GREEN_COLOR;
	private static final String BUTTON_FONT = TITLE_FONT_FONT;
	private static final Color SELECTED_BUTTON_BG_COLOR = WHITE_SMOKE_COLOR;
	private static final Color UNSELECTED_BUTTON_BG_COLOR = IRON_COLOR;
	private static final String COMMAND_FIELD_FONT = "Arial";
	private static final int COMMAND_FIELD_SIZE = 17;
	
	private static final Color BACKGROUND_COLOR = CHATEAU_GREEN_COLOR;
	private static final int APP_HEIGHT = 640;
	private static final int APP_WIDTH = 960;
	private static final int START_LOCATION_Y = 50;
	private static final int START_LOCATION_X = 50;
	
	private static final String[] CALENDAR_MONTH = {"Jan","Feb","March","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	private static final String[] KEYWORDS = {"add","on","at","from","to","today","tomorrow"}; 
	
	private JPanel contentPane;
	private Calendar cal;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static JEditorPane showArea;
	private JButton selectedButton = null;
	private JTextPane commandField;
	private SimpleAttributeSet[] commandAttributeSet = new SimpleAttributeSet[3];
	private int count = 0;
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
		
		JButton btnAll = new JButton("All");
		initButton(btnAll);
		
		JButton btnDone = new JButton("Done");
		initButton(btnDone);
		
		JButton btnUndone = new JButton("Undone");
		initButton(btnUndone);
		
		JButton btnToday = new JButton("Today");
		initButton(btnToday);
		
		JButton btnTomorrow = new JButton("Tomorrow");
		initButton(btnTomorrow);
		
		JButton btnThisWeek = new JButton("This Week");
		initButton(btnThisWeek);
		
		JButton btnNextWeek = new JButton("Next Week");
		initButton(btnNextWeek);
		
		JButton btnThisMonth = new JButton("This Month");
		initButton(btnThisMonth);
		
		JButton btnNextMonth = new JButton("Next Month");
		initButton(btnNextMonth);
		
		JButton btnNoDate = new JButton("No Date");
		initButton(btnNoDate);
		
		JButton btnHelp = new JButton("Help");
		initButton(btnHelp);
		
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
						.addComponent(btnThisWeek	, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNextWeek	, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnThisMonth	, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNextMonth	, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNoDate		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnHelp		, GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)))
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
					.addComponent(btnThisWeek	, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNextWeek	, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnThisMonth	, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNextMonth	, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNoDate		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnHelp		, GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT, GroupLayout.PREFERRED_SIZE)
					.addGap(0))
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
		
		EzTask task = new EzTask("meet Mary",5);
		task.setId(1);
		task.setStartTime(2014, 4, 28, 21, 00);
		task.setEndTimeAsStartTime();
		task.setDone(true);
		
		EzTask task2 = new EzTask("watch dancing on the mooooooooooooon","cathay cinema",3);
		task2.setId(2);
		task2.setDone(false);
		task2.setStartTime(2014, 4, 28, 21, 00);
		task2.setEndTimeAsStartTime();
		
		EzTask task3 = new EzTask("play badminton","pgp",2);
		task3.setId(3);
		task3.setStartTime(2014, 9, 28, 14, 0);
		task3.setEndTime(2014, 9, 28, 17, 0);
		task3.setDone(true);
		
		EzTask task3_1 = new EzTask("watch the begin is the end","cathay cinema",1);
		task3_1.setId(4);
		task3_1.setStartTime(2014, 9, 30, 14, 0);
		task3_1.setEndTime(2014, 9, 30, 17, 0);
		
		EzTask task4 = new EzTask("do homework",6);
		task4.setId(5);
		task4.setDone(false);
		
		ArrayList<EzTask> listOfTasks = new ArrayList<EzTask>();
		listOfTasks.add(task);
		listOfTasks.add(task2);
		listOfTasks.add(task3);
		listOfTasks.add(task3_1);
		listOfTasks.add(task4);
		listOfTasks.add(task);
		listOfTasks.add(task2);
		listOfTasks.add(task3);
		listOfTasks.add(task3_1);
		listOfTasks.add(task4);
		
		showContent("All tasks",listOfTasks);
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
	
	
	private static String createHtmlEzTask(EzTask task,int type){
		if (task!=null){
			return 	"<table border=0 cellspacing=0 cellpadding=1 bgcolor=\"#" + convertColorToHex(TASK_BG_COLOR[type]) + "\" width=\"560px\"><tr>"
					+ "<td width=\"53px\" bgcolor=\"#" + convertColorToHex(ID_BG_COLOR[type]) +"\">" + createHtmlIdAndPriorityOfEzTask(task) + "</td>"
					+ "<td width=\"5px\"></td>"
					+ "<td width=\"300px\">"  + createHtmlTitleAndVenueOfEzTask(task) + "</td>" 
					+ "<td width=\"15px\"></td>"
					+ "<td align=\"left\" >"  + createHtmlDateOfEzTask(task) + "</td>"
					+ "<td align=\"right\" width=\"40px\">"  + createHtmlDoneOfEzTask(task) + "</td>" 
					+ "</tr></table>";
		} else {
			return "";
		}
	}
	
	private static String createHtmlDoneOfEzTask(EzTask task) {
		if (task.isDone()){
			return createHtmlImg("image/done.png");
		}
		return "";
	}

	/*private String createHtmlTitleVenueAndDateOfEzTask(EzTask task) {
		return "<table ><tr><td>" + createHtmlTitleAndVenueOfEzTask(task)
				+ "</td></tr><tr><td align=\"right\">" + createHtmlDateOfEzTask(task) 
				+ "</td></tr></table>";
	}*/

	private static String createHtmlDateOfEzTask(EzTask task) {
		GregorianCalendar date1 = task.getStartTime();
		GregorianCalendar date2 = task.getEndTime();
		ArrayList<String> list = new ArrayList<String>();
		
		if (date1==date2) {
			if (date1==null){
				return "";
			} else {
				list.add(createHtmlCalendar(date1));
				if ((date1.get(Calendar.HOUR_OF_DAY)!=0) || (date1.get(Calendar.MINUTE)!=0)){
					list.add(createHtmlClock(date1));
				}
			}
		} else {
			list.add(createHtmlCalendar(date1));
			if (((date1.get(Calendar.HOUR_OF_DAY)!=0) || (date1.get(Calendar.MINUTE)!=0)) 
					|| ((date2.get(Calendar.HOUR_OF_DAY)!=0) || (date2.get(Calendar.MINUTE)!=0))){
				list.add(createHtmlClock(date1));
			}
			list.add(createHtmlImg("image/rightArrow.png"));
			if ((date1.get(Calendar.YEAR) != date2.get(Calendar.YEAR))
					|| (date1.get(Calendar.MONTH) != date2.get(Calendar.MONTH))
					|| (date1.get(Calendar.DATE) != date2.get(Calendar.DATE))){
				list.add(createHtmlCalendar(date2));
			}
			if ((date2.get(Calendar.HOUR_OF_DAY)!=0) || (date2.get(Calendar.MINUTE)!=0)){
				list.add(createHtmlClock(date2));
			}
		}
		return createHtmlTable(1,list.size(),list,"border=0 cellspacing=0 cellpadding=1");
	}
	
	private static String createHtmlTable(int row, int col, ArrayList<String> list, String tableAttribute){
		String result = "<table "+tableAttribute+">";
		for(int i=0;i<row;i++){
			result = result + "<tr>";
			for(int j=0;j<col;j++){
				result = result + "<td>";
				if (i*col+j<list.size()) {
					result = result + list.get(i*col+j);
				}
				result = result + "</td>";
			}
			result = result + "</tr>";
		}
		return result + "</table>";
	}
	
	private static String createHtmlTitleAndVenueOfEzTask(EzTask task) {
		String result = createHtmlText(task.getTitle(),TITLE_FONT_FONT,TITLE_FONT_SIZE, TITLE_FONT_COLOR); 
		if (task.getVenue()!=null){
			result = result + "<br>"+right(createHtmlText("@"+task.getVenue(),VENUE_FONT_FONT,VENUE_FONT_SIZE , VENUE_FONT_COLOR));
		}
		return result;
	}

	private static String createHtmlIdAndPriorityOfEzTask(EzTask task){
		String htmlId = createHtmlText("#"+task.getId(), ID_FONT_FONT, ID_FONT_SIZE, ID_FONT_COLOR);
		String htmlPriority = createHtmlStar(task.getPriority());
		return "<table width=\"48px\"><tr><td>" + center(htmlId) + "</td></tr>"
				+ "<tr><td>"+ center(htmlPriority) + "</td></tr></table>";
	}
	
	private static String createHtmlCalendar(GregorianCalendar date){
		String monthHtmlText = createHtmlText(CALENDAR_MONTH[date.get(Calendar.MONTH)],"Arial Rounded MT Bold",2,new Color(255,255,255));
		String dateHtmlText = createHtmlText(String.valueOf(date.get(Calendar.DATE)),"Arial",6,CALENDAR_DATE_FONT_COLOR);
		
		return "<table background=\"file:" + IMAGE_CALENDAR_PNG +"\" border=0 cellspacing=0 cellpadding=0 width=\"38px\">"
		+ "<tr><td height=\"10px\">" + center(monthHtmlText) + "</td></tr>" 
		+ "<tr><td height=\"27px\">" + center(dateHtmlText) + "</td></tr></table>";
	}
	
	private static String createHtmlClock(GregorianCalendar date){
		String hour = String.valueOf(date.get(Calendar.HOUR_OF_DAY));
		if (date.get(Calendar.HOUR_OF_DAY)<10) {
			hour = "0" + hour;
		}
		
		String minute = String.valueOf(date.get(Calendar.MINUTE));
		if (date.get(Calendar.MINUTE)<10) {
			minute = "0" + minute;
		}
		
		String timeHtmlText = createHtmlText(hour+":"+minute,"Digital Dismay",5,"38e204");
		return "<table background=\"file:" + IMAGE_CLOCK_PNG +"\" border=0 cellspacing=0 cellpadding=0 width=\"40px\">"
		+ "<tr><td width=\"2px\"></td><td height=\"25px\">" + center(timeHtmlText) + "</td></tr></table>";
	}
	
	private static String createHtmlImg(String url){
		return "<img src=\"file:"+ url + "\">";
	}
	
	private static String createHtmlStar(int numStar){
		String result = "";
		String chosenStar = "";
		switch (numStar){
		case 1:
			chosenStar = createHtmlImg("image/star100.png");
			break;
		case 2:
			chosenStar = createHtmlImg("image/star080.png");
			break;
		case 3:
			chosenStar = createHtmlImg("image/star060.png");
			break;
		case 4:
			chosenStar = createHtmlImg("image/star040.png");
			break;
		case 5:
			chosenStar = createHtmlImg("image/star020.png");
			break;
		default:
			chosenStar = createHtmlImg("image/star000.png");
			break;
		}
		for(int i=0;i<numStar;i++) {
			result = result + chosenStar;
			if (i%STAR_PER_LINE==STAR_PER_LINE-1) {
				result = result + "<br>";
			}
		}
		return result;
	}
	
	private static String center(String content){
		return "<center>" + content + "</center>";
	}
	
	private static String right(String content){
		return "<div align=\"right\">" + content + "</div>";
	}
	
	private static String convertColorToHex(Color color){
		return convertIntToHex(color.getRed(),2) + convertIntToHex(color.getGreen(),2) + convertIntToHex(color.getBlue(),2);
	}
	
	private static String createHtmlText(final String content, final String font, final int size, final Color color) {
		return "<font face=\"" + font +
				"\" size=\"" + size +
				"\" color=\"#" + convertColorToHex(color) + "\">" + 
				content + "</font>";
	}
	
	private static String createHtmlText(String content, String font, int size, String hexColor) {
		return "<font face=\"" + font +
				"\" size=\"" + size +
				"\" color=\"#" + hexColor + "\">" + 
				content + "</font>";
	}
	
	private static String convertIntToHex(int i, int length){
		if (length == 0) return "";
		if ((0<=i) && (i<=9)) {
			return convertIntToHex(i/16,length-1) + (char)('0'+i);
		}
		else if (i<16){
			return convertIntToHex(i/16,length-1) + (char)('a'+i-10);
		} else {
			return convertIntToHex(i/16,length-1) + convertIntToHex(i%16,length-1);
		}
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
				if (arg0.getKeyChar()==KeyEvent.VK_ENTER){
					EzController.execute(commandField.getText());
					commandField.setText("");
					arg0.consume();
				}
				if (arg0.getKeyChar()==KeyEvent.VK_BACK_SPACE){
					arg0.consume();
				}
				if (arg0.getKeyChar()==KeyEvent.VK_DELETE){
					arg0.consume();
				}
				if (arg0.isControlDown()){
					switch (arg0.getKeyChar()){
					case 22:
						arg0.consume();
						break;
					case 25:
						arg0.consume();
						break;
					case 26:
						arg0.consume();
						break;
						
					default:
						break;
					}
				}
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
						int endPos = commandField.getSelectionEnd();
						if (commandField.getSelectionStart() == commandField.getSelectionEnd()){
							if (commandField.getSelectionEnd()<commandField.getText().length()){
								//showArea.setText(String.valueOf(commandField.getSelectionStart()) + " " + String.valueOf(commandField.getSelectionEnd()));
								contentInputField = commandField.getText().substring(0, commandField.getSelectionEnd())+
										commandField.getText().substring(commandField.getSelectionEnd()+1,commandField.getText().length());
								addColorForCommandField(contentInputField, commandField.getStyledDocument());
								
								commandField.setCaretPosition(endPos);
								
							}
						} else {
							endPos = commandField.getSelectionStart();
							contentInputField = commandField.getText().substring(0, commandField.getSelectionStart())+
									commandField.getText().substring(commandField.getSelectionEnd(),commandField.getText().length());
							addColorForCommandField(contentInputField, commandField.getStyledDocument());
							commandField.setCaretPosition(endPos);
						}
						e.consume();
						break;
					case KeyEvent.VK_BACK_SPACE:
						int startPos = commandField.getSelectionStart();
						if (commandField.getSelectionStart() == commandField.getSelectionEnd()){
							if (commandField.getSelectionStart()>0){
								//showArea.setText(String.valueOf(commandField.getSelectionStart()) + " " + String.valueOf(commandField.getSelectionEnd()));
								
								if ((commandField.getText().charAt(commandField.getSelectionStart()-1) == '\"') 
									&& (commandField.getSelectionStart()<commandField.getText().length()) 
									&& (commandField.getText().charAt(commandField.getSelectionStart()) == '\"')){
									contentInputField = commandField.getText().substring(0, commandField.getSelectionStart()-1)+
											commandField.getText().substring(commandField.getSelectionStart()+1,commandField.getText().length());
								} else {
									contentInputField = commandField.getText().substring(0, commandField.getSelectionStart()-1)+
											commandField.getText().substring(commandField.getSelectionStart(),commandField.getText().length());
								}
								addColorForCommandField(contentInputField, commandField.getStyledDocument());
								
								commandField.setCaretPosition(startPos-1);
								
							}
						} else {
							contentInputField = commandField.getText().substring(0, commandField.getSelectionStart())+
									commandField.getText().substring(commandField.getSelectionEnd(),commandField.getText().length());
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
						if (lastWord.equalsIgnoreCase("add") || lastWord.equalsIgnoreCase("at")){
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
        StyleConstants.setForeground(commandAttributeSet[1], MARINER_COLOR);
        
        commandAttributeSet[2] = new SimpleAttributeSet(commandAttributeSet[0]);
        StyleConstants.setForeground(commandAttributeSet[2], IRON_GRAY_COLOR);   
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
		commandLabel.setForeground(new Color(255, 255, 255));
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
	
	public static void showContent(String header, ArrayList<EzTask> listOfTasks){
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<listOfTasks.size();i++){
			list.add(createHtmlEzTask(listOfTasks.get(i),i%2));
		}
		String content = createHtmlTable(list.size(),1,list,"border=0 cellspacing=4 cellpadding=1");
		
		showArea.setText(
				"<table border=0 cellspacing=0 cellpadding=0 width=\"100%\"><tr><td height=\"44px\">"+ createHtmlText("__",TITLE_FONT_FONT,2,SHOW_AREA_BACKGROUND) + createHtmlText(header,TITLE_FONT_FONT,8,MAIN_TITLE_FONT_COLOR) +"</td></tr>"
				//+ "<tr height=\"0px\"></tr>"
				+ "<tr><td>" + right(content) +"</td></tr></table>");
	}
}
