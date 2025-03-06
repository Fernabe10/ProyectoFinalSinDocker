import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpertosCrearComponent } from './expertos-crear.component';

describe('ExpertosCrearComponent', () => {
  let component: ExpertosCrearComponent;
  let fixture: ComponentFixture<ExpertosCrearComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpertosCrearComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpertosCrearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
