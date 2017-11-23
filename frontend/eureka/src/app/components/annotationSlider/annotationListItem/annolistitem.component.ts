import { Component } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { User } from '../../../services/user';
import { Listory } from '../../../services/Listory';
import { Directive, ElementRef, Input } from '@angular/core';
import { OnInit } from '@angular/core';

import { AnnotationService } from '../../../services/annotation.service';

import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';
import { Annotation } from '../../../services/Annotation';



@Component({
  selector: 'annolistitem',
  templateUrl: './annolistitem.component.html',
  styleUrls: ['./annolistitem.component.css'],
})
export class AnnotationListComponent implements OnInit {

    @Input() annotation: Annotation = {
        body : {
            value: "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
            type: "TextualBody",
            format: "text/plain"
        },
        id : "123123",
        target : "this",
        type : "werer"
    };

  constructor(
    private annotationService : AnnotationService
  ){}

  ngOnInit(){
  }
}
