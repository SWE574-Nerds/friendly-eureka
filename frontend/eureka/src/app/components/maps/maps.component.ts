import { Marker } from './Marker';
import { Polyline, Point } from './Polyline';
import { NgModule, Component ,OnInit, Input} from '@angular/core';
import { MouseEvent } from '@agm/core/services/google-maps-types';

@Component({
  selector: 'maps',
  styleUrls: ['./maps.component.css'],
  templateUrl: './maps.component.html'
})
export class MapsComponent implements OnInit{
  lat: number = 41.0027927;
  lng: number = 29.0157484;

  @Input() config = {
    editable: false,
    markers: [],
    polylines: []
  };

  private currentlyEditingLine: Polyline;


  ngOnInit(){
    if (this.config.markers.length > 0)
    {
      var lat = 0;
      var long = 0;
      this.config.markers.forEach((marker)=>{ lat += marker.lat; long += marker.long  });
      lat /= this.config.markers.length;
      long /= this.config.markers.length;

      this.lat = lat;
      this.lng = long;
    }
  }

  onCenterChange(whatever, marker){
    marker.lat = whatever.lat;
    marker.long = whatever.lng;
  }

  addNewCircle(){

    console.log(this.config);

    var newMarker : Marker = new Marker();
    newMarker.color = "#ffa0a0";
    
    if (this.config.markers.length > 0){
      this.config.markers.forEach(element => {
      newMarker.lat += element.lat;
      newMarker.long += element.long;
      newMarker.mag += element.mag;
      newMarker.color = element.color;
      newMarker.name = "";
    });
    
    newMarker.lat /= this.config.markers.length;
    newMarker.long /= this.config.markers.length;
    newMarker.mag /= this.config.markers.length;
    }else{
      newMarker.lat = this.lat;
      newMarker.long = this.lng;
      newMarker.mag = 200;
    }
    
    this.config.markers.push(newMarker);
  }


  addNewPolyline(){
    this.currentlyEditingLine = new Polyline();
    this.currentlyEditingLine.color = "#ffa0a0";
    this.currentlyEditingLine.points = [];
  }

  onClick(e){
    if (this.currentlyEditingLine)
    {
      this.currentlyEditingLine.points.push({lat: e.coords.lat, long: e.coords.lng});
    }
  }

  onCircleDelete(marker:Marker){
    var index = this.config.markers.indexOf(marker);
    if (index > -1)
      this.config.markers.splice(index, 1);
  }

  acceptPolyline(){
    this.config.polylines.push(this.currentlyEditingLine);
    this.currentlyEditingLine = null;
  }

  cancelPolyline(){
    this.currentlyEditingLine = null;    
  }

  onDeletePolyline(polyline:Polyline){
    var index = this.config.polylines.indexOf(polyline);
    if (index > -1)
      this.config.polylines.splice(index, 1);
  }
}