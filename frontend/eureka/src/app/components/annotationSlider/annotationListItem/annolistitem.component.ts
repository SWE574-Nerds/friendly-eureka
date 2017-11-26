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
        body : null,
        id : "0",
        target : "",
        creator: ""
    };

    @Input() author: object = {
      name : ""
    } 

    @Input() bodies: object[] = null;

  constructor(
    private annotationService : AnnotationService
  ){}

  ngOnInit(){
    if (this.annotation.creator !== "")
    {
      this.annotationService.getAnnotationOwner(this.annotation.creator).then((author)=>{
        this.author = author;
        if (Array.isArray(this.annotation.body)){
          this.bodies = this.annotation.body;
        }else if (this.annotation.body){
          this.bodies = [ this.annotation.body ];
        }
      });
    }
  }
}
