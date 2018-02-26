import { Injectable, Injector } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { Router } from '@angular/router';
import 'rxjs/add/observable/throw'
import 'rxjs/add/operator/catch';

@Injectable()
export class InterceptorHttp implements HttpInterceptor {

    constructor(private router: Router) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
     
        let token = '';
        if (localStorage.getItem('token')){
          token = localStorage.getItem('token');
        }
        // Clone the request to add the new header.
        const authReq = req.clone({ headers: req.headers.set("Authorization", token)});
        return next.handle(authReq)
        .catch((error, caught) => {
            //intercept the respons error and displace it to the console
            localStorage.removeItem('user');
            localStorage.removeItem('token');
            this.router.navigate(['/login']);
            //return the error to the method that called it
            return Observable.throw(error);
        }) as any;    
    }
}
