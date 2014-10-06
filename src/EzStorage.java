import java.util.ArrayList;
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
	int largestId = 0;


	/**
	 * this function just simply adds the task to the list then return it.
	 * @param task
	 * @return task 
	 */
	public EzTask addTask(EzTask task){
		for(EzTask taskInList : listOfAllTasks)
			if(taskInList.getId() > largestId)
				largestId = taskInList.getId();
		
		if(task.getId() > largestId)
			largestId = task.getId() + 1;
		
		
		listOfAllTasks.add(task);
		
		//System.out.println("sizeList" + listOfAllTasks.get(listOfAllTasks.size()-1).getId());
		
		return task;
	}
	
	/**
	 * this function adds the task to the list and provides an unique ID for the task then return it.
	 * @param task
	 * @return task with new id
	 */
	public EzTask addTaskWithNewId(EzTask task){
		

		listOfAllTasks.add(task);
		task.setId(largestId);
		largestId++;
		
		return task;
		
	}
	
	/**
	 * this function replaces the tasks with certain ID in the original list, with the new tasks carrying the same ID in the new list.
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
	 * this function removes the task having the id as the id of the tasks from the listOfTasks
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
	 * this function returns the number of tasks in the list.
	 * @return
	 */
	public int getSize(){
		return listOfAllTasks.size();

	}
	
	/**
	 * this function finds the task by ID and return it if found or else return null.
	 * @param id
	 * @return EzTask or null
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
	 * this function finds all the tasks on the date and return that list.
	 * @param date
	 * @return ArrayList called tasksByDate or null
	 */
	public ArrayList<EzTask> getTasksByDate(Date date){
	
		ArrayList<EzTask> tasksByDate = new ArrayList<EzTask>();
		for(EzTask task : listOfAllTasks)
		if(checkByDate(date,task))
			tasksByDate.add(task);
		
		if(tasksByDate.isEmpty())
			return null;
		
		return tasksByDate;
	}
	
	/*
	 * this function returns true if the the task falls on the date specified.
	 * @param date, task
	 * @return boolean
	 */
	private boolean checkByDate(Date date, EzTask task) {
		if(task.getStartTime().before(date) && task.getEndTime().after(date) )
			return true;

		else if(task.getStartTime().equals(date) || task.getEndTime().equals(date))
			return true;

		return false;
	}

	/*
	 * this function returns a sorted ArrayList by id
	 * @return ArrayList of tasksById or null
	 */
	public ArrayList<EzTask> getSortedTasksById(){
		ArrayList <EzTask> tasksById = new ArrayList<EzTask>();
		
		for(EzTask task : listOfAllTasks)
			for(int id = 0; id < largestId ; id++)
				if(task.getId() == id)
					tasksById.add(task);
		
		
		if(tasksById.isEmpty())
			return null;
		
		else
			return tasksById;
					
	}
	
	/**
	 * this function return a list of tasks, which is sorted by priority then by date.
	 * @return ArrayList tasksByPriority or null
	 */
	public ArrayList<EzTask> getSortedTasksByPriority(){
		
		ArrayList<EzTask> tasksByPriority = new ArrayList<EzTask>();
		for(EzTask task : listOfAllTasks)
			for(int priority = 0; priority < EzConstants.MAXIMUM_PRIORITY ; priority++)
			{
				if(task.getPriority() == priority)
					tasksByPriority.add(task);
					
			}
		if(tasksByPriority.isEmpty())
			return null;
		
		return tasksByPriority;
				
		
	}
	
	/**
	 * this function return a list of tasks, which contain one or more words in the keywords.
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
					
					tasksWithKeywords.add(task);
					//System.out.println(" taskwKeyword" + task.getTitle());
				}

			}
		//System.out.println(tasksWithKeywords);
		return tasksWithKeywords;


	}

	public boolean checkTitle(String keyword, EzTask task) {
		
		String titleToCheck = task.getTitle();
		if(titleToCheck.contains(keyword))
			return true;
		
		return false;
		
		
	}
	
	private void clearList(ArrayList list){
		list.clear();
	}

	public void printList() {
		for(EzTask task: listOfAllTasks)
		System.out.println(task.getTitle());
	}
	
	
}
