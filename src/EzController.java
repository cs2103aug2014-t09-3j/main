import java.util.ArrayList;

/**
 * 
 *
 * @author Yu Shuen
 * @author Khanh (skeleton file)
 */
public class EzController {
	
	private static final int MAX_SIZE = 20;
	private static EzStorage storage = new EzStorage();
	private static ArrayList<EzAction> history = new ArrayList<EzAction>();
	private static int pos = -1;
	
	public static String execute(String userCommand){
		EzAction userAction = EzParser.extractInfo(userCommand, storage);
		/*EzAction tempAction = new EzAction();
		tempAction.setAction(TypeOfAction.ADD);
		tempAction.setTargets(null);
		ArrayList<EzTask> list = new ArrayList<EzTask>();
		EzTask task = new EzTask("do homework","pgp",4);
		task.setStartTime(2014, 9, 27,15,30);
		task.setEndTimeAsStartTime();
		list.add(task);
		tempAction.setResults(list);*/
		determineUserAction(userAction);
		return "";
	}

	public static void determineUserAction(EzAction userAction) {
		switch(userAction.getAction()) {
		case ADD:
			EzTask task = userAction.getResults().get(0);
			checkPos();
			addHistory(userAction);
			storage.addTaskWithNewId(task);
			EzGUI.showContent("TitleAdd", storage.getSortedTasksByPriority());
			break;
			
		case UPDATE:
			storage.updateTask(userAction.getResults());
			checkPos();
			addHistory(userAction);
			EzGUI.showContent("TitleUpdate", storage.getSortedTasksByPriority());
			break;
			
		case DELETE:
			ArrayList<EzTask> toBeDeleted = userAction.getTargets();
			checkPos();
			storage.deleteTask(toBeDeleted);
			addHistory(userAction);
			EzGUI.showContent("TitleDelete", storage.getSortedTasksByPriority());
			break;
			
		case DONE:
			storage.updateTask(userAction.getResults());
			checkPos();
			addHistory(userAction);
			EzGUI.showContent("TitleDone", storage.getSortedTasksByPriority());
			break;
			
		case UNDO:
			if(pos <= -1) {
				break;
			}
			else {
				undoTask();
				EzGUI.showContent("UNDO", storage.getSortedTasksByPriority());
				
			}
			break;
			
		case REDO:
			if(pos >= history.size()) {
				return;
			}
			else {
				redoTask();
				EzGUI.showContent("REDO", storage.getSortedTasksByPriority());
			}
			break;
			
		case SHOW:
			
			break;
			
		case HELP:
			break;
			
		default:
			break;
		}
	}

	private static void redoTask() {
		pos++;
		switch(history.get(pos).getAction()) {
		case ADD:
			EzTask reTask = history.get(pos).getResults().get(0);
			storage.addTask(reTask);
			break;
			
		case DELETE:
			ArrayList<EzTask> delete = history.get(pos).getTargets();
			storage.deleteTask(delete);
			break;
			
		case UPDATE:
			storage.updateTask(history.get(pos).getResults());
			break;
			
		case DONE:
			storage.updateTask(history.get(pos).getResults());
			break;
			
		default:
			break;
		}
	}

	private static void undoTask() {
		switch(history.get(pos).getAction()) {
		case ADD:
			storage.deleteTask(history.get(pos--).getResults());
			break;
		
		case DELETE:
			ArrayList<EzTask> deletedData = history.get(pos--).getTargets();
			for(int i = 0; i < deletedData.size(); i++) {
				storage.addTask(deletedData.get(i));
			}
			break;
		
		case UPDATE:
			storage.updateTask(history.get(pos--).getTargets());
			break;
		
		case DONE:
			storage.updateTask(history.get(pos--).getTargets());
			break;
			
		default:
			break;
		}
	}

	private static void addHistory(EzAction userAction) {
		if(history.size() == MAX_SIZE) {
			history.remove(0);
		}
		history.add(userAction);
		pos = history.size()-1;
	}
	
	private static void checkPos() {
		int sizeOfHistory = history.size();
		if(pos < sizeOfHistory-1) {
			for(int i = --sizeOfHistory; i > pos; i--) {
				history.remove(i);
			}
		}
	}
	
	public void refresh(){
		
	}
}
