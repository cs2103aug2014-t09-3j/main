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
 REMOVE TIME [id]: set time as from 00:00 to 23:59
 
 SORT ID
 SORT TITLE
 SORT VENUE
 SORT DATE
 SORT PRIORITY
 SORT DONE
 
 PAGE [page number]: set both target and result as null and set the page number and the type of action as page
 *
 */

/**
 * @author Khanh Cao Tianwen
 */

public class EzParser {
	public static EzAction extractInfo(String userCommand, EzStorage storage) {
		EzAction newAction = new EzAction();
		newAction.setAction(getAction(userCommand));
		String content = removeFirstWord(userCommand);
		switch (newAction.getAction()) {// content starts with no Action
		case ADD:
			newAction.setFeedback("Add successfully!");//feedback
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
			    task.setEndTime(23,59);
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
				task.setEndTime(23,59);
				}
			}

			content = content.trim();

			int[] dateArr = new int[5];
			String date = new String();
			String time = new String();
			if (getFirstWord(content).equalsIgnoreCase("on")) {//on **/**/****
				content = removeFirstWord(content);
				date = getFirstWord(content);
				content = removeFirstWord(content);
				dateArr[0] = readDate(date)[0];
				dateArr[1] = readDate(date)[1];
				dateArr[2] = readDate(date)[2];
				if (readDate(date)[0] < 0) {
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command.");
				}
				calendar = new GregorianCalendar(dateArr[2], dateArr[1] - 1,
						dateArr[0]);
				task.setStartTime(calendar);
				task.setEndTimeAsStartTime();
			
				if (!getFirstWord(content).equalsIgnoreCase("from")
						&& !content.isEmpty()) {
					if (getFirstWord(content).equalsIgnoreCase("at"))//on **** at ****
						content = removeFirstWord(content);
					time = getFirstWord(content);
					content = removeFirstWord(content);
					dateArr[3] = readTime(time)[0];
					dateArr[4] = readTime(time)[1];
					calendar = new GregorianCalendar(dateArr[2],
							dateArr[1] - 1, dateArr[0], dateArr[3], dateArr[4]);
					task.setStartTime(calendar);
					task.setEndTimeAsStartTime();
				} else if (content.isEmpty())// if empty means no time, then set it as whole day.
				{
					task.setEndTime(23,59);
				} 
			}
			if (getFirstWord(content).equalsIgnoreCase("at")) {//at **:**
				content = removeFirstWord(content);
				time = getFirstWord(content);
				content = removeFirstWord(content);
				dateArr[3] = readTime(time)[0];
				dateArr[4] = readTime(time)[1];
				if (readTime(time)[0] < 0) {
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command.");
				}
				calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[3]);
				calendar.set(GregorianCalendar.MINUTE, dateArr[4]);
				task.setStartTime(dateArr[3],dateArr[4]);
				task.setEndTimeAsStartTime();

