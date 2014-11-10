import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
//@author A0112220J

public class EzDataManage {
	
	private static final String EXTERNAL_FILENAME = "external.ezt";
	
	private static final int TASKS_LIST_BYTES = 4;
	private static final int TYPE_OF_TASK_BYTES = 1;
	private static final int NUM_OF_ATTRIBUTES_BYTES = 1;
	
	/*
	 * Time bytes constants
	 */
	private static final int YEAR_BYTES = 2;
	private static final int MONTH_BYTES = 1;
	private static final int DAY_OF_MONTH_BYTES = 1;
	private static final int HOUR_OF_DAY_BYTES = 1;
	private static final int MINUTES_BYTES = 1;
	
	/*
	 * Task attributes bytes constants
	 */
	private static final int INDEX_BYTES = 1;
	private static final int ID_BYTES = 4;
	private static final int TITLE_BYTES = 2;
	private static final int VENUE_BYTES = 2;
	private static final int PRIORITY_BYTES = 1;
	private static final int STATUS_BYTES = 1;
	
	/*
	 * Number of attributes of tasks with different type of time
	 */
	private static final int FLOATING_NUM_OF_ATTRIBUTES = 5;
	private static final int DEADLINE_NUM_OF_ATTRIBUTES = 6;
	private static final int TIMED_NUM_OF_ATTRIBUTES = 7;
	
	/*
	 * Type of task indices
	 */
	private static final int FLOATING_TYPE_OF_TASK = 0;
	private static final int DEADLINE_TYPE_OF_TASK = 1;
	private static final int TIMED_TYPE_OF_TASK = 2;
	
	/*
	 * Indices
	 */
	private static final int ID_INDEX = 0;
	private static final int TITLE_INDEX = 1;
	private static final int VENUE_INDEX = 2;
	private static final int START_TIME_INDEX = 3;
	private static final int END_TIME_INDEX = 4;
	private static final int PRIORITY_INDEX = 5;
	private static final int STATUS_INDEX = 6;
	private static final int DONE_INDEX = 1;
	private static final int UNDONE_INDEX = 0;

	/**
	 * This method writes tasks stored in storage into the external file
	 * @param storage
	 * @throws IOException
	 */
	public static void saveToFile(EzStorage storage) throws IOException {
		EzBinaryWriter writer = new EzBinaryWriter(EXTERNAL_FILENAME);
		
		ArrayList<EzTask> tempStorage = EzSort.sortByPriority(storage.getListOfAllTasks());
		writer.write(tempStorage.size(), TASKS_LIST_BYTES);
		for(int i = 0; i < tempStorage.size(); i++) {
			int typeOfTask;
			int numAttribute;
			EzTask task = tempStorage.get(i);
			if(task.getStartTime() != tempStorage.get(i).getEndTime()) {
				typeOfTask = TIMED_TYPE_OF_TASK;
			}
			else {
				if(task.getStartTime() == null) {
					typeOfTask = FLOATING_TYPE_OF_TASK;
				}
				else {
					typeOfTask = DEADLINE_TYPE_OF_TASK;
				}
			}
			writer.write(typeOfTask, TYPE_OF_TASK_BYTES);
			switch(typeOfTask) {
			case FLOATING_TYPE_OF_TASK:
				numAttribute = FLOATING_NUM_OF_ATTRIBUTES;
				break;
			case DEADLINE_TYPE_OF_TASK:
				numAttribute = DEADLINE_NUM_OF_ATTRIBUTES;
				break;
			default:
				numAttribute = TIMED_NUM_OF_ATTRIBUTES;
				break;
			}
			writer.write(numAttribute, NUM_OF_ATTRIBUTES_BYTES);
			
			// id
			writer.write(ID_INDEX, INDEX_BYTES); 
			writer.write(task.getId(), ID_BYTES);
			
			// title
			writer.write(TITLE_INDEX, INDEX_BYTES); 
			writer.write(task.getTitle().length(), TITLE_BYTES);
			writer.writeString(task.getTitle());
			
			// venue
			writer.write(VENUE_INDEX, INDEX_BYTES);
			int lenVenue;
			if(task.getVenue() == null) {
				lenVenue = 0;
			}
			else {
				lenVenue = task.getVenue().length();
			}
			writer.write(lenVenue, VENUE_BYTES);
			writer.writeString(task.getVenue());
			
			// priority
			writer.write(PRIORITY_INDEX, INDEX_BYTES);
			writer.write(task.getPriority(), PRIORITY_BYTES);
			
			// done
			writer.write(STATUS_INDEX, INDEX_BYTES);
			int status;
			if(task.isDone()) {
				status = DONE_INDEX;
			}
			else {
				status = UNDONE_INDEX;
			}
			writer.write(status, STATUS_BYTES);
			
			// time
			if(typeOfTask != FLOATING_TYPE_OF_TASK) {
				writer.write(START_TIME_INDEX, INDEX_BYTES);
				writer.write(task.getStartTime().get(Calendar.YEAR), YEAR_BYTES);
				writer.write(task.getStartTime().get(Calendar.MONTH), MONTH_BYTES);
				writer.write(task.getStartTime().get(Calendar.DAY_OF_MONTH), DAY_OF_MONTH_BYTES);
				writer.write(task.getStartTime().get(Calendar.HOUR_OF_DAY), HOUR_OF_DAY_BYTES);
				writer.write(task.getStartTime().get(Calendar.MINUTE), MINUTES_BYTES);
				
				if(typeOfTask == TIMED_TYPE_OF_TASK) {
					writer.write(END_TIME_INDEX, INDEX_BYTES);
					writer.write(task.getEndTime().get(Calendar.YEAR), YEAR_BYTES);
					writer.write(task.getEndTime().get(Calendar.MONTH), MONTH_BYTES);
					writer.write(task.getEndTime().get(Calendar.DAY_OF_MONTH), DAY_OF_MONTH_BYTES);
					writer.write(task.getEndTime().get(Calendar.HOUR_OF_DAY), HOUR_OF_DAY_BYTES);
					writer.write(task.getEndTime().get(Calendar.MINUTE), MINUTES_BYTES);
				}
			}
		}
		writer.close();
	}
	
