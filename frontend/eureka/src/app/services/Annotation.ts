export class Selector{
    public exact: string;
    public prefix: string;
    public suffix: string;
}
export class Creator{
    public nickname: string;
    public id: string;
    public type: string;
}

export class Annotation 
{
    public id : string;
    public body: object;
    public target: string;
    public creator: Creator;
    public selector: Selector[]
}