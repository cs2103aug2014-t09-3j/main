import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


public class EzDataManage {
	

	public static void saveToFile(EzStorage storage) throws IOException {
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
	
	public static void loadFromFile(EzStorage storage) throws IOException {
		try{
			EzBinaryReader rd = new EzBinaryReader("external.ezt");
			if (rd.available() > 0){
				int numTask = rd.read(4);
				for (int i = 0; i < numTask; i++){
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
}
