import { Idea } from './../../model/idea';
import { ActivatedRoute } from '@angular/router';
import { IdeaService } from './../../service/idea.service';
import { Component, OnInit } from '@angular/core';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Router } from '@angular/router';

@Component({
  selector: 'app-idea-update',
  templateUrl: './idea-update.component.html',
  styleUrls: ['./idea-update.component.css']
})
export class IdeaUpdateComponent implements OnInit {
  idea: Idea = new Idea();

  constructor(
    private service: IdeaService, 
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private router: Router,
  ) { }

  ngOnInit() {

    this.route.params.subscribe(
      (params) => {
        this.service.readIdeas(+params.id).subscribe(

          (response) => {
            this.idea = response;
          }

        );
      }
    );

  }

  updateIdea() {
    this.spinnerService.show();
    this.idea.comlist = null;
    this.idea.dateIdea = null;
    this.service.updateIdea(this.idea).subscribe(
      (response) => {
        this.spinnerService.hide();
        alert("Idea updated successfully");
        this.router.navigate(['']);
      },
      (err) => {
        this.spinnerService.hide();
        alert("Idea updated error");
      }
    );
  }
}
