import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.logging.*;
import java.io.*;

//@author A0112129U

/**
 * this class is the GUI of the program
 */
public class EzGUI extends JFrame {

	private static final String ICON_FILENAME = "/icon.png";
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

	/**
	 * initiate the GUI, it will read the available data file
	 */
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

	/**
	 * initiate the GUI with a choice to have a new file or not
	 */
	public EzGUI(boolean newFile) {
		initMainFrame();
		createMainPanel();
		createDisplayPanel();
		createCommandPanel();
		createButtonPanel();
		registerFont();
		if (!newFile) {
			loadFile();
		}
		setDefaultButton("Today");
		createSuggestPanel();
		setIcon();
	}

	/**
	 * assigned values to the main frame
	 */
	private void initMainFrame() {
		mainFrame = this;
		setTitle(PROGRAM_TITLE);
		setResizable(true);
		setMinimumSize(new Dimension(APP_WIDTH, APP_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(START_LOCATION_X, START_LOCATION_Y, APP_WIDTH, APP_HEIGHT);
	}

	/**
	 * return the main frame;
	 */
	public static JFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * return the window's location
	 */
	public static Point getMainFrameLocation() {
		return mainFrame.getLocation();
	}

	/**
	 * set the location of the window
	 */
	public static void setMainFrameLocation(int x, int y) {
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

	/**
	 * add the font to the program
	 */
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

	/**
	 * load the external file
	 */
	private void loadFile() {
		try {
			EzController.loadFromFile();
			LOGGER.log(Level.INFO, "Loaded file successfully");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Data file not found");
			e.printStackTrace();
		}
	}

	/**
	 * press the button
	 * 
	 * @param buttonName
	 *            is the name of the button
	 */
	private void setDefaultButton(String buttonName) {
		buttonPanel.pressButton(buttonName);
	}

	/**
	 * set the icon for the program
	 */
	private void setIcon() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				EzGUI.class.getResource(ICON_FILENAME)));
	}

