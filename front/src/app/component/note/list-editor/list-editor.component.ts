import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {NoteService} from "../../../service/note.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Section} from "../../../model/section";
import {Location} from "@angular/common";

@Component({
  selector: 'app-list-editor',
  templateUrl: './list-editor.component.html',
  styleUrls: ['./list-editor.component.scss']
})
export class ListEditorComponent implements OnInit {

  sectionsForm: FormGroup;
  noteId: number;
  private activatedSecNum: number;
  private removedActivedSec = false;

  constructor(private activatedRoute: ActivatedRoute,
              private formBuilder: FormBuilder,
              private noteService: NoteService,
              private location: Location,
              private router: Router) {
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.noteId = params.noteId;

      this.initFormGroup();
      this.initSections();
      this.activatedRoute.queryParams.subscribe(queryParams => {
        this.activatedSecNum = +queryParams['secNum'];
      })
    });
  }


  private initSections() {
    this.noteService.getSections(this.noteId).subscribe(
      sections => {
        console.log("Received sections")
        console.log(JSON.stringify(sections));
        const soretedSections = sections
          .sort(Section.compare);

        console.log("After sort");
        console.log(soretedSections);

        const sectionFGs = soretedSections
          .map(section => this.formBuilder.group(section));

        const sectionFormArray = this.formBuilder.array(sectionFGs);
        this.sectionsForm.setControl('items', sectionFormArray);
      }
    );

  }

  private initFormGroup() {
    this.sectionsForm = this.formBuilder.group({
      items: this.formBuilder.array([])
    });
  }

  public addNewSection() {
    let lastSection = this.getSectionAt(this.items.length - 1);
    this.items.push(this.formBuilder.group(new Section(lastSection.seqNumber + 1, "")));
  }

  private getSectionAt(idx: number) {
    let lastSection = this.items.at(idx).value as Section;
    return lastSection;
  }

  removeLastSection(){
    let section = this.items.controls[this.items.length-1].value as Section;
    this.noteService.removeSection(this.noteId, section.seqNumber).subscribe(() => {
        this.items.removeAt(this.items.length-1);
        if (this.items.length === 0) {
          //remove note all
          this.noteService.removeNote(this.noteId).subscribe(
            () => this.router.navigate(['/notes'])
          );
        }
      }
    );
  }

  removeSection(formGroupIdx: number) {
    console.log("clicked removeSection at idx: " + formGroupIdx);
    let section = this.items.controls[formGroupIdx].value as Section;

    this.removedActivedSec = this.activatedSecNum === section.seqNumber;

    this.noteService.removeSection(this.noteId, section.seqNumber).subscribe(() => {
        console.log("Removing input at idx : " + formGroupIdx);
        this.items.removeAt(formGroupIdx);
        if (this.items.length === 0) {
          //remove note all
          this.noteService.removeNote(this.noteId).subscribe(
            () => this.router.navigate(['/notes'])
          );
        }
      }
    );
  }

  public onSubmit() {
    const formModel = this.sectionsForm.value;

    this.noteService.updateSections(this.noteId, formModel.items).subscribe(
      () => {
        if (this.removedActivedSec) {
          this.router.navigate(['notes', this.noteId, 'sections', formModel.items[0].seqNumber])
        }
        this.goBack();
      }
    );
  }

  goBack(): void {
    this.location.back();
  }

  get items(): FormArray {
    return this.sectionsForm.get('items') as FormArray;
  };


}
