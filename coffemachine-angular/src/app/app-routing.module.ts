import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CartComponent } from './components/cart/cart.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { ProductListComponent } from './components/product-list/product-list.component';

const routes: Routes = [
  {path : '', component : ProductListComponent}
  , { path : 'products/:productId' , component : ProductDetailsComponent}
  , { path: 'cart', component: CartComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,
    {onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
