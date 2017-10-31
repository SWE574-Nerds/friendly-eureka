import { Directive, ElementRef, Component, Input, OnInit } from '@angular/core';
import { Card } from './card';

@Directive({ selector: '[ngback]' })
export class BackgroundDirective implements OnInit {
  @Input() ngback: Card;

  private el: ElementRef;
  
    constructor(el: ElementRef) {
            this.el = el;
    }

  ngOnInit(): void {
        this.el.nativeElement.style.backgroundImage = 'url(\'' + this.ngback.image + '\')';
        this.el.nativeElement.style.backgroundSize = 'cover';
        console.log("Image is ::::" + this.ngback.image);
    } 

}
