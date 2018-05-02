import { Injectable } from '@angular/core';

@Injectable()
export class CodeHighlightService {

  constructor() { }
  // todo - use dom parser instead of regex
  public insertHighlightWrapperToPre(content) {
    const langRegex = /::\w+::/g;
    const preRegex = /<pre>(.|\s)+?<\/pre>/gm;
    const parsedLangsArray = this.parseData(content, langRegex, this.langInfoConverter);
    const parsedPreArray = this.parseData(content, preRegex, this.preInfoConverter);

    const slices = [];
    if (parsedPreArray.length) {
      slices.push(this.getSliceToFirstPre(content, parsedPreArray[0]));
      slices.push(this.getPreBlocksWithFollowingSlicesInOrder(parsedPreArray, parsedLangsArray, content).join(''));
      slices.push(this.getSliceFromLastPreToEnd(content, parsedPreArray[parsedPreArray.length - 1]));

      return slices.join('');
    }else {
      return content;
    }
  }

  private getPreBlocksWithFollowingSlicesInOrder(parsedPreArray: any[], parsedLangsArray: any[], content) {
    const slices = [];
    for (let i = 0; i < parsedPreArray.length; i++) {
      const preBlock = parsedPreArray[i];
      slices.push(this.enchancePreBlock(parsedLangsArray, preBlock));
      if (!this.isLastPre(parsedPreArray, i)) {
        const nextPreBlock = parsedPreArray[i + 1];
        slices.push(content.slice(preBlock.endIdx, nextPreBlock.startIdx));
      }
    }
    return slices;
  }

  private enchancePreBlock(parsedLangsArray, preBlock: any) {
    const lang = this.getLangForPre(parsedLangsArray, preBlock);
    return this.insertHighlight(preBlock, lang);
  }
  private isLastPre(parsedPreArray, i: number) {
    return i + 1 === parsedPreArray.length;
  }
  private getSliceToFirstPre(content, firstPre) {
    return content.slice(0, firstPre.startIdx);
  }
  private getSliceFromLastPreToEnd(content, lastPre) {
    return content.slice(lastPre.endIdx, content.length);
  }

  private parseData(content, regex, matchConverter) {
    const parsedLangs = [];
    let match;
    while ((match = regex.exec(content)) != null) {
      parsedLangs.push(matchConverter(match));
    }
    return parsedLangs;
  }

  private langInfoConverter(match) {
    const lang = String(match);
    return {
      lang: lang.substring(2, lang.length - 2),
      idx: match.index
    };
  }

  private preInfoConverter(match) {
    let str = String(match);
    str = str.substring(0, str.length - 2); // todo - i dont know why it always add ,} to the end
    return {
      startIdx: match.index,
      endIdx: match.index + str.length,
      content: str.substring('<pre>'.length, str.length - '</pre>'.length)
    };
  }

  private getLangForPre(parsedLangsArray, preBlock) {
    const startCodeIdx = preBlock.startIdx + '<pre>'.length;
    for (let i = 0; i < parsedLangsArray.length - 1; i++) {
      if (startCodeIdx >= parsedLangsArray[i].idx && startCodeIdx < parsedLangsArray[i + 1].idx) {
        return parsedLangsArray[i].lang;
      }
    }
    if (startCodeIdx >= parsedLangsArray.slice(-1)[0] .idx) {
      return parsedLangsArray.slice(-1)[0].lang;
    }
    return '';
  }

  private insertHighlight(preBlock, lang) {
    const codeTemplate = '<pre><div class="highlight"><code class="%LANG_NAME%">%CONTENT%</code></div></pre>';
    return codeTemplate.replace('%LANG_NAME%', lang).replace('%CONTENT%', preBlock.content);
  }
}
