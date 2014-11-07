import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

//@author A0112129U
public class EzHtmlGenerator {
	
	private static final int TIME_FONT_SIZE = 5;

	private static final int DAY_OF_WEEK_CALENDAR_FONT_SIZE = 2;

	private static final int MONTH_CALENDAR_FONT_SIZE = 3;

	private static final int DATE_CALENDAR_FONT_SIZE = 3;

	private static final int YEAR_CALENDAR_FONT_SIZE = 2;

	private static final int MAXIMUM_CHARACTER = 20;

	private static final int MAIN_TITLE_FONT_SIZE = 8;

	private static final int STAR_PER_LINE = 5;
	
	private static final Color MAIN_TITLE_FONT_COLOR = new Color(231,76,60);
	private static final String MAIN_TITLE_FONT_FONT = "Arial";
	
	private static final String TITLE_FONT_FONT = "Arial";
	private static final int TITLE_FONT_SIZE = 5;
	private static final Color TITLE_FONT_COLOR = EzConstants.WHITE_SMOKE_COLOR;
	
	private static final String VENUE_FONT_FONT = "Arial Rounded MT Bold";
	private static final int VENUE_FONT_SIZE = 4;
	private static final Color VENUE_FONT_COLOR = EzConstants.WHITE_SMOKE_COLOR;
	
	private static final String ID_FONT_FONT = "Arial Rounded MT Bold";
	private static final int ID_FONT_SIZE = 4;
	private static final Color ID_FONT_COLOR = EzConstants.WHITE_SMOKE_COLOR;
	
	private static final String IMAGE_CALENDAR_PNG = "images/calendar.png";
	private static final String IMAGE_CLOCK_PNG = "images/clock.png";

	private static final int NORMAL_TASK = 0;
	private static final int PAST_OR_DONE_TASK = 1;
	private static final int TODAY_AND_UNDONE_TASK = 2;

	private static final Color[] TASK_BG_COLOR = {EzConstants.CHATEAU_GREEN_COLOR,	// for normal tasks
													EzConstants.ALMOND_FROST_COLOR,		// for past or done tasks
													EzConstants.CURIOUS_BLUE_COLOR};	// for today and undone tasks
	
	private static final Color ID_BG_COLOR = EzConstants.BLUE_GEM_COLOR;
	
