import { Component, Input, OnInit } from '@angular/core';
import { CreateService } from '../../services/create.service';
import { UserService } from '../../services/user.service';
import { Category } from '../../services/Category';
import { TimeInfo } from '../../services/TimeInfo';
import { Marker } from '../maps/Marker';
import { CreateForm } from '../../services/CreateForm';

import { WelcomeForm } from '../../components/welcome-screen/form';

@Component({
  selector: 'create-form',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})

export class CreateFormComponent implements OnInit{
  constructor(
    private createService: CreateService,
    private userService: UserService
    
  ){}
  
  public mapsConfig = {
    markers: [],
    editable: true
  }

  private categories: Category[] = [ {id:"1", name: "Cars"} ];
  private timeInfo: TimeInfo[] = [
    {id: "1", name: "Years", value_name: "YEAR", value_count: "1"},
    {id: "1", name: "Years Range", value_name: "YEAR", value_count: "2"}
  ];

  private selectedTime: TimeInfo;
  private selectedCategory: Category;
  @Input() date1: number;
  @Input() date2: number;
  @Input() description: string;
  @Input() title: string;
  @Input() mediaType:string;
  @Input() link:string;

  private message:string;

  ngOnInit(){
    this.createService.getCategories().then(categories => this.categories = categories);
    this.createService.getTimeInfos().then(times => this.timeInfo = times);    


    setInterval(()=>{
      
          this.mapsConfig.markers.forEach((item)=>{
            console.log(item);
          });
          }, 500);
  }

  onSubmit(){
    if (!this.title || this.title.length < 5){
      this.message = "Title can't be less than 5 characters";
    }else if (!this.description || this.description.length < 10){
      this.message = "Description can't be less than 10 characters";      
    }else if (!this.selectedTime || (this.selectedTime.value_count == "1" && !(this.date1 && this.date1 > 0 )) ){
      this.message = "Please type in the time value";      
    }else if (!this.selectedTime || (this.selectedTime.value_count == "2" && (
      !(this.date1 && this.date1 > 0 ) ||
      !(this.date2 && this.date2 > 0 )
    ) )
    ){
      this.message = "Please type in both time values";      
    }else{
      this.message = "";

      var form = new CreateForm();
      form.category = this.selectedCategory.id;
      form.name = this.title;
      form.description = this.description;
      form.markers = this.mapsConfig.markers;
      form.timeInfo = {
        id: this.selectedTime.id,
        value_1 : ''+this.date1,
        value_2 : ''+this.date2,
      }
      if (this.mediaType == "link")
      form.image = this.link;


      this.createService.createListory(form);
    }
  }
}