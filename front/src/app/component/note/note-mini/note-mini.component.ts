import {Component, Input, OnInit} from '@angular/core';
import {Note} from '../../../model/note';

@Component({
  selector: 'app-note-mini',
  templateUrl: './note-mini.component.html',
  styleUrls: ['./note-mini.component.scss']
})
export class NoteMiniComponent implements OnInit {
  @Input() note: Note;

  constructor() { }

  ngOnInit() {
  }

}