	private static final Color CALENDAR_DATE_FONT_COLOR = new Color(231,76,60);
	
	
	private static final String[] CALENDAR_MONTH = {"Jan","Feb","March","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	private static final String[] CALENDAR_DAY_OF_WEEK = {"","Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
	
	private static String td(String format, String content){
		return "<td " + format + ">" + content + "</td>";
	}
	
	private static String td(String content){
		return td("",content);
	}
	
	private static String tr(String format, String content){
		return "<tr " + format + ">" + content + "</tr>";
	}
	
	private static String tr(String content){
		return tr("",content);
	}
	
	private static String table(String format, String content){
		return "<table " + format + ">" + content + "</table>";
	}
	
	public static String createHtmlEzTask(EzTask task){
		if (task!=null){
			int typeOfTask = NORMAL_TASK;
			if (task.isDone() || task.isPast()){
				typeOfTask = PAST_OR_DONE_TASK;
			} else if (task.isToday()){
				typeOfTask = TODAY_AND_UNDONE_TASK;
			}
			return 	table("border=0 cellspacing=0 cellpadding=1 bgcolor=\"#" + convertColorToHex(TASK_BG_COLOR[typeOfTask]) + "\" width=\"100%\"",
						tr(
							td("width=\"53px\" bgcolor=\"#" + convertColorToHex(ID_BG_COLOR) +"\"  height=\"40px\"",createHtmlIdAndPriorityOfEzTask(task)) +
							td("width=\"5px\"", "") +
							//td("width=\"300px\"", createHtmlTitleAndVenueOfEzTask(task)) +
							td(createHtmlTitleAndVenueOfEzTask(task)) +
							td("width=\"15px\"", "") +
							//td("align=\"left\"", createHtmlDateOfEzTask(task)) +
							td("align=\"left\" width=\"200px\"", createHtmlDateOfEzTask(task)) +
							td("align=\"right\" width=\"40px\"", createHtmlDoneOfEzTask(task))
						)
					);
		} else {
			return "";
		}
	}
	
	private static String createHtmlDoneOfEzTask(EzTask task) {
		if (task.isDone()){
			return img("images/done.png");
		}
		return "";
	}

	private static String createHtmlDateOfEzTask(EzTask task) {
		GregorianCalendar date1 = task.getStartTime();
		GregorianCalendar date2 = task.getEndTime();
		ArrayList<String> list = new ArrayList<String>();
		
		if ((date1==null) || (date2==null)){
			return "";
		}
		else {
			if (date1.equals(date2)){
				list.add(createHtmlCalendar(date1));
				//if ((date1.get(Calendar.HOUR_OF_DAY)!=0) || (date1.get(Calendar.MINUTE)!=0)){
				list.add(createHtmlClock(date1));
				//}
			} else {
				list.add(createHtmlCalendar(date1));
				if ((date1.get(Calendar.HOUR_OF_DAY)!=0) || (date1.get(Calendar.MINUTE)!=0) ||
						(date2.get(Calendar.HOUR_OF_DAY)!=23) || (date2.get(Calendar.MINUTE)!=59)){
					list.add(createHtmlClock(date1));
				}
				if ((date1.get(Calendar.YEAR) != date2.get(Calendar.YEAR))
						|| (date1.get(Calendar.MONTH) != date2.get(Calendar.MONTH))
						|| (date1.get(Calendar.DATE) != date2.get(Calendar.DATE))
						|| (date1.get(Calendar.HOUR_OF_DAY)!=0) || (date1.get(Calendar.MINUTE)!=0) 
						|| (date2.get(Calendar.HOUR_OF_DAY)!=23) || (date2.get(Calendar.MINUTE)!=59)){
					list.add(img("images/rightArrow.png"));
				}
				if ((date1.get(Calendar.YEAR) != date2.get(Calendar.YEAR))
						|| (date1.get(Calendar.MONTH) != date2.get(Calendar.MONTH))
						|| (date1.get(Calendar.DATE) != date2.get(Calendar.DATE))){
					list.add(createHtmlCalendar(date2));
				}
				if ((date1.get(Calendar.HOUR_OF_DAY)!=0) || (date1.get(Calendar.MINUTE)!=0) ||
						(date2.get(Calendar.HOUR_OF_DAY)!=23) || (date2.get(Calendar.MINUTE)!=59)){
					list.add(createHtmlClock(date2));
				}
			}
		}
		return createHtmlTable(1,list.size(),list,"border=0 cellspacing=0 cellpadding=1");
	}
	
	public static String createHtmlTable(int row, int col, ArrayList<String> list, String tableAttribute){
		String result = "";
		for(int i=0;i<row;i++){
			String tdList = "";
			for(int j=0;j<col;j++){
				if (i*col+j<list.size()) {
					tdList += td(list.get(i*col+j));
				}
			}
			result = result + tr(tdList);
		}
		return table(tableAttribute,result);
	}
	
	public static String createHtmlTableWithHeader(String header, String content, String tableAttribute){
		header = EzHtmlGenerator.createHtmlText("__",MAIN_TITLE_FONT_FONT,2,EzConstants.SHOW_AREA_BACKGROUND)
				+EzHtmlGenerator.createHtmlText(header,MAIN_TITLE_FONT_FONT, MAIN_TITLE_FONT_SIZE, MAIN_TITLE_FONT_COLOR);
		String result = table(tableAttribute,
							tr(td("height=\"44px\" width=\"100%\" ", header)) +
							tr(td(content))
						);
		return result;
	}
	
	private static String createHtmlTitleAndVenueOfEzTask(EzTask task) {
		String result = createHtmlText(addBreak(task.getTitle()),TITLE_FONT_FONT,TITLE_FONT_SIZE, TITLE_FONT_COLOR); 
		if (!task.getVenue().equalsIgnoreCase("")){
			result = result + "<br>"+right(createHtmlText("@"+addBreak(task.getVenue()),VENUE_FONT_FONT,VENUE_FONT_SIZE , VENUE_FONT_COLOR));
		}
		return result;
	}

	private static String addBreak(String text) {
		String result = "";
		for(int i=0;i<text.length();i++){
			result = result + text.charAt(i); 
			if (text.charAt(i)==' '){
				while((i+1<text.length()) && (text.charAt(i+1)==' ')){
					result = result + ' ';
					i++;
				}
			} else {
				int count = 1;
				while((i+1<text.length()) && (text.charAt(i+1)!=' ')){
					result = result + text.charAt(i+1);
					i++;
					count++;
					if (count==MAXIMUM_CHARACTER){
						result = result + "-<br/>";
						count = 0;
					}
				}
			}
		}
		return result;
	}

	private static String createHtmlIdAndPriorityOfEzTask(EzTask task){
		String htmlId = createHtmlText("#"+task.getId(), ID_FONT_FONT, ID_FONT_SIZE, ID_FONT_COLOR);
		String htmlPriority = createHtmlStar(task.getPriority());
		
		return 	table("width=\"48px\"",
					tr(td(center(htmlId))) +
					tr(td(center(htmlPriority)))
				);
	}
	
	private static String createHtmlCalendar(GregorianCalendar date){
		String dayOfWeekHtmlText = createHtmlText(CALENDAR_DAY_OF_WEEK[date.get(Calendar.DAY_OF_WEEK)],"Arial Rounded MT Bold",DAY_OF_WEEK_CALENDAR_FONT_SIZE, Color.white);
		String monthHtmlText = createHtmlText(CALENDAR_MONTH[date.get(Calendar.MONTH)],"Arial Rounded MT Bold",MONTH_CALENDAR_FONT_SIZE,CALENDAR_DATE_FONT_COLOR);
		String dateHtmlText = createHtmlText(String.valueOf(date.get(Calendar.DATE)),"Arial Rounded MT Bold",DATE_CALENDAR_FONT_SIZE,CALENDAR_DATE_FONT_COLOR);
		String yearHtmlText = createHtmlText(String.valueOf(date.get(Calendar.YEAR)),"Arial",YEAR_CALENDAR_FONT_SIZE,CALENDAR_DATE_FONT_COLOR);
		
		return 	table("background=\"file:" + IMAGE_CALENDAR_PNG +"\" border=0 cellspacing=0 cellpadding=0 width=\"38px\"",
					tr(td("height=\"10px\"",center(dayOfWeekHtmlText))) +
					tr(td("height=\"17px\"",center(monthHtmlText + " " +dateHtmlText))) + 
					tr(td("height=\"10px\"",center(yearHtmlText)))
				);
	}
	
	private static String createHtmlClock(GregorianCalendar date){
		String hour = String.valueOf(date.get(Calendar.HOUR_OF_DAY));
		if (date.get(Calendar.HOUR_OF_DAY)<10) {
			hour = "0" + hour;
		}
		
		String minute = String.valueOf(date.get(Calendar.MINUTE));
		if (date.get(Calendar.MINUTE)<10) {
			minute = "0" + minute;
		}
		
		String timeHtmlText = createHtmlText(hour+":"+minute,"Digital Dismay",TIME_FONT_SIZE,"38e204");
		
		return 	table("background=\"file:" + IMAGE_CLOCK_PNG +"\" border=0 cellspacing=0 cellpadding=0 width=\"40px\"",
					tr(td("width=\"2px\"","") + td("height=\"25px\"",center(timeHtmlText)))
				);
	}
	
	private static String img(String url){
		return "<img src=\"file:"+ url + "\">";
	}
	
	private static String createHtmlStar(int numStar){
		String result = "";
		String chosenStar = "";
		switch (numStar){
		case 1:
			chosenStar = img("images/star100.png");
			break;
		case 2:
			chosenStar = img("images/star080.png");
			break;
		case 3:
			chosenStar = img("images/star060.png");
			break;
		case 4:
			chosenStar = img("images/star040.png");
			break;
		case 5:
			chosenStar = img("images/star020.png");
			break;
		default:
			chosenStar = img("images/star000.png");
			break;
		}
		for(int i=0;i<numStar;i++) {
			result = result + chosenStar;
			if (i % STAR_PER_LINE == STAR_PER_LINE-1) {
				result = result + "<br>";
			}
		}
		return result;
	}
	
	public static String center(String content){
		return "<center>" + content + "</center>";
	}
	
	public static String right(String content){
		return "<div align=\"right\">" + content + "</div>";
	}
	
	public static String createHtmlText(final String content, final String font, final int size, final Color color) {
		return "<font face=\"" + font +
				"\" size=\"" + size +
				"\" color=\"#" + convertColorToHex(color) + "\">" + 
				content + "</font>";
	}
	
	public static String createHtmlText(String content, String font, int size, String hexColor) {
		return "<font face=\"" + font +
				"\" size=\"" + size +
				"\" color=\"#" + hexColor + "\">" + 
				content + "</font>";
	}
	
	private static String convertColorToHex(Color color){
		return convertIntToHex(color.getRed(),2) + convertIntToHex(color.getGreen(),2) + convertIntToHex(color.getBlue(),2);
	}
	
	private static String convertIntToHex(int i, int length){
		if (length == 0) return "";
		if ((0<=i) && (i<=9)) {
			return convertIntToHex(i/16,length-1) + (char)('0'+i);
		}
		else if (i<16){
			return convertIntToHex(i/16,length-1) + (char)('a'+i-10);
		} else {
			return convertIntToHex(i/16,length-1) + convertIntToHex(i%16,length-1);
		}
	}
}
