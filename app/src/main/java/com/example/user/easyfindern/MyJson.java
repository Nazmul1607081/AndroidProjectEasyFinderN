package com.example.user.easyfindern;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 12/9/2018.
 */

public class MyJson extends AsyncTask<String,String,String> {
    TextView t1;
    String hospital_name="";
    MyJson(TextView t,String s)
    {
        t1=t;
        hospital_name=s;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;



        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();




        try {
            URL url = new URL( "https://api.myjson.com/bins/1cfl8u" );
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer(  );
            String line = "";

            StringBuffer lastBuffer = new StringBuffer(  );
            while((line=bufferedReader.readLine())!=null)
            {
                stringBuffer.append( line );
            }

            String name = "",address="",contact="";



            /////////////////////////////////////////////////////////
            String file = stringBuffer.toString();
            JSONObject fileObject = new JSONObject( file );
            JSONArray jsonArray = fileObject.getJSONArray( "hospital" );
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject arrayObject = jsonArray.getJSONObject( i );
                name = arrayObject.getString( "name" );
                address = arrayObject.getString( "address" );
                contact = arrayObject.getString( "contact" );
                Hospital hospital = new Hospital( name,address,contact );
                String id = mDatabase.push().getKey();
                mDatabase.child( "Hospital" ).child( id ).setValue( hospital );

                if(name.contains( hospital_name ))
                      lastBuffer.append( name+"\n"+address+"\n"+contact+"\n\n\n" );
            }




            return lastBuffer.toString();









           //   return lastBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }/* catch (JSONException e) {
                e.printStackTrace();
            } */ catch (JSONException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;


    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute( s );
        t1.setText( s );
        //t1.setText( "Bangladesh" );
    }

}
