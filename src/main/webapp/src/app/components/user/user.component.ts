import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { User } from '../../model/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  model: any = {};

  user: User = new User();

  constructor(
    private service: UserService, 
    private spinnerService: Ng4LoadingSpinnerService,
    private router: Router) { }

  ngOnInit() {

    this.user = {
      id: 0,
      username: '',
      mail: '',
      password: '',
      confirmPassword: ''
  }

  }

  register(model: User, isValid: boolean) {

    if (isValid){
      this.spinnerService.show();
      this.service.createAdmin(model).subscribe (
        (response) => {
          this.spinnerService.hide();
          alert('Admin created successfully.');
          this.router.navigate(['']);
  
        },
        (err) => {
          this.spinnerService.hide();
          alert('Impossible to create admin');
        }
      );
    }


  }
}
