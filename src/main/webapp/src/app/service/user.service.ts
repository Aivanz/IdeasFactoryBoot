import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './../model/user';

@Injectable()
export class UserService {

  private URI_USER = '/user';

  constructor(private http: HttpClient) { }

  createAdmin(user: User) {
    return this.http.post<User>(this.URI_USER, user);
  }

}
