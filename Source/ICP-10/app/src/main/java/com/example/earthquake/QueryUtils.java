package com.example.earthquake;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData2(String requestUrl) throws IOException {
        //  URL object to store the url for a given string
        URL url = null;
        // A string to store the response obtained from rest call in the form of string
        String jsonResponse = null;


        //TODO: 1. Create a URL from the requestUrl string and make a GET request to it
        url = new URL(requestUrl);
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();

                //TODO: 2. Read from the Url Connection and store it as a string(jsonResponse)
                StringBuilder output = new StringBuilder();
                if (inputStream != null) {
                    InputStreamReader inputSR = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                    BufferedReader reader = new BufferedReader(inputSR);
                    String line = reader.readLine();
                    while (line != null) {
                        output.append(line);
                        line = reader.readLine();
                    }
                }
                jsonResponse = output.toString();

            } else {
                Log.e(LOG_TAG, "Connection response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Cannot get JSON object.");
        }


        /*TODO: 3. Parse the jsonResponse string obtained in step 2 above into JSONObject to extract the values of
                        "mag","place","time","url"for every earth quake and create corresponding Earthquake objects with them
                        Add each earthquake object to the list(earthquakes) and return it.*/

        // An empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        try{
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONArray arrayJSONObj = jsonObj.getJSONArray("features");

            for (int counter = 0; counter < arrayJSONObj.length(); counter++){
                JSONObject earthquakeObj = arrayJSONObj.getJSONObject(counter);
                JSONObject propertiesObj = earthquakeObj.getJSONObject("properties");
                double magnitude = propertiesObj.getDouble("mag");
                String place = propertiesObj.getString("place");
                long time = propertiesObj.getLong("time");
                String urlAddress = propertiesObj.getString("url");

                earthquakes.add(new Earthquake(magnitude, place, time, urlAddress));
            }
        }catch (JSONException e){
            Log.e("QueryUtils", "Parsing problem", e);
        }
        assert urlConnection != null;
        urlConnection.disconnect();
        assert inputStream != null;
        inputStream.close();

        // Return the list of earthquakes
        return earthquakes;
    }
}
