import { Component, Input, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'login-form',
  templateUrl: './login-form.component.html',
})

export class LoginFormComponent {

  constructor(
    private userService: UserService
  ){}

  @Input() password: string;

  @Input() username: string;

  private message: string;

  onSubmit(){

      this.message = "";   
      this.userService.login(this.password, this.username);
    
  }

}