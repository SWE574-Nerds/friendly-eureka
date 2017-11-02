import { Injectable } from '@angular/core';
import { User } from './user';
import { UserService } from './user.service';
import { Headers, Http } from '@angular/http';
import { Router } from '@angular/router';
import { Card } from '../components/cardview/card';
import { Listory } from './Listory';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class ListoryService {
  private listoryUrl = '/api/listory';
  private listoryIdUrl = '/api/listory/:id';

  constructor(
    private http: Http,
    private userService: UserService
  ) { }

  getListory(id): Promise<Listory> {
    return new Promise<Listory>(resolve => {
      this.http.get(this.listoryIdUrl.replace(':id', id))
        .toPromise()
        .then((resp) => {
          var listory: Listory = resp.json();
          resolve(listory);
        });
    });
  }

  deleteListory(id) {
    return new Promise<void>((resolve) => {
      this.userService.getUser().then((user) => {        
        if (user && user.token) {
          this.http.delete(this.listoryIdUrl.replace(':id', id), {
            headers: this.createHeaders(user.token)
          }).toPromise().then(() => resolve())
        } else {
          resolve();
        }
      });
    });
  }

  getCards(): Promise<Card[]> {
    return new Promise(resolve => {

      this.http.get(this.listoryUrl)
        .toPromise()
        .then((resp) => {

          var listories: Listory[] = resp.json() as Listory[];
          console.log(listories);

          var result: Card[] = [];

          for (var listoryId in listories) {
            var listory = listories[listoryId];

            result.push({
              id: listory.listoryId,
              title: listory.name,
              description: listory.description.substr(0, 150),
              owner: listory.owner.name,
              lastUpdate: listory.createdAt,
              image: listory.image
            });
          }

          resolve(result);
        });
    });
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }



  private createHeaders(token: string): Headers{
    var headers = new Headers({'Content-Type': 'application/json'});
    if (token)
      headers.append('Authorization', token);
    return headers;
  }


}
