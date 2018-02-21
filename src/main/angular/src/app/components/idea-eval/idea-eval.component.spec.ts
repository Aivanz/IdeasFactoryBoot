import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IdeaEvalComponent } from './idea-eval.component';

describe('IdeaEvalComponent', () => {
  let component: IdeaEvalComponent;
  let fixture: ComponentFixture<IdeaEvalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IdeaEvalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdeaEvalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
