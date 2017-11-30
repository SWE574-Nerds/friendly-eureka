import { Marker } from '../components/maps/Marker';
import { Polyline } from '../components/maps/Polyline';
import { Tag } from './Tag';

export class Listory {
    name:string;
    description:string;
    image:string;
    page:string;
    listoryId:string;
    tags:Tag[];
    owner:{
        name:string;
        userId:string;
    };
    createdAt:string;
    time:{
        name:string;
        units:string;
        values:number[];
    }
    markers: Marker[];
    polylines: Polyline[];

}

