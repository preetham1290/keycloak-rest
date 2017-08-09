import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { RootAuthGuardGuard } from 'app/root-auth-guard.guard';
import { SharedModule } from 'app/shared/shared.module';
import { InitResolveService } from './init-resolve.service';
import { WrapperComponent } from './wrapper/wrapper.component';

const appRoutes: Routes = [{
  path: '',
  component: WrapperComponent,
  resolve: {
    kcInit: InitResolveService
  },
  children: [],
  pathMatch: 'full'
}, {
  path: 'home',
  loadChildren: 'app/hello-module/hello-module.module#HelloModuleModule',
  canActivate: [RootAuthGuardGuard]
}
];


@NgModule({
  declarations: [
    AppComponent,
    WrapperComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    SharedModule.forRoot(),
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    RootAuthGuardGuard,
    InitResolveService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }