package br.com.gushiwho.james;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import br.com.gushiwho.james.util.ApplicationHttpClient;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class SmsReceiver extends BroadcastReceiver {
	

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();

			Object messages[] = (Object[]) bundle.get("pdus");
			SmsMessage smsMessage[] = new SmsMessage[messages.length];
			for (int n = 0; n < messages.length; n++) {
				smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
			}
			RequestParams params = new RequestParams();
			String msg = "Sms from " + smsMessage[0].getDisplayOriginatingAddress() + ": " +  smsMessage[0].getMessageBody();
			msg.replace(" ", "%20");
			params.put("notification", msg);
			params.put("id", context.getSharedPreferences("com.chrome.james", Context.MODE_PRIVATE).getString("userId", null));
			ApplicationHttpClient.get("send_notification", params, new AsyncHttpResponseHandler() {
				
				public void onSuccess(final String response) {
				}
				
				public void onFailure(Throwable e) {
					e.printStackTrace();
				}
			});
			System.out.println(msg);
		}
	
}
