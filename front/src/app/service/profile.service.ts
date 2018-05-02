import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LocalStorageService} from "./local-storage.service";
import {Profile} from "../model/profile";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ProfileService {
  private baseUri = '/api/profiles';
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private http: HttpClient,
    private localStorageService: LocalStorageService) { }

  getProfileForCurrUser(): Observable<Profile>{
    let username = this.localStorageService.getUsername();
    let url = this.baseUri+ '/' + username;
    return this.http.get<Profile>(url, {headers: this.headers});
  }
  getFriendsForCurrUser(): Observable<Profile[]>{
    let username = this.localStorageService.getUsername();
    let url = this.baseUri+ '/' + username + '/friends';
    return this.http.get<Profile[]>(url, {headers: this.headers});
  }
}
