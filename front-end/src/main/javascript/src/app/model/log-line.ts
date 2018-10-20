import {LogType} from "./log-type";
import Filter from "ansi-to-html";

export class LogLine {

  constructor(public line: string,
              public logType: LogType) {
  }

  public getFormattedLine(): string {
    let converter = new Filter();
    return converter.toHtml(this.line);
  }
}
