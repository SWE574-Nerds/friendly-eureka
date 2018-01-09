package com.example.canma.eurekaswe.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AltCellData {

   @SerializedName("@context")
   public String context;
   public String id;
   public String type;
   public Creator creator;
   public List<Body> body = null;
   public List<Selector> selector = null;
   public String target;
}


