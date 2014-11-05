/**
 * @author Khanh
 *
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.logging.*;
import java.io.*;

public class EzGUI extends JFrame {
	public static final String ALL = EzGUIButtonPanel.getInstance().ALL;
	public static final String DONE = EzGUIButtonPanel.getInstance().DONE;
	public static final String NOT_DONE = EzGUIButtonPanel.getInstance().NOT_DONE;
	public static final String TODAY = EzGUIButtonPanel.getInstance().TODAY;
	public static final String TOMORROW = EzGUIButtonPanel.getInstance().TOMORROW;
	public static final String UPCOMING = EzGUIButtonPanel.getInstance().UPCOMING;
	public static final String OVERDUE = EzGUIButtonPanel.getInstance().OVERDUE;
	public static final String NO_DATE = EzGUIButtonPanel.getInstance().NO_DATE;
	public static final String HELP = EzGUIButtonPanel.getInstance().HELP;
	
	private static final String HELP_DOCUMENT_FILE_NAME = "help.txt";

	private final static Logger LOGGER = Logger
			.getLogger(EzGUI.class.getName());

	private static final String PROGRAM_TITLE = "EzTask";

	
	public static final String BUTTON_FONT = "Arial";
	
	public static final Color BACKGROUND_COLOR = EzConstants.CHATEAU_GREEN_COLOR;
	private static final int APP_HEIGHT = 640;
	private static final int APP_WIDTH = 960;
	private static final int START_LOCATION_Y = 0;
	private static final int START_LOCATION_X = 50;

	private static final int TASK_PER_PAGE = 8;
	
	private static final String[] KEYWORDS = { "add", "delete", "update",
			"show", "done", "undone", "undo", "redo", "on", "at", "from", "to",
			"today", "tomorrow", "page", "title", "date", "time", "start",
			"end", "venue", "priority", "all", "have", "help", "y", "n",
			"remove", "id", "sort" };
	
	private static JFrame mainFrame;
	private JPanel mainPanel;
	private static EzGUIDisplayPanel displayPanel;
	private static JEditorPane displayArea;
	
	private static EzGUISuggestPanel suggestPanel;
	private static ArrayList<EzTask> onScreenTasks;
	private static int pageToShow;
	private static String headerToShow;
	private static EzGUIButtonPanel buttonPanel;
	
	public EzGUI() {
		initMainFrame();
		createMainPanel();
		createDisplayPanel();
		createCommandPanel();
		createButtonPanel();
		registerFont();
		loadFile();
		setDefaultButton("Today");
		createSuggestPanel();
		setIcon();
	}

	private void initMainFrame() {
		mainFrame = this;
		setTitle(PROGRAM_TITLE);
		setResizable(true);
		setMinimumSize(new Dimension(APP_WIDTH, APP_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(START_LOCATION_X, START_LOCATION_Y, APP_WIDTH, APP_HEIGHT);
	}
	
	public static JFrame getMainFrame(){
		return mainFrame;
	}
	
	public static Point getMainFrameLocation(){
		return mainFrame.getLocation();
	}
	
	public static void setMainFrameLocation(int x, int y){
		mainFrame.setLocation(x, y);
	}
	
	/**
	 * Create main panel
	 */
	private void createMainPanel() {
		mainPanel = new EzGUIMainPanel();
		setContentPane(mainPanel);
		LOGGER.log(Level.INFO, "Created Main Panel");
	}
	
	/**
	 * create show panel
	 */
	private void createDisplayPanel() {
		displayArea = new JEditorPane();
		displayPanel = new EzGUIDisplayPanel(displayArea);
		mainPanel.add(displayPanel, BorderLayout.CENTER);
		LOGGER.log(Level.INFO, "Created Display Panel");
	}
	
	/**
	 * create command panel
	 */
	private void createCommandPanel() {
		EzGUICommandPanel commandPanel = EzGUICommandPanel.getInstance();
		mainPanel.add(commandPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * create button panel
	 */
	private void createButtonPanel() {
		buttonPanel = EzGUIButtonPanel.getInstance();
		mainPanel.add(buttonPanel, BorderLayout.WEST);
		LOGGER.log(Level.INFO, "Created Button Panel");
	}
	
	private static void registerFont() {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT,
					new File("fonts/Digital Dismay.otf")).deriveFont(16f);
			GraphicsEnvironment genv = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			LOGGER.log(Level.INFO,
					"Registered Font Digital Dismay Successfully");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Font Digital Dismay Not Found");
		}

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT,
					new File("fonts/ARLRDBD.TTF")).deriveFont(16f);
			GraphicsEnvironment genv = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			LOGGER.log(Level.INFO, "Registered Font ARLRDBD Successfully");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Font ARLRDBD Not Found");
		}

	}
	
	private void loadFile() {
		try {
			EzController.loadFromFile();
			LOGGER.log(Level.INFO, "Loaded file successfully");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Data file not found");
			e.printStackTrace();
		}
	}
	
	private void setDefaultButton(String buttonName) {
		buttonPanel.pressButton(buttonName);
	}
	
	private void setIcon() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(EzGUI.class.getResource("/icon.png")));
	}

	private void createSuggestPanel() {
		suggestPanel = EzGUISuggestPanel.getInstance();
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				JFrame frame = (JFrame) arg0.getSource();

				int x = frame.getLocation().x + 165;
				int y = frame.getLocation().y + frame.getHeight() - 10;
				suggestPanel.setLocation(x, y);
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				JFrame frame = (JFrame) arg0.getSource();

				int x = frame.getLocation().x + 165;
				int y = frame.getLocation().y + frame.getHeight() - 10;
				suggestPanel.setPreferredSize(new Dimension(frame
						.getWidth() - (960 - 784), 85));
				suggestPanel.setLocation(x, y);
				suggestPanel.loadSuggestion(EzGUICommandPanel.getInstance().getText());
			}
		});
	}
	
	public void showReminder() {
		GregorianCalendar today = new GregorianCalendar();
		ArrayList<EzTask> list = EzController.getStorage().getTasksByDate(today.getTime());
		int numTasksTodayToDo = 0;
		for(int i=0;i<list.size();i++){
			if (!list.get(i).isDone()) {
				numTasksTodayToDo++;
			}
		}
		 
		if (numTasksTodayToDo>0){
			JOptionPane.showMessageDialog(this, "You have " + numTasksTodayToDo + " task(s) that need to be done today");
		}
	}

	public static void scrollUp(){
		displayPanel.scrollUp();
	}
	
	public static void scrollDown(){
		displayPanel.scrollDown();
	}
	
	private static String readHelpDocument() {
		File file = new File(HELP_DOCUMENT_FILE_NAME);
		assert (file != null);
		BufferedReader in;
		String text = "";
		try {
			String line;
			in = new BufferedReader(new InputStreamReader(file.toURI().toURL()
					.openStream()));
			while ((line = in.readLine()) != null) {
				text += line;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	public static void increaseWindowSize(int x, int y){
		int width = mainFrame.getWidth();
		int height = mainFrame.getHeight();
		mainFrame.setSize(width + x, height + y);
	}

	public static void showHelp() {
		String text = readHelpDocument();
		onScreenTasks = null;
		showContentTop("Help - All commands", text);
	}

	public static ArrayList<EzTask> getTasksOnScreen() {
		return onScreenTasks;
	}

	public static int getPage(){
		return pageToShow;
	}
	
	public static String getHeader(){
		return headerToShow;
	}
	
	public static void showContent(String header, ArrayList<EzTask> listOfTasks) {
		assert (listOfTasks != null);
		onScreenTasks = listOfTasks;
		headerToShow = new String(header);
		
		showPage(1);
		EzGUIButtonPanel.getInstance().refreshButton();
	}

	public static void showContent(String header, ArrayList<EzTask> listOfTasks, EzTask task) {
		assert (listOfTasks != null);
		onScreenTasks = listOfTasks;
		headerToShow = new String(header);
		
		showPage(findPage(task));
		EzGUIButtonPanel.getInstance().refreshButton();
	}
	
	public static void showContent(String header, ArrayList<EzTask> listOfTasks, int page) {
		assert (listOfTasks != null);
		onScreenTasks = listOfTasks;
		headerToShow = new String(header);
		
		showPage(page);
		EzGUIButtonPanel.getInstance().refreshButton();
	}
	
	public static int findPage(EzTask task){
		int id = -1;
		if (onScreenTasks!=null){
			for(int i=0;i<onScreenTasks.size();i++){
				if (task.getId()==onScreenTasks.get(i).getId()){
					id = i; 
				}
			}
		}
		if (id!=-1){
			return id/TASK_PER_PAGE + 1;
		} else {
			return -1;
		}
	}
	
	public static int getMaxPage(){
		if (onScreenTasks!=null){
			int pageMaximum = onScreenTasks.size()/TASK_PER_PAGE;
			if (onScreenTasks.size()%TASK_PER_PAGE>0){
				pageMaximum++;
			}
			
			if (pageMaximum<1){
				pageMaximum = 1;
			}
			return pageMaximum;
		} else {
			return 1;
		}
	}
	
	public static void showPage(int numPage){
		if (onScreenTasks!=null){
			int pageMaximum = getMaxPage();
			
			if (numPage<1){
				numPage = 1;
			} else if (numPage>pageMaximum){
				numPage = pageMaximum;
			}
			
			pageToShow = numPage;
			
			ArrayList<String> list = new ArrayList<String>();
			for (int i = (numPage-1)*TASK_PER_PAGE; i < Math.min(numPage*TASK_PER_PAGE, onScreenTasks.size()); i++) {
				list.add(EzHtmlGenerator.createHtmlEzTask(onScreenTasks.get(i), i % 2));
			}
			
			String content = EzHtmlGenerator.center(EzHtmlGenerator
					.createHtmlTable(list.size(), 1, list,
							"border=0 cellspacing=4 cellpadding=1 width=\"100%\""));
			showContent(headerToShow + " (" + numPage + "/" + pageMaximum + ")", content);
		}
	}
	
	public static int getPageToShow(){
		return pageToShow;
	}
	
	private static void showContent(String header, String content) {
		String text = EzHtmlGenerator.createHtmlTableWithHeader(header,
				content, "border=0 cellspacing=0 cellpadding=0 width=\"100%\"");
		displayArea.setText(text);
		displayArea.setCaretPosition(0);
		
		EzGUIButtonPanel.getInstance().refreshButton();
	}
	
	private static void showContentTop(String header, String content) {
		String text = EzHtmlGenerator.createHtmlTableWithHeader(header,
				content, "border=0 cellspacing=0 cellpadding=0 width=\"100%\"");
		displayArea.setText(text);
		displayArea.setCaretPosition(0);
		
		EzGUIButtonPanel.getInstance().refreshButton();
	}
	
	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static Date getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static boolean isKeyword(String word) {
		for (int i = 0; i < KEYWORDS.length; i++) {
			if (KEYWORDS[i].equalsIgnoreCase(word)) {
				return true;
			}
		}
		return false;
	}
	
	public static void highlightButton(String name) {
		EzGUIButtonPanel.getInstance().highlightButton(name);
	}
	
	public static void unhighlightButton(){
		EzGUIButtonPanel.getInstance().unhighlightButton();
	}
	
	public static String getCurrentTab(){
		return EzGUIButtonPanel.getInstance().getCurrentTab();
	}
	
	public static ArrayList<EzTask> getTaskListOfTheTab(String name){
		JButton  button = EzGUIButtonPanel.getInstance().getButton(name);
		return EzGUIButtonPanel.getInstance().getTaskListOfButton(button);
	}
	
	public static void refreshButton(){
		EzGUIButtonPanel.getInstance().refreshButton();
	}
}
