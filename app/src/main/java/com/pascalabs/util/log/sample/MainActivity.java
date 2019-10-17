package com.pascalabs.util.log.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pascalabs.util.log.activity.ActivityPascaLog;
import com.pascalabs.util.log.helper.Helper;

public class MainActivity extends Activity {

    private Button btnSampleActivity, btnSampleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSampleActivity = (Button) findViewById(R.id.btnSampleActivity);
        btnSampleFragment = (Button) findViewById(R.id.btnSampleFragment);


        btnSampleActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Helper.setEmailAndAddressForSendLogReport(MainActivity.this, "your.email@address.com", "subject");

                Helper.logEventLocal(MainActivity.this, "INFO", "Log 1");
                Helper.logEventLocal(MainActivity.this, "INFO", "Log 2");
                Helper.logEventLocal(MainActivity.this, "INFO", "Log 3");
                Helper.logEventLocal(MainActivity.this, "INFO", "Log 4");
                Helper.logEventLocal(MainActivity.this, "INFO", "Log 5");
                Helper.logEventLocal(MainActivity.this, "INFO", "Log 6");
                Helper.logEventLocal(MainActivity.this, "INFO", "Log 7");

                startActivity(new Intent(MainActivity.this, ActivityPascaLog.class));

            }
        });


        btnSampleFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, SampleLogFragment.class));

            }
        });

    }

}
