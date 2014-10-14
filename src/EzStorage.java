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
	
	//largestId represents the task Id with the greatest value in listOfAllTasks 
	private int largestId = 0;


	/**
	 * this method just simply adds the task to the list then return it.
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
	@SuppressWarnings("unchecked")
	public ArrayList<EzTask> getSortedTasksById(){
		ArrayList <EzTask> tasksById = new ArrayList<EzTask>();
		
		tasksById = (ArrayList<EzTask>)listOfAllTasks.clone();
		
		Collections.sort(tasksById, EzTask.TaskIdComparator);
		
		
			return tasksById;
					
	}
	
	
	/**
	 * this method return a list of tasks, which is sorted by priority then by date.
	 * @return ArrayList tasksByPriority or null
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<EzTask> getSortedTasksByPriority(){
		
		ArrayList<EzTask> tasksByPriority = new ArrayList<EzTask>();
		tasksByPriority = (ArrayList<EzTask>) listOfAllTasks.clone();
		Collections.sort(tasksByPriority, EzTask.TaskPriorityComparator);
		return tasksByPriority;
				
		
	}
	
	/**
	 * this method return a list of tasks, which contain one or more words in the keywords.
	 * @param listOfKeywords
	 * @return ArrayList tasksWithKeywords
	 */
	public ArrayList<EzTask> getTasksByKeywords(ArrayList<String> listOfKeywords){

		ArrayList<EzTask> tasksWithKeywords = new ArrayList<EzTask>();
		for(String keyword : listOfKeywords)
			for(EzTask task: listOfAllTasks)
			{
				//System.out.println(" new Task:" + task.getTitle());
				//System.out.println(listOfAllTasks.size());
				
				if(checkTitle(keyword, task))
				{
					if(!tasksWithKeywords.contains(task))
						tasksWithKeywords.add(task);
					//System.out.println(" taskwKeyword" + task.getTitle());
				}

			}
		//System.out.println(tasksWithKeywords);
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
	
	public ArrayList<String> getHelpCommand(){
		//TODO
	
		return null;
	}
	
	public ArrayList<String> getHelpCommand(String word){
		//TODO
		return null;
	}
	
	/**
	 * This method clears the ArrayList passed into it
	 * @param ArrayList list
	 * @return void
	 */
	private void clearList(ArrayList list){
		list.clear();
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
