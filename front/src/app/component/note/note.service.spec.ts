import { TestBed, inject } from '@angular/core/testing';

import { NoteService } from '../../service/note.service';

describe('NoteService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NoteService]
    });
  });

  it('should be created', inject([NoteService], (service: NoteService) => {
    expect(service).toBeTruthy();
  }));
});
