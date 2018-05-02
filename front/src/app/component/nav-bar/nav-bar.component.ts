import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../service/authentication.service";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
  links = [
    {
      text: 'Profile',
      link: 'profile/details'
    },
    {
      text: 'Notes',
      link: 'notes'
    },
    {
      text: 'Resources', // books & video - global
      link: 'resources'
    }];

  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
  }

  logout(): void {
    this.authService.logout();
    window.location.reload();
  }

}
