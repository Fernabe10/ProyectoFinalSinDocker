import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PruebasCrearComponent } from './pruebas-crear.component';

describe('PruebasCrearComponent', () => {
  let component: PruebasCrearComponent;
  let fixture: ComponentFixture<PruebasCrearComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PruebasCrearComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PruebasCrearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
