import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {LocalStorageService} from "../../service/local-storage.service";
import {NoteService} from "../../service/note.service";
import {Note} from "../../model/note";

@Component({
  selector: 'app-all-items',
  templateUrl: './all-items.component.html',
  styleUrls: ['./all-items.component.scss']
})
export class AllItemsComponent implements OnInit {

  type: string;
  username: string;
  all: number = 0;
  notes: Note[]

  constructor(private activatedRoute: ActivatedRoute,
              private localStorageService: LocalStorageService,
              private noteService: NoteService) {
  }

  ngOnInit() {
    this.username = this.localStorageService.getUsername();
    this.activatedRoute.params.subscribe(params => {
      this.type = params.type;
    });
    switch (this.type) {
      case 'friends':
        this.initFriendsNotes();
        break;
      case 'owner':
        this.initOwnerNotes();
        break;
      case 'favorite':
        this.initFavoriteNotes();
        break;
      default:
        this.initPublicNotes();
    }
  }

  initPublicNotes(): void {
    this.noteService.getPublicNotes(this.username, this.all)
      .subscribe(notes => this.notes = notes);
  }

  initOwnerNotes(): void {
    this.noteService.getOwnerNotes(this.username, this.all)
      .subscribe(notes => this.notes = notes);
  }

  initFriendsNotes(): void {
    this.noteService.getFriendsNotes(this.username, this.all)
      .subscribe(notes => this.notes = notes);
  }

  initFavoriteNotes(): void {
    this.noteService.getFavoriteNotes(this.username, this.all)
      .subscribe(notes => this.notes = notes);
  }

}
