import { TextAnnotationPosition } from './TextAnnotationPosition';

export class HighlightInfo{
    public startAt: number = 0;
    public endAt: number = 0;

    public annotations: TextAnnotationPosition[] = [];
}