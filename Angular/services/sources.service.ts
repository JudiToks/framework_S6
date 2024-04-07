import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sources } from './sources.model';

@Injectable({
  providedIn: 'root'
})
export class SourcesService {

  private apiUrl = 'http://localhost:4200/test/sources';

  constructor(private http: HttpClient) { }

  
  getSources(): Observable<Sources[]> {
    return this.http.get<Sources[]>(this.apiUrl);
  }

 
  insertSources(sources: Sources): Observable<void> {
    return this.http.post<Sources>(this.apiUrl, sources);
  }

 
  updateSources(sources: Sources): Observable<void> {
    const url = `${this.apiUrl}/${ sources.id}`;
    return this.http.put<Sources>(this.apiUrl,  sources);
  }

  
  deleteSources(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
