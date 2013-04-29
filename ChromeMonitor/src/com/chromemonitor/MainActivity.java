package com.chromemonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {
	
	boolean isRunning = false;
	private boolean detectEnabled;
	Button btnService;
	CheckBox cbCall;
	CheckBox cbSms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnService = (Button)findViewById(R.id.service_button);
        cbCall = (CheckBox)findViewById(R.id.check_call);
        cbSms = (CheckBox)findViewById(R.id.check_sms);
        
        btnService.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	setDetectEnabled(!detectEnabled);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void setDetectEnabled(boolean enable) {
    	detectEnabled = enable;
    	
        Intent intent = new Intent(this, ChromeMonitorService.class);
        Intent notificationIntent = new Intent(this, MonitorService.class);
    	if (enable) {
    		if (cbCall.isChecked()) {
    			startService(intent);
    		}
    		if (cbSms.isChecked()) {
    			startService(notificationIntent);
    		}
            btnService.setText("Stop");
    	}
    	else {
    		stopService(intent);
    		stopService(notificationIntent);
    		btnService.setText("Start");
    	}
    }
}
