import { Component, OnInit } from '@angular/core';
import { Luminosite } from './luminosite.model'; 
import { LuminositeService } from './luminosite.service';

@Component({
  selector: 'app-luminosite',
  templateUrl: './luminosite.component.html',
  styleUrls: ['./luminosite.component.css']
})
export class LuminositeComponent implements OnInit {

  luminosite: Luminosite[] = [];
  nouvelLuminosite: Luminosite = {} as Luminosite; 
  luminositeSelectionne: Luminosite = {} as Luminosite; 

  constructor(private luminositeService: LuminositeService) { }

  ngOnInit(): void {
    this.getLuminosite();
  }

  getLuminosite(): void {
    this.luminositeService.getLuminosite().subscribe(
      (data: Luminosite[]) => {
        this.luminosite = data;
      },
      (error) => {
        console.log('Erreur lors de la récupération des luminosite : ', error);
      }
    );
  }

  insertLuminosite(): void {
    this.luminositeService.insertLuminosite(this.nouvelLuminosite).subscribe(
      (luminosite: Luminosite) => {
        this.luminosite.push(luminosite);
        this.nouvelLuminosite = {} as Luminosite; 
      },
      (error) => {
        console.log('Erreur lors de linsertion de la luminosite : ', error);
      }
    );
  }

  updateLuminosite(): void {
    this.luminositeService.updateLuminosite(this.luminositeSelectionne).subscribe(
      (luminosite: Luminosite) => {
        const index = this.luminosite.findIndex(e => e.id === luminositet.id);
        if (index !== -1) {
          this.luminosite[index] = luminosite;
        }
        this.luminositeSelectionne = {} as Luminosite; 
      },
      (error) => {
        console.log('Erreur lors de la mise à jour de la luminosite : ', error);
      }
    );
  }

  deleteLuminosite(id: number): void {
    this.luminositeService.deleteLuminosite(id).subscribe(
      () => {
        this.luminosite = this.luminosite.filter(e => e.id !== id);
      },
      (error) => {
        console.log('Erreur lors de la suppression de la luminosite : ', error);
      }
    );
  }
}
