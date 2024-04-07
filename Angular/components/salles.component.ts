import { Component, OnInit } from '@angular/core';
import { Salles } from './salles.model'; 
import { SallesService } from './salles.service';

@Component({
  selector: 'app-salles',
  templateUrl: './salles.component.html',
  styleUrls: ['./salles.component.css']
})
export class SallesComponent implements OnInit {

  salles: Salles[] = [];
  nouvelSalles: Salles = {} as Salles; 
  sallesSelectionne: Salles = {} as Salles; 

  constructor(private sallesService: SallesService) { }

  ngOnInit(): void {
    this.getSalles();
  }

  getSalles(): void {
    this.sallesService.getSalles().subscribe(
      (data: Salles[]) => {
        this.salles = data;
      },
      (error) => {
        console.log('Erreur lors de la récupération des salles : ', error);
      }
    );
  }

  insertSalles(): void {
    this.sallesService.insertSalles(this.nouvelSalles).subscribe(
      (salles: Salles) => {
        this.salles.push(salles);
        this.nouvelSalles = {} as Salles; 
      },
      (error) => {
        console.log('Erreur lors de linsertion de la salles : ', error);
      }
    );
  }

  updateSalles(): void {
    this.sallesService.updateSalles(this.sallesSelectionne).subscribe(
      (salles: Salles) => {
        const index = this.salles.findIndex(e => e.id === sallest.id);
        if (index !== -1) {
          this.salles[index] = salles;
        }
        this.sallesSelectionne = {} as Salles; 
      },
      (error) => {
        console.log('Erreur lors de la mise à jour de la salles : ', error);
      }
    );
  }

  deleteSalles(id: number): void {
    this.sallesService.deleteSalles(id).subscribe(
      () => {
        this.salles = this.salles.filter(e => e.id !== id);
      },
      (error) => {
        console.log('Erreur lors de la suppression de la salles : ', error);
      }
    );
  }
}
