export class Subsection {
  public title: string;
  public seqNumber: number;
  public subLevel: number;


  constructor(title: string, seqNumber: number, subLevel: number) {
    this.title = title;
    this.seqNumber = seqNumber;
    this.subLevel = subLevel;
  }
}
