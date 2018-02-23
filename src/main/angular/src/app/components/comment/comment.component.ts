import { Comment } from './../../model/comment';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommentService } from './../../service/comment.service';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {
  @Input() comment: Comment;
  @Output() onDeleteComment: EventEmitter<Boolean> = new EventEmitter<Boolean>();

  constructor(
    private service: CommentService,
    private spinnerService: Ng4LoadingSpinnerService,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit() {
  }

  modifyComment() {
    this.router.navigate(['comment', this.comment.id, 'edit']);
  }

  deleteComment() {
    if (confirm('Are you sure to delete the comment?')){
      this.spinnerService.show();
      this.service.reject(this.comment.id).subscribe(
        (response) => {
          this.spinnerService.hide();
          this.onDeleteComment.emit(true);
        },
        (err) => {
          this.spinnerService.hide();
        }
      );
    }
  }

  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }

}
