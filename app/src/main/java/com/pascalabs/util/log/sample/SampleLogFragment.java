package com.pascalabs.util.log.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.pascalabs.util.log.fragment.FragmentPascaLog;
import com.pascalabs.util.log.helper.Helper;

public class SampleLogFragment extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_log_fragment);


        Helper.setEmailAndAddressForSendLogReport(this, "your.email@address.com", "subject");

        Helper.logEventLocal(this, "INFO", "Log 1");
        Helper.logEventLocal(this, "INFO", "Log 2");
        Helper.logEventLocal(this, "INFO", "Log 3");
        Helper.logEventLocal(this, "INFO", "Log 4");
        Helper.logEventLocal(this, "INFO", "Log 5");

        getFragmentManager().beginTransaction().replace(R.id.thefragment, new FragmentPascaLog()).addToBackStack(null).commit();

    }


}
