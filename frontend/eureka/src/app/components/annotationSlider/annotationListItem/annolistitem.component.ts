import { Component } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { User } from '../../../services/user';
import { Listory } from '../../../services/Listory';
import { Directive, ElementRef, Input } from '@angular/core';
import { OnInit, OnChanges, Output, EventEmitter } from '@angular/core';

import { AnnotationService } from '../../../services/annotation.service';
import { Annotation } from '../../../services/Annotation';
import { TextAnnotationPosition } from '../../selectabletext/TextAnnotationPosition';

@Component({
  selector: 'annolistitem',
  templateUrl: './annolistitem.component.html',
  styleUrls: ['./annolistitem.component.css'],
})

@Directive({ selector: '[ngClass]' })
export class AnnotationListComponent implements OnInit {

  @Input() currentlyHighlightedAnnotation: Annotation;

  @Input() annotation: Annotation = {
    body: null,
    id: "0",
    target: "",
    creator: {
      nickname: "testser",
      id: "0",
      type: "Person"
    },
    selector: []
  };

  @Output() onHighlight: EventEmitter<Annotation> = new EventEmitter();

  @Input() author: object = {
    name: ""
  }

  @Input() bodies: object[] = null;

  ngOnChanges(e) {
    if (this.currentlyHighlightedAnnotation)
    if (this.annotation)
    if (this.currentlyHighlightedAnnotation.id === this.annotation.id){
      this.highlight = true;
      this.onHighlight.emit(this.annotation);
    }else{
      this.highlight = false;
    }
    
    console.log(this.highlight);
  }

  private highlight: boolean = false;

  constructor(
    private annotationService: AnnotationService
  ) { }

  ngOnInit() {
/*
    setInterval(() => {
      console.log(this.currentlyHighlightedAnnotation);
      console.log(this.annotation);
      console.log("*************");
    }, 1000);*/


    
        if (Array.isArray(this.annotation.body)) {
          this.bodies = this.annotation.body;
        } else if (this.annotation.body) {
          this.bodies = [this.annotation.body];
        }
      }
}
