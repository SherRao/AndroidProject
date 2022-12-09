package tech.sherrao.wlu.localify.misc;

import com.google.android.gms.maps.model.LatLng;

public class Location {

    public final String id;
    public final String name;
    public final LatLng latLong;
    public final double rating;

    /**
     * Represents a location on the map.
     *
     * @param id The Google Places API UUID.
     * @param name The name of the location.
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param rating The Google Maps rating for the location.
     */
    public Location(String id, String name, double latitude, double longitude, double rating) {
        this.id = id;
        this.name = name;
        this.latLong = new LatLng(latitude, longitude);
        this.rating = rating;
    }

}