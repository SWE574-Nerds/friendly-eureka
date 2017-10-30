import { Directive, ElementRef, Component, Input, OnInit } from '@angular/core';
import { Card } from './card';

import { ListoryService } from '../../services/listory.service';



@Component({
  selector: 'cardview',
  templateUrl: './cardview.component.html',
  styleUrls: [ './cardview.component.css' ],
})

export class CardViewComponent implements OnInit{
  constructor(
    private listoryService: ListoryService
  ){}

  private cards: Card[];


  ngOnInit(){
    this.listoryService.getCards().then(cards => this.cards = cards);
  }

}
