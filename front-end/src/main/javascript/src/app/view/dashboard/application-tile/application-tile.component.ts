import {Component, ElementRef, Input, OnInit, Renderer2} from '@angular/core';
import {Application} from "../../../model/application";
import {Router} from "@angular/router";
import {Tile} from "./tile";

@Component({
  selector: 'app-application-tile',
  templateUrl: './application-tile.component.html',
  styleUrls: ['./application-tile.component.scss'],
  host: {
    "(click)": "openApplication()"
  }
})
export class ApplicationTileComponent implements OnInit, Tile {

  @Input() private application: Application;

  constructor(private router: Router, private elRef: ElementRef, private renderer: Renderer2) {
  }

  ngOnInit() {
    this.setTileColor();
  }

  public openApplication(): void {
    this.router.navigate(["/application/" + this.application.id]);
  }

  public setTileColor(): void {
    this.renderer.setStyle(this.elRef.nativeElement, "background-color", "hsl(" + this.application.colorHue + ", 100%, 34.5%)");
  }

  public getName(): string {
    return this.application.name;
  }
}
