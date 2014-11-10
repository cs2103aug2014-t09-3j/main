import java.awt.Component;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

//@author A0112129U

/**
 * this class is a scroll panel for storing the content
 */
public class EzGUIDisplayPanel extends JScrollPane {
	private static final int SCROLLING_INCREMENT = 20;

	/**
	 * initialize the scroll panel
	 * 
	 * @param view
	 *            is a JEditorPane, which actually show the content
	 */
	public EzGUIDisplayPanel(Component view) {
		super(view);
		setFocusable(false);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setLayout(new ScrollPaneLayout());
		setBorder(null);

		view.setBackground(EzConstants.SHOW_AREA_BACKGROUND);
		((JEditorPane) view).setEditable(false);
		((JEditorPane) view).setContentType("text/html");
		view.setFocusable(false);
	}

	/**
	 * scroll the view down
	 */
	public void scrollDown() {
		getVerticalScrollBar().setValue(
				getVerticalScrollBar().getValue() + SCROLLING_INCREMENT);
	}

	/**
	 * scroll the view up
	 */
	public void scrollUp() {
		getVerticalScrollBar().setValue(
				getVerticalScrollBar().getValue() - SCROLLING_INCREMENT);
	}
}
