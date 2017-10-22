import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
 
import { WelcomeScreenComponent }   from './welcome-screen.component';
 
const routes: Routes = [
  { path: '', redirectTo: '/welcome', pathMatch: 'full' },
  { path: 'welcome',  component: WelcomeScreenComponent },
];
 
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}