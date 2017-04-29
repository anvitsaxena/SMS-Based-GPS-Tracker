package com.srm.timerservice;

import com.srm.timerservice.R;
import com.srm.timerservice.util.SavePrefrences;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ActivityMain extends Activity implements OnClickListener {

	private EditText etCreatePassword;
	private Button btnCreateEnter;
	private EditText etEnterPassword;
	private Button btnEnter;
	private LinearLayout llCreate;
	private LinearLayout llEnter;
	private String str;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		llCreate = (LinearLayout) findViewById(R.id.llCreatePassword);
		etCreatePassword = (EditText) findViewById(R.id.etCreatePassword);
		btnCreateEnter = (Button) findViewById(R.id.btnCreateEnter);
		btnCreateEnter.setOnClickListener(this);

		llEnter = (LinearLayout) findViewById(R.id.llEnterPassword);
		etEnterPassword = (EditText) findViewById(R.id.etEnterPassword);
		btnEnter = (Button) findViewById(R.id.btnEnter);
		btnEnter.setOnClickListener(this);


		if (SavePrefrences.getBoolean(C.ENTER_MODE,getApplicationContext())) {
			llEnter.setVisibility(View.VISIBLE);
			llCreate.setVisibility(View.GONE);
		} else {
			llEnter.setVisibility(View.GONE);
			llCreate.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnCreateEnter:
			str=etCreatePassword.getText().toString();
			if(str.length()>0)
			{
				SavePrefrences.setString(C.PASSWORD, getApplicationContext(), str);
				SavePrefrences.setBoolean(C.ENTER_MODE,getApplicationContext(),true);
				Intent intent = new Intent(ActivityMain.this,ActivitySetMobile.class);
				startActivity(intent);
				finish();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Please Create Password", Toast.LENGTH_LONG).show();
			}

			break;
		case R.id.btnEnter:
			str=etEnterPassword.getText().toString();
			if(str.length()>0)
			{
				String strP=SavePrefrences.getString(C.PASSWORD, getApplicationContext());
				if(str.equals(strP))
				{
					Intent intent = new Intent(ActivityMain.this,ActivitySetMobile.class);
					startActivity(intent);
					finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Enter Correct Password", Toast.LENGTH_LONG).show();
				}

				
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Please Create Password", Toast.LENGTH_LONG).show();
			}

			break;

		default:
			break;
		}

	}
}
