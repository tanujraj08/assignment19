package com.example.tanujr.webservices.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {

    // Crate Object of NetworkStatus Class
    private static NetworkStatus networkStatus = new NetworkStatus();

    //Current Context
    static Context context;

    // Create Object of connectivity Manager
    ConnectivityManager connectivityManager;

    // Connection Status True or false
    boolean connected = false;

    // NetworkStatus Class Constructor
    public static NetworkStatus getInstance(Context ctx){
        context = ctx;
        return networkStatus;
    }

    // Check whether the device online or not
    public boolean isOnline(Context con){
        try{

            // Set ConnectiviyManger object by retriving System Service
            connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            // Set Available NetworkInfo by connectivity Manager Object
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            // Validate whether network connected or not, if connected set connected property to true.
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();

            // Return Result
            return connected;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        // Not Connected.
        return connected;
    }
}

