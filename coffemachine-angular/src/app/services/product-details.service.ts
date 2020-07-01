import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@appEnv/environment';
import { catchError, retry } from 'rxjs/operators';
import { ErrorHandlerService } from '@appServices/error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class ProductDetailsService {

  constructor(
    private http: HttpClient
    , private errorHandler: ErrorHandlerService
  ) { }


}
