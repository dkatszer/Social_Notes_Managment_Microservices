import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Note} from "../../../model/note";

@Component({
  selector: 'app-notes-section',
  templateUrl: './notes-section.component.html',
  styleUrls: ['./notes-section.component.scss']
})
export class NotesSectionComponent implements OnInit, OnChanges {
  @Input()
  notes: Note[];

  @Input()
  iconName: string;
  @Input()
  title: string;
  @Input()
  route: string;
  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
  }
}
