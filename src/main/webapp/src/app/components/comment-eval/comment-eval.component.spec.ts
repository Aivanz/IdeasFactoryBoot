import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentEvalComponent } from './comment-eval.component';

describe('CommentEvalComponent', () => {
  let component: CommentEvalComponent;
  let fixture: ComponentFixture<CommentEvalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentEvalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentEvalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
