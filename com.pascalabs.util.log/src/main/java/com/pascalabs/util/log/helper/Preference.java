package com.pascalabs.util.log.helper;

import android.content.Context;
import android.content.SharedPreferences;
import com.pascalabs.util.log.model.BeanLog;
import java.util.ArrayList;

public class Preference {

	private final String PREF_NAME_NO_CLEAR = "pascalabs_log_noclear";

	//Email address
	private final String EMAIL_ADDRESS_SAVED = "email_address_saved";

	//Email Subject
	private final String EMAIL_SUBJECT_SAVED = "email_subject_saved";

    //LOG
    private final String LOG_SAVED = "log_saved";

	private Context context;
	private static Preference pref;

	public static Preference getInstance(Context context) {
		if (pref == null) {
			pref = new Preference(context);
		}

		return pref;
	}

	private Preference(Context context) {
		this.context = context;
	}


	public void saveEmailAddressSaved(String email) {
		try {
			getPreferenceNoClear().edit().putString(EMAIL_ADDRESS_SAVED, email).commit();
		} catch (Exception e) {}
	}

	public String getEmailAddressSaved() {
		try {
			String test = getPreferenceNoClear().getString(EMAIL_ADDRESS_SAVED, null);
			return test;
		} catch (Exception e) {}
		return null;
	}

	public void saveEmailSubjectSaved(String subject) {
		try {
			getPreferenceNoClear().edit().putString(EMAIL_SUBJECT_SAVED, subject).commit();
		} catch (Exception e) {}
	}

	public String getEmailSubjectSaved() {
		try {
			String test = getPreferenceNoClear().getString(EMAIL_SUBJECT_SAVED, null);
			return test;
		} catch (Exception e) {}
		return null;
	}


	public SharedPreferences getPreferenceNoClear() {
		return context.getSharedPreferences(PREF_NAME_NO_CLEAR, Context.MODE_PRIVATE);
	}

	public void saveLogTrackerSaved(ArrayList<BeanLog> nsaved) {
		try {
			getPreferenceNoClear().edit().putString(LOG_SAVED, ObjectSerializer.serialize(nsaved)).commit();
		} catch (Exception e) {}
	}

	public ArrayList<BeanLog> getLogTrackerSaved() {
		try {
			String test = getPreferenceNoClear().getString(LOG_SAVED, null);
			return ((ArrayList<BeanLog>)ObjectSerializer.deserialize(test));
		} catch (Exception e) {}
		return null;
	}

}
