/**
 * Copyright 2010 Kurtis Nusbaum
 *
 * This file is part of LinkSchedule.  
 *
 * LinkSchedule is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU General Public 
 * License as published by the Free Software Foundation, either version 2 of the 
 * License, or (at your option) any later version.  
 *
 * LinkSchedule is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.  You should have received a copy of the GNU  General 
 * Public License along with LinkSchedule. If not, see 
 * http://www.gnu.org/licenses/.
 */

package org.klnusbaum.linkschedule;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{

	public static final int ALARM_NOTIFICATION_ID = 0;
	public static final String BROADCAST_BUS_STOP_ALARM = "BROADCAST_BUS_STOP_ALARM";

	public AlarmReceiver(){
		super();
	}
			
	@Override
	public void onReceive(Context context, Intent intent){
		if(intent.getAction().equals(BROADCAST_BUS_STOP_ALARM)){
			String tickerText = context.getString(R.string.bus_coming);
			long when = System.currentTimeMillis();
			String contentTitle = context.getString(R.string.bus_coming);
			String contentText = context.getString(R.string.bus_here_shortly);
		
			Intent busStopIntent = new Intent(context, SingleStopActivity.class);
			busStopIntent.putExtra(
				BusStopActivity.EXTRA_STOPNAME, 
				intent.getStringExtra(BusStopActivity.EXTRA_STOPNAME));
			PendingIntent contentIntent = PendingIntent.getActivity(
				context, 0, busStopIntent, 0);
			
			Notification busNotify = new Notification(
				android.R.drawable.stat_sys_warning,
				tickerText,
				when);
			busNotify.defaults |= Notification.DEFAULT_ALL;
			busNotify.flags |= Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;
			busNotify.setLatestEventInfo(
				context, contentTitle, contentText, contentIntent);
		
			NotificationManager notifyManager = 
				(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			notifyManager.notify(ALARM_NOTIFICATION_ID, busNotify);
		}
	}
}