/**
 * Task Object  
 */

/**
 * @author Khanh
 * modified by Cao Tianwen
 * modified by Tun Leng
 */
import java.util.*;

public class EzTask {
	private int id;
	private String title;
	private GregorianCalendar startTime;
	private GregorianCalendar endTime;
	private String venue;
	private int priority;
	private boolean done;
	
	public EzTask(EzTask newTask){
		setId(newTask.getId());
		setTitle(newTask.getTitle());
		setStartTime(newTask.getStartTime());
		setEndTime(newTask.getEndTime());
		setVenue(newTask.getVenue());
		setPriority(newTask.getPriority());
		setDone(newTask.isDone());	
	}
	
    public EzTask() {
    }
    
    public EzTask(String title) {
        setTitle(title);
    }
    
    public EzTask(String title, String venue) {
        this(title);
        setVenue(venue);
    }
    
    public EzTask(String title, int priority) {
        this(title);
        setPriority(priority);
    }
    
    public EzTask(String title, String venue, int priority) {
        this(title,venue);
        setPriority(priority);
    }

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		if (title != null){
			this.title = new String(title);
		} else {
			this.title = "";
		}
	}
	
	public GregorianCalendar getStartTime() {
		return startTime;
	}
	
	public void setStartTime(GregorianCalendar startTime) {
		if (startTime != null){
			this.startTime = (GregorianCalendar) startTime.clone();
		} else {
			this.startTime = null;
		}
	}
	
	
	public void setStartTime(int year, int month, int day) {
		this.startTime = new GregorianCalendar(year,month,day);
	}

	public void setStartTime(int year, int month, int day,int hour,int minute) {
		this.startTime = new GregorianCalendar(year,month,day,hour,minute);
	}
	
	
	public GregorianCalendar getEndTime() {
		return endTime;
	}
	
	public void setEndTime(GregorianCalendar endTime) {
		if (endTime != null){
			this.endTime = (GregorianCalendar) endTime.clone();
		} else {
			this.endTime = null;
		}
	}
	
	
	public void setEndTime(int year, int month, int day) {
		this.endTime = new GregorianCalendar(year,month,day);
	}
	
	public void setEndTime(int year, int month, int day,int hour,int minute) {
		this.endTime = new GregorianCalendar(year,month,day,hour,minute);
	}
	
	
	public void setEndTimeAsStartTime(){
		this.endTime = this.startTime;
	}
	
	public String getVenue() {
		return venue;
	}
	
	public void setVenue(String venue) {
		if (venue!=null){
			this.venue = new String(venue);
		} else {
			this.venue = null;
		}
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
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

			return taskPriority1 - taskPriority2;
		}
	};
}
