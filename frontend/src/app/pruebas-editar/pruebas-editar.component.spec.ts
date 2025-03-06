import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PruebasEditarComponent } from './pruebas-editar.component';

describe('PruebasEditarComponent', () => {
  let component: PruebasEditarComponent;
  let fixture: ComponentFixture<PruebasEditarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PruebasEditarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PruebasEditarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
