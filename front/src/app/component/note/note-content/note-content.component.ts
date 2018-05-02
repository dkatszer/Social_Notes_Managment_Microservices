import {Component, Input, OnInit} from '@angular/core';
import {NoteService} from "../../../service/note.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subsection} from "../../../model/subsection";

@Component({
  selector: 'app-note-content',
  templateUrl: './note-content.component.html',
  styleUrls: ['./note-content.component.scss']
})
export class NoteContentComponent implements OnInit {
  content: String;
  sectionId: number;
  noteId: number;

  subsecs: Subsection[] ;

  @Input()
  isCurrUserAnAuthor: boolean;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private noteService: NoteService) {
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.sectionId = params.sectionId;
      this.noteId = params.noteId;

      this.initContent();
      this.initToc();
    });
  }

  editSection() {
    this.router.navigate(['/notes', this.noteId, 'sections', this.sectionId], {queryParams: {isEditable: true}});
  }

  private initToc(){
    this.noteService.getToc(this.noteId, this.sectionId).subscribe(subsecs => this.subsecs = subsecs);
  }

  private initContent() {
    this.activatedRoute.params.subscribe(params => {
      this.sectionId = params.sectionId;
      this.noteId = params.noteId;

      this.noteService.getContent(this.noteId, this.sectionId, false).subscribe(content => {
          this.content = content;
      });
    });
  }
}
