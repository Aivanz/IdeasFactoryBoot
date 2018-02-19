import { Router } from '@angular/router';
import { IdeaService } from './../../service/idea.service';
import { Idea } from './../../model/idea';
import { Component, OnInit, Input } from '@angular/core';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';

@Component({
  selector: 'app-idea-insert',
  templateUrl: './idea-insert.component.html',
  styleUrls: ['./idea-insert.component.css']
})
export class IdeaInsertComponent implements OnInit {
  idea: Idea = new Idea();

  constructor(private service: IdeaService, private router: Router,
    private spinnerService: Ng4LoadingSpinnerService) {}

  ngOnInit() {}
  newIdea() {
    this.spinnerService.show();
    this.service.createIdea(this.idea).subscribe (
      (response) => {
        this.spinnerService.hide();
        alert('Idea created successfully.');
        this.router.navigate(['']);

      },
      (err) => {
        this.spinnerService.hide();
        alert('Impossible to create idea');
      }
    );
  }
}
