package tech.sherrao.wlu.localify.misc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "favourites";
    public static final int VERSION_NUM = 1;
    public static final String ID_COLUMN = "Id";
    public static final String NAME_COLUMN = "Name";
    public static final String LAT_COLUMN = "Latitude";
    public static final String LONG_COLUMN = "Longitude";
    public static final String RATING_COLUMN = "Rating";

    private SQLiteDatabase readDb;
    private SQLiteDatabase writeDb;
    private Cursor cursor;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION_NUM);
        this.readDb = super.getReadableDatabase();
        this.writeDb = super.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().getSimpleName(), "Calling onCreate()");

        String query = String.format("CREATE TABLE %s (%s VARCHAR(255), %s VARCHAR(255), %s FLOAT, %s FLOAT, %s FLOAT)",
                TABLE_NAME, ID_COLUMN, NAME_COLUMN, LAT_COLUMN, LONG_COLUMN, RATING_COLUMN);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(this.getClass().getSimpleName(), "Calling onUpgrade(), oldVersion=" + oldVersion + ", newVersion=" + newVersion);

        String query = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(query);
        this.onCreate(db);
    }

    public void saveFavourite(Location loc) {
        this.saveFavourite(loc.id, loc.name, loc.latLong.latitude, loc.latLong.longitude, loc.rating);
    }

    public void saveFavourite(String id, String name, double latitude, double longitude, double rating) {
        ContentValues values = new ContentValues();
        values.put(ID_COLUMN, id);
        values.put(NAME_COLUMN, name);
        values.put(LAT_COLUMN, latitude);
        values.put(LONG_COLUMN, longitude);
        values.put(RATING_COLUMN, rating);

        this.writeDb.insert(TABLE_NAME, null, values);
    }

    @SuppressLint("Range")
    public List<Location> getStoredFavourites() {
        List<Location> result = new ArrayList<>();
        cursor = this.readDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.getColumnIndex(ID_COLUMN) == -1)
            return result;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String id = cursor.getString(cursor.getColumnIndex(ID_COLUMN));
            String name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
            double latitude = cursor.getDouble(cursor.getColumnIndex(LAT_COLUMN));
            double longitude = cursor.getDouble(cursor.getColumnIndex(LONG_COLUMN));
            double rating = cursor.getDouble(cursor.getColumnIndex(RATING_COLUMN));

            result.add(new Location(id, name, latitude, longitude, rating));
            cursor.moveToNext();
            Log.i(this.getClass().getSimpleName(), "Retrieving location with ID: \"" + id + "\"");
        }

        cursor.close();
        return result;
    }

    public int getItemId(int position) {
        if(cursor == null)
            return -1;

        cursor.moveToPosition(position);
        int columnIndex = cursor.getColumnIndex(ID_COLUMN);
        if(columnIndex < 0)
            return -1;

        return cursor.getInt(columnIndex);
    }

    public void onDestroy() {
        Log.i(this.getClass().getSimpleName(), "Calling onDestroy()");
        super.close();
    }
}
