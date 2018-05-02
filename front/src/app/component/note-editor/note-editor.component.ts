import {Component, Input, OnInit} from '@angular/core';
import {CodeHighlightService} from '../../service/code-highlight.service';
import {NoteService} from "../../service/note.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-note-editor',
  templateUrl: './note-editor.component.html',
  styleUrls: ['./note-editor.component.scss']
})
export class NoteEditorComponent implements OnInit {
  private basicToolbar = [
    'bold', 'italic', 'underline', 'strikeThrough', '|',
    'paragraphFormat', 'formatOL', 'formatUL', 'outdent', 'indent', 'quote',
    '-',
    'insertLink', 'insertImage', 'insertVideo', 'insertTable', '|',
    'subscript', 'superscript', 'specialCharacters', '|',
    'insertHR', 'print', '|', 'undo', 'redo'];


  public options: Object = {
    placeholderText: 'Edit Your Content Here!',
    charCounterCount: false,
    toolbarButtons: this.basicToolbar,
    toolbarButtonsMD: this.basicToolbar,
    toolbarButtonsSM: this.basicToolbar,
    toolbarButtonsXS: ['bold', 'italic', 'underline', 'paragraphFormat', 'insertLink', 'insertImage', 'insertTable', 'undo', 'redo'],
    paragraphFormat: {
      N: 'Normal',
      H2: 'Heading 1',
      H3: 'Heading 2',
      PRE: 'Code'
    },
    heightMin: 150,
    heightMax: 400,
  };

  content: string;

  sectionId: number;
  sectionName: string;
  noteId: number;

  constructor(private highlighter: CodeHighlightService,
              private notesService: NoteService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.sectionId = params.sectionId;
      this.noteId = params.noteId;

      this.initContent();
    });
  }

  onSubmit() {
    console.log("Submit note editor");
    // this.content = this.highlighter.insertHighlightWrapperToPre(this.content);
    this.notesService.updateSection(this.noteId, this.sectionId, this.content).subscribe( any =>
      this.router.navigate(['/notes', this.noteId, 'sections', this.sectionId], {queryParams: {isEditable: false}})
    );
  }

  private initContent() {
    this.activatedRoute.params.subscribe(params => {
      this.sectionId = params.sectionId;
      this.noteId = params.noteId;

      this.notesService.getContent(this.noteId, this.sectionId, true).subscribe(content => {
        this.content = content.toString();
      });
    });
  }
}
