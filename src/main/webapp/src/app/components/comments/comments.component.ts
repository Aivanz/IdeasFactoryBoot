import { IdeaService } from './../../service/idea.service';
import { Idea } from './../../model/idea';
import { Comment } from './../../model/comment';
import { CommentService } from './../../service/comment.service';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JsonPipe } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  idea: Idea = new Idea();
  comment: Comment = new Comment();
  comments: Array<Comment>;
  fullComments: boolean;

  constructor(
    private ideaService: IdeaService,
    private commentService: CommentService,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: Ng4LoadingSpinnerService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.ideaService.readIdeas(+params.id).subscribe (
        (response) => {
          this.idea = response;
          this.listComments(params.id);
        }
      );
    });

  }
  newComment(e: Event) {
    e.preventDefault();
    this.spinnerService.show();
    this.commentService.createComment(this.idea.id, this.comment).subscribe (
      (response) => {
        alert('Insert comment');
        this.router.navigate(['']);
        this.spinnerService.hide();
      },
      (err) => {
        alert('Error comment');
        this.spinnerService.hide();
      }
    );
  }

  listComments(id: number){
    this.commentService.readCommentsByIdeaId(id).subscribe(
    
      (response) => {
        this.comments = response;
      }
    
    );
  }

}
