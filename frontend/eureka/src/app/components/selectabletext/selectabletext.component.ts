import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../services/user';
import { Listory } from '../../services/Listory';
import { Directive, ElementRef, Input } from '@angular/core';
import { OnInit } from '@angular/core';
import { ListoryService } from '../../services/listory.service';
import { HighlightInfo } from './highlightelement/HighlightInfo';

@Component({
  selector: 'selectabletext',
  templateUrl: './selectabletext.component.html',
  styleUrls: ['./selectabletext.component.css']
})

export class SelectableTextComponent implements OnInit{
  @Input() text: string = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
  @Input() texts: HighlightInfo[] = [];
  @Input() selectionText: string = "";


  private selection = {
    begin: 0,
    end: 0,
    originalText: ""
  };

  ngOnInit(){
    this.texts = [{text: this.text, highlight: false}];
  }

  onSelect(){
    var selection = window.getSelection();
    this.selection.begin = selection.anchorOffset;
    this.selection.end = selection.focusOffset;
    var selectedElement = selection.focusNode.textContent;
    
    this.selection.originalText = selectedElement;

    if (this.selection.end<this.selection.begin){
      var temp = this.selection.begin;
      this.selection.begin = this.selection.end;
      this.selection.end = temp;
    }
  }

  onClick(){
    for (var i=0; i<this.texts.length; i++){
      if (this.selection.originalText == this.texts[i].text)
        break;
    }

    const texts = this.texts;
    this.texts = [];
    for (var j=0; j<texts.length; j++)
    {
      if (i!=j){
        if (texts[j])
        this.texts.push(texts[j]);
      }
      else{
        var starter = this.selection.originalText.substr(0,this.selection.begin);
        var middle = this.selection.originalText.substr(this.selection.begin,this.selection.end-this.selection.begin);
        var ending = this.selection.originalText.substr(this.selection.end);
        if (starter.length>0) this.texts.push({text: starter, highlight: false});
        if (middle.length>0)  this.texts.push({text: middle, highlight: true});
        if (ending.length>0)  this.texts.push({text: ending, highlight: false});
      }
    }
  }
}


