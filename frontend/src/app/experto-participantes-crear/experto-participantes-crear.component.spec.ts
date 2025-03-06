import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpertoParticipantesCrearComponent } from './experto-participantes-crear.component';

describe('ExpertoParticipantesCrearComponent', () => {
  let component: ExpertoParticipantesCrearComponent;
  let fixture: ComponentFixture<ExpertoParticipantesCrearComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpertoParticipantesCrearComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpertoParticipantesCrearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
