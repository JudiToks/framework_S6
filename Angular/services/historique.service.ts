import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Historique } from './historique.model';

@Injectable({
  providedIn: 'root'
})
export class HistoriqueService {

  private apiUrl = 'http://localhost:4200/test/historique';

  constructor(private http: HttpClient) { }

  
  getHistorique(): Observable<Historique[]> {
    return this.http.get<Historique[]>(this.apiUrl);
  }

 
  insertHistorique(historique: Historique): Observable<void> {
    return this.http.post<Historique>(this.apiUrl, historique);
  }

 
  updateHistorique(historique: Historique): Observable<void> {
    const url = `${this.apiUrl}/${ historique.id}`;
    return this.http.put<Historique>(this.apiUrl,  historique);
  }

  
  deleteHistorique(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
