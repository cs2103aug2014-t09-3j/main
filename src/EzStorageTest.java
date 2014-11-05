import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Khanh
 * @author A0113922N
 */
public class EzStorageTest {
	private EzTask createTask(String command){
		EzTask task = EzParser.extractInfo(command, null).getResults().get(0);
		return task;
	}
	
	/* This is an equivalence partition test */
	@Test
	public void testGetDoneTask() {
		EzStorage storage = new EzStorage();
		storage.addTaskWithNewId(createTask("add \"task 0\" on " + getDateFromToday(0)));
		storage.addTaskWithNewId(createTask("add \"task 1\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 2\""));
		storage.addTaskWithNewId(createTask("add \"task 3\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 4\""));
		storage.addTaskWithNewId(createTask("add \"task 5\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 6\" on " + getDateFromToday(2)));
		storage.addTaskWithNewId(createTask("add \"task 7\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 8\""));
		storage.addTaskWithNewId(createTask("add \"task 9\" on " + getDateFromToday(2)));
		
		storage.getTask(1).setDone(true);
		storage.getTask(2).setDone(true);
		storage.getTask(3).setDone(true);
		storage.getTask(5).setDone(true);
		storage.getTask(7).setDone(true);
		storage.getTask(8).setDone(true);
		
		ArrayList<EzTask> list = storage.getDoneTasks();
		assertEquals("check number of task: ", 6, list.size()); 
		ArrayList<EzTask> list2 = storage.getUndoneTasks();
		assertEquals("check number of task: ", 4, list2.size());
	}
	
	@Test
	public void testGetUndoneTask() {
		EzStorage storage = new EzStorage();
		storage.addTaskWithNewId(createTask("add \"task 0\" on " + getDateFromToday(0)));
		storage.addTaskWithNewId(createTask("add \"task 1\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 2\""));
		storage.addTaskWithNewId(createTask("add \"task 3\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 4\""));
		storage.addTaskWithNewId(createTask("add \"task 5\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 6\" on " + getDateFromToday(2)));
		storage.addTaskWithNewId(createTask("add \"task 7\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 8\""));
		storage.addTaskWithNewId(createTask("add \"task 9\" on " + getDateFromToday(2)));
		
		storage.getTask(1).setDone(true);
		storage.getTask(2).setDone(true);
		storage.getTask(3).setDone(true);
		storage.getTask(5).setDone(true);
		storage.getTask(7).setDone(true);
		storage.getTask(8).setDone(true);
		
		ArrayList<EzTask> list = storage.getUndoneTasks();
		assertEquals("check number of task: ", 4, list.size()); 
	}
	
	@Test
	public void testGetNoDateTask() {
		EzStorage storage = new EzStorage();
		storage.addTaskWithNewId(createTask("add \"task 0\" on " + getDateFromToday(0)));
		storage.addTaskWithNewId(createTask("add \"task 1\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 2\""));
		storage.addTaskWithNewId(createTask("add \"task 3\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 4\""));
		storage.addTaskWithNewId(createTask("add \"task 5\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 6\" on " + getDateFromToday(2)));
		storage.addTaskWithNewId(createTask("add \"task 7\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 8\""));
		storage.addTaskWithNewId(createTask("add \"task 9\" on " + getDateFromToday(2)));
		
		ArrayList<EzTask> list = storage.getNoDateTasks();
		assertEquals("check number of task: ", 3, list.size()); 
	}
	
	
	@Test
	public void testGetComingTask() {
		EzStorage storage = new EzStorage();
		storage.addTaskWithNewId(createTask("add \"task 0\" on " + getDateFromToday(0)));
		storage.addTaskWithNewId(createTask("add \"task 1\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 2\""));
		storage.addTaskWithNewId(createTask("add \"task 3\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 4\""));
		storage.addTaskWithNewId(createTask("add \"task 5\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 6\" on " + getDateFromToday(2)));
		storage.addTaskWithNewId(createTask("add \"task 7\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 8\""));
		storage.addTaskWithNewId(createTask("add \"task 9\" on " + getDateFromToday(2)));
		
		ArrayList<EzTask> list = storage.getComingTasks();
		assertEquals("check number of task: ", 5, list.size()); 
	}
	
