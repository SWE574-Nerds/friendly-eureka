import { Component } from "@angular/core";
import { Input, Output } from "@angular/core";
import { Listory } from "../../../services/Listory";
import { Router } from "@angular/router";
import { NavigationEnd } from "@angular/router";
import { EventEmitter } from '@angular/core';


@Component({
    selector: "searchitem",
    styleUrls: ["searchitem.component.css"],
    templateUrl: "searchitem.component.html"
})
export class SearchItemComponent {
    constructor(private router: Router) { 
         this.router.routeReuseStrategy.shouldReuseRoute = function(){
        return false;
     }


     this.router.events.subscribe((evt) => {
        if (evt instanceof NavigationEnd) {
           this.router.navigated = false;
           window.scrollTo(0, 0);
        }
    });
    }

    @Input() listory: Listory;

    @Output() onClick: EventEmitter<any> = new EventEmitter();


    gotoItem() {
        this.router.navigated = false;
        this.onClick.emit();
        this.router.navigate(['/detail', this.listory.listoryId]);
    }
}