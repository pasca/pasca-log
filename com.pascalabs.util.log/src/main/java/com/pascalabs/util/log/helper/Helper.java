package com.pascalabs.util.log.helper;

import android.content.Context;
import com.pascalabs.util.log.model.BeanLog;
import com.pascalabs.util.log.model.BeanLogAPI;
import org.joda.time.DateTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Helper {

	public static void setEmailAndAddressForSendLogReport(Context ctx, String email, String subject){
		if(ctx != null){
		Preference.getInstance(ctx).saveEmailAddressSaved(email);
		Preference.getInstance(ctx).saveEmailSubjectSaved(subject);
		}
	}

	public static void logEventLocal(Context ctx, String type, String event){
		try {
			ArrayList<BeanLog> templog = null;
			try {
				templog = Preference.getInstance(ctx).getLogTrackerSaved();
			} catch (Exception e) {
				if(templog==null) templog = new ArrayList<BeanLog>();
			}
			if(templog==null) templog = new ArrayList<BeanLog>();
			templog.add(new BeanLog(type, event, String.valueOf(DateTime.now().getMillis())));
			Preference.getInstance(ctx).saveLogTrackerSaved(templog);
		} catch (Exception e) {}
	}
	
	
	private void reduceChoreographerSkippedFramesWarningThreshold() {
	    if (BuildConfig.DEBUG) {
		Field field = null;
		try {
		    field = Choreographer.class.getDeclaredField("SKIPPED_FRAME_WARNING_LIMIT");
		    field.setAccessible(true);
		    field.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		    field.set(null, 5);
		} catch (Throwable e) {
		    Log.e(TAG, "failed to change choreographer's skipped frames threshold");
		}
	    }
	}

	public static void logThisAPI(Context ctx, BeanLogAPI api){
		try {
			String shortenClassName = api.getShortenClassName().getClass().toString().substring(api.getShortenClassName().getClass().toString().indexOf("$")+1);
			String params = api.getParams().toString();
			String method = api.getMethod();
			String header = api.getHeader().toString();
			String fileparam = api.getFileparam().toString();
			String byteparam = api.getByteparam().toString();
			String url = api.getUrl().toString();
			SimpleDateFormat formatter = new SimpleDateFormat(Constants.TIME_JSON_SHORTEN);
			String dateString = formatter.format(new Date(DateTime.now().getMillis()));
			logEventLocal(ctx, "API", shortenClassName + "\n" +
					"Date : " + dateString + "\n" +
					"URL : " + url + "\n" +
					"Method : " + method + "\n" +
					"Header : " + header + "\n" +
					"Params : " + params + "\n" +
					"File Params : " + fileparam + "\n" +
					"Byte Params : " + byteparam + "\n" +
					"Status Code : " + api.getStatuscode() + "\n" +
					"Response : " + api.getContent().toString());
		} catch (Exception e) {}
	}

}
