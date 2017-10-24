import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
 
import { WelcomeScreenComponent } from './components/welcome-screen/welcome-screen.component';
import { MyTestComponent } from './components/my-test/my-test.component'

const routes: Routes = [
  { path: '', redirectTo: '/welcome', pathMatch: 'full' },
  { path: 'welcome',  component: WelcomeScreenComponent },
  { path: 'test',  component: MyTestComponent },
];
 
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}