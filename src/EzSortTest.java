import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class EzSortTest {

	@Test
	public void testSortByVenue() {
		EzStorage storage = new EzStorage();
		
		storage.addTask(createTask(4,"add \"task4\" at \"venue4\""));
		storage.addTask(createTask(5,"add \"task5\" at \"venue5\""));
		storage.addTask(createTask(7,"add \"task7\" at \"venue7\""));
		storage.addTask(createTask(6,"add \"task6\" at \"venue6\""));
		storage.addTask(createTask(1,"add \"task1\" at \"venue1\""));
		storage.addTask(createTask(2,"add \"task2\" at \"venue2\""));
		storage.addTask(createTask(3,"add \"task3\" at \"venue3\""));
		
		ArrayList<EzTask> result = EzSort.sortByVenue(storage.getListOfAllTasks());
		assertEquals("check id: ", 1, result.get(0).getId());
		assertEquals("check id: ", 2, result.get(1).getId());
		assertEquals("check id: ", 3, result.get(2).getId());
		assertEquals("check id: ", 4, result.get(3).getId());
		assertEquals("check id: ", 5, result.get(4).getId());
		assertEquals("check id: ", 6, result.get(5).getId());
		assertEquals("check id: ", 7, result.get(6).getId());
		
	}

	@Test
	public void testSortByPriority() {
		EzStorage storage = new EzStorage();
		
		storage.addTask(createTask(4,"add \"task4\"",true));
		storage.addTask(createTask(5,"add \"task5\" **",false));
		storage.addTask(createTask(7,"add \"task7\" ***",false));
		storage.addTask(createTask(6,"add \"task6\" *",true));
		storage.addTask(createTask(1,"add \"task1\" *",false));
		storage.addTask(createTask(2,"add \"task2\" **",true));
		storage.addTask(createTask(3,"add \"task3\"",true));
		
		ArrayList<EzTask> result = EzSort.sortByPriority(storage.getListOfAllTasks());
		assertEquals("check priority: ", 3, result.get(0).getPriority());
		assertEquals("check priority: ", 2, result.get(1).getPriority());
		assertEquals("check priority: ", 2, result.get(2).getPriority());
		assertEquals("check priority: ", 1, result.get(3).getPriority());
		assertEquals("check priority: ", 1, result.get(4).getPriority());
		assertEquals("check priority: ", 0, result.get(5).getPriority());
		assertEquals("check priority: ", 0, result.get(6).getPriority());
		
	}

	@Test
	public void testSortByDone() {
		EzStorage storage = new EzStorage();
		
		storage.addTask(createTask(4,"add \"task4\"",true));
		storage.addTask(createTask(5,"add \"task5\"",false));
		storage.addTask(createTask(7,"add \"task7\"",false));
		storage.addTask(createTask(6,"add \"task6\"",true));
		storage.addTask(createTask(1,"add \"task1\"",false));
		storage.addTask(createTask(2,"add \"task2\"",true));
		storage.addTask(createTask(3,"add \"task3\"",true));
		
		ArrayList<EzTask> result = EzSort.sortByDone(storage.getListOfAllTasks());
		assertEquals("check done: ", false, result.get(0).isDone());
		assertEquals("check done: ", false, result.get(1).isDone());
		assertEquals("check done: ", false, result.get(2).isDone());
		assertEquals("check done: ", true, result.get(3).isDone());
		assertEquals("check done: ", true, result.get(4).isDone());
		assertEquals("check done: ", true, result.get(5).isDone());
		assertEquals("check done: ", true, result.get(6).isDone());
	}

	@Test
	public void testSortByTitle() {
		EzStorage storage = new EzStorage();
		
		storage.addTask(createTask(4,"add \"task4\""));
		storage.addTask(createTask(5,"add \"task5\""));
		storage.addTask(createTask(7,"add \"task7\""));
		storage.addTask(createTask(6,"add \"task6\""));
		storage.addTask(createTask(1,"add \"task1\""));
		storage.addTask(createTask(2,"add \"task2\""));
		storage.addTask(createTask(3,"add \"task3\""));
		
		ArrayList<EzTask> result = EzSort.sortByTitle(storage.getListOfAllTasks());
		assertEquals("check id: ", 1, result.get(0).getId());
		assertEquals("check id: ", 2, result.get(1).getId());
		assertEquals("check id: ", 3, result.get(2).getId());
		assertEquals("check id: ", 4, result.get(3).getId());
		assertEquals("check id: ", 5, result.get(4).getId());
		assertEquals("check id: ", 6, result.get(5).getId());
		assertEquals("check id: ", 7, result.get(6).getId());
	}

	@Test
	public void testSortByDate() {
		EzStorage storage = new EzStorage();
		
		storage.addTask(createTask(4,"add \"task4\" on 14/10/2014"));
		storage.addTask(createTask(5,"add \"task5\" on 10/10/2014"));
		storage.addTask(createTask(7,"add \"task7\" on 13/10/2014"));
		storage.addTask(createTask(6,"add \"task6\" on 16/10/2014"));
		storage.addTask(createTask(1,"add \"task1\""));
		storage.addTask(createTask(2,"add \"task2\" on 12/10/2014"));
		storage.addTask(createTask(3,"add \"task3\" on 18/10/2014"));
		
		ArrayList<EzTask> result = EzSort.sortByDate(storage.getListOfAllTasks());
		assertEquals("check id: ", 5, result.get(0).getId());
		assertEquals("check id: ", 2, result.get(1).getId());
		assertEquals("check id: ", 7, result.get(2).getId());
		assertEquals("check id: ", 4, result.get(3).getId());
		assertEquals("check id: ", 6, result.get(4).getId());
		assertEquals("check id: ", 3, result.get(5).getId());
		assertEquals("check id: ", 1, result.get(6).getId());
	}

	@Test
	public void testSortById() {
		EzStorage storage = new EzStorage();
		
		storage.addTask(createTask(4,"add \"task4\""));
		storage.addTask(createTask(5,"add \"task5\""));
		storage.addTask(createTask(7,"add \"task7\""));
		storage.addTask(createTask(6,"add \"task6\""));
		storage.addTask(createTask(1,"add \"task1\""));
		storage.addTask(createTask(2,"add \"task2\""));
		storage.addTask(createTask(3,"add \"task3\""));
		
		ArrayList<EzTask> result = EzSort.sortById(storage.getListOfAllTasks());
		assertEquals("check id: ", 1, result.get(0).getId());
		assertEquals("check id: ", 2, result.get(1).getId());
		assertEquals("check id: ", 3, result.get(2).getId());
		assertEquals("check id: ", 4, result.get(3).getId());
		assertEquals("check id: ", 5, result.get(4).getId());
		assertEquals("check id: ", 6, result.get(5).getId());
		assertEquals("check id: ", 7, result.get(6).getId());
	}

	private EzTask createTask(int id, String command){
		EzTask task = EzParser.extractInfo(command, null).getResults().get(0);
		task.setId(id);
		return task;
	}
	
	private EzTask createTask(int id, String command, boolean done){
		EzTask task = EzParser.extractInfo(command, null).getResults().get(0);
		task.setId(id);
		task.setDone(done);
		return task;
	}
	
}
