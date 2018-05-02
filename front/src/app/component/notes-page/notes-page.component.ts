import {Component, OnInit} from '@angular/core';
import {NoteService} from "../../service/note.service";
import {Note} from "../../model/note";
import {LocalStorageService} from "../../service/local-storage.service";

@Component({
  selector: 'app-notes-page',
  templateUrl: './notes-page.component.html',
  styleUrls: ['./notes-page.component.scss']
})
export class NotesPageComponent implements OnInit {
  private numberOfPreviewedItems = 4;

  favoriteNotes: Note[];
  ownerNotes: Note[];
  friendsNotes: Note[];
  publicNotes: Note[];

  username: string;

  constructor(
    private noteService: NoteService,
    private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.username = this.localStorageService.getUsername();
    this.initFavoriteNotes();
    this.initOwnerNotes();
    this.initFriendsNotes();
    this.initPublicNotes();
  }

  initPublicNotes(): void {
    this.noteService.getPublicNotes(this.username, this.numberOfPreviewedItems)
      .subscribe(notes => this.publicNotes = notes);
  }
  initOwnerNotes(): void {
    this.noteService.getOwnerNotes(this.username, this.numberOfPreviewedItems)
      .subscribe(notes => this.ownerNotes = notes);
  }
  initFriendsNotes(): void {
    this.noteService.getFriendsNotes(this.username, this.numberOfPreviewedItems)
      .subscribe(notes => this.friendsNotes = notes);
  }

  initFavoriteNotes(): void {
    this.noteService.getFavoriteNotes(this.username, this.numberOfPreviewedItems)
      .subscribe(notes => this.favoriteNotes = notes);
  }

}
