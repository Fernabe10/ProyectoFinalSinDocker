import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpertoParticipantesEditarComponent } from './experto-participantes-editar.component';

describe('ExpertoParticipantesEditarComponent', () => {
  let component: ExpertoParticipantesEditarComponent;
  let fixture: ComponentFixture<ExpertoParticipantesEditarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpertoParticipantesEditarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpertoParticipantesEditarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
