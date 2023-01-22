package tech.sherrao.wlu.localify;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import tech.sherrao.wlu.localify.databinding.ActivityToolbarBinding;
import tech.sherrao.wlu.localify.misc.DatabaseHelper;

public class ToolbarActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final float DEFAULT_ZOOM_LEVEL = 13f;
    private final String SHARED_PREFS_NAME = "tech.sherrao.wlu.localify";
    private final String SHARED_PREFS_ORIGINS_KEY = SHARED_PREFS_NAME + ".origin";

    private GoogleMap map;
    private DatabaseHelper db;
    private ArrayList<Marker> visibleMarkers;
    private HashMap<String, tech.sherrao.wlu.localify.misc.Location> visibleLocations;

    private String lastSelectedLocationId;
    private boolean addedLastSelectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityToolbarBinding binding = ActivityToolbarBinding.inflate(super.getLayoutInflater());
        super.setContentView(binding.getRoot());
        super.setSupportActionBar(binding.toolbar);

        SharedPreferences prefs = super.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        db = new DatabaseHelper(this);
        visibleMarkers = new ArrayList<>();
        visibleLocations = new HashMap<>();

        SupportMapFragment mapFragment = (SupportMapFragment) super.getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        binding.fab.setOnClickListener(view ->
                startActivity(new Intent(ToolbarActivity.this, FavouritesActivity.class))
        );

        binding.fab2.setOnClickListener(view -> {
            if(lastSelectedLocationId == null )
                Toast.makeText(this, "No location selected!", Toast.LENGTH_SHORT).show();

            else if(!addedLastSelectedLocation) {
                Log.i(this.getClass().getSimpleName(), "Last selected location=" + lastSelectedLocationId);
                tech.sherrao.wlu.localify.misc.Location loc = visibleLocations.get(lastSelectedLocationId);
                assert loc != null;

                db.saveFavourite(loc);
                addedLastSelectedLocation = true;
                Toast.makeText(this, "Added location to favourites!", Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(this, "Already added location to favourites!", Toast.LENGTH_SHORT).show();

        });

        LatLng loc = getLastBestKnownLocation();
        String origin = prefs.getString(SHARED_PREFS_ORIGINS_KEY, "indian");
        new NearbyLocationTask().execute(Double.toString(loc.latitude), Double.toString(loc.longitude),  origin);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.toolbar_menu_action_one:
                Log.d("Toolbar", "Option 1 selected");
                super.startActivity(new Intent(ToolbarActivity.this, HelpActivity.class));
                break;

            case R.id.toolbar_menu_action_two:
                Log.d("Toolbar", "Option 2 selected");
                super.startActivity(new Intent(ToolbarActivity.this, SettingsActivity.class));
                break;

            default:
                Log.d("Toolbar", "Default selected");
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        this.map = map;
        map.setOnMarkerClickListener(this);

        LatLng loc = getLastBestKnownLocation();
        map.addMarker(new MarkerOptions().position(loc).title("Your location"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, DEFAULT_ZOOM_LEVEL));
    }

    @SuppressLint({"InlinedApi", "MissingPermission"})
    private LatLng getLastBestKnownLocation() {
        LocationManager locationManager = (LocationManager) super.getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER);
        return new LatLng(loc.getLatitude(), loc.getLongitude());
    }

    private void addNearbyLocationsToMap(JSONObject data) {
        try {
            JSONArray arr = data.getJSONArray("results");
            for(int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String id = obj.getString("place_id");
                String name = obj.getString("name");
//                String iconUrl = obj.getString("icon");
                double lat = obj.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                double lng = obj.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                double rating = obj.getDouble("rating");
                String type = obj.getJSONArray("types").getString(0);

                Marker marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title(name + "(" + type + ")")
                );

                assert marker != null;
                marker.setTag(id);

                visibleMarkers.add(marker);
                visibleLocations.put(id, new tech.sherrao.wlu.localify.misc.Location(id, name, lat, lng, rating));
            }

        } catch(JSONException e) { e.printStackTrace(); }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        lastSelectedLocationId = (String) marker.getTag();
        addedLastSelectedLocation = false;
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    private class NearbyLocationTask extends AsyncTask<String, Integer, String> {

        public static final String API_KEY = "AIzaSyAqnjm7qA82V6UJJVM2HU7nIUBLdaC4dNU";
        public static final String BASE_QUERY_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
        public static final String QUERY_URL = BASE_QUERY_URL + "key=" + API_KEY +
                "&location=%s,%s" +
                "&radius=5000" +
                "&keyword=%s" +
                "&maxprice=2" +
                "&type=restaurant|cafe";

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(ToolbarActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i(this.getClass().getSimpleName(), "doInBackground called");
            Log.i(this.getClass().getSimpleName(), "Making API call w/ args: (" + strings[0] + "," + strings[1] + "," + strings[2] + ")");

            String formattedUrl = String.format(QUERY_URL, strings[0], strings[1], strings[2]);
            Log.i(this.getClass().getSimpleName(), "Using API Key w/ URL: " + formattedUrl );

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(formattedUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                try {
                    if (connection != null)
                        connection.disconnect();

                    if (reader != null)
                        reader.close();

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
            super.onPostExecute(result);
            Log.i(this.getClass().getSimpleName(), "onPostExecute() called!");

            if (pd.isShowing())
                pd.dismiss();

            try {
                addNearbyLocationsToMap(new JSONObject(new JSONTokener(result)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}