import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Idea } from './../../model/idea';
import { IdeaService } from '../../service/idea.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';


@Component({
  selector: 'app-idea-eval',
  templateUrl: './idea-eval.component.html',
  styleUrls: ['./idea-eval.component.css']
})
export class IdeaEvalComponent implements OnInit {
  
  @Input() idea: Idea;
  @Output() onChangeIdea: EventEmitter<Boolean> = new EventEmitter<Boolean>();

  constructor(private service: IdeaService, private spinnerService: Ng4LoadingSpinnerService) { }


  ngOnInit() {
    
  }
  accept() {
    if (confirm("Are you sure to accept the idea?")){
      this.spinnerService.show();
      this.service.accept(this.idea).subscribe(
        (response) => {
          this.spinnerService.hide();
          this.onChangeIdea.emit(true);
          //this.listIdeas();
        },
        (err) => {
          this.spinnerService.hide();
        }
      );
    }


  }
  reject() {
    if (confirm("Are you sure to delete the idea?")){
      this.spinnerService.show();
      this.service.reject(this.idea.id).subscribe(
        (response) => {
          this.spinnerService.hide();
          this.onChangeIdea.emit(true);
          //this.listIdeas();
        },
        (err) => {
          this.spinnerService.hide();
        }
      );
    }
  }
}
