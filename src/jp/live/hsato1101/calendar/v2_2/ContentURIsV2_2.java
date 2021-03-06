package jp.live.hsato1101.calendar.v2_2;

import android.net.Uri;
import jp.live.hsato1101.calendar.CalendarContentURIs;

public class ContentURIsV2_2 extends CalendarContentURIs {
	
	private final static String AUTHORITY = "com.android.calendar";
	private final static Uri EVENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/events");
	private final static Uri EVENT_BY_DAY_URI = Uri.parse("content://"
			+ AUTHORITY + "/instances/whenbyday");
	
	private final static Uri CALENDAR_URI = Uri.parse("content://" + AUTHORITY
			+ "/calendars");
	
	@Override
	public Uri getEventUri() {
		return EVENT_URI;
	}

	@Override
	public Uri getCalendarUri() {
		return CALENDAR_URI;
	}

	@Override
	public Uri getEventByDayUri() {
		return EVENT_BY_DAY_URI;
	}

}
