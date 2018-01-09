package com.srm.timerservice.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public abstract class RestInvoke
{
	public static String invoke(String restUrl)
	{
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(restUrl);
		try
		{

			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null)
			{
				InputStream in = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuffer temp = new StringBuffer();
				String currentLine = null;
				while ((currentLine = reader.readLine()) != null)
				{
					temp.append(currentLine);
				}
				result = temp.toString();
				in.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return (result);
	}
}
