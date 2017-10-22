import { Component, Input, OnInit } from '@angular/core';

import { User } from './user'
import { UserService } from './user.service'

@Component({
  selector: 'welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.css']
})
export class WelcomeScreenComponent implements OnInit {
  
  @Input() user: User;

  private message = 'Welcome to Eureka!'

  constructor(
    private userService: UserService
  ){}


  ngOnInit(): void {
    this.userService.getUser().then(user => {
      this.user = user;      
      if (this.user != null)
      this.message = 'Hello, ' + this.user.name;
    });  
  }

}
