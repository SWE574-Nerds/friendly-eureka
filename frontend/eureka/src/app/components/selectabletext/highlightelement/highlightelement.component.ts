import { Component, EventEmitter } from '@angular/core';
import { Directive, ElementRef, Input, Output } from '@angular/core';
import { OnInit } from '@angular/core';
import { HighlightInfo } from '../HighlightInfo';

@Component({
  selector: 'highlightelement',
  templateUrl: './highlightelement.component.html',
  styleUrls: [ './highlightelement.component.css' ]
})

export class HighlightElementComponent {
  @Input() highlight: HighlightInfo;
  @Input() text: string;

  @Output() onHighlight: EventEmitter<HighlightInfo> = new EventEmitter();

  click(){
    console.log("highlight tıklantı");
    this.onHighlight.emit(this.highlight);
  }
}