import { Injectable } from '@angular/core';
// import {TocElement} from "../model/toc-element";

@Injectable()
export class TocService {
/*
  private regex = /<(h2|h3)>(.|\s)+?<\/(h2|h3)>/gm;

  constructor() { }

  public generateToc(content) {
    let match;
    const toc = []
    while ((match = this.regex.exec(content)) != null){
      const header = String(match);
      const headerLevel = this.getHeaderLevel(header);
      toc.push(new TocElement(this.getTagContent(header), headerLevel));
    }
    return toc;
  }

  private getHeaderLevel(header){
    if( header.search('<h2>') != -1) {
      return 2;
    } else if(header.search('<h3>') != -1){
      return 3;
    }
  }
  private getTagContent(tag) {
    return tag.substring('<h2>'.length, tag.length - '</h2>'.length);
  }
*/

}
