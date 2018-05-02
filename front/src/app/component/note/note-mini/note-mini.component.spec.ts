import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NoteMiniComponent } from './note-mini.component';

describe('NoteMiniComponent', () => {
  let component: NoteMiniComponent;
  let fixture: ComponentFixture<NoteMiniComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoteMiniComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteMiniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
