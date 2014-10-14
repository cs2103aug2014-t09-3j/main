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

		Collections.sort(tasksById, TaskIdComparator);


		return tasksById;
	
	}
	
	/**
	 * return the target sorted by start time
	 * @param target
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<EzTask> sortByDate(ArrayList<EzTask> target){
		ArrayList<EzTask> tasksByDate = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByDate, TaskDateComparator);
		return tasksByDate;
	}
	
	
	/**
	 * return the target sorted by Priority
	 * greatest priority comes first
	 * 
	 * @param target
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public static ArrayList<EzTask> sortByPriority(ArrayList<EzTask> target){
		
		ArrayList<EzTask> tasksByPriority = new ArrayList<EzTask>();
		tasksByPriority = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByPriority, TaskPriorityComparator);
		return tasksByPriority;
		
	}
	
	/**
	 * return the target sorted by title
	 * @param target
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<EzTask> sortByTitle(ArrayList<EzTask> target){
		ArrayList<EzTask> tasksByTitle = new ArrayList<EzTask>();
		tasksByTitle = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByTitle, TaskTitleComparator);
		
		return tasksByTitle;
	}
	
	/**
	 * return the target sorted by venue
	 * @param target
	 * @return
	 */
	public static ArrayList<EzTask> sortByVenue(ArrayList<EzTask> target){
		@SuppressWarnings("unchecked")
		ArrayList<EzTask> tasksByVenue = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByVenue,TaskVenueComparator);
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
	
		@SuppressWarnings("unchecked")
		ArrayList<EzTask> taskByDone = (ArrayList<EzTask>) tasksNotDone.clone();
		taskByDone.addAll(tasksDone);
	
		
		return taskByDone;
	}
	

	
	public static Comparator<EzTask> TaskIdComparator = new Comparator<EzTask>() {

		public int compare(EzTask task1, EzTask task2) {

			int taskId1 = task1.getId();
			int taskId2 = task2.getId();

			//ascending order
			return taskId1 - taskId2;

		}


	};
	
	public static Comparator<EzTask> TaskPriorityComparator = new Comparator<EzTask>() {

		public int compare(EzTask task1, EzTask task2){

			int taskPriority1 = task1.getPriority();
			int taskPriority2 = task2.getPriority();

			return taskPriority2 - taskPriority1;
		}
	};
	
	public static Comparator<EzTask> TaskTitleComparator = new Comparator<EzTask>() {
        public int compare(EzTask task1, EzTask task2) {
        	
        	String taskTitle1 = task1.getTitle().toUpperCase();
        	String taskTitle2 = task2.getTitle().toUpperCase();
        	
            return taskTitle1.compareTo(taskTitle2);
        }
    };
    
    public static Comparator<EzTask> TaskDateComparator = new Comparator<EzTask>() {
    	public int compare(EzTask task1, EzTask task2){
    		if (task1.getStartTime() == null || task2.getStartTime() == null)
    	        return 0;
    		return task1.getStartTime().getTime().compareTo(task2.getStartTime().getTime());
    	}
    };
    
    public static Comparator<EzTask> TaskVenueComparator = new Comparator<EzTask>() {
    	public int compare(EzTask task1, EzTask task2){
    		
    		String taskVenue1 = task1.getVenue().toUpperCase();
    		String taskVenue2 = task2.getVenue().toUpperCase();
    		return taskVenue1.compareTo(taskVenue2);
    		
    	
    	}
    };
	
	
}
