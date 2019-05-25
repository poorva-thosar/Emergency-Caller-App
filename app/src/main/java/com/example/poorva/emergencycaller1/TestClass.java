package com.example.poorva.emergencycaller1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TestClass extends AppCompatActivity implements AsyncResponse1 {

    TextView textView;
    TelephonyManager mTelephonyManager;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    Button emergencyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneno);
        textView = findViewById(R.id.id_textview1);
        Bundle bundle = getIntent().getExtras();
        String output1 = bundle.getString("message");
        
/////////////////////////
        emergencyButton = (Button) findViewById(R.id.callbutton);
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(output));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

/////////////////////////
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        sb.append("placeid=" + output1);
        sb.append("&fields=name,rating,formatted_phone_number&key=YOUR API KEY");
        String url1 = sb.toString();

        GetPhoneNumber getPhoneNumber = new GetPhoneNumber(this);
        getPhoneNumber.delegate = this;
        getPhoneNumber.execute(this, url1);
     }

    /////////////////////////
    @Override
    public void processFinish(String output) {
        Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        textView.setText(output);
        }

}
