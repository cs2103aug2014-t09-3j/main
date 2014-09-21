/**
 * Task Object
 */

/**
 * @author Khanh
 *
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
	
	/**
	 * initialize by other EzTask Object
	 * @param target
	 */
	public EzTask(EzTask target){
		setId(target.getId());
		setTitle(target.getTitle());
		setStartTime(target.getStartTime());
		setEndTime(target.getEndTime());
		setVenue(target.getVenue());
		setPriority(target.getPriority());
		setDone(target.isDone());
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

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = new String(title);
	}
	
	/**
	 * @return the startTime
	 */
	public GregorianCalendar getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(GregorianCalendar startTime) {
		this.startTime = (GregorianCalendar) startTime.clone();
	}
	
	/**
	 * set startTime by 3 input
	 * @param year
	 * @param month
	 * @param day
	 */
	public void setStartTime(int year, int month, int day) {
		this.startTime = new GregorianCalendar(year,month,day);
	}
	
	/**
	 * set startTime by 5 input
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 */
	public void setStartTime(int year, int month, int day,int hour,int minute) {
		this.startTime = new GregorianCalendar(year,month,day,hour,minute);
	}
	
	/**
	 * @return the endTime
	 */
	public GregorianCalendar getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(GregorianCalendar endTime) {
		this.endTime = (GregorianCalendar) endTime.clone();
	}
	
	/**
	 * set endTime by 3 inputs
	 * @param year
	 * @param month
	 * @param day
	 */
	public void setEndTime(int year, int month, int day) {
		this.endTime = new GregorianCalendar(year,month,day);
	}
	
	/**
	 * set endTime by 5 inputs
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 */
	public void setEndTime(int year, int month, int day,int hour,int minute) {
		this.endTime = new GregorianCalendar(year,month,day,hour,minute);
	}
	
	/**
	 * this function is used for the task that has Deadline only
	 */
	public void setEndTimeAsStartTime(){
		this.endTime = this.startTime;
	}
	
	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}
	/**
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = new String(venue);
	}
	
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}
}
