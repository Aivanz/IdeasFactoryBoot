import { HttpClient } from '@angular/common/http';
import { Idea } from './../model/idea';
import { IdeaService } from './idea.service';
import { Comment } from './../model/comment';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class CommentService {
  private URI_COMMENT = '/comment';
  private URI_IDEA = '/idea';
  
  idea: Idea;
  constructor(private ideaService: IdeaService, private http: HttpClient) {}

  createComment(id: number, comment: Comment) {
    return this.http.post<Comment>(this.URI_COMMENT + '/' + id, comment );
  }

  readCommentsByIdeaId(id: number) {
    return this.http.get<Array<Comment>>(this.URI_IDEA + '/comlist/' + id );
  }

  readCommentsById(id: number) {
    return this.http.get<Comment>(this.URI_COMMENT + '/' + id );
  }

  updateComment(comment: Comment) {
    return this.http.put<Comment>(this.URI_COMMENT, comment);
  }
 
  readAllCommentsEval(): Observable<Array<Comment>> {
    return this.http.get<Array<Comment>>(this.URI_COMMENT);
  }

  accept(comment: Comment) {
    return this.http.put<Comment>(this.URI_COMMENT + '/accepting/'+comment.id, comment);
  }

  reject(id: number) {
    return this.http.delete<Comment>(this.URI_COMMENT + '/' + id);
  }

  modify(comment: Comment) {
    return this.http.put<Comment>(this.URI_COMMENT, comment);
  }

}
