package com.chromemonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	boolean isRunning = false;
	private boolean detectEnabled;
	Button btnService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnService = (Button)findViewById(R.id.service_button);
        
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
    	if (enable) {
            startService(intent);
            btnService.setText("Stop");
    	}
    	else {
    		stopService(intent);
    		btnService.setText("Start");
    	}
    }
}
