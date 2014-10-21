import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;


public class EzTaskTest {

	@Test
	public void test() {
		EzTask task = new EzTask();
		task.setStartTime(new GregorianCalendar());
		
		// set date 10/11/2014 at 23:23
		task.setStartTime(2014,10,10,23, 23);
		
		// change time to 15:15
		task.setStartTime(15,15);
		
		// check for 10/11/2014 at 15:15
		assertEquals("Check Year: ", 2014, task.getStartTime().get(Calendar.YEAR));
		assertEquals("Check Month: ", Calendar.NOVEMBER, task.getStartTime().get(Calendar.MONTH));
		assertEquals("Check Day: ", 10, task.getStartTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("Check Hour: ", 15, task.getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("Check Minute: ", 15, task.getStartTime().get(Calendar.MINUTE));
		
	}

}
