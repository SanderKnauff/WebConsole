import {Injectable} from "@angular/core";

@Injectable()
export class HtmlSanitizer {

  private toReplace: Map<RegExp, string> = new Map([
    [/&/g, "&amp;"],
    [/</g, "&lt;"],
    [/>/g, "&gt;"]
  ]);

  constructor() {
  }

  public sanitize(toSanitize: string): string {
    this.toReplace.forEach((value, key) => {
      toSanitize = toSanitize.replace(key, value);
    });
    return toSanitize;
  }
}
