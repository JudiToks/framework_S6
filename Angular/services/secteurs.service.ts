import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Secteurs } from './secteurs.model';

@Injectable({
  providedIn: 'root'
})
export class SecteursService {

  private apiUrl = 'http://localhost:4200/test/secteurs';

  constructor(private http: HttpClient) { }

  
  getSecteurs(): Observable<Secteurs[]> {
    return this.http.get<Secteurs[]>(this.apiUrl);
  }

 
  insertSecteurs(secteurs: Secteurs): Observable<void> {
    return this.http.post<Secteurs>(this.apiUrl, secteurs);
  }

 
  updateSecteurs(secteurs: Secteurs): Observable<void> {
    const url = `${this.apiUrl}/${ secteurs.id}`;
    return this.http.put<Secteurs>(this.apiUrl,  secteurs);
  }

  
  deleteSecteurs(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
