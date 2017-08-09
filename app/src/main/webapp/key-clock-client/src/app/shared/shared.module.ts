import { NgModule, ModuleWithProviders, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenericService } from './generic-service/generic.service';
import { KEYCLOAK_HTTP_PROVIDER, keycloakHttpFactory } from 'app/shared/keyclock/keycloak.http';
import { KeycloakService } from 'app/shared/keyclock/keycloak.service';
import { HttpModule } from '@angular/http';
import { HttpGenericService } from './generic-service/http-generic.service';

@NgModule({
  imports: [
    HttpModule
  ],
  declarations: [],
  exports: [],
  providers: [
  ],
})
export class SharedModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [KeycloakService, KEYCLOAK_HTTP_PROVIDER, GenericService, HttpGenericService]
    }
  }
  constructor( @Optional() @SkipSelf() parentModule: SharedModule) {
    if (parentModule) {
      throw new Error(
        'SharedModule is already loaded. Import it in the AppModule only');
    }
  }
}