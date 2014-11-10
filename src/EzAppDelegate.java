import java.awt.EventQueue;

//@author A0112129U

/**
 * this class contains the main function. It will call the initialization of the
 * GUI
 * 
 */
public class EzAppDelegate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initiateGUI();
	}

	/**
	 * initiate the GUI
	 */
	public static void initiateGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EzGUI frame = new EzGUI();
					frame.setVisible(true);
					frame.showReminder();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
