import { Component, OnInit } from '@angular/core';
import {ProfileService} from "../../../service/profile.service";
import {Profile} from "../../../model/profile";

@Component({
  selector: 'app-profil-details',
  templateUrl: './profil-details.component.html',
  styleUrls: ['./profil-details.component.scss']
})
export class ProfilDetailsComponent implements OnInit {

  profile: Profile;

  constructor(private profileService: ProfileService) { }

  ngOnInit() {
    this.profileService.getProfileForCurrUser().subscribe( profile => this.profile = profile);
  }

}
