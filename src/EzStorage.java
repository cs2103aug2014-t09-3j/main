import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * 
 */

/**
 * @author Khanh(Skeleton code)
 * @author Tun Leng
 *
 */
public class EzStorage {
	
	//ArrayList of tasks entered by user.

	private ArrayList<EzTask> listOfAllTasks = new ArrayList<EzTask>();
	
	//largestId represents the largest taskId + 1  
	private int largestId = 0;

	/**
	 * this method just simply adds the task to the list and returns it.
	 * @param task
	 * @return task 
	 */
	public EzTask addTask(EzTask task){
		
		for(EzTask taskInList : listOfAllTasks)
			if(taskInList.getId() > getLargestId())
				setLargestId(taskInList.getId());
		
		if(task.getId()+1 > getLargestId())
			setLargestId(task.getId() + 1);
		
		
		listOfAllTasks.add(task);
		
		int size = listOfAllTasks.size();
		assert size >0;
		int largestNum = 0;
		for(EzTask task1 : listOfAllTasks)
			if(task1.getId() > largestNum)
				largestNum = task1.getId();
		
		assert largestId == largestNum + 1;
		return task;
	}
	
	/**
	 * this method adds the task to the list and provides an unique ID for the task then return it.
	 * @param task
	 * @return task with new id
	 */
	public EzTask addTaskWithNewId(EzTask task){
		
		listOfAllTasks.add(task);
		task.setId(getLargestId());
		setLargestId(getLargestId() + 1);
		
		return task;
		
	}
	
	/**
	 * this method replaces the tasks with certain ID in the original list, with the new tasks carrying the same ID in the new list.
	 * @param listOfTasksUpdated
	 * @return the number of tasks found and replaced.
	 */

	public int updateTask(ArrayList<EzTask> listOfTasksUpdated) {
		int count = 0;
		int index;
		for(EzTask taskToBeUpdated : listOfTasksUpdated)
			for(int i=0; i< listOfAllTasks.size(); i++)
			{
				EzTask task = listOfAllTasks.get(i);
				if(taskToBeUpdated.getId() == task.getId() )
				{
			
					index = listOfAllTasks.indexOf(task);
					
					listOfAllTasks.remove(index);
					listOfAllTasks.add(index, taskToBeUpdated);
					count++;
				}
			}
	
		return count;
	}
	
	/**
	 * this method removes the task having the id as the id of the tasks from the listOfTasks
	 * @param listOfTasks
	 * @return the number of tasks found and removed.
	 */

	public int deleteTask(ArrayList<EzTask> listOfTasksDeleted){
		int count = 0;
		int index = 0;
		for(EzTask taskToBeDeleted: listOfTasksDeleted)
			for(int i = 0; i < listOfAllTasks.size(); i++)
			{
				EzTask task = listOfAllTasks.get(i);
				if(taskToBeDeleted.getId() == task.getId())

				{
					index = listOfAllTasks.indexOf(task);
					listOfAllTasks.remove(index);
					count++;
				}
			}

	return count;
	}
	
	/**
	 * this method returns the number of tasks in the list.
	 * @return size of task list
	 */
	public int getSize(){
		return listOfAllTasks.size();

	}
	
	/**
	 * this method finds the task by ID and return it if found or else return null.
	 * @param id
	 * @return EzTask 
	 */
	public EzTask findTask(int id){
		for(EzTask task : listOfAllTasks)
		{
			//System.out.println("findTask: " + task.getTitle());
		
			if(id == task.getId())
				return task;
		}
			return null;
	}
	
	/**
	 * this method finds all the tasks on the date and return that list.
	 * @param date
	 * @return ArrayList called tasksByDate or null
	 */
	public ArrayList<EzTask> getTasksByDate(Date date){
	
		ArrayList<EzTask> tasksByDate = new ArrayList<EzTask>();
		for(EzTask task : listOfAllTasks)
			if(checkByDate(date,task))
				tasksByDate.add(task);

		
		
		return tasksByDate;
	}
	
