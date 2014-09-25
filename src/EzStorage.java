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
	private ArrayList<EzTask> listOfAllTasks = new ArrayList<EzTask>();
	
	//numTasks represents the number of tasks entered by the user.
	int numTasks = 0;
	
	/**
	 * this function just simply adds the task to the list then return it.
	 * @param task
	 * @return task 
	 */
	public EzTask addTask(EzTask task){
		
		listOfAllTasks.add(task);
		numTasks++;
		//System.out.println(numTasks);
		task.setId(numTasks);
		return task;
	}
	
	/**
	 * this function adds the task to the list and provides an unique ID for the task then return it.
	 * @param task
	 * @return task with new id
	 */
	public EzTask addTaskWithNewId(EzTask task){
		
		listOfAllTasks.add(task);
		task.setId(numTasks);
		//System.out.println(numTasks);
		return task;
		
	}
	
	/**
	 * this function replaces the tasks with certain ID in the original list, with the new tasks carrying the same ID in the new list.
	 * @param listOfTasksUpdated
	 * @return the number of tasks found and replaced.
	 */
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
			for(EzTask task: listOfAllTasks)
				if(taskToBeDeleted.getId() == task.getId())
				{
					index = task.getId();
					listOfAllTasks.remove(index);
					count++;
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
			if(id == task.getId())
				return task;
		
			return null;
	}
	
	/**
	 * this function finds all the tasks on the date and return that list.
	 * @param date
	 * @return
	 */
	public ArrayList<EzTask> getTasksByDate(Date date){
		return null;
	}
	
	/**
	 * this function return a list of tasks, which is sorted by priority then by date.
	 * @return
	 */
	public ArrayList<EzTask> getSortedTasksByPriority(){
		return null;
	}
	
	/**
	 * this function return a list of tasks, which contain one or more words in the keywords.
	 * @param listOfKeywords
	 * @return
	 */
	public ArrayList<EzTask> getTasksByKeywords(ArrayList<String> listOfKeywords){
		return null;
	}
	
	
}
