import { Component, Input, OnInit } from '@angular/core';

import { User } from '../../services/user'
import { UserService } from '../../services/user.service'
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.css']
})
export class WelcomeScreenComponent implements OnInit {
  
  @Input() user: User;

  private message = 'Welcome to Eureka!';
  public title : string;
  private route$ : Subscription;


  constructor(
    private userService: UserService,
    private route : ActivatedRoute
  ){}


  ngOnInit(): void {
    this.userService.getUser().then(user => {
      this.user = user;
    });  

    this.route$ = this.route.params.subscribe(
      (params : Params) => {
          this.title = params["title"]; 
      }
  );
  }

  ngOnDestroy() {
    if(this.route$) this.route$.unsubscribe();
}

}



