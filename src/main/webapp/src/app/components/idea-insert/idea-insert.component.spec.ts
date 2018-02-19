import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IdeaInsertComponent } from './idea-insert.component';

describe('IdeaInsertComponent', () => {
  let component: IdeaInsertComponent;
  let fixture: ComponentFixture<IdeaInsertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IdeaInsertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdeaInsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
