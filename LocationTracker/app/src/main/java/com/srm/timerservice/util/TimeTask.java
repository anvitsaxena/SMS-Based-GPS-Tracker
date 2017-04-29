package com.srm.timerservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.srm.timerservice.C;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class TimeTask extends Service {
	// constant
	public static long notifyTime = 2 * 60 * 1000;
	// run on another Thread to avoid crash
	private Handler mHandler = new Handler();
	// timer handling
	private Timer mTimer = null;
	private TimeDisplayTimerTask timer;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		// cancel if already existed
		if (mTimer != null) {
			mTimer.cancel();
		} else {
			// recreate new
			mTimer = new Timer();
		}
		notifyTime = Integer.parseInt(SavePrefrences.getString(C.TIME,
				getApplicationContext()));
		notifyTime = notifyTime * 1000;
		// schedule task
		timer = new TimeDisplayTimerTask();
		mTimer.scheduleAtFixedRate(timer, 0, notifyTime);
	}

	class TimeDisplayTimerTask extends TimerTask {

		@Override
		public void run() {
			// run on another thread
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// display toast
					
					new GetGPSLocation(getApplicationContext());
				}

			});
		}

		private String getDateTime() {
			// get date time in custom format
			SimpleDateFormat sdf = new SimpleDateFormat(
					"[yyyy/MM/dd - HH:mm:ss]");
			return sdf.format(new Date());
		}
	}

	@Override
	public void onDestroy() {
		if (timer != null)
		{
			timer.cancel();
			timer=null;
			Toast.makeText(getApplicationContext(),"Location Tracker Deactivated",
					Toast.LENGTH_SHORT).show();
		}
		super.onDestroy();
	}

}