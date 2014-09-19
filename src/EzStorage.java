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
	 * @return task with id
	 */
	public EzTask addTask(EzTask task){
		return null;
	}
	
	
	/**
	 * this function creates a copy of the task then changes the title of the copy into title and returns the copy.
	 * @param task
	 * @param title
	 * @return the copy of the task with a new title
	 */
	public EzTask editTitle(EzTask task, String title){
		return null;
	}
	
	/**
	 * this function creates a copy of the task then changes the date of the copy into date and returns the copy.
	 * @param task
	 * @param date
	 * @return the copy of the task with a new date
	 * 
	 * Note: Date here includes both date and time
	 * 
	 */
	public EzTask editDate(EzTask task, Date date){
		return null;
	}
	
	/**
	 * this function creates a copy of the task then changes the priority of the copy into priority and returns the copy.
	 * @param task
	 * @param priority
	 * @return the copy of the task with a new priority
	 */
	public EzTask editPriority(EzTask task, int priority){
		return null;
	}
	
	/**
	 * this function creates a copy of the task then changes the venue of the copy into venue and returns the copy.
	 * @param task
	 * @param venue
	 * @return the copy of the task with a new venue
	 */
	public EzTask editVenue(EzTask task, String venue){
		return null;
	}
	
	/**
	 * this function creates a copy of the listOfTasks then marks all tasks in the copy as Done and return the copy.
	 * @param listOfTasks
	 * @return
	 */
	public ArrayList<EzTask> updateTask(ArrayList<EzTask> oriTask) {
		return null;
	}
	
	public ArrayList<EzTask> markDone(ArrayList<EzTask> listOfTasks){ 
		return null;
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
	 * this function removes the task having the id as id of the tasks from the list and return it
	 * @param listOfTasks
	 * @return
	 */
	public ArrayList<EzTask> deleteTask(ArrayList<EzTask> listOfTasks){
		return null;
	}
	
	/**
	 * this function removes the task having the id as id and return it
	 * @param listOfTasks
	 * @return
	 */
	public EzTask deleteTask(EzTask task){
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
	public ArrayList<EzTask> getTasksByKeyword(ArrayList<String> listOfKeywords){
		return null;
	}
	
	
}
