import { Component, Input, OnInit } from '@angular/core';

import { User } from '../../services/user'
import { WelcomeForm } from './form'
import { UserService } from '../../services/user.service'

@Component({
  selector: 'welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.css'],
})
export class WelcomeScreenComponent implements OnInit {
  
  @Input() user: User = new User();

  private form: WelcomeForm = new WelcomeForm(); // false means Login
  onRegister: Boolean = true; // false means login.

  constructor(private userService: UserService) { }
  
  ngOnInit(): void {
    this.userService.getUser().then(user => {
      this.user = user;
    });  
  }
}

