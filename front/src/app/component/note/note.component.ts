import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Route, Router} from '@angular/router';
import {Location} from '@angular/common';
import {NoteService} from "../../service/note.service";
import {Section} from "../../model/section";
import {LocalStorageService} from "../../service/local-storage.service";


@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.scss']
})
export class NoteComponent implements OnInit {
  sections: Section[];
  selectedSection: Section;
  noteId: number;
  sectionId: number;
  isEditable = false;
  isCurrUserAnAuthor = false;

  constructor(private activatedRoute: ActivatedRoute,
              private noteService: NoteService,
              private location: Location,
              private router: Router,
              private localStorageService: LocalStorageService) {
  }


  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
        this.sectionId = params.sectionId;
        this.noteId = params.noteId;
        this.initSections();
        this.noteService.getNoteInfo(this.noteId).subscribe(note =>{
          this.isCurrUserAnAuthor = this.localStorageService.getUsername() === note.author;
        })
      }
    );

    this.activatedRoute.queryParams.subscribe( queryParams => {
      this.isEditable = queryParams['isEditable'] === 'true' || false;
    })
  }

  private initSections() {
    this.noteService.getSections(this.noteId).subscribe(
      sections => {
        this.sections = sections.sort(Section.compare);
        this.selectedSection = this.sections[0];
      }
    );
  }



  selectSection(section: Section) {
    this.selectedSection = section;
  }

  isActive(section: Section) {
    return this.selectedSection.seqNumber === section.seqNumber;
  }

  routeToSecListEdit(){
    console.log("route to edit list of section with queryParam: " + this.sectionId);
    this.router.navigate(['/notes',this.noteId,'sections'], {queryParams: {secNum: this.sectionId}});
  }

  goBack(): void {
    this.location.back();
  }
}
