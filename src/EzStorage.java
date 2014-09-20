import java.util.ArrayList;
import java.util.Date;

/**
 * 
 */

/**
 * @author Khanh
 *
 */
public class EzStorage {
	
	
	/**
	 * this function adds the task to the list and provides an unique ID for the task then return it.
	 * @param task
	 * @return task with new id
	 */
	public EzTask addTask(EzTask task){
		return null;
	}
	
	/**
	 * this function replaces the tasks with certain ID in the original list, with the new tasks carrying the same ID in the new list.
	 * @param listOfTasks
	 * @return the number of tasks found and replaced.
	 */
	public int updateTask(ArrayList<EzTask> listOfTasks) {
		int count = 0;
		
		return count;
	}
	
	/**
	 * this function removes the task having the id as the id of the tasks from the listOfTasks
	 * @param listOfTasks
	 * @return the number of tasks found and removed.
	 */
	public int deleteTask(ArrayList<EzTask> listOfTasks){
		int count = 0;
		
		return count;
	}
	
	/**
	 * this function returns the number of tasks in the list.
	 * @return
	 */
	public int getSize(){
		return 0;
	}
	
	/**
	 * this function finds the task by ID and return it if found or else return null.
	 * @param id
	 * @return EzTask or null
	 */
	public EzTask findTask(int id){
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