	/**
	 * create suggest panel
	 */
	private void createSuggestPanel() {
		suggestPanel = EzGUISuggestPanel.getInstance();

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				JFrame frame = (JFrame) arg0.getSource();

				int x = frame.getLocation().x
						+ EzGUISuggestPanel.SUGGEST_PANEL_X_RELATIVE_POS;
				int y = frame.getLocation().y + frame.getHeight()
						- EzGUISuggestPanel.SUGGEST_PANEL_Y_RELATIVE_POS;
				suggestPanel.setLocation(x, y);
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				JFrame frame = (JFrame) arg0.getSource();

				int x = frame.getLocation().x
						+ EzGUISuggestPanel.SUGGEST_PANEL_X_RELATIVE_POS;
				int y = frame.getLocation().y + frame.getHeight()
						- EzGUISuggestPanel.SUGGEST_PANEL_Y_RELATIVE_POS;
				suggestPanel.setPreferredSize(new Dimension(frame.getWidth()
						- (APP_WIDTH - EzGUISuggestPanel.ORIGINAL_WIDTH),
						EzGUISuggestPanel.ORIGINAL_HEIGHT));
				suggestPanel.setLocation(x, y);
				suggestPanel.loadSuggestion(EzGUICommandPanel.getInstance()
						.getText());
			}
		});
	}

	/**
	 * show the reminder if you have tasks to be done on today
	 */
	public void showReminder() {
		GregorianCalendar today = new GregorianCalendar();
		ArrayList<EzTask> list = EzController.getStorage().getTasksByDate(
				today.getTime());
		int numTasksTodayToDo = 0;
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).isDone()) {
				numTasksTodayToDo++;
			}
		}

		if (numTasksTodayToDo > 0) {
			JOptionPane.showMessageDialog(this, "You have " + numTasksTodayToDo
					+ " task(s) that need to be done today");
		}
	}

	/**
	 * scroll the main display up
	 */
	public static void scrollUp() {
		displayPanel.scrollUp();
	}

	/**
	 * scroll the main display down
	 */
	public static void scrollDown() {
		displayPanel.scrollDown();
	}

	/**
	 * read the content of the help file
	 * 
	 * @return the content of the help file in HTML format
	 */
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

	/**
	 * change the window's size
	 * 
	 * @param x
	 *            is the increment of the width
	 * @param y
	 *            is the increment of the height
	 */
	public static void increaseWindowSize(int x, int y) {
		int width = mainFrame.getWidth();
		int height = mainFrame.getHeight();
		mainFrame.setSize(width + x, height + y);
	}

	/**
	 * show the help page
	 */
	public static void showHelp() {
		String text = readHelpDocument();
		onScreenTasks = null;
		showContent("Help - All commands", text);
	}

	/**
	 * get the tasks are shown on the screen
	 * 
	 * @return the tasks are shown on the screen
	 */
	public static ArrayList<EzTask> getTasksOnScreen() {
		return onScreenTasks;
	}

	/**
	 * get the current page
	 * 
	 * @return the current page
	 */
	public static int getPage() {
		return pageToShow;
	}

	/**
	 * get the header
	 * 
	 * @return the header
	 */
	public static String getHeader() {
		return headerToShow;
	}

	/**
	 * show the list of task with the header. Showing the first page is default.
	 * 
	 * @param header
	 * @param listOfTasks
	 */
	public static void showContent(String header, ArrayList<EzTask> listOfTasks) {
		showContent(header, listOfTasks, 1);
	}

	/**
	 * show the list of task with the header. Showing the page the contains the
	 * task
	 * 
	 * @param header
	 * @param listOfTasks
	 * @param task
	 */
	public static void showContent(String header,
			ArrayList<EzTask> listOfTasks, EzTask task) {
		assert (listOfTasks != null);
		onScreenTasks = listOfTasks;
		headerToShow = new String(header);

		showPage(findPage(task));
		EzGUIButtonPanel.getInstance().refreshButton();
	}

	/**
	 * show the list of task with the header.
	 * 
	 * @param header
	 * @param listOfTasks
	 * @param page
	 */
	public static void showContent(String header,
			ArrayList<EzTask> listOfTasks, int page) {
		assert (listOfTasks != null);
		onScreenTasks = listOfTasks;
		headerToShow = new String(header);

		showPage(page);
		EzGUIButtonPanel.getInstance().refreshButton();
	}

	/**
	 * find the page that contains the task
	 */
	public static int findPage(EzTask task) {
		int id = -1;
		if (onScreenTasks != null) {
			for (int i = 0; i < onScreenTasks.size(); i++) {
				if (task.getId() == onScreenTasks.get(i).getId()) {
					id = i;
				}
			}
		}
		if (id != -1) {
			return id / TASK_PER_PAGE + 1;
		} else {
			return -1;
		}
	}

	/**
	 * get the maximum page
	 * 
	 * @return
	 */
	public static int getMaxPage() {
		if (onScreenTasks != null) {
			int pageMaximum = onScreenTasks.size() / TASK_PER_PAGE;
			if (onScreenTasks.size() % TASK_PER_PAGE > 0) {
				pageMaximum++;
			}

			if (pageMaximum < 1) {
				pageMaximum = 1;
			}
			return pageMaximum;
		} else {
			return 1;
		}
	}

	/**
	 * show the page according to the numPage
	 * 
	 * @param numPage
	 */
	public static void showPage(int numPage) {
		if (onScreenTasks != null) {
			int pageMaximum = getMaxPage();

			if (numPage < 1) {
				numPage = 1;
			} else if (numPage > pageMaximum) {
				numPage = pageMaximum;
			}

			pageToShow = numPage;

			ArrayList<String> list = new ArrayList<String>();
			for (int i = (numPage - 1) * TASK_PER_PAGE; i < Math.min(numPage
					* TASK_PER_PAGE, onScreenTasks.size()); i++) {
				list.add(EzHtmlGenerator.createHtmlEzTask(onScreenTasks.get(i)));
			}

			String content = EzHtmlGenerator
					.center(EzHtmlGenerator.createHtmlTable(list.size(), 1,
							list,
							"border=0 cellspacing=4 cellpadding=1 width=\"100%\""));
			showContent(
					headerToShow + " (" + numPage + "/" + pageMaximum + ")",
					content);
		}
	}

	/**
	 * get the page that is being shown
	 * 
	 * @return
	 */
	public static int getPageToShow() {
		return pageToShow;
	}

	/**
	 * show the content with a header
	 * 
	 * @param header
	 * @param content
	 */
	private static void showContent(String header, String content) {
		String text = EzHtmlGenerator.createHtmlTableWithHeader(header,
				content, "border=0 cellspacing=0 cellpadding=0 width=\"100%\"");
		displayArea.setText(text);
		displayArea.setCaretPosition(0);

		EzGUIButtonPanel.getInstance().refreshButton();
	}

	/**
	 * @return the date of today
	 */
	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	/**
	 * @return the date of tomorrow
	 */
	public static Date getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * check if @param word is the keyword or not
	 * 
	 * @param word
	 * @return
	 */
	public static boolean isKeyword(String word) {
		for (int i = 0; i < KEYWORDS.length; i++) {
			if (KEYWORDS[i].equalsIgnoreCase(word)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * highlight a button by its name
	 * 
	 * @param name
	 *            is the name of the button
	 */
	public static void highlightButton(String name) {
		EzGUIButtonPanel.getInstance().highlightButton(name);
	}

	/**
	 * make all buttons unhighlighted
	 */
	public static void unhighlightButton() {
		EzGUIButtonPanel.getInstance().unhighlightButton();
	}

	/**
	 * @return the name of the current selected tab
	 */
	public static String getCurrentTab() {
		return EzGUIButtonPanel.getInstance().getCurrentTab();
	}

	/**
	 * @param name
	 *            is the name of the tab
	 * @return the list of tasks that the tab is represented
	 */
	public static ArrayList<EzTask> getTaskListOfTheTab(String name) {
		JButton button = EzGUIButtonPanel.getInstance().getButton(name);
		return EzGUIButtonPanel.getInstance().getTaskListOfButton(button);
	}

	/**
	 * refresh the button
	 */
	public static void refreshButton() {
		EzGUIButtonPanel.getInstance().refreshButton();
	}
}
