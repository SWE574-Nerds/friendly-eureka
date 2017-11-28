import { Marker } from '../components/maps/Marker';
export class CreateForm
{
    name:string;
    description:string;
    image:string;
    category:string;
    timeInfo:{
        id:string;
        value_1:string;
        value_2:string;
    };
    markers:Marker[];
}