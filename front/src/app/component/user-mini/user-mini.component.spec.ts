import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserMiniComponent } from './user-mini.component';

describe('UserMiniComponent', () => {
  let component: UserMiniComponent;
  let fixture: ComponentFixture<UserMiniComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserMiniComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserMiniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
