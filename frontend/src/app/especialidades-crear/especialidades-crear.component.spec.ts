import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EspecialidadesCrearComponent } from './especialidades-crear.component';

describe('EspecialidadesCrearComponent', () => {
  let component: EspecialidadesCrearComponent;
  let fixture: ComponentFixture<EspecialidadesCrearComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EspecialidadesCrearComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EspecialidadesCrearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
