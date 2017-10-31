import { Component, Input, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

import { WelcomeForm } from '../../components/welcome-screen/form'

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
  @Input() form: WelcomeForm;
  
  private message: string;

  onSubmit(){
      this.userService.login(this.password, this.username);    
  }

  gotoRegister(){
    this.form.onRegister = true;
  }

}