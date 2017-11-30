import { Marker } from '../components/maps/Marker';
import { Polyline } from '../components/maps/Polyline';
export class CreateForm
{
    name:string;
    description:string;
    image:string;
    tags:string[];
    timeInfo:{
        id:string;
        value_1:string;
        value_2:string;
    };
    markers:Marker[];
    polylines:Polyline[];
}