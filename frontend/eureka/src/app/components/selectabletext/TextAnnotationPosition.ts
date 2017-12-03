import { Annotation } from "../../services/Annotation";

export class TextAnnotationPosition{
    public startsWith: string;
    public endsWith: string;
    public selection: string;

    public annotation: Annotation;
}