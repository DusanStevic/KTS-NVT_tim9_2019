import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  public role: string;
  public isAuthenticated: boolean;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService) {}

  ngOnInit() {
    this.isAuthenticated = this.authenticationService.isLoggedIn();
    this.role = this.authenticationService.getRole();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  register(): void {
    this.router.navigate(['/register']);
  }

  logout() {
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }



}
