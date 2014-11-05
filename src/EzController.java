import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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
	private static boolean confirmation = false;
	private static EzAction deleteAction = null;
	private static boolean testing = false;

	public static String execute(String userCommand){
		EzAction userAction = EzParser.extractInfo(userCommand, storage);
		determineUserAction(userAction);
		return userAction.getFeedback();
	}

	public static void determineUserAction(EzAction userAction) {
		switch(userAction.getAction()) {
		case ADD:
			if (!confirmation){
				EzTask task = userAction.getResults().get(0);
				checkPos();
				addHistory(userAction);
				storage.addTaskWithNewId(task);
				if(!testing) {
					try {
						EzDataManage.saveToFile(storage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ArrayList<EzTask> updatedList = storage.getListOfAllTasks();
					EzGUI.highlightButton("All");
					EzGUI.showContent("All Tasks", EzSort.sortById(updatedList), task);
				}
			}
			break;

		case UPDATE:
			if (!confirmation){
				storage.updateTask(userAction.getResults());
				checkPos();
				addHistory(userAction);
				if(!testing) {
					try {
						EzDataManage.saveToFile(storage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ArrayList<EzTask> updatedList = storage.getListOfAllTasks();
					EzGUI.highlightButton("All");
					EzGUI.showContent("All Tasks", EzSort.sortById(updatedList), userAction.getResults().get(0));
				}
			}
			break;

		case DELETE:
			if (!confirmation){
				deleteAction = userAction;
				ArrayList<EzTask> toBeDeleted = userAction.getTargets();
				confirmation = true;
				assert(toBeDeleted.size() >= 0);
				if(!testing) {
					if(toBeDeleted.size() == 1) {
						EzGUI.showContent("Are you sure to delete this task? (Y/N)", toBeDeleted);
					}
					else if(toBeDeleted.size() > 1) {
						EzGUI.showContent("Are you sure to delete these tasks? (Y/N)", toBeDeleted);
					}
				}
			}
			break;

		case Y:
			if (confirmation){
				checkPos();
				assert(deleteAction != null);
				storage.deleteTask(deleteAction.getTargets());
				addHistory(deleteAction);
				if(!testing) {
					try {
						EzDataManage.saveToFile(storage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ArrayList<EzTask> updatedList = storage.getListOfAllTasks();
					EzGUI.highlightButton("All");
					EzGUI.showContent("Tasks Deleted", EzSort.sortById(updatedList));
				}
				userAction.setFeedback("Deleted successfully");
				deleteAction = null;
				confirmation = false;
			}
			break;

		case N:
			if (confirmation){
				if(!testing) {
					ArrayList<EzTask> updatedList = storage.getListOfAllTasks();
					EzGUI.highlightButton("All");
					EzGUI.showContent("All Tasks", EzSort.sortById(updatedList));
				}
				userAction.setFeedback("Action cancelled");
				deleteAction = null;
				confirmation = false;
			}
			break;

		case DONE:
			if (!confirmation){
				storage.updateTask(userAction.getResults());
				checkPos();
				addHistory(userAction);
				if(!testing) {
					try {
						EzDataManage.saveToFile(storage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ArrayList<EzTask> updatedList = storage.getListOfAllTasks();
					EzGUI.highlightButton("All");
					EzGUI.showContent("All Tasks", EzSort.sortById(updatedList), userAction.getResults().get(0));
				}
			}
			break;

		case UNDONE:
			if (!confirmation){
				storage.updateTask(userAction.getResults());
				checkPos();
				addHistory(userAction);
				if(!testing) {
					try {
						EzDataManage.saveToFile(storage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ArrayList<EzTask> updatedList = storage.getListOfAllTasks();
					EzGUI.highlightButton("All");
					EzGUI.showContent("All Tasks", EzSort.sortById(updatedList), userAction.getResults().get(0));
				}
			}
			break;

		case SORT:
			if(!confirmation) {
				sortTask(userAction);
			}
			break;

		case REMOVE:
			if(!confirmation) {
				storage.updateTask(userAction.getResults());
				checkPos();
				addHistory(userAction);
				if(!testing) {
					try {
						EzDataManage.saveToFile(storage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ArrayList<EzTask> updatedList = storage.getListOfAllTasks();
					EzGUI.highlightButton("All");
					EzGUI.showContent("All Tasks", EzSort.sortById(updatedList), userAction.getResults().get(0));
				}
			}
			break;

		case PAGE:
			if (!confirmation){
				EzGUI.showPage(userAction.getPageNumber());
			}
			else if(confirmation && deleteAction != null) {
				EzGUI.showPage(userAction.getPageNumber());
				break;
			}
			break;

		case UNDO:
			if (!confirmation){
				if(pos <= -1) {
					return;
				}
				else {
					undoTask();
					if(!testing) {
						try {
							EzDataManage.saveToFile(storage);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ArrayList<EzTask> updatedList = storage.getListOfAllTasks();
						EzGUI.highlightButton("All");
						EzGUI.showContent("All Tasks", EzSort.sortById(updatedList), EzGUI.getPage());
					}
				}
			}
			break;

		case REDO:
			if (!confirmation){
				if(pos >= history.size()-1) {
					return;
				}
				else {
					redoTask();
					if(!testing) {
						try {
							EzDataManage.saveToFile(storage);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ArrayList<EzTask> updatedList = storage.getListOfAllTasks();
						EzGUI.highlightButton("All");
						EzGUI.showContent("All Tasks", EzSort.sortById(updatedList), EzGUI.getPage());
					}
				}
			}
			break;

		case SHOW:
			if (!confirmation){
				ArrayList<EzTask> toBeShown = userAction.getTargets();
				EzGUI.highlightButton("All");
				EzGUI.showContent("Result", toBeShown);
			}
			break;

		default:
			break;
		}
	}

	private static void sortTask(EzAction userAction) {
		// TODO Auto-generated method stub
		switch(userAction.getTypeSort()) {
		case ID:
			EzGUI.showContent("Tasks sorted by ID", EzSort.sortById(EzGUI.getTasksOnScreen()));
			break;

		case TITLE:
			EzGUI.showContent("Tasks sorted in Alphabethical order", EzSort.sortByTitle(EzGUI.getTasksOnScreen()));
			break;

		case VENUE:
			EzGUI.showContent("Tasks sorted by venue", EzSort.sortByVenue(EzGUI.getTasksOnScreen()));
			break;

		case DATE:
			EzGUI.showContent("Tasks sorted by date", EzSort.sortByDate(EzGUI.getTasksOnScreen()));
			break;

		case PRIORITY:
			EzGUI.showContent("Tasks sorted by priority", EzSort.sortByPriority(EzGUI.getTasksOnScreen()));
			break;

		case DONE:
			EzGUI.showContent("Tasks sorted by status", EzSort.sortByDone(EzGUI.getTasksOnScreen()));
			break;

		default:
			break;
		}
	}

	private static void redoTask() {
		switch(history.get(++pos).getAction()) {
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

		case UNDONE:
			storage.updateTask(history.get(pos).getResults());
			break;

		case REMOVE:
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

		case UNDONE:
			storage.updateTask(history.get(pos--).getTargets());
			break;

		case REMOVE:
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

	public static void loadFromFile() throws IOException {
		EzDataManage.loadFromFile(storage);
	}

	public static EzStorage getStorage() {
		return storage;
	}

	public static void setTesting(boolean onTest) {
		testing = onTest;
	}

	public static ArrayList<EzAction> getHistory() {
		return history;
	}
}
