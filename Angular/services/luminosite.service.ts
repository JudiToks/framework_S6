import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Luminosite } from './luminosite.model';

@Injectable({
  providedIn: 'root'
})
export class LuminositeService {

  private apiUrl = 'http://localhost:4200/test/luminosite';

  constructor(private http: HttpClient) { }

  
  getLuminosite(): Observable<Luminosite[]> {
    return this.http.get<Luminosite[]>(this.apiUrl);
  }

 
  insertLuminosite(luminosite: Luminosite): Observable<void> {
    return this.http.post<Luminosite>(this.apiUrl, luminosite);
  }

 
  updateLuminosite(luminosite: Luminosite): Observable<void> {
    const url = `${this.apiUrl}/${ luminosite.id}`;
    return this.http.put<Luminosite>(this.apiUrl,  luminosite);
  }

  
  deleteLuminosite(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
