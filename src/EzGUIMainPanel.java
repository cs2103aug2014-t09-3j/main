import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class EzGUIMainPanel extends JPanel {
	
	/**
	 * Create the panel.
	 */
	public EzGUIMainPanel() {
		setFocusable(false);
		setBackground(EzGUI.BACKGROUND_COLOR);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0, 10));
	}

}
