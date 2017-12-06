import { Injectable } from '@angular/core';
import { User } from './user';
import { UserService } from './user.service';
import { Headers, Http } from '@angular/http';
import { Router } from '@angular/router';
import { Card } from '../components/cardview/card';
import { Listory } from './Listory';

import 'rxjs/add/operator/toPromise';


const list1:Listory= {
    createdAt: null,
    description:  "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Voluptatem voluptatum voluptates sunt beatae distinctio ea, praesentium facilis minima consequuntur, inventore, recusandae quisquam dicta in? Quis atque ducimus suscipit corrupti dolores?",
    name: "Lorem Ipsum",
    image: "http://riverbuoys.co.za/wp-content/uploads/2015/01/boat.jpg",
    listoryId: "5",
    markers: [],
    polylines: [],
    time: null,
    owner: {name: "onat", userId: "0"},
    page: "0",
    selector: null,
    tags: [{name: "istanbul", id:0},{name: "boats", id:0},{name: "sea", id:0}]
}

@Injectable()
export class SearchService {
    searchFor(keywords: string[]): Promise<Listory[]> {
        return new Promise((resolve) => {
            setTimeout(() => resolve(
                [
                    list1,list1,list1,list1,list1
                ]
            ), 100);
        });
    }
}