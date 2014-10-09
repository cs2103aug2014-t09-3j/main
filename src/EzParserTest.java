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
		action = EzParser.extractInfo("Add \"play badminton\" on 26/09/2014 **", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", null, action.getResults().get(0).getVenue());
		assertTrue("check startTime = endTime: ", action.getResults().get(0).getStartTime() == action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 8, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check priority of task: ", 2, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		/**
		 * Test Case 3
		 */
		action = EzParser.extractInfo("Add \"play badminton\" on 26/09/2014 3:14pm ***", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", null, action.getResults().get(0).getVenue());
		assertTrue("check startTime = endTime: ", action.getResults().get(0).getStartTime() == action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 8, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 15, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 14, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
    	assertEquals("check priority of task: ", 3, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		/**
		 * Test Case 4
		 */
		action = EzParser.extractInfo("Add \"play badminton\" at \"pgp\" on 26/09/2014 3:14pm ***", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", "pgp", action.getResults().get(0).getVenue());
		assertTrue("check startTime = endTime: ", action.getResults().get(0).getStartTime() == action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 8, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 15, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
	    assertEquals("check minute of task: ", 14, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
		assertEquals("check priority of task: ", 3, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		/**
		 * Test Case 4.1
		 */
		action = EzParser.extractInfo("Add \"play badminton\" on 26/09/2014 3:14pm at \"pgp\" ***", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", "pgp", action.getResults().get(0).getVenue());
		assertTrue("check startTime = endTime: ", action.getResults().get(0).getStartTime() == action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 8, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 15, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
	    assertEquals("check minute of task: ", 14, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
		assertEquals("check priority of task: ", 3, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		
		/**
		 * Test Case 5
		 */
		action = EzParser.extractInfo("Add \"play badminton\" at \"pgp\" on 26/09/2014 from 3pm to 6:25pm ***", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
		assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check venue of task: ", "pgp", action.getResults().get(0).getVenue());
		assertTrue("check startTime != endTime: ", action.getResults().get(0).getStartTime() != action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getStartTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 8, action.getResults().get(0).getStartTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 15, action.getResults().get(0).getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 0, action.getResults().get(0).getStartTime().get(Calendar.MINUTE));
		
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 8, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 18, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 25, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
		assertEquals("check priority of task: ", 3, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
		
		/**
		 * Test Case 6
		 */
	//homework!not homewodk
		action = EzParser.extractInfo("Add \"do homework\" on 26/09/2014 from 23h to 2:40am ***", null);
		assertEquals("check add action: ", TypeOfAction.ADD, action.getAction());
		assertTrue("check target = null: ", action.getTargets() == null);
		assertTrue("check result != null: ", action.getResults() != null);
		assertEquals("check size of result: ",1,action.getResults().size());
	//this time is do homework
		//assertEquals("check title of task: ","play badminton",action.getResults().get(0).getTitle());
		assertEquals("check title of task: ","do homework",action.getResults().get(0).getTitle());
		//there is no venue in this command
	//	assertEquals("check venue of task: ", "pgp", action.getResults().get(0).getVenue());
		assertTrue("check startTime != endTime: ", action.getResults().get(0).getStartTime() != action.getResults().get(0).getEndTime());
		assertEquals("check date of task: ", 26, action.getResults().get(0).getStartTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 8, action.getResults().get(0).getStartTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 23, action.getResults().get(0).getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 0, action.getResults().get(0).getStartTime().get(Calendar.MINUTE));
		
		assertEquals("check date of task: ", 26, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("check month of task: ", 8, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("check hour of task: ", 2, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("check minute of task: ", 40, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
		assertEquals("check priority of task: ", 3, action.getResults().get(0).getPriority());
		assertEquals("check done of task: ", false, action.getResults().get(0).isDone());
	}

	@Test
	public void testUpdateCommand() {
		EzStorage storage = new EzStorage();
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 1\" at \"venue1\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 0
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 2\" at \"venue2\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 1
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 3\" at \"venue3\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 2
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 4\" at \"venue4\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 3
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 5\" at \"venue5\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 4
		
		// check change title
		EzAction action = EzParser.extractInfo("update 0 set title \"new title 1\"",storage);
    	assertEquals("Check action is UPDATE: ", TypeOfAction.UPDATE, action.getAction());
		assertEquals("Check size of target: ", 1, action.getTargets().size());
		assertEquals("Check size of result: ", 1, action.getResults().size());
		assertEquals("Check id of target: ", 0, action.getTargets().get(0).getId());
		assertEquals("Check id of result: ", 0, action.getResults().get(0).getId());
		assertEquals("Check title of target: ", "task 1", action.getTargets().get(0).getTitle());
		assertEquals("Check title of result: ", "new title 1", action.getResults().get(0).getTitle());
		storage.updateTask(action.getResults());
		
		// check change venue
		action = EzParser.extractInfo("update 1 set Venue \"new venue 2\"",storage);
		assertEquals("Check action is UPDATE: ", TypeOfAction.UPDATE, action.getAction());
		assertEquals("Check size of target: ", 1, action.getTargets().size());
		assertEquals("Check size of result: ", 1, action.getResults().size());
		assertEquals("Check id of target: ", 1, action.getTargets().get(0).getId());
		assertEquals("Check id of result: ", 1, action.getResults().get(0).getId());
		assertEquals("Check venue of target: ", "venue2", action.getTargets().get(0).getVenue());
		assertEquals("Check venue of result: ", "new venue 2", action.getResults().get(0).getVenue());
		storage.updateTask(action.getResults());
		
		// check change date
		action = EzParser.extractInfo("update 2 set Date 30/8/2015",storage);
		assertEquals("Check action is UPDATE: ", TypeOfAction.UPDATE, action.getAction());
		assertEquals("Check size of target: ", 1, action.getTargets().size());
		assertEquals("Check size of result: ", 1, action.getResults().size());
		assertEquals("Check id of target: ", 2, action.getTargets().get(0).getId());
		assertEquals("Check id of result: ", 2, action.getResults().get(0).getId());
		assertEquals("Check date of target: ", 26, action.getTargets().get(0).getStartTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("Check date of result: ", 30, action.getResults().get(0).getStartTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("Check date of target: ", 26, action.getTargets().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("Check date of result: ", 30, action.getResults().get(0).getEndTime().get(Calendar.DAY_OF_MONTH));
		assertEquals("Check month of target: ", Calendar.JULY, action.getTargets().get(0).getStartTime().get(Calendar.MONTH));
		assertEquals("Check month of result: ", Calendar.AUGUST, action.getResults().get(0).getStartTime().get(Calendar.MONTH));
		assertEquals("Check month of target: ", Calendar.JULY, action.getTargets().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("Check month of result: ", Calendar.AUGUST, action.getResults().get(0).getEndTime().get(Calendar.MONTH));
		assertEquals("Check year of target: ", 2014, action.getTargets().get(0).getStartTime().get(Calendar.YEAR));
		assertEquals("Check year of result: ", 2015, action.getResults().get(0).getStartTime().get(Calendar.YEAR));
		assertEquals("Check year of target: ", 2014, action.getTargets().get(0).getEndTime().get(Calendar.YEAR));
		assertEquals("Check year of result: ", 2015, action.getResults().get(0).getEndTime().get(Calendar.YEAR));
		storage.updateTask(action.getResults());
		
		//check change priority
		action = EzParser.extractInfo("update 3 set priority ***",storage);
		assertEquals("Check action is UPDATE: ", TypeOfAction.UPDATE, action.getAction());
		assertEquals("Check size of target: ", 1, action.getTargets().size());
		assertEquals("Check size of result: ", 1, action.getResults().size());
		assertEquals("Check id of target: ", 3, action.getTargets().get(0).getId());
		assertEquals("Check id of result: ", 3, action.getResults().get(0).getId());
		assertEquals("Check priority of target: ", 0, action.getTargets().get(0).getPriority());
		assertEquals("Check priority of result: ", 3, action.getResults().get(0).getPriority());
		storage.updateTask(action.getResults());
		
		// check change time
		action = EzParser.extractInfo("update 4 set start time 12h15",storage);
		assertEquals("Check action is UPDATE: ", TypeOfAction.UPDATE, action.getAction());
		assertEquals("Check size of target: ", 1, action.getTargets().size());
		assertEquals("Check size of result: ", 1, action.getResults().size());
		assertEquals("Check id of target: ", 4, action.getTargets().get(0).getId());
		assertEquals("Check id of result: ", 4, action.getResults().get(0).getId());
		assertEquals("Check start hour of target: "		, 10, action.getTargets().get(0).getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("Check start minute of target: "	, 0, action.getTargets().get(0).getStartTime().get(Calendar.MINUTE));
		assertEquals("Check end hour of target: "		, 17, action.getTargets().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("Check end minute of target: "		, 0, action.getTargets().get(0).getEndTime().get(Calendar.MINUTE));
		assertEquals("Check start hour of result: "		, 12, action.getResults().get(0).getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("Check start minute of result: "	, 15, action.getResults().get(0).getStartTime().get(Calendar.MINUTE));
		assertEquals("Check end hour of result: "		, 17, action.getResults().get(0).getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals("Check end minute of result: "		, 0, action.getResults().get(0).getEndTime().get(Calendar.MINUTE));
		storage.updateTask(action.getResults());
	}
	
	@Test
	public void testDeleteCommand() {
		EzStorage storage = new EzStorage();
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 1\" at \"venue1\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 0
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 2\" at \"venue2\" on 27/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 1
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 3\" at \"venue3\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 2
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 4\" at \"venue4\" on 27/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 3
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 5\" at \"venue5\" on 27/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 4
		
		EzAction action = EzParser.extractInfo("Delete 2",storage);
		assertEquals("Check type of action: ", TypeOfAction.DELETE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is null: ", action.getResults() == null);
		assertEquals("Check size of the target: ", 1, action.getTargets().size());
		assertEquals("Check id of the target: ", 2, action.getTargets().get(0).getId());
		
		action = EzParser.extractInfo("Delete 2 4 5",storage);	
		assertEquals("Check type of action: ", TypeOfAction.DELETE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is null: ", action.getResults() == null);
		assertEquals("Check size of the target: ", 2, action.getTargets().size());	// there is no task with id 5 so only tasks with id 2 and 4 are returned
		assertEquals("Check id of the target: ", 2, action.getTargets().get(0).getId());
		assertEquals("Check id of the target: ", 4, action.getTargets().get(1).getId());
		
		action = EzParser.extractInfo("Delete from 1 to 4",storage);
		assertEquals("Check type of action: ", TypeOfAction.DELETE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is null: ", action.getResults() == null);
		assertEquals("Check size of the target: ", 4, action.getTargets().size());
		assertEquals("Check id of the target: ", 1, action.getTargets().get(0).getId());
		assertEquals("Check id of the target: ", 2, action.getTargets().get(1).getId());
		assertEquals("Check id of the target: ", 3, action.getTargets().get(2).getId());
		assertEquals("Check id of the target: ", 4, action.getTargets().get(3).getId());
		
		action = EzParser.extractInfo("Delete from 1 to 10",storage);
		assertEquals("Check type of action: ", TypeOfAction.DELETE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is null: ", action.getResults() == null);
		assertEquals("Check size of the target: ", 4, action.getTargets().size()); // only tasks with id 1,2,3 and 4 are returned
		assertEquals("Check id of the target: ", 1, action.getTargets().get(0).getId());
		assertEquals("Check id of the target: ", 2, action.getTargets().get(1).getId());
		assertEquals("Check id of the target: ", 3, action.getTargets().get(2).getId());
		assertEquals("Check id of the target: ", 4, action.getTargets().get(3).getId());
		
		
/*		action = EzParser.extractInfo("Delete all on 27/7/2014",storage);
		assertEquals("Check type of action: ", TypeOfAction.DELETE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is null: ", action.getResults() == null);
		assertEquals("Check size of the target: ", 3, action.getTargets().size());
		assertEquals("Check id of the target: ", 1, action.getTargets().get(0).getId());
		assertEquals("Check id of the target: ", 3, action.getTargets().get(1).getId());
		assertEquals("Check id of the target: ", 4, action.getTargets().get(2).getId());
*/		
	}
	
	@Test
	public void testDoneCommand() {
		EzStorage storage = new EzStorage();
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 1\" at \"venue1\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 0
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 2\" at \"venue2\" on 27/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 1
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 3\" at \"venue3\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 2
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 4\" at \"venue4\" on 27/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 3
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 5\" at \"venue5\" on 27/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 4
		
		EzAction action = EzParser.extractInfo("DONE 2",storage);
		assertEquals("Check type of action: ", TypeOfAction.DONE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is not null: ", action.getResults() != null);
		assertEquals("Check size of the target: ", 1, action.getTargets().size());
		assertEquals("Check id of the target: ", 2, action.getTargets().get(0).getId());
		
		action = EzParser.extractInfo("DONE 2 4 5",storage);	
		assertEquals("Check type of action: ", TypeOfAction.DONE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is not null: ", action.getResults() != null);
		assertEquals("Check size of the target: ", 2, action.getTargets().size());	// there is no task with id 5 so only tasks with id 2 and 4 are returned
		assertEquals("Check id of the target: ", 2, action.getTargets().get(0).getId());
		assertEquals("Check id of the target: ", 4, action.getTargets().get(1).getId());
		
		action = EzParser.extractInfo("DONE from 1 to 4",storage);
		assertEquals("Check type of action: ", TypeOfAction.DONE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is not null: ", action.getResults() != null);
		assertEquals("Check size of the target: ", 4, action.getTargets().size());
		assertEquals("Check id of the target: ", 1, action.getTargets().get(0).getId());
		assertEquals("Check id of the target: ", 2, action.getTargets().get(1).getId());
		assertEquals("Check id of the target: ", 3, action.getTargets().get(2).getId());
		assertEquals("Check id of the target: ", 4, action.getTargets().get(3).getId());
		
		action = EzParser.extractInfo("DONE from 1 to 10",storage);
		assertEquals("Check type of action: ", TypeOfAction.DONE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is not null: ", action.getResults() != null);
		assertEquals("Check size of the target: ", 4, action.getTargets().size()); // only tasks with id 1,2,3 and 4 are returned
		assertEquals("Check id of the target: ", 1, action.getTargets().get(0).getId());
		assertEquals("Check id of the target: ", 2, action.getTargets().get(1).getId());
		assertEquals("Check id of the target: ", 3, action.getTargets().get(2).getId());
		assertEquals("Check id of the target: ", 4, action.getTargets().get(3).getId());
		
		
		action = EzParser.extractInfo("DONE all on 27/7/2014",storage);
		assertEquals("Check type of action: ", TypeOfAction.DONE, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is not null: ", action.getResults() != null);
		assertEquals("Check size of the target: ", 3, action.getTargets().size());
		assertEquals("Check id of the target: ", 1, action.getTargets().get(0).getId());
		assertEquals("Check id of the target: ", 3, action.getTargets().get(1).getId());
		assertEquals("Check id of the target: ", 4, action.getTargets().get(2).getId());
		
	}
	
	@Test
	public void testShowCommand() {
		EzStorage storage = new EzStorage();
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 0 ba\" at \"venue0\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 0
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 1 aa\" at \"venue1\" on 27/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 1
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 2 ab\" at \"venue2\" on 26/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 2
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 3 aa\" at \"venue3\" on 27/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 3
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 4 ac\" at \"venue4\" on 27/7/2014 from 10am to 5pm", storage).getResults().get(0)); // id 4
		
		EzAction action = EzParser.extractInfo("DONE 2 4 5",storage); // only task 2 & 4 are marked as done
		storage.updateTask(action.getResults());
		
		
		action = EzParser.extractInfo("Show all",storage);	
		assertEquals("Check type of action: ", TypeOfAction.SHOW, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is the same pointer as target: ", action.getTargets() == action.getResults());
		assertEquals("Check size of the target: ", 5, action.getTargets().size());
		
		action = EzParser.extractInfo("Show done",storage);	
		assertEquals("Check type of action: ", TypeOfAction.SHOW, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is the same pointer as target: ", action.getTargets() == action.getResults());
		assertEquals("Check size of the target: ", 2, action.getTargets().size());
		
		action = EzParser.extractInfo("Show undone",storage);	
		assertEquals("Check type of action: ", TypeOfAction.SHOW, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is the same pointer as target: ", action.getTargets() == action.getResults());
		assertEquals("Check size of the target: ", 3, action.getTargets().size());
		
		action = EzParser.extractInfo("Show all on 27/7/2014",storage);	
		assertEquals("Check type of action: ", TypeOfAction.SHOW, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is the same pointer as target: ", action.getTargets() == action.getResults());
	//	assertEquals("Check size of the target: ", 3, action.getTargets().size());
		
		action = EzParser.extractInfo("Show done on 27/7/2014",storage);	
		assertEquals("Check type of action: ", TypeOfAction.SHOW, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is the same pointer as target: ", action.getTargets() == action.getResults());
	//	assertEquals("Check size of the target: ", 1, action.getTargets().size());
		
		action = EzParser.extractInfo("Show undone on 27/7/2014",storage);	
		assertEquals("Check type of action: ", TypeOfAction.SHOW, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is the same pointer as target: ", action.getTargets() == action.getResults());
		//assertEquals("Check size of the target: ", 2, action.getTargets().size());
		
		action = EzParser.extractInfo("Show all have \"aa\" \"ba\"",storage);	
		assertEquals("Check type of action: ", TypeOfAction.SHOW, action.getAction());
		assertTrue("Check target is not null: ", action.getTargets() != null);
		assertTrue("Check result is the same pointer as target: ", action.getTargets() == action.getResults());
	//	assertEquals("Check size of the target: ", 3, action.getTargets().size());
		
	}
	
}
