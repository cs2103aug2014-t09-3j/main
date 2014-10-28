import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 
 */

/**
 * @author Khanh
 *
 */
public class EzHtmlGenerator {
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
	
	private static final String IMAGE_CALENDAR_PNG = "image/calendar.png";
	private static final String IMAGE_CLOCK_PNG = "image/clock.png";

	private static final Color[] TASK_BG_COLOR = {EzConstants.FERN_COLOR, EzConstants.CHATEAU_GREEN_COLOR};
	private static final Color[] ID_BG_COLOR = {EzConstants.WISTERIA_COLOR, EzConstants.BLUE_GEM_COLOR};
	
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
	
	private static String table(String content){
		return table("",content);
	}
	
	public static String createHtmlEzTask(EzTask task,int type){
		if (task!=null){
			return 	table("border=0 cellspacing=0 cellpadding=1 bgcolor=\"#" + convertColorToHex(TASK_BG_COLOR[type]) + "\" width=\"100%\"",
						tr(
							td("width=\"53px\" bgcolor=\"#" + convertColorToHex(ID_BG_COLOR[type]) +"\"  height=\"40px\"",createHtmlIdAndPriorityOfEzTask(task)) +
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
			return img("image/done.png");
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
					list.add(img("image/rightArrow.png"));
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
				+EzHtmlGenerator.createHtmlText(header,MAIN_TITLE_FONT_FONT, 8, MAIN_TITLE_FONT_COLOR);
		String result = table(tableAttribute,
							tr(td("height=\"44px\"", header)) +
							tr(td(content))
						);
		return result;
	}
	
	private static String createHtmlTitleAndVenueOfEzTask(EzTask task) {
		String result = createHtmlText(task.getTitle(),TITLE_FONT_FONT,TITLE_FONT_SIZE, TITLE_FONT_COLOR); 
		if (task.getVenue()!=""){
			result = result + "<br>"+right(createHtmlText("@"+task.getVenue(),VENUE_FONT_FONT,VENUE_FONT_SIZE , VENUE_FONT_COLOR));
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
		/*String monthHtmlText = createHtmlText(CALENDAR_MONTH[date.get(Calendar.MONTH)],"Arial Rounded MT Bold",2,new Color(255,255,255));
		String dateHtmlText = createHtmlText(String.valueOf(date.get(Calendar.DATE)),"Arial",5,CALENDAR_DATE_FONT_COLOR);
		String dayOfWeekHtmlText = createHtmlText(CALENDAR_DAY_OF_WEEK[date.get(Calendar.DAY_OF_WEEK)],"Arial",2,CALENDAR_DATE_FONT_COLOR);
		
		return 	table("background=\"file:" + IMAGE_CALENDAR_PNG +"\" border=0 cellspacing=0 cellpadding=0 width=\"38px\"",
					tr(td("height=\"10px\"",center(monthHtmlText))) +
					tr(td("height=\"17px\"",center(dateHtmlText))) + 
					tr(td("height=\"10px\"",center(dayOfWeekHtmlText)))
				);*/
		String dayOfWeekHtmlText = createHtmlText(CALENDAR_DAY_OF_WEEK[date.get(Calendar.DAY_OF_WEEK)],"Arial Rounded MT Bold",2,new Color(255,255,255));
		String monthHtmlText = createHtmlText(CALENDAR_MONTH[date.get(Calendar.MONTH)],"Arial Rounded MT Bold",3,CALENDAR_DATE_FONT_COLOR);
		String dateHtmlText = createHtmlText(String.valueOf(date.get(Calendar.DATE)),"Arial Rounded MT Bold",3,CALENDAR_DATE_FONT_COLOR);
		String yearHtmlText = createHtmlText(String.valueOf(date.get(Calendar.YEAR)),"Arial",2,CALENDAR_DATE_FONT_COLOR);
		
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
		
		String timeHtmlText = createHtmlText(hour+":"+minute,"Digital Dismay",5,"38e204");
		
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
			chosenStar = img("image/star100.png");
			break;
		case 2:
			chosenStar = img("image/star080.png");
			break;
		case 3:
			chosenStar = img("image/star060.png");
			break;
		case 4:
			chosenStar = img("image/star040.png");
			break;
		case 5:
			chosenStar = img("image/star020.png");
			break;
		default:
			chosenStar = img("image/star000.png");
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
