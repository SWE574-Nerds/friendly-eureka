import { Component } from "@angular/core";
import { Input } from "@angular/core";
import { Listory } from "../../../services/Listory";

@Component({
    selector: "searchitem",
    styleUrls: ["searchitem.component.css"],
    templateUrl: "searchitem.component.html"
})
export class SearchItemComponent{

    @Input() listory: Listory;

}