import { IdeaService } from './../../service/idea.service';
import { Idea } from './../../model/idea';
import { Comment } from './../../model/comment';
import { CommentService } from './../../service/comment.service';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JsonPipe } from '@angular/common';

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
    private router: Router
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.ideaService.readIdeas(+params.id).subscribe (
        (response) => {
          this.idea = response;
        }
      );
    });
    this.comments = this.commentService.readAllComments(this.idea.id);
    this.fullComments = this.commentService.countComments() > 0 ? true : false;
  }
  newComment(e: Event) {
    e.preventDefault();
    this.commentService.createComment(this.idea.id, this.comment).subscribe (
      (response) => {
        alert('Insert comment');
        this.router.navigate(['']);
      },
      (err) => {
        alert(JSON.stringify(this.comment));
        alert('Error comment');
      }
    );
  }
}
