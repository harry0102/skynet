package common.util.mediainfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParseDate {

	static Log log = LogFactory.getLog(ParseDate.class);

	/**
	 * 适配不同环境下mediainfo客户端返回的 适配时长数据
	 * 
	 * @param param
	 * @return int 分钟
	 */
	public static int parse(String param) {
		int value = 0;
		String os = System.getProperties().getProperty("os.name");
		if (os.startsWith("win") || os.startsWith("Win")) {// windows
			if (param.contains(".")) {
				param = param.split("\\.")[0];
				int paramint = Integer.parseInt(param);
				if (paramint < 60000) {
					value = value + 1;
				} else {
					value = paramint % 60000;
				}
			}
		} else {// Linux
			if (param.contains(".")) {
				param = param.split("\\.")[0];
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				try {
					Date date = sdf.parse(param);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					int hour = calendar.get(Calendar.HOUR);
					if (hour > 0) {
						value = value + hour * 60;
					}
					int minute = calendar.get(Calendar.MINUTE);
					if (minute > 0) {
						value = value + minute;
					}
					int second = calendar.get(Calendar.SECOND);
					if (second > 0) {
						value = value + 1;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static Integer parseSecond(String param) {
		Integer  value = 0;

		if (param.contains(".")) {
			param = param.split("\\.")[0];
		}
		int paramint = Integer.parseInt(param);
		if (paramint < 1000) {
			value = value + 1;
		} else {
			value = paramint / 1000;
		}
		return value;
	}
}
