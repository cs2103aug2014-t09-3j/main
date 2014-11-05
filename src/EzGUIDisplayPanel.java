import java.awt.Component;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;


public class EzGUIDisplayPanel extends JScrollPane {
	private static final int SCROLLING_INCREMENT = 20;

	public EzGUIDisplayPanel(Component view){
		super(view);
		setFocusable(false);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setLayout(new ScrollPaneLayout());
		setBorder(null);
		
		view.setBackground(EzConstants.SHOW_AREA_BACKGROUND);
		((JEditorPane) view).setEditable(false);
		((JEditorPane) view).setContentType("text/html");
		view.setFocusable(false);
	}
	
	public void scrollDown() {
		getVerticalScrollBar().setValue(getVerticalScrollBar().getValue() + SCROLLING_INCREMENT);
	}

	public void scrollUp() {
		getVerticalScrollBar().setValue(getVerticalScrollBar().getValue() - SCROLLING_INCREMENT);
	}
}
