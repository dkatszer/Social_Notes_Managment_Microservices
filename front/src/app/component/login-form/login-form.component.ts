import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../service/authentication.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {
  hide = true;
  model: any = {};
  error = false;

  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    this.authenticationService.logout();
  }

  login() {
    this.authenticationService.login(this.model.username, this.model.password)
      .then(result => {
        if (result === true) {
          // login successful
          this.router.navigate(['/']);
        } else {
          // login failed
          this.error = true;
        }
      }, error => {
        this.error = error;
      });
  }


}
