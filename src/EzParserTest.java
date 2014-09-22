import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;


public class EzParserTest {

	@Test
	public void testAddCommand() {
		/**
		 * Test Case 1
		 */
		EzAction action = EzParser.extractInfo("Add \"play badminton\"", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", null, action.getResults().get(0).getVenue());
		assertEquals("check startTime of task: ", null, action.getResults().get(0).getStartTime());
		assertEquals("check endTime of task: ", null, action.getResults().get(0).getEndTime());
		assertEquals("check priority of task: ", 0, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		/**
		 * Test Case 2
		 */
		action = EzParser.extractInfo("Add \"play badminton\" on 26/09 *****", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", null, action.getResults().get(0).getVenue());
		assertTrue("check startTime = endTime: ", action.getResults().get(0).getStartTime() == action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 9, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check priority of task: ", 5, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		/**
		 * Test Case 3
		 */
		action = EzParser.extractInfo("Add \"play badminton\" on 26/09 3:14pm *****", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", null, action.getResults().get(0).getVenue());
		assertTrue("check startTime = endTime: ", action.getResults().get(0).getStartTime() == action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 9, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 15, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 14, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
		assertEquals("check priority of task: ", 5, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		/**
		 * Test Case 4
		 */
		action = EzParser.extractInfo("Add \"play badminton\" at \"pgp\" on 26/09 3:14pm *****", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", "pgp", action.getResults().get(0).getVenue());
		assertTrue("check startTime = endTime: ", action.getResults().get(0).getStartTime() == action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 9, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 15, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 14, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
		assertEquals("check priority of task: ", 5, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		/**
		 * Test Case 5
		 */
		action = EzParser.extractInfo("Add \"play badminton\" at \"pgp\" on 26/09 from 3pm to 6:25pm *****", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", "pgp", action.getResults().get(0).getVenue());
		assertTrue("check startTime != endTime: ", action.getResults().get(0).getStartTime() != action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getStartTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 9, action.getResults().get(0).getStartTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 15, action.getResults().get(0).getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 0, action.getResults().get(0).getStartTime().get(Calendar.MINUTE));
		
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 9, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 18, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 25, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
		assertEquals("check priority of task: ", 5, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		/**
		 * Test Case 6
		 */
		action = EzParser.extractInfo("Add \"do homewodk\" on 26/09 from 23h to 2:40am *****", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", "pgp", action.getResults().get(0).getVenue());
		assertTrue("check startTime != endTime: ", action.getResults().get(0).getStartTime() != action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getStartTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 9, action.getResults().get(0).getStartTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 23, action.getResults().get(0).getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 0, action.getResults().get(0).getStartTime().get(Calendar.MINUTE));
		
		assertEquals("check date of task: ", 27, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 9, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 2, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 40, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
		assertEquals("check priority of task: ", 5, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
	}


}
