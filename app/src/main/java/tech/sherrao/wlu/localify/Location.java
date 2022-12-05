package tech.sherrao.wlu.localify;

import com.google.android.gms.maps.model.LatLng;

public class Location {

    public final String id;
    public final String name;
    public final LatLng latLong;
    public final float rating;

    public Location(String id, String name, double latitude, double longitude, float rating) {
        this.id = id;
        this.name = name;
        this.latLong = new LatLng(latitude, longitude);
        this.rating = rating;
    }

}