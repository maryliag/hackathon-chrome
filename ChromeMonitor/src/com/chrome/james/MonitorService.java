package com.chrome.james;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;

public class MonitorService extends AccessibilityService {
	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("iniciouuu");
	}
	
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
	    final int eventType = event.getEventType();
	    System.out.println("eventooooo >> " + event.getEventType() + " > " + String.valueOf(event.getPackageName()));
	    switch(eventType) {
	        case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
	        	System.out.println("estado da notificacao mudou " + event.getContentDescription());
	            break;
	        default : 
	        	System.out.println("algum evento " + event.getEventType());
	    }
	}
	
	@Override
	protected void onServiceConnected() {
		System.out.println("conectado");
	    AccessibilityServiceInfo info = new AccessibilityServiceInfo();
	    info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
	    info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
	    info.notificationTimeout = 100;
	    setServiceInfo(info);
	}

	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub
		
	}

}
