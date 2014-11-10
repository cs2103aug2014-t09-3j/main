import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.BorderLayout;

//@author A0112129U

/**
 * this class is the command panel of the GUI
 */
public class EzGUICommandPanel extends JPanel {
	private static final String COMMAND_LABEL = "  Enter Command: ";

	private static final int WINDOW_SIZE_INCREMENT = 10;

	private static final int COMMAND_FIELD_HEIGHT = 10;
	private static final int COMMAND_FIELD_LENGTH = 100000;
	private static final int COMMAND_LABEL_FONT_SIZE = 17;
	private static final int COMMAND_LABEL_HEIGHT = 26;
	private static final int COMMAND_LABEL_WIDTH = 154;
	private static final int COMMAND_LABEL_PANEL_HEIGHT = 25;
	private static final int COMMAND_LABEL_PANEL_WIDTH = 160;
	private JTextPane commandField;
	private static EzGUICommandPanel commandPanel;
	private SimpleAttributeSet[] commandAttributeSet = new SimpleAttributeSet[4];
	private static final String COMMAND_FIELD_FONT = "Arial";
	private static final int COMMAND_FIELD_SIZE = 17;
	private ArrayList<String> commandHistory = new ArrayList<String>();
	private int historyPos = 0;
	private boolean showingFeedback = false;
	private static final String[] DOUBLE_QUOTE_KEYWORDS = { "add", "at",
			"title", "venue", "have" };

	/**
	 * @return the text of the command field
	 */
	public String getText() {
		return commandField.getText();
	}

	/**
	 * focus on the command field
	 */
	public void focusOnField() {
		commandField.requestFocus();
	}

	/**
	 * @return the instance of this class
	 */
	public static EzGUICommandPanel getInstance() {
		if (commandPanel == null) {
			commandPanel = new EzGUICommandPanel();
		}
		return commandPanel;
	}

	/**
	 * Create the main panel.
	 */
	private EzGUICommandPanel() {
		setLayout(new BorderLayout(0, 0));
		setBackground(EzGUI.BACKGROUND_COLOR);
		setBorder(null);
		setFocusable(false);
		createCommandLabel();
		createCommandInputField();
	}

	/**
	 * create a panel for the label next to the command field
	 */
	private void createCommandLabel() {
		JTextPane commandLabel = new JTextPane();
		commandLabel.setBounds(0, 0, COMMAND_LABEL_WIDTH, COMMAND_LABEL_HEIGHT);
		commandLabel.setEditable(false);
		commandLabel.setForeground(Color.WHITE);
		commandLabel.setFont(new Font(EzGUI.BUTTON_FONT, Font.BOLD,
				COMMAND_LABEL_FONT_SIZE));
		commandLabel.setBackground(EzGUI.BACKGROUND_COLOR);
		commandLabel.setText(COMMAND_LABEL);
		commandLabel.setFocusable(false);

		JPanel commandLabelPanel = new JPanel();
		commandLabelPanel.setBackground(EzGUI.BACKGROUND_COLOR);
		commandLabelPanel.setFocusable(false);
		commandLabelPanel.setPreferredSize(new Dimension(
				COMMAND_LABEL_PANEL_WIDTH, COMMAND_LABEL_PANEL_HEIGHT));
		commandLabelPanel.setLayout(null);
		commandLabelPanel.add(commandLabel);

		add(commandLabelPanel, BorderLayout.WEST);

	}

