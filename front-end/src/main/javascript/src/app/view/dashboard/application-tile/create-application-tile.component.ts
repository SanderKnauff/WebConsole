import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {Router} from "@angular/router";
import {Tile} from "./tile";

@Component({
  selector: 'app-create-application-tile',
  templateUrl: './application-tile.component.html',
  styleUrls: ['./application-tile.component.scss', "./create-application-tile.component.scss"],
  host: {
    "(click)": "openApplication()"
  }
})
export class CreateApplicationTileComponent implements OnInit, Tile {

  constructor(private router: Router, private elRef: ElementRef, private renderer: Renderer2) {
  }

  ngOnInit() {
    this.setTileColor();
  }

  public openApplication(): void {
    this.router.navigate(["/create-application"]);
  }

  public setTileColor(): void {
    this.renderer.setStyle(this.elRef.nativeElement, "background-color", "rgb(0,0,0)");
  }

  public getName(): string {
    return "+";
  }
}
