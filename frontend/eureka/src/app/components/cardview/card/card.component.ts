import { Directive, ElementRef, Component, Input } from '@angular/core';
import { Card } from '../card';

import { Router } from '@angular/router';



@Component({
  selector: 'card',
  templateUrl: './card.component.html',
  styleUrls: [ './card.component.css' ],
})

export class CardComponent {

  constructor(private router: Router) {}

  
  @Input() card: Card;


  gotoItem(){
    this.router.navigate(['/detail', this.card.id]);
  }
}