import { Component, OnInit } from '@angular/core';
import { Sources } from './sources.model'; 
import { SourcesService } from './sources.service';

@Component({
  selector: 'app-sources',
  templateUrl: './sources.component.html',
  styleUrls: ['./sources.component.css']
})
export class SourcesComponent implements OnInit {

  sources: Sources[] = [];
  nouvelSources: Sources = {} as Sources; 
  sourcesSelectionne: Sources = {} as Sources; 

  constructor(private sourcesService: SourcesService) { }

  ngOnInit(): void {
    this.getSources();
  }

  getSources(): void {
    this.sourcesService.getSources().subscribe(
      (data: Sources[]) => {
        this.sources = data;
      },
      (error) => {
        console.log('Erreur lors de la récupération des sources : ', error);
      }
    );
  }

  insertSources(): void {
    this.sourcesService.insertSources(this.nouvelSources).subscribe(
      (sources: Sources) => {
        this.sources.push(sources);
        this.nouvelSources = {} as Sources; 
      },
      (error) => {
        console.log('Erreur lors de linsertion de la sources : ', error);
      }
    );
  }

  updateSources(): void {
    this.sourcesService.updateSources(this.sourcesSelectionne).subscribe(
      (sources: Sources) => {
        const index = this.sources.findIndex(e => e.id === sourcest.id);
        if (index !== -1) {
          this.sources[index] = sources;
        }
        this.sourcesSelectionne = {} as Sources; 
      },
      (error) => {
        console.log('Erreur lors de la mise à jour de la sources : ', error);
      }
    );
  }

  deleteSources(id: number): void {
    this.sourcesService.deleteSources(id).subscribe(
      () => {
        this.sources = this.sources.filter(e => e.id !== id);
      },
      (error) => {
        console.log('Erreur lors de la suppression de la sources : ', error);
      }
    );
  }
}
