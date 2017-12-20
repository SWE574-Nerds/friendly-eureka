export class Selector{
    public exact: string;
    public prefix: string;
    public suffix: string;
}

export class Annotation 
{
    public id : string;
    public body: object;
    public target: string;
    public creator: string;
    public selector: Selector[];
    public motivation: string
}