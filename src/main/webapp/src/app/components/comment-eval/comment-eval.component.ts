import { Comment } from './../../model/comment';
import { CommentService } from './../../service/comment.service';
import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';

@Component({
  selector: 'app-comment-eval',
  templateUrl: './comment-eval.component.html',
  styleUrls: ['./comment-eval.component.css']
})
export class CommentEvalComponent implements OnInit {
  comments: Array<Comment>;

  constructor(private service: CommentService, private router: Router,
    private spinnerService: Ng4LoadingSpinnerService) { }

  ngOnInit() {
    this.listComments();
  }
  listComments() {
    this.service.readAllCommentsEval().subscribe (
      (response) => {
        this.comments = response;
      }
    );
  }
  accept(comment: Comment) {
    if (confirm("Are you sure to accept the comment?")){
      this.spinnerService.show();
      this.service.accept(comment).subscribe(
        (response) => {
          this.spinnerService.hide();
          this.listComments();
        },
        (err) => {
          this.spinnerService.hide();
        }
      );
    }


  }
  reject(id: number) {
    if (confirm("Are you sure to delete the comment?")){
      this.spinnerService.show();
      this.service.reject(id).subscribe(
        (response) => {
          this.spinnerService.hide();
          this.listComments();
        },
        (err) => {
          this.spinnerService.hide();
        }
      );
    }
  }
}
