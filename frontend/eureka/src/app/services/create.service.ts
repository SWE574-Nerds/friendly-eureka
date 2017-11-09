import { Injectable } from '@angular/core';
import { User } from './user';
import { Headers, Http } from '@angular/http';
import { Router } from '@angular/router';
import { Card } from '../components/cardview/card';
import { Category } from './Category';
import { TimeInfo } from './TimeInfo';
import { CreateForm } from './CreateForm'

import 'rxjs/add/operator/toPromise';

import { UserService } from './user.service';

@Injectable()
export class CreateService {    
    private categoryUrl = '/api/category';
    private timeInfo = '/api/time';
    private create = '/api/listory/create';
    
    constructor(
        private http: Http,
        private userService: UserService,
        private router: Router
    ) { }

    getCategories(): Promise<Category[]> {
        return new Promise(resolve => {
        
        this.http.get(this.categoryUrl)
        .toPromise()
        .then((resp) => {
            let categories:Category[] = resp.json() as Category[];
            resolve(categories);
         });
        }).catch(this.handleError);
    }

    createListory(form: CreateForm): Promise<Card>{
        return new Promise(resolve => {
            this.userService.getUser().then(user => {
            if (!user || !user.token){
                this.router.navigate(['/welcome']);
                resolve(null);
                return;
            }

            this.http.post(this.create, form, {
                headers: this.createHeaders(user.token)
              })
            .toPromise()
            .then((resp) => {
                console.log(resp);
                this.router.navigate(['/all']);
             //   resolve(resp)
            });

        });
        });
    }

    getTimeInfos(): Promise<TimeInfo[]> {
        return new Promise(resolve => {
        
        this.http.get(this.timeInfo)
        .toPromise()
        .then((resp) => {
            let categories:TimeInfo[] = resp.json() as TimeInfo[];
            resolve(categories);
         });
        }).catch(this.handleError);
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
