package com.example.poorva.emergencycaller1;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetNearByPlaces extends AsyncTask<Object,String,String> {

    public AsyncResponse delegate = null;
    TextView txtLat;
    String url;
    InputStream is;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    String data;
    Context c;
    String name;
    String placeID;

    GetNearByPlaces(Context c)
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

        try {
            JSONObject parentObject=new JSONObject(s);
            JSONArray resultsArray=parentObject.getJSONArray("results");

                JSONObject jsonObject=resultsArray.getJSONObject(0);
                name=jsonObject.getString("name");
                placeID=jsonObject.getString("place_id");


                if (placeID == null) {
                    //Toast.makeText(this,"Unable to fetch",Toast.LENGTH_SHORT);
                    Toast.makeText(c, "Unable to Fetch", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(c, "Success!", Toast.LENGTH_SHORT).show();

                    delegate.processFinish(placeID);
//                    StringBuilder sb=new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
//                    sb.append("placeid="+placeID);
//                    sb.append("&fields=name,rating,formatted_phone_number&key=AIzaSyDyL9KVP-Ciro3_o72l4VbVdibDq2KloNI");
//                    String url1=sb.toString();
//
//                    ForPhoneNumber forPhoneNumber=new ForPhoneNumber();
//                    forPhoneNumber.getPhoneNumber(c,url1);

                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }

}
