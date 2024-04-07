import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PresenceParClasses } from './presenceParClasses.model';

@Injectable({
  providedIn: 'root'
})
export class PresenceParClassesService {

  private apiUrl = 'http://localhost:4200/test/presenceParClasses';

  constructor(private http: HttpClient) { }

  
  getPresenceParClasses(): Observable<PresenceParClasses[]> {
    return this.http.get<PresenceParClasses[]>(this.apiUrl);
  }

 
  insertPresenceParClasses(presenceParClasses: PresenceParClasses): Observable<void> {
    return this.http.post<PresenceParClasses>(this.apiUrl, presenceParClasses);
  }

 
  updatePresenceParClasses(presenceParClasses: PresenceParClasses): Observable<void> {
    const url = `${this.apiUrl}/${ presenceParClasses.id}`;
    return this.http.put<PresenceParClasses>(this.apiUrl,  presenceParClasses);
  }

  
  deletePresenceParClasses(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
