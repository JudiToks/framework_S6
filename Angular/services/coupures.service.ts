import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Coupures } from './coupures.model';

@Injectable({
  providedIn: 'root'
})
export class CoupuresService {

  private apiUrl = 'http://localhost:4200/test/coupures';

  constructor(private http: HttpClient) { }

  
  getCoupures(): Observable<Coupures[]> {
    return this.http.get<Coupures[]>(this.apiUrl);
  }

 
  insertCoupures(coupures: Coupures): Observable<void> {
    return this.http.post<Coupures>(this.apiUrl, coupures);
  }

 
  updateCoupures(coupures: Coupures): Observable<void> {
    const url = `${this.apiUrl}/${ coupures.id}`;
    return this.http.put<Coupures>(this.apiUrl,  coupures);
  }

  
  deleteCoupures(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
