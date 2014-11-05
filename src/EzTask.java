import java.util.*;

//@author A0112129U
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
		if (startTime!=null){
			return (GregorianCalendar) startTime.clone();
		} else {
			return null;
		}
	}
	
	public void setStartTime(GregorianCalendar startTime) {
		if (startTime != null){
			this.startTime = (GregorianCalendar) startTime.clone();
		} else {
			this.startTime = null;
		}
	}
	
	public void setStartTime(int year, int month, int day) {
		if (this.startTime==null){
			this.startTime = new GregorianCalendar(year, month, day);
		} else {
			this.startTime = (GregorianCalendar) this.startTime.clone();
			this.startTime.set(Calendar.YEAR, year);
			this.startTime.set(Calendar.MONTH, month);
			this.startTime.set(Calendar.DAY_OF_MONTH, day);
		}
	}

	public void setStartTime(int hour, int minute) {
		if (this.startTime==null){
			this.startTime = new GregorianCalendar();
		} else {
			this.startTime = (GregorianCalendar) this.startTime.clone();
		}
		this.startTime.set(Calendar.HOUR_OF_DAY, hour);
		this.startTime.set(Calendar.MINUTE, minute);
	}

	public void setStartTime(int year, int month, int day,int hour,int minute) {
		this.startTime = new GregorianCalendar(year,month,day,hour,minute);
	}
	
	
	public GregorianCalendar getEndTime() {
		if (endTime!=null){
			return (GregorianCalendar) endTime.clone();
		} else {
			return null;
		}
	}
	
	public void setEndTime(GregorianCalendar endTime) {
		if (endTime != null){
			this.endTime = (GregorianCalendar) endTime.clone();
		} else {
			this.endTime = null;
		}
	}
	
	
	public void setEndTime(int year, int month, int day) {
		if (this.endTime==null){
			this.endTime = new GregorianCalendar(year, month, day);
		} else {
			this.endTime = (GregorianCalendar) this.endTime.clone();
			this.endTime.set(Calendar.YEAR, year);
			this.endTime.set(Calendar.MONTH, month);
			this.endTime.set(Calendar.DAY_OF_MONTH, day);
		}
	}
	
	public void setEndTime(int hour, int minute) {
		if (this.endTime==null){
			this.endTime = new GregorianCalendar();
		} else {
			this.endTime = (GregorianCalendar) this.endTime.clone();
		}
		this.endTime.set(Calendar.HOUR_OF_DAY, hour);
		this.endTime.set(Calendar.MINUTE, minute);
	}
	
	public void setEndTime(int year, int month, int day,int hour,int minute) {
		this.endTime = new GregorianCalendar(year,month,day,hour,minute);
	}
	
	public void setEndTimeAsStartTime(){
		this.endTime = this.startTime;
	}
	
	public String getVenue() {
		if (venue==null) {
			return "";
		}
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
	
	public String toString(){
		String idText = String.format("[#%d]", this.getId());
		
		String venueText = "";
		if (this.getVenue()!=null) {
			if (venueText.length()>30){
				venueText = String.format("- @[%s]", this.getVenue().substring(0, 30)) + "...";
			} else {
				venueText = String.format("- @[%s]", this.getVenue());
			}
		}
		
		String titleText = "\"";
		if (this.getTitle().length()>50){
			titleText += this.getTitle().substring(0,50) + "...";
		} else {
			titleText += this.getTitle();
		}
		titleText += "\"";
		
		String dateText = "";
		if (this.getStartTime()!=null){
			dateText = "- [";
			if (this.getStartTime()==this.getEndTime()){
				GregorianCalendar time = this.getStartTime(); 
				dateText += String.format("%d/%d/%d", time.get(Calendar.DAY_OF_MONTH), time.get(Calendar.MONTH)+1, time.get(Calendar.YEAR));
				if ((time.get(Calendar.HOUR_OF_DAY)!=0) || (time.get(Calendar.MINUTE)!=0)){
					dateText += " " + String.format("%02d:%02d",time.get(Calendar.HOUR_OF_DAY),time.get(Calendar.MINUTE));
				}
			} else {
				GregorianCalendar startTime = this.getStartTime(); 
				GregorianCalendar endTime = this.getEndTime(); 
				
				if ((startTime.get(Calendar.HOUR_OF_DAY)!=0) || (startTime.get(Calendar.MINUTE)!=0) || 
						(endTime.get(Calendar.HOUR_OF_DAY)!=0) || (endTime.get(Calendar.MINUTE)!=0)){
					dateText += String.format("%d/%d/%d %02d:%02d => %d/%d/%d %02d:%02d",
							startTime.get(Calendar.DAY_OF_MONTH),
							startTime.get(Calendar.MONTH)+1,
							startTime.get(Calendar.YEAR),
							startTime.get(Calendar.HOUR_OF_DAY),
							startTime.get(Calendar.MINUTE),
							endTime.get(Calendar.DAY_OF_MONTH),
							endTime.get(Calendar.MONTH)+1,
							endTime.get(Calendar.YEAR),
							endTime.get(Calendar.HOUR_OF_DAY),
							endTime.get(Calendar.MINUTE));
				} else {
					dateText += String.format("%d/%d/%d => %d/%d/%d",
							startTime.get(Calendar.DAY_OF_MONTH),
							startTime.get(Calendar.MONTH)+1,
							startTime.get(Calendar.YEAR),
							endTime.get(Calendar.DAY_OF_MONTH),
							endTime.get(Calendar.MONTH)+1,
							endTime.get(Calendar.YEAR));
				}
			}
			dateText += "]";
		}
		return String.format("%s %s %s %s", idText, titleText, venueText, dateText); 
	}

	public boolean isPast(){
		if (this.getEndTime()==null){
			return false;
		} else {
			GregorianCalendar today = new GregorianCalendar();
			return this.getEndTime().before(today);
		}
	}
	
	public boolean isToday(){
		if (this.getStartTime()==null){
			return false;
		} else {
			GregorianCalendar startToday = new GregorianCalendar();
			startToday.set(Calendar.HOUR_OF_DAY, 0);
			startToday.set(Calendar.MINUTE,0);
			startToday.set(Calendar.SECOND,0);
			startToday.set(Calendar.MILLISECOND,0);
			GregorianCalendar endToday = new GregorianCalendar();
			endToday.set(Calendar.HOUR_OF_DAY, 23);
			endToday.set(Calendar.MINUTE,59);
			endToday.set(Calendar.SECOND,59);
			endToday.set(Calendar.MILLISECOND,0);
			return ((this.getStartTime().after(startToday) || this.getStartTime().equals(startToday)) && 
					(this.getStartTime().before(endToday) || this.getStartTime().equals(endToday)));
		}
	}
}
