import java.util.*;

/**
 * 
 */

/**
 * @author Khanh
 * Cao Tianwen
 */


public class EzParser {
	public static EzAction extractInfo(String userCommand,EzStorage storage){
		EzAction newAction=new EzAction();
		newAction.setAction(getAction(userCommand));
		switch(newAction.getAction())
		{
		case ADD:
			break;
		
			
		case UPDATE:
			
			break;
			
		case DELETE:
			
			break;
			
		case DONE:
			
			break;
			
		case UNDO:
			newAction.setResults(null);
			newAction.setTargets(null);
			break;
			
		case REDO:
			newAction.setResults(null);
			newAction.setTargets(null);
			break;
			
		case SHOW:
			break;
			
		case HELP:
			break;
		
		default:
			break;
			}
        

			
		return newAction;
	}


public static TypeOfAction getAction(String userCommand)
{
	
	String action=userCommand.substring(0, userCommand.indexOf(" "));
	if(action.equalsIgnoreCase("add"))
		return TypeOfAction.ADD;
	else if(action.equalsIgnoreCase("update"))
		return TypeOfAction.UPDATE;
	else if(action.equalsIgnoreCase("delete"))
		return TypeOfAction.DELETE;
	else if(action.equalsIgnoreCase("undo"))
		return TypeOfAction.UNDO;
	else if(action.equalsIgnoreCase("redo"))
		return TypeOfAction.REDO;
	else if(action.equalsIgnoreCase("show"))
		return TypeOfAction.SHOW;
	else if(action.equalsIgnoreCase("help"))
		return TypeOfAction.HELP;
	
	return TypeOfAction.INVALID;
}


}