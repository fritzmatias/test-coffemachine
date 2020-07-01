import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from '@appEnv/environment';

@Injectable({
  providedIn: 'root'
})
export class CartService {
items = [];

constructor(
    private http: HttpClient
    ) { }
  addToCart(product) {
    if (!product) {
      console.warn(`product is undefined on cart's addition. Skipping`);
      return;
    }
    this.items.push(product);
  }

  getItems() {
    return this.items;
  }

  clearCart() {
    this.items = [];
    return this.items;
  }

  calculatePurchase(items) {
    return this.http.post(environment.calculatePurchaseURL, items);
  }
}
