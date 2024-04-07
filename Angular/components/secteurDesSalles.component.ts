import { Component, OnInit } from '@angular/core';
import { SecteurDesSalles } from './secteurDesSalles.model'; 
import { SecteurDesSallesService } from './secteurDesSalles.service';

@Component({
  selector: 'app-secteurDesSalles',
  templateUrl: './secteurDesSalles.component.html',
  styleUrls: ['./secteurDesSalles.component.css']
})
export class SecteurDesSallesComponent implements OnInit {

  secteurDesSalles: SecteurDesSalles[] = [];
  nouvelSecteurDesSalles: SecteurDesSalles = {} as SecteurDesSalles; 
  secteurDesSallesSelectionne: SecteurDesSalles = {} as SecteurDesSalles; 

  constructor(private secteurDesSallesService: SecteurDesSallesService) { }

  ngOnInit(): void {
    this.getSecteurDesSalles();
  }

  getSecteurDesSalles(): void {
    this.secteurDesSallesService.getSecteurDesSalles().subscribe(
      (data: SecteurDesSalles[]) => {
        this.secteurDesSalles = data;
      },
      (error) => {
        console.log('Erreur lors de la récupération des secteurDesSalles : ', error);
      }
    );
  }

  insertSecteurDesSalles(): void {
    this.secteurDesSallesService.insertSecteurDesSalles(this.nouvelSecteurDesSalles).subscribe(
      (secteurDesSalles: SecteurDesSalles) => {
        this.secteurDesSalles.push(secteurDesSalles);
        this.nouvelSecteurDesSalles = {} as SecteurDesSalles; 
      },
      (error) => {
        console.log('Erreur lors de linsertion de la secteurDesSalles : ', error);
      }
    );
  }

  updateSecteurDesSalles(): void {
    this.secteurDesSallesService.updateSecteurDesSalles(this.secteurDesSallesSelectionne).subscribe(
      (secteurDesSalles: SecteurDesSalles) => {
        const index = this.secteurDesSalles.findIndex(e => e.id === secteurDesSallest.id);
        if (index !== -1) {
          this.secteurDesSalles[index] = secteurDesSalles;
        }
        this.secteurDesSallesSelectionne = {} as SecteurDesSalles; 
      },
      (error) => {
        console.log('Erreur lors de la mise à jour de la secteurDesSalles : ', error);
      }
    );
  }

  deleteSecteurDesSalles(id: number): void {
    this.secteurDesSallesService.deleteSecteurDesSalles(id).subscribe(
      () => {
        this.secteurDesSalles = this.secteurDesSalles.filter(e => e.id !== id);
      },
      (error) => {
        console.log('Erreur lors de la suppression de la secteurDesSalles : ', error);
      }
    );
  }
}
