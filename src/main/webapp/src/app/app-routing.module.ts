import { RouteGuardService } from './service/route-guard.service';
import { CommentEvalComponent } from './components/comment-eval/comment-eval.component';
import { IdeasEvalComponent } from './components/ideas-eval/ideas-eval.component';
import { UserComponent } from './components/user/user.component';
import { IdeaInsertComponent } from './components/idea-insert/idea-insert.component';
import { CommentsComponent } from './components/comments/comments.component';
import { LoginComponent } from './components/login/login.component';
import { IdeasComponent } from './components/ideas/ideas.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule, CanActivate } from '@angular/router';
import { IdeaUpdateComponent } from './components/idea-update/idea-update.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'idea'
  },
  {
    path: 'idea',
    pathMatch: 'full',
    component: IdeasComponent
  },
  {
    path: 'idea/edit',
    pathMatch: 'full',
    component: IdeaInsertComponent
  },
  {
    path: 'idea/:id/edit',
    pathMatch: 'full',
    component: IdeaUpdateComponent
  },
  {
    path: 'admin/edit',
    pathMatch: 'full',
    component: UserComponent,
    canActivate: [RouteGuardService]
  },
  {
    path: 'admin/eval/ideas',
    pathMatch: 'full',
    component: IdeasEvalComponent,
    canActivate: [RouteGuardService]
  },
  {
    path: 'admin/eval/comments',
    pathMatch: 'full',
    component: CommentEvalComponent,
    canActivate: [RouteGuardService]
  },
  {
    path: 'idea/:id/comments',
    pathMatch: 'full',
    component: CommentsComponent
  },
  {
    path: 'login',
    pathMatch: 'full',
    component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
