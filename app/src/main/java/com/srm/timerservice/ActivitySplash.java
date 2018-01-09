package com.srm.timerservice;

import java.util.Timer;
import java.util.TimerTask;

import com.srm.timerservice.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class ActivitySplash extends Activity {

	private int time=0;
	private Timer timeTimer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		startTimeTimer();
	}

	public void startTimeTimer()
	{
		timeTimer = new Timer();
		timeTimer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						time = time + 1;
					if (time == 3)
						{
						Intent in=new Intent(ActivitySplash.this,	ActivityMain.class);
						startActivity(in);
						timeTimer.cancel();
						finish();
						}

					}

				});
			}
		}, 0, 1000);

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash, menu);
		return true;
	}

}
