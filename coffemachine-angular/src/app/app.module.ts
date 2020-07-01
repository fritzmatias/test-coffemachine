import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import {ProductListComponent} from './components/product-list/product-list.component';
import {ProductDetailsComponent} from './components/product-details/product-details.component';
import {ProductAlertsComponent} from './components/product-alerts/product-alerts.component';
import {CartComponent} from './components/cart/cart.component';
import {TopBarComponent} from './top-bar/top-bar.component';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  declarations: [
      AppComponent,
      TopBarComponent,
      ProductListComponent,
      ProductAlertsComponent,
      ProductDetailsComponent,
      CartComponent
   ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
