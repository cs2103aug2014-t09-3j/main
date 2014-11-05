import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

//@author A0112220J
public class EzControllerTest {
	@Test
	public void testController() {
		
		ArrayList<EzAction> history;
		String tempCommand;
		
		EzController.setTesting(true);
		// history index 0
		EzController.execute("Add \"do automated testing\"");
		// history index 1
		EzController.execute("Add \"attend CS2103T tutorial\" at 9am at \"COM 2\" tomorrow");
		// history index 2
		EzController.execute("Add \"task 1\"");
		// history index 3
		EzController.execute("Add \"task 2\"");
		// history index 4
		EzController.execute("delete from 2 to 3");
		EzController.execute("Y");
		// history index 5
		EzController.execute("update 1 set venue \"COM 1\"");
		// history index 6
		EzController.execute("done 1");
		
		history = EzController.getHistory();
		
		// check the size of history
		assertEquals(7, history.size());
	
		// check history index 0
		assertEquals(TypeOfAction.ADD, history.get(0).getAction());
		assertEquals("do automated testing", history.get(0).getResults().get(0).getTitle());
		
		// check history index 1
		assertEquals(TypeOfAction.ADD, history.get(1).getAction());
		assertEquals("attend CS2103T tutorial", history.get(1).getResults().get(0).getTitle());
		assertEquals("COM 2", history.get(1).getResults().get(0).getVenue());
		assertEquals(9, history.get(1).getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, history.get(1).getResults().get(0).getEndTime().get(Calendar.MINUTE));
		
		// check history index 2
		assertEquals(TypeOfAction.ADD, history.get(2).getAction());
		assertEquals("task 1", history.get(2).getResults().get(0).getTitle());
		
		// check history index 3
		assertEquals(TypeOfAction.ADD, history.get(3).getAction());
		assertEquals("task 2", history.get(3).getResults().get(0).getTitle());
		
		// check history index 4
		assertEquals(TypeOfAction.DELETE, history.get(4).getAction());
		assertTrue(history.get(4).getResults() == null);
		assertEquals(2, history.get(4).getTargets().size());
		assertEquals("task 1", history.get(4).getTargets().get(0).getTitle());
		assertEquals("task 2", history.get(4).getTargets().get(1).getTitle());
		
		// check history index 5
		assertFalse(TypeOfAction.Y == history.get(5).getAction());
		assertEquals(TypeOfAction.UPDATE, history.get(5).getAction());
		assertEquals(1, history.get(5).getTargets().size());
		assertEquals(1, history.get(5).getResults().size());
		assertEquals("COM 1", history.get(5).getResults().get(0).getVenue());
		assertEquals("COM 2", history.get(5).getTargets().get(0).getVenue());
		
		// check history index 6
		assertEquals(TypeOfAction.DONE, history.get(6).getAction());
		assertTrue(history.get(6).getResults() != null);
		assertTrue(history.get(6).getTargets() != null);
		
		// history index 7 to index 21
		// a total of 7 tasks added previously
		// a total of 14 tasks added here
		// history exceeds 20, the first action in history will be removed
		for(int i = 0; i <= 13; i++) { 
			tempCommand = "add \"task " + i + "\"";
			EzController.execute(tempCommand);
		}

		// check history index 0 again after size of history exceeds 20
		// should be equals to previous index 1 action
		assertEquals(TypeOfAction.ADD, history.get(0).getAction());
		assertEquals("attend CS2103T tutorial", history.get(0).getResults().get(0).getTitle());
		assertEquals("COM 2", history.get(0).getResults().get(0).getVenue());
		assertEquals(9, history.get(0).getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, history.get(0).getResults().get(0).getEndTime().get(Calendar.MINUTE));
	}
}
