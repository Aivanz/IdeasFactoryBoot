import { Comment } from './../../model/comment';
import { CommentService } from './../../service/comment.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-comment-eval',
  templateUrl: './comment-eval.component.html',
  styleUrls: ['./comment-eval.component.css']
})
export class CommentEvalComponent implements OnInit {
  comments: Array<Comment>;

  constructor(private service: CommentService) { }

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
  accept(comment: Comment): void {
    this.service.accept(comment);
  }
  reject(id: number): void {
    this.service.reject(id);
  }
}
