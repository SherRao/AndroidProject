package tech.sherrao.wlu.localify;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tech.sherrao.wlu.localify.misc.DatabaseHelper;
import tech.sherrao.wlu.localify.misc.Location;

public class FavouritesActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_favourites);

        db = new DatabaseHelper(this);
        recyclerView = super.findViewById(R.id.favouritesList);
//        deleteButton = super.findViewById(R.id.deleteButton);

        ArrayList<Location> locArray = addToList();
        LocationAdapter adapter = new LocationAdapter(this, locArray);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.onDestroy();
    }

    private ArrayList<Location> addToList(){
        ArrayList<Location> locArray = new ArrayList<>();
        Location loc1 = new Location("WLU", "Wilfrid Laurier University",43.4738,80.5275, 2);
        Location loc2 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
//        Location loc3 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
//        Location loc4 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
//        Location loc5 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
//        Location loc6 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
//        Location loc7 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
//        Location loc8 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);
//        Location loc9 = new Location("UW", "Waterloo University",43.4723,80.5449, 2);

        locArray.add(loc1);
        locArray.add(loc2);
        return locArray;
    }


}