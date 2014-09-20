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
		
		EzTask task0 = new EzTask("go shopping","at Clementi",3);
		task0.setStartTime(2014, 9, 25);
		task0.setEndTime(2014, 9, 25);
		storage.addTask(task0);
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 */
		checkId(task0,0);
		
		
		EzTask task1 = new EzTask("do homework",2);
		storage.addTask(task1);
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 1. "do homework" 2
		 */
		checkId(task1,1);
		
		
		EzTask task2 = new EzTask("do EE2021 Tut",5);
		storage.addTask(task2);
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 1. "do homework" 2
		 * 2. "do EE2021 Tut" 5
		 */
		checkId(task2,2);
		
		checkSize(storage,3);
		
		ArrayList<EzTask> list = new ArrayList<EzTask>();
		EzTask tmp = new EzTask(storage.findTask(1));
		checkTitle(tmp,"do homework");
		
		tmp.setTitle("do CS2103T");
		list.add(tmp);
		checkNumberTasksAffected(storage, list,1);
		/**
		 * now the list is:
		 * 0. "go shopping" "at Clementi" 3
		 * 1. "do CS2103T" 2
		 * 2. "do EE2021 Tut" 5
		 */
		
		checkTitle(storage.findTask(1),"do CS2103T");
		
		ArrayList<String> listWords = new ArrayList<String>();
		listWords.add("do");
		checkSearchByKeywords(storage, listWords,2);
		listWords.add("go");
		checkSearchByKeywords(storage, listWords,3);
		
		list.clear();
		list.add(storage.findTask(1));
		storage.deleteTask(list);
		checkSize(storage,2);
		assertEquals("check not found:",null,storage.findTask(1));
	}

	/**
	 * @param tmp
	 */
	private void checkTitle(EzTask tmp, String expectedTitle) {
		assertEquals("check title: ", expectedTitle, tmp.getTitle());
	}

	/**
	 * @param task0
	 */
	private void checkId(EzTask task0, int expectedId) {
		assertEquals("check id:", expectedId, task0.getId());
	}

	/**
	 * @param storage
	 * @param list
	 */
	private void checkNumberTasksAffected(EzStorage storage,
			ArrayList<EzTask> list, int expectedNumber) {
		assertEquals("check the number of tasks affected: ", expectedNumber, storage.updateTask(list));
	}

	/**
	 * @param storage
	 * @param listWords
	 */
	private void checkSearchByKeywords(EzStorage storage,
			ArrayList<String> listWords, int expectedNumber) {
		assertEquals("check search by keyword: ", expectedNumber, storage.getTasksByKeywords(listWords));
	}

	/**
	 * @param storage
	 */
	private void checkSize(EzStorage storage,int expectedSize) {
		assertEquals("check size:", expectedSize, storage.getSize());
	}

}
