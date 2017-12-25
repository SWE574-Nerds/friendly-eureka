package com.example.canma.eurekaswe.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by suzaneceada on 21.11.2017.
 */

public class Points {


   public Points(double lat, double longitude) {
      this.lat = lat;
      this.longitude = longitude;
   }

   public Double lat;

   @SerializedName("long")
   public Double longitude;

   public String toString() {

      return Double.toString(this.lat)+":"+Double.toString(this.longitude);




   }

}
