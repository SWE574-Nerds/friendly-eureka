import { Component, EventEmitter } from '@angular/core';
import { Directive, ElementRef, Input, Output } from '@angular/core';
import { OnInit } from '@angular/core';
import { Annotation } from '../../../services/Annotation';
import { HighlightInfo } from '../HighlightInfo';

@Component({
  selector: 'highlightelement',
  templateUrl: './highlightelement.component.html',
  styleUrls: [ './highlightelement.component.css' ]
})

export class HighlightElementComponent implements OnInit {
  @Input() highlight: HighlightInfo;
  @Input() text: string;

  private dataTarget: string;

  @Output() onHighlight: EventEmitter<Annotation> = new EventEmitter();

  ngOnInit(){
    if (this.highlight && this.highlight.annotations.length > 0)
    this.dataTarget = "#"+this.highlight.annotations[0].annotation.id;
  }

  click(annotation){
    console.log("highlight tıklantı");
    this.onHighlight.emit(annotation);
  }
}