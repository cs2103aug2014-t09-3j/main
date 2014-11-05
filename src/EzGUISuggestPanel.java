import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

//@author A0112129U
public class EzGUISuggestPanel extends JDialog {
	private JList<String> suggestList;
	private JScrollPane suggestScrollPanel;
	private static EzGUISuggestPanel suggestPanel;
	private boolean selectionMode = false;

	public static EzGUISuggestPanel getInstance(){
		if (suggestPanel==null){
			suggestPanel = new EzGUISuggestPanel(EzGUI.getMainFrame(), "Suggest", false);
		}
		return suggestPanel;
	}
	
	public boolean inSelectionMode(){
		return selectionMode;
	}
	
	private EzGUISuggestPanel(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		suggestPanel = this;
		
		int x = owner.getLocation().x + 165;
		int y = owner.getLocation().y + this.getHeight() - 10;
		setLocation(x, y);
		setMinimumSize(new Dimension(784, 0));

		ArrayList<EzTask> listTask = EzController.getStorage()
				.getListOfAllTasks();
		// String[] listString = {"aaaaa","aaaaa","aaaaa"};
		String[] listString = new String[listTask.size()];

		for (int i = 0; i < listTask.size(); i++) {
			listString[i] = listTask.get(i).toString();
		}

		suggestList = new JList<String>(listString);
		suggestList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					enterSelection();
				}
			}
		});
		suggestList.setBackground(EzConstants.WHITE_SMOKE_COLOR);

		suggestScrollPanel = new JScrollPane(suggestList);
		suggestScrollPanel.setFocusable(false);
		suggestScrollPanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		suggestScrollPanel
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		suggestScrollPanel.setLayout(new ScrollPaneLayout());
		suggestScrollPanel.setBorder(BorderFactory.createLineBorder(
				EzConstants.CHATEAU_GREEN_COLOR, 5));
		suggestScrollPanel.setPreferredSize(new Dimension(784, 85));

		getContentPane().add(suggestScrollPanel, BorderLayout.SOUTH);
		setUndecorated(true);
		pack();
		setFocusableWindowState(false);
		setVisible(false);
		selectionMode = false;
	}

	public void enterSelection() {
		int selectIndex = suggestList.getSelectedIndex();
		loadSuggestion(EzGUICommandPanel.getInstance().getText());
		if ((0 <= selectIndex)
				&& (selectIndex < suggestList.getModel().getSize())) {
			int taskId = getFirstNumber(suggestList.getModel().getElementAt(
					selectIndex));
			if (taskId > -1) {
				int caretPos = EzGUICommandPanel.getInstance().getSelectionStart();
				EzGUICommandPanel.getInstance().deleteSelection();
				EzGUICommandPanel.getInstance().typeNormal(String.valueOf(taskId) + " ",caretPos);
			}
		}
		suggestList.setSelectedIndex(-1);
		selectionMode = false;
		setVisible(false);
	}
	
	private int getFirstNumber(String text) {
		int result = -1;
		int i = 0;
		while ((text.charAt(i) < '0') || (text.charAt(i) > '9')) {
			i++;
		}
		if ((text.charAt(i) >= '0') && (text.charAt(i) <= '9')) {
			result = 0;
			while ((text.charAt(i) >= '0') && (text.charAt(i) <= '9')) {
				result = result * 10 + (int) (text.charAt(i) - '0');
				i++;
			}
		}
		return result;
	}
	
	public void loadSuggestion(String contentInputField) {
		boolean activateSuggestion = false;
		int startCaretPos = -1;

		ArrayList<String> notKeywordOrNumberList = new ArrayList<String>();
		int length = contentInputField.length();
		String word;
		String lastKeyword = "";
		boolean doneOrDeleteKeywordAvailable = false;
		boolean removeKeywordAvailable = false;
		int firstPosNotKeywordOrNumber = -1;
		// int lastPosNotKeywordOrNumber = -1;

		for (int i = 0; i < length; i++) {
			if (contentInputField.charAt(i) == ' ') {
				while ((i + 1 < length)
						&& (contentInputField.charAt(i + 1) == ' ')) {
					i++;
				}
			} else if (contentInputField.charAt(i) == '\"') {
				if (firstPosNotKeywordOrNumber == -1) {
					firstPosNotKeywordOrNumber = i;
				}
				word = "";
				while ((i + 1 < length)
						&& (contentInputField.charAt(i + 1) != '\"')) {
					i++;
					word = word + contentInputField.charAt(i);
				}

				if (i + 1 < length) {
					i++;
				}
				// lastPosNotKeywordOrNumber = i;
				notKeywordOrNumberList.add(word.trim());
			} else {
				int tmp = i;
				word = "" + contentInputField.charAt(i);
				while ((i + 1 < length)
						&& (contentInputField.charAt(i + 1) != ' ')) {
					i++;
					word = word + contentInputField.charAt(i);
				}

				if (EzGUI.isKeyword(word)) {
					lastKeyword = word;
					if (word.equalsIgnoreCase("remove")) {
						removeKeywordAvailable = true;
					}
					if (word.equalsIgnoreCase("done")
							|| word.equalsIgnoreCase("undone")
							|| word.equalsIgnoreCase("delete")) {
						doneOrDeleteKeywordAvailable = true;
					}
				}

				if ((!EzGUI.isKeyword(word)) && (!isNumber(word))) {
					notKeywordOrNumberList.add(word);
					if (firstPosNotKeywordOrNumber == -1) {
						firstPosNotKeywordOrNumber = tmp;
					}
					// lastPosNotKeywordOrNumber = i;
					if (startCaretPos == -1) {
						startCaretPos = i - word.length() + 1;
					}
				}
			}
		}

		if (lastKeyword.equalsIgnoreCase("done")
				|| lastKeyword.equalsIgnoreCase("undone")
				|| lastKeyword.equalsIgnoreCase("delete")
				|| lastKeyword.equalsIgnoreCase("update")
				|| (lastKeyword.equalsIgnoreCase("date") && removeKeywordAvailable)
				|| (lastKeyword.equalsIgnoreCase("time") && removeKeywordAvailable)
				|| (lastKeyword.equalsIgnoreCase("venue") && removeKeywordAvailable)
				|| (lastKeyword.equalsIgnoreCase("from") && doneOrDeleteKeywordAvailable)
				|| (lastKeyword.equalsIgnoreCase("to") && doneOrDeleteKeywordAvailable)) {
			activateSuggestion = true;
		}

		if (activateSuggestion) {
			suggestList.removeAll();

			// LOGGER.log(Level.INFO, String.format("List keywords: %d",
			// notKeywordOrNumberList.size()));

			ArrayList<EzTask> listTask = EzSort.sortById(EzController
					.getStorage().getTasksByKeywords(notKeywordOrNumberList));
			String[] listString = new String[listTask.size()];

			for (int i = 0; i < listTask.size(); i++) {
				listString[i] = listTask.get(i).toString();
			}

			suggestList.setListData(listString);
			if (listString.length > 0) {
				suggestList.setSelectedIndex(0);
			} else {
				suggestList.clearSelection();
			}

			selectionMode = true;
			suggestPanel.setVisible(true);

			if (firstPosNotKeywordOrNumber != -1) {
				EzGUICommandPanel.getInstance().setSelection(
						firstPosNotKeywordOrNumber, EzGUICommandPanel.getInstance().getCaretPosition());
			}
		} else {
			suggestList.clearSelection();
			selectionMode = false;
			suggestPanel.setVisible(false);
		}
		suggestPanel.pack();
	}
	
	private boolean isNumber(String word) {
		for (int i = 0; i < word.length(); i++) {
			if ((word.charAt(i) < '0') || (word.charAt(i) > '9')) {
				return false;
			}
		}
		return true;
	}

	
	
	public void selectBelow() {
		int selectIndex = suggestList.getSelectedIndex();
		selectIndex++;
		if (selectIndex >= suggestList.getModel().getSize()) {
			selectIndex = -1;
		}
		if ((0 <= selectIndex)
				&& (selectIndex < suggestList.getModel().getSize())) {
			suggestList.setSelectedIndex(selectIndex);
			suggestList.ensureIndexIsVisible(selectIndex);
		} else {
			suggestList.clearSelection();
		}
	}
	
	public void selectAbove() {
		int selectIndex = suggestList.getSelectedIndex();
		selectIndex--;
		if (selectIndex < -1) {
			selectIndex = suggestList.getModel().getSize() - 1;
		}
		if ((0 <= selectIndex)
				&& (selectIndex < suggestList.getModel().getSize())) {
			suggestList.setSelectedIndex(selectIndex);
			suggestList.ensureIndexIsVisible(selectIndex);
		} else {
			suggestList.clearSelection();
		}
	}
}
