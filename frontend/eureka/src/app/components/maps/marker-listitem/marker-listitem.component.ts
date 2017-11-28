import { NgModule, Component ,Input, Output, EventEmitter} from '@angular/core';
import { Marker } from '../Marker';

@Component({
  selector: 'marker-listitem',
  styleUrls: ['./marker-listitem.component.css'],
  templateUrl: './marker-listitem.component.html'
})
export class MarkerListItem{
    @Input() marker:Marker;
    @Input() public name: string;
    @Input() public editable: Boolean = false;

    @Output() deleter = new EventEmitter<Marker>();    

    deleteMarker(){
        this.deleter.emit(this.marker);
    }
}