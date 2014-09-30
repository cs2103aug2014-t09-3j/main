import java.util.ArrayList;
import java.util.Date;

/**
 * 
 */

/**
 * @author Khanh
 * @author Tun Leng
 *
 */
public class EzStorage {
	
	//ArrayList of tasks entered by user.
<<<<<<< Updated upstream
	private ArrayList<EzTask> listOfAllTasks = new ArrayList<EzTask>();
	
	//numTasks represents the number of tasks entered by the user.
	int numTasks = 0;
=======
	ArrayList<EzTask> listOfTask = new ArrayList<EzTask>();
	
	//numTasks represents the number of tasks entered by the user.
	//int numTasks = listOfTasks.size();
>>>>>>> Stashed changes
	
	/**
	 * this function just simply adds the task to the list then return it.
	 * @param task
	 * @return task 
	 */
	public EzTask addTask(EzTask task){
		
<<<<<<< Updated upstream
		listOfAllTasks.add(task);
		
		//System.out.println(numTasks);
		
=======
		listOfTask.add(task);
>>>>>>> Stashed changes
		return task;
	}
	
	/**
	 * this function adds the task to the list and provides an unique ID for the task then return it.
	 * @param task
	 * @return task with new id
	 */
	public EzTask addTaskWithNewId(EzTask task){
		
<<<<<<< Updated upstream
		listOfAllTasks.add(task);
		task.setId(numTasks);
		numTasks++;
		//System.out.println(numTasks);
=======
		listOfTask.add(task);
		task.setId(getSize()-1);
>>>>>>> Stashed changes
		return task;
		
	}
	
	/**
	 * this function replaces the tasks with certain ID in the original list, with the new tasks carrying the same ID in the new list.
	 * @param listOfTasksUpdated
	 * @return the number of tasks found and replaced.
	 */
<<<<<<< Updated upstream
	public int updateTask(ArrayList<EzTask> listOfTasksUpdated) {
		int count = 0;
		int index = 0;
		for(EzTask taskToBeUpdated : listOfTasksUpdated)
			for(EzTask task: listOfAllTasks)
				if(taskToBeUpdated.getId() == task.getId() )
				{
					index = task.getId();
					listOfAllTasks.remove(index);
					listOfAllTasks.add(index, taskToBeUpdated);
					count++;
				}
		
=======
	public int updateTask(ArrayList<EzTask> listOfUpdatedTasks) {
		int count = 0;
	
		for(int index=0; index < listOfUpdatedTasks.size(); index++ )
			for(int index1=0; index1< listOfTask.size(); index1++)
			{
				if(listOfUpdatedTasks.get(index).getId() == listOfTask.get(index1).getId())
				{	
					listOfTask.remove(index1);
					listOfTask.add(index1, listOfUpdatedTasks.get(index));
					count++;
				}
			}
>>>>>>> Stashed changes
		
		return count;
	}
	
	/**
	 * this function removes the task having the id as the id of the tasks from the listOfTasks
	 * @param listOfTasks
	 * @return the number of tasks found and removed.
	 */
<<<<<<< Updated upstream
	public int deleteTask(ArrayList<EzTask> listOfTasksDeleted){
		int count = 0;
		int index = 0;
		for(EzTask taskToBeDeleted: listOfTasksDeleted)
			for(EzTask task: listOfAllTasks)
				if(taskToBeDeleted.getId() == task.getId())
				{
					index = task.getId();
					listOfAllTasks.remove(index);
					count++;
			}
=======
	public int deleteTask(ArrayList<EzTask> listOfDeletedTasks){
		int count = 0;
		
		for(int index=0; index < listOfDeletedTasks.size(); index++)
			for(int index1=0; index1 < listOfTask.size(); index1++)
			{
				if(listOfDeletedTasks.get(index).getId()== listOfTask.get(index1).getId())
					listOfTask.remove(index1);
					count++;
			}
	
		
		
>>>>>>> Stashed changes
		return count;
	}
	
	/**
	 * this function returns the number of tasks in the list.
	 * @return
	 */
	public int getSize(){
		
<<<<<<< Updated upstream
		return listOfAllTasks.size();
=======
		return listOfTask.size();
>>>>>>> Stashed changes
	}
	
	/**
	 * this function finds the task by ID and return it if found or else return null.
	 * @param id
	 * @return EzTask or null
	 */
	public EzTask findTask(int id){
<<<<<<< Updated upstream
		for(EzTask task : listOfAllTasks)
			if(id == task.getId())
				return task;
		
			return null;
=======
		for(int index=0; index < listOfTask.size(); index++)
			if(id == listOfTask.get(index).getId())
				return listOfTask.get(id);
		
		return null;
>>>>>>> Stashed changes
	}
	
	/**
	 * this function finds all the tasks on the date and return that list.
	 * @param date
	 * @return ArrayList called tasksByDate or null
	 */
	public ArrayList<EzTask> getTasksByDate(Date date){
		ArrayList<EzTask> tasksByDate = new ArrayList<EzTask>();
		
		for(int index=0; index < listOfTask.size()l index++)
			if(date == listOfTask.get(index).getStartTime())
				tasksByDate.add(listOfTask.get(index));
		
				
			return tasksByDate;
	}
	
	/**
	 * this function return a list of tasks, which is sorted by priority then by date.
	 * @return ArrayList tasksByPriority
	 */
	public ArrayList<EzTask> getSortedTasksByPriority(){
		
		ArrayList<EzTask> tasksByPriority = new ArrayList<EzTask>();
		for(EzTask task : listOfAllTasks)
			for(int priority = 0; priority < 6 ; priority++)
			{
				if(task.getPriority() == priority)
					tasksByPriority.add(task);
					
			}
		
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
			for(EzTask task : listOfAllTasks)
				if(checkTitle(keyword, task))
					tasksWithKeywords.add(task);

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
	
	
}
