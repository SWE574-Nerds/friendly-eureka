package com.example.canma.eurekaswe.data;

import android.nfc.Tag;

import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by suzaneceada on 3.01.2018.
 */

public class DetailResponse {

    public String name;
    public String description;
    public String image;
    public Integer listoryId;
    public List<Polylines> polylines = null;
    public List<Markers> markers = null;
    public Owner owner;
    public Time time;
    public List<CategoryFormat> tags = null;
    public String createdAt;
}
