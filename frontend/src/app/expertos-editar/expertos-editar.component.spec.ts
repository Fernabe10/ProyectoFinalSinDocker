import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpertosEditarComponent } from './expertos-editar.component';

describe('ExpertosEditarComponent', () => {
  let component: ExpertosEditarComponent;
  let fixture: ComponentFixture<ExpertosEditarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpertosEditarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpertosEditarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