	/**
	 * create the panel for the command field
	 */
	private void createCommandInputField() {

		commandField = new JTextPane();
		commandField.setBackground(EzConstants.SHOW_AREA_BACKGROUND);
		commandField.grabFocus();
		commandField.addKeyListener(new EzKeyAdapter());

		JPanel newPanel = new JPanel();
		newPanel.add(commandField);
		newPanel.setPreferredSize(new Dimension(COMMAND_FIELD_LENGTH,
				COMMAND_FIELD_HEIGHT));
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));

		JScrollPane scroll = new JScrollPane(newPanel);

		scroll.setFocusable(false);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setLayout(new ScrollPaneLayout());
		scroll.setBorder(null);

		JPanel commandFieldpanel = new JPanel();
		commandFieldpanel.setLayout(new BoxLayout(commandFieldpanel,
				BoxLayout.X_AXIS));
		commandFieldpanel.add(scroll);
		add(commandFieldpanel);

		loadCommandAttributeSet();
	}

	/**
	 * this class will process according to the keyboard stroke
	 * 
	 */
	class EzKeyAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent arg0) {
			clearFeedback();
			if (arg0.isControlDown()) {
				if ((!arg0.isAltDown() && (!arg0.isShiftDown()))) { // only Ctrl
					switch (arg0.getKeyChar()) {
					case 22:
					case 25:
					case 26:
						arg0.consume();
						break;
					}
					switch (arg0.getKeyCode()) {
					case KeyEvent.VK_UP:
						EzGUI.scrollUp();
						arg0.consume();
						break;
					case KeyEvent.VK_DOWN:
						EzGUI.scrollDown();
						arg0.consume();
						break;
					case KeyEvent.VK_RIGHT:
						EzGUI.showPage(EzGUI.getPageToShow() + 1);
						arg0.consume();
						break;
					case KeyEvent.VK_LEFT:
						EzGUI.showPage(EzGUI.getPageToShow() - 1);
						arg0.consume();
						break;
					case KeyEvent.VK_1:
						EzGUIButtonPanel.getInstance().pressButton("All");
						break;
					case KeyEvent.VK_2:
						EzGUIButtonPanel.getInstance().pressButton("Done");
						break;
					case KeyEvent.VK_3:
						EzGUIButtonPanel.getInstance().pressButton("Not done");
						break;
					case KeyEvent.VK_4:
						EzGUIButtonPanel.getInstance().pressButton("Today");
						break;
					case KeyEvent.VK_5:
						EzGUIButtonPanel.getInstance().pressButton("Tomorrow");
						break;
					case KeyEvent.VK_6:
						EzGUIButtonPanel.getInstance().pressButton("Upcoming");
						break;
					case KeyEvent.VK_7:
						EzGUIButtonPanel.getInstance().pressButton("Overdue");
						break;
					case KeyEvent.VK_8:
						EzGUIButtonPanel.getInstance().pressButton("No date");
						break;
					case KeyEvent.VK_9:
					case KeyEvent.VK_H:
						EzGUIButtonPanel.getInstance().pressButton("Help");
						break;
					}
				} else if ((!arg0.isAltDown() && (arg0.isShiftDown()))) { // Ctrl
																			// +
																			// Shift
					int x = EzGUI.getMainFrameLocation().x;
					int y = EzGUI.getMainFrameLocation().y;

					switch (arg0.getKeyCode()) { // resize the window
					case KeyEvent.VK_UP:
						EzGUI.setMainFrameLocation(x, y - 10);
						arg0.consume();
						break;
					case KeyEvent.VK_DOWN:
						EzGUI.setMainFrameLocation(x, y + 10);
						arg0.consume();
						break;
					case KeyEvent.VK_LEFT:
						EzGUI.setMainFrameLocation(x - 10, y);
						arg0.consume();
						break;
					case KeyEvent.VK_RIGHT:
						EzGUI.setMainFrameLocation(x + 10, y);
						arg0.consume();
						break;
					}
				}
			} else if (!arg0.isAltDown()) {
				switch (arg0.getKeyChar()) {
				case KeyEvent.VK_ENTER:
					if (!EzGUISuggestPanel.getInstance().inSelectionMode()) {
						enterCommand();
					} else {
						EzGUISuggestPanel.getInstance().enterSelection();
					}
					arg0.consume();
					break;
				case KeyEvent.VK_BACK_SPACE:
				case KeyEvent.VK_DELETE:
					arg0.consume();
					break;
				}

				switch (arg0.getKeyCode()) {
				case KeyEvent.VK_UP:
					if (!EzGUISuggestPanel.getInstance().inSelectionMode()) {
						goToPreviousCommand();
					} else {
						EzGUISuggestPanel.getInstance().selectAbove();
					}
					arg0.consume();
					break;
				case KeyEvent.VK_DOWN:
					if (!EzGUISuggestPanel.getInstance().inSelectionMode()) {
						goToNextCommand();
					} else {
						EzGUISuggestPanel.getInstance().selectBelow();
					}
					arg0.consume();
					break;
				case KeyEvent.VK_F1:
					EzGUIButtonPanel.getInstance().pressButton("help");
					break;
				case KeyEvent.VK_TAB:
					if (!arg0.isShiftDown()) {
						EzGUIButtonPanel.getInstance().pressBelowButton();
					} else {
						EzGUIButtonPanel.getInstance().pressAboveButton();
					}
					arg0.consume();
					break;
				}

			} else {
				if ((arg0.isAltDown() && (arg0.isShiftDown()))) {
					switch (arg0.getKeyCode()) { // resize the window
					case KeyEvent.VK_UP:
						EzGUI.increaseWindowSize(0, -WINDOW_SIZE_INCREMENT);
						arg0.consume();
						break;
					case KeyEvent.VK_DOWN:
						EzGUI.increaseWindowSize(0, WINDOW_SIZE_INCREMENT);
						arg0.consume();
						break;
					case KeyEvent.VK_LEFT:
						EzGUI.increaseWindowSize(-WINDOW_SIZE_INCREMENT, 0);
						arg0.consume();
						break;
					case KeyEvent.VK_RIGHT:
						EzGUI.increaseWindowSize(WINDOW_SIZE_INCREMENT, 0);
						arg0.consume();
						break;
					}
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// clearFeedback();
			int caretPos = commandField.getCaretPosition();
			if ((!e.isControlDown()) && (!e.isAltDown())) {
				switch (e.getKeyChar()) {
				case KeyEvent.VK_ENTER:
					break;
				case KeyEvent.VK_DELETE:
					deleteSelection();
					break;
				case KeyEvent.VK_BACK_SPACE:
					backSpaceSelection();
					break;
				case KeyEvent.VK_SPACE:
					typeSpace(caretPos);
					break;
				case KeyEvent.VK_TAB:
					e.consume();
					break;
				default:
					typeNormal("" + e.getKeyChar(), caretPos);
					break;
				}
				e.consume();
			} else {
				switch (e.getKeyChar()) {
				case 26: // CTRL + Z
					undo();
					e.consume();
					break;
				case 25: // CTRL + Y
					redo();
					e.consume();
					break;
				case 22: // CTRL + V
					pasteText();
					e.consume();
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * clear the feedback on the command field
	 */
	private void clearFeedback() {
		if (showingFeedback) {
			showingFeedback = false;
			commandField.setText("");
		}
	}

	/**
	 * redo the action
	 */
	private void redo() {
		EzController.execute("redo");
	}

	/**
	 * undo the action
	 */
	private void undo() {
		EzController.execute("undo");
	}

	/**
	 * do the action paste into the command field
	 */
	private void pasteText() {
		int caretPos;
		commandField.paste();
		caretPos = commandField.getCaretPosition();
		addColorForCommandField(commandField.getText(),
				commandField.getStyledDocument());
		commandField.setCaretPosition(caretPos);
	}

	/**
	 * process when user type a space into the command field
	 * 
	 * @param caretPos
	 */
	private void typeSpace(int caretPos) {
		String contentInputField;
		contentInputField = commandField.getText();
		String result = "";
		String lastWord = "";
		boolean haveKeywordAvailable = false;
		boolean insideQuote = false;

		for (int i = 0; i < caretPos; i++) {
			if (contentInputField.charAt(i) == ' ') {
				result = result + " ";
				while ((i + 1 < caretPos)
						&& (contentInputField.charAt(i + 1) == ' ')) {
					i++;
					result = result + " ";
				}
			} else if (contentInputField.charAt(i) == '\"') {
				insideQuote = true;
				lastWord = "\"";
				while ((i + 1 < caretPos)
						&& (contentInputField.charAt(i + 1) != '\"')) {
					i++;
					lastWord = lastWord + contentInputField.charAt(i);
				}

				if (i + 1 < caretPos) {
					i++;
					lastWord = lastWord + contentInputField.charAt(i);
				}

				if ((lastWord.charAt(lastWord.length() - 1) == '\"')
						&& (lastWord.length() > 2)) {
					insideQuote = false;
				}

				result = result + lastWord;
			} else {
				lastWord = "" + contentInputField.charAt(i);
				while ((i + 1 < caretPos)
						&& (contentInputField.charAt(i + 1) != ' ')) {
					i++;
					lastWord = lastWord + contentInputField.charAt(i);
				}
				if (lastWord.equalsIgnoreCase("have")) {
					haveKeywordAvailable = true;
				}
				result = result + lastWord;
			}
		}

		if ((!insideQuote)
				&& (isDoubleQuoteKeyword(lastWord) || haveKeywordAvailable)) {
			result = result
					+ " \""
					+ contentInputField.substring(caretPos,
							contentInputField.length());
			addColorForCommandField(result, commandField.getStyledDocument());
			commandField.setCaretPosition(caretPos + 2);
		} else {
			result = result
					+ " "
					+ contentInputField.substring(caretPos,
							contentInputField.length());
			addColorForCommandField(result, commandField.getStyledDocument());
			commandField.setCaretPosition(caretPos + 1);
		}

		EzGUISuggestPanel.getInstance().loadSuggestion(contentInputField);
	}

	/**
	 * check if the word is the keyword that needs a quote after it or not
	 */
	private boolean isDoubleQuoteKeyword(String word) {
		for (int i = 0; i < DOUBLE_QUOTE_KEYWORDS.length; i++) {
			if (word.equalsIgnoreCase(DOUBLE_QUOTE_KEYWORDS[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * delete the selection when pressing backspace
	 */
	private void backSpaceSelection() {
		String contentInputField;
		int startPos = commandField.getSelectionStart();
		if (commandField.getSelectionStart() == commandField.getSelectionEnd()) {
			if (commandField.getSelectionStart() > 0) {
				if ((commandField.getText().charAt(
						commandField.getSelectionStart() - 1) == '\"')
						&& (commandField.getSelectionStart() < commandField
								.getText().length())
						&& (commandField.getText().charAt(
								commandField.getSelectionStart()) == '\"')) {
					contentInputField = deleteString(commandField.getText(),
							commandField.getSelectionStart() - 1,
							commandField.getSelectionStart() + 1);
				} else {
					contentInputField = deleteString(commandField.getText(),
							commandField.getSelectionStart() - 1,
							commandField.getSelectionStart());
				}
				addColorForCommandField(contentInputField,
						commandField.getStyledDocument());
				commandField.setCaretPosition(startPos - 1);
				EzGUISuggestPanel.getInstance().loadSuggestion(
						contentInputField);
			}
		} else {
			contentInputField = deleteString(commandField.getText(),
					commandField.getSelectionStart(),
					commandField.getSelectionEnd());
			addColorForCommandField(contentInputField,
					commandField.getStyledDocument());
			commandField.setCaretPosition(startPos);
			EzGUISuggestPanel.getInstance().loadSuggestion(contentInputField);
		}

	}

	/**
	 * process when user press ENTER
	 */
	private void enterCommand() {
		String fb = EzController.execute(commandField.getText());
		commandHistory.add(commandField.getText());
		historyPos = commandHistory.size();
		if (fb != null) {
			addColorForFeedBack(fb, commandField.getStyledDocument());
			showingFeedback = true;
		} else {
			addColorForFeedBack("No feedback.",
					commandField.getStyledDocument());
			showingFeedback = true;
		}
	}

	/**
	 * go to the next command in the command history
	 */
	private void goToNextCommand() {
		if (historyPos < commandHistory.size()) {
			historyPos++;
			if (historyPos < commandHistory.size()) {
				addColorForCommandField(commandHistory.get(historyPos),
						commandField.getStyledDocument());
			} else {
				addColorForCommandField("", commandField.getStyledDocument());
			}
		}
	}

	/**
	 * go to the previous command in the command history
	 */
	private void goToPreviousCommand() {
		if (historyPos > 0) {
			historyPos--;
			addColorForCommandField(commandHistory.get(historyPos),
					commandField.getStyledDocument());
		}
	}

	/**
	 * delete the selection when DEL is pressed
	 */
	public void deleteSelection() {
		String contentInputField;
		int endPos = commandField.getSelectionStart();
		if (commandField.getSelectionStart() == commandField.getSelectionEnd()) {
			if (commandField.getSelectionStart() < commandField.getText()
					.length()) {
				contentInputField = deleteString(commandField.getText(),
						commandField.getSelectionStart(),
						commandField.getSelectionEnd() + 1);
				addColorForCommandField(contentInputField,
						commandField.getStyledDocument());
				commandField.setCaretPosition(endPos);
				EzGUISuggestPanel.getInstance().loadSuggestion(
						contentInputField);
			}
		} else {
			int endCaretPos = commandField.getSelectionEnd();
			if ((endCaretPos < commandField.getText().length())
					&& (commandField.getText().charAt(endCaretPos) == '\"')) {
				endCaretPos++;
			}
			contentInputField = deleteString(commandField.getText(),
					commandField.getSelectionStart(), endCaretPos);
			addColorForCommandField(contentInputField,
					commandField.getStyledDocument());
			commandField.setCaretPosition(endPos);
			EzGUISuggestPanel.getInstance().loadSuggestion(contentInputField);
		}
	}

	/**
	 * set the selection in the command field
	 * 
	 * @param start
	 * @param end
	 */
	public void setSelection(int start, int end) {
		commandField.setSelectionStart(start);
		commandField.setSelectionEnd(end);
	}

	/**
	 * @return the position of the caret
	 */
	public int getCaretPosition() {
		return commandField.getCaretPosition();
	}

	/**
	 * get the start position of the selection in the command field
	 * 
	 * @return
	 */
	public int getSelectionStart() {
		return commandField.getSelectionStart();
	}

	/**
	 * process when user type normal character
	 * 
	 * @param typedText
	 * @param caretPos
	 */
	public void typeNormal(String typedText, int caretPos) {
		String contentInputField;
		contentInputField = commandField.getText().substring(0, caretPos)
				+ typedText
				+ commandField.getText().substring(caretPos,
						commandField.getText().length());
		addColorForCommandField(contentInputField,
				commandField.getStyledDocument());
		commandField.setCaretPosition(caretPos + typedText.length());

		EzGUISuggestPanel.getInstance().loadSuggestion(contentInputField);
	}

	/**
	 * delete a part of a string
	 * 
	 * @param text
	 * @param startPos
	 * @param endPos
	 * @return the result string
	 */
	private String deleteString(String text, int startPos, int endPos) {
		return text.substring(0, startPos) + text.substring(endPos);
	}

	/**
	 * add color to the command field
	 * 
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
		for (int i = 0; i < contentInputField.length(); i++) {
			String word = "";
			if (contentInputField.charAt(i) == ' ') {
				word = " ";
				while ((i + 1 < contentInputField.length())
						&& (contentInputField.charAt(i + 1) == ' ')) {
					i++;
					word = word + ' ';
				}
				try {
					doc.insertString(doc.getLength(), word,
							commandAttributeSet[0]);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			} else if (contentInputField.charAt(i) == '\"') {
				word = "\"";
				while ((i + 1 < contentInputField.length())
						&& (contentInputField.charAt(i + 1) != '\"')) {
					i++;
					word = word + contentInputField.charAt(i);
				}
				if (i + 1 < contentInputField.length()) {
					i++;
					word = word + contentInputField.charAt(i);
				}
				try {
					doc.insertString(doc.getLength(), word,
							commandAttributeSet[2]);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			} else {
				word = "" + contentInputField.charAt(i);
				while ((i + 1 < contentInputField.length())
						&& (contentInputField.charAt(i + 1) != ' ')) {
					i++;
					word = word + contentInputField.charAt(i);
				}
				if (EzGUI.isKeyword(word)) {
					try {
						doc.insertString(doc.getLength(), word,
								commandAttributeSet[1]);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						doc.insertString(doc.getLength(), word,
								commandAttributeSet[0]);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * add color for the feedback
	 * 
	 * @param feedback
	 * @param doc
	 */
	private void addColorForFeedBack(String feedback, StyledDocument doc) {
		try {
			doc.remove(0, doc.getLength());
			doc.insertString(doc.getLength(), feedback, commandAttributeSet[3]);
		} catch (BadLocationException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * load the text attributes used in the command field
	 */
	private void loadCommandAttributeSet() {
		commandAttributeSet[0] = new SimpleAttributeSet();
		StyleConstants
				.setFontFamily(commandAttributeSet[0], COMMAND_FIELD_FONT);
		StyleConstants.setFontSize(commandAttributeSet[0], COMMAND_FIELD_SIZE);

		commandAttributeSet[1] = new SimpleAttributeSet(commandAttributeSet[0]);
		StyleConstants.setForeground(commandAttributeSet[1],
				EzConstants.MARINER_COLOR);

		commandAttributeSet[2] = new SimpleAttributeSet(commandAttributeSet[0]);
		StyleConstants.setForeground(commandAttributeSet[2],
				EzConstants.IRON_GRAY_COLOR);

		commandAttributeSet[3] = new SimpleAttributeSet(commandAttributeSet[0]);
		StyleConstants.setForeground(commandAttributeSet[3],
				EzConstants.TERRA_COTTA_COLOR);
	}
}
