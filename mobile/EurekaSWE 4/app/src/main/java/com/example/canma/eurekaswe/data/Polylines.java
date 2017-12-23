package com.example.canma.eurekaswe.data;

import java.util.ArrayList;

/**
 * Created by suzaneceada on 21.11.2017.
 */

public class Polylines {


    public String name;
    public String color;
    public ArrayList<Points> points;

    public Polylines(ArrayList<Points> points, String color, String name) {
        this.name = name;
        this.color = color;
        this.points = points;

    }


    @Override
    public String toString() {
        return name;
    }
}
