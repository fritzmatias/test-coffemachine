import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@appEnv/environment';
import { catchError, retry } from 'rxjs/operators';
import {ErrorHandlerService} from '@appServices/error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

constructor(
  private http: HttpClient
  , private errorHandler: ErrorHandlerService
) { }

  getItems(){
    const products = this.http.get(environment.porductListingURL)
          .pipe(catchError(this.errorHandler.handleError));

    return products;
  }

  getProduct(productId){
    const details = this.http.get(environment.porductURL + '/' + productId)
          .pipe(catchError(this.errorHandler.handleError));

    return details;
  }

}
