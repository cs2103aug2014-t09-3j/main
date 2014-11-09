import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;



//@author A0113922N

public class EzStorage {
	
	//ArrayList of tasks entered by user.

	private ArrayList<EzTask> listOfAllTasks = new ArrayList<EzTask>();
	
	//nextTaskId represents the taskId of the next task to be added
	//nextTaskId will be 1 initially as task id starts from 1
	private int nextTaskId = 1;

	/**
	 * Adds the task to listOfAllTasks and returns it.
	 * @param task
	 * @return task 
	 */
	public EzTask addTask(EzTask task){
		
	
		for(EzTask taskInList : listOfAllTasks)
			if(taskInList.getId() > getNextTaskId())
				setNextTaskId(taskInList.getId());
		
		if(task.getId() > getNextTaskId())
			setNextTaskId(task.getId());
		
		
		listOfAllTasks.add(task);
		
		//assertions 
		assert listOfAllTasks.size() >0;

		//end assertions 
		
		return task;
	}
	
	/**
	 * Adds the task to the list, provides an unique task ID for the task and returns it.
	 * @param task
	 * @return task with new id
	 */
	public EzTask addTaskWithNewId(EzTask task){
		
		
		int largestTaskId = 0;            
		if(listOfAllTasks != null)
		for(EzTask task1 : listOfAllTasks )
			if(task1.getId() > largestTaskId)
				largestTaskId = task1.getId();
		
		
		if(listOfAllTasks.size() == 0)        
			setNextTaskId(0);                   //if list is empty, then task id resets to 1
		
		listOfAllTasks.add(task);
		if(largestTaskId + 1 != getNextTaskId())      //
			setNextTaskId(largestTaskId);
		
		task.setId(getNextTaskId());
		setNextTaskId(getNextTaskId());          //nextTaskId must always be 1 more than the largestTaskId
		
		return task;
		
	}
	
	/**
	 * Replaces old tasks with new tasks carrying the same task ID in the list and returns the number of tasks replaced.
	 * @param listOfTasksUpdated
	 * @return the number of tasks found and replaced.
	 */

	public int updateTask(ArrayList<EzTask> listOfTasksUpdated) {
		int numTasksReplaced = 0;       
		int index;			 //the index of the task in listOfAllTasks
		
		for(EzTask taskToBeUpdated : listOfTasksUpdated)
			for(int i=0; i< listOfAllTasks.size(); i++)
			{
				EzTask task = listOfAllTasks.get(i);
				if(taskToBeUpdated.getId() == task.getId() )
				{
			
					index = listOfAllTasks.indexOf(task);
					
					listOfAllTasks.remove(index);
					listOfAllTasks.add(index, taskToBeUpdated);
					numTasksReplaced++;
				}
			}
	
		return numTasksReplaced;
	}
	
	/**
	 * Removes task(s) from the listOfAllTasks that has the same task id
	 * as task(s) passed into the method
	 * and returns the number of tasks removed.
	 * @param listOfTasks
	 * @return the number of tasks found and removed.
	 */

	public int deleteTask(ArrayList<EzTask> listOfTasksDeleted){
		int numTasksRemoved = 0;
		
		for(EzTask taskToBeDeleted: listOfTasksDeleted)
			for(int i = 0; i < listOfAllTasks.size(); i++)
			{
				EzTask task = listOfAllTasks.get(i);
				if(taskToBeDeleted.getId() == task.getId())

				{
					listOfAllTasks.remove(listOfAllTasks.indexOf(task));
					numTasksRemoved++;
				}
			}

	return numTasksRemoved;
	}
	
	/**
	 *Returns the number of tasks in the list.
	 * @return size of task list
	 */
	public int getSize(){
		return listOfAllTasks.size();

	}
	
	/**
	 * Finds the task by task id and returns a copy of the task if found, and returns null if otherwise.
	 * @param id
	 * @return taskFound or null
	 */
	public EzTask findTask(int id){
		
		EzTask taskFound;
		for(EzTask task : listOfAllTasks)
			if(id == task.getId())
			{
				taskFound = new  EzTask(task);
				return taskFound;
			}
		
		return null;
	}

	/**
	 * Finds the task by taskId and returns it if found or returns null otherwise.
	 * @param id
	 * @return task or null
	 */
	public EzTask getTask(int id){
		for(EzTask task : listOfAllTasks)
			if(id == task.getId())
				return task;

			return null;
	}
	
	/**
	 * Finds all the tasks that fall on a particular date and returns that list.
	 * @param date
	 * @return tasksByDate or null
	 */
	public ArrayList<EzTask> getTasksByDate(Date date){
	
		ArrayList<EzTask> tasksByDate = new ArrayList<EzTask>();
		
		for(EzTask task : listOfAllTasks)
			if(checkByDate(date,task))
				tasksByDate.add(task);

		return tasksByDate;
	}
	
	/**
	 * Returns true if the the task falls on the date specified.
	 * @param date, task
	 * @return boolean
	 */
	private boolean checkByDate(Date date, EzTask task) {
		
		if(task.getEndTime() == null && task.getStartTime() == null)
			return false;
		
		Calendar cal1 = Calendar.getInstance();
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
    	
    	cal1.setTime(date);
    	startTime.setTime(task.getStartTime().getTime());
    	endTime.setTime(task.getEndTime().getTime());

    	

    	if(startTime.before(cal1) && endTime.after(cal1) )
    		return true;
    	
    	//returns true if the startTime or endTime falls on the date as specified in cal1, as the tasks will fall on that date too.
    	else if(compareDate(cal1, startTime)  || compareDate(cal1, endTime))
    		return true;

		return false;
	}

	
	
