import { Router } from '@angular/router';
import { AuthService } from './../../service/auth.service';
import { User } from './../../model/user';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();
  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit() {
  }
  login() {
    this.auth.logIn(this.user);
    this.router.navigate(['']);
  }
}
