package sr.unasat.unabuku.Services;

import android.os.AsyncTask;

import sr.unasat.unabuku.HomeFragment;

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

public class DataFetcher extends AsyncTask<Void, Void, Void> {
    String data = "";
    String allParsedData = "";
    String singleParsedData = "";

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("http://limitless-wildwood-86411.herokuapp.com/api/books");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                singleParsedData = "id:" + jsonObject.get("id") + "\n" +
                        "title:" + jsonObject.get("title") + "\n" +
                        "author:" + jsonObject.get("author") + "\n";
                allParsedData = allParsedData + singleParsedData;
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
