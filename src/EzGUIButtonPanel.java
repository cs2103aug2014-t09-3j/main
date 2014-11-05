import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EzGUIButtonPanel extends JPanel {
	public final String ALL = "All";
	public final String DONE = "Done";
	public final String NOT_DONE = "Not Done";
	public final String TODAY = "Today";
	public final String TOMORROW = "Tomorrow";
	public final String UPCOMING = "Upcoming";
	public final String OVERDUE = "Overdue";
	public final String NO_DATE = "No Date";
	public final String HELP = "Help";
	
	private ArrayList<JButton> listOfButtons;
	private final String[] LIST_OF_BUTTON_NAMES = { ALL, DONE,
			NOT_DONE, TODAY, TOMORROW, UPCOMING, OVERDUE, NO_DATE,
			HELP };
	private final int BUTTON_HEIGHT = 40;
	private final int BUTTON_WIDTH = 160;
	private final Color BUTTON_TEXT_COLOR = EzConstants.PERSIAN_GREEN_COLOR;
	public final Color SELECTED_BUTTON_BG_COLOR = EzConstants.WHITE_SMOKE_COLOR;
	public final Color UNSELECTED_BUTTON_BG_COLOR = EzConstants.IRON_COLOR;
	
	private JButton selectedButton = null;
	
	static EzGUIButtonPanel buttonPanel;
	
	public static EzGUIButtonPanel getInstance(){
		if (buttonPanel==null){
			buttonPanel = new EzGUIButtonPanel();
		}
		return buttonPanel;
	}
	
	/**
	 * Create the panel.
	 */
	private EzGUIButtonPanel() {
		setBackground(EzGUI.BACKGROUND_COLOR);
		setBorder(null);
		setFocusable(false);
		
		listOfButtons = new ArrayList<JButton>();
		for (int i = 0; i < LIST_OF_BUTTON_NAMES.length; i++) {
			JButton button = initButton(LIST_OF_BUTTON_NAMES[i]);
			listOfButtons.add(button);
		}

		GroupLayout gl_buttonPanel = new GroupLayout(this);
		ParallelGroup prGroup = gl_buttonPanel
				.createParallelGroup(Alignment.LEADING);
		for (int i = 0; i < LIST_OF_BUTTON_NAMES.length; i++) {
			prGroup.addComponent(listOfButtons.get(i),
					GroupLayout.DEFAULT_SIZE, BUTTON_WIDTH,
					GroupLayout.PREFERRED_SIZE);
		}

		gl_buttonPanel.setHorizontalGroup(gl_buttonPanel.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_buttonPanel.createSequentialGroup().addGroup(prGroup)));

		SequentialGroup sqGroup = gl_buttonPanel.createSequentialGroup();
		sqGroup.addGap(60);
		for (int i = 0; i < LIST_OF_BUTTON_NAMES.length; i++) {
			sqGroup.addComponent(listOfButtons.get(i),
					GroupLayout.PREFERRED_SIZE, BUTTON_HEIGHT,
					GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED).addGap(7);
		}
		gl_buttonPanel.setVerticalGroup(gl_buttonPanel.createParallelGroup(
				Alignment.LEADING).addGroup(sqGroup));
		setLayout(gl_buttonPanel);
	}
	
	private JButton initButton(String nameOfButton) {
		assert (nameOfButton != null);

		JButton button = new JButton(nameOfButton);
		button.setName(nameOfButton);

		if (nameOfButton.equalsIgnoreCase(HELP)) {
			button.setMnemonic(KeyEvent.VK_H);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase(ALL)) {
			button.setMnemonic(KeyEvent.VK_A);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase(DONE)) {
			button.setMnemonic(KeyEvent.VK_D);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase(NOT_DONE)) {
			button.setMnemonic(KeyEvent.VK_N);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase(TODAY)) {
			button.setMnemonic(KeyEvent.VK_T);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase(TOMORROW)) {
			button.setMnemonic(KeyEvent.VK_M);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase(UPCOMING)) {
			button.setMnemonic(KeyEvent.VK_U);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase(OVERDUE)) {
			button.setMnemonic(KeyEvent.VK_P);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase(NO_DATE)) {
			button.setMnemonic(KeyEvent.VK_E);
			button.addActionListener(new ButtonAction());
		}

		button.setFont(new Font(EzGUI.BUTTON_FONT, Font.BOLD, 16));
		button.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		button.setBorderPainted(false);
		button.setForeground(BUTTON_TEXT_COLOR);
		button.setFocusPainted(false);
		button.setFocusable(false);
		return button;
	}
	
	/**
	 * @param btnAll
	 */
	class ButtonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			pressButton(button);
		}
	}
	
	public JButton getButton(String name) {
		for (int i = 0; i < listOfButtons.size(); i++) {
			if (listOfButtons.get(i).getName().equalsIgnoreCase(name)) {
				return listOfButtons.get(i);
			}
		}
		return null;
	}

	public void pressButton(String name) {
		pressButton(getButton(name));
	}
	
	public void unhighlightButton(){
		if (selectedButton != null) {
			selectedButton.setBackground(UNSELECTED_BUTTON_BG_COLOR);
			selectedButton = null;
		}
	}
	
	public String getCurrentTab(){
		if (selectedButton!=null){
			return selectedButton.getName();
		} else {
			return null;
		}
	}
	
	public ArrayList<EzTask> getTaskListOfButton(JButton button){
		EzStorage storage = EzController.getStorage();
		assert (storage != null);

		if (button.getName().equalsIgnoreCase(ALL)) {
			return EzSort.sortById(storage.getListOfAllTasks());
		} else if (button.getName().equalsIgnoreCase(DONE)) {
			return EzSort.sortById(storage.getDoneTasks());
		} else if (button.getName().equalsIgnoreCase(NOT_DONE)) {
			return EzSort.sortByDate(storage.getUndoneTasks());
		} else if (button.getName().equalsIgnoreCase(TODAY)) {
			return EzSort.sortByPriority(storage.getTasksByDate(EzGUI.getToday()));
		} else if (button.getName().equalsIgnoreCase(TOMORROW)) {
			return EzSort.sortByPriority(storage.getTasksByDate(EzGUI.getTomorrow()));
		} else if (button.getName().equalsIgnoreCase(UPCOMING)) {
			return EzSort.sortByDate(storage.getComingTasks());
		} else if (button.getName().equalsIgnoreCase(OVERDUE)) {
			return EzSort.sortByDate(storage.getOverdueTasks());
		} else if (button.getName().equalsIgnoreCase(NO_DATE)) {
			return EzSort.sortByPriority(storage.getNoDateTasks());
		}
		return null;
	}
	
	private void pressButton(JButton button) {
		paintFocusedButton(button);
		EzStorage storage = EzController.getStorage();
		assert (storage != null);

		ArrayList<EzTask> list = getTaskListOfButton(button);
		
		if (button.getName().equalsIgnoreCase(HELP)) {
			EzGUI.showHelp();
		} else {
			if (list!=null){
				EzGUI.showContent(button.getName(), list);
			}
		}

		EzGUICommandPanel.getInstance().focusOnField();
	}
	
	private void paintFocusedButton(JButton button) {
		if (selectedButton != null) {
			selectedButton.setBackground(UNSELECTED_BUTTON_BG_COLOR);
		}
		button.setBackground(SELECTED_BUTTON_BG_COLOR);
		selectedButton = button;
	}
	
	public void highlightButton(String name){
		paintFocusedButton(getButton(name));
	}
	
	public void refreshButton() {
		if (listOfButtons != null) {
			EzStorage storage = EzController.getStorage();
			for (int i = 0; i < listOfButtons.size(); i++) {
				JButton button = listOfButtons.get(i);
				int numTask = 0;
				if (button.getName().equalsIgnoreCase(ALL)) {
					numTask = storage.getListOfAllTasks().size();
				} else if (button.getName().equalsIgnoreCase(DONE)) {
					numTask = storage.getDoneTasks().size();
				} else if (button.getName().equalsIgnoreCase(NOT_DONE)) {
					numTask = storage.getUndoneTasks().size();
				} else if (button.getName().equalsIgnoreCase(TODAY)) {
					numTask = storage.getTasksByDate(EzGUI.getToday()).size();
				} else if (button.getName().equalsIgnoreCase(TOMORROW)) {
					numTask = storage.getTasksByDate(EzGUI.getTomorrow()).size();
				} else if (button.getName().equalsIgnoreCase(UPCOMING)) {
					numTask = storage.getComingTasks().size();
				} else if (button.getName().equalsIgnoreCase(OVERDUE)) {
					numTask = storage.getOverdueTasks().size();
				} else if (button.getName().equalsIgnoreCase(NO_DATE)) {
					numTask = storage.getNoDateTasks().size();
				}
				if (!button.getName().equalsIgnoreCase(HELP)) {
					button.setText(button.getName() + " ["
							+ String.valueOf(numTask) + "]");
				}
			}
		}
	}
	
	public void pressBelowButton(){
		if (selectedButton == null) {
			pressButton(ALL);
		} else if (selectedButton == getButton(ALL)) {
			pressButton(DONE);
		} else if (selectedButton == getButton(DONE)) {
			pressButton(NOT_DONE);
		} else if (selectedButton == getButton(NOT_DONE)) {
			pressButton(TODAY);
		} else if (selectedButton == getButton(TODAY)) {
			pressButton(TOMORROW);
		} else if (selectedButton == getButton(TOMORROW)) {
			pressButton(UPCOMING);
		} else if (selectedButton == getButton(UPCOMING)) {
			pressButton(OVERDUE);
		} else if (selectedButton == getButton(OVERDUE)) {
			pressButton(NO_DATE);
		} else if (selectedButton == getButton(NO_DATE)) {
			pressButton(HELP);
		} else if (selectedButton == getButton(HELP)) {
			pressButton(ALL);
		}
	}
	
	public void pressAboveButton(){
		if (selectedButton == null) {
			pressButton(getButton(HELP));
		} else if (selectedButton == getButton(ALL)) {
			pressButton(getButton(HELP));
		} else if (selectedButton == getButton(DONE)) {
			pressButton(getButton(ALL));
		} else if (selectedButton == getButton(NOT_DONE)) {
			pressButton(getButton(DONE));
		} else if (selectedButton == getButton(TODAY)) {
			pressButton(getButton(NOT_DONE));
		} else if (selectedButton == getButton(TOMORROW)) {
			pressButton(getButton(TODAY));
		} else if (selectedButton == getButton(UPCOMING)) {
			pressButton(getButton(TOMORROW));
		} else if (selectedButton == getButton(OVERDUE)) {
			pressButton(getButton(UPCOMING));
		} else if (selectedButton == getButton(NO_DATE)) {
			pressButton(getButton(OVERDUE));
		} else if (selectedButton == getButton(HELP)) {
			pressButton(getButton(NO_DATE));
		}
	}
}
