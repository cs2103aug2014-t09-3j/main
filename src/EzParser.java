import java.util.*;

/**
 * 
 COMMAND:
 ADD "title"		: set the title of EzTask object
 AT "venue"	: set the venue of EzTask object
 AT time		: set the time 		of both startTime and endTime of EzTask object
 AT time	date	: set the date and time of both startTime and endTime of EzTask object
 ON date		: set the date 		of both startTime and endTime of EzTask object
 ON date time	: set the date and time of both startTime and endTime of EzTask object
 TODAY		: if it appears after ON, FROM or TO, it represents the date of today
 if it stands alone, the same as ON TODAY
 TOMORROW	: if it appears after ON, FROM or TO, it represents the date of tomorrow
 if it stands alone, the same as ON TOMORROW
 ****		: set the priority depending on the number of *
 FROM date	: set the date 		of startTime of EzTask object
 FROM time	: set the time 		of startTime of EzTask object
 FROM date time	: set the date & time 	of startTime of EzTask object
 FROM time date 	: set the date & time 	of startTime of EzTask object
 TO date		: set the date 		of endTime of EzTask object
 TO time		: set the time 		of endTime of EzTask object
 TO date time	: set the date & time 	of endTime of EzTask object
 TO time date 	: set the date & time 	of endTime of EzTask object

//deleted the "SET" keyword
 UPDATE id 
 	TITLE "newTitle"
 	DATE [newDate]
 	TIME [newTime]
 	START DATE [newDate]
 	START TIME [newTime]
 	END DATE [newDate]
 	END TIME [newTime]
 	VENUE "newVenue"
 	PRIORITY [newPriority]

 UNDO
 REDO

 DONE [id1] [id2] [id3]...
 DONE FROM [startID] TO [endID]
 DONE ALL
 ON [date]
 
 /////new////
 UNDONE [id1] [id2] [id3]...
 UNDONE FROM [startID] TO [endID]
 UNDONE ALL
 ON [date]
 
 DELETE [id1] [id2] [id3]
 DELETE FROM [startId] TO [endId]
 DELETE ALL
 ON [date]

 SHOW ALL
 SHOW DONE
 SHOW UNDONE
 ON [date]
 HAVE [keyword1] [keyword2]...		// 
 REMOVE VENUE [id]: set venue as null
 REMOVE DATE [id]: set both start and end date as null
 REMOVE TIME [id]: set time as from 00:00 to LAST_HOUR_OF_DAY:LAST_MINUTE_OF_HOUR
 
 SORT ID
 SORT TITLE
 SORT VENUE
 SORT DATE
 SORT PRIORITY
 SORT DONE
 
 PAGE [page number]: set both target and result as null and set the page number and the type of action as page
 *
 */

//@author A0113528L
 
public class EzParser {
	private static final int MAX_DAY_OF_MONTH = 31;
	private static final int HOUR_DIFFERENCE_BETWEEN_AM_PM = 12;
	private static final int MAX_MONTH_OF_YEAR = 12;
	private static final int LAST_HOUR_OF_DAY = 23;
	private static final int LAST_MINUTE_OF_HOUR = 59;
	private static final int MAX_HOUR_OF_DAY = 24;
	private static final int ARRAYSIZE_FOR_DATEARR_WITH_TIME = 5;
	private static final int ARRAYSIZE_FOR_DATEARR_WITHOUT_TIME = 3;
	private static final int MINUTE_INDEX_FOR_READTIME = 1;
	private static final int HOUR_INDEX_FOR_READTIME = 0;
	private static final int MINUTE_INDEX_FOR_DATE_ARR = 4;
	private static final int HOUR_INDEX_FOR_DATE_ARR = 3;
	private static final int YEAR_INDEX = 2;
	private static final int MONTH_INDEX = 1;
	private static final int DAY_INDEX = 0;
	private static final int YEAR_DIFFERENCE_FOR_DATE_CONSTRUCTOR = 1900;
	public static EzAction extractInfo(String userCommand, EzStorage storage) {
		EzAction newAction = new EzAction();
		newAction.setAction(getAction(userCommand));
		String content = removeFirstWord(userCommand);
		switch (newAction.getAction()) {// content starts with no Action
		case ADD:
			addAction(newAction, content);
			break;

		case UPDATE:
			updateAction(storage, newAction, content);
			break;

		case DELETE:
			deleteAction(storage, newAction, content);
			break;

		case DONE:
			doneAction(storage, newAction, content);
			break;

		case UNDO:
			undoAction(newAction);
			break;

		case REDO:
			redoAction(newAction);
			break;

		case SHOW:
			showAction(storage, newAction, content);
			break;

		case HELP:
			helpAction(newAction);
			break;
		
		case Y:
			setTargetsAndResultsNull(newAction);
			break;
		
		case N:
			setTargetsAndResultsNull(newAction);
			break;
		
		case SORT:
			sortAction(newAction, content);
			break;
		
		case UNDONE:
			undoneAction(storage, newAction, content);
			break;
			
		case REMOVE:
			removeAction(storage, newAction, content);
			break;
		
		case PAGE:
			pageAction(newAction, content);
			break;

		default:
			break;
		}

		return newAction;
	}

