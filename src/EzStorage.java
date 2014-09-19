import java.util.ArrayList;
import java.util.Date;

/**
 * 
 */

/**
 * @author Khanh
 *
 */
public class EzStorage {
	public EzTask addTask(EzTask task){
		return null;
	}
	
	public EzTask editTitle(EzTask task, String title){
		return null;
	}
	
	public EzTask editDate(EzTask task, Date date){
		return null;
	}
	
	public EzTask editPriority(EzTask task, int priority){
		return null;
	}
	
	public EzTask editVenue(EzTask task, String venue){
		return null;
	}
	
	public boolean undo(){
		return false;
	}
	
	public boolean redo(){
		return false;
	}
	
	public ArrayList<EzTask> markDone(ArrayList<EzTask> listOfTasks){ 
		return null;
	}
	
	public EzTask findTask(int id){
		return null;
	}
	
	public ArrayList<EzTask> deleteTask(ArrayList<EzTask> listOfTasks){
		return null;
	}
	
	public ArrayList<EzTask> getTasksByDate(Date date){
		return null;
	}
	
	public ArrayList<EzTask> getSortedTasksByPriority(){
		return null;
	}
	
	public ArrayList<EzTask> getTasksByKeyword(ArrayList<String> listOfKeywords){
		return null;
	}
	
	
}
