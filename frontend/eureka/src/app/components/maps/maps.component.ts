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
    {lat: 51.1, long: 7.05, mag:10000 ,color:"#ffaafa", id:"135245"},
    {lat: 51.2, long: 7, mag:20000, color:"#ffaafa" ,id:"23452646"},
  ];


  ngOnInit(){
      setInterval(()=>{
          this.markers.forEach(marker => {
              console.log(marker);
          });
          console.log("*************");
        }, 5000);
  }
  onCenterChange(whatever, marker){
    console.log(whatever);
    marker.lat = whatever.lat;
    marker.long = whatever.lng;
  }

}