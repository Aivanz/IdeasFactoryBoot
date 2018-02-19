import { RouteGuardService } from './service/route-guard.service';
import { AuthService } from './service/auth.service';
import { CommentService } from './service/comment.service';
import { IdeaService } from './service/idea.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { NavComponent } from './components/nav/nav.component';
import { IdeasComponent } from './components/ideas/ideas.component';
import { LoginComponent } from './components/login/login.component';
import { CommentsComponent } from './components/comments/comments.component';
import { IdeaComponent } from './components/idea/idea.component';
import { CommentComponent } from './components/comment/comment.component';
import { IdeaUpdateComponent } from './components/idea-update/idea-update.component';
import { IdeaInsertComponent } from './components/idea-insert/idea-insert.component';
import { UserComponent } from './components/user/user.component';
import { IdeaEvalComponent } from './components/idea-eval/idea-eval.component';
import { CommentEvalComponent } from './components/comment-eval/comment-eval.component';


@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    IdeasComponent,
    LoginComponent,
    CommentsComponent,
    IdeaComponent,
    CommentComponent,
    IdeaUpdateComponent,
    IdeaInsertComponent,
    UserComponent,
    IdeaEvalComponent,
    CommentEvalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularFontAwesomeModule,
    FormsModule,
    HttpClientModule,
    NgbModule.forRoot()
  ],
  providers: [
    IdeaService,
    CommentService,
    AuthService,
    RouteGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
