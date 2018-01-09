package com.srm.timerservice;

import com.srm.timerservice.R;
import com.srm.timerservice.util.SavePrefrences;
import com.srm.timerservice.util.TimeTask;

import android.R.string;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySetMobile extends Activity implements OnClickListener {
	private Button btnStartTrack;
	private Button btnReset;
	private EditText etNumber;
	private EditText etTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_mobile);
		btnStartTrack = (Button) findViewById(R.id.btnTrackNumber);
		btnStartTrack.setOnClickListener(this);
		btnReset = (Button) findViewById(R.id.btnResetNumber);
		btnReset.setOnClickListener(this);
		etNumber = (EditText) findViewById(R.id.etMobileNumber);
		etTime = (EditText) findViewById(R.id.etTimeInterval);

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnTrackNumber:
			String number = etNumber.getText().toString();
			String time = etTime.getText().toString();
			if ((number.length() >= 10)
					&& (time.length() >= C.TIME_LENGTH && time.length() <= 3))

			{
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
				SavePrefrences.setString(C.NUMBER,
						getApplicationContext(), number);
				SavePrefrences.setString(C.TIME,
						getApplicationContext(), time);
				Intent intent = new Intent(this, TimeTask.class);

				startService(intent);
				Toast.makeText(getApplicationContext(),"Location Tracker Activated",
						Toast.LENGTH_SHORT).show();
//				Toast.makeText(getApplicationContext(), "Valid",
//						Toast.LENGTH_LONG).show();
			} else {
				if (number.length() < 10) {
					Toast.makeText(getApplicationContext(), "Number Not Valid",
							Toast.LENGTH_LONG).show();
				}
				if (time.length() < C.TIME_LENGTH || time.length() > 4) {
					Toast.makeText(getApplicationContext(),
							"Time Should be Greater then 10 sec",
							Toast.LENGTH_LONG).show();
				}
			}
			break;

		case R.id.btnResetNumber:
			etNumber.setText("");
			etTime.setText("");
			stopService(new Intent(ActivitySetMobile.this, TimeTask.class));

			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_set_mobile, menu);
		return true;
	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
	}

}
