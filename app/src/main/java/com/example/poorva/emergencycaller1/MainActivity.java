package com.example.poorva.emergencycaller1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener,AsyncResponse {

    protected LocationManager locationManager;
    TextView txtLat;
    Button nextButton;
    String nextclassinput;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(nextclassinput!=null)
        {
        Intent intent = new Intent(MainActivity.this, TestClass.class);
        intent.putExtra("message", nextclassinput);
        startActivity(intent);
        }

        txtLat = (TextView) findViewById(R.id.id_textview);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(com.example.poorva.emergencycaller1.MainActivity.super.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(com.example.poorva.emergencycaller1.MainActivity.super.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.id_textview);
        if (flag == true)
            findHospitals(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude", "disable");
    }

    public void findHospitals(double latitude, double longitude) {
        //StringBuilder stringBuilder=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyC7KPNhWH55VmY9rdQ027Q_gGVAeZBhM5E");
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location=" + latitude + "," + longitude);
        stringBuilder.append("&radius=5000&keyword=hospitals&key=YOUR API KEY");

        flag = false;
        String url = stringBuilder.toString();
        GetNearByPlaces getNearByPlaces = new GetNearByPlaces(this);
        getNearByPlaces.delegate = this;
        getNearByPlaces.execute(this, url);
    }

    @Override
    public void processFinish(String output) {
        nextclassinput = output;
        txtLat.setText(output);
        Intent intent = new Intent(MainActivity.this, TestClass.class);
        intent.putExtra("message", nextclassinput);
        startActivity(intent);
    }
}
