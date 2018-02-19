import { Router } from '@angular/router';
import { IdeaService } from './../../service/idea.service';
import { Idea } from './../../model/idea';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-idea-insert',
  templateUrl: './idea-insert.component.html',
  styleUrls: ['./idea-insert.component.css']
})
export class IdeaInsertComponent implements OnInit {
  idea: Idea = new Idea();

  constructor(private service: IdeaService, private router: Router) {}

  ngOnInit() {}
  newIdea() {
    this.service.createIdea(this.idea).subscribe (
      (response) => {
        alert('Create idea successfull.');
        this.router.navigate(['']);

      },
      (err) => {
        alert('Impossible create idea');
      }
    );
  }
}
