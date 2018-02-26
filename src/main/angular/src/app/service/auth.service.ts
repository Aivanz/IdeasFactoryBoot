import { HttpClient } from '@angular/common/http';
import { User } from './../model/user';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable()
export class AuthService {
  private URI_AUTH = '/login';
  private URI_AUTH_OUT = '/logout/';

  constructor(
    private http: HttpClient,
    public jwtHelperService: JwtHelperService
  ) {}
  
  isUserLoggedIn(): boolean {
    const token: string = this.jwtHelperService.tokenGetter()

    if (!token) {
      return false
    }
    const tokenExpired: boolean = this.jwtHelperService.isTokenExpired(token)
    return !tokenExpired
  }
  
  logIn(user: User): void {
    this.http.post(this.URI_AUTH, user, { observe: 'response' }).subscribe(
      (response) => {
        localStorage.setItem('user', user.username);
        localStorage.setItem('token', response.headers.get('Authorization'));
      },
      (err) => {
        alert('Username or password is wrong');
      }
    );
  }
  logOut(): void {
    this.http.post(this.URI_AUTH_OUT, new User());
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  }
}
