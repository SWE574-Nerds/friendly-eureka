import { Component, Input, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { ListoryService } from '../../services/listory.service';
import { Listory } from '../../services/Listory';
import { User } from '../../services/user';
import { UserService } from '../../services/user.service';
import { AnnotationService } from '../../services/annotation.service';
import { AnnotationSelector } from '../../services/AnnotationSelector';
import { AnnotationFormat } from '../../services/AnnotationFormat';
import { Geometry } from '../../services/Geometry';


import 'rxjs/add/operator/switchMap';
declare var anno: any;
declare var jQuery: any;

@Component({
  selector: 'detail',
    templateUrl: './detail.component.html',
    styleUrls: [ './detail.component.css' ],
})

export class DetailComponent implements OnInit {
constructor(
  private route: ActivatedRoute,
  private router: Router,
  private location: Location,
  private listoryService: ListoryService,
  private  userService: UserService,
  private annotationService: AnnotationService,
) {}

private user: User = new User();
private annotationSelector: AnnotationSelector = new AnnotationSelector();

private selections = [];

private listory: Listory;
private markersConfig = {
  editable: false,
  polylines: [],
  markers: []
};

  ngAfterViewInit() {
    let that = this;

    setTimeout(function() {
        anno.makeAnnotatable(jQuery('#img_'+that.listory.listoryId)[0]);
        anno.addHandler('onAnnotationCreated', function(annotation) {

        that.annotationSelector.mediaType = "text";
        that.annotationSelector.description = annotation.text;
        that.annotationSelector.shape = annotation.shapes[0].geometry;
        that.annotationSelector.listoryId = that.listory.listoryId;
        that.annotationService.createAnnotation(that.annotationSelector)  // console.log(annotation.text);
        .then(annotationCreateResult => { console.log(annotationCreateResult);
        });
        });
        that.annotationService.getAnnotationsOfListory(that.listory.listoryId)
        .then(annotations => {
          debugger;
        for(let _annotation of annotations){
            let format = new AnnotationFormat();
            format.text = _annotation.body.value;
            format.shapes = [];
            format.context = window.location.href;
            format.src = that.listory.image;
            let geometry = new Geometry();
            geometry.geometry = _annotation.body.shape;
            geometry.type = "rect";
            format.shapes.push(geometry);
            anno.addAnnotation(format);
        }
         } );
      }, 1000);
  }

  ngOnInit(): void {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.listoryService.getListory(+params.get('id')))
      .subscribe((listory) => {
        this.listory = listory;
        this.markersConfig.markers = this.listory.markers;
        this.markersConfig.polylines = this.listory.polylines;
      });

      this.userService.getUser().then(user => {
        this.user = user;
      });
  }

  deleteListory(listoryId): void{
    console.log(listoryId);
    this.listoryService.deleteListory(listoryId).then(()=>{
      this.router.navigate(['/all']);
    });
  }

  goBack(): void {
    this.location.back();
  }

}
