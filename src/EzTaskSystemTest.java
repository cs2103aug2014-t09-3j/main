import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * 
 * @author Tun Leng
 *
 */


public class EzTaskSystemTest {

	@Test
	public void test() throws ParseException {
		
		
		
		EzController.setTesting(true);
		//task 1
		EzController.execute("add \"doing automated testing\" ");
		//task 2
		EzController.execute("add \"automated testing 2\" at \"utown\" ");
		
		
		//checking size of task list
		assertEquals ("size of task list = " , 2, getListSize());
		
		//checking task 1
		assertEquals ("task 1 title: " , "doing automated testing", getTaskTitle(1));
		
		/*Calendar expectedDate = Calendar.getInstance();
		expectedDate = setDate(2014, 11, 5);
		assertEquals("task 1 date: ", expectedDate, getTaskDate(1));
		*/
		
		//checking task 2
		assertEquals("task 2 title: " , "automated testing 2", getTaskTitle(2));
		assertEquals("task2 venue: " , "utown", getTaskVenue(2));
		
		
			
	}

	

	private Object getListSize() {
		return EzController.getStorage().getListOfAllTasks().size();
	}



	private Object getTaskVenue(int i) {
		return EzController.getStorage().findTask(i).getVenue();
	}

	/*private Object getTaskDate(int i) {
		return EzController.getStorage().findTask(i).getStartTime();
		
	}
	*/

	private Object getTaskTitle(int i) {
		return EzController.getStorage().findTask(i).getTitle();
		
	}
	
	/*
	private Calendar setDate(int year, int month, int day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
		 
		Calendar calendar = new GregorianCalendar();	
		
	 
		//update a date
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		
		return calendar;
	}
	*/


	

	

}
