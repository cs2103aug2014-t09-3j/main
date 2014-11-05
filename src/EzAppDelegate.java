import java.awt.EventQueue;

//@author A0112129U
public class EzAppDelegate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initiateGUI();
	}
	
	public static void initiateGUI(){
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
