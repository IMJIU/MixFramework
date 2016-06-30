import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimeZoneTest {

	protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void printSysProperties() {
		Properties props = System.getProperties();
		Iterator iter = props.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			System.out.println(key + " = " + props.get(key));
		}
	}

	/**
	 * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
	 * 
	 * @param timeZoneOffset
	 * @return
	 */
	public String getFormatedDateString(int timeZoneOffset) {
		if (timeZoneOffset > 13 || timeZoneOffset < -12) {
			timeZoneOffset = 0;
		}
		TimeZone timeZone;
		String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
		if (ids.length == 0) {
			// if no ids were returned, something is wrong. use default TimeZone
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(timeZone);
		return sdf.format(new Date());
	}

	public static String getFormatedDateString(String _timeZone) {
		TimeZone timeZone = null;
		if (_timeZone == null || "".equals(_timeZone)) {
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = TimeZone.getTimeZone(_timeZone);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		sdf.setTimeZone(timeZone);
		// TimeZone.setDefault(timeZone);
		return sdf.format(new Date());
	}

	public static void main(String args[]) {
		test_time_zone();
	}
	
	public static void test_time_zone() {
		// System.setProperty("user.timezone","Europe/Madrid");
		 printSysProperties();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");

		System.out.println(sdf.format(new Date()));
		System.out.println(getFormatedDateString(""));

		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		System.out.println(sdf2.format(new Date()));
		System.out.println(getFormatedDateString(""));
		
		System.out.println(TimeZone.getDefault());

		// System.out.println(getFormatedDateString("Asia/Shanghai"));
		// System.out.println(getFormatedDateString("Japan"));
		// System.out.println(getFormatedDateString("Europe/Madrid"));
		// System.out.println(getFormatedDateString("GMT+8:00"));

//		 printSysProperties();
	}
}
