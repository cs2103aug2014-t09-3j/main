import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 
 */

/**
 * @author Khanh
 *
 */
public class EzSort {
	
	/**
	 * return the target sorted by Id
	 * @param target
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<EzTask> sortById(ArrayList<EzTask> target){
		
		ArrayList <EzTask> tasksById = new ArrayList<EzTask>();

		tasksById = (ArrayList<EzTask>)target.clone();

		Collections.sort(tasksById, EzTask.TaskIdComparator);


		return tasksById;
	
	}
	
	/**
	 * return the target sorted by start time
	 * @param target
	 * @return
	 */
	public static ArrayList<EzTask> sortByDate(ArrayList<EzTask> target){
		ArrayList<EzTask> tasksByDate = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByDate, EzTask.TaskDateComparator);
		return tasksByDate;
	}
	
	
	/**
	 * return the target sorted by Priority
	 * greatest priority comes first
	 * 
	 * @param target
	 * @return
	 */
	
	public static ArrayList<EzTask> sortByPriority(ArrayList<EzTask> target){
		
		ArrayList<EzTask> tasksByPriority = new ArrayList<EzTask>();
		tasksByPriority = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByPriority, EzTask.TaskPriorityComparator);
		return tasksByPriority;
		
	}
	
	/**
	 * return the target sorted by title
	 * @param target
	 * @return
	 */
	public static ArrayList<EzTask> sortByTitle(ArrayList<EzTask> target){
		ArrayList<EzTask> tasksByTitle = new ArrayList<EzTask>();
		tasksByTitle = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByTitle, EzTask.TaskTitleComparator);
		
		return tasksByTitle;
	}
	
	/**
	 * return the target sorted by venue
	 * @param target
	 * @return
	 */
	public static ArrayList<EzTask> sortByVenue(ArrayList<EzTask> target){
		ArrayList<EzTask> tasksByVenue = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByVenue, EzTask.TaskVenueComparator);
		return tasksByVenue;
	
	}
	
	/**
	 * return the target sorted by done
	 * Undone tasks come first
	 * 
	 * @param target
	 * @return
	 */
	public static ArrayList<EzTask> sortByDone(ArrayList<EzTask> target){
		ArrayList<EzTask> tasksDone = new ArrayList<EzTask>();
		ArrayList<EzTask> tasksNotDone = new ArrayList<EzTask>();
		
		for(EzTask task : target)
			if(task.isDone())
				tasksDone.add(task);
		
			else
				tasksNotDone.add(task);
				
		sortById(tasksDone);
		sortById(tasksNotDone);
	
		ArrayList<EzTask> taskByDone = (ArrayList<EzTask>) tasksNotDone.clone();
		taskByDone.addAll(tasksDone);
	
		
		return taskByDone;
	}
	
	
	
	
}
