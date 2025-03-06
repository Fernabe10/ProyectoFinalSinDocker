import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemsService } from '../items.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../login.service';
import { EvaluacionService } from '../evaluacion.service';
import { EvaluacionItemService } from '../evaluacion-item.service';
import { PruebasService } from '../pruebas.service';

@Component({
  selector: 'app-pruebas-evaluar',
  imports: [CommonModule, FormsModule],
  templateUrl: './pruebas-evaluar.component.html',
  styleUrl: './pruebas-evaluar.component.css'
})
export class PruebasEvaluarComponent implements OnInit {
  items: any[] = [];
  idPrueba: number = 0;
  idParticipante: number = 0;
  idUser: number = 0;
  idEvaluacion: number = 0;
  notaPonderada: number = 0;

  evaluacion: any = {};
  

  constructor(
    private route: ActivatedRoute,
    private router:Router,
    private servicioItem: ItemsService,
    private servicioLogin: LoginService,
    private servicioEvaluacion: EvaluacionService,
    private servicioEvaluacionItem: EvaluacionItemService,
    private servicioPrueba:PruebasService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.idPrueba = +this.route.snapshot.paramMap.get('idPrueba')!;
      this.idParticipante = +this.route.snapshot.paramMap.get('idParticipante')!;
      this.idUser = this.servicioLogin.getIdUser();
  
      // Creamos la nueva evaluación y obtenemos su ID después
      this.evaluacion = {
        notaFinal: 0.0,
        participanteId: this.idParticipante,
        pruebaId: this.idPrueba,
        userId: this.idUser
      };
  
      this.insertarEvaluacion();
    });
  }


  
  insertarEvaluacion(): void {
    this.servicioEvaluacion.crearEvaluacion(this.evaluacion).subscribe(
      (respuesta) => {
        console.log("Evaluación insertada con éxito:", respuesta);
  
        // Una vez que la evaluación se ha creado, traemos la última ID
        this.servicioEvaluacion.traerLaUltimaId().subscribe(
          (data) => {
            this.idEvaluacion = data.idEvaluacion;
            console.log('ID de la evaluación creada:', this.idEvaluacion);
  
            // Ahora que tenemos la ID de la evaluación, obtenemos los ítems
            this.traerItemsDePrueba();
          },
          (error) => {
            console.error("Error al obtener la última evaluación:", error);
          }
        );
      },
      (error) => {
        console.error("Error al insertar la evaluación:", error);
        alert("Hubo un error al registrar la evaluación.");
      }
    );
  }
  
  

  traerItemsDePrueba(): void {
    this.servicioItem.obtenerItemsByIdPrueba(this.idPrueba).subscribe(
      (data) => {
        this.items = data;
        console.log(data);
      },
      (error) => {
        console.error('Error al obtener los ítems de la prueba', error);
      }
    );
  }

  InsertarNotas(): void {
    
    this.items.forEach(item => {
      
      const evaluacionItem = {
        explicacion: item.explicacion,  
        valoracion: item.valoracion,  
        evaluacionId: this.idEvaluacion,  
        itemId: item.idItem 
      };
  
      this.servicioEvaluacionItem.crearEvaluacionItem(evaluacionItem).subscribe(
        (respuesta) => {
          //console.log("Evaluación ITEM insertada con éxito:", respuesta);
          alert("Evaluación ITEM registrada correctamente.");
          this.ActualizarNotaEvaluacion();
          this.router.navigate(['experto/pruebas']); 
        },
        (error) => {
          console.error("Error al insertar la evaluación ITEM:", error);
        }
      );
    });
  }

  ActualizarNotaEvaluacion(): void {
    // obtenemos la prueba para conocer su puntuación máxima
    this.servicioPrueba.obtenerPruebaPorId(this.idPrueba).subscribe(
      (prueba) => {
        const puntuacionMaxima = prueba.puntuacionMaxima;
        let sumaPonderada = 0;
        let sumaPesos = 0;
  
        // Recorremos los ítems de la evaluación
        this.items.forEach((item) => {
          const peso = item.peso;
          const gradosConsecucion = item.gradosConsecucion;
          const valoracionUsuario = item.valoracion;
  
          if (gradosConsecucion > 0) {
            sumaPonderada += (valoracionUsuario / gradosConsecucion) * peso;
            sumaPesos += peso;
          }
        });
  
        
        if (sumaPesos === 0) {
          console.error("Error: La suma de los pesos es cero, no se puede calcular la nota final.");
          return;
        }
  
        // Calculamos la nota final usando la media ponderada
        let notaFinal = (sumaPonderada * puntuacionMaxima) / sumaPesos;
  
        // Redondeamos la nota final a 2 decimales
        notaFinal = Math.round(notaFinal * 100) / 100;
  
        // Actualizamos la evaluación con la nota final calculada
        this.evaluacion.notaFinal = notaFinal;
  
        this.servicioEvaluacion.actualizarEvaluacionPorId(this.idEvaluacion, this.evaluacion).subscribe(
          (respuesta) => {
            console.log("Nota de la evaluación ajustada correctamente:", respuesta);
          },
          (error) => {
            console.error("Error al actualizar la nota de la evaluación:", error);
          }
        );
      },
      (error) => {
        console.error("Error al obtener la prueba:", error);
      }
    );
  }


  calcularNotaPonderada(): void {
    if (!this.items || this.items.length === 0) {
      this.notaPonderada = 0;
      return;
    }
  
    let sumaPonderada = 0;
    let sumaPesos = 0;
  
    this.items.forEach(item => {
      const peso = item.peso;
      const gradosConsecucion = item.gradosConsecucion;
      const valoracionUsuario = item.valoracion ? item.valoracion : 0;
  
      if (gradosConsecucion > 0) {
        sumaPonderada += (valoracionUsuario * peso);
        sumaPesos += (gradosConsecucion * peso);
      }
    });
  
    this.notaPonderada = sumaPesos > 0 ? Math.round((sumaPonderada / sumaPesos) * 100) : 0;
  }
  
  
  
  
  
}
