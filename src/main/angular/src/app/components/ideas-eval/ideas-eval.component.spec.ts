import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IdeasEvalComponent } from './ideas-eval.component';

describe('IdeasEvalComponent', () => {
  let component: IdeasEvalComponent;
  let fixture: ComponentFixture<IdeasEvalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IdeasEvalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdeasEvalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
