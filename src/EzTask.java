/**
 * Task Object
 */

/**
 * @author Khanh
 * modified by Cao Tianwen
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
		this.title = new String(title);
	}
	
	public GregorianCalendar getStartTime() {
		return startTime;
	}
	
	public void setStartTime(GregorianCalendar startTime) {
		this.startTime = (GregorianCalendar) startTime.clone();
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
		this.endTime = (GregorianCalendar) endTime.clone();
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
		this.venue = new String(venue);
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
}
