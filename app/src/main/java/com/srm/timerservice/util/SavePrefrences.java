package com.srm.timerservice.util;



import android.content.Context;
import android.content.SharedPreferences;

public class SavePrefrences
{
	private static SharedPreferences sp;

	private static String tracker="tracker";
	
	
	public static boolean getBoolean(String key,Context context)
	{
		sp = context.getSharedPreferences(tracker, Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}
	
	public static void setBoolean(String key,Context context,boolean value)
	{
		sp=context.getSharedPreferences(tracker, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
		
	}
	
	public static String getString(String key,Context context)
	{
		sp = context.getSharedPreferences(tracker, Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}
	
	public static void setString(String key,Context context,String value)
	{
		sp=context.getSharedPreferences(tracker, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
		
	}


}
