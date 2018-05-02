import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss']
})
export class ProfilePageComponent implements OnInit {

  links = [
    {text: 'Details' , link: '/profile/details'},
    {text: 'Friends', link: '/profile/friends'}
  ]

  constructor() { }

  ngOnInit() {
  }

}
