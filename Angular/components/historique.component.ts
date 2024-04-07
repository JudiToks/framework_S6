import { Component, OnInit } from '@angular/core';
import { Historique } from './historique.model'; 
import { HistoriqueService } from './historique.service';

@Component({
  selector: 'app-historique',
  templateUrl: './historique.component.html',
  styleUrls: ['./historique.component.css']
})
export class HistoriqueComponent implements OnInit {

  historique: Historique[] = [];
  nouvelHistorique: Historique = {} as Historique; 
  historiqueSelectionne: Historique = {} as Historique; 

  constructor(private historiqueService: HistoriqueService) { }

  ngOnInit(): void {
    this.getHistorique();
  }

  getHistorique(): void {
    this.historiqueService.getHistorique().subscribe(
      (data: Historique[]) => {
        this.historique = data;
      },
      (error) => {
        console.log('Erreur lors de la récupération des historique : ', error);
      }
    );
  }

  insertHistorique(): void {
    this.historiqueService.insertHistorique(this.nouvelHistorique).subscribe(
      (historique: Historique) => {
        this.historique.push(historique);
        this.nouvelHistorique = {} as Historique; 
      },
      (error) => {
        console.log('Erreur lors de linsertion de la historique : ', error);
      }
    );
  }

  updateHistorique(): void {
    this.historiqueService.updateHistorique(this.historiqueSelectionne).subscribe(
      (historique: Historique) => {
        const index = this.historique.findIndex(e => e.id === historiquet.id);
        if (index !== -1) {
          this.historique[index] = historique;
        }
        this.historiqueSelectionne = {} as Historique; 
      },
      (error) => {
        console.log('Erreur lors de la mise à jour de la historique : ', error);
      }
    );
  }

  deleteHistorique(id: number): void {
    this.historiqueService.deleteHistorique(id).subscribe(
      () => {
        this.historique = this.historique.filter(e => e.id !== id);
      },
      (error) => {
        console.log('Erreur lors de la suppression de la historique : ', error);
      }
    );
  }
}
