import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Classes } from './classes.model';

@Injectable({
  providedIn: 'root'
})
export class ClassesService {

  private apiUrl = 'http://localhost:4200/test/classes';

  constructor(private http: HttpClient) { }

  
  getClasses(): Observable<Classes[]> {
    return this.http.get<Classes[]>(this.apiUrl);
  }

 
  insertClasses(classes: Classes): Observable<void> {
    return this.http.post<Classes>(this.apiUrl, classes);
  }

 
  updateClasses(classes: Classes): Observable<void> {
    const url = `${this.apiUrl}/${ classes.id}`;
    return this.http.put<Classes>(this.apiUrl,  classes);
  }

  
  deleteClasses(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
