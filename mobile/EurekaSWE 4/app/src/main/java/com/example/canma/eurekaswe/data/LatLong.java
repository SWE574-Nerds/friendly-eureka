package com.example.canma.eurekaswe.data;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

/**
 * Created by suzaneceada on 12.12.2017.
 */

public class LatLong {
   public Markers marker;

    public LatLong(Markers marker, Polylines polylines) {
        this.marker = marker;
        this.polylines = polylines;
    }

    public Polylines polylines;


}
