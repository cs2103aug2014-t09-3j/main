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
	
	public static String execute(String userCommand){
		EzAction userAction = EzParser.extractInfo(userCommand, storage);
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
			try {
				saveToFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EzGUI.showContent("TitleAdd", storage.getSortedTasksById());
			break;
			
		case UPDATE:
			storage.updateTask(userAction.getResults());
			checkPos();
			addHistory(userAction);
			try {
				saveToFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EzGUI.showContent("TitleUpdate", storage.getSortedTasksById());
			break;
			
		case DELETE:
			ArrayList<EzTask> toBeDeleted = userAction.getTargets();
			checkPos();
			storage.deleteTask(toBeDeleted);
			addHistory(userAction);
			try {
				saveToFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EzGUI.showContent("TitleDelete", storage.getSortedTasksById());
			break;
			
		case DONE:
			storage.updateTask(userAction.getResults());
			checkPos();
			addHistory(userAction);
			try {
				saveToFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EzGUI.showContent("TitleDone", storage.getSortedTasksById());
			break;
			
		case UNDO:
			if(pos <= -1) {
				break;
			}
			else {
				undoTask();
				try {
					saveToFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				EzGUI.showContent("UNDO", storage.getSortedTasksById());
				
			}
			break;
			
		case REDO:
			if(pos >= history.size()-1) {
				return;
			}
			else {
				redoTask();
				try {
					saveToFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				EzGUI.showContent("REDO", storage.getSortedTasksById());
			}
			break;
			
		case SHOW:
			ArrayList<EzTask> toBeShown = userAction.getTargets();
			EzGUI.showContent("Display", toBeShown);
			break;
			
		case HELP:
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
	
	public static void saveToFile() throws IOException {
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
		
	}
	
	public void refresh(){
		
	}
}
