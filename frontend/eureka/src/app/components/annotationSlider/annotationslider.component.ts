import { Component, EventEmitter } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../services/user';
import { Listory } from '../../services/Listory';
import { Directive, ElementRef, Input, Output } from '@angular/core';
import { OnInit } from '@angular/core';

import { AnnotationService } from '../../services/annotation.service';
import { Annotation } from '../../services/Annotation';
import { AnnotationSelector } from '../../services/AnnotationSelector';
import { TextAnnotationPosition } from '../selectabletext/TextAnnotationPosition';

import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';



@Component({
  selector: 'annotationSlider',
  templateUrl: './annotationslider.component.html',
  styleUrls: ['./annotationslider.component.css'],

  animations: [
    trigger('onToggle', [
      state('in', style({
        transform: 'translate3d(0, 0, 0)'
      })),
      state('out',   style({
        transform: 'translate3d(100%, 0, 0)'
      })),
      transition('in => out', animate('100ms ease-in')),
      transition('out => in', animate('100ms ease-out'))
    ])
  ]
})
export class AnnotationSliderComponent implements OnInit {

  @Input() listory: Listory;
  @Input() annotations: object[] = [];  
  public toggle = 'out';
  @Input() mediaType = "text";
  @Input() description = "";
  @Input() link = "";

  @Input() textSelection: TextAnnotationPosition = null;
  @Input() currentlyHighlightedAnnotation: TextAnnotationPosition;

  @Output() onChange:EventEmitter<TextAnnotationPosition> = new EventEmitter();
  
  constructor(
    private annotationService : AnnotationService
  ){}

  toggleMenu(){
    this.toggle = this.toggle === 'in' ? 'out' : 'in';
  }

  ngOnInit() {
    this.updateAnnotations();
  }

  onHighlightItemChanged(anno:Annotation){
    this.toggle = "in";
  }

  updateAnnotations(){
    if (this.listory) {
      this.annotationService.getAnnotationsOfListory(this.listory.listoryId).then((annotations) => {
        this.annotations = annotations;
      });
    }
  }

  convertMediaType(type)
  {
     this.mediaType = type;
  }

  onSubmit() {
    var annotationSelector = new AnnotationSelector();
    annotationSelector.fullPage = this.textSelection.selection.length === 0;
    annotationSelector.textSelector = this.textSelection.selection.length > 0;
    annotationSelector.startsWith = this.textSelection.startsWith;
    annotationSelector.endsWith = this.textSelection.endsWith;
    annotationSelector.selection = this.textSelection.selection;
    annotationSelector.listoryId = ""+this.listory.listoryId;
    annotationSelector.mediaType = this.mediaType;

    if (
      (this.mediaType === "text" && this.description === "") ||
      (this.mediaType === "image" && this.link === "") ||
      (this.mediaType === "video" && this.link === "")
    ){
      annotationSelector.highlight = true;
    }

    if (this.mediaType === "text") {
        annotationSelector.description = this.description;
    }else if (this.mediaType === "image") {
      annotationSelector.description = this.description;
      annotationSelector.link = this.link;
    }else if (this.mediaType === "video") {
      annotationSelector.description = this.description;
      annotationSelector.link = this.link;
    }

    this.annotationService.createAnnotation(annotationSelector).then(()=>{
      this.updateAnnotations();
      this.onChange.emit(this.textSelection);
    });
  }
}
