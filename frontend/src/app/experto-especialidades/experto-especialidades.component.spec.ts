import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpertoEspecialidadesComponent } from './experto-especialidades.component';

describe('ExpertoEspecialidadesComponent', () => {
  let component: ExpertoEspecialidadesComponent;
  let fixture: ComponentFixture<ExpertoEspecialidadesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpertoEspecialidadesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpertoEspecialidadesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
