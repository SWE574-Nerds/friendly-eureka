import { Component, EventEmitter } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../services/user';
import { Listory } from '../../services/Listory';
import { Directive, ElementRef, Input, Output } from '@angular/core';
import { OnInit } from '@angular/core';
import { ListoryService } from '../../services/listory.service';
import { HighlightInfo } from './HighlightInfo';
import { TextAnnotationPosition } from './TextAnnotationPosition';
import { IntersectionHelper } from './helpers/IntersectionHelper';
import { AnnotationService } from '../../services/annotation.service';
import { Annotation, Selector } from '../../services/Annotation';
import { OnChanges } from '@angular/core';


@Component({
  selector: 'selectabletext',
  templateUrl: './selectabletext.component.html',
  styleUrls: ['./selectabletext.component.css']
})


export class SelectableTextComponent implements OnInit {
  @Input() listory: Listory;
  private text: string;
  @Input() selections: TextAnnotationPosition[] = [];
  @Input() highlights: HighlightInfo[] = [];

  private texts = [];

  private NORM_CHAR = "`";
  private DIVIDER = /`+/g;

  @Input() textSelection:TextAnnotationPosition = new TextAnnotationPosition();

  public currentlyHighlightedAnnotation: Annotation;

  public selected: string = "";

  constructor(
    public annotationService: AnnotationService    
  ){}

  sortHighlights(a: HighlightInfo, b: HighlightInfo): number {
    const x = a.startAt;
    const y = b.startAt;
    return ((x < y) ? -1 : ((x > y) ? 1 : 0));
  }


  replaceAt(str, index, replacement) {
    return str.substr(0, index) + replacement + str.substr(index + replacement.length);
  }

  private selection = {
    begin: 0,
    end: 0,
    originalText: ""
  };

  ngOnInit() {
    this.text = this.listory.description;
    this.onChange(null);
  }

  onHighlight(annotation:Annotation){
      console.log("id passing " + annotation.id);
      this.currentlyHighlightedAnnotation = annotation;
  }

  onChange(e){
    this.updateAnnotations();
    this.update();
  }

  updateAnnotations(){
    this.annotationService.getAnnotationsOfListory(this.listory.listoryId).then((annos:Annotation[])=>{
      this.selections = [];
      annos.forEach((anno)=>{
        if (anno.selector.length === 0) return;

        var selection = new TextAnnotationPosition();
        selection.selection = anno.selector[0].exact;
        selection.startsWith = anno.selector[0].prefix;
        selection.endsWith = anno.selector[0].suffix;
        selection.annotation = anno;
        this.selections.push(selection);
      });

      this.update();
      console.log(this.texts);
    });
  }

  update() {
    var selections_temp = this.selections;
    this.selections = [];
    selections_temp.forEach((selection) => this.addSelection(selection));
    this.createAnnoItems();
  }

  doNeedToSlice(current, highlight, lastIndex): boolean {
    var result = (lastIndex + current.length > highlight.startAt) &&
      (lastIndex < highlight.startAt);

    return result;
  }

  createAnnoItems() {
    var texts = [];
    var lastIndex = 0;

    var temp_text = this.text;

    this.highlights.forEach((highlight) => {
      for (var i = highlight.startAt; i < highlight.endAt; i++)
        temp_text = this.replaceAt(temp_text, i, this.NORM_CHAR);
    });

    var count = 0;
    var texts_group: string[] = temp_text.split(this.DIVIDER);
    for (var i = 0; i < texts_group.length; i++) {
      texts.push({ text: texts_group[i], highlight: null });
      count += texts_group[i].length;

      this.highlights.forEach(element => {
        if (element.startAt === count) {
          texts.push({ text: this.text.substr(element.startAt, element.endAt - element.startAt), highlight: element });
          count += element.endAt - element.startAt;
        }
      });
    }

    this.texts = texts;
  }

  addSelection(p: TextAnnotationPosition) {

    var helper: IntersectionHelper = new IntersectionHelper();

    // Get start and end positions of this annotation.
    var startingPoints = helper.getIndicesOf(this.text, p.selection, p.startsWith, p.endsWith);
    if (startingPoints.length <= 0)
      return;

    var startingPoint = startingPoints[0];
    var endingPoint = startingPoint + p.selection.length;

    // nullify the highlights first, there's gonna be a lot of changes on this.
    var highlights = this.highlights;
    this.highlights = [];

    // Add as a HighlightInfo first. This will be sorted out later on.
    var highlight = new HighlightInfo();
    highlight.startAt = startingPoint;
    highlight.endAt = endingPoint;
    highlight.annotations.push(p);
    highlights.push(highlight);

    // Now check for all of the highlights, one-by-one merge them together.
    for (var k = 0; k < 10; k++)
      for (var i = 0; i < highlights.length; i++) {
        for (var j = 0; j < highlights.length; j++) {
          if (j <= i) continue;

          var high_1 = highlights[i];
          var high_2 = highlights[j];

          if (helper.rangesIntersect(high_1.startAt, high_1.endAt, high_2.startAt, high_2.endAt)) {
            var borders = helper.broadenBorders(high_1.startAt, high_1.endAt, high_2.startAt, high_2.endAt);

            highlights[i].startAt = borders.a;
            highlights[i].endAt = borders.b;
            highlights[j].annotations.forEach((anno) => {
              if (anno.selection.length === 0) return;
              highlights[i].annotations.push(anno);
            });
            highlights[j].annotations = [];
          }
        }
      }

    //Move highlights to correct place
    for (var i = 0; i < highlights.length; i++) {
      if (highlights[i].annotations.length > 0) {
        this.highlights.push(highlights[i]);
      }
    }
    this.highlights.sort(this.sortHighlights);
  }


  onSelect() {    
    var selection = window.getSelection();

    if (selection.focusNode === selection.anchorNode) {
      var selectedElement = selection.focusNode.textContent;
      this.selection.begin = selection.anchorOffset;
      this.selection.end = selection.focusOffset;

      if (this.selection.begin > this.selection.end) {
        var temp = this.selection.begin;
        this.selection.begin = this.selection.end;
        this.selection.end = temp;
      }

      this.selection.originalText = selectedElement;
      this.textSelection.selection = this.selection.originalText.substr(this.selection.begin, this.selection.end - this.selection.begin);
      this.textSelection.startsWith = this.selection.originalText.substr(0, this.selection.begin);
      this.textSelection.endsWith = this.selection.originalText.substr(this.selection.end);
    } else {
      var firstSpan = selection.anchorNode.textContent;
      var secondSpan = selection.focusNode.textContent;

      if (this.selection.begin > this.selection.end) {
        var temp = this.selection.begin;
        this.selection.begin = this.selection.end;
        this.selection.end = temp;
      }

      var firstIndex = 0;
      var secondIndex = 0;
      for (var i = 0; i < this.texts.length; i++) {
        if (this.texts[i].text === firstSpan) firstIndex = i;
        if (this.texts[i].text === secondSpan) secondIndex = i;
      }

      var completeText = "";
      for (var i = firstIndex; i <= secondIndex; i++) {
        completeText += this.texts[i].text;
      }

      var selectionText = selection.toString();
      var startIndex = completeText.indexOf(selectionText);

      this.selection.originalText = completeText;
      this.textSelection.selection = selectionText;
      this.textSelection.startsWith = this.selection.originalText.substr(0, startIndex);
      this.textSelection.endsWith = this.selection.originalText.substr(startIndex + selectionText.length);
    }
  }

}
