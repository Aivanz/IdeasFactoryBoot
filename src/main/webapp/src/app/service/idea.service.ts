import { HttpClient } from '@angular/common/http';
import { Idea } from './../model/idea';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class IdeaService {
  private URI_IDEA = '/idea';
  ideas: Array<Idea> = [];


  constructor(private http: HttpClient) {}

  createIdea(idea: Idea) {
    return this.http.post<Idea>(this.URI_IDEA, idea);
  }

  readAllIdeas() {
    return this.http.get<Array<Idea>>(this.URI_IDEA);
  }

  readIdeas(id: number) {
    return this.http.get<Idea>(this.URI_IDEA + '/' + id);
  }

  readAllIdeasEval(): Observable<Array<Idea>> {
    return this.http.get<Array<Idea>>(this.URI_IDEA + '/evaluating');
  }

  updateIdea(idea: Idea) {
    alert('aggiornato');
  }

  deleteIdea(idea: Idea) {
    return this.http.delete<Idea>(this.URI_IDEA + '/' + idea.id);
  }

  accept(idea: Idea) {
    return this.http.put<Idea>(this.URI_IDEA + '/accepting/'+idea.id, idea);
  }
  reject(id: number) {
    return this.http.delete<Idea>(this.URI_IDEA + '/' + id);
  }
}
