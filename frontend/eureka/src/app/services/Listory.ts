export class Listory {
    name:string;
    description:string;
    image:string;
    page:string;
    listoryId:string;
    category:string;
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

}