	@Test
	public void testgetOverdueTask() {
		EzStorage storage = new EzStorage();
		storage.addTaskWithNewId(createTask("add \"task 0\" on " + getDateFromToday(0)));
		storage.addTaskWithNewId(createTask("add \"task 1\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 2\""));
		storage.addTaskWithNewId(createTask("add \"task 3\" on " + getDateFromToday(1)));
		storage.addTaskWithNewId(createTask("add \"task 4\""));
		storage.addTaskWithNewId(createTask("add \"task 5\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 6\" on " + getDateFromToday(2)));
		storage.addTaskWithNewId(createTask("add \"task 7\" on " + getDateFromToday(-1)));
		storage.addTaskWithNewId(createTask("add \"task 8\""));
		storage.addTaskWithNewId(createTask("add \"task 9\" on " + getDateFromToday(2)));
		
		ArrayList<EzTask> list = storage.getOverdueTasks();
		assertEquals("check number of task: ", 2, list.size()); 
	}

	private String getDateFromToday(int step){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, step);
		return "" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) 
				+ "/" + String.valueOf(cal.get(Calendar.MONTH)+1)
				+ "/" + String.valueOf(cal.get(Calendar.YEAR));
	}
	
	@Test
	public void testAddTask() {
		EzStorage storage = new EzStorage();
		EzTask task;
		
		checkId(storage.addTaskWithNewId(new EzTask("task 0")), 1);
		checkId(storage.addTaskWithNewId(new EzTask("task 1")), 2);
		checkId(storage.addTaskWithNewId(new EzTask("task 2")), 3);
		
		task = new EzTask("task 5");
		task.setId(5);
		checkId(storage.addTask(task), 5);
		
		task = new EzTask("task 4");
		task.setId(4);
		checkId(storage.addTask(task), 4);
		
		checkId(storage.addTaskWithNewId(new EzTask("task 6")), 6);
		
		task = new EzTask("task 7");
		task.setId(7);
		checkId(storage.addTask(task), 7);
		
		checkId(storage.addTaskWithNewId(new EzTask("task 8")), 8);
	}
	
	/* This is an equivalence partition test */
	@Test
	public void testGetByDate() {
		EzStorage storage = new EzStorage();
		
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 0\" on 26/7/2014", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 1\" on 26/7/2014 5h30", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 2\" on 27/7/2014 from 5h30 to 22h", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 3\" on 26/7/2014 5h30", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 4\" on 27/7/2014 from 5h30 to 22h", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 5\"", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 6\" on 25/7/2014", storage).getResults().get(0));
		assertEquals("Check size of storage: ", 7, storage.getSize());
		ArrayList<EzTask> list = storage.getTasksByDate((new GregorianCalendar(2014,Calendar.JULY,26,0,0)).getTime());
		assertTrue("Check list is not null: ", list!=null);
		assertEquals("Check size of list: ", 3, list.size());
	}
	
	@Test
	public void testGetByKeywords() {
		EzStorage storage = new EzStorage();
		
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 0 ab bc\" on 26/7/2014", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 1 ab ca\" on 26/7/2014 5h30", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 2 ee ab\" on 27/7/2014 from 5h30 to 22h", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 3 ac ee\" on 26/7/2014 5h30", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 4 bf aa\" on 27/7/2014 from 5h30 to 22h", storage).getResults().get(0));
		
		assertEquals("Check size of storage: ", 5, storage.getSize());
		
		ArrayList<String> listOfKeywords = new ArrayList<String>();
		listOfKeywords.add("ab");
		listOfKeywords.add("bc");
		ArrayList<EzTask> list = storage.getTasksByKeywords(listOfKeywords);
		assertTrue("Check list is not null: ", list!=null);
		assertEquals("Check size of list: ", 3, list.size());
	}
	
	@Test
	public void test() {
		EzStorage storage = new EzStorage();
		
		checkId(storage.addTaskWithNewId(new EzTask("go shopping","at Clementi",3)), 1);
		checkId(storage.addTaskWithNewId(new EzTask("do homework",2)), 2);
		checkId(storage.addTaskWithNewId(new EzTask("do EE2021 Tut",5)),3);
		/**
		 * now the list is:
		 * 1. "go shopping" "at Clementi" 3
		 * 2. "do homework" 2
		 * 3. "do EE2021 Tut" 5
		 */
	
		assertEquals("check size: ", 3, storage.getSize());
		
		ArrayList<EzTask> list = new ArrayList<EzTask>();
		
		EzTask tmp = new EzTask(storage.getTask(2));
		assertEquals("check title: ", "do homework", tmp.getTitle());
		
		tmp.setTitle("do CS2103T");
		list.add(tmp);
		assertEquals("check the number of tasks affected: ", 1, storage.updateTask(list));
		/**
		 * now the list is:
		 * 1. "go shopping" "at Clementi" 3
		 * 2. "do CS2103T" 2
		 * 3. "do EE2021 Tut" 5
		 */

		assertEquals("check title: ", "do CS2103T", storage.getTask(2).getTitle());
		
		list.clear();
		tmp = new EzTask(storage.getTask(2));
		list.add(tmp);
		
		assertEquals("check the number of tasks affected: ", 1, storage.deleteTask(list));
		
		/**
		 * now the list is:
		 * 1. "go shopping" "at Clementi" 3
		 * 3. "do EE2021 Tut" 5
		 */
		
		checkId(storage.addTaskWithNewId(new EzTask("do CS2101 Tut",7)),4);
		
		/**
		 * now the list is:
		 * 1. "go shopping" "at Clementi" 3
		 * 3. "do EE2021 Tut" 5
		 * 4. "do CS2101 Tut" 7
		 */
		
	
		ArrayList<String> listWords = new ArrayList<String>();
		listWords.add("do");
		
		
		checkSearchByKeywords(storage, listWords,2);

		listWords.clear();
		listWords.add("Tut");
		checkSearchByKeywords(storage, listWords,2);
		listWords.add("go");
		checkSearchByKeywords(storage, listWords,3);
		
		assertEquals("check not found:",null,storage.getTask(2));
		
		tmp = new EzTask(storage.getTask(4));
		assertEquals("check title: ", "do CS2101 Tut", tmp.getTitle());
		
		
		tmp.setTitle("do CS2103 Tut");
		list.add(tmp);
	
		assertEquals("check the number of tasks affected: ", 1, storage.updateTask(list));
		/**
		 * now the list is:
		 * 1. "go shopping" "at Clementi" 3
		 * 3. "do EE2021 Tut" 5
		 * 4. "do CS2103 Tut" 7
		 */
		
		list.clear();
		tmp = new EzTask(storage.getTask(3));
		list.add(tmp);
		assertEquals("check the number of tasks affected: ", 1, storage.deleteTask(list));
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 2. "do EE2021 Tut" 5
		 */
		
		
		EzTask task = new EzTask("do something", 4);
		task.setId(6);
		
		checkId(storage.addTask(new EzTask(task)), 6);
		checkNumTask(storage, 7);
		
		checkId(storage.addTaskWithNewId(new EzTask("do EE2021 tut", 5)), 7);
		
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 *2. "do EE2021 Tut" 5
		 *6. "do something" 4
		 *7. "do EE2021 tut" 5
		 */
		
		
		task = new EzTask("do something again", 4);
		task.setId(4);
		checkId(storage.addTask(task), 4);
		checkNumTask(storage, 8);
		
		task = new EzTask("do something again", 4);
		task.setId(5);
		checkId(storage.addTask(task), 5);
		checkNumTask(storage, 8);
		
		checkId(storage.addTaskWithNewId(new EzTask("do EE2021 tut", 5)), 8);
		
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 *2. "do EE2021 Tut" 5
		 *6. "do something" 4
		 *7. "do EE2021 tut" 5
		 *4. "do something again" 4
		 *5. "do something again" 4
		 *8. "do EE2021 tut" 5
		 */
		
		EzStorage storage2 = new EzStorage();
		
		
		task = new EzTask("do something", 4);
		task.setId(6);
		checkId(storage2.addTask(task), 6);
		checkNumTask(storage2, 7);
		
	}


	/**
	 * @param task0
	 */
	private void checkId(EzTask task0, int expectedId) {
		assertEquals("check id:", expectedId, task0.getId());
	}


	/**
	 * @param storage
	 * @param listWords
	 */
	private void checkSearchByKeywords(EzStorage storage,
			ArrayList<String> listWords, int expectedNumber) {
		assertEquals("check search by keyword: ", expectedNumber, storage.getTasksByKeywords(listWords).size());
	}

	/** 
	 * Prints the task list
	 * @param storage
	 */
	public void printTaskList(EzStorage storage){
		storage.printList();
	}

	private void checkNumTask(EzStorage storage, int expectedNumber){
		assertEquals("checkNumTasks: " , expectedNumber, storage.getLargestId());
	}
}
