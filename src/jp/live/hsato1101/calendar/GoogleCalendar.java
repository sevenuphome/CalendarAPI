package jp.live.hsato1101.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class GoogleCalendar {
	
	private final ContentResolver mResolver;
	private final EventColumns mEventColumns;
	private final CalendarContentURIs mURIs;
	private final int mCalendarId;

	public GoogleCalendar(Context context, EventColumns eventColumns, CalendarContentURIs uris, int calendar_id) {
		mResolver = context.getContentResolver();
		mEventColumns = eventColumns;
		mURIs = uris;
		mCalendarId = calendar_id;
	}

	public boolean insert(Event e) {
		ContentValues values = mEventColumns.values(e, mCalendarId);
		mResolver.insert(mURIs.getEventUri(), values);
		return true;
	}

	public int update(Event e) {
    	ContentValues values = mEventColumns.values(e);
    	return mResolver.update(mURIs.toUri(e), values, null, null);
	}
	

	public int delete(Event e) {
		return mResolver.delete(mURIs.toUri(e), null, null);
	}

	public Event[] select(Calendar start, Calendar end) {
		Uri uri = mURIs.buildByDayUri(start, end);
		Cursor c = mResolver.query(uri, null, null, null, mEventColumns.getSortOrder());
		ArrayList<Event> result = new ArrayList<Event>();

		if (c.moveToFirst()) {
			int titleColumn = c.getColumnIndex(mEventColumns.getTitle());
			int descColumn = c.getColumnIndex(mEventColumns.getDescription());
			int eventLocationColumn = c
					.getColumnIndex(mEventColumns.getEventLocation());
			int beginColumn = c.getColumnIndex(mEventColumns.getBegin());
			int endColumn = c.getColumnIndex(mEventColumns.getEnd());
			int eventIdColumn = c.getColumnIndex(mEventColumns.getEventId());
			//int calendarIdColumn = c.getColumnIndex(mEventColumns.getCalendarId());
			
			do {
				Calendar startTime = new GregorianCalendar();
				startTime.setTimeInMillis(c.getLong(beginColumn));
				Calendar endTime = new GregorianCalendar();
				endTime.setTimeInMillis(c.getLong(endColumn));
				
				Event s = new Event(c.getLong(eventIdColumn),
						c.getString(titleColumn), c.getString(descColumn),
						c.getString(eventLocationColumn), startTime, endTime);
				result.add(s);
			} while (c.moveToNext());
		}

		c.close();
		return result.toArray(new Event[0]);
	}
}
