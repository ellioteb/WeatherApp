/**
 * Created by Elliot on 31/03/2017.
 */
package elliot_boileau.myweatherapp;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.speech.tts.Voice;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class dataDownload extends AsyncTask<String,Void,String> {




    @Override
    protected String doInBackground(String... urls) {

        String result ="";
        URL url;
        HttpURLConnection urlConnection = null;


        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader isR = new InputStreamReader(in);
            int data = isR.read();
            while (data != -1){
                char current = (char) data;
                result += current;
                data = isR.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result){
        System.out.println(result);


        try {
            JSONObject jsonObject = new JSONObject(result);
            String weatherInfo = jsonObject.getString("Weather");
            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            Double temp = Double.parseDouble(weatherData.getString("temp"));

            String placeName = jsonObject.getString("Name");

            MainActivity.tempTextView.setText(String.valueOf(temp));
            MainActivity.placeTextView.setText(String.valueOf(placeName));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//9c3f570f69afa7651fa40846233996cd   api key

}
