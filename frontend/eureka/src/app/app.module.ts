import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';
import { FormsModule }   from '@angular/forms'; 


import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard.component';
import { RouterOutlet } from '@angular/router';
import { WelcomeScreenComponent } from './welcome-screen.component';
import { RegisterFormComponent } from './register-form.component';

import { UserService } from './user.service';

@NgModule({
  declarations: [
    AppComponent, DashboardComponent, WelcomeScreenComponent, RegisterFormComponent
  ],
  imports: [
    BrowserModule, FormsModule, AppRoutingModule, HttpModule
  ],
  providers: [ UserService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
