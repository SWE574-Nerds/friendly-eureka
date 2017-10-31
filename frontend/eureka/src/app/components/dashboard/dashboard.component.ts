import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../services/user'

@Component({
  selector: 'my-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {

  private user: User;

  constructor(
    private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.userService.getUser().then(user => {
      this.user = user;
    });
  }
}
