import { HttpClient } from '@angular/common/http';
import { Idea } from './../model/idea';
import { IdeaService } from './idea.service';
import { Comment } from './../model/comment';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class CommentService {
  private URI_COMMENT = '/comment';
  idea: Idea;
  constructor(private ideaService: IdeaService, private http: HttpClient) {}

  createComment(id: number, comment: Comment) {
    return this.http.post<Comment>(this.URI_COMMENT + '/' + id, comment );
  }
  readAllComments(id: number) {
//    this.idea = this.ideaService.readIdeas(id);
    return this.idea.commentsList;
  }
  updateComment(comment: Comment) {
    alert('aggiornato');
  }
  deleteComment() {
    alert('eliminato');
  }
  countComments(): number {
    return this.idea.commentsList.length;
  }
  readAllCommentsEval(): Observable<Array<Comment>> {
    return this.http.get<Array<Comment>>(this.URI_COMMENT);
  }
  accept(comment: Comment): void {
    this.http.put<Comment>(this.URI_COMMENT + '/accepting', comment);
  }
  reject(id: number): void {}
}
