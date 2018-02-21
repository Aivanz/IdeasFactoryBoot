import { Component, OnInit } from '@angular/core';
import { Comment } from './../../model/comment';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Router, ActivatedRoute } from '@angular/router';
import { CommentService } from './../../service/comment.service';

@Component({
  selector: 'app-comment-update',
  templateUrl: './comment-update.component.html',
  styleUrls: ['./comment-update.component.css']
})
export class CommentUpdateComponent implements OnInit {

  textareaLength: number;

  comment : Comment = new Comment();
  
  constructor(
    private service: CommentService, 
    private spinnerService: Ng4LoadingSpinnerService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.textareaLength = 500;
    this.spinnerService.show();
    this.route.params.subscribe(
      (params) => {
        this.service.readCommentsById(+params.id).subscribe(

          (response) => {
            this.spinnerService.hide();
            this.comment = response;
          },

          (err) => {
            alert("Error");
            this.spinnerService.hide();
          }

        );
      }
    );
  }

  updateComment() {
    this.spinnerService.show();
    this.comment.dateComment = null;
    this.service.updateComment(this.comment).subscribe(
      (response) => {
        this.spinnerService.hide();
        alert("Comment updated successfully");
        this.router.navigate(['']);
      },
      (err) => {
        this.spinnerService.hide();
        alert("Comment updated error");
        this.router.navigate(['']);
      }
    );
  }
}
