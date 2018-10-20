import {LogType} from "./log-type";

export class LogLine {

  constructor(
    public line: string,
    public logType: LogType
  ) {}
}
