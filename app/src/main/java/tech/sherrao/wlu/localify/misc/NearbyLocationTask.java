package tech.sherrao.wlu.localify.misc;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NearbyLocationTask extends AsyncTask<String, Integer, String> {

        public static final String API_KEY = "dcb4c99a5df9107c36f398032741e86c";
        public static final String BASE_QUERY_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
        public static final String QUERY_URL = BASE_QUERY_URL + API_KEY + "&mode=xml&units=metric";

        @Override
        protected String doInBackground(String... strings) {
            Log.i(this.getClass().getSimpleName(), "doInBackground called");
            Log.i(this.getClass().getSimpleName(), "Using API Key w/ URL: " + QUERY_URL);

            InputStream in = downloadUrl();
            return null;
        }

        @Override
        public void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    private InputStream downloadUrl() {
        try {
            URL url = new URL(QUERY_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            return conn.getInputStream();

        } catch(IOException e) { e.printStackTrace(); }

        return null;
    }

}
