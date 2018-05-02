import {Component, OnInit} from '@angular/core';
import {NoteService} from '../../service/note.service';
import {Note} from '../../model/note';
import {AuthenticationService} from '../../service/authentication.service';
import {LocalStorageService} from "../../service/local-storage.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  private numberOfNewestNotes = 4;

  newestNotes: Note[] = [];

  constructor(private noteService: NoteService,
              private localStorageService: LocalStorageService) {
  }

  ngOnInit() {
    this.getNewestNotes();
  }

  getNewestNotes(): void {
    this.noteService.getPublicNotes(this.localStorageService.getUsername(), this.numberOfNewestNotes)
      .subscribe(notes => this.newestNotes = notes);
  }
}
