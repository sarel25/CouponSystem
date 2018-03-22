package sidekicks;

import java.util.Calendar;
import java.util.Date;
/***
 * Date method for setting new dates
 * @author grimbergs
 *
 */
public class DateMaker {
	
	

	/***
	 * Date fixer
	 * @param year
	 * @param month
	 * @param day
	 * @return a fix date
	 */
	public static Date dateFix(int year , int month , int day) {
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(year, month , day);
		date = cal.getTime();
		
		return date;
		
	}
}
