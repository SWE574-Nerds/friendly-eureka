import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms'; 
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import { AppComponent } from './app/app.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RouterOutlet } from '@angular/router';
import { WelcomeScreenComponent } from './components/welcome-screen/welcome-screen.component';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import { TermsofserviceComponent } from './components/termsofservice-screen/termsofservice-screen.component'
import { LoginFormComponent } from './components/login-form/login-form.component';
import { CardViewComponent } from './components/cardview/cardview.component';

import { UserService } from './services/user.service';
import { ListoryService } from './services/listory.service';
import { CreateService } from './services/create.service';
import { AnnotationService } from './services/annotation.service';
import { SimpleStore } from '../app/app/SimpleStore';

import { BackgroundDirective } from './components/cardview/backgrounder.directive';
import { CardComponent } from './components/cardview/card/card.component';
import { CreateFormComponent } from './components/create/create.component';
import { DetailComponent } from './components/detail/detail.component';
import { AnnotationSliderComponent } from './components/annotationSlider/annotationslider.component';
import { AnnotationListComponent } from './components/annotationSlider/annotationListItem/annolistitem.component';
import { MapsComponent } from './components/maps/maps.component';
import { MarkerListItem } from './components/maps/marker-listitem/marker-listitem.component';
import { AgmCoreModule } from '@agm/core';
import { SelectableTextComponent } from './components/selectabletext/selectabletext.component';
import { HighlightElementComponent } from './components/selectabletext/highlightelement/highlightelement.component';



import { TagInputModule } from 'ngx-chips';
import { SearchComponent } from './components/search/search.component';
import { SearchService } from './services/search.service';
import { SearchItemComponent } from './components/search/searchitem/searchitem.component';

import { CloudinaryModule } from '@cloudinary/angular-4.x';
import * as  Cloudinary from 'cloudinary-core';
import { TestComponent } from './components/test/test.component';
import { AgoBoxComponent } from './components/detail/agobox/agobox.component';

@NgModule({
  declarations: [
    AppComponent, 
    DashboardComponent, 
    WelcomeScreenComponent, 
    RegisterFormComponent, 
    TermsofserviceComponent, 
    LoginFormComponent,
    CardViewComponent,
    BackgroundDirective,
    CardComponent,
    CreateFormComponent,
    DetailComponent,
    AnnotationSliderComponent,
    AnnotationListComponent,
    MapsComponent,
    MarkerListItem,
    SelectableTextComponent,
    HighlightElementComponent,
    SearchComponent,
    SearchItemComponent,
    TestComponent,
    AgoBoxComponent
  ],
  imports: [
    TagInputModule, BrowserModule, BrowserAnimationsModule, FormsModule, AppRoutingModule,CommonModule, HttpModule, AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCJ4TFKjFJ7RN-J4dC_GSWy09mYlQZ6m5w'
    }), 
    CloudinaryModule.forRoot(Cloudinary, { cloud_name: 'onatbas'}),
  ],
  providers: [ SearchService, UserService, ListoryService, CreateService, AnnotationService, SimpleStore],
  bootstrap: [AppComponent]
})
export class AppModule { }
