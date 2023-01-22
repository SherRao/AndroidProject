package tech.sherrao.wlu.localify.misc;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import tech.sherrao.wlu.localify.ToolbarActivity;

public class NearbyLocationTask extends AsyncTask<String, Integer, String> {

        public static final String API_KEY = "AIzaSyAqnjm7qA82V6UJJVM2HU7nIUBLdaC4dNU";
        public static final String BASE_QUERY_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
        public static final String QUERY_URL = BASE_QUERY_URL + "key=" + API_KEY +
                "&location=%s,%s" +
                "&radius=5000" +
                "&keyword=%s" +
                "&maxprice=2" +
                "&type=restaurant|cafe";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            ProgressDialog pd = new ProgressDialog(ToolbarActivity.this);
//            pd.setMessage("Please wait");
//            pd.setCancelable(false);
//            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i(this.getClass().getSimpleName(), "doInBackground called");
            Log.i(this.getClass().getSimpleName(), "Using API Key w/ URL: " + QUERY_URL );
            Log.i(this.getClass().getSimpleName(), "Making API call w/ args: (" + strings[0] + "," + strings[1] + "," + strings[2] + ")");

//            InputStream in = downloadUrl(strings);
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                String formattedUrl = String.format(QUERY_URL, strings[0], strings[1], strings[2]);
                URL url = new URL(formattedUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            Log.i(this.getClass().getSimpleName(), "onPostExecute() called!");
            super.onPostExecute(result);
        }

    private InputStream downloadUrl(String... args) {
        try {
            String formattedUrl = String.format(QUERY_URL, args[0], args[1], args[2]);
            URL url = new URL(formattedUrl);
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
