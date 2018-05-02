import {Section} from './section';

export class Note {
  noteId: number;
  title: string;
  sections: [Section];
  lastModification: Date;
  author: string
}
