package com.tochalumni.osat.osat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class notif extends Activity {

    //EditText etResponse;
    TextView not1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        // get reference to the views
       not1 = (TextView) findViewById(R.id.not1);

        if(isConnected()){
            new HttpAsyncTask().execute("http://192.168.1.7:8000/appone/give/");

        }
        else{
            Toast.makeText(getBaseContext(), "You are NOT connected!", Toast.LENGTH_LONG).show();
        }

        // call AsynTask to perform network operation on separate thread
    }
      //  tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

        // check if you are connected or not


    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            JSONArray json = null;
            try {
                json = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String a = null;
            String str = "";

            if (json != null) {
                for (int i = 0, size = json.length(); i < size; i++) {
                    JSONObject objectInArray = null;
                    try {
                        objectInArray = json.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject getObject = null;
                    try {
                        getObject = objectInArray.getJSONObject("fields");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        a = (String) getObject.get("noti");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    str+=a+"\n\n";

                }
                not1.setText(str);

            }

        }
        }
    }
