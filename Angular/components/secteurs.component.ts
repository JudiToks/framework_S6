import { Component, OnInit } from '@angular/core';
import { Secteurs } from './secteurs.model'; 
import { SecteursService } from './secteurs.service';

@Component({
  selector: 'app-secteurs',
  templateUrl: './secteurs.component.html',
  styleUrls: ['./secteurs.component.css']
})
export class SecteursComponent implements OnInit {

  secteurs: Secteurs[] = [];
  nouvelSecteurs: Secteurs = {} as Secteurs; 
  secteursSelectionne: Secteurs = {} as Secteurs; 

  constructor(private secteursService: SecteursService) { }

  ngOnInit(): void {
    this.getSecteurs();
  }

  getSecteurs(): void {
    this.secteursService.getSecteurs().subscribe(
      (data: Secteurs[]) => {
        this.secteurs = data;
      },
      (error) => {
        console.log('Erreur lors de la récupération des secteurs : ', error);
      }
    );
  }

  insertSecteurs(): void {
    this.secteursService.insertSecteurs(this.nouvelSecteurs).subscribe(
      (secteurs: Secteurs) => {
        this.secteurs.push(secteurs);
        this.nouvelSecteurs = {} as Secteurs; 
      },
      (error) => {
        console.log('Erreur lors de linsertion de la secteurs : ', error);
      }
    );
  }

  updateSecteurs(): void {
    this.secteursService.updateSecteurs(this.secteursSelectionne).subscribe(
      (secteurs: Secteurs) => {
        const index = this.secteurs.findIndex(e => e.id === secteurst.id);
        if (index !== -1) {
          this.secteurs[index] = secteurs;
        }
        this.secteursSelectionne = {} as Secteurs; 
      },
      (error) => {
        console.log('Erreur lors de la mise à jour de la secteurs : ', error);
      }
    );
  }

  deleteSecteurs(id: number): void {
    this.secteursService.deleteSecteurs(id).subscribe(
      () => {
        this.secteurs = this.secteurs.filter(e => e.id !== id);
      },
      (error) => {
        console.log('Erreur lors de la suppression de la secteurs : ', error);
      }
    );
  }
}
