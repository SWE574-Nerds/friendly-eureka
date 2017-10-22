import { Injectable } from '@angular/core';
import { User } from './user';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class UserService {
    private headers = new Headers({'Content-Type': 'application/json'});
    
    private user: User;

    private registerUrl = '/api/user/register';

    constructor(private http: Http) { }

    getUser(): Promise<User> {
        return new Promise(resolve => {
       setTimeout(() => resolve(this.user), 100);
    });
    }

    register(email: string, password: string, username: string): void {

        this.http.post(this.registerUrl, {
            name: username,
            password: password,
            mail: email
        }, {
            headers: this.headers
          })
        .toPromise()
        .then(response => this.user = response.json() as User )
        .catch(this.handleError);

    }

      private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
      }
      
    
}
