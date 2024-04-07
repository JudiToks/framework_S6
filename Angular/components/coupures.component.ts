import { Component, OnInit } from '@angular/core';
import { Coupures } from './coupures.model'; 
import { CoupuresService } from './coupures.service';

@Component({
  selector: 'app-coupures',
  templateUrl: './coupures.component.html',
  styleUrls: ['./coupures.component.css']
})
export class CoupuresComponent implements OnInit {

  coupures: Coupures[] = [];
  nouvelCoupures: Coupures = {} as Coupures; 
  coupuresSelectionne: Coupures = {} as Coupures; 

  constructor(private coupuresService: CoupuresService) { }

  ngOnInit(): void {
    this.getCoupures();
  }

  getCoupures(): void {
    this.coupuresService.getCoupures().subscribe(
      (data: Coupures[]) => {
        this.coupures = data;
      },
      (error) => {
        console.log('Erreur lors de la récupération des coupures : ', error);
      }
    );
  }

  insertCoupures(): void {
    this.coupuresService.insertCoupures(this.nouvelCoupures).subscribe(
      (coupures: Coupures) => {
        this.coupures.push(coupures);
        this.nouvelCoupures = {} as Coupures; 
      },
      (error) => {
        console.log('Erreur lors de linsertion de la coupures : ', error);
      }
    );
  }

  updateCoupures(): void {
    this.coupuresService.updateCoupures(this.coupuresSelectionne).subscribe(
      (coupures: Coupures) => {
        const index = this.coupures.findIndex(e => e.id === coupurest.id);
        if (index !== -1) {
          this.coupures[index] = coupures;
        }
        this.coupuresSelectionne = {} as Coupures; 
      },
      (error) => {
        console.log('Erreur lors de la mise à jour de la coupures : ', error);
      }
    );
  }

  deleteCoupures(id: number): void {
    this.coupuresService.deleteCoupures(id).subscribe(
      () => {
        this.coupures = this.coupures.filter(e => e.id !== id);
      },
      (error) => {
        console.log('Erreur lors de la suppression de la coupures : ', error);
      }
    );
  }
}
