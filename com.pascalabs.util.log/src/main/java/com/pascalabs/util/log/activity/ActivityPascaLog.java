package com.pascalabs.util.log.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.pascalabs.util.log.R;
import com.pascalabs.util.log.adapter.LogAdapter;
import com.pascalabs.util.log.helper.Preference;
import com.pascalabs.util.log.model.BeanLog;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityPascaLog extends Activity implements AbsListView.OnScrollListener, View.OnClickListener {

    private ListView lv;
    private Button btnClear;
    private Button btnSendEmail;
    private TextView tvDetail;
    private TextView tvInfoClose;
    private ScrollView SVLogDetail;

    private int mPage = 1, mVisibleCount, mVisibleFirst;
    private boolean isNoMoreResult;
    private LogAdapter mAdapter;
    private ArrayList<BeanLog> mLog = new ArrayList<BeanLog>();

    private boolean toogleLogProcess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_log);

        lv = (ListView) findViewById(R.id.lv);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        tvInfoClose = (TextView) findViewById(R.id.tvInfoClose);
        SVLogDetail = (ScrollView) findViewById(R.id.SVLogDetail);

        try {
            mLog = new ArrayList<BeanLog>();
            mLog = Preference.getInstance(ActivityPascaLog.this).getLogTrackerSaved();
            Collections.reverse(mLog);
            mAdapter = new LogAdapter(ActivityPascaLog.this, mLog);
            lv.setAdapter(mAdapter);
        } catch (Exception e) {}
        lv.setOnItemClickListener(ListenerClickItem);
        btnClear.setOnClickListener(this);
        btnSendEmail.setOnClickListener(this);
        tvInfoClose.setOnClickListener(this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    ActivityPascaLog.this.runOnUiThread(new Thread(new Runnable() {
                        public void run() {
                            callApi();
                        }
                    }));
                    handler.postDelayed(this, 1 * 1000);
                } catch (Exception e) {
                }
            }
        }, 1 * 1000);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void callApi() {

        if(toogleLogProcess) return;

        try {
            extractNotif();
        }
        catch (Exception e) {}
    }

    public void extractNotif() {
        try {
            mLog = Preference.getInstance(ActivityPascaLog.this).getLogTrackerSaved();
            Collections.reverse(mLog);
            mAdapter.setItems(mLog);
            mAdapter.notifyDataSetChanged();
            lv.invalidateViews();
        }
        catch (Exception e) {}
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.mVisibleCount = visibleItemCount;
        this.mVisibleFirst = firstVisibleItem;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && mAdapter.getCount() <= mVisibleCount + mVisibleFirst && !isNoMoreResult) {
            isNoMoreResult = true;
            mPage++;

            callApi();
        }
    }


    public AdapterView.OnItemClickListener ListenerClickItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
            tvDetail.setText( mLog.get(pos).getEvent());
            SVLogDetail.setVisibility(View.VISIBLE);
        }
    };


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnClear){
            try {
                mLog = new ArrayList<BeanLog>();
                Preference.getInstance(ActivityPascaLog.this).saveLogTrackerSaved(mLog);
                callApi();
            } catch (Exception e) {}
        }else if(view.getId() == R.id.tvInfoClose){
            SVLogDetail.setVisibility(View.GONE);
        }else if(view.getId() == R.id.btnSendEmail){
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{Preference.getInstance(ActivityPascaLog.this).getEmailAddressSaved()});
                i.putExtra(Intent.EXTRA_SUBJECT, Preference.getInstance(ActivityPascaLog.this).getEmailSubjectSaved());
                i.putExtra(Intent.EXTRA_TEXT   , tvDetail.getText().toString());
                try {
                    startActivity(Intent.createChooser(i, "Send mail"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ActivityPascaLog.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(ActivityPascaLog.this, "Please set your email address", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
