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
	
	private static final Color BACKGROUND_COLOR = CHATEAU_GREEN_COLOR;
	private static final int APP_HEIGHT = 640;
	private static final int APP_WIDTH = 960;
	private static final int START_LOCATION_Y = 50;
	private static final int START_LOCATION_X = 50;
	
	private static final String[] CALENDAR_MONTH = {"Jan","Feb","March","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	
	
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
		showArea.setContentType("text/html");
		EzTask task = new EzTask("meet Mary",5);
		task.setId(1);
		task.setStartTime(2014, 4, 28, 21, 00);
		task.setEndTimeAsStartTime();
		task.setDone(true);
		
		EzTask task2 = new EzTask("watch dancing on the moon","cathay cinema",3);
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
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(createHtmlEzTask(task,0));
		list.add(createHtmlEzTask(task2,1));
		list.add(createHtmlEzTask(task3,0));
		list.add(createHtmlEzTask(task3_1,1));
		list.add(createHtmlEzTask(task4,0));
		
		showArea.setText(createMainTable("All tasks",createHtmlTable(list.size(),1,list,"border=0 cellspacing=4 cellpadding=1")));
		//showArea.setFocusable(false);
		
		JScrollPane showPanel = new JScrollPane(showArea);
		showPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		showPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//showPanel.setBorder(new LineBorder(BUTTON_TEXT_COLOR, 2, true));
		contentPane.add(showPanel, BorderLayout.CENTER);
		showPanel.setLayout(new ScrollPaneLayout());
		showPanel.setBorder(null);
	}
	
	private String createMainTable(String title, String content){
		return "<table border=0 cellspacing=0 cellpadding=0 width=\"100%\"><tr><td height=\"44px\">"+ createHtmlText("__",TITLE_FONT_FONT,2,SHOW_AREA_BACKGROUND) + createHtmlText(title,TITLE_FONT_FONT,8,MAIN_TITLE_FONT_COLOR) +"</td></tr>"
				//+ "<tr height=\"0px\"></tr>"
				+ "<tr><td>" + right(content) +"</td></tr></table>";
	}
	
	private String createHtmlEzTask(EzTask task,int type){
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
	
	private String createHtmlDoneOfEzTask(EzTask task) {
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

	private String createHtmlDateOfEzTask(EzTask task) {
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
	
	private String createHtmlTable(int row, int col, ArrayList<String> list, String tableAttribute){
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
	
	private String createHtmlTitleAndVenueOfEzTask(EzTask task) {
		String result = createHtmlText(task.getTitle(),TITLE_FONT_FONT,TITLE_FONT_SIZE, TITLE_FONT_COLOR); 
		if (task.getVenue()!=null){
			result = result + "<br>"+right(createHtmlText("@"+task.getVenue(),VENUE_FONT_FONT,VENUE_FONT_SIZE , VENUE_FONT_COLOR));
		}
		return result;
	}

	private String createHtmlIdAndPriorityOfEzTask(EzTask task){
		String htmlId = createHtmlText("#"+task.getId(), ID_FONT_FONT, ID_FONT_SIZE, ID_FONT_COLOR);
		String htmlPriority = createHtmlStar(task.getPriority());
		return "<table width=\"48px\"><tr><td>" + center(htmlId) + "</td></tr>"
				+ "<tr><td>"+ center(htmlPriority) + "</td></tr></table>";
	}
	
	private String createHtmlCalendar(GregorianCalendar date){
		String monthHtmlText = createHtmlText(CALENDAR_MONTH[date.get(Calendar.MONTH)-1],"Arial Rounded MT Bold",2,new Color(255,255,255));
		String dateHtmlText = createHtmlText(String.valueOf(date.get(Calendar.DATE)),"Arial",6,CALENDAR_DATE_FONT_COLOR);
		
		return "<table background=\"file:" + IMAGE_CALENDAR_PNG +"\" border=0 cellspacing=0 cellpadding=0 width=\"38px\">"
		+ "<tr><td height=\"10px\">" + center(monthHtmlText) + "</td></tr>" 
		+ "<tr><td height=\"27px\">" + center(dateHtmlText) + "</td></tr></table>";
	}
	
	private String createHtmlClock(GregorianCalendar date){
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
	
	private String createHtmlImg(String url){
		return "<img src=\"file:"+ url + "\">";
	}
	
	private String createHtmlStar(int numStar){
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
	
	private String center(String content){
		return "<center>" + content + "</center>";
	}
	
	private String right(String content){
		return "<div align=\"right\">" + content + "</div>";
	}
	
	private String convertColorToHex(Color color){
		return convertIntToHex(color.getRed(),2) + convertIntToHex(color.getGreen(),2) + convertIntToHex(color.getBlue(),2);
	}
	
	private String createHtmlText(final String content, final String font, final int size, final Color color) {
		return "<font face=\"" + font +
				"\" size=\"" + size +
				"\" color=\"#" + convertColorToHex(color) + "\">" + 
				content + "</font>";
	}
	
	private String createHtmlText(String content, String font, int size,
			String hexColor) {
		return "<font face=\"" + font +
				"\" size=\"" + size +
				"\" color=\"#" + hexColor + "\">" + 
				content + "</font>";
	}
	
	private String convertIntToHex(int i, int length){
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
		commandLabel.setText("  Enter Command: ");
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

