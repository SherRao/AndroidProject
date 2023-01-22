package tech.sherrao.wlu.localify;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tech.sherrao.wlu.localify.misc.DatabaseHelper;
import tech.sherrao.wlu.localify.misc.Location;

public class FavouritesActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_favourites);

        db = new DatabaseHelper(this);
        RecyclerView recyclerView = super.findViewById(R.id.favouritesList);

        ArrayList<Location> locArray = db.getStoredFavourites();
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

        locArray.add(loc1);
        locArray.add(loc2);
        return locArray;
    }


}