				if (!getFirstWord(content).equalsIgnoreCase("from")
						&& !content.isEmpty()) {
					if (getFirstWord(content).equalsIgnoreCase("on"))//at ** on ***
						content = removeFirstWord(content);

					date = getFirstWord(content);
					content = removeFirstWord(content);
					dateArr[0] = readDate(date)[0];
					dateArr[1] = readDate(date)[1];
					dateArr[2] = readDate(date)[2];
					if (readDate(date)[0] < 0) {
						newAction.setAction(TypeOfAction.INVALID);
						newAction.setFeedback("Invalid command.");
					}
					calendar = new GregorianCalendar(dateArr[2],
							dateArr[1] - 1, dateArr[0], dateArr[3], dateArr[4]);
					task.setStartTime(calendar);
					task.setEndTimeAsStartTime();

				} else if (content.isEmpty())// if empty means no time,then no
												// need to do anything because
												// date is set.
				{
				} else {
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command. Cannot recongize keywords.");
				}
				if (!content.isEmpty()) {
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command. Cannot recongize keywords.");
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
	

						if (readDate(start)[0] >= 0 && readDate(end)[0] >= 0) {
							dateArr[0] = readDate(start)[0];
							dateArr[1] = readDate(start)[1];
							dateArr[2] = readDate(start)[2];
							calendar = new GregorianCalendar(dateArr[2],
									dateArr[1] - 1, dateArr[0]);
							task.setStartTime(calendar);
							dateArr[0] = readDate(end)[0];
							dateArr[1] = readDate(end)[1];
							dateArr[2] = readDate(end)[2];
							calendar = new GregorianCalendar(dateArr[2],
									dateArr[1] - 1, dateArr[0],23,59);
							task.setEndTime(calendar);
							if(
									(readDate(start)[2]>readDate(end)[2])
									||((readDate(start)[2]==readDate(end)[2])&&(readDate(start)[1]>readDate(end)[1]))
									||((readDate(start)[2]==readDate(end)[2])&&(readDate(start)[1]==readDate(end)[1])
											&&(readDate(start)[0]>readDate(end)[0]))
									)//from an later date to an earlier date
							{
								newAction.setAction(TypeOfAction.INVALID);
								newAction.setFeedback("From a later date to an earlier date!");
							}
						} else if (readTime(start)[0] >= 0
								&& readTime(end)[0] >= 0) {
							if(
									(readTime(start)[0]<readTime(end)[0])||
									((readTime(start)[0]==readTime(end)[0])&&(readTime(start)[1]>readTime(end)[1]))
											)
							{
							dateArr[3] = readTime(start)[0];
							dateArr[4] = readTime(start)[1];
							calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[3]);
							calendar.set(GregorianCalendar.MINUTE, dateArr[4]);
							task.setStartTime(calendar);
							dateArr[3] = readTime(end)[0];
							dateArr[4] = readTime(end)[1];
							calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[3]);
							calendar.set(GregorianCalendar.MINUTE, dateArr[4]);
							task.setEndTime(calendar);
							}
							else
							{
								dateArr[3] = readTime(start)[0];
								dateArr[4] = readTime(start)[1];
								calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[3]);
								calendar.set(GregorianCalendar.MINUTE, dateArr[4]);
								task.setStartTime(calendar);
								dateArr[3] = readTime(end)[0];
								dateArr[4] = readTime(end)[1];
								calendar.set(GregorianCalendar.DAY_OF_MONTH,calendar.get(GregorianCalendar.DAY_OF_MONTH)+1);
								calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[3]);
								calendar.set(GregorianCalendar.MINUTE, dateArr[4]);
								task.setEndTime(calendar);
							}
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
						if ((readDate(start1)[0] >= 0 && readDate(end1)[0] >= 0)
								&& (readTime(start2)[0] >= 0 && readTime(end2)[0] >= 0)) {
							dateArr[0] = readDate(start1)[0];
							dateArr[1] = readDate(start1)[1];
							dateArr[2] = readDate(start1)[2];
							calendar = new GregorianCalendar(dateArr[2],
									dateArr[1] - 1, dateArr[0]);
							
							dateArr[3] = readTime(start2)[0];
							dateArr[4] = readTime(start2)[1];
							calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[3]);
							calendar.set(GregorianCalendar.MINUTE, dateArr[4]);
							task.setStartTime(calendar);
							
							dateArr[0] = readDate(end1)[0];
							dateArr[1] = readDate(end1)[1];
							dateArr[2] = readDate(end1)[2];
							calendar = new GregorianCalendar(dateArr[2],
									dateArr[1] - 1, dateArr[0]);
				
							dateArr[3] = readTime(end2)[0];
							dateArr[4] = readTime(end2)[1];
							calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[3]);
							calendar.set(GregorianCalendar.MINUTE, dateArr[4]);
							task.setEndTime(calendar);
						} else if ((readDate(start2)[0] >= 0 && readDate(end2)[0] >= 0)
								&& (readTime(start1)[0] >= 0 && readTime(end1)[0] >= 0)) {
							dateArr[0] = readDate(start2)[0];
							dateArr[1] = readDate(start2)[1];
							dateArr[2] = readDate(start2)[2];
							calendar = new GregorianCalendar(dateArr[2],
									dateArr[1] - 1, dateArr[0]);
							dateArr[3] = readTime(start1)[0];
							dateArr[4] = readTime(start1)[1];
							calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[3]);
							calendar.set(GregorianCalendar.MINUTE, dateArr[4]);
							task.setStartTime(calendar);
							
							dateArr[0] = readDate(end2)[0];
							dateArr[1] = readDate(end2)[1];
							dateArr[2] = readDate(end2)[2];
							calendar = new GregorianCalendar(dateArr[2],
									dateArr[1] - 1, dateArr[0]);
							
							dateArr[3] = readTime(end1)[0];
							dateArr[4] = readTime(end1)[1];
							calendar.set(GregorianCalendar.HOUR_OF_DAY, dateArr[3]);
							calendar.set(GregorianCalendar.MINUTE, dateArr[4]);
							task.setEndTime(calendar);
						} else {
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Invalid command. Cannot recongize keywords.");
						}
					}
				} 
				else {
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command. Cannot recongize keywords.");
				}
			}

			targetAdd.add(task);

			newAction.setTargets(null);
			newAction.setResults(targetAdd);

			break;

		case UPDATE:
			newAction.setFeedback("Update successfully!");
			ArrayList<EzTask> targetUpdate = new ArrayList<EzTask>();
			ArrayList<EzTask> resultUpdate = new ArrayList<EzTask>();
			int[] dateUpdate = new int[5];
			try
			{
			
			String id = getFirstWord(content);
			content = removeFirstWord(content);
			int index = Integer.parseInt(id);
			if (storage.findTask(index) == null) {
				newAction.setTargets(null);
				newAction.setResults(null);
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

						dateUpdate[0] = readDate(content)[0];
						dateUpdate[1] = readDate(content)[1];
						dateUpdate[2] = readDate(content)[2];

						if (readDate(content)[0] < 0) {
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Invalid date.");
						}

						if(taskUpdate.getStartTime()==null&&taskUpdate.getEndTime()==null)
						{
							taskUpdate.setStartTime(new GregorianCalendar(dateUpdate[2],dateUpdate[1]-1,dateUpdate[0],0,0));
						    taskUpdate.setEndTime(new GregorianCalendar(dateUpdate[2],dateUpdate[1]-1,dateUpdate[0],23,59));
						}
						else 
						{
							taskUpdate.setStartTime(new GregorianCalendar(dateUpdate[2],dateUpdate[1]-1,dateUpdate[0],taskUpdate.getStartTime().get(GregorianCalendar.HOUR_OF_DAY),taskUpdate.getStartTime().get(GregorianCalendar.MINUTE)));
						    taskUpdate.setEndTime(new GregorianCalendar(dateUpdate[2],dateUpdate[1]-1,dateUpdate[0],taskUpdate.getEndTime().get(GregorianCalendar.HOUR_OF_DAY),taskUpdate.getEndTime().get(GregorianCalendar.MINUTE)));
						}
						resultUpdate.add(taskUpdate);
						newAction.setResults(resultUpdate);

						content = removeFirstWord(content);
						if (!content.isEmpty()) {
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Invalid command. Cannot recongize keywords.");
						}
					} 
					else if (getFirstWord(content).equalsIgnoreCase("time")) {
						content = removeFirstWord(content);

						dateUpdate[3] = readTime(content)[0];
						dateUpdate[4] = readTime(content)[1];
						if (readTime(content)[0] < 0) {
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Invalid time.");
						}
						calendarUpdate.set(GregorianCalendar.HOUR_OF_DAY,
								dateUpdate[3]);
						calendarUpdate.set(GregorianCalendar.MINUTE,
								dateUpdate[4]);

						taskUpdate.setStartTime(calendarUpdate);
						taskUpdate.setEndTimeAsStartTime();
						resultUpdate.add(taskUpdate);
						newAction.setResults(resultUpdate);

						content = removeFirstWord(content);
						if (!content.isEmpty()) {
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Invalid command. Cannot recongize keywords.");
						}
					} else if (getFirstWord(content).equalsIgnoreCase("start")) {
					
					    content=removeFirstWord(content);

						if (getFirstWord(content).equalsIgnoreCase("date")) 
						{
							content = removeFirstWord(content);
					
							dateUpdate[0] = readDate(content)[0];
							dateUpdate[1] = readDate(content)[1];
							dateUpdate[2] = readDate(content)[2];

							if (readDate(content)[0] < 0) {
								newAction.setAction(TypeOfAction.INVALID);
								newAction.setFeedback("Invalid date");
							}
							calendarUpdate.set(dateUpdate[2],dateUpdate[1]-1,dateUpdate[0]);
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
							dateUpdate[3] = readTime(content)[0];
							dateUpdate[4] = readTime(content)[1];
							if (readTime(content)[0] < 0) {
							newAction.setAction(TypeOfAction.INVALID);
							newAction.setFeedback("Invalid time.");
							}
						
							calendarUpdate.set(GregorianCalendar.HOUR_OF_DAY,
									dateUpdate[3]);
							calendarUpdate.set(GregorianCalendar.MINUTE,
									dateUpdate[4]);
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
							newAction.setFeedback("Invalid command. Cannot recongize keywords.");
						}

					} else if (getFirstWord(content).equalsIgnoreCase("end")) {
						calendarUpdate=taskTarget.getEndTime();

						content = removeFirstWord(content);
						if (getFirstWord(content).equalsIgnoreCase("date")) {
							content = removeFirstWord(content);

							dateUpdate[0] = readDate(content)[0];
							dateUpdate[1] = readDate(content)[1];
							dateUpdate[2] = readDate(content)[2];

							if (readDate(content)[0] < 0) {
								newAction.setAction(TypeOfAction.INVALID);
								newAction.setFeedback("Invalid date.");
							}

							calendarUpdate.set(dateUpdate[2],dateUpdate[1]-1,dateUpdate[0]);
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
							dateUpdate[3] = readTime(content)[0];
							dateUpdate[4] = readTime(content)[1];
							if (readTime(content)[0] < 0) {
								newAction.setAction(TypeOfAction.INVALID);
							}
							calendarUpdate.set(GregorianCalendar.HOUR_OF_DAY,
									dateUpdate[3]);
							calendarUpdate.set(GregorianCalendar.MINUTE,
									dateUpdate[4]);
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
							newAction.setFeedback("Invalid command. Cannot recongize keywords.");
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
				newAction.setFeedback("Invalid command.");
			}
			

			break;

		case DELETE:
			ArrayList<EzTask> targetDelete=new ArrayList<EzTask>();
			newAction.setResults(null);
			if(getFirstWord(content).equalsIgnoreCase("all"))//if the command is "delete all on a date"
			{
				content=removeFirstWord(content);
				content=removeFirstWord(content);//now content is suppose to be the date
				if(readDate(content)[0]>=0)
				{
					
				int[] dateArrDelete=new int[3];
				dateArrDelete[0]=readDate(content)[0];//find all tasks on the date and assign it to the arraylist.
				dateArrDelete[1]=readDate(content)[1];
				dateArrDelete[2]=readDate(content)[2];
				
				Date dateDelete=new Date(dateArrDelete[2]-1900,dateArrDelete[1]-1,dateArrDelete[0]);
				
				newAction.setTargets(storage.getTasksByDate(dateDelete));
				}
				else
				{
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command.");
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
					newAction.setFeedback("Invalid command. Cannot recongize keywords.");
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
			break;

		case DONE:
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
			  if(readDate(content)[0]>=0)
			  {
				int[] dateArrDone=new int[3];
				dateArrDone[0]=readDate(content)[0];
				dateArrDone[1]=readDate(content)[1];
				dateArrDone[2]=readDate(content)[2];
				
				Date dateDone=new Date(dateArrDone[2]-1900,dateArrDone[1]-1,dateArrDone[0]);
			
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
			  	newAction.setFeedback("Invalid date.");
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
					newAction.setFeedback("Invalid command. Cannot recongize keywords.");
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
						newAction.setFeedback("Invalid input.");
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
				newAction.setFeedback("Invalid number format or index.");		
			}
			}
			catch(NumberFormatException e)
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid number format or index.");		
			}
			
			// target not found, set as null

			// same as update
			break;

		case UNDO:
			newAction.setFeedback("Undo successfully!");
			newAction.setResults(null);
			newAction.setTargets(null);
			break;

		case REDO:
			newAction.setFeedback("Redo successfully!");
			newAction.setResults(null);
			newAction.setTargets(null);
			break;

		case SHOW:
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
					
					if(readDate(content)[0]>=0)
					  {
						int[] dateArrShow=new int[3];
						dateArrShow[0]=readDate(content)[0];
						dateArrShow[1]=readDate(content)[1];
						dateArrShow[2]=readDate(content)[2];
						
						Date dateShow=new Date(dateArrShow[2]-1900,dateArrShow[1]-1,dateArrShow[0]);
					    targetsShow=storage.getTasksByDate(dateShow);
					  }
					  else
					  {
					  	newAction.setAction(TypeOfAction.INVALID);
					  	newAction.setFeedback("Invalid date.");
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
							newAction.setFeedback("Extra \"");
						}
					}
					targetsShow=storage.getTasksByKeywords(keywords);
				}
				else
				{
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command. Cannot recongnize keywords.");
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
					
					if(readDate(content)[0]>=0)
					  {
						int[] dateArrShowDone=new int[3];
						dateArrShowDone[0]=readDate(content)[0];
						dateArrShowDone[1]=readDate(content)[1];
						dateArrShowDone[2]=readDate(content)[2];
						
						Date dateShowDone=new Date(dateArrShowDone[2]-1900,dateArrShowDone[1]-1,dateArrShowDone[0]);
					    targetsShow=getDoneTasks(storage.getTasksByDate(dateShowDone));
					  }
					  else
					  {
					  	newAction.setAction(TypeOfAction.INVALID);
					  	newAction.setFeedback("Invalid date.");
					  }
					
				}
				else
				{
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command. Cannot recongnize keywords.");
					
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
					
					if(readDate(content)[0]>=0)
					  {
						int[] dateArrShowUndone=new int[3];
						dateArrShowUndone[0]=readDate(content)[0];
						dateArrShowUndone[1]=readDate(content)[1];
						dateArrShowUndone[2]=readDate(content)[2];
						
						Date dateShowUndone=new Date(dateArrShowUndone[2]-1900,dateArrShowUndone[1]-1,dateArrShowUndone[0]);
					    targetsShow=getUndoneTasks(storage.getTasksByDate(dateShowUndone));
					  }
					  else
					  {
					  	newAction.setAction(TypeOfAction.INVALID);
					  	newAction.setFeedback("Invalid date.");
					  }
					
				}
				else
				{
					newAction.setAction(TypeOfAction.INVALID);
					newAction.setFeedback("Invalid command. Cannot recongnize keywords.");
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
			break;

		case HELP:
			newAction.setTargets(null);
			newAction.setAction(null);
			// set results and target as null
			break;
		case Y:
			newAction.setTargets(null);
			newAction.setResults(null);
			break;
		case N:
			newAction.setTargets(null);
			newAction.setResults(null);
			break;
		case SORT:
			String sortType=getFirstWord(content);
			content=removeFirstWord(content);
			if(content.isEmpty()!=true)
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid command.");		
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
				newAction.setFeedback("Invalid command");
			}
				
			newAction.setTargets(null);
			newAction.setResults(null);
			break;
		case UNDONE:
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
			  if(readDate(content)[0]>=0)
			  {
				int[] dateArrUndone=new int[3];
				dateArrUndone[0]=readDate(content)[0];
				dateArrUndone[1]=readDate(content)[1];
				dateArrUndone[2]=readDate(content)[2];
				
				Date dateUndone=new Date(dateArrUndone[2]-1900,dateArrUndone[1]-1,dateArrUndone[0]);
			
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
			  	newAction.setFeedback("Invalid date.");
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
					newAction.setFeedback("Invalid command. Cannot recongize keywords.");
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
						temp.setDone(true);
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
			try
			{	
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
						newAction.setFeedback("Invalid input.");
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
				newAction.setFeedback("Invalid number format or index.");		
			}
			}
			catch(NumberFormatException e)
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid number format or index.");		
			}
			break;
			
		case REMOVE:
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
				newAction.setFeedback("Cannot recognize keyword.");
			}
			
		
			targetsRemove.add(storage.findTask(Integer.parseInt(idRemove)));
			EzTask taskRemove=new EzTask(storage.findTask(Integer.parseInt(idRemove)));
			if(typeRemove.equalsIgnoreCase("venue"))
			{
				taskRemove.setVenue(null);
				newAction.setFeedback("Remove venue successfully.");
			}
			else if(typeRemove.equalsIgnoreCase("date"))
			{
				taskRemove.setStartTime(null);
				taskRemove.setEndTime(null);
				newAction.setFeedback("Remove date successfully.");
			}
			else if(typeRemove.equalsIgnoreCase("time"))
			{
				taskRemove.setStartTime(0,0);
				taskRemove.setEndTime(23, 59);
				newAction.setFeedback("Remove time succefully.");
			}
			else
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Cannot recognize keyword.");
			}
			
			resultsRemove.add(taskRemove);
			newAction.setTargets(targetsRemove);
			newAction.setResults(resultsRemove);
			}
			catch(NumberFormatException e)
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid number format.");
			}
			break;
		case PAGE:
			newAction.setTargets(null);
			newAction.setResults(null);
			try
			{
			int pageNum=Integer.parseInt(content);
			newAction.setPageNumber(pageNum);
			newAction.setFeedback("Page "+pageNum);
			}
			catch(NumberFormatException e)
			{
				newAction.setAction(TypeOfAction.INVALID);
				newAction.setFeedback("Invalid number format.");
			}
			break;

		default:
			break;
		}

		return newAction;
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

	private static int[] readDate(String date)// return int[0] as -1 if the date
												// is invalid
	throws NumberFormatException
	{
		int[] results = new int[3];
		try
		{
		
	
		date = date.trim();
		if(date.equalsIgnoreCase("today"))//today
		{
             GregorianCalendar calendar=new GregorianCalendar();

             results[0]=calendar.get(GregorianCalendar.DAY_OF_MONTH);
           	 results[1]=calendar.get(GregorianCalendar.MONTH)+1;
           	 results[2]=calendar.get(GregorianCalendar.YEAR);
           	 return results;
			   
		}
		if(date.equalsIgnoreCase("tomorrow"))//tomorrow
		{
			GregorianCalendar calendar=new GregorianCalendar();
			calendar.add(GregorianCalendar.DATE, 1);

            results[0]=calendar.get(GregorianCalendar.DAY_OF_MONTH);
          	results[1]=calendar.get(GregorianCalendar.MONTH)+1;
          	results[2]=calendar.get(GregorianCalendar.YEAR);
          	return results;
		}
		if ((date.indexOf("/") >= 0)
				&& (date.indexOf("/", date.indexOf("/") + 1) >= 0)
				&& (date.indexOf("/", date.indexOf("/") + 1) == date
						.lastIndexOf("/"))) {
			results[0] = Integer.parseInt(date.substring(0, date.indexOf("/")));
			results[1] = Integer.parseInt(date.substring(date.indexOf("/") + 1,
					date.lastIndexOf("/")));
			results[2] = Integer
					.parseInt(date.substring(date.lastIndexOf("/") + 1));
		} else if ((date.indexOf(".") >= 0)
				&& (date.indexOf(".", date.indexOf(".") + 1) >= 0)
				&& (date.indexOf(".", date.indexOf(".") + 1) == date
						.lastIndexOf("."))) {
			results[0] = Integer.parseInt(date.substring(0, date.indexOf(".")));
			results[1] = Integer.parseInt(date.substring(date.indexOf(".") + 1,
					date.lastIndexOf(".")));
			results[2] = Integer
					.parseInt(date.substring(date.lastIndexOf(".") + 1));
			if ((results[0] > 31) || (results[0] <= 0) || (results[1] > 12)
					|| (results[1] <= 0) || (results[2] <= 0)
					|| ((results[2] >= 100) && (results[2] < 1970))) {
				results[0] = -1;
			}
			if (results[2] < 100) {
				results[2] = results[2] + 2000;
			}
		} 
		else if((date.indexOf("/")>=0)&&(date.indexOf("/")==date.lastIndexOf("/")))
		{
			results[0] = Integer.parseInt(date.substring(0, date.indexOf("/")));
			results[1] = Integer.parseInt(date.substring(date.indexOf("/")+1));
			GregorianCalendar calendar=new GregorianCalendar();
			results[2]=calendar.get(GregorianCalendar.YEAR);		
			if ((results[0] > 31) || (results[0] <= 0) || (results[1] > 12)
					|| (results[1] <= 0) ) 
			{
				results[0] = -1;
			}
		}
		else if((date.indexOf(".")>=0)&&(date.indexOf(".")==date.lastIndexOf(".")))
		{
			results[0] = Integer.parseInt(date.substring(0, date.indexOf(".")));
			results[1] = Integer.parseInt(date.substring(date.indexOf(".")+1));
			GregorianCalendar calendar=new GregorianCalendar();
			results[2]=calendar.get(GregorianCalendar.YEAR);		
			if ((results[0] > 31) || (results[0] <= 0) || (results[1] > 12)
					|| (results[1] <= 0) ) 
			{
				results[0] = -1;
			}
		}
		else
			results[0] = -1;

		return results;// need a method to analyze the date int[]
						// readDate(String date) dd/mm/yy, dd/mm/yyyy,
						// dd.mm.yy,dd.mm.yyyy
		}
		catch (NumberFormatException e)
		{
			results[0]= -1;
		}
		return results;

	}

	private static int[] readTime(String time)// return int[0] as -1 if the time
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
				results[0] = 12;
			}
			time = time.substring(0, time.length() - 2);
			time.trim();
		}
		if ((time.indexOf(":") >= 0)
				&& ((time.indexOf(":") == time.lastIndexOf(":")))) {
			results[0] = results[0]
					+ Integer.parseInt(time.substring(0, time.indexOf(":"))
							.trim());
			results[1] = Integer.parseInt(time.substring(time.indexOf(":") + 1)
					.trim());
			if (results[0] > 24 || results[0] < 0 || results[1] > 59
					|| results[1] < 0) {
				results[0] = -1;
			}

		} else if (time.indexOf("h") >= 0
				&& time.indexOf("h") == time.lastIndexOf("h")) {
			results[0] = Integer.parseInt(time.substring(0, time.indexOf("h"))
					.trim());
			if (!time.substring(time.indexOf("h") + 1).trim().isEmpty()) {
				results[1] = Integer.parseInt(time.substring(
						time.indexOf("h") + 1).trim());
			}
			if (results[0] > 24 || results[0] < 0 || results[1] > 59
					|| results[1] < 0) {
				results[0] = -1;
			}
		} 
		else if(Integer.parseInt(time)>=0)
		{
			results[0]=results[0]+Integer.parseInt(time);
		}
		else
			results[0] = -1;
		if(isPM==true&&results[0]==24&&results[1]==0)
		{
			results[0]=12;
		}
		if(isPM==false&&results[0]==12&&results[1]==0)
		{
			results[0]=0;
		}
	}
	catch(NumberFormatException e)
	{
		results[0]=-1;
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
