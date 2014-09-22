import java.util.*;

/**
 * COMMAND:
	ADD "title"			: set the title of EzTask object
		AT "venue"		: set the venue of EzTask object
		AT time			: set the time 		of both startTime and endTime of EzTask object
		AT time	date	: set the date and time of both startTime and endTime of EzTask object
		ON date			: set the date 		of both startTime and endTime of EzTask object
		ON date time	: set the date and time of both startTime and endTime of EzTask object
		TODAY			: if it appears after ON, FROM or TO, it represents the date of today
				  			if it stands alone, the same as ON TODAY
		TOMORROW		: if it appears after ON, FROM or TO, it represents the date of tomorrow
				  			if it stands alone, the same as ON TOMORROW
		****			: set the priority depending on the number of *
		FROM date		: set the date 		of startTime of EzTask object
		FROM time		: set the time 		of startTime of EzTask object
		FROM date time	: set the date & time 	of startTime of EzTask object
		FROM time date 	: set the date & time 	of startTime of EzTask object
		TO date			: set the date 		of endTime of EzTask object
		TO time			: set the time 		of endTime of EzTask object
		TO date time	: set the date & time 	of endTime of EzTask object
		TO time date 	: set the date & time 	of endTime of EzTask object
		

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