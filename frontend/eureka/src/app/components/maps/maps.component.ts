import { Marker } from './Marker';
import { NgModule, Component ,OnInit} from '@angular/core';

@Component({
  selector: 'maps',
  styleUrls: ['./maps.component.css'],
  templateUrl: './maps.component.html'
})
export class MapsComponent implements OnInit{
  lat: number = 51.678418;
  lng: number = 7.809007;

  private markers: Marker[] = [
    {lat: 51.1, long: 7.05, mag:10000 ,color:"#ffaafa", id:"135245", name:"dalay"},
    {lat: 51.2, long: 7, mag:20000, color:"#ffaafa" ,id:"23452646", name:"lmaa"},
  ];


  ngOnInit(){
  }
  onCenterChange(whatever, marker){
    console.log(whatever);
    marker.lat = whatever.lat;
    marker.long = whatever.lng;
  }

  addNewCircle(){
    var newMarker : Marker = new Marker();
    this.markers.forEach(element => {
      newMarker.lat += element.lat;
      newMarker.long += element.long;
      newMarker.mag += element.mag;
      newMarker.color = element.color;
      newMarker.name = "";
    });
    
    newMarker.lat /= this.markers.length;
    newMarker.long /= this.markers.length;
    newMarker.mag /= this.markers.length;
    
    this.markers.push(newMarker);
  }

  onCircleDelete(marker:Marker){
    var index = this.markers.indexOf(marker);
    if (index > -1)
      this.markers.splice(index, 1);
  }
}