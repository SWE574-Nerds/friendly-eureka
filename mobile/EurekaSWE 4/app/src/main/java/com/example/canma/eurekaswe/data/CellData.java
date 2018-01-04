package com.example.canma.eurekaswe.data;

import java.io.Serializable;

public class CellData implements Serializable {
   public String name;
   public String description;
   public String image;
   public CategoryFormat[] tags;
   public int listoryId;
   public Owner owner;
   public Time time;
   public String category;
   public String createdAt;


   public int getListoryId() {
      return listoryId;
   }

   @Override
   public boolean equals(Object obj) {

      if(obj instanceof CellData){


         return ((CellData) obj).listoryId==getListoryId();


      }else {
         return false;
      }


   }
}





