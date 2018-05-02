import {Component, Input, OnInit} from '@angular/core';
import {Profile} from "../../model/profile";

@Component({
  selector: 'app-user-mini',
  templateUrl: './user-mini.component.html',
  styleUrls: ['./user-mini.component.scss']
})
export class UserMiniComponent implements OnInit {
  @Input()
  user: Profile;

  constructor() { }

  ngOnInit() {
  }

}
