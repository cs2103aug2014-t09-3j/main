import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Khanh
 * 
 */
public class EzStorageTest {
	@Test
	public void testAddTask() {
		EzStorage storage = new EzStorage();
		EzTask task;
		
		checkId(storage.addTaskWithNewId(new EzTask("task 0")), 0);
		checkId(storage.addTaskWithNewId(new EzTask("task 1")), 1);
		checkId(storage.addTaskWithNewId(new EzTask("task 2")), 2);
		
		task = new EzTask("task 5");
		task.setId(5);
		checkId(storage.addTask(task), 5);
		
		task = new EzTask("task 4");
		task.setId(4);
		checkId(storage.addTask(task), 4);
		
		checkId(storage.addTaskWithNewId(new EzTask("task 6")), 6);
	}
	
	@Test
	public void testGetByDate() {
		EzStorage storage = new EzStorage();
		
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 0\" on 26/7/2014", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 1\" on 26/7/2014 5h30", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 2\" on 27/7/2014 from 5h30 to 22h", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 3\" on 26/7/2014 5h30", storage).getResults().get(0));
		storage.addTaskWithNewId(EzParser.extractInfo("add \"task 4\" on 27/7/2014 from 5h30 to 22h", storage).getResults().get(0));
		
		assertEquals("Check size of storage: ", 5, storage.getSize());
		ArrayList<EzTask> list = storage.getTasksByDate((new GregorianCalendar(2014,Calendar.JULY,26,0,0)).getTime());
		assertTrue("Check list is not null: ", list!=null);
		assertEquals("Check size of list: ", 3, list.size());
	}
	
	@Test
	public void testSortedTasksByID() {
		EzStorage storage = new EzStorage();
		EzTask task = EzParser.extractInfo("add \"task 0\" on 26/7/2014", storage).getResults().get(0);
		task.setId(6);
		storage.addTask(task);
		
		task = EzParser.extractInfo("add \"task 1\" on 26/7/2014 5h30", storage).getResults().get(0);
		task.setId(3);
		storage.addTask(task);
		
		task = EzParser.extractInfo("add \"task 2\" on 27/7/2014 from 5h30 to 22h", storage).getResults().get(0);
		task.setId(0);
		storage.addTask(task);
		
		task = EzParser.extractInfo("add \"task 3\" on 26/7/2014 5h30", storage).getResults().get(0);
		task.setId(2);
		storage.addTask(task);
		
		task = EzParser.extractInfo("add \"task 4\" on 27/7/2014 from 5h30 to 22h", storage).getResults().get(0);
		task.setId(1);
		storage.addTask(task);
		
		assertEquals("Check size of storage: ", 5, storage.getSize());
		ArrayList<EzTask> list = storage.getSortedTasksById();
		assertTrue("Check list is not null: ", list!=null);
		assertEquals("Check size of list: ", 5, list.size());
		assertEquals("Check id: ", 0, list.get(0).getId());
		assertEquals("Check id: ", 1, list.get(1).getId());
		assertEquals("Check id: ", 2, list.get(2).getId());
		assertEquals("Check id: ", 3, list.get(3).getId());
		assertEquals("Check id: ", 6, list.get(4).getId());
	}
	
	@Test
	public void testSortedTasksByPriority() {
		EzStorage storage = new EzStorage();
		EzTask task = EzParser.extractInfo("add \"task 0\" on 26/7/2014 ***", storage).getResults().get(0);
		task.setId(6);
		storage.addTask(task);
		
		task = EzParser.extractInfo("add \"task 1\" on 26/7/2014 5h30 **", storage).getResults().get(0);
		task.setId(3);
		storage.addTask(task);
		
		task = EzParser.extractInfo("add \"task 2\" on 27/7/2014 from 5h30 to 22h *", storage).getResults().get(0);
		task.setId(0);
		storage.addTask(task);
		
		task = EzParser.extractInfo("add \"task 3\" on 26/7/2014 5h30 ***", storage).getResults().get(0);
		task.setId(2);
		storage.addTask(task);
		
		task = EzParser.extractInfo("add \"task 4\" on 27/7/2014 from 5h30 to 22h", storage).getResults().get(0);
		task.setId(1);
		storage.addTask(task);
		
		assertEquals("Check size of storage: ", 5, storage.getSize());
		ArrayList<EzTask> list = storage.getSortedTasksByPriority();
		assertTrue("Check list is not null: ", list!=null);
		assertEquals("Check size of list: ", 5, list.size());
	
		assertEquals("Check id: ", 0, list.get(0).getPriority());
		assertEquals("Check id: ", 1, list.get(1).getPriority());
		assertEquals("Check id: ", 2, list.get(2).getPriority());
		assertEquals("Check id: ", 3, list.get(3).getPriority());
		//System.out.println(list.get(4).getTitle());
		//it seems this priority should be 3? there's no four asterisks
		assertEquals("Check id: ", 3, list.get(4).getPriority());
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
		
		checkId(storage.addTaskWithNewId(new EzTask("go shopping","at Clementi",3)), 0);
		checkId(storage.addTaskWithNewId(new EzTask("do homework",2)), 1);
		checkId(storage.addTaskWithNewId(new EzTask("do EE2021 Tut",5)),2);
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 1. "do homework" 2
		 * 2. "do EE2021 Tut" 5
		 */
	
		assertEquals("check size: ", 3, storage.getSize());
		
		ArrayList<EzTask> list = new ArrayList<EzTask>();
		
		EzTask tmp = new EzTask(storage.findTask(1));
		assertEquals("check title: ", "do homework", tmp.getTitle());
		
		tmp.setTitle("do CS2103T");
		list.add(tmp);
		assertEquals("check the number of tasks affected: ", 1, storage.updateTask(list));
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 1. "do CS2103T" 2
		 * 2. "do EE2021 Tut" 5
		 */

		assertEquals("check title: ", "do CS2103T", storage.findTask(1).getTitle());
		
		list.clear();
		tmp = new EzTask(storage.findTask(1));
		list.add(tmp);
		
		assertEquals("check the number of tasks affected: ", 1, storage.deleteTask(list));
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 2. "do EE2021 Tut" 5
		 */
		
		checkId(storage.addTaskWithNewId(new EzTask("do CS2101 Tut",7)),3);
		
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 2. "do EE2021 Tut" 5
		 * 3. "do CS2101 Tut" 7
		 */
		
	
		ArrayList<String> listWords = new ArrayList<String>();
		listWords.add("do");
	
		checkSearchByKeywords(storage, listWords,2);
		listWords.clear();
		listWords.add("Tut");
		checkSearchByKeywords(storage, listWords,2);
		listWords.add("go");
		checkSearchByKeywords(storage, listWords,3);
		
		assertEquals("check not found:",null,storage.findTask(1));
		
		tmp = new EzTask(storage.findTask(3));
		assertEquals("check title: ", "do CS2101 Tut", tmp.getTitle());
		
		tmp.setTitle("do CS2103 Tut");
		list.add(tmp);
	
		assertEquals("check the number of tasks affected: ", 1, storage.updateTask(list));
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 2. "do EE2021 Tut" 5
		 * 3. "do CS2103 Tut" 7
		 */
		
		list.clear();
		tmp = new EzTask(storage.findTask(3));
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
	private void printTaskList(EzStorage storage){
		storage.printList();
	}

	private void checkNumTask(EzStorage storage, int expectedNumber){
		assertEquals("checkNumTasks: " , expectedNumber, storage.largestId);
	}
}