	/**
	 * Returns a list of tasks, which contain one or more keywords in its title or venue.
	 * @param listOfKeywords
	 * @return ArrayList tasksWithKeywords
	 */
	public ArrayList<EzTask> getTasksByKeywords(ArrayList<String> listOfKeywords){

		ArrayList<EzTask> tasksWithKeywords = new ArrayList<EzTask>();
		
		for(String keyword : listOfKeywords)
			for(EzTask task: listOfAllTasks)
				if(checkForKeyword(keyword, task))
					if(!tasksWithKeywords.contains(task))
						tasksWithKeywords.add(task);
				
		return tasksWithKeywords;


	}
	
	/**
	 * Checks if the task title or venue contains the keyword
	 * @param keyword
	 * @param task
	 * @return boolean
	 */
	private boolean checkForKeyword(String keyword, EzTask task){
		String titleToCheck = task.getTitle().toLowerCase();
		String venueToCheck = task.getVenue().toLowerCase();
		String keywordToCheck = keyword.toLowerCase();
		
		if(titleToCheck.contains(keywordToCheck))
			return true;
		
		else if(venueToCheck.contains(keywordToCheck) && !titleToCheck.contains(keywordToCheck))
			return true;
		
		else 
			return false;
		
	}
		 
	
	

	/**
	 * Returns a list of tasks that have been done in order of id.
	 * 
	 * @return doneTasks
	 */
	public ArrayList<EzTask> getDoneTasks(){
		
		ArrayList<EzTask> doneTasks = new ArrayList<EzTask>();
		for(EzTask task: listOfAllTasks)
			if(task.isDone())
				doneTasks.add(task);
		
		EzSort.sortById(doneTasks);
		
		return doneTasks;
	}
	
	/**
	 * Returns the list of tasks that have not been done in order of id
	 * @return undoneTasks
	 */
	public ArrayList<EzTask> getUndoneTasks() {
		ArrayList<EzTask> undoneTasks  = new ArrayList<EzTask>();
		for(EzTask task: listOfAllTasks)
			if(!task.isDone())
				undoneTasks.add(task);
		
		EzSort.sortById(undoneTasks);
		
		return undoneTasks;
	}

	/**
	 * Returns the list of upcoming tasks according to date
	 * @return comingTasks
	 */
	public  ArrayList<EzTask> getComingTasks(){
		ArrayList<EzTask> comingTasks = new ArrayList<EzTask>();
		
		Calendar currentDate = Calendar.getInstance();
		Calendar startTime = Calendar.getInstance();
		
		for(EzTask task: listOfAllTasks)
		{
			startTime = task.getStartTime();
			if(startTime != null)
			{
				if(currentDate.before(startTime))
					comingTasks.add(task);
				
				else if(compareDate(currentDate, startTime))
					comingTasks.add(task);
			}
		}

		EzSort.sortByDate(comingTasks);

		return comingTasks;
	}

	/**
	 * Compares between current date and the dateToBeCompared 
	 * returns true if current date == dateToBeCompared and false if not
	 * @param currentDate
	 * @param timeToBeCompared
	 * @return boolean
	 */
	private boolean compareDate(Calendar currentDate, Calendar dateToBeCompared) {
		return currentDate.get(Calendar.YEAR) == dateToBeCompared.get(Calendar.YEAR) && currentDate.get(Calendar.MONTH) == dateToBeCompared.get(Calendar.MONTH) &&
				currentDate.get(Calendar.DAY_OF_MONTH) == dateToBeCompared.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Returns overdue tasks according to date
	 * @return overdueTasks
	 */
	public ArrayList<EzTask> getOverdueTasks() {
		ArrayList<EzTask> overdueTasks = new ArrayList<EzTask>();
		Calendar currentDate = Calendar.getInstance();
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();

		for(EzTask task: listOfAllTasks)
			if(!task.isDone())
				if(task.getStartTime() != null && task.getEndTime() != null)
				{
					startTime = task.getStartTime();
					endTime = task.getEndTime();
					if(currentDate.after(startTime) && currentDate.after(endTime))
						overdueTasks.add(task);
					
					else if(!compareDate(currentDate, startTime) || !compareDate(currentDate, startTime))
						if(currentDate.after(startTime) && currentDate.after(endTime))
							if(!overdueTasks.contains(task))
								overdueTasks.add(task);


			}

		EzSort.sortByDate(overdueTasks);
		return overdueTasks;

	}

	/**
	 * Returns the list of tasks that have no dates
	 * @return noDateTasks
	 */
	public ArrayList<EzTask> getNoDateTasks(){
		ArrayList<EzTask> noDateTasks = new ArrayList<EzTask>();
		for(EzTask task: listOfAllTasks)
			if(task.getStartTime() == null)
				noDateTasks.add(task);
		
		EzSort.sortById(noDateTasks);
		return noDateTasks;
		
	}

	/**
	 * Returns a copy of listOfAllTasks
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<EzTask> getListOfAllTasks(){
		ArrayList<EzTask> list = (ArrayList<EzTask>) listOfAllTasks.clone();
		return list;
	}


	/**
	 * Prints the title of each task in the ArrayList
	 */
	public void printList() {
		for(EzTask task: listOfAllTasks)
		System.out.println(task.getTitle());
	}

	/**
	 * Gets the nextTaskId 
	 * @return nextTaskId
	 */
	public int getNextTaskId() {
		return this.nextTaskId;
	}

	/**
	 * Assigns the nextTaskId
	 * @param nextTaskId
	 */
	public void setNextTaskId(int nextTaskId) {
		this.nextTaskId = nextTaskId + 1;
	}
	
	
}
