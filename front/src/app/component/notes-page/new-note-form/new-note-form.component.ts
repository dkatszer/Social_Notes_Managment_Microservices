import {Component, OnInit, ViewChild} from '@angular/core';
import {NoteService} from "../../../service/note.service";
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {LocalStorageKeys} from "../../../model/local-storage-keys.enum";
import {CurrUser} from "../../../model/curr-user";
import {LocalStorageService} from "../../../service/local-storage.service";

@Component({
  selector: 'app-new-note-form',
  templateUrl: './new-note-form.component.html',
  styleUrls: ['./new-note-form.component.scss']
})
export class NewNoteFormComponent implements OnInit {

  constructor(
    private notesService: NoteService,
    private router: Router,
    private localStorageService: LocalStorageService) { }

  name: string;
  access: string;

  // todo - add mini-img

  ngOnInit() {
  }

  onSubmit(){
    let username = this.localStorageService.getUsername();
    const noteIdObesrv = this.notesService.createNote(this.name, this.access, username);
    noteIdObesrv.subscribe(
      value => {
        console.log(value);
        this.router.navigate(['notes', value, 'sections', 1]);
      }
    );
  }
}
