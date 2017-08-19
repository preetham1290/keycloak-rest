import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PublicRoutingModule } from './public-routing.module';
import { HomepageComponent } from './homepage/homepage.component';
import { LandingComponent } from './landing/landing.component';
import { AboutComponent } from './about/about.component';
import { SamplePostComponent } from './sample-post/sample-post.component';
import { ContactComponent } from './contact/contact.component';
import { NavigationComponent } from './navigation/navigation.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  imports: [
    CommonModule,
    PublicRoutingModule
  ],
  declarations: [HomepageComponent, LandingComponent, AboutComponent, SamplePostComponent, ContactComponent, NavigationComponent, FooterComponent]
})
export class PublicModule { }
