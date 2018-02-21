import { HttpClient } from '@angular/common/http';
import { User } from './../model/user';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthService {
  private URI_AUTH = '/login';

  constructor(private http: HttpClient) {}
  isUserLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
  logIn(user: User): void {
    this.http.post(this.URI_AUTH, user, { observe: 'response' }).subscribe(
      (response) => {
        localStorage.setItem('token', user.username);
      },
      (err) => {
        alert('Username or password is wrong');
      }
    );
  }
  logOut(): void {
    localStorage.removeItem('token');
  }
}