	/**
	 * this method returns true if the the task falls on the date specified.
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

    	else if(cal1.get(Calendar.YEAR) == startTime.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == startTime.get(Calendar.MONTH) &&
    			cal1.get(Calendar.DAY_OF_MONTH) == startTime.get(Calendar.DAY_OF_MONTH)  || cal1.get(Calendar.YEAR) == endTime.get(Calendar.YEAR) &&
    			cal1.get(Calendar.MONTH) == endTime.get(Calendar.MONTH) && cal1.get(Calendar.DAY_OF_MONTH) == endTime.get(Calendar.DAY_OF_MONTH))
    		return true;

		return false;
	}

	/**
	 * this method returns a sorted ArrayList by id
	 * @return ArrayList of tasksById or null
	 */
	/*@SuppressWarnings("unchecked")
	public ArrayList<EzTask> getSortedTasksById(){
		ArrayList <EzTask> tasksById = new ArrayList<EzTask>();
		
		tasksById = (ArrayList<EzTask>)listOfAllTasks.clone();
		
		Collections.sort(tasksById, EzSort.TaskIdComparator);
		
		
			return tasksById;
					
	}*/
	
	
	/**
	 * this method return a list of tasks, which is sorted by priority then by date.
	 * @return ArrayList tasksByPriority or null
	 */
	/*@SuppressWarnings("unchecked")
	public ArrayList<EzTask> getSortedTasksByPriority(){
		
		ArrayList<EzTask> tasksByPriority = new ArrayList<EzTask>();
		tasksByPriority = (ArrayList<EzTask>) listOfAllTasks.clone();
		Collections.sort(tasksByPriority, EzSort.TaskPriorityComparator);
		return tasksByPriority;
				
		
	}*/
	
	/**
	 * this method return a list of tasks, which contain one or more words in the keywords.
	 * @param listOfKeywords
	 * @return ArrayList tasksWithKeywords
	 */
	public ArrayList<EzTask> getTasksByKeywords(ArrayList<String> listOfKeywords){

		ArrayList<EzTask> tasksWithKeywords = new ArrayList<EzTask>();
		for(String keyword : listOfKeywords)
			for(EzTask task: listOfAllTasks)
				if(checkTitle(keyword, task))
					if(!tasksWithKeywords.contains(task))
						tasksWithKeywords.add(task);
				
		return tasksWithKeywords;


	}
	
	/**
	 * This method checks whether the keyword is present in the task's title
	 * @param String keyword, EzTask task
	 * @return boolean
	 */
	private boolean checkTitle(String keyword, EzTask task) {
		
		String titleToCheck = task.getTitle();
		if(titleToCheck.contains(keyword))
			return true;
		
		return false;
		
		
	}

	/**
	 * this function returns the list of tasks that have been done in order of id.
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
	 * this method returns the list of tasks that have not been done in order of id
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
				else if(currentDate.get(Calendar.YEAR) == startTime.get(Calendar.YEAR) && currentDate.get(Calendar.MONTH) == startTime.get(Calendar.MONTH) &&
						currentDate.get(Calendar.DAY_OF_MONTH) == startTime.get(Calendar.DAY_OF_MONTH))

					comingTasks.add(task);
			}}

		EzSort.sortByDate(comingTasks);

		return comingTasks;
	}
	
	public ArrayList<EzTask> getPastTasks() {
		ArrayList<EzTask> pastTasks = new ArrayList<EzTask>();
		Calendar currentDate = Calendar.getInstance();
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		for(EzTask task: listOfAllTasks)
		{
			if(task.getStartTime() != null && task.getEndTime() != null)
			{
				startTime = task.getStartTime();
				endTime = task.getEndTime();
				if(currentDate.get(Calendar.YEAR) != startTime.get(Calendar.YEAR) || currentDate.get(Calendar.MONTH) != startTime.get(Calendar.MONTH) ||
						currentDate.get(Calendar.DAY_OF_MONTH) != startTime.get(Calendar.DAY_OF_MONTH) || currentDate.get(Calendar.YEAR) != endTime.get(Calendar.YEAR) || currentDate.get(Calendar.MONTH) != endTime.get(Calendar.MONTH) ||
						currentDate.get(Calendar.DAY_OF_MONTH) != endTime.get(Calendar.DAY_OF_MONTH))
					if(currentDate.after(startTime)  || currentDate.after(endTime))
						if(!pastTasks.contains(task))
							pastTasks.add(task);
			}
		}

		EzSort.sortByDate(pastTasks);
		return pastTasks;

	}

	/**
	 * this method returns the list of tasks that have no date
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

	@SuppressWarnings("unchecked")
	public ArrayList<EzTask> getListOfAllTasks(){
		ArrayList<EzTask> list = (ArrayList<EzTask>) listOfAllTasks.clone();
		return list;
	}


	/**
	 * This method prints the title of each task in the ArrayList
	 */
	public void printList() {
		for(EzTask task: listOfAllTasks)
		System.out.println(task.getTitle());
	}

	/**
	 * Gets the largestId 
	 * @return largestId
	 */
	public int getLargestId() {
		return this.largestId;
	}

	/**
	 * Assigns the largestId
	 * @param largestId
	 */
	public void setLargestId(int largestId) {
		this.largestId = largestId;
	}
	
	
}
