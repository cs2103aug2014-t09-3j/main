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
import javax.swing.JTextPane;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;


public class EzGUI extends JFrame {
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_WIDTH = 160;
	private static final Color SHOW_AREA_BACKGROUND = new Color(224, 255, 255);
	private static final Color BUTTON_TEXT_COLOR = new Color(0, 0, 0);
	private static final String BUTTON_FONT = "Courier New";
	private static final Color UNSELECTED_BUTTON_BG_COLOR = new Color(192, 192, 192);
	private static final Color BACKGROUND_COLOR = new Color(0, 100, 0);
	private static final int APP_HEIGHT = 640;
	private static final int APP_WIDTH = 960;
	private static final int START_LOCATION_Y = 50;
	private static final int START_LOCATION_X = 50;
	
	
	private JPanel contentPane;
	private Calendar cal;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 5));
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
		
		JButton btnToday = new JButton("Today");
		btnToday.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		btnToday.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		
		goToNextDay();
		JButton btnTomorrow = new JButton("Tomorrow");
		btnTomorrow.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		btnTomorrow.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		
		goToNextDay();
		JButton btnDay2 = new JButton(getDate());
		btnDay2.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		btnDay2.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		
		goToNextDay();
		JButton btnDay3 = new JButton(getDate());
		btnDay3.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		btnDay3.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		
		goToNextDay();
		JButton btnDay4 = new JButton(getDate());
		btnDay4.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		btnDay4.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		
		goToNextDay();
		JButton btnDay5 = new JButton(getDate());
		btnDay5.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		btnDay5.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		
		goToNextDay();
		JButton btnDay6 = new JButton(getDate());
		btnDay6.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		btnDay6.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		
		goToNextDay();
		JButton btnDay7 = new JButton(getDate());
		btnDay7.setFont(new Font(BUTTON_FONT, Font.BOLD, 16));
		btnDay7.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
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
	 * 
	 */
	private void goToNextDay() {
		cal.roll(Calendar.DATE, true);
	}

	/**
	 * create show panel
	 */
	private void createShowPanel() {
		JPanel showPanel = new JPanel();
		showPanel.setBorder(new LineBorder(BUTTON_TEXT_COLOR, 2, true));
		contentPane.add(showPanel, BorderLayout.CENTER);
		showPanel.setLayout(new BorderLayout(0, 0));
		
		JTextPane showArea = new JTextPane();
		showArea.setBackground(SHOW_AREA_BACKGROUND);
		showPanel.add(showArea);
		showArea.setEditable(false);
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
		commandFieldpanel.setBorder(new LineBorder(BUTTON_TEXT_COLOR, 2));
		commandFieldpanel.setLayout(new BoxLayout(commandFieldpanel, BoxLayout.X_AXIS));
		
		JTextPane commandField = new JTextPane();
		commandFieldpanel.add(commandField);
		commandField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
					commandField.setText("");
					arg0.consume();
				}
			}
		});
		commandField.setFont(new Font(BUTTON_FONT, Font.PLAIN, 17));
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

