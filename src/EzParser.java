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
		String content=userCommand.substring(userCommand.indexOf(" ")+1).trim();
		switch(newAction.getAction())
		{
		case ADD:
			String title=new String();
			if(content.indexOf("\'")<0||content.indexOf("\"", 1)<0//if cannot find "***" means no title in the command
			||!content.startsWith("\""))//if the string does not start with ",means invalid command type.
				newAction.setAction(TypeOfAction.INVALID);
			title=content.substring(content.indexOf("\"")+1, content.indexOf("\"",1));
			content=content.substring(content.indexOf("\"",1)+1).trim();
			String location=new String();
			location=null;
			String time=new String();
			time=null;
			if(content.indexOf("\"")>=0&&content.indexOf("\"", content.indexOf("\""))==content.lastIndexOf("\""))//there is another "***",it must be the location
				{
				location=content.substring(content.indexOf("\"")+1,content.indexOf("\"",content.indexOf("\"")));
				String before=new String();
				String after=new String();
				before=content.substring(0,content.indexOf("\""));
				after=content.substring(content.indexOf("\"",content.indexOf("\"")));
				before=before.trim();
				if(before.substring(before.length()-2).equalsIgnoreCase("at"))//remove the "at" before location if there is an "at"
				{
					before=before.substring(0, before.length());
				}
				content=before+after;
				}
			//take priority if have.
			//after take out priority, there should be only time,date
			//deal with keyword today,tomorrow
			//deal with on date, at time, begin, end

			
			
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