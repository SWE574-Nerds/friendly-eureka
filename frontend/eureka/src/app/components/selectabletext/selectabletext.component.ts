import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../services/user';
import { Listory } from '../../services/Listory';
import { Directive, ElementRef, Input } from '@angular/core';
import { OnInit } from '@angular/core';
import { ListoryService } from '../../services/listory.service';

@Component({
  selector: 'selectabletext',
  templateUrl: './selectabletext.component.html',
  styleUrls: ['./selectabletext.component.css'],


})
export class SelectableTextComponent {

    @Input() text: string;

}
