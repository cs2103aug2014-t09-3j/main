import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//@author A0112129U

/**
 * this class is the main panel of the GUI, containing other components
 * 
 */
public class EzGUIMainPanel extends JPanel {

	/**
	 * Create the main panel.
	 */
	public EzGUIMainPanel() {
		setFocusable(false);
		setBackground(EzGUI.BACKGROUND_COLOR);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0, 10));
	}

}
