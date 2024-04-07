import { Component, OnInit } from '@angular/core';
import { PresenceParClasses } from './presenceParClasses.model'; 
import { PresenceParClassesService } from './presenceParClasses.service';

@Component({
  selector: 'app-presenceParClasses',
  templateUrl: './presenceParClasses.component.html',
  styleUrls: ['./presenceParClasses.component.css']
})
export class PresenceParClassesComponent implements OnInit {

  presenceParClasses: PresenceParClasses[] = [];
  nouvelPresenceParClasses: PresenceParClasses = {} as PresenceParClasses; 
  presenceParClassesSelectionne: PresenceParClasses = {} as PresenceParClasses; 

  constructor(private presenceParClassesService: PresenceParClassesService) { }

  ngOnInit(): void {
    this.getPresenceParClasses();
  }

  getPresenceParClasses(): void {
    this.presenceParClassesService.getPresenceParClasses().subscribe(
      (data: PresenceParClasses[]) => {
        this.presenceParClasses = data;
      },
      (error) => {
        console.log('Erreur lors de la récupération des presenceParClasses : ', error);
      }
    );
  }

  insertPresenceParClasses(): void {
    this.presenceParClassesService.insertPresenceParClasses(this.nouvelPresenceParClasses).subscribe(
      (presenceParClasses: PresenceParClasses) => {
        this.presenceParClasses.push(presenceParClasses);
        this.nouvelPresenceParClasses = {} as PresenceParClasses; 
      },
      (error) => {
        console.log('Erreur lors de linsertion de la presenceParClasses : ', error);
      }
    );
  }

  updatePresenceParClasses(): void {
    this.presenceParClassesService.updatePresenceParClasses(this.presenceParClassesSelectionne).subscribe(
      (presenceParClasses: PresenceParClasses) => {
        const index = this.presenceParClasses.findIndex(e => e.id === presenceParClassest.id);
        if (index !== -1) {
          this.presenceParClasses[index] = presenceParClasses;
        }
        this.presenceParClassesSelectionne = {} as PresenceParClasses; 
      },
      (error) => {
        console.log('Erreur lors de la mise à jour de la presenceParClasses : ', error);
      }
    );
  }

  deletePresenceParClasses(id: number): void {
    this.presenceParClassesService.deletePresenceParClasses(id).subscribe(
      () => {
        this.presenceParClasses = this.presenceParClasses.filter(e => e.id !== id);
      },
      (error) => {
        console.log('Erreur lors de la suppression de la presenceParClasses : ', error);
      }
    );
  }
}
