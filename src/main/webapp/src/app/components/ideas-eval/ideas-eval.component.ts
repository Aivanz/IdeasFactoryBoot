import { Component, OnInit } from '@angular/core';
import { Idea } from '../../model/idea';
import { IdeaService } from '../../service/idea.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';

@Component({
  selector: 'app-ideas-eval',
  templateUrl: './ideas-eval.component.html',
  styleUrls: ['./ideas-eval.component.css']
})
export class IdeasEvalComponent implements OnInit {
  ideas: Array<Idea>;
  selectedIdea: number;

  constructor(private service: IdeaService, private spinnerService: Ng4LoadingSpinnerService) { }

  ngOnInit() {
    this.listIdeas();
  }
  listIdeas() {
    this.service.readAllIdeasEval().subscribe (
      (response) => {
        this.ideas = response;
      }
    );
  }

  

}
