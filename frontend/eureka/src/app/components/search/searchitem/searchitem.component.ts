import { Component } from "@angular/core";
import { Input } from "@angular/core";
import { Listory } from "../../../services/Listory";
import { Router } from "@angular/router";

@Component({
    selector: "searchitem",
    styleUrls: ["searchitem.component.css"],
    templateUrl: "searchitem.component.html"
})
export class SearchItemComponent {
    constructor(private router: Router) { }


    @Input() listory: Listory;


    gotoItem() {
        console.log("Navigating to /detail" + this.listory.listoryId);
        this.router.navigate(['/detail', this.listory.listoryId]);
    }
}