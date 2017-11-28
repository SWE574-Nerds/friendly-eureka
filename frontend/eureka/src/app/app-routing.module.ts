import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
 
import { WelcomeScreenComponent } from './components/welcome-screen/welcome-screen.component';
import { TermsofserviceComponent } from './components/termsofservice-screen/termsofservice-screen.component'
import { CardViewComponent } from './components/cardview/cardview.component';
import { CreateFormComponent } from './components/create/create.component';
import { DetailComponent } from './components/detail/detail.component';
import { AnnotationListComponent } from './components/annotationSlider/annotationListItem/annolistitem.component';
import { MapsComponent } from './components/maps/maps.component';

const routes: Routes = [
  { path: '', redirectTo: '/welcome', pathMatch: 'full' },
  { path: 'welcome',  component: WelcomeScreenComponent },
  { path: 'termsofservice',  component: TermsofserviceComponent },
  { path: 'all',  component: CardViewComponent },
  { path: 'create', component: CreateFormComponent },
  { path: 'detail/:id', component: DetailComponent },  
  { path: 'test', component: MapsComponent }

];
 
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}