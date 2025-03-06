import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpertoParticipantesComponent } from './experto-participantes.component';

describe('ExpertoParticipantesComponent', () => {
  let component: ExpertoParticipantesComponent;
  let fixture: ComponentFixture<ExpertoParticipantesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpertoParticipantesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpertoParticipantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
