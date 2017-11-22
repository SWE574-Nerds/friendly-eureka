import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../services/user';
import { Listory } from '../../services/Listory';
import { Directive, ElementRef, Input } from '@angular/core';
import { OnInit } from '@angular/core';

import { AnnotationService } from '../../services/annotation.service';
import { Annotation } from '../../services/Annotation';

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

  constructor(
    private annotationService : AnnotationService
  ){}

  toggleMenu(){
    this.toggle = this.toggle === 'in' ? 'out' : 'in';
  }

  ngOnInit(){
    this.annotationService.getAnnotationsOfListory(this.listory.listoryId).then((annotations) => {
      console.log(annotations);
      this.annotations = annotations;
      console.log(this.annotations);
    });
  }
}
