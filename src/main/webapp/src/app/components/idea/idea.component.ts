import { AuthService } from './../../service/auth.service';
import { IdeaService } from './../../service/idea.service';
import { Idea } from "./../../model/idea";
import { Component, OnInit, Input } from "@angular/core";
import { Router } from "@angular/router";

@Component({
  selector: "app-idea",
  templateUrl: "./idea.component.html",
  styleUrls: ["./idea.component.css"]
})
export class IdeaComponent implements OnInit {
  @Input() idea;
  @Input() color;
  colors: Array<string> = [
    "bg-primary",
    "bg-success",
    "bg-danger",
    "bg-warning",
    "bg-info"
  ];

  constructor(private service: IdeaService, private auth: AuthService, private router: Router) {}

  ngOnInit() {}

  deleteIdea() {
    this.service.deleteIdea(this.idea).subscribe(
      (response) => {
      }
    );
  }
  modifyIdea() {
    this.router.navigate(['idea', this.idea.id, 'edit']);
  }
  commentIdea() {
    this.router.navigate(['comment', this.idea.id]);
  }
  isUserLoggedIn(): boolean {
    return this.auth.isUserLoggedIn();
  }
}
