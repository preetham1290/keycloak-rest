import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HelloModuleRoutingModule } from './hello-module-routing.module';
import { HelloComponent } from './hello/hello.component';

@NgModule({
  imports: [
    CommonModule,
    HelloModuleRoutingModule
  ],
  declarations: [HelloComponent]
})
export class HelloModuleModule { }
