import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../services/user';
import { Listory } from '../../services/Listory';
import { Directive, ElementRef, Input } from '@angular/core';
import { OnInit } from '@angular/core';
import { ListoryService } from '../../services/listory.service';
import { HighlightInfo } from './HighlightInfo';
import { TextAnnotationPosition } from './TextAnnotationPosition';
import { IntersectionHelper } from './helpers/IntersectionHelper';

@Component({
  selector: 'selectabletext',
  templateUrl: './selectabletext.component.html',
  styleUrls: ['./selectabletext.component.css']
})


export class SelectableTextComponent implements OnInit {
  @Input() text: string = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
  @Input() selections: TextAnnotationPosition[] = [];
  @Input() highlights: HighlightInfo[] = [];

  private texts = [];

  private NORM_CHAR = "`";
  private DIVIDER = /`+/g;

  private textSelection:TextAnnotationPosition = null;
  

  public selected: string = "";

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
    this.update();
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
    this.textSelection = new TextAnnotationPosition();

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

  onClick() {
    this.selections.push(this.textSelection);
    this.update();
  }

}
