import { Component, OnInit } from '@angular/core';
import {ProfileService} from "../../../service/profile.service";
import {Profile} from "../../../model/profile";

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.scss']
})
export class FriendsComponent implements OnInit {

  friends: Profile[];

  constructor(
    private profileService: ProfileService
  ) { }

  ngOnInit() {
    this.profileService.getFriendsForCurrUser().subscribe(
      friends => this.friends = friends
    );
  }

}
