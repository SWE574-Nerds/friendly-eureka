import { Injectable } from '@angular/core';
import { User } from './user';
import { UserService } from './user.service';
import { Headers, Http } from '@angular/http';
import { Router } from '@angular/router';
import { Card } from '../components/cardview/card';
import { Listory } from './Listory';

import 'rxjs/add/operator/toPromise';
import { Buffer } from 'buffer';

@Injectable()
export class SearchService {

    constructor(
        private http: Http
    ) { }

    getSearchUrl(keywords: string) {
        var searchUrl = '/api/listory/search/base64/:keywords';
        var buffer = new Buffer(keywords).toString('base64')
        console.log(searchUrl.replace(":keywords", buffer));
        return searchUrl.replace(":keywords", buffer);
    }

    searchFor(keywords: string): Promise<Listory[]> {
        return new Promise<Listory[]>(resolve => {
            var url = this.getSearchUrl(keywords);
            console.log(url);
            this.http.get(url)
                .toPromise()
                .then((resp) => {
                    var listory: Listory[] = resp.json();
                    resolve(listory);
                });
        });
    }
}