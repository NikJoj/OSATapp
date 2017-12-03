package com.tochalumni.osat.osat;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class alumniActivity extends AppCompatActivity implements View.OnClickListener{


    EditText fname,lname,pass,stud,insti,phone,email;
    Button submitbtn;

    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);

//        View view1 = findViewById(R.id.layout1);

  //      View view2 = view1.findViewById(R.id.layout2);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        pass = (EditText) findViewById(R.id.pass);
        stud = (EditText) findViewById(R.id.stud);
        insti = (EditText) findViewById(R.id.insti);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
       submitbtn = (Button) findViewById(R.id.submitbtn);

      submitbtn.setOnClickListener(this);

    }


    public static String POST(String url, Person person){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("fname", person.getFname());
            jsonObject.accumulate("lname", person.getLname());
            jsonObject.accumulate("passo", person.getPass());
            jsonObject.accumulate("stud", person.getStud());
            jsonObject.accumulate("insti", person.getInsti());
            jsonObject.accumulate("phone", person.getPhone());
            jsonObject.accumulate("email", person.getEmail());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
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

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.submitbtn:
                if(isConnected()){
                    if(!validate())
                        Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                        // call AsynTask to perform network operation on separate thread
                    else
                        new HttpAsyncTask().execute("http://139.59.65.12/json/");
                    //new HttpAsyncTask().execute("http://192.168.1.3:8000/appone/data/");
                }
                else{
                    Toast.makeText(getBaseContext(), "You are NOT connected!", Toast.LENGTH_LONG).show();
                }


                break;
        }

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            person = new Person();
            person.setFname(fname.getText().toString());
            person.setLname(lname.getText().toString());
            person.setPass(pass.getText().toString());
            person.setStud(stud.getText().toString());
            person.setInsti(insti.getText().toString());
            person.setPhone(phone.getText().toString());
            person.setEmail(email.getText().toString());


            return POST(urls[0],person);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            Handler handler3 = new Handler();
            handler3.postDelayed(new Runnable(){
                public void run() {
                    Intent intent = new Intent(alumniActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }
            }, 600);
        }
    }

    private boolean validate(){
        if(fname.getText().toString().trim().equals(""))
            return false;
        else if(lname.getText().toString().trim().equals(""))
            return false;
        else if(pass.getText().toString().trim().equals(""))
            return false;
        else if(stud.getText().toString().trim().equals(""))
            return false;
        else if(insti.getText().toString().trim().equals(""))
            return false;
        else if(phone.getText().toString().trim().equals(""))
            return false;
        else if(email.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }




}
