import { Router } from '@angular/router';
import { AuthService } from './../../service/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit() {}
  logOut(): void {
    this.auth.logOut();
    this.router.navigate(['']);
  }
  isUserLoggedIn(): boolean {
    return this.auth.isUserLoggedIn();
  }
}
