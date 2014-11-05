import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		EzController.execute("add \"doing automated testing\" on 4/11/2014");
		//task 2
		EzController.execute("add \"automated testing 2\" at \"utown\" at 10am");
		//task 3
		EzController.execute("add \"automated testing 3\" at 5pm 5/11/2014 ");
		//task 4
		EzController.execute("add \"do EE2021 tutorial\" from 5/11 to 6/11");
		//task 5
		EzController.execute("add \"search for keywords\" today ***" );
		
		
		//checking size of task list
		assertEquals ("size of task list = " , 5, getListSize());
		
		//checking task 1
		assertEquals ("task 1 title: " , "doing automated testing", getTaskTitle(1));
		assertEquals("task 1 is overdue: ", true, getTaskDateStatus(1));
		
	
		
		//checking task 2
		assertEquals("task 2 title: " , "automated testing 2", getTaskTitle(2));
		assertEquals("task 2 venue: " , "utown", getTaskVenue(2));
		assertEquals("task 2 is overdue: ", true, getTaskDateStatus(2));
		
		
		//checking task 3
		assertEquals("task 3 title: " , "automated testing 3", getTaskTitle(3));
		assertEquals("task 3 is overdue: ", false, getTaskDateStatus(3));
		assertEquals("task 3 is due today", true, getTaskTodayStatus(3));
		
		//checking task 4
		assertEquals("task 4 title: ", "do EE2021 tutorial", getTaskTitle(4));
		assertEquals("task 4 starts today", true, getTaskTodayStatus(4));
		
		//checking task 5
		assertEquals("task 5 title: ", "search for keywords", getTaskTitle(5));
		assertEquals("task 5 priority: ", 3, getTaskPriority(5));
		assertEquals("task 5 is due today:", true, getTaskTodayStatus(5));
		
		
		//marking task 3 as done
		EzController.execute("done 3");
		assertEquals("number of tasks done: ", 1, getNumDoneTasks());
		assertEquals("task 3 is done", true, getTaskDoneStatus(3));
		
		
		//undoing the previous action( marking task 3 as done)
		EzController.execute("undo");
		assertEquals("undo successful ", false, getTaskDoneStatus(3));
		
		//undoing the previous action(adding task 3)
		EzController.execute("undo");
		assertEquals("undo successful ", 4, getListSize());
		
		//redoing previous action(adding task 3)
		EzController.execute("redo");
		assertEquals("redo successful", 5, getListSize());
		
		//TODO please add the relevant part of your code. 
		//searching for keyword: "do"
		//EzController.execute("show all have \"do \" ");
		ArrayList <String> keywords = new ArrayList<String>();
		keywords.add("do");
		assertEquals("Number of task(s) found: ", 2, getKeywordsTasks(keywords));
		
		//searching for keywords: "do" and "automated"
		//EzController.execute("show all have \"do\" \"automated\" ");
		keywords.add("automated");
		assertEquals("Number of tasks(s) found: ", 4, getKeywordsTasks(keywords));
		
		//searching for keyword: "keywords"
		//EzController.execute("show all have \"keywords\" ");
		keywords.clear();
		keywords.add("keywords");
		assertEquals("Number of tasks(s) found: ", 1, getKeywordsTasks(keywords));
		
		//marking tasks 1, 2 and 3 as done
		EzController.execute("done from 1 to 3");
		assertEquals("task 1 is done", true, getTaskDoneStatus(1));
		assertEquals("task 2 is done", true, getTaskDoneStatus(2));
		assertEquals("task 3 is done", true, getTaskDoneStatus(3));
		
		//marking tasks 4 as done
		EzController.execute("done 4");
		assertEquals("task 4 is done", true, getTaskDoneStatus(4));
		
		//deleting task 1 
		EzController.execute("delete 1");
		EzController.execute("y");
		assertEquals("Number of tasks in list: ", 4, getListSize());
		
		//not deleting task 2
		EzController.execute("delete 2");
		EzController.execute("n");
		assertEquals("Number of tasks in list: ", 4, getListSize());
		
		//updating task 3 venue to utown
		EzController.execute("update 3 venue \"utown\" ");
		assertEquals("task 3 venue is: ", "utown", getTaskVenue(3));
		
		//updating task 3 title to "do something"
		EzController.execute("update 3 title \"do something\" ");
		assertEquals("task 3 titles is: ", "do something", getTaskTitle(3));
		
		
	
	}

	

	private int getKeywordsTasks(ArrayList <String> keywords) {
		return EzController.getStorage().getTasksByKeywords(keywords).size();
		
		
		
	}



	private int getTaskPriority(int i) {
		return EzController.getStorage().findTask(i).getPriority();
	}



	private Object getTaskTodayStatus(int i) {
		
		return EzController.getStorage().getTask(i).isToday();
	}



	private Object getTaskDateStatus(int i) {
		return EzController.getStorage().getTask(i).isPast();
	}



	private Object getTaskDoneStatus(int i) {
		return EzController.getStorage().getTask(i).isDone();
	}



	private int getNumDoneTasks() {
		int i = 0;
		for(EzTask task: EzController.getStorage().getListOfAllTasks())
			if(task.isDone())
				i++;
		return i;
	}



	private Object getListSize() {
		return EzController.getStorage().getListOfAllTasks().size();
	}



	private Object getTaskVenue(int i) {
		return EzController.getStorage().findTask(i).getVenue();
	}



	private Object getTaskTitle(int i) {
		return EzController.getStorage().findTask(i).getTitle();
		
	}
	



	

	

}
