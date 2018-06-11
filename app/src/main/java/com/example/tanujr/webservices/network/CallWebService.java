package com.example.tanujr.webservices.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tanujr.webservices.CommonUtilities;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


import java.util.concurrent.TimeUnit;

import okio.Buffer;

public class CallWebService extends AsyncTask<String, Void, String> {

    // Current Context
    Context context;
    // Result string
    String result = "";

    // FormEncoing Builder
    FormEncodingBuilder formBody;
    String url; // Current URL to call
    OnWebServiceResult onWebServiceResultListener; // WebService Result Listener
    CommonUtilities.SERVICE_TYPE Servicetype; // Service Type
    Request request; // Request Object

    // Getter and Setter for request object
    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }


    // Constructor to initialize the required objects.
    public CallWebService(Context context, String url, FormEncodingBuilder formBody, CommonUtilities.SERVICE_TYPE Servicetype, OnWebServiceResult onWebServiceResultListener) {
        this.context = context;
        this.formBody = formBody;
        this.url = url;
        this.onWebServiceResultListener = onWebServiceResultListener;
        this.Servicetype = Servicetype;
    }

    //
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("CallAddr", "service_type= " + Servicetype + " result= " + s);
        onWebServiceResultListener.getWebResponse(s, Servicetype);
    }

    protected String doInBackground(String... strings) {
        // Do In Background to retieve data from URl
        OkHttpClient client =new OkHttpClient();
        client.setConnectTimeout(120, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(120, TimeUnit.SECONDS); // socket timeout
        //formBody.add("platform", "android");

        RequestBody body = formBody.build(); // create Request Body
        Request request = new Request.Builder() // Create Request Object
                .url(url)
                //.post(body)
                .build();
        Log.e("CallAddr " + Servicetype, "url= " + url + " params= " + bodyToString(request));
        try {
            Response response = client.newCall(request).execute(); // Get Response
            if (!response.isSuccessful()) { // if response success convert resopnse to string
                result = response.toString();
                if (result.equals("") || result.equals("null") || result.length() == 0) {
                    // CommonUtilities.showToast(context, "Something went wrong. Try later.");
                }
            } else {

            }
            result = response.body().string(); // return response body
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    // Convert Body to String to display toast
    private static String bodyToString(final Request request){
        try {
            final Request copy = request.newBuilder().build(); // Create Request Builder buffer of OkHTTp class
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer); // Write to Buffer
            return buffer.readUtf8(); // Return bufffer object.
        } catch (Exception e) {
            return "Error";
        }
    }
}