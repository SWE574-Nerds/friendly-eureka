import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../services/user';


import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';



@Component({
  selector: 'test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css'],


  animations: [
    trigger('onToggle', [
      state('in', style({
        transform: 'translate3d(0, 0, 0)'
      })),
      state('out',   style({
        transform: 'translate3d(100%, 0, 0)'
      })),
      transition('in => out', animate('100ms ease-in')),
      transition('out => in', animate('100ms ease-out'))
    ])
  ]
})
export class TestComponent {

  public toggle = 'out';


  toggleMenu(){
    this.toggle = this.toggle === 'in' ? 'out' : 'in';
  }
}
