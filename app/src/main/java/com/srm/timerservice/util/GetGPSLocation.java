package com.srm.timerservice.util;

import org.json.JSONArray;
import org.json.JSONObject;

import com.srm.timerservice.C;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class GetGPSLocation implements LocationListener {
	private final Context mContext;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	Location location = null;
	double latitude;
	double longitude;

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	protected LocationManager locationManager;
	private Location m_Location;
	private String address;
	private String number;

	public GetGPSLocation(Context context) {
		this.mContext = context;
		
		m_Location = getLocation();
		if(m_Location!=null)
		{
		getLocation(String.valueOf(m_Location.getLatitude()),
				String.valueOf(m_Location.getLongitude()));

		System.out.println("getLocation():" + getLocation());
		}
		else
			
		{
			Toast.makeText(mContext, "GPS and Network Not Available. Please turn on GPS and N/W", Toast.LENGTH_LONG).show();
		}

	}

	void getLocation(String lat, String lon) {
		String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
				+ lat + "," + lon + "&sensor=true";
		String res = RestInvoke.invoke(url);

		try {

			if (res != null && !res.equalsIgnoreCase("")) {
				JSONObject jObject = new JSONObject(res);
				// if (jObject.getString("status").equalsIgnoreCase("ok")) {
				JSONArray jArray = jObject.getJSONArray("results");
				if (jArray.length() > 0) {
					JSONObject geo = jArray.getJSONObject(0);
					address = geo.getString("formatted_address");
					// Log.e("address", address);
//					Toast.makeText(mContext, " Address : " + address,
//							Toast.LENGTH_LONG).show();
					sendSms(address);
				}
			}
		} catch (Exception e) {

		}
	}

	public void sendSms(String Address) {
		SmsManager sms = SmsManager.getDefault();
		try {
			String msg = "My Current location is " + address + ",  Latitude : "+m_Location.getLatitude() +" Logitude :"+m_Location.getLongitude();
			sms.sendTextMessage(SavePrefrences.getString(C.NUMBER, mContext), null, msg, null, null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(Context.LOCATION_SERVICE);

			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {

			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network Enabled");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GetGPSLocation.this);
		}
	}

	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}

		return latitude;
	}

	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}

		return longitude;
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}