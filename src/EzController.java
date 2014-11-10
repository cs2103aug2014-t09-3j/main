import java.io.IOException;
import java.util.ArrayList;

//@author A0112220J
public class EzController {

	private static final int MAX_SIZE = 20;

	private static EzStorage storage = new EzStorage();
	private static EzAction deleteAction = null;
	private static ArrayList<EzAction> history = new ArrayList<EzAction>();
	private static String prevTab;
	private static int pos = -1;
	private static boolean confirmation = false;
	private static boolean testing = false;

	/**
	 * This method takes in raw user input and pass it to the parser to generate an userAction object
	 * a feedback is returned after execution
	 * @param userCommand 
	 * @return feedback of the operation
	 */
	public static String execute(String userCommand){
		EzAction userAction = EzParser.extractInfo(userCommand, storage);
		determineUserAction(userAction);
		return userAction.getFeedback();
	}

	/**
	 * This method determine the intention of the user through userAction object
	 * @param userAction
	 */
	public static void determineUserAction(EzAction userAction) {
		switch(userAction.getAction()) {
		case ADD:
			addTask(userAction);
			break;

		case UPDATE:
			updateTask(userAction);
			break;

		case DELETE:
			deleteTask(userAction);
			break;

		case Y:
			confirmDelete(userAction);
			break;

		case N:
			abortDelete(userAction);
			break;

		case DONE:
			updateTask(userAction);
			break;

		case UNDONE:
			updateTask(userAction);
			break;

		case SORT:
			sortTask(userAction, EzGUI.getCurrentTab());
			break;

		case REMOVE:
			updateTask(userAction);
			break;

		case PAGE:
			navigatePage(userAction);
			break;

		case UNDO:
			undoAction(userAction);
			break;

		case REDO:
			redoAction(userAction);
			break;

		case SHOW:
			showTask(userAction);
			break;

		default:
			break;
		}
	}

	
	/**
	 * This method adds a task to the list of task in storage
	 * @param userAction
	 */
	private static void addTask(EzAction userAction) {
		if (!confirmation){
			EzTask task = userAction.getResults().get(0);
			checkPos();
			addHistory(userAction);
			String currTab = EzGUI.getCurrentTab();
			ArrayList<EzTask> listBeforeAdd = EzGUI.getTaskListOfTheTab(currTab);
			int sizeOfList;
			if(listBeforeAdd == null) {
				sizeOfList = 0;
			}
			else {
				sizeOfList = listBeforeAdd.size();
			}
			storage.addTaskWithNewId(task);
			if(!testing) {
				try {
					EzDataManage.saveToFile(storage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(currTab != null && !currTab.equalsIgnoreCase("help")) {
					int newSizeOfList = EzGUI.getTaskListOfTheTab(currTab).size();
					if(sizeOfList != newSizeOfList) {
						ArrayList<EzTask> list = EzGUI.getTaskListOfTheTab(currTab);
						EzGUI.showContent(currTab, list, task);
					}
					else {
						ArrayList<EzTask> list = EzGUI.getTaskListOfTheTab(currTab);
						EzGUI.showContent(currTab, list, EzGUI.getPage());
					}
				}
				else {
					EzGUI.refreshButton();
				}
			}
		}
	}

	/**
	 * This method update a task or a list of tasks to their new attributes
	 * @param userAction
	 */
	private static void updateTask(EzAction userAction) {
		if (!confirmation){
			storage.updateTask(userAction.getResults());
			checkPos();
			addHistory(userAction);
			if(!testing) {
				try {
					EzDataManage.saveToFile(storage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String currTab = EzGUI.getCurrentTab();
				if(currTab != null && !currTab.equalsIgnoreCase("help")) {
					ArrayList<EzTask> list = EzGUI.getTaskListOfTheTab(currTab);
					EzGUI.showContent(currTab, list, EzGUI.getPage());
				}
				else {
					EzGUI.refreshButton();
				}
			}
		}
	}

	/**
	 * This method prepares the deletion and wait for user's confirmation
	 * @param userAction
	 */
	private static void deleteTask(EzAction userAction) {
		if (!confirmation){
			deleteAction = userAction;
			ArrayList<EzTask> toBeDeleted = userAction.getTargets();
			confirmation = true;
			assert(toBeDeleted.size() >= 0);
			if(!testing) {
				prevTab = EzGUI.getCurrentTab();
				EzGUI.unhighlightButton();
				if(toBeDeleted.size() == 1) {
					EzGUI.showContent("Are you sure to delete this task? (Y/N)", toBeDeleted);
				}
				else if(toBeDeleted.size() > 1) {
					EzGUI.showContent("Are you sure to delete these tasks? (Y/N)", toBeDeleted);
				}
			}
		}
	}

	/**
	 * This method proceed the deletion if the user enter a Y
	 * @param userAction
	 */
	private static void confirmDelete(EzAction userAction) {
		if (confirmation){
			checkPos();
			assert(deleteAction != null);
			storage.deleteTask(deleteAction.getTargets());
			addHistory(deleteAction);
			if(!testing) {
				try {
					EzDataManage.saveToFile(storage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(prevTab != null && !prevTab.equalsIgnoreCase("help")) {
					ArrayList<EzTask> list = EzGUI.getTaskListOfTheTab(prevTab);
					EzGUI.showContent(prevTab, list, EzGUI.getPage());
					EzGUI.highlightButton(prevTab);
				}
				else {
					EzGUI.refreshButton();
				}
			}
			userAction.setFeedback("Deleted successfully");
			deleteAction = null;
			confirmation = false;
		}
	}

	/** 
	 * This method abort the deletion if the user enter a N
	 * @param userAction
	 */
	private static void abortDelete(EzAction userAction) {
		if (confirmation){
			if(!testing) {
				if(prevTab != null && !prevTab.equalsIgnoreCase("help")) {
					ArrayList<EzTask> list = EzGUI.getTaskListOfTheTab(prevTab);
					EzGUI.showContent(prevTab, list, EzGUI.getPage());
					EzGUI.highlightButton(prevTab);
				}
				else {
					EzGUI.refreshButton();
				}
			}
			userAction.setFeedback("Action cancelled");
			deleteAction = null;
			confirmation = false;
		}
	}

	/**
	 * This method calls the corresponding sorting method according to user's input
	 * @param userAction
	 * @param currTab
	 */
	private static void sortTask(EzAction userAction, String currTab) {
		if(!confirmation) {
			switch(userAction.getTypeSort()) {
			case ID:
				EzGUI.showContent(currTab, EzSort.sortById(EzGUI.getTasksOnScreen()));
				break;

			case TITLE:
				EzGUI.showContent(currTab, EzSort.sortByTitle(EzGUI.getTasksOnScreen()));
				break;

			case VENUE:
				EzGUI.showContent(currTab, EzSort.sortByVenue(EzGUI.getTasksOnScreen()));
				break;

			case DATE:
				EzGUI.showContent(currTab, EzSort.sortByDate(EzGUI.getTasksOnScreen()));
				break;

			case PRIORITY:
				EzGUI.showContent(currTab, EzSort.sortByPriority(EzGUI.getTasksOnScreen()));
				break;

			case DONE:
				EzGUI.showContent(currTab, EzSort.sortByDone(EzGUI.getTasksOnScreen()));
				break;

			default:
				break;
			}
		}
	}

	/**
	 * This method allows navigation between multiple pages
	 * @param userAction
	 */
	private static void navigatePage(EzAction userAction) {
		if (!confirmation){
			setPageFeedback(userAction);
			EzGUI.showPage(userAction.getPageNumber());
		}
		else if(confirmation && deleteAction != null) {
			setPageFeedback(userAction);
			EzGUI.showPage(userAction.getPageNumber());
			return;
		}
	}

	private static void setPageFeedback(EzAction userAction) {
		if(EzGUI.getMaxPage() < userAction.getPageNumber()) {
			userAction.setFeedback("Page " + EzGUI.getMaxPage());
		}
		else if(userAction.getPageNumber() <= 0) {
			userAction.setFeedback("Page " + "1");
		}
		else {
			userAction.setFeedback("Page " + userAction.getPageNumber());
		}
	}
	
	/**
	 * This method checks the validity of undo action before calling the actual method
	 * @param userAction
	 */
	private static void undoAction(EzAction userAction) {
		if (!confirmation){
			if(pos <= -1) {
				userAction.setFeedback("Nothing to undo!");
				return;
			}
			else {
				undoTask();
				if(!testing) {
					try {
						EzDataManage.saveToFile(storage);
					} catch (IOException e) {
						e.printStackTrace();
					}
					String currTab = EzGUI.getCurrentTab();
					if(currTab != null && !currTab.equalsIgnoreCase("help")) {
						ArrayList<EzTask> list = EzGUI.getTaskListOfTheTab(currTab);
						EzGUI.showContent(currTab, list, EzGUI.getPage());
					}
					else {
						EzGUI.refreshButton();
					}
				}
			}
		}
	}

	/**
	 * This method checks the validity of redo action before calling the actual method
	 * @param userAction
	 */
	private static void redoAction(EzAction userAction) {
		if (!confirmation){
			if(pos >= history.size()-1) {
				userAction.setFeedback("Nothing to redo!");
				return;
			}
			else {
				redoTask();
				if(!testing) {
					try {
						EzDataManage.saveToFile(storage);
					} catch (IOException e) {
						e.printStackTrace();
					}
					String currTab = EzGUI.getCurrentTab();
					if(currTab != null && !currTab.equalsIgnoreCase("help")) {
						ArrayList<EzTask> list = EzGUI.getTaskListOfTheTab(currTab);
						EzGUI.showContent(currTab, list, EzGUI.getPage());
					}
					else {
						EzGUI.refreshButton();
					}
				}
			}
		}
	}

	/**
	 * This method calls the GUI to display the list of tasks
	 * @param userAction
	 */
	private static void showTask(EzAction userAction) {
		if (!confirmation){
			ArrayList<EzTask> toBeShown = userAction.getTargets();
			EzGUI.highlightButton("All");
			EzGUI.showContent("Result", toBeShown);
		}
	}

	/*
	 * This method calls the corresponding method to remove the previous action of the user
	 */
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

	/*
	 * This method calls the corresponding method to perform the previous undo'ed action 
	 */
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

	/**
	 * This method adds user actions to a list of history for undo and redo operations
	 * @param userAction
	 */
	private static void addHistory(EzAction userAction) {
		if(history.size() == MAX_SIZE) {
			history.remove(0);
		}
		history.add(userAction);
		pos = history.size()-1;
	}

	/*
	 * This method ensures the latest version of history by checking the pointer on the history list
	 */
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

	/**
	 * This method sets the controller in testing mode to disable feedback function
	 * @param onTest
	 */
	public static void setTesting(boolean onTest) {
		testing = onTest;
	}

	public static ArrayList<EzAction> getHistory() {
		return history;
	}
}