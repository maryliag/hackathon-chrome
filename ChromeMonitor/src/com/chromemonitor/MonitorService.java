package com.chromemonitor;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;

public class MonitorService extends AccessibilityService {
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
	    final int eventType = event.getEventType();
	    switch(eventType) {
	        case AccessibilityEvent.TYPE_ANNOUNCEMENT:
	        	System.out.println("Announcement");
	            break;
	        case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
	        	System.out.println("estado da notificacao mudou");
	            break;
	        case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
	        	System.out.println("window content changed");
	        	break;
	        case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
	        	System.out.println("window state changed");
	        	break;
	        default: 
	        	System.out.println("accessibility eveeeent");
	    }
	}
	
	@Override
	protected void onServiceConnected() {
	    AccessibilityServiceInfo info = new AccessibilityServiceInfo();
	    info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
	    info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
	    info.notificationTimeout = 100;
	    setServiceInfo(info);
	}

    @Override
    public void onInterrupt() {
    	System.out.println("parooooo");
    }
}
