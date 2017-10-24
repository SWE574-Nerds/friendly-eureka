import { Component, Input, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'register-form',
  templateUrl: './register-form.component.html',
})

export class RegisterFormComponent {

  constructor(
    private userService: UserService
  ){}

  @Input() password: string;
  @Input() password2: string;

  @Input() username: string;
  @Input() email: string;

  private message: string;

  onSubmit(){

    if (this.password != this.password2){
      this.message = "Passwords don't match";       
    }else if (!this.password || this.password.length <= 8){
      this.message = "Minimum password length is 8";   
    }else if (!this.email){
      this.message = "Please type a valid email address";
    }else{
      this.message = "";
      
      this.userService.register(this.email, this.password, this.username);
    }
  }

}