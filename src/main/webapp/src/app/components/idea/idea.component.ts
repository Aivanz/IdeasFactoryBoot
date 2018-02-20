import { AuthService } from './../../service/auth.service';
import { IdeaService } from './../../service/idea.service';
import { Idea } from "./../../model/idea";
import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { Router } from "@angular/router";
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';

@Component({
  selector: "app-idea",
  templateUrl: "./idea.component.html",
  styleUrls: ["./idea.component.css"]
})
export class IdeaComponent implements OnInit {
  @Input() idea : Idea;
  @Input() color;
  @Output() onDeleteIdea: EventEmitter<Boolean> = new EventEmitter<Boolean>();

  colors: Array<string> = [
    "bg-primary",
    "bg-success",
    "bg-danger",
    "bg-warning",
    "bg-info"
  ];
  currentRate: number;

  constructor(
    private service: IdeaService, 
    private auth: AuthService, 
    private router: Router,
    private spinnerService: Ng4LoadingSpinnerService
  ) {}

  ngOnInit() {
  }

  deleteIdea() {
    this.spinnerService.show();
    this.service.deleteIdea(this.idea).subscribe(
      (response) => {
        this.spinnerService.hide();
        alert("Idea deleted successfully");
        this.onDeleteIdea.emit(true);
      },
      (err) => {
        this.spinnerService.hide();
        alert("Error delete");
      }
    );
  }

  modifyIdea() {
    this.router.navigate(['idea', this.idea.id, 'edit']);
  }

  commentIdea() {
    this.router.navigate(['idea', this.idea.id, 'comments']);
  }

  isUserLoggedIn(): boolean {
    return this.auth.isUserLoggedIn();
  }

  vote(vote: number) {
    this.idea.comlist = null;
    this.idea.dateIdea = null;
    this.spinnerService.show();
    this.service.vote(this.idea, vote).subscribe(
      (response) => {
        this.spinnerService.hide();
        alert('Vote received');
        this.idea.voteaverage = response.voteaverage;
      },
      (err) => {
        this.spinnerService.hide();
        alert('Error');
      }
    );
  }
}
