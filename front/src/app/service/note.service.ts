import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Note} from '../model/note';
import {catchError, tap} from 'rxjs/operators';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {Section} from "../model/section";
import {Subsection} from "../model/subsection";

@Injectable()
//todo - split it into note nad sections service
export class NoteService {
  private notesUri = '/api/notes';
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) {
  }

  getPublicNotes(username: string, quantity: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.notesUri}?user=${username}&type=PUBLIC&quantity=${quantity}`);
  }
  getFriendsNotes(username: string, quantity: number): Observable<Note[]>{
    return this.http.get<Note[]>(`${this.notesUri}?user=${username}&type=FRIENDS&quantity=${quantity}`);
  }
  getOwnerNotes(username: string, quantity: number): Observable<Note[]>{
    return this.http.get<Note[]>(`${this.notesUri}?user=${username}&type=OWNER&quantity=${quantity}`);
  }
  getFavoriteNotes(username: string, quantity: number): Observable<Note[]>{
    return this.http.get<Note[]>(`${this.notesUri}?user=${username}&type=FAVORITE&quantity=${quantity}`);
  }


  getNoteInfo(noteId: number): Observable<Note> {
    return this.http.get<Note>(this.notesUri + '/' + noteId);
  }

  // todo - add author !!!
  createNote(noteName: string, accessLevel: string, author: string): Observable<Number>{
    let body = JSON.stringify({
      noteName: noteName,
      accessLevel: accessLevel,
      author: author
    });
    return this.http.post<Number>(this.notesUri, body, {headers: this.headers});
  }

  updateSection(noteId: number, sectionSeqNum: number, content: string){
    console.log("sending put request");
    return this.http.put(this.notesUri + '/' + noteId + '/sections/' + sectionSeqNum, content, {headers: this.headers});
  }



  getSections(noteId: number): Observable<Section[]>{
    return this.http.get<Section[]>(
      this.notesUri + '/' + noteId + '/sections'
    ).pipe(
      tap(sections => console.log(sections)),
      catchError(this.handleError('getSections', []))
    );
  }

  updateSections(noteId: number, secs: Section[]) {
    let url = this.notesUri + '/' + noteId + '/sections';
    console.log("Update Sections ("+ url +"), new sections = " + JSON.stringify(secs));
    return this.http.put(url, secs, {headers: this.headers});
  }

  removeSection(noteId: number, sectionNumber: number) {
    let url = this.notesUri + '/' + noteId + '/sections/' + sectionNumber;
    console.log("Removing section : " + sectionNumber);
    console.log("Delete url : " + url);
    return this.http.delete(url, {headers: this.headers});
  }

  removeNote(noteId: number) {
    let url = this.notesUri + '/' + noteId;
    console.log("Removing note: "+ noteId);
    return this.http.delete(url,{headers: this.headers});
  }

  getToc(noteId: number, sectionId: number) {
    return this.http.get<Subsection[]>(
      this.notesUri + '/' + noteId + '/sections/' + sectionId + '/toc'
    ).pipe(
      tap(content => console.log(content))
    );
  }

  getContent(noteId: number, sectionId: number, metaVisible: boolean) {
    let params = new HttpParams().set("toEdit", String(metaVisible));
    return this.http.get(
      this.notesUri + '/' + noteId + '/sections/' + sectionId,
      {
        responseType: 'text',
        params: params
      }
    ).pipe(
      tap(content => console.log(content)),
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.log(error); // log to console instead

      console.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }
}
