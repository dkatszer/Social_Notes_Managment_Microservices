import { Injectable } from '@angular/core';
import {LocalStorageKeys} from "../model/local-storage-keys.enum";
import {CurrUser} from "../model/curr-user";

@Injectable()
export class LocalStorageService {

  constructor() { }

  getUsername(){
    let currUserJson = localStorage.getItem(LocalStorageKeys.CURRENT_USER);
    let currUser: CurrUser = JSON.parse(currUserJson);
    return currUser.username;
  }
}
