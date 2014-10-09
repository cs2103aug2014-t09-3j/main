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
	
	public static String createHtmlEzTask(EzTask task,int type){
		if (task!=null){
			return 	"<table border=0 cellspacing=0 cellpadding=1 bgcolor=\"#" + convertColorToHex(TASK_BG_COLOR[type]) + "\" width=\"560px\"><tr>"
					+ "<td width=\"53px\" bgcolor=\"#" + convertColorToHex(ID_BG_COLOR[type]) +"\">" + createHtmlIdAndPriorityOfEzTask(task) + "</td>"
					+ "<td width=\"5px\"></td>"
					+ "<td width=\"300px\">"  + createHtmlTitleAndVenueOfEzTask(task) + "</td>" 
					+ "<td width=\"15px\"></td>"
					+ "<td align=\"left\" >"  + createHtmlDateOfEzTask(task) + "</td>"
					+ "<td align=\"right\" width=\"40px\">"  + createHtmlDoneOfEzTask(task) + "</td>" 
					+ "</tr></table>";
		} else {
			return "";
		}
	}
	
	private static String createHtmlDoneOfEzTask(EzTask task) {
		if (task.isDone()){
			return createHtmlImg("image/done.png");
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
				if ((date1.get(Calendar.HOUR_OF_DAY)!=0) || (date1.get(Calendar.MINUTE)!=0)){
					list.add(createHtmlClock(date1));
				}
			} else {
				list.add(createHtmlCalendar(date1));
				list.add(createHtmlClock(date1));
				list.add(createHtmlImg("image/rightArrow.png"));
				if ((date1.get(Calendar.YEAR) != date2.get(Calendar.YEAR))
						|| (date1.get(Calendar.MONTH) != date2.get(Calendar.MONTH))
						|| (date1.get(Calendar.DATE) != date2.get(Calendar.DATE))){
					list.add(createHtmlCalendar(date2));
				}
				list.add(createHtmlClock(date2));
			}
		}
		return createHtmlTable(1,list.size(),list,"border=0 cellspacing=0 cellpadding=1");
	}
	
	public static String createHtmlTable(int row, int col, ArrayList<String> list, String tableAttribute){
		String result = "<table "+tableAttribute+">";
		for(int i=0;i<row;i++){
			result = result + "<tr>";
			for(int j=0;j<col;j++){
				result = result + "<td>";
				if (i*col+j<list.size()) {
					result = result + list.get(i*col+j);
				}
				result = result + "</td>";
			}
			result = result + "</tr>";
		}
		return result + "</table>";
	}
	
	private static String createHtmlTitleAndVenueOfEzTask(EzTask task) {
		String result = createHtmlText(task.getTitle(),TITLE_FONT_FONT,TITLE_FONT_SIZE, TITLE_FONT_COLOR); 
		if (task.getVenue()!=null){
			result = result + "<br>"+right(createHtmlText("@"+task.getVenue(),VENUE_FONT_FONT,VENUE_FONT_SIZE , VENUE_FONT_COLOR));
		}
		return result;
	}

	private static String createHtmlIdAndPriorityOfEzTask(EzTask task){
		String htmlId = createHtmlText("#"+task.getId(), ID_FONT_FONT, ID_FONT_SIZE, ID_FONT_COLOR);
		String htmlPriority = createHtmlStar(task.getPriority());
		return "<table width=\"48px\"><tr><td>" + center(htmlId) + "</td></tr>"
				+ "<tr><td>"+ center(htmlPriority) + "</td></tr></table>";
	}
	
	private static String createHtmlCalendar(GregorianCalendar date){
		String monthHtmlText = createHtmlText(CALENDAR_MONTH[date.get(Calendar.MONTH)],"Arial Rounded MT Bold",2,new Color(255,255,255));
		String dateHtmlText = createHtmlText(String.valueOf(date.get(Calendar.DATE)),"Arial",6,CALENDAR_DATE_FONT_COLOR);
		
		return "<table background=\"file:" + IMAGE_CALENDAR_PNG +"\" border=0 cellspacing=0 cellpadding=0 width=\"38px\">"
		+ "<tr><td height=\"10px\">" + center(monthHtmlText) + "</td></tr>" 
		+ "<tr><td height=\"27px\">" + center(dateHtmlText) + "</td></tr></table>";
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
		return "<table background=\"file:" + IMAGE_CLOCK_PNG +"\" border=0 cellspacing=0 cellpadding=0 width=\"40px\">"
		+ "<tr><td width=\"2px\"></td><td height=\"25px\">" + center(timeHtmlText) + "</td></tr></table>";
	}
	
	private static String createHtmlImg(String url){
		return "<img src=\"file:"+ url + "\">";
	}
	
	private static String createHtmlStar(int numStar){
		String result = "";
		String chosenStar = "";
		switch (numStar){
		case 1:
			chosenStar = createHtmlImg("image/star100.png");
			break;
		case 2:
			chosenStar = createHtmlImg("image/star080.png");
			break;
		case 3:
			chosenStar = createHtmlImg("image/star060.png");
			break;
		case 4:
			chosenStar = createHtmlImg("image/star040.png");
			break;
		case 5:
			chosenStar = createHtmlImg("image/star020.png");
			break;
		default:
			chosenStar = createHtmlImg("image/star000.png");
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
	
	private static String center(String content){
		return "<center>" + content + "</center>";
	}
	
	public static String right(String content){
		return "<div align=\"right\">" + content + "</div>";
	}
	
	private static String convertColorToHex(Color color){
		return convertIntToHex(color.getRed(),2) + convertIntToHex(color.getGreen(),2) + convertIntToHex(color.getBlue(),2);
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
