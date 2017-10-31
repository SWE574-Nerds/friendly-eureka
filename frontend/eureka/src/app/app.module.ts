import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';
import { FormsModule }   from '@angular/forms'; 


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

import { BackgroundDirective } from './components/cardview/backgrounder.directive';
import { CardComponent } from './components/cardview/card/card.component';
import { CreateFormComponent } from './components/create/create.component';
import { DetailComponent } from './components/detail/detail.component';

import { SimpleStore } from '../app/app/SimpleStore';



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
    DetailComponent
  ],
  imports: [
    BrowserModule, FormsModule, AppRoutingModule, HttpModule
  ],
  providers: [ UserService, ListoryService, CreateService, SimpleStore],
  bootstrap: [AppComponent]
})
export class AppModule { }
