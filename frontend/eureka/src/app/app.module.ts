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

import { UserService } from './services/user.service';

@NgModule({
  declarations: [
    AppComponent, DashboardComponent, WelcomeScreenComponent, RegisterFormComponent, TermsofserviceComponent, LoginFormComponent
  ],
  imports: [
    BrowserModule, FormsModule, AppRoutingModule, HttpModule
  ],
  providers: [ UserService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
