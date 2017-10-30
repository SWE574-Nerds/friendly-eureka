import { Injectable } from '@angular/core';
import { User } from './user';
import { Headers, Http } from '@angular/http';
import { Router } from '@angular/router';
import { Card } from '../components/cardview/card';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class ListoryService {
    private headers = new Headers({'Content-Type': 'application/json'});
    
    private listoryUrl = '/api/listory';
    
    constructor(private http: Http) { }

    getCards(): Promise<Card[]> {
        return new Promise(resolve => {
       setTimeout(() => resolve([
        {image: "http://blog.wallstreetsurvivor.com/wp-content/uploads/goog.jpg", title: "Modadaki tombili heykeli", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
        {image: "http://www.dormontappliance.com/media/icons/youtube/youtube-25x25.png", title: "Modadaki tombili heykeli", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
        {image: "http://img.wennermedia.com/social/rs-232620-massive-attack-moss.jpg", title: "Modadaki tombili heykeli", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
        {image: "", title: "Modadaki tombili", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
        {image: "", title: "Modadaki", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
       ]), 10);
    });
    }

      private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
      }
      
    
}
