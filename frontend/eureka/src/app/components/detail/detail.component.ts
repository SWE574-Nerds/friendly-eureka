import { Component, Input, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { ListoryService } from '../../services/listory.service';
import { Listory } from '../../services/Listory';
import { User } from '../../services/user';
import { UserService } from '../../services/user.service';

import 'rxjs/add/operator/switchMap';

@Component({
  selector: 'detail',
    templateUrl: './detail.component.html',
    styleUrls: [ './detail.component.css' ],
})

export class DetailComponent implements OnInit {
constructor(
  private route: ActivatedRoute,
  private router: Router,
  private location: Location,
  private listoryService: ListoryService,
  private  userService: UserService
) {}

private user: User = new User();

private listory: Listory;
private markersConfig = {
  editable: false,
  polylines: [],
  markers: []
};

  ngOnInit(): void {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.listoryService.getListory(+params.get('id')))
      .subscribe((listory) => {
        this.listory = listory;
        this.markersConfig.markers = this.listory.markers;
        this.markersConfig.polylines = this.listory.polylines;
      });

      this.userService.getUser().then(user => {
        this.user = user;
      });
  }

  deleteListory(listoryId): void{
    console.log(listoryId);
    this.listoryService.deleteListory(listoryId).then(()=>{
      this.router.navigate(['/all']);      
    });
  }

  goBack(): void {
    this.location.back();
  }
}