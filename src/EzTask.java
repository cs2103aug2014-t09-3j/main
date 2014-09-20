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
	private Date startTime;
	private Date endTime;
	private String venue;
	private int priority;
	private boolean done;
	
	/**
	 * initialize by other EzTask Object
	 * @param target
	 */
	public EzTask(EzTask target){
		this.id = target.getId();
		this.title = new String(target.getTitle());
		this.startTime = new Date(target.getStartTime().getTime());
		this.endTime = new Date(target.getEndTime().getTime());
		this.venue = new String(target.getVenue());
		this.priority = target.getPriority();
		this.done = target.isDone();
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
		this.title = title;
	}
	
	/**
	 * get the Date from 3 inputs
	 * @param year
	 * @param month
	 * @param day
	 * @return Date
	 */
	private Date getTime(int year, int month, int day){
		Calendar cal = new GregorianCalendar(year,month,day);
		return cal.getTime();
	}
	
	/**
	 * get the Date from 5 inputs
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return Date
	 */
	private Date getTime(int year, int month, int day,int hour,int minute){
		Calendar cal = new GregorianCalendar(year,month,day,hour,minute);
		return cal.getTime();
	}
	
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * set startTime by 3 input
	 * @param year
	 * @param month
	 * @param day
	 */
	public void setStartTime(int year, int month, int day) {
		this.startTime = getTime(year,month,day);
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
		this.startTime = getTime(year,month,day,hour,minute);
	}
	
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * set endTime by 3 inputs
	 * @param year
	 * @param month
	 * @param day
	 */
	public void setEndTime(int year, int month, int day) {
		this.endTime = getTime(year,month,day);
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
		this.endTime = getTime(year,month,day,hour,minute);
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
		this.venue = venue;
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
