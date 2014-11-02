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
	private ArrayList<JButton> listOfButtons;
	private final String[] LIST_OF_BUTTON_NAMES = { "All", "Done",
		"Not Done", "Today", "Tomorrow", "Coming", "Past", "No Date",
		"Help" };
	private final int BUTTON_HEIGHT = 40;
	private final int BUTTON_WIDTH = 160;
	private final Color BUTTON_TEXT_COLOR = EzConstants.PERSIAN_GREEN_COLOR;
	
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

		if (nameOfButton.equalsIgnoreCase("help")) {
			button.setMnemonic(KeyEvent.VK_H);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase("All")) {
			button.setMnemonic(KeyEvent.VK_A);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase("Done")) {
			button.setMnemonic(KeyEvent.VK_D);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase("Not Done")) {
			button.setMnemonic(KeyEvent.VK_N);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase("Today")) {
			button.setMnemonic(KeyEvent.VK_T);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase("Tomorrow")) {
			button.setMnemonic(KeyEvent.VK_M);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase("Coming")) {
			button.setMnemonic(KeyEvent.VK_C);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase("Past")) {
			button.setMnemonic(KeyEvent.VK_P);
			button.addActionListener(new ButtonAction());
		} else if (nameOfButton.equalsIgnoreCase("No Date")) {
			button.setMnemonic(KeyEvent.VK_E);
			button.addActionListener(new ButtonAction());
		}

		button.setFont(new Font(EzGUI.BUTTON_FONT, Font.BOLD, 16));
		button.setBackground(EzGUI.UNSELECTED_BUTTON_BG_COLOR);
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
	
	private JButton getButton(String name) {
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
	
	private void pressButton(JButton button) {
		paintFocusedButton(button);
		EzStorage storage = EzController.getStorage();
		assert (storage != null);

		if (button.getName().equalsIgnoreCase("All")) {
			EzGUI.showContent("All tasks",
					EzSort.sortById(storage.getListOfAllTasks()));
		} else if (button.getName().equalsIgnoreCase("Done")) {
			EzGUI.showContent("Done tasks", EzSort.sortById(storage.getDoneTasks()));
		} else if (button.getName().equalsIgnoreCase("Not done")) {
			EzGUI.showContent("Not Done tasks",
					EzSort.sortByDate(storage.getUndoneTasks()));
		} else if (button.getName().equalsIgnoreCase("Today")) {
			EzGUI.showContent("Today tasks",
					EzSort.sortByPriority(storage.getTasksByDate(EzGUI.getToday())));
		} else if (button.getName().equalsIgnoreCase("Tomorrow")) {
			EzGUI.showContent("Tomorrow tasks", EzSort.sortByPriority(storage
					.getTasksByDate(EzGUI.getTomorrow())));
		} else if (button.getName().equalsIgnoreCase("Coming")) {
			EzGUI.showContent("Coming tasks",
					EzSort.sortByDate(storage.getComingTasks()));
		} else if (button.getName().equalsIgnoreCase("Past")) {
			EzGUI.showContent("Past tasks", EzSort.sortByDate(storage.getPastTasks()));
		} else if (button.getName().equalsIgnoreCase("No Date")) {
			EzGUI.showContent("No Date tasks",
					EzSort.sortByPriority(storage.getNoDateTasks()));
		} else if (button.getName().equalsIgnoreCase("Help")) {
			EzGUI.showHelp();
		}

		EzGUICommandPanel.getInstance().focusOnField();
	}
	
	private void paintFocusedButton(JButton button) {
		if (selectedButton != null) {
			selectedButton.setBackground(EzGUI.UNSELECTED_BUTTON_BG_COLOR);
		}
		button.setBackground(EzGUI.SELECTED_BUTTON_BG_COLOR);
		selectedButton = button;
	}
	
	public void refreshButton() {
		if (listOfButtons != null) {
			EzStorage storage = EzController.getStorage();
			for (int i = 0; i < listOfButtons.size(); i++) {
				JButton button = listOfButtons.get(i);
				int numTask = 0;
				if (button.getName().equalsIgnoreCase("All")) {
					numTask = storage.getListOfAllTasks().size();
				} else if (button.getName().equalsIgnoreCase("Done")) {
					numTask = storage.getDoneTasks().size();
				} else if (button.getName().equalsIgnoreCase("Not Done")) {
					numTask = storage.getUndoneTasks().size();
				} else if (button.getName().equalsIgnoreCase("Today")) {
					numTask = storage.getTasksByDate(EzGUI.getToday()).size();
				} else if (button.getName().equalsIgnoreCase("Tomorrow")) {
					numTask = storage.getTasksByDate(EzGUI.getTomorrow()).size();
				} else if (button.getName().equalsIgnoreCase("Coming")) {
					numTask = storage.getComingTasks().size();
				} else if (button.getName().equalsIgnoreCase("Past")) {
					numTask = storage.getPastTasks().size();
				} else if (button.getName().equalsIgnoreCase("No Date")) {
					numTask = storage.getNoDateTasks().size();
				}
				if (!button.getName().equalsIgnoreCase("Help")) {
					button.setText(button.getName() + "....[ "
							+ String.valueOf(numTask) + " ]");
				}
			}
		}
	}
	
	public void pressBelowButton(){
		if (selectedButton == null) {
			pressButton("All");
		} else if (selectedButton == getButton("all")) {
			pressButton("Done");
		} else if (selectedButton == getButton("Done")) {
			pressButton("Not done");
		} else if (selectedButton == getButton("Not Done")) {
			pressButton("Today");
		} else if (selectedButton == getButton("Today")) {
			pressButton("Tomorrow");
		} else if (selectedButton == getButton("Tomorrow")) {
			pressButton("Coming");
		} else if (selectedButton == getButton("Coming")) {
			pressButton("Past");
		} else if (selectedButton == getButton("Past")) {
			pressButton("No Date");
		} else if (selectedButton == getButton("No Date")) {
			pressButton("Help");
		} else if (selectedButton == getButton("Help")) {
			pressButton("All");
		}
	}
	
	public void pressAboveButton(){
		if (selectedButton == null) {
			pressButton(getButton("Help"));
		} else if (selectedButton == getButton("all")) {
			pressButton(getButton("Help"));
		} else if (selectedButton == getButton("Done")) {
			pressButton(getButton("All"));
		} else if (selectedButton == getButton("Not Done")) {
			pressButton(getButton("Done"));
		} else if (selectedButton == getButton("Today")) {
			pressButton(getButton("Not Done"));
		} else if (selectedButton == getButton("Tomorrow")) {
			pressButton(getButton("Today"));
		} else if (selectedButton == getButton("Coming")) {
			pressButton(getButton("Tomorrow"));
		} else if (selectedButton == getButton("Past")) {
			pressButton(getButton("Coming"));
		} else if (selectedButton == getButton("No Date")) {
			pressButton(getButton("Past"));
		} else if (selectedButton == getButton("Help")) {
			pressButton(getButton("No Date"));
		}
	}
}