	private static void pageAction(EzAction newAction, String content) {
		setTargetsAndResultsNull(newAction);
		try
		{
		int pageNum=Integer.parseInt(content);
		newAction.setPageNumber(pageNum);
		newAction.setFeedback("Page "+pageNum);
		}
		catch(NumberFormatException e)
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command. User might entered an invalid number format.");
		}
	}

	private static void removeAction(EzStorage storage, EzAction newAction,
			String content) {
		ArrayList<EzTask> targetsRemove=new ArrayList<EzTask>();
		ArrayList<EzTask> resultsRemove=new ArrayList<EzTask>();
		
		String typeRemove=getFirstWord(content);
		content=removeFirstWord(content);
		String idRemove=getFirstWord(content);
		content=removeFirstWord(content);
		try
		{
			if(content.isEmpty()!=true)
		
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command.");
		}
		

		targetsRemove.add(storage.findTask(Integer.parseInt(idRemove)));
		EzTask taskRemove=new EzTask(storage.findTask(Integer.parseInt(idRemove)));
		if(typeRemove.equalsIgnoreCase("venue"))
		{
			taskRemove.setVenue(null);
			newAction.setFeedback("Venue removed successfully.");
		}
		else if(typeRemove.equalsIgnoreCase("date"))
		{
			taskRemove.setStartTime(null);
			taskRemove.setEndTime(null);
			newAction.setFeedback("Date removed successfully.");
		}
		else if(typeRemove.equalsIgnoreCase("time"))
		{
			taskRemove.setStartTime(0,0);
			taskRemove.setEndTime(LAST_HOUR_OF_DAY, LAST_MINUTE_OF_HOUR);
			newAction.setFeedback("Time removed succefully.");
		}
		else
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command.");
		}
		
		resultsRemove.add(taskRemove);
		newAction.setTargets(targetsRemove);
		newAction.setResults(resultsRemove);
		}
		catch(NumberFormatException e)
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command. User might entered an invalid number format.");
		}
	}

	private static void undoneAction(EzStorage storage, EzAction newAction,
			String content) {
		newAction.setFeedback("Set as undone successfully!");
		ArrayList<EzTask> targetsUndone=new ArrayList<EzTask>();
		ArrayList<EzTask> resultsUndone=new ArrayList<EzTask>();
		try
		{
		if(getFirstWord(content).equalsIgnoreCase("all"))//if the command is "undone all on a date"
		{
			content=removeFirstWord(content);
			content=removeFirstWord(content);//now content is suppose to be the date
			//find all tasks on the date and assign it to the arraylist.
		  if(readDate(content)[DAY_INDEX]>=0)
		  {
			int[] dateArrUndone=new int[ARRAYSIZE_FOR_DATEARR_WITHOUT_TIME];
			dateArrUndone[DAY_INDEX]=readDate(content)[DAY_INDEX];
			dateArrUndone[MONTH_INDEX]=readDate(content)[MONTH_INDEX];
			dateArrUndone[YEAR_INDEX]=readDate(content)[YEAR_INDEX];
			
			Date dateUndone=new Date(dateArrUndone[YEAR_INDEX]-YEAR_DIFFERENCE_FOR_DATE_CONSTRUCTOR,dateArrUndone[MONTH_INDEX]-1,dateArrUndone[DAY_INDEX]);
		
			ArrayList<EzTask> temp=new ArrayList<EzTask>(storage.getTasksByDate(dateUndone));
			newAction.setTargets(temp);
			for(int i=0;i<storage.getTasksByDate(dateUndone).size();i++)
			{
				EzTask result=new EzTask(storage.getTasksByDate(dateUndone).get(i));
				result.setDone(false);
				resultsUndone.add(result);
			}
			newAction.setResults(storage.getTasksByDate(dateUndone));
			}
		  else
		  {
		  	newAction.setAction(TypeOfAction.INVALID);
		  	newAction.setFeedback("Invalid command. User might entered an invalid date.");
		  }
		}
		else if(getFirstWord(content).equalsIgnoreCase("from"))//"Undone from .. to "
		{
			content=removeFirstWord(content);
			int i,j;
			i=Integer.parseInt(getFirstWord(content));
			content=removeFirstWord(content);
			if(getFirstWord(content).equalsIgnoreCase("to"))
			{
				content=removeFirstWord(content);
			}
			else
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. Invalid keywords.");
			}
			j=Integer.parseInt(content);
			content=removeFirstWord(content);
			if(!content.isEmpty()||i>j)
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("From a later id to an earlier id.");
			}
			for(int k=i;k<=j;k++)
			{
				if(storage.findTask(k)!=null)
				{
					targetsUndone.add(storage.findTask(k));
					EzTask temp=new EzTask(storage.findTask(k));
					temp.setDone(false);
					resultsUndone.add(temp);
				}
			}
			if(targetsUndone.isEmpty())//if none of the tasks are found,set as null
			{
				newAction.setTargets(null);
			}
			newAction.setTargets(targetsUndone);
			newAction.setResults(resultsUndone);
		}
		else 	
		if(Integer.parseInt(getFirstWord(content))>=0)//"Undone id id id id "
		{
			
			while(!content.isEmpty())
			{
				try
				{
				   if(storage.findTask(Integer.parseInt(getFirstWord(content)))!=null)
				   {
			
					targetsUndone.add(storage.findTask(Integer.parseInt(getFirstWord(content))));
					EzTask temp=new EzTask(storage.findTask(Integer.parseInt(getFirstWord(content))));
					temp.setDone(false);//
					resultsUndone.add(temp);
				   }
				   else//cannot find task 
				   {
					   newAction.setAction(TypeOfAction.INVALID);
					   newAction.setFeedback("Cannot find task.");
				   }
				}
				catch(NumberFormatException e)
				{
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command. User might entered an invalid number format.");
				}
				content=removeFirstWord(content);
			
				
			}
			if(targetsUndone.isEmpty())//if none of the tasks are found,set as null
			{
				newAction.setTargets(null);
			}
			newAction.setTargets(targetsUndone);
			newAction.setResults(resultsUndone);
		}else // set as invalid if the command fits none of the above
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command.");
		}
		}
		
		catch(NumberFormatException e)
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command. User might entered an invalid number format.");		
		}
	}

	private static void sortAction(EzAction newAction, String content) {
		String sortType=getFirstWord(content);
		content=removeFirstWord(content);
		if(content.isEmpty()!=true)
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command. Type of sorting not indicated.");		
		}
		else if(sortType.equalsIgnoreCase("id"))
		{
			newAction.setTypeSort(TypeOfSort.ID);
			newAction.setFeedback("Sorted by ID.");
		}
		else if(sortType.equalsIgnoreCase("title"))
		{
			newAction.setTypeSort(TypeOfSort.TITLE);
			newAction.setFeedback("Sorted by TITLE.");
		}
		else if(sortType.equalsIgnoreCase("date"))
		{
			newAction.setTypeSort(TypeOfSort.DATE);
			newAction.setFeedback("Sorted by DATE.");
		}
		else if(sortType.equalsIgnoreCase("priority"))
		{
			newAction.setTypeSort(TypeOfSort.PRIORITY);
			newAction.setFeedback("Sorted by PRIORITY.");
		}
		else if(sortType.equalsIgnoreCase("done"))
		{
			newAction.setTypeSort(TypeOfSort.DONE);
			newAction.setFeedback("Sorted by DONE.");
		}
		else if(sortType.equalsIgnoreCase("venue"))
		{
			newAction.setTypeSort(TypeOfSort.VENUE);
			newAction.setFeedback("Sorted by VENUE.");
		}
		else
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command. Invalid keywords.");
		}
			
		setTargetsAndResultsNull(newAction);
	}

	private static void setTargetsAndResultsNull(EzAction newAction) {
		newAction.setTargets(null);
		newAction.setResults(null);
	}

	private static void helpAction(EzAction newAction) {
		newAction.setTargets(null);
		newAction.setAction(null);
	}

	private static void showAction(EzStorage storage, EzAction newAction,
			String content) {
		ArrayList<EzTask> targetsShow=new ArrayList<EzTask>();
		if(getFirstWord(content).equalsIgnoreCase("all"))
		{
			content=removeFirstWord(content);
			if(getFirstWord(content).isEmpty())
			{
				targetsShow=storage.getListOfAllTasks();
			}
			else if(getFirstWord(content).equalsIgnoreCase("on"))
			{
				content=removeFirstWord(content);
				
				if(readDate(content)[DAY_INDEX]>=0)
				  {
					int[] dateArrShow=new int[ARRAYSIZE_FOR_DATEARR_WITHOUT_TIME];
					dateArrShow[DAY_INDEX]=readDate(content)[DAY_INDEX];
					dateArrShow[MONTH_INDEX]=readDate(content)[MONTH_INDEX];
					dateArrShow[YEAR_INDEX]=readDate(content)[YEAR_INDEX];
					
					Date dateShow=new Date(dateArrShow[YEAR_INDEX]-YEAR_DIFFERENCE_FOR_DATE_CONSTRUCTOR,dateArrShow[MONTH_INDEX]-1,dateArrShow[DAY_INDEX]);
				    targetsShow=storage.getTasksByDate(dateShow);
				  }
				  else
				  {
				  	newAction.setAction(TypeOfAction.INVALID);
				  	newAction.setFeedback("Invalid command. User might entered an invalid date.");
				  }
				
			}
			else if(getFirstWord(content).equalsIgnoreCase("have"))
			{
				content=removeFirstWord(content);
				
				ArrayList<String> keywords=new ArrayList<String>();
				
				while(!content.isEmpty())
				{
					if(getFirstWord(content).indexOf("\"")==0&&
							getFirstWord(content).indexOf("\"",1)==getFirstWord(content).length()-1)
					{
						keywords.add(getFirstWord(content).substring(1, getFirstWord(content).length()-1));
						content=removeFirstWord(content);
					}
					else
					{
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Extra \" or missing \" in the command.");
					}
				}
				targetsShow=storage.getTasksByKeywords(keywords);
			}
			else
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. Invalid keywords.");
			}
			newAction.setResults(targetsShow);
			newAction.setTargets(targetsShow);
			
		}
		else if(getFirstWord(content).equalsIgnoreCase("done"))
		{
			content=removeFirstWord(content);
			if(getFirstWord(content).isEmpty())
			{
				targetsShow=getDoneTasks(storage.getListOfAllTasks());
			}
			else if(getFirstWord(content).equalsIgnoreCase("on"))
			{
				content=removeFirstWord(content);
				
				if(readDate(content)[DAY_INDEX]>=0)
				  {
					int[] dateArrShowDone=new int[ARRAYSIZE_FOR_DATEARR_WITHOUT_TIME];
					dateArrShowDone[DAY_INDEX]=readDate(content)[DAY_INDEX];
					dateArrShowDone[MONTH_INDEX]=readDate(content)[MONTH_INDEX];
					dateArrShowDone[YEAR_INDEX]=readDate(content)[YEAR_INDEX];
					
					Date dateShowDone=new Date(dateArrShowDone[YEAR_INDEX]-YEAR_DIFFERENCE_FOR_DATE_CONSTRUCTOR,dateArrShowDone[MONTH_INDEX]-1,dateArrShowDone[DAY_INDEX]);
				    targetsShow=getDoneTasks(storage.getTasksByDate(dateShowDone));
				  }
				  else
				  {
				  	newAction.setAction(TypeOfAction.INVALID);
				  	newAction.setFeedback("Invalid command. User might entered an invalid date.");
				  }
				
			}
			else
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. Invalid keywords.");
				
			}
			newAction.setResults(targetsShow);
			newAction.setTargets(targetsShow);
		}
		else if(getFirstWord(content).equalsIgnoreCase("undone"))
		{
			content=removeFirstWord(content);
			if(getFirstWord(content).isEmpty())
			{
				targetsShow=getUndoneTasks(storage.getListOfAllTasks());
			}
			else if(getFirstWord(content).equalsIgnoreCase("on"))
			{
				content=removeFirstWord(content);
				
				if(readDate(content)[DAY_INDEX]>=0)
				  {
					int[] dateArrShowUndone=new int[ARRAYSIZE_FOR_DATEARR_WITHOUT_TIME];
					dateArrShowUndone[DAY_INDEX]=readDate(content)[DAY_INDEX];
					dateArrShowUndone[MONTH_INDEX]=readDate(content)[MONTH_INDEX];
					dateArrShowUndone[YEAR_INDEX]=readDate(content)[YEAR_INDEX];
					
					Date dateShowUndone=new Date(dateArrShowUndone[YEAR_INDEX]-YEAR_DIFFERENCE_FOR_DATE_CONSTRUCTOR,dateArrShowUndone[MONTH_INDEX]-1,dateArrShowUndone[DAY_INDEX]);
				    targetsShow=getUndoneTasks(storage.getTasksByDate(dateShowUndone));
				  }
				  else
				  {
				  	newAction.setAction(TypeOfAction.INVALID);
				  	newAction.setFeedback("Invalid command. User might entered an invalid date.");
				  }
				
			}
			else
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. Invalid keywords.");
			}
			newAction.setResults(targetsShow);
			newAction.setTargets(targetsShow);
		}
		
		newAction.setTargets(targetsShow);
		newAction.setResults(targetsShow);
		//show all
		//show done
		//show undone
		//show all on date
		//show done on date
		//show undone on date
		//show all have ""
		
		// set results and target as all tasks()
	}

	private static void redoAction(EzAction newAction) {
		newAction.setFeedback("Redo successfully!");
		newAction.setResults(null);
		newAction.setTargets(null);
	}

	private static void undoAction(EzAction newAction) {
		newAction.setFeedback("Undo successfully!");
		newAction.setResults(null);
		newAction.setTargets(null);
	}

	private static void doneAction(EzStorage storage, EzAction newAction,
			String content) {
		newAction.setFeedback("Set as done successfully!");
		ArrayList<EzTask> targetsDone=new ArrayList<EzTask>();
		ArrayList<EzTask> resultsDone=new ArrayList<EzTask>();
		try
		{
		if(getFirstWord(content).equalsIgnoreCase("all"))//if the command is "done all on a date"
		{
			content=removeFirstWord(content);
			content=removeFirstWord(content);//now content is suppose to be the date
			//find all tasks on the date and assign it to the arraylist.
		  if(readDate(content)[DAY_INDEX]>=0)
		  {
			int[] dateArrDone=new int[ARRAYSIZE_FOR_DATEARR_WITHOUT_TIME];
			dateArrDone[DAY_INDEX]=readDate(content)[DAY_INDEX];
			dateArrDone[MONTH_INDEX]=readDate(content)[MONTH_INDEX];
			dateArrDone[YEAR_INDEX]=readDate(content)[YEAR_INDEX];
			
			Date dateDone=new Date(dateArrDone[YEAR_INDEX]-YEAR_DIFFERENCE_FOR_DATE_CONSTRUCTOR,dateArrDone[MONTH_INDEX]-1,dateArrDone[DAY_INDEX]);
		
			ArrayList<EzTask> temp=new ArrayList<EzTask>(storage.getTasksByDate(dateDone));
			newAction.setTargets(temp);
			for(int i=0;i<storage.getTasksByDate(dateDone).size();i++)
			{
				EzTask result=new EzTask(storage.getTasksByDate(dateDone).get(i));
				result.setDone(true);
				resultsDone.add(result);
			}
			newAction.setResults(resultsDone);
			}
		  else
		  {
		  	newAction.setAction(TypeOfAction.INVALID);
		  	newAction.setFeedback("Invalid command. User might entered an invalid date.");
		  }
		}
		else if(getFirstWord(content).equalsIgnoreCase("from"))//"done from .. to "
		{
			content=removeFirstWord(content);
			int i,j;
			i=Integer.parseInt(getFirstWord(content));
			content=removeFirstWord(content);
			if(getFirstWord(content).equalsIgnoreCase("to"))
			{
				content=removeFirstWord(content);
			}
			else
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. Invalid keywords.");
			}
			j=Integer.parseInt(content);
			content=removeFirstWord(content);
			if(!content.isEmpty()||i>j)
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("From a later id to an earlier id.");
			}
			for(int k=i;k<=j;k++)
			{
				if(storage.findTask(k)!=null)
				{
				targetsDone.add(storage.findTask(k));
				EzTask temp=new EzTask(storage.findTask(k));
				temp.setDone(true);
				resultsDone.add(temp);
				}
			}
			if(targetsDone.isEmpty())//if none of the tasks are found,set as null
			{
				newAction.setTargets(null);
			}
			newAction.setTargets(targetsDone);
			newAction.setResults(resultsDone);
		}
		else
		try
		{	
		if(Integer.parseInt(getFirstWord(content))>=0)//"done id id id id "
		{
			
			while(!content.isEmpty())
			{
				try
				{
				   if(storage.findTask(Integer.parseInt(getFirstWord(content)))!=null)
				   {
			
					targetsDone.add(storage.findTask(Integer.parseInt(getFirstWord(content))));
					EzTask temp=new EzTask(storage.findTask(Integer.parseInt(getFirstWord(content))));
					temp.setDone(true);//
					resultsDone.add(temp);
				   }
				   else//cannot find task 
				   {
					   newAction.setAction(TypeOfAction.INVALID);
					   newAction.setFeedback("Cannot find task.");
				   }
				}
				catch(NumberFormatException e)
				{
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command. User might entered an invalid number format.");
				}
				content=removeFirstWord(content);
			
				
			}
			if(targetsDone.isEmpty())//if none of the tasks are found,set as null
			{
				newAction.setTargets(null);
			}
			newAction.setTargets(targetsDone);
			newAction.setResults(resultsDone);
		}else // set as invalid if the command fits none of the above
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command.");
		}
		}
		catch(NumberFormatException e)
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command. User might entered an invalid number format.");		
		}
		}
		catch(NumberFormatException e)
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command. User might entered an invalid number format.");		
		}
		
		// target not found, set as null

		// same as update
	}

	private static void deleteAction(EzStorage storage, EzAction newAction,
			String content) {
		ArrayList<EzTask> targetDelete=new ArrayList<EzTask>();
		newAction.setResults(null);
		if(getFirstWord(content).equalsIgnoreCase("all"))//if the command is "delete all on a date"
		{
			content=removeFirstWord(content);
			content=removeFirstWord(content);//now content is suppose to be the date
			if(readDate(content)[DAY_INDEX]>=0)
			{
				
			int[] dateArrDelete=new int[ARRAYSIZE_FOR_DATEARR_WITHOUT_TIME];
			dateArrDelete[DAY_INDEX]=readDate(content)[DAY_INDEX];//find all tasks on the date and assign it to the arraylist.
			dateArrDelete[MONTH_INDEX]=readDate(content)[MONTH_INDEX];
			dateArrDelete[YEAR_INDEX]=readDate(content)[YEAR_INDEX];
			
			Date dateDelete=new Date(dateArrDelete[YEAR_INDEX]-YEAR_DIFFERENCE_FOR_DATE_CONSTRUCTOR,dateArrDelete[MONTH_INDEX]-1,dateArrDelete[DAY_INDEX]);
			
			newAction.setTargets(storage.getTasksByDate(dateDelete));
			}
			else
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. User might entered an invalid date.");
			}
			
		}
		else if(getFirstWord(content).equalsIgnoreCase("from"))//"delete from .. to "
		{
			content=removeFirstWord(content);
			int i,j;
			i=Integer.parseInt(getFirstWord(content));
			content=removeFirstWord(content);
			if(getFirstWord(content).equalsIgnoreCase("to"))
			{
				content=removeFirstWord(content);
			}
			else
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. Invalid keywords.");
			}
			j=Integer.parseInt(content);
			content=removeFirstWord(content);
			if(!content.isEmpty()||i>j)
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("From a later id to an earlier id.");
			}
			for(int k=i;k<=j;k++)
			{
				if(storage.findTask(k)!=null)
				{
				targetDelete.add(storage.findTask(k));
				}
			}
			if(targetDelete.isEmpty())//if none of the tasks are found,set as null
			{
				newAction.setTargets(null);
			}
			newAction.setTargets(targetDelete);
		}
		else if(Integer.parseInt(getFirstWord(content))>=0)//"delete id id id id "
		{
			while(!content.isEmpty())
			{
				if(storage.findTask(Integer.parseInt(getFirstWord(content)))!=null)
				{
				targetDelete.add(storage.findTask(Integer.parseInt(getFirstWord(content))));
				}
				content=removeFirstWord(content);
			}
			if(targetDelete.isEmpty())//if none of the tasks are found,set as null
			{
				newAction.setTargets(null);
			}
			newAction.setTargets(targetDelete);
		}else // set as invalid if the command fits none of the above
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command.");
		}
		
		
		
		//DELETE [id1] [id2] [id3]
			//	 DELETE FROM [startId] TO [endId]
				// DELETE ALL
		// set target as the task to be deleted
		// target not found, set as null
		// set results as null
	}

	private static void updateAction(EzStorage storage, EzAction newAction,
			String content) {
		newAction.setFeedback("Updated successfully!");
		ArrayList<EzTask> targetUpdate = new ArrayList<EzTask>();
		ArrayList<EzTask> resultUpdate = new ArrayList<EzTask>();
		int[] dateUpdate = new int[ARRAYSIZE_FOR_DATEARR_WITH_TIME];
		try
		{
		
		String id = getFirstWord(content);
		content = removeFirstWord(content);
		int index = Integer.parseInt(id);
		if (storage.findTask(index) == null) {
			setTargetsAndResultsNull(newAction);
		} 
		else {
			EzTask taskTarget=storage.findTask(index);
			EzTask taskUpdate = new EzTask(taskTarget);
			
			GregorianCalendar calendarUpdate = new GregorianCalendar();
			if(taskTarget.getStartTime()!=null)
			{
				calendarUpdate=taskTarget.getStartTime();
			}
			

			targetUpdate.add(taskTarget);
			newAction.setTargets(targetUpdate);
			taskUpdate.setId(taskTarget.getId());

			
				if (getFirstWord(content).equalsIgnoreCase("title")) 
				{
					content = removeFirstWord(content);
					if ((content.indexOf("\"") >= 0)
							&& (content.indexOf("\"",
									content.indexOf("\"") + 1) == content
									.lastIndexOf("\""))) {
						content = content.substring(
								content.indexOf("\"") + 1,
								content.lastIndexOf("\""));				
						taskUpdate.setTitle(content);
						resultUpdate.add(taskUpdate);
						newAction.setResults(resultUpdate);
					}
				
				} 
				else if (getFirstWord(content).equalsIgnoreCase("date")) {
					content = removeFirstWord(content);// content is now the
														// date

					dateUpdate[DAY_INDEX] = readDate(content)[DAY_INDEX];
					dateUpdate[MONTH_INDEX] = readDate(content)[MONTH_INDEX];
					dateUpdate[YEAR_INDEX] = readDate(content)[YEAR_INDEX];

					if (readDate(content)[DAY_INDEX] < 0) {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command. User might entered an invalid date.");
					}

					if(taskUpdate.getStartTime()==null&&taskUpdate.getEndTime()==null)
					{
						taskUpdate.setStartTime(new GregorianCalendar(dateUpdate[YEAR_INDEX],dateUpdate[MONTH_INDEX]-1,dateUpdate[DAY_INDEX],0,0));
					    taskUpdate.setEndTime(new GregorianCalendar(dateUpdate[YEAR_INDEX],dateUpdate[MONTH_INDEX]-1,dateUpdate[DAY_INDEX],LAST_HOUR_OF_DAY,LAST_MINUTE_OF_HOUR));
					}
					else 
					{
						taskUpdate.setStartTime(new GregorianCalendar(dateUpdate[YEAR_INDEX],dateUpdate[MONTH_INDEX]-1,dateUpdate[DAY_INDEX],taskUpdate.getStartTime().get(GregorianCalendar.HOUR_OF_DAY),taskUpdate.getStartTime().get(GregorianCalendar.MINUTE)));
					    taskUpdate.setEndTime(new GregorianCalendar(dateUpdate[YEAR_INDEX],dateUpdate[MONTH_INDEX]-1,dateUpdate[DAY_INDEX],taskUpdate.getEndTime().get(GregorianCalendar.HOUR_OF_DAY),taskUpdate.getEndTime().get(GregorianCalendar.MINUTE)));
					}
					resultUpdate.add(taskUpdate);
					newAction.setResults(resultUpdate);

					content = removeFirstWord(content);
					if (!content.isEmpty()) {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command. Invalid keywords.");
					}
				} 
				else if (getFirstWord(content).equalsIgnoreCase("time")) {
					content = removeFirstWord(content);

					dateUpdate[HOUR_INDEX_FOR_DATE_ARR] = readTime(content)[HOUR_INDEX_FOR_READTIME];
					dateUpdate[MINUTE_INDEX_FOR_DATE_ARR] = readTime(content)[MINUTE_INDEX_FOR_READTIME];
					if (readTime(content)[HOUR_INDEX_FOR_READTIME] < 0) {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command. User might entered an invalid time.");
					}
					calendarUpdate.set(GregorianCalendar.HOUR_OF_DAY,
							dateUpdate[HOUR_INDEX_FOR_DATE_ARR]);
					calendarUpdate.set(GregorianCalendar.MINUTE,
							dateUpdate[MINUTE_INDEX_FOR_DATE_ARR]);

					taskUpdate.setStartTime(calendarUpdate);
					taskUpdate.setEndTimeAsStartTime();
					resultUpdate.add(taskUpdate);
					newAction.setResults(resultUpdate);

					content = removeFirstWord(content);
					if (!content.isEmpty()) {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command. Invalid keywords.");
					}
				} else if (getFirstWord(content).equalsIgnoreCase("start")) {
				
				    content=removeFirstWord(content);

					if (getFirstWord(content).equalsIgnoreCase("date")) 
					{
						content = removeFirstWord(content);
				
						dateUpdate[DAY_INDEX] = readDate(content)[DAY_INDEX];
						dateUpdate[MONTH_INDEX] = readDate(content)[MONTH_INDEX];
						dateUpdate[YEAR_INDEX] = readDate(content)[YEAR_INDEX];

						if (readDate(content)[DAY_INDEX] < 0) {
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Invalid command. User might entered an invalid date.");
						}
						calendarUpdate.set(dateUpdate[YEAR_INDEX],dateUpdate[MONTH_INDEX]-1,dateUpdate[DAY_INDEX]);
						if(calendarUpdate.compareTo(taskUpdate.getEndTime())>0)
						{
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Start time is later than end time.");
						}
						
						
						taskUpdate.setStartTime(calendarUpdate);
						resultUpdate.add(taskUpdate);
						newAction.setResults(resultUpdate);

						content = removeFirstWord(content);
					}
					
					if (getFirstWord(content).equalsIgnoreCase("time")) {
						content = removeFirstWord(content);
						dateUpdate[HOUR_INDEX_FOR_DATE_ARR] = readTime(content)[HOUR_INDEX_FOR_READTIME];
						dateUpdate[MINUTE_INDEX_FOR_DATE_ARR] = readTime(content)[MINUTE_INDEX_FOR_READTIME];
						if (readTime(content)[HOUR_INDEX_FOR_READTIME] < 0) {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command. User might entered an invalid time.");
						}
					
						calendarUpdate.set(GregorianCalendar.HOUR_OF_DAY,
								dateUpdate[HOUR_INDEX_FOR_DATE_ARR]);
						calendarUpdate.set(GregorianCalendar.MINUTE,
								dateUpdate[MINUTE_INDEX_FOR_DATE_ARR]);
						if(calendarUpdate.compareTo(taskUpdate.getEndTime())>0)
						{
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Start time is later than end time.");
						}

						taskUpdate.setStartTime(calendarUpdate);
						resultUpdate.add(taskUpdate);
						newAction.setResults(resultUpdate);
					}

					content = removeFirstWord(content);

					if (!content.isEmpty()) {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command. Invalid keywords.");
					}

				} else if (getFirstWord(content).equalsIgnoreCase("end")) {
					calendarUpdate=taskTarget.getEndTime();

					content = removeFirstWord(content);
					if (getFirstWord(content).equalsIgnoreCase("date")) {
						content = removeFirstWord(content);

						dateUpdate[DAY_INDEX] = readDate(content)[DAY_INDEX];
						dateUpdate[MONTH_INDEX] = readDate(content)[MONTH_INDEX];
						dateUpdate[YEAR_INDEX] = readDate(content)[YEAR_INDEX];

						if (readDate(content)[DAY_INDEX] < 0) {
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Invalid command. User might entered an invalid date.");
						}

						calendarUpdate.set(dateUpdate[YEAR_INDEX],dateUpdate[MONTH_INDEX]-1,dateUpdate[DAY_INDEX]);
						if(calendarUpdate.compareTo(taskUpdate.getStartTime())<0)
						{
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("End time is earlier than start time.");
						}
						
						taskUpdate.setEndTime(calendarUpdate);
						resultUpdate.add(taskUpdate);
						newAction.setResults(resultUpdate);

						content = removeFirstWord(content);
					}
					if (getFirstWord(content).equalsIgnoreCase("time")) {
						content = removeFirstWord(content);
						dateUpdate[HOUR_INDEX_FOR_DATE_ARR] = readTime(content)[HOUR_INDEX_FOR_READTIME];
						dateUpdate[MINUTE_INDEX_FOR_DATE_ARR] = readTime(content)[MINUTE_INDEX_FOR_READTIME];
						if (readTime(content)[HOUR_INDEX_FOR_READTIME] < 0) {
							newAction.setAction(TypeOfAction.INVALID);
						}
						calendarUpdate.set(GregorianCalendar.HOUR_OF_DAY,
								dateUpdate[HOUR_INDEX_FOR_DATE_ARR]);
						calendarUpdate.set(GregorianCalendar.MINUTE,
								dateUpdate[MINUTE_INDEX_FOR_DATE_ARR]);
						if(calendarUpdate.compareTo(taskUpdate.getStartTime())<0)
						{
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("End time is earlier than start time.");
						}

						taskUpdate.setEndTime(calendarUpdate);
						resultUpdate.add(taskUpdate);
						newAction.setResults(resultUpdate);
					}
					content = removeFirstWord(content);

					if (!content.isEmpty()) {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command. Invalid keywords.");
					}
					
				}
				else if (getFirstWord(content).equalsIgnoreCase("venue")) {
					content = removeFirstWord(content);

					if ((content.indexOf("\"") >= 0)
							&& (content.indexOf("\"",
									content.indexOf("\"") + 1) == content
									.lastIndexOf("\""))) {
						content = content.substring(
								content.indexOf("\"") + 1,
								content.lastIndexOf("\""));
						taskUpdate.setVenue(content);
						resultUpdate.add(taskUpdate);
						newAction.setResults(resultUpdate);
					}

				} else if (getFirstWord(content).equalsIgnoreCase(
						"priority")) {
					int priorityUpdate;
					content = removeFirstWord(content);
					if ((content.lastIndexOf("*") - content.indexOf("*")) < EzConstants.MAXIMUM_PRIORITY) {
						for (int i = content.indexOf("*"); i <= content
								.lastIndexOf("*"); i++) {
							if (content.charAt(i) != '*') {
						//		newAction.setAction(TypeOfAction.INVALID);
							} // check if wrong input like *adfdfs*
						}
						priorityUpdate = content.lastIndexOf("*")
								- content.indexOf("*") + 1;
						taskUpdate.setPriority(priorityUpdate);
					} else {
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Priority exceeds maximum limit.");
					}

					resultUpdate.add(taskUpdate);
					newAction.setResults(resultUpdate);
				}
			
		}
		}
		catch(NumberFormatException e)
		{
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Invalid command. User might entered an invalid number format.");
		}
	}

	private static void addAction(EzAction newAction, String content) {
		newAction.setFeedback("Added successfully!");//feedback
		ArrayList<EzTask> targetAdd = new ArrayList<EzTask>();
		assert(newAction.getAction()!=null);
		EzTask task = new EzTask();
		String title = new String();
		if ((content.indexOf("\"") < 0) || (content.indexOf("\"",content.indexOf("\"")+ 1) < 0)
				|| !content.startsWith("\""))
			{
			newAction.setAction(TypeOfAction.INVALID);
			
		    newAction.setFeedback("Invalid command.");
			}
		else
		{
			title = content.substring(content.indexOf("\"") + 1,
				content.indexOf("\"", 1));
		content = content.substring(content.indexOf("\"", content.indexOf("\"")+1)+1).trim();
		task.setTitle(title);
		}
		String location = new String();
		location = null;
		

		if ((content.indexOf("\"") >= 0)
				&& (content.indexOf("\"", content.indexOf("\"") + 1) == content
						.lastIndexOf("\"")))// there is another "***",it
											// must be the location
		{
			location = content.substring(content.indexOf("\"") + 1,
					content.indexOf("\"", content.indexOf("\"") + 1));
			
			String before = new String();
			String after = new String();
			before = content.substring(0, content.indexOf("\""));
			after = content.substring(content.indexOf("\"",
					content.indexOf("\"") + 1) + 1).trim();
			before = before.trim();
			
			if ((before.length()>=2)&&
					before.substring(before.length() - 2)
					.equalsIgnoreCase("at"))// remove the "at" before
											// location if there is an "at"
			{
				before = before.substring(0, before.length()-2);
			}
			content = before + after;// command without "location"
		}
		task.setVenue(location);

		if (content.indexOf("\"") >= 0) {
		newAction.setAction(TypeOfAction.INVALID);
		newAction.setFeedback("Extra or missing \" in the command.");
		}// if there is more ",the command is invalid.

		if (checkMultipleAction(content) == true) {
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Multiple types of action.");
		}// see if there is other keywords for action.

		int priority = 0;
		// take priority if have.
		// after take out priority, there should be only time,date
		if (content.indexOf("*") >= 0)// see if there is any * indicating
										// priority
		{
			if ((content.lastIndexOf("*") - content.indexOf("*")) <= EzConstants.MAXIMUM_PRIORITY-1)// maximum
																		// priority
																		// is
																		// 3
			{
				for (int i = content.indexOf("*"); i <= content
						.lastIndexOf("*"); i++) {
					if (content.charAt(i) != '*') {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid priority.");
					} // check if wrong input like *adfdfs*
				}
				priority = content.lastIndexOf("*") - content.indexOf("*")
						+ 1;

			} 
			else {
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Priority exceeds maximum limit");
			}
			content = content.replaceAll("\\*", "");// remove all "*"
		}
		task.setPriority(priority);

		if (checkMultipleAction(content) == true) {
			newAction.setAction(TypeOfAction.INVALID);
			newAction.setFeedback("Multiple types of action");
		}// see if there is other keywords for action.

		GregorianCalendar calendar = new GregorianCalendar();
		content = content.trim();
		content = content.toLowerCase();
		// deal with keyword today,tomorrow
		if (content.indexOf("today") >= 0) 
		{
			if(!(content.indexOf("from")>=0&&content.indexOf("today")>=content.indexOf("from")))
			{
			content = content.replace("today", "");
			
			task.setStartTime(calendar.get(GregorianCalendar.YEAR),calendar.get(GregorianCalendar.MONTH),calendar.get(GregorianCalendar.DAY_OF_MONTH));
		    task.setEndTime(LAST_HOUR_OF_DAY,LAST_MINUTE_OF_HOUR);
			}
		}
		if (content.indexOf("tomorrow") >= 0) 
		{
			if(!(content.indexOf("from")>=0&&content.indexOf("tomorrow")>=content.indexOf("from")))
			{
			content = content.replace("tomorrow", "");
		
			calendar.set(GregorianCalendar.DATE, calendar.get(GregorianCalendar.DATE)+1);
			task.setStartTime(calendar.get(GregorianCalendar.YEAR),calendar.get(GregorianCalendar.MONTH),calendar.get(GregorianCalendar.DAY_OF_MONTH));
		    task.setEndTimeAsStartTime();
			task.setEndTime(LAST_HOUR_OF_DAY,LAST_MINUTE_OF_HOUR);
			}
		}

		content = content.trim();

		int[] dateArr = new int[ARRAYSIZE_FOR_DATEARR_WITH_TIME];
		String date = new String();
		String time = new String();
		if (getFirstWord(content).equalsIgnoreCase("on")) {//on **/**/****
			content = removeFirstWord(content);
			date = getFirstWord(content);
			content = removeFirstWord(content);
			dateArr[DAY_INDEX] = readDate(date)[DAY_INDEX];
			dateArr[MONTH_INDEX] = readDate(date)[MONTH_INDEX];
			dateArr[YEAR_INDEX] = readDate(date)[YEAR_INDEX];
			if (readDate(date)[DAY_INDEX] < 0) {
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. User might entered an invalid date.");
			}
			calendar = new GregorianCalendar(dateArr[YEAR_INDEX], dateArr[MONTH_INDEX] - 1,
					dateArr[DAY_INDEX]);
			task.setStartTime(calendar);
			task.setEndTimeAsStartTime();
		
			if (!getFirstWord(content).equalsIgnoreCase("from")
					&& !content.isEmpty()) {
				if (getFirstWord(content).equalsIgnoreCase("at"))//on **** at ****
					content = removeFirstWord(content);
				time = getFirstWord(content);
				content = removeFirstWord(content);
				dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(time)[HOUR_INDEX_FOR_READTIME];
				dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(time)[MINUTE_INDEX_FOR_READTIME];
				calendar = new GregorianCalendar(dateArr[YEAR_INDEX],
						dateArr[MONTH_INDEX] - 1, dateArr[DAY_INDEX], dateArr[HOUR_INDEX_FOR_DATE_ARR], dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
				task.setStartTime(calendar);
				task.setEndTimeAsStartTime();
			} else if (content.isEmpty())// if empty means no time, then set it as whole day.
			{
				task.setEndTime(LAST_HOUR_OF_DAY,LAST_MINUTE_OF_HOUR);
			} 
		}
		if (getFirstWord(content).equalsIgnoreCase("at")) {//at **:**
			content = removeFirstWord(content);
			time = getFirstWord(content);
			content = removeFirstWord(content);
			dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(time)[HOUR_INDEX_FOR_READTIME];
			dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(time)[MINUTE_INDEX_FOR_READTIME];
			if (readTime(time)[HOUR_INDEX_FOR_READTIME] < 0) {
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. User might entered an invalid time.");
			}
			calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[HOUR_INDEX_FOR_DATE_ARR]);
			calendar.set(GregorianCalendar.MINUTE, dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
			task.setStartTime(dateArr[HOUR_INDEX_FOR_DATE_ARR],dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
			task.setEndTimeAsStartTime();

			if (!getFirstWord(content).equalsIgnoreCase("from")
					&& !content.isEmpty()) {
				if (getFirstWord(content).equalsIgnoreCase("on"))//at ** on ***
					content = removeFirstWord(content);

				date = getFirstWord(content);
				content = removeFirstWord(content);
				dateArr[DAY_INDEX] = readDate(date)[DAY_INDEX];
				dateArr[MONTH_INDEX] = readDate(date)[MONTH_INDEX];
				dateArr[YEAR_INDEX] = readDate(date)[YEAR_INDEX];
				if (readDate(date)[DAY_INDEX] < 0) {
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command. User might entered an invalid date.");
				}
				calendar = new GregorianCalendar(dateArr[YEAR_INDEX],
						dateArr[MONTH_INDEX] - 1, dateArr[DAY_INDEX], dateArr[HOUR_INDEX_FOR_DATE_ARR], dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
				task.setStartTime(calendar);
				task.setEndTimeAsStartTime();

			} else if (content.isEmpty())// if empty means no time,then no
											// need to do anything because
											// date is set.
			{
			} else {
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. Invalid keywords.");
			}
			if (!content.isEmpty()) {
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. Invalid keywords.");
			}
		}

		if (getFirstWord(content).equalsIgnoreCase("from")) {//from *** to ***
			content = removeFirstWord(content);
			String temp = content;
			
			int count1 = 0;
			while (!temp.isEmpty()) {
				if (!getFirstWord(temp).equalsIgnoreCase("to")) {
					temp = removeFirstWord(temp);
					count1++;
				} else {
					temp = removeFirstWord(temp);
					break;
				}
			}
			int count2 = 0;
			while (!temp.isEmpty()) {
				temp = removeFirstWord(temp);
				count2++;
			}
			if (count1 == count2 && count1 <= 2 && count1 > 0) {
				if (count1 == 1) {//from *** to ***
					String start = getFirstWord(content);
					content=removeFirstWord(content);
					content=removeFirstWord(content);
					String end = getFirstWord(content);
					content=removeFirstWord(content);


					if (readDate(start)[DAY_INDEX] >= 0 && readDate(end)[DAY_INDEX] >= 0) {
						dateArr[DAY_INDEX] = readDate(start)[DAY_INDEX];
						dateArr[MONTH_INDEX] = readDate(start)[MONTH_INDEX];
						dateArr[YEAR_INDEX] = readDate(start)[YEAR_INDEX];
						calendar = new GregorianCalendar(dateArr[YEAR_INDEX],
								dateArr[MONTH_INDEX] - 1, dateArr[DAY_INDEX]);
						task.setStartTime(calendar);
						dateArr[DAY_INDEX] = readDate(end)[DAY_INDEX];
						dateArr[MONTH_INDEX] = readDate(end)[MONTH_INDEX];
						dateArr[YEAR_INDEX] = readDate(end)[YEAR_INDEX];
						calendar = new GregorianCalendar(dateArr[YEAR_INDEX],
								dateArr[MONTH_INDEX] - 1, dateArr[DAY_INDEX],LAST_HOUR_OF_DAY,LAST_MINUTE_OF_HOUR);
						task.setEndTime(calendar);
						if(
								(readDate(start)[YEAR_INDEX]>readDate(end)[YEAR_INDEX])
								||((readDate(start)[YEAR_INDEX]==readDate(end)[YEAR_INDEX])&&(readDate(start)[MONTH_INDEX]>readDate(end)[MONTH_INDEX]))
								||((readDate(start)[YEAR_INDEX]==readDate(end)[YEAR_INDEX])&&(readDate(start)[MONTH_INDEX]==readDate(end)[MONTH_INDEX])
										&&(readDate(start)[DAY_INDEX]>readDate(end)[DAY_INDEX]))
								)//from an later date to an earlier date
						{
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("The task is from a later date to an earlier date!");
						}
					} else if (readTime(start)[HOUR_INDEX_FOR_READTIME] >= 0
							&& readTime(end)[HOUR_INDEX_FOR_READTIME] >= 0) {
						if(
								(readTime(start)[HOUR_INDEX_FOR_READTIME]<readTime(end)[HOUR_INDEX_FOR_READTIME])||
								((readTime(start)[HOUR_INDEX_FOR_READTIME]==readTime(end)[HOUR_INDEX_FOR_READTIME])&&(readTime(start)[MINUTE_INDEX_FOR_READTIME]<readTime(end)[MINUTE_INDEX_FOR_READTIME]))
										)
						{
						dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(start)[HOUR_INDEX_FOR_READTIME];
						dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(start)[MINUTE_INDEX_FOR_READTIME];
						calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[HOUR_INDEX_FOR_DATE_ARR]);
						calendar.set(GregorianCalendar.MINUTE, dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
						task.setStartTime(calendar);
						dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(end)[HOUR_INDEX_FOR_READTIME];
						dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(end)[MINUTE_INDEX_FOR_READTIME];
						calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[HOUR_INDEX_FOR_DATE_ARR]);
						calendar.set(GregorianCalendar.MINUTE, dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
						task.setEndTime(calendar);
						}
						else
						{
							dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(start)[HOUR_INDEX_FOR_READTIME];
							dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(start)[MINUTE_INDEX_FOR_READTIME];
							calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[HOUR_INDEX_FOR_DATE_ARR]);
							calendar.set(GregorianCalendar.MINUTE, dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
							task.setStartTime(calendar);
							dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(end)[HOUR_INDEX_FOR_READTIME];
							dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(end)[MINUTE_INDEX_FOR_READTIME];
							calendar.set(GregorianCalendar.DAY_OF_MONTH,calendar.get(GregorianCalendar.DAY_OF_MONTH)+1);
							calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[HOUR_INDEX_FOR_DATE_ARR]);
							calendar.set(GregorianCalendar.MINUTE, dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
							task.setEndTime(calendar);
						}
					}
					else 
					{
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command. User might entered an invalid date or time.");
					}

				} else {//from *** *** to *** ***
					String start1 = getFirstWord(content);
					content=removeFirstWord(content);
					String start2 = getFirstWord(content);
					content=removeFirstWord(content);
					content=removeFirstWord(content);
					String end1 = getFirstWord(content);
					content=removeFirstWord(content);
					String end2 = getFirstWord(content);
					content=removeFirstWord(content);
					if ((readDate(start1)[DAY_INDEX] >= 0 && readDate(end1)[DAY_INDEX] >= 0)
							&& (readTime(start2)[HOUR_INDEX_FOR_READTIME] >= 0 && readTime(end2)[HOUR_INDEX_FOR_READTIME] >= 0)) {
						dateArr[DAY_INDEX] = readDate(start1)[DAY_INDEX];
						dateArr[MONTH_INDEX] = readDate(start1)[MONTH_INDEX];
						dateArr[YEAR_INDEX] = readDate(start1)[YEAR_INDEX];
						calendar = new GregorianCalendar(dateArr[YEAR_INDEX],
								dateArr[MONTH_INDEX] - 1, dateArr[DAY_INDEX]);
						
						dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(start2)[HOUR_INDEX_FOR_READTIME];
						dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(start2)[MINUTE_INDEX_FOR_READTIME];
						calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[HOUR_INDEX_FOR_DATE_ARR]);
						calendar.set(GregorianCalendar.MINUTE, dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
						task.setStartTime(calendar);
						
						dateArr[DAY_INDEX] = readDate(end1)[DAY_INDEX];
						dateArr[MONTH_INDEX] = readDate(end1)[MONTH_INDEX];
						dateArr[YEAR_INDEX] = readDate(end1)[YEAR_INDEX];
						calendar = new GregorianCalendar(dateArr[YEAR_INDEX],
								dateArr[MONTH_INDEX] - 1, dateArr[DAY_INDEX]);
			
						dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(end2)[HOUR_INDEX_FOR_READTIME];
						dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(end2)[MINUTE_INDEX_FOR_READTIME];
						calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[HOUR_INDEX_FOR_DATE_ARR]);
						calendar.set(GregorianCalendar.MINUTE, dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
						task.setEndTime(calendar);
					} else if ((readDate(start2)[DAY_INDEX] >= 0 && readDate(end2)[DAY_INDEX] >= 0)
							&& (readTime(start1)[HOUR_INDEX_FOR_READTIME] >= 0 && readTime(end1)[HOUR_INDEX_FOR_READTIME] >= 0)) {
						dateArr[DAY_INDEX] = readDate(start2)[DAY_INDEX];
						dateArr[MONTH_INDEX] = readDate(start2)[MONTH_INDEX];
						dateArr[YEAR_INDEX] = readDate(start2)[YEAR_INDEX];
						calendar = new GregorianCalendar(dateArr[YEAR_INDEX],
								dateArr[MONTH_INDEX] - 1, dateArr[DAY_INDEX]);
						dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(start1)[HOUR_INDEX_FOR_READTIME];
						dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(start1)[MINUTE_INDEX_FOR_READTIME];
						calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[HOUR_INDEX_FOR_DATE_ARR]);
						calendar.set(GregorianCalendar.MINUTE, dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
						task.setStartTime(calendar);
						
						dateArr[DAY_INDEX] = readDate(end2)[DAY_INDEX];
						dateArr[MONTH_INDEX] = readDate(end2)[MONTH_INDEX];
						dateArr[YEAR_INDEX] = readDate(end2)[YEAR_INDEX];
						calendar = new GregorianCalendar(dateArr[YEAR_INDEX],
								dateArr[MONTH_INDEX] - 1, dateArr[DAY_INDEX]);
						
						dateArr[HOUR_INDEX_FOR_DATE_ARR] = readTime(end1)[HOUR_INDEX_FOR_READTIME];
						dateArr[MINUTE_INDEX_FOR_DATE_ARR] = readTime(end1)[MINUTE_INDEX_FOR_READTIME];
						calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[HOUR_INDEX_FOR_DATE_ARR]);
						calendar.set(GregorianCalendar.MINUTE, dateArr[MINUTE_INDEX_FOR_DATE_ARR]);
						task.setEndTime(calendar);
					} else {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command. Invalid keywords.");
					}
				}
			} 
			else {
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command. Invalid keywords.");
			}
		}

		targetAdd.add(task);

		newAction.setTargets(null);
		newAction.setResults(targetAdd);
	}

	private static TypeOfAction getAction(String userCommand) {

		String action = getFirstWord(userCommand);  
		if (action.equalsIgnoreCase("add"))
			return TypeOfAction.ADD;
		else if (action.equalsIgnoreCase("update"))
			return TypeOfAction.UPDATE;
		else if (action.equalsIgnoreCase("delete"))
			return TypeOfAction.DELETE;
		else if (action.equalsIgnoreCase("undo"))
			return TypeOfAction.UNDO;
		else if (action.equalsIgnoreCase("redo"))
			return TypeOfAction.REDO;
		else if (action.equalsIgnoreCase("show"))
			return TypeOfAction.SHOW;
		else if (action.equalsIgnoreCase("help"))
			return TypeOfAction.HELP;
		else if (action.equalsIgnoreCase("done"))
			return TypeOfAction.DONE;
		else if (action.equalsIgnoreCase("y"))
			return TypeOfAction.Y;
		else if (action.equalsIgnoreCase("n"))
			return TypeOfAction.N;
		else if (action.equalsIgnoreCase("sort"))
			return TypeOfAction.SORT;
		else if (action.equalsIgnoreCase("undone"))
			return TypeOfAction.UNDONE;
		else if (action.equalsIgnoreCase("remove"))
			return TypeOfAction.REMOVE;
		else if (action.equalsIgnoreCase("page"))
			return TypeOfAction.PAGE;
		
		
		
		

		return TypeOfAction.INVALID;
	}

	private static String getFirstWord(String input) {
		String firstword = new String();

		if (input.indexOf(" ") >= 0) {
			firstword = input.substring(0, input.indexOf(" "));
			firstword = firstword.trim();
		} else 
		if(input.isEmpty())
		{
			return "";
		}
		else{
			firstword=input;
		}
		return firstword;
	}

	private static String removeFirstWord(String input) {
		String output = new String();
		if (input.indexOf(" ") >= 0) {
			output = input.substring(input.indexOf(" "));
			output = output.trim();
		} else {
			output = "";
		}
		return output;
	}

	private static int[] readDate(String date)// return int[DAY_INDEX] as -1 if the date
												// is invalid
	throws NumberFormatException
	{
		int[] results = new int[ARRAYSIZE_FOR_DATEARR_WITHOUT_TIME];
		try
		{
		
	
		date = date.trim();
		if(date.equalsIgnoreCase("today"))//today
		{
             GregorianCalendar calendar=new GregorianCalendar();

             results[DAY_INDEX]=calendar.get(GregorianCalendar.DAY_OF_MONTH);
           	 results[MONTH_INDEX]=calendar.get(GregorianCalendar.MONTH)+1;
           	 results[YEAR_INDEX]=calendar.get(GregorianCalendar.YEAR);
           	 return results;
			   
		}
		if(date.equalsIgnoreCase("tomorrow"))//tomorrow
		{
			GregorianCalendar calendar=new GregorianCalendar();
			calendar.add(GregorianCalendar.DATE, 1);

            results[DAY_INDEX]=calendar.get(GregorianCalendar.DAY_OF_MONTH);
          	results[MONTH_INDEX]=calendar.get(GregorianCalendar.MONTH)+1;
          	results[YEAR_INDEX]=calendar.get(GregorianCalendar.YEAR);
          	return results;
		}
		if ((date.indexOf("/") >= 0)
				&& (date.indexOf("/", date.indexOf("/") + 1) >= 0)
				&& (date.indexOf("/", date.indexOf("/") + 1) == date
						.lastIndexOf("/"))) {
			results[DAY_INDEX] = Integer.parseInt(date.substring(0, date.indexOf("/")));
			results[MONTH_INDEX] = Integer.parseInt(date.substring(date.indexOf("/") + 1,
					date.lastIndexOf("/")));
			results[YEAR_INDEX] = Integer
					.parseInt(date.substring(date.lastIndexOf("/") + 1));
		} else if ((date.indexOf(".") >= 0)
				&& (date.indexOf(".", date.indexOf(".") + 1) >= 0)
				&& (date.indexOf(".", date.indexOf(".") + 1) == date
						.lastIndexOf("."))) {
			results[DAY_INDEX] = Integer.parseInt(date.substring(0, date.indexOf(".")));
			results[MONTH_INDEX] = Integer.parseInt(date.substring(date.indexOf(".") + 1,
					date.lastIndexOf(".")));
			results[YEAR_INDEX] = Integer
					.parseInt(date.substring(date.lastIndexOf(".") + 1));
			if ((results[DAY_INDEX] > MAX_DAY_OF_MONTH) || (results[DAY_INDEX] <= 0) || (results[MONTH_INDEX] > MAX_MONTH_OF_YEAR)
					|| (results[MONTH_INDEX] <= 0) || (results[YEAR_INDEX] <= 0)
					|| ((results[YEAR_INDEX] >= 100) && (results[YEAR_INDEX] < 1970))) {
				results[DAY_INDEX] = -1;
			}
			if (results[YEAR_INDEX] < 100) {
				results[YEAR_INDEX] = results[YEAR_INDEX] + 2000;
			}
		} 
		else if((date.indexOf("/")>=0)&&(date.indexOf("/")==date.lastIndexOf("/")))
		{
			results[DAY_INDEX] = Integer.parseInt(date.substring(0, date.indexOf("/")));
			results[MONTH_INDEX] = Integer.parseInt(date.substring(date.indexOf("/")+1));
			GregorianCalendar calendar=new GregorianCalendar();
			results[YEAR_INDEX]=calendar.get(GregorianCalendar.YEAR);		
			if ((results[DAY_INDEX] > MAX_DAY_OF_MONTH) || (results[DAY_INDEX] <= 0) || (results[MONTH_INDEX] > MAX_MONTH_OF_YEAR)
					|| (results[MONTH_INDEX] <= 0) ) 
			{
				results[DAY_INDEX] = -1;
			}
		}
		else if((date.indexOf(".")>=0)&&(date.indexOf(".")==date.lastIndexOf(".")))
		{
			results[DAY_INDEX] = Integer.parseInt(date.substring(0, date.indexOf(".")));
			results[MONTH_INDEX] = Integer.parseInt(date.substring(date.indexOf(".")+1));
			GregorianCalendar calendar=new GregorianCalendar();
			results[YEAR_INDEX]=calendar.get(GregorianCalendar.YEAR);		
			if ((results[DAY_INDEX] > MAX_DAY_OF_MONTH) || (results[DAY_INDEX] <= 0) || (results[MONTH_INDEX] > MAX_MONTH_OF_YEAR)
					|| (results[MONTH_INDEX] <= 0) ) 
			{
				results[DAY_INDEX] = -1;
			}
		}
		else
			results[DAY_INDEX] = -1;

		return results;// need a method to analyze the date int[]
						// readDate(String date) dd/mm/yy, dd/mm/yyyy,
						// dd.mm.yy,dd.mm.yyyy
		}
		catch (NumberFormatException e)
		{
			results[DAY_INDEX]= -1;
		}
		return results;

	}

	private static int[] readTime(String time)// return int[HOUR_INDEX_FOR_READTIME] as -1 if the time
												// is invalid.
	throws NumberFormatException
	{
		int[] results = new int[2];
		time = time.trim();
		boolean isPM=false;
	try{
		if (time.length()>=2&&(time.substring(time.length() - 2).equalsIgnoreCase("am")
				|| time.substring(time.length() - 2).equalsIgnoreCase("pm"))) {
			if (time.substring(time.length() - 2).equalsIgnoreCase("pm")) {
				isPM=true;
				results[HOUR_INDEX_FOR_READTIME] = HOUR_DIFFERENCE_BETWEEN_AM_PM;
			}
			time = time.substring(0, time.length() - 2);
			time.trim();
		}
		if ((time.indexOf(":") >= 0)
				&& ((time.indexOf(":") == time.lastIndexOf(":")))) {
			results[HOUR_INDEX_FOR_READTIME] = results[HOUR_INDEX_FOR_READTIME]
					+ Integer.parseInt(time.substring(0, time.indexOf(":"))
							.trim());
			results[MINUTE_INDEX_FOR_READTIME] = Integer.parseInt(time.substring(time.indexOf(":") + 1)
					.trim());
			if (results[HOUR_INDEX_FOR_READTIME] > MAX_HOUR_OF_DAY || results[HOUR_INDEX_FOR_READTIME] < 0 || results[MINUTE_INDEX_FOR_READTIME] > LAST_MINUTE_OF_HOUR
					|| results[MINUTE_INDEX_FOR_READTIME] < 0) {
				results[HOUR_INDEX_FOR_READTIME] = -1;
			}

		} else if (time.indexOf("h") >= 0
				&& time.indexOf("h") == time.lastIndexOf("h")) {
			results[HOUR_INDEX_FOR_READTIME] = Integer.parseInt(time.substring(0, time.indexOf("h"))
					.trim());
			if (!time.substring(time.indexOf("h") + 1).trim().isEmpty()) {
				results[MINUTE_INDEX_FOR_READTIME] = Integer.parseInt(time.substring(
						time.indexOf("h") + 1).trim());
			}
			if (results[HOUR_INDEX_FOR_READTIME] > MAX_HOUR_OF_DAY || results[HOUR_INDEX_FOR_READTIME] < 0 || results[MINUTE_INDEX_FOR_READTIME] > LAST_MINUTE_OF_HOUR
					|| results[MINUTE_INDEX_FOR_READTIME] < 0) {
				results[HOUR_INDEX_FOR_READTIME] = -1;
			}
		} 
		else if(Integer.parseInt(time)>=0)
		{
			results[HOUR_INDEX_FOR_READTIME]=results[HOUR_INDEX_FOR_READTIME]+Integer.parseInt(time);
		}
		else
			results[HOUR_INDEX_FOR_READTIME] = -1;
		if(isPM==true&&results[HOUR_INDEX_FOR_READTIME]==MAX_HOUR_OF_DAY&&results[MINUTE_INDEX_FOR_READTIME]==0)
		{
			results[HOUR_INDEX_FOR_READTIME]=HOUR_DIFFERENCE_BETWEEN_AM_PM;
		}
		if(isPM==false&&results[HOUR_INDEX_FOR_READTIME]==HOUR_DIFFERENCE_BETWEEN_AM_PM&&results[MINUTE_INDEX_FOR_READTIME]==0)
		{
			results[HOUR_INDEX_FOR_READTIME]=0;
		}
	}
	catch(NumberFormatException e)
	{
		results[HOUR_INDEX_FOR_READTIME]=-1;
	}
		
		return results;// int[] readTime(String time) only accept 10h, 10:00,
						// 10am
	}

	private static boolean checkMultipleAction(String command) {
		ArrayList<String> listOfAction = new ArrayList<String>();

		listOfAction.add("add");
		listOfAction.add("upate");
		listOfAction.add("delete");
		listOfAction.add("undo");
		listOfAction.add("redo");
		listOfAction.add("show");
		listOfAction.add("help");
		listOfAction.add("invalid");
		listOfAction.add("sort");
		listOfAction.add("undone");
		listOfAction.add("done");
		listOfAction.add("page");
		listOfAction.add("remove");

		String word = getFirstWord(command);
		while (command.isEmpty() != true) {
			for (int i = 0; i < listOfAction.size(); i++) {
				if (word.equalsIgnoreCase(listOfAction.get(i))) {
					return true;
				}
			}
			command = removeFirstWord(command);
		}

		return false;

	}
	
	private static ArrayList<EzTask> getDoneTasks(ArrayList<EzTask> input)
	{
		ArrayList<EzTask> results=new ArrayList<EzTask>();
		for(int i=0;i<input.size();i++)
		{
			if(input.get(i).isDone())
			{
				results.add(input.get(i));
			}
		}
		
		return results;
	}
	private static ArrayList<EzTask> getUndoneTasks(ArrayList<EzTask> input)
	{
		ArrayList<EzTask> results=new ArrayList<EzTask>();
		for(int i=0;i<input.size();i++)
		{
			if(!input.get(i).isDone())
			{
				results.add(input.get(i));
			}
		}
		return results;
	}

}
