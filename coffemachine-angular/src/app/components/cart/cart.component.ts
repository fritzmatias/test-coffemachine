import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray } from '@angular/forms';

import { CartService } from '@appServices/cart.service';
import { JsonPipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
    // formItems = new FormControl();
    checkoutForm: FormGroup;
    amount;

  constructor(
      private cartService: CartService,
      private formBuilder: FormBuilder,
      private router: Router
  ) {
      this.checkoutForm = this.formBuilder.group({
      name: '',
      items: this.formBuilder.array(this.cartService.getItems())
    });
      this.cartService.calculatePurchase(this.items.value).subscribe(amount => {
        this.amount = amount;
      });
   }

  get items(): FormArray {
    return this.checkoutForm.get('items') as FormArray;
  }

  ngOnInit() {
  }

  onSubmit() {
    console.info(`Your purchased has been made.`);
    //purchase funnel here.
    window.alert("Your purchase has been made. You are going to be redirected to home page.")
    this.cartService.clearCart();
    this.checkoutForm.reset();
    this.router.navigate(['']);
  }

  onCancel(){
    window.alert("Your purchase has been canceled. You are going to be redirected to home page.")
    this.checkoutForm.reset();
    this.cartService.clearCart();
    this.router.navigate(['']);
  }
}
