import { User } from './../../model/user';
import { NavComponent } from './../nav/nav.component';
import { Routes } from '@angular/router';
import { IdeaService } from './../../service/idea.service';
import { Idea } from './../../model/idea';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ideas',
  templateUrl: './ideas.component.html',
  styleUrls: ['./ideas.component.css']
})
export class IdeasComponent implements OnInit {
  ideas: Array<Idea>;
  selectedIdea: number;

  constructor(private service: IdeaService) {}

  ngOnInit() {
    this.listIdeas();
  }

  listIdeas() {
    this.service.readAllIdeas().subscribe (
      (response) => {
        this.ideas = response;
      }
    );
  }
}
