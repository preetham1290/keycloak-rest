import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingComponent } from './landing/landing.component';
import { HomepageComponent } from './homepage/homepage.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { SamplePostComponent } from './sample-post/sample-post.component';
const routes: Routes = [{
  path: '',
  component: LandingComponent,
  children: [{
    path: 'home',
    component: HomepageComponent
  }, {
    path: 'about',
    component: AboutComponent
  }, {
    path: 'contact',
    component: ContactComponent
  }, {
    path: 'sample',
    component: SamplePostComponent
  }]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
