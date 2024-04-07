import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Salles } from './salles.model';

@Injectable({
  providedIn: 'root'
})
export class SallesService {

  private apiUrl = 'http://localhost:4200/test/salles';

  constructor(private http: HttpClient) { }

  
  getSalles(): Observable<Salles[]> {
    return this.http.get<Salles[]>(this.apiUrl);
  }

 
  insertSalles(salles: Salles): Observable<void> {
    return this.http.post<Salles>(this.apiUrl, salles);
  }

 
  updateSalles(salles: Salles): Observable<void> {
    const url = `${this.apiUrl}/${ salles.id}`;
    return this.http.put<Salles>(this.apiUrl,  salles);
  }

  
  deleteSalles(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
