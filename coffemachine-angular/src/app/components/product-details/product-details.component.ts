import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '@appServices/product.service';
import { CartService } from '@appServices/cart.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  product;
  products;
  mySubscription: any;

  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private cartService: CartService,
      private productService: ProductService
  ) {
  }

  ngOnInit() {
    // this.products = this.productService.getItems().toPromise();
    this.mySubscription = this.route.paramMap.subscribe(params => {
        // this.product = this.products[+params.get('productId')];
        const productId = params.get('productId');
        this.productService.getProduct(productId).subscribe(details => {
          this.product = details;
        });
      });
  }

  ngOnDestroy() {
    if (this.mySubscription) {
      this.mySubscription.unsubscribe();
    }
  }

  addToCart(product){
        this.cartService.addToCart(product);
  }

  public gotoProductDetailsV2(url, id) {

    var myurl = `${url}/${id}`;
    this.router.navigateByUrl(myurl).then(e => {
      if (e) {
        this.productService.getProduct(id).subscribe(p => {
          this.product = p;
          this.addToCart(this.product);
        } );
      } else {
        console.error("Navigation has failed!");
      }
    });
  }

}
