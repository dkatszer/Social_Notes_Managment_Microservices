export class Section {
  seqNumber: number;
  title: string;


  constructor(seqNumber: number, title: string) {
    this.seqNumber = seqNumber;
    this.title = title;
  }

  static compare(a: Section, b: Section) {
    if (a.seqNumber < b.seqNumber) {
      return -1;
    } else if (a.seqNumber > b.seqNumber) {
      return 1;
    } else {
      return 0;
    }
  }

}
