package com.example.canma.eurekaswe.data;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by suzaneceada on 21.11.2017.
 */

public class Markers {


    public Markers(double lat, double longitude, double mag, String name, String color) {
        this.lat = lat;
        this.longitude = longitude;
        this.mag = mag;
        this.name = name;
        this.color = color;
    }

    public Double lat;
    public Double longitude;
    public Double mag;
    public String name;
    public String color;



    @Override
    public String toString() {
        return name;
    }
}
