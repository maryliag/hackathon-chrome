package br.com.gushiwho.james;

import android.app.Activity;
import android.content.Context;
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
	Button btnNewCode;
	CheckBox cbCall;
	CheckBox cbSms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnService = (Button)findViewById(R.id.service_button);
        btnNewCode = (Button)findViewById(R.id.new_code_button);
        cbCall = (CheckBox)findViewById(R.id.check_call);
        cbSms = (CheckBox)findViewById(R.id.check_sms);
        
        btnService.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	setDetectEnabled(!detectEnabled);
            }
        });
        btnNewCode.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				MainActivity.this.getSharedPreferences("com.chrome.james", Context.MODE_PRIVATE).edit().putString("userId", null).commit();
				Intent i = new Intent(MainActivity.this, CodeActivity.class);
	    		startActivity(i);
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
    	this.getSharedPreferences("com.chrome.james", Context.MODE_PRIVATE).edit().putBoolean("call", cbCall.isChecked()).commit();
    	this.getSharedPreferences("com.chrome.james", Context.MODE_PRIVATE).edit().putBoolean("sms", cbSms.isChecked()).commit();
    	
        Intent intent = new Intent(this, ChromeMonitorService.class);
        //Intent notificationIntent = new Intent(this, MonitorService.class);
    	if (enable) {
    			startService(intent);
//    			startService(notificationIntent);
            btnService.setText("Stop");
    	}
    	else {
    		stopService(intent);
//    		stopService(notificationIntent);
    		btnService.setText("Start");
    	}
    }
}
