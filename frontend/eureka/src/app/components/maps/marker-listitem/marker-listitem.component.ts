import { NgModule, Component ,Input, Output, EventEmitter} from '@angular/core';
import { Marker } from '../Marker';
import { Polyline } from '../Polyline';

@Component({
  selector: 'marker-listitem',
  styleUrls: ['./marker-listitem.component.css'],
  templateUrl: './marker-listitem.component.html'
})
export class MarkerListItem{
    @Input() marker:Marker = null;
    @Input() polyline:Polyline = null;
    @Input() public name: string;
    @Input() public editable: Boolean = false;

    @Output() deleter = new EventEmitter<Marker>();    
    @Output() deleterPolyline = new EventEmitter<Polyline>();    
    
    deleteMarker(){
        if (this.marker)
            this.deleter.emit(this.marker);
        else if (this.polyline)
            this.deleterPolyline.emit(this.polyline);
    }
}