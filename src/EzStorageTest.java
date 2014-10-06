import static org.junit.Assert.*;

import java.util.ArrayList;

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
	public void test() {
		EzStorage storage = new EzStorage();
		
		checkId(storage.addTaskWithNewId(new EzTask("go shopping","at Clementi",3)), 0);
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 */
		
		checkId(storage.addTaskWithNewId(new EzTask("do homework",2)), 1);
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 1. "do homework" 2
		 */
		
		
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
		assertEquals("checkNumTasks: " , expectedNumber, storage.numTasks);
	}
}
