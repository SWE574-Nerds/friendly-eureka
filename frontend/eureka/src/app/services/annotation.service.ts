import { Injectable } from '@angular/core';
import { User } from './user';
import { UserService } from './user.service';
import { Headers, Http } from '@angular/http';
import { Router } from '@angular/router';
import { Card } from '../components/cardview/card';
import { Listory } from './Listory';
import { Annotation } from './Annotation';


import 'rxjs/add/operator/toPromise';

@Injectable()
export class AnnotationService {
  private listoryUrl = '/api/listory';
  private listoryIdUrl = '/api/listory/:id/annotations';

  constructor(
    private http: Http,
    private userService: UserService
  ) { }

  getAnnotationsOfListory(id): Promise<Annotation[]> {
    return new Promise<Annotation[]>(resolve => {
      this.http.get(this.listoryIdUrl.replace(':id', id))
        .toPromise()
        .then((resp) => {
          var listory: Annotation[] = resp.json() as Annotation[];
          resolve(listory);
        });
    });
  }

  getAnnotationOwner(ownerIRI): Promise<object> {
    const userId = ownerIRI.replace(/.+user\/?/g, "");
    console.log(userId);
    return new Promise<object>(resolve => {
      this.userService.getUserInfo(userId).then((user)=>{ resolve(user); });
    });
    
  }
}
