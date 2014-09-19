
/**
 * @author Khanh
 *
 *
 * @author Yu Shuen
 */
public class EzController {
	public static String execute(String userCommand){
		EzAction userAction = EzParser.extractInfo(userCommand);
		switch(userAction.getAction()) {
		case ADD:
			break;
		default:
			break;
		}
		return "";
	}
	
	public void refresh(){
		
	}
}
