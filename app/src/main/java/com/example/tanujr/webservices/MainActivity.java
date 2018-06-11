package com.example.tanujr.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanujr.webservices.network.CallWebService;
import com.example.tanujr.webservices.network.NetworkStatus;
import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.example.tanujr.webservices.network.OnWebServiceResult;

public class MainActivity extends AppCompatActivity implements OnWebServiceResult {

    // Api URL in which we shall receive result for Weather info.
    String url="http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=d7b900681c37193223281142bd919019";

    // Create Objects of TextView which shall be used in On Create Method to assign the reference.
    TextView countryTextView, minTempTextView, maxTempTextView, statusTextView, descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryTextView = (TextView) findViewById(R.id.countryNameTextView);  // Set Reference to Country TextView
        minTempTextView= (TextView) findViewById(R.id.minTempTextView); // Set Reference to Min Temperature Text View
        maxTempTextView = (TextView) findViewById(R.id.maxTempTextView); //Set Reference to Max Temperature Text View.
        statusTextView = (TextView) findViewById(R.id.statusTextView); // Set Reference to Stauts TextView
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView); //  Set Reference Description Text View.

        // Call Web Service Call Method.
        hitRequest();
    }

    public void hitRequest(){

        // Create Object of FormEncodingBuilder.
        FormEncodingBuilder parameters= new FormEncodingBuilder();
        // Set Parameters
        parameters.add("page" , "1");

        // Check Whether Device Connected to Internet
        if(NetworkStatus.getInstance(this).isOnline(this)) {
            // Create Object of Web Service Call Async Task
            CallWebService call = new CallWebService(this, url,parameters,CommonUtilities.SERVICE_TYPE.GET_DATA, this);
            // Execute Async Task
            call.execute();
        }else {
            // Show Error Message if no network connection available
            Toast.makeText(this, "No Network!!", Toast.LENGTH_SHORT).show();
        }
    }

    // Override Web Service Response and retrieve information of JSON and Show them in TextView
    @Override
    public void getWebResponse(String result, CommonUtilities.SERVICE_TYPE type) {
        // Toast Result object
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        try {
            // Create JSon OBject from result object.
            JSONObject obj= new JSONObject(result);
            // Create Json Array
            JSONArray arr= obj.getJSONArray("weather");

            // Read Json Object from Json Array of index 0
            JSONObject object= arr.getJSONObject(0);

            // Set Status TextView Text to json result main property
            statusTextView.setText("Status: "+object.getString("main"));
            // Set Description TextView Text to json result description property
            descriptionTextView.setText("Description: "+object.getString("description"));
            // Set Country Name TextView Text to json result name property
            countryTextView.setText("Country: "+obj.getString("name"));
            // Set minTemp TextView Text to json result min_temp property
            minTempTextView.setText("Min Temperature: "+obj.getJSONObject("main").getDouble("temp_min")+"");

            // Set maxTemp TextView Text to json result max_temp property
            maxTempTextView.setText("Max Temperature: "+obj.getJSONObject("main").getDouble("temp_max")+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
