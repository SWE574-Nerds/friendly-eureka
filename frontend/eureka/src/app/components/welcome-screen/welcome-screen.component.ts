import { Component, Input, OnInit } from '@angular/core';

import { User } from '../../services/user'
import { WelcomeForm } from './form'
import { UserService } from '../../services/user.service'
<<<<<<< Updated upstream
import { Subscription } from 'rxjs/Subscription';
=======
>>>>>>> Stashed changes

@Component({
  selector: 'welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.css']
})
export class WelcomeScreenComponent implements OnInit {
  
  @Input() user: User;

<<<<<<< Updated upstream
  private form: WelcomeForm = new WelcomeForm(); // false means Login
=======
  private message = 'Welcome to Eureka!';

  onRegister: Boolean = true; // false means login.
>>>>>>> Stashed changes

  constructor(
    private userService: UserService,
  ){}

  ngOnInit(): void {
    this.userService.getUser().then(user => {
      this.user = user;
<<<<<<< Updated upstream
    });  

  }


=======
    });
  }

>>>>>>> Stashed changes
}



