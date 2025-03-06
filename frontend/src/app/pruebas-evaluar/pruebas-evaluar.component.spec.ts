import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PruebasEvaluarComponent } from './pruebas-evaluar.component';

describe('PruebasEvaluarComponent', () => {
  let component: PruebasEvaluarComponent;
  let fixture: ComponentFixture<PruebasEvaluarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PruebasEvaluarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PruebasEvaluarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
