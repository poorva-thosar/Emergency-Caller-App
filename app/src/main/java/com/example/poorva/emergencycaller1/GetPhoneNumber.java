package com.example.poorva.emergencycaller1;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetPhoneNumber extends AsyncTask<Object,String,String> {

    public AsyncResponse1 delegate = null;
    TextView txtLat;
    String url;
    InputStream is;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    String data;
    Context c;

    GetPhoneNumber(Context c)
    {
        this.c=c;
    }

    @Override
    protected String doInBackground(Object... params) {
        url=(String)params[1];
        //c=params[0];
        try
        {
            URL myurl=new URL(url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) myurl.openConnection();
            httpURLConnection.connect();
            is=httpURLConnection.getInputStream();
            bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line="";
            stringBuilder=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }
            data=stringBuilder.toString();
            return data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(c, "Inside postExecute", Toast.LENGTH_LONG).show();

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject object = jsonObject.getJSONObject("result");
            String formattedPhoneNumber=object.getString("formatted_phone_number");
            //Toast.makeText(c, "Inside postExecute 1", Toast.LENGTH_LONG).show();

            if (formattedPhoneNumber == null) {
                Toast.makeText(c, "Something Wrong with Phone", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(c, "Phone Number Found", Toast.LENGTH_SHORT).show();
                delegate.processFinish(formattedPhoneNumber);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
