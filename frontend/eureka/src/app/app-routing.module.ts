import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
 
import { WelcomeScreenComponent } from './components/welcome-screen/welcome-screen.component';
import { TermsofserviceComponent } from './components/termsofservice-screen/termsofservice-screen.component'

const routes: Routes = [
  { path: '', redirectTo: '/welcome/signup', pathMatch: 'full' },
  { path: 'welcome/:title',  component: WelcomeScreenComponent },
  { path: 'termsofservice',  component: TermsofserviceComponent },
];
 
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}