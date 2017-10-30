import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
 
import { WelcomeScreenComponent } from './components/welcome-screen/welcome-screen.component';
import { TermsofserviceComponent } from './components/termsofservice-screen/termsofservice-screen.component'
import { CardViewComponent } from './components/cardview/cardview.component';

const routes: Routes = [
  { path: '', redirectTo: '/welcome', pathMatch: 'full' },
  { path: 'welcome',  component: WelcomeScreenComponent },
  { path: 'termsofservice',  component: TermsofserviceComponent },
  { path: 'all',  component: CardViewComponent },
];
 
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}