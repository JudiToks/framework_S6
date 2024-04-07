import { Component, OnInit } from '@angular/core';
import { Classes } from './classes.model'; 
import { ClassesService } from './classes.service';

@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.css']
})
export class ClassesComponent implements OnInit {

  classes: Classes[] = [];
  nouvelClasses: Classes = {} as Classes; 
  classesSelectionne: Classes = {} as Classes; 

  constructor(private classesService: ClassesService) { }

  ngOnInit(): void {
    this.getClasses();
  }

  getClasses(): void {
    this.classesService.getClasses().subscribe(
      (data: Classes[]) => {
        this.classes = data;
      },
      (error) => {
        console.log('Erreur lors de la récupération des classes : ', error);
      }
    );
  }

  insertClasses(): void {
    this.classesService.insertClasses(this.nouvelClasses).subscribe(
      (classes: Classes) => {
        this.classes.push(classes);
        this.nouvelClasses = {} as Classes; 
      },
      (error) => {
        console.log('Erreur lors de linsertion de la classes : ', error);
      }
    );
  }

  updateClasses(): void {
    this.classesService.updateClasses(this.classesSelectionne).subscribe(
      (classes: Classes) => {
        const index = this.classes.findIndex(e => e.id === classest.id);
        if (index !== -1) {
          this.classes[index] = classes;
        }
        this.classesSelectionne = {} as Classes; 
      },
      (error) => {
        console.log('Erreur lors de la mise à jour de la classes : ', error);
      }
    );
  }

  deleteClasses(id: number): void {
    this.classesService.deleteClasses(id).subscribe(
      () => {
        this.classes = this.classes.filter(e => e.id !== id);
      },
      (error) => {
        console.log('Erreur lors de la suppression de la classes : ', error);
      }
    );
  }
}
