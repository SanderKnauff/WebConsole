import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationConsoleComponent } from './application-console.component';

describe('ApplicationConsoleComponent', () => {
  let component: ApplicationConsoleComponent;
  let fixture: ComponentFixture<ApplicationConsoleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationConsoleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationConsoleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
