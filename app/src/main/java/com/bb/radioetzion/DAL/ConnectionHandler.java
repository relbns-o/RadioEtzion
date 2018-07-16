package com.bb.radioetzion.DAL;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionHandler extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... url) {
        // open HTTP URL Connection to our desired URL
        HttpURLConnection connection = null;
        String jsonString = "";
        try {
            connection = (HttpURLConnection) new URL(url[0]).openConnection();
            BufferedReader buf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = buf.readLine()) != null) {
                jsonString += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        // return the json string to for post execute
        return jsonString;
    }


    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        // passing the json string to main activity by AsyncResponse Interface
        delegate.processFinish(jsonString);
    }
}


