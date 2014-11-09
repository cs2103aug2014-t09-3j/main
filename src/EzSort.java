import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


//@author A0113922N

public class EzSort {
	
	/**
	 * returns ArrayList tasksById sorted by task Id
	 * task id is sorted by ascending order
	 * @param target
	 * @return tasksById
	 */
	
	public static ArrayList<EzTask> sortById(ArrayList<EzTask> target){
		@SuppressWarnings("unchecked")
		ArrayList<EzTask> tasksById = (ArrayList<EzTask>)target.clone();

		Collections.sort(tasksById, TaskIdComparator);


		return tasksById;
	
	}
	
	/**
	 * returns ArrayList tasksByDate sorted by start time
	 *start time is sorted by how close is startTime to the current time.
	 * @param target
	 * @return tasksByDate
	 */
	
	public static ArrayList<EzTask> sortByDate(ArrayList<EzTask> target){
		@SuppressWarnings("unchecked")
		ArrayList<EzTask> tasksByDate = (ArrayList<EzTask>) target.clone();
		
		Collections.sort(tasksByDate, TaskDateComparator);
		
		return tasksByDate;
	}
	
	
	/**
	 * returns ArrayList tasksByPriority sorted by Priority
	 * greatest priority comes first
	 * @param target
	 * @return tasksByPriority
	 */
	
	public static ArrayList<EzTask> sortByPriority(ArrayList<EzTask> target){
		@SuppressWarnings("unchecked")
		ArrayList<EzTask> tasksByPriority = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByPriority, TaskPriorityComparator);
		return tasksByPriority;
		
	}
	
	/**
	 * returns ArrayList tasksByTitle sorted by task title
	 * titles are sorted alphabetically  
	 * @param target
	 * @return tasksByTitle
	 */
	
	public static ArrayList<EzTask> sortByTitle(ArrayList<EzTask> target){
		@SuppressWarnings("unchecked")
		ArrayList<EzTask> tasksByTitle = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByTitle, TaskTitleComparator);
		
		return tasksByTitle;
	}
	
	/**
	 * returns ArrayList tasksByVenue sorted by task venue
	 * venues are sorted alphabetically
	 * @param target
	 * @return tasksByVenue
	 */
	public static ArrayList<EzTask> sortByVenue(ArrayList<EzTask> target){
		@SuppressWarnings("unchecked")
		ArrayList<EzTask> tasksByVenue = (ArrayList<EzTask>) target.clone();
		Collections.sort(tasksByVenue,TaskVenueComparator);
		return tasksByVenue;
	
	}
	
	/**
	 * returns ArrayList tasksByDone sorted by the done status of tasks
	 * Undone tasks come before done tasks
	 * 
	 * @param target
	 * @return tasksByDone
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
	

	/**
	 * sorts the task by ascending task id
	 * 
	 */
	public static Comparator<EzTask> TaskIdComparator = new Comparator<EzTask>() {

		public int compare(EzTask task1, EzTask task2) {

			int taskId1 = task1.getId();
			int taskId2 = task2.getId();

			//ascending order
			return taskId1 - taskId2;

		}


	};
	
	/**
	 * sorts the tasks by ascending priority
	 */
	
	public static Comparator<EzTask> TaskPriorityComparator = new Comparator<EzTask>() {

		public int compare(EzTask task1, EzTask task2){

			int taskPriority1 = task1.getPriority();
			int taskPriority2 = task2.getPriority();

			return taskPriority2 - taskPriority1;
		}
	};
	
	/**
	 * sorts the tasks by the task title alphabetically
	 */
	public static Comparator<EzTask> TaskTitleComparator = new Comparator<EzTask>() {
        public int compare(EzTask task1, EzTask task2) {
        	
        	String taskTitle1 = task1.getTitle().toUpperCase();
        	String taskTitle2 = task2.getTitle().toUpperCase();
        	
            return taskTitle1.compareTo(taskTitle2);
        }
    };
   
    
    /**
     * sorts the tasks by ascending date
     */
    public static Comparator<EzTask> TaskDateComparator = new Comparator<EzTask>() {
    	public int compare(EzTask task1, EzTask task2){
    		if (task1.getStartTime() == null && task2.getStartTime() == null)
    	        return 0;
    		
    		else if(task1.getStartTime() == null && task2.getStartTime() != null)
    			return 1;
    		
    		else if(task1.getStartTime() != null && task2.getStartTime() == null)
    			return -1;
    		
    		return task1.getStartTime().getTime().compareTo(task2.getStartTime().getTime());
    	}
    };
    
    
    /**
     * sorts the task by venue alphabetically
     */
    public static Comparator<EzTask> TaskVenueComparator = new Comparator<EzTask>() {
    	public int compare(EzTask task1, EzTask task2){
    		
    		String taskVenue1 = task1.getVenue().toUpperCase();
    		String taskVenue2 = task2.getVenue().toUpperCase();
    		return taskVenue1.compareTo(taskVenue2);
    		
    	
    	}
    };
	
	
}
