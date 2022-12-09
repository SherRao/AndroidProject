package tech.sherrao.wlu.localify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button deleteButton;
    protected static final String ACTIVITY_NAME = "FavouritesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_favourites);

        recyclerView = (RecyclerView) findViewById(R.id.favouritesList);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        ArrayList<Location> locArray = addToList();

        LocationAdapter adapter = new LocationAdapter(this, locArray);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private ArrayList<Location> addToList(){
        /**
         * public final String id;
         *     public final String name;
         *     public final LatLng latLong;
         *     public final float rating;
         */
        ArrayList<Location> locArray = new ArrayList<>();
        Location loc1 = new Location("WLU", "Wilfrid Laurier University",43.4738,80.5275, 2);
        Location loc2 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
        Location loc3 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
        Location loc4 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
        Location loc5 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
        Location loc6 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
        Location loc7 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
        Location loc8 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
        Location loc9 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);

        locArray.add(loc1);
        locArray.add(loc2);

        return locArray;
    }


}