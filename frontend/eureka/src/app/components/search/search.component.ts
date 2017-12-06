import { Component, Input, OnInit } from '@angular/core';
import { SearchService } from '../../services/search.service';
import { Listory } from '../../services/Listory';

const WAIT_TIMEOUT = 500; // in milliseconds.

@Component({
  selector: 'search',
  templateUrl: './search.component.html',
  styleUrls:  [ './search.component.css' ],
})
export class SearchComponent {

    private searchString: string;
    private listories: Listory[];

  constructor(
      private searchService: SearchService
  ){}

  onSearchStringChange(){
      var currString = this.searchString;

      setTimeout(()=>{
        var newString = this.searchString;
        if (newString === currString) // The input havent changed
            this.searchFor(currString);
      }, WAIT_TIMEOUT);
  }


  searchFor(searchString){
      this.searchService.searchFor(searchString).then((listories:Listory[])=>{
          console.log(listories);
        this.listories = listories;
      });
  }

}