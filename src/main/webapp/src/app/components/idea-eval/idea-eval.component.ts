import { Component, OnInit } from '@angular/core';
import { Idea } from '../../model/idea';
import { IdeaService } from '../../service/idea.service';

@Component({
  selector: 'app-idea-eval',
  templateUrl: './idea-eval.component.html',
  styleUrls: ['./idea-eval.component.css']
})
export class IdeaEvalComponent implements OnInit {
  ideas: Array<Idea>;
  selectedIdea: number;

  constructor(private service: IdeaService) { }

  ngOnInit() {
    this.listIdeas();
  }
  listIdeas() {
    this.ideas = this.service.readAllIdeasEval();
  }
}
