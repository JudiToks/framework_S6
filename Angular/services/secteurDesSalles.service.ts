import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SecteurDesSalles } from './secteurDesSalles.model';

@Injectable({
  providedIn: 'root'
})
export class SecteurDesSallesService {

  private apiUrl = 'http://localhost:4200/test/secteurDesSalles';

  constructor(private http: HttpClient) { }

  
  getSecteurDesSalles(): Observable<SecteurDesSalles[]> {
    return this.http.get<SecteurDesSalles[]>(this.apiUrl);
  }

 
  insertSecteurDesSalles(secteurDesSalles: SecteurDesSalles): Observable<void> {
    return this.http.post<SecteurDesSalles>(this.apiUrl, secteurDesSalles);
  }

 
  updateSecteurDesSalles(secteurDesSalles: SecteurDesSalles): Observable<void> {
    const url = `${this.apiUrl}/${ secteurDesSalles.id}`;
    return this.http.put<SecteurDesSalles>(this.apiUrl,  secteurDesSalles);
  }

  
  deleteSecteurDesSalles(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