	/**
	 * This method reads the data from the external file once the program starts up
	 * @param storage
	 * @throws IOException
	 */
	public static void loadFromFile(EzStorage storage) throws IOException {
		try{
			EzBinaryReader rd = new EzBinaryReader(EXTERNAL_FILENAME);
			if (rd.available() > 0){
				int numTask = rd.read(TASKS_LIST_BYTES);
				for (int i = 0; i < numTask; i++){
					int typeOfTask = rd.read(TYPE_OF_TASK_BYTES);
					int numAttribute = rd.read(NUM_OF_ATTRIBUTES_BYTES);
					EzTask temp = new EzTask();
					for(int j = 0; j < numAttribute; j++) {
						int typeAtt = rd.read(INDEX_BYTES);
						switch(typeAtt) {
						case 0:
							temp.setId(rd.read(ID_BYTES));
							break;
						case 1:
							int sizeOfTitle = rd.read(TITLE_BYTES);
							temp.setTitle(rd.readString(sizeOfTitle));
							break;
						case 2:
							int sizeOfVenue = rd.read(VENUE_BYTES);
							temp.setVenue(rd.readString(sizeOfVenue));
							break;
						case 3:
							temp.setStartTime(rd.read(YEAR_BYTES), rd.read(MONTH_BYTES), rd.read(DAY_OF_MONTH_BYTES), rd.read(HOUR_OF_DAY_BYTES), rd.read(MINUTES_BYTES));
							break;
						case 4:
							temp.setEndTime(rd.read(YEAR_BYTES), rd.read(MONTH_BYTES), rd.read(DAY_OF_MONTH_BYTES), rd.read(HOUR_OF_DAY_BYTES), rd.read(MINUTES_BYTES));
							break;
						case 5:
							temp.setPriority(rd.read(PRIORITY_BYTES));
							break;
						default:
							temp.setDone(rd.read(STATUS_BYTES) == DONE_INDEX);
							break;
						}
						if (typeOfTask == DEADLINE_TYPE_OF_TASK) {
							temp.setEndTimeAsStartTime();
						}
					}
					storage.addTask(temp);
				}
				ArrayList<EzTask> loadedList = storage.getListOfAllTasks();
				EzGUI.showContent("Loaded from file", EzSort.sortById(loadedList));
				rd.close();
			}
		} 
		catch (IOException e)
		{
			EzBinaryWriter write = new EzBinaryWriter(EXTERNAL_FILENAME);
		}
		
	}
}
