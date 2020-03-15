package com.example.unabuku.Services;

import android.os.AsyncTask;

import com.example.unabuku.HomeFragment;

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

public class DataFetcher extends AsyncTask<Void,Void,Void> {
        String data = "";
        String allParsedData = "";
        String singleParsedData = "";
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/todos");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line !=null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                singleParsedData = "ID:"+ jsonObject.get("id")+"\n"+
                        "Title:"+ jsonObject.get("title")+"\n"+
                        "Completed:"+ jsonObject.get("completed")+"\n";
                allParsedData = allParsedData + singleParsedData +"\n";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        HomeFragment.data.setText(this.allParsedData);
    }
}
