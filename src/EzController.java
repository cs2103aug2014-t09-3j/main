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
						EzGUI.showContent("All Tasks", EzSort.sortById(updatedList), EzGUI.getPage());
					}
				}
			}
			break;
			
		case SHOW:
			if (!confirmation){
				ArrayList<EzTask> toBeShown = userAction.getTargets();
				EzGUI.showContent("Result", toBeShown);
			}
			break;
			
		case HELP:
			if (!confirmation){
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
	
	/*public static void saveToFile() throws IOException {
		EzBinaryWriter writer = new EzBinaryWriter("external.ezt");
		
		ArrayList<EzTask> tempStorage = storage.getSortedTasksByPriority();
		writer.write(tempStorage.size(), 4);
		for(int i = 0; i < tempStorage.size(); i++) {
			int typeOfTask;
			int numAttribute;
			EzTask task = tempStorage.get(i);
			if(task.getStartTime() != tempStorage.get(i).getEndTime()) {
				typeOfTask = 2; // means start and endtime task
			}
			else {
				if(task.getStartTime() == null) {
					typeOfTask = 0; // floating task
				}
				else {
					typeOfTask = 1; // deadline task;
				}
			}
			writer.write(typeOfTask, 1);
			switch(typeOfTask) {
			case 0:
				numAttribute = 5;
				break;
			case 1:
				numAttribute = 6;
				break;
			default:
				numAttribute = 7;
				break;
			}
			writer.write(numAttribute, 1);
			
			//id
			writer.write(0, 1); // indicate ID
			writer.write(task.getId(),4);
			
			//title
			writer.write(1, 1); //indicate title
			writer.write(task.getTitle().length(), 2);
			writer.writeString(task.getTitle());
			
			//venue
			writer.write(2, 1);
			int lenVenue;
			if(task.getVenue() == null) {
				lenVenue = 0;
			}
			else {
				lenVenue = task.getVenue().length();
			}
			writer.write(lenVenue, 2);
			writer.writeString(task.getVenue());
			
			//priority
			writer.write(5, 1);
			writer.write(task.getPriority(), 1);
			
			//done
			writer.write(6, 1);
			int status;
			if(task.isDone()) {
				status = 1;
			}
			else {
				status = 0;
			}
			writer.write(status, 1);
			
			if(typeOfTask != 0) {
				writer.write(3, 1);
				writer.write(task.getStartTime().get(Calendar.YEAR),2);
				writer.write(task.getStartTime().get(Calendar.MONTH),1);
				writer.write(task.getStartTime().get(Calendar.DAY_OF_MONTH),1);
				writer.write(task.getStartTime().get(Calendar.HOUR_OF_DAY),1);
				writer.write(task.getStartTime().get(Calendar.MINUTE),1);
				
				if(typeOfTask == 2) {
					writer.write(4, 1);
					writer.write(task.getEndTime().get(Calendar.YEAR),2);
					writer.write(task.getEndTime().get(Calendar.MONTH),1);
					writer.write(task.getEndTime().get(Calendar.DAY_OF_MONTH),1);
					writer.write(task.getEndTime().get(Calendar.HOUR_OF_DAY),1);
					writer.write(task.getEndTime().get(Calendar.MINUTE),1);
				}
			}
		}
		writer.close();
	}
	
	public static void loadFromFile() throws IOException {
		try{
			EzBinaryReader rd = new EzBinaryReader("external.ezt");
			if (rd.available()>0){
				int numTask = rd.read(4);
				for (int i=0;i<numTask;i++){
					int typeOfTask = rd.read(1);
					int numAttribute = rd.read(1);
					EzTask temp = new EzTask();
					for(int j = 0; j < numAttribute; j++) {
						int typeAtt = rd.read(1);
						switch(typeAtt) {
						case 0:
							temp.setId(rd.read(4));
							break;
						case 1:
							int sizeOfTitle = rd.read(2);
							temp.setTitle(rd.readString(sizeOfTitle));
							break;
						case 2:
							int sizeOfVenue = rd.read(2);
							temp.setVenue(rd.readString(sizeOfVenue));
							break;
						case 3:
							temp.setStartTime(rd.read(2), rd.read(1), rd.read(1), rd.read(1), rd.read(1));
							break;
						case 4:
							temp.setEndTime(rd.read(2), rd.read(1), rd.read(1), rd.read(1), rd.read(1));
							break;
						case 5:
							temp.setPriority(rd.read(1));
							break;
						default:
							temp.setDone(rd.read(1) == 1);
							break;
						}
						if (typeOfTask == 1) {
							temp.setEndTimeAsStartTime();
						}
					}
					storage.addTask(temp);
				}
				EzGUI.showContent("Loaded from file", storage.getSortedTasksById());
				rd.close();
			}
		} 
		catch (IOException e)
		{
			EzBinaryWriter write = new EzBinaryWriter("external.ezt");
		}
		
	}*/
	
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
	
	public void refresh(){
		
	}
}
