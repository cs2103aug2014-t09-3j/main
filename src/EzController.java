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
		determineUserAction(userAction);
		return "";
	}

	public static void determineUserAction(EzAction userAction) {
		switch(userAction.getAction()) {
		case ADD:
			EzTask task = userAction.getResults().get(0);
			addHistory(userAction);
			storage.addTaskWithNewId(task);
			break;
			
		case UPDATE:
			storage.updateTask(userAction.getResults());
			addHistory(userAction);
			break;
			
		case DELETE:
			ArrayList<EzTask> toBeDeleted = userAction.getTargets();
			storage.deleteTask(toBeDeleted);
			addHistory(userAction);
			break;
			
		case DONE:
			storage.updateTask(userAction.getResults());
			addHistory(userAction);
			break;
			
		case UNDO:
			if(pos <= -1) {
				break;
			}
			else {
				undoTask();
			}
			break;
			
		case REDO:
			if(pos >= history.size()) {
				return;
			}
			else {
				redoTask();
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
		switch(history.get(pos).getAction()) {
		case ADD:
			EzTask reTask = history.get(pos++).getResults().get(0);
			storage.addTask(reTask);
			break;
			
		case DELETE:
			ArrayList<EzTask> delete = history.get(pos++).getTargets();
			storage.deleteTask(delete);
			break;
			
		case UPDATE:
			storage.updateTask(history.get(pos++).getResults());
			break;
			
		case DONE:
			storage.updateTask(history.get(pos++).getResults());
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
	
	public void refresh(){
		
	}
}
