import { Component } from '@angular/core';

import { ProductService } from '@appServices/product.service';
import { CartService } from '@appServices/cart.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent {
    products;

    constructor(
      private productService: ProductService
      , private cartService: CartService
    ){
    }

    ngOnInit() {
     this.productService.getItems()
      .subscribe(params => {
        this.products = params;
      });
    }

    addToCart(productPos){
      const product = this.products[productPos];
      this.cartService.addToCart(product);
    }
  }

