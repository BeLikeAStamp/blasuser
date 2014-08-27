package com.belikeastamp.blasuser.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.SparseArray;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.MainActivity;

public class MessageReceiver extends BroadcastReceiver {

	private final String DWBC = "[DWBC]";

	private final static int PROTO = 0;
	private final static int PROJET = 1;
	private final static int REAL = 2;

	private final static int ACCEPT = 0;
	private final static int INPROGRESS = 1;
	private final static int DISPO = 2;

	private static final  SparseArray<String> part1 = new SparseArray<String>();
	private static final  SparseArray<String> part2 = new SparseArray<String>();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		setSparseArrays(context);
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, MainActivity.class), 0);

		Bundle pudsBundle = intent.getExtras();
		Object[] pdus = (Object[]) pudsBundle.get("pdus");
		SmsMessage messages =SmsMessage.createFromPdu((byte[]) pdus[0]);    

		if(messages.getMessageBody().contains(DWBC)) { 
			abortBroadcast();
			// Quel type de message ? proto ou realisation ?
			String[] parts = messages.getMessageBody().split(";");
			String content = context.getResources().getString(R.string.notif, part1.get(Integer.valueOf(parts[1])), 
					part2.get(Integer.valueOf(parts[2])), parts[3]);

			// generation notification
			NotificationCompat.Builder mBuilder =
					new NotificationCompat.Builder(context)
			.setSmallIcon(R.drawable.ic_logodontworry)
			.setContentTitle(context.getResources().getString(R.string.app_name))
			.setContentText(content);
			mBuilder.setContentIntent(contentIntent);
			mBuilder.setDefaults(Notification.DEFAULT_SOUND);
			mBuilder.setAutoCancel(true);
			NotificationManager mNotificationManager =
					(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(1, mBuilder.build());
		} 
	}
	
	
	private void setSparseArrays(Context context) {
		
		Resources res = context.getResources();
		part1.put(PROTO, res.getString(R.string.notif_proto));
		part1.put(PROJET, res.getString(R.string.notif_proj));
		part1.put(REAL, res.getString(R.string.notif_real));
		
		part2.put(DISPO, res.getString(R.string.notif_dispo));
		part2.put(INPROGRESS, res.getString(R.string.notif_inprogress));
		part2.put(ACCEPT, res.getString(R.string.notif_accept));
		
	}

}
