import { Idea } from './../../model/idea';
import { ActivatedRoute } from '@angular/router';
import { IdeaService } from './../../service/idea.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-idea-update',
  templateUrl: './idea-update.component.html',
  styleUrls: ['./idea-update.component.css']
})
export class IdeaUpdateComponent implements OnInit {
  idea: Idea = new Idea();

  constructor(private service: IdeaService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.params.subscribe(
      (params) => {
       // this.idea = this.service.readIdeas(+params.id);
      }
    );
  }
}